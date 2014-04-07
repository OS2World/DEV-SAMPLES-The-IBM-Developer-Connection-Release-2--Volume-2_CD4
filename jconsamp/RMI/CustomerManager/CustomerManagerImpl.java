/*
(c) Copyright IBM Corp. 1997  All rights reserved.

These sample programs are owned by International Business Machines
Corporation or one of its subsidiaries ("IBM") and are copyrighted and
licensed, not sold.

You may copy, modify, and distribute these sample programs in any
form without payment to IBM,  for any purpose including developing,
using, marketing or distributing programs that include or are
derivative works of the sample programs.

The sample programs are provided to you on an "AS IS" basis, without
warranty of any kind.  IBM HEREBY EXPRESSLY DISCLAIMS ALL WARRANTIES,
EITHER EXPRESS OR IMPLIED, INCLUDING , BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
Some jurisdictions do not allow for the exclusion or limitation of
implied warranties, so the above limitations or exclusions may not
apply to you.  IBM shall not be liable for any damages you suffer
as a result of using, modifying or distributing the sample programs
or their derivatives.

Each copy of any portion of this/these sample program(s) or any
derivative work, must include a the above copyright notice and warranty.
*/



package com.ibm.sdm.hotel ;
import java.util.* ;
import java.rmi.* ;
import java.io. * ;
import java.sql.* ;


public class CustomerManagerImpl extends PersistentEntityImpl
			implements CustomerManager
{

	public CustomerManagerImpl() throws RemoteException
	{
		System.out.println("CustomerManagerImpl constructor called") ;
	}

	public void initialize( ) throws RemoteException
	{
	  	try{
			String url = "jdbc:db2:HMS"  ; //: Local access--DB2 resides on this machine
			Class.forName("COM.ibm.db2.jdbc.app.DB2Driver") ; //: Need to set CLASSPATH to C:\SQLLIB\JAVA\DB2Java.zip
			connection = DriverManager.getConnection(url, "mnix", "password") ;
			customerTable = new Hashtable() ;
		} catch(Exception exception) {
			System.out.println(exception.toString()) ;
	      }

 		System.out.println("CustomerManager is initialized.") ;
	}

	public void uninitialize( ) throws RemoteException
	{
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		System.out.println("Entering readObject of CustomerManager...") ;
		in.defaultReadObject() ;	
	}

	private void writeObject(ObjectOutputStream out) throws IOException
	{
		System.out.println("Entering writeObject of CustomerManager...") ;
		out.defaultWriteObject() ;
	}


	public void addCustomer(Customer aCustomer) throws RemoteException
	{
	  	Long id = new Long(aCustomer.getId()) ;
		customerTable.put(id, aCustomer) ;
	}


	public void addCustomer(String anId, String aFirstName, String aMiddleInitial,
					String aLastName,	String anAddress1,String anAddress2,
					String aCity,String aState,String aZip,String aPhone) throws RemoteException
	{
	  
		//: Note:  Entity creation really should be handled by the Global Factory; but, for simplicity, we'll take
		//:        care of it here.  This really isn't too far fetched from a real design in that the Global
		//:        factory could be using output from a schema mapping tool to store a customer entity into
		//:        a specific database with specific columns.  But again, this really would be handled by
		//:        the Global Factory and would be dependent upon administrator configuration in some sort
		//:        of global naming service.
		
		try{
	      	Statement statement = connection.createStatement() ;
			int numRowsAffected = statement.executeUpdate("INSERT INTO HMSCUSTOMER VALUES('" + anId + "','" +
															aFirstName + "','" + 
															aMiddleInitial + "','" +
															aLastName + "','" +
															anAddress1 + "','" +
															anAddress2 + "','" +
															aCity + "','" +
															aState + "','" +
															aZip + "','" +
															aPhone +
															"')" ) ;
			System.out.println( "INSERT INTO HMSCUSTOMER VALUES('" + anId + "','" +
															aFirstName + "','" + 
															aMiddleInitial + "','" +
															aLastName + "','" +
															anAddress1 + "','" +
															anAddress2 + "','" +
															aCity + "','" +
															aState + "','" +
															aZip + "','" +
															aPhone +
															"')"  
						+ "and "  + numRowsAffected + " row(s) affected") ;

			statement.close() ;

			//: Create persistent entity and add it to internal hashtable
			customerHandle++ ;
			long numrep = Long.parseLong(anId) ;
			Customer customer = CustomerFactory.createCustomer((short)1,"CUSTINFO", customerHandle, numrep, aFirstName, 
						aMiddleInitial.charAt(0), aLastName, anAddress1, anAddress2, aCity, 
						aState, aZip, aPhone) ;
			Long id = new Long(numrep) ;
			customerTable.put(id, customer) ;
			
		} catch(Exception exception) {
			System.out.println(exception.toString()) ;
	      }


	}


	public void addCustomer(long anId, String aFirstName,	String aMiddleInitial,
					String aLastName,	String anAddress1,String anAddress2,
					String aCity,String aState,String aZip,String aPhone) throws RemoteException
	{
        	//: Not used, yet.
	}


	public void deleteCustomerWithId(String anId) throws RemoteException
	{
		try{
			Statement statement = connection.createStatement() ;
			int numRowsAffected = statement.executeUpdate("DELETE FROM HMSCUSTOMER WHERE CUSTOMER_ID = '" + anId + "'" ) ;
			System.out.println("Employee number " + anId + " deleted.") ;
			statement.close() ;

			//: Delete from internal hashtable
			Long id = new Long(Long.parseLong(anId)) ;
			customerTable.remove(id) ;

		} catch(Exception exception){

			System.out.println(exception.toString()) ;
			System.out.println("DELETE FROM HMSCUSTOMER WHERE CUSTOMER_ID = " + anId ) ;
		}


	  
	}



	public void deleteCustomerWithId(long anId) throws RemoteException
	{
	     //: Not used, yet.
	}

	

	public void deleteAllCustomers() throws RemoteException
	{
		try{
			Statement statement = connection.createStatement() ;
			int numRowsAffected = statement.executeUpdate("DELETE FROM HMSCUSTOMER") ;
			System.out.println(numRowsAffected + " row(s) deleted.") ;
			statement.close() ;

			//: Clear internal hashtable
			customerTable.clear() ;

		} catch(Exception exception){

			System.out.println(exception.toString()) ;
		}

	  
	}

	public Customer getCustomerWithId(long anId) throws RemoteException
	{
		//: Check internal hashtable first for customer
		Long id = new Long(anId) ;
		Customer customer;
		if((customer = (Customer)customerTable.get(id)) != null) {
			System.out.println("found in hashtable") ;
			return customer ;
		}
		//: If not found, check database and then create a persistent customer object if found
		else {
			try{
				System.out.println("looking in db") ;
				//: Check data base
	  			Statement statement = connection.createStatement() ;
				ResultSet resultSet = statement.executeQuery("SELECT * FROM HMSCUSTOMER WHERE CUSTOMER_ID = " + "'" + anId + "'") ;
				resultSet.next();
								
				//: Create persistent entity
				customerHandle++ ;
				customer = CustomerFactory.createCustomer((short)1,"HMSCUSTOMER", customerHandle,
										anId,
										resultSet.getString("FIRST_NAME"), 
										resultSet.getString("MI").charAt(0),
										resultSet.getString("LAST_NAME"),
										resultSet.getString("ADDRESS1"),
										resultSet.getString("ADDRESS2"),
										resultSet.getString("CITY"),
										resultSet.getString("STATE"),
										resultSet.getString("POSTALCODE"),
										resultSet.getString("PHONE")) ;
				resultSet.close() ;
				statement.close() ;

				//: Add to internal hashtable
				customerTable.put(id, customer) ;
				return customer ;
			} catch(Exception exception) {
				//: Not found in database--do nothing here
			}
		}
		System.out.println("about to return null") ;
		return (Customer)null ;
	
	}

	public void displayAllCustomers()
	{
		try{
			Statement statement = connection.createStatement() ;
        		ResultSet resultSet = statement.executeQuery("SELECT * FROM HMSCUSTOMER") ;
			while(resultSet.next())
			{
				String customer_id = resultSet.getString("CUSTOMER_ID") ;
				String name = resultSet.getString("FIRST_NAME") + " " + resultSet.getString("MI") + ". " + resultSet.getString("LAST_NAME") ;
						
				System.out.println(customer_id + ":  " + name) ;
			}
			resultSet.close() ;
			statement.close() ;
		} catch(Exception exception) {
			System.out.println(exception.toString()) ;
		}


	}
	
	private transient Hashtable customerTable ;
	private long customerHandle ;
	private transient Connection connection ;

}
