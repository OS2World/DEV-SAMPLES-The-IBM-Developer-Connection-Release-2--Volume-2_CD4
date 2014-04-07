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



import java.util. * ;
import java.sql.*;   
import COM.ibm.db2.app.*;   

public class AppMgrSP extends StoredProc
{
	public void deleteAllProducts( int numberRowsAffected ) throws Exception
	{
		// get caller's connection to the database; inherited from StoredProc
		Connection connection = getConnection ();
		
		// calculate the total payroll and average salary before the increase
		Statement statement = connection.createStatement ();
		String sql = "DELETE FROM isvapps" ;											
		int rowsAffected = statement.executeUpdate(sql);
		set (1, rowsAffected);
		statement.close() ;
		connection.close() ;
	}



	public void deleteProduct( String product_id, int numberRowsAffected ) throws Exception
	{
		// get caller's connection to the database; inherited from StoredProc
		Connection connection = getConnection ();
		
		// calculate the total payroll and average salary before the increase
		Statement statement = connection.createStatement ();
		String sql = "DELETE FROM isvapps WHERE product_id = '" + product_id + "'" ;											
		int rowsAffected = statement.executeUpdate(sql);
		set (2, rowsAffected);
		statement.close() ;
		connection.close() ;
	}

		
	
  	public void insertProduct (	String product_id,
						String product_name,
						String short_name,
						String description,
						String supplier_id, 
						String url,
						int numberRowsAffected ) throws Exception
  	{
    	// get caller's connection to the database; inherited from StoredProc
    	Connection connection = getConnection ();

    	// calculate the total payroll and average salary before the increase
    	Statement statement = connection.createStatement ();
    	String sql = "INSERT INTO isvapps VALUES('" + product_id + "','" + 
								product_name + "','" + 
							 	short_name + "','" + 
							 	description + "'," +
								"null,null,null,null,null,null,'" +
								supplier_id + "',null,'" +
								url + "',null)" ;
													
  
  											
    	int rowsAffected = statement.executeUpdate(sql);
		set (7, rowsAffected);
		statement.close() ;
		connection.close() ;
  	}
	


	public void updateProduct (	String product_id,
						String product_name,
						String short_name,
						String description,
						String supplier_id, 
						String url,
						int numberRowsAffected ) throws Exception
  	{
    	// get caller's connection to the database; inherited from StoredProc
    	Connection connection = getConnection ();

    	// calculate the total payroll and average salary before the increase
    	Statement statement = connection.createStatement ();
    	String sql = "UPDATE isvapps SET product_name = '" + product_name +
			 "', short_name = '" + short_name +
			 "', description = '" + description +
			 "', supplier_id = '" + supplier_id +
			 "', url = '" + url + "' WHERE product_id = '" + product_id + "'" ;
			 
													
  
  											
    	int rowsAffected = statement.executeUpdate(sql);
		set (7, rowsAffected);
		statement.close() ;
		connection.close() ;
  	}
	




	public void getProduct(String product_id, String holder)
	{
		String product_name = null, description = null, supplier_id = null, url = null ;
		try
		  {
			// get caller's connection to the database; inherited from StoredProc
    			Connection connection = getConnection ();
			Statement statement = connection.createStatement() ;
			String sql = "Select PRODUCT_NAME, DESCRIPTION, SUPPLIER_ID, URL from isvapps WHERE PRODUCT_ID = '" + product_id + "'" ; 
			ResultSet resultSet = statement.executeQuery(sql) ;
			//: Only 1 row returned
			while(resultSet.next())
			{
				product_name = resultSet.getString("PRODUCT_NAME") ;
				description = resultSet.getString("DESCRIPTION") ;
				supplier_id = resultSet.getString("SUPPLIER_ID") ;
				url = resultSet.getString("URL") ;
     		  	}
			resultSet.close() ;
			statement.close() ;

			String info = new String(product_id) ;
			info += "\t" ;
			info += product_name ;
			info += "\t" ;
			info += description ;
			info += "\t" ;
			info += supplier_id ;
			info += "\t" ;
			info += url ;
		
		    	set(2, info) ;
			connection.close() ;
		
		  } catch(Exception exception) {exception.printStackTrace() ; }
          
			
	}


	
	public void qetProductIDsAndNames(String container)  //: Object table
	{
		
	

	// Hashtable table = (Hashtable)object ;
		int counter = 0 ;
		String temp = new String() ;
		container = "Will it work?" ;
	
	 	try
		  {
			// get caller's connection to the database; inherited from StoredProc
    			Connection connection = getConnection ();

	    		// calculate the total payroll and average salary before the increase
	    		Statement statement = connection.createStatement ();

	    		String sql = "SELECT PRODUCT_ID, PRODUCT_NAME FROM isvapps" ;
			ResultSet results = statement.executeQuery(sql) ;
				
			set(1,"About to enter while loop\n") ;
			while(results.next())
			{
			  // When I can pass a Java object....
			
				//	String product_id = results.getString("PRODUCT_ID") ;
				//	String product_name = results.getString("PRODUCT_NAME") ;
				
				// 	table.put(product_id, product_name) ;
			  //
			  //: Until then, ...
				String product_id = results.getString("PRODUCT_ID") ;
				String product_name = results.getString("PRODUCT_NAME") ;
				temp += product_id ;
				temp += ":" ;
				temp += product_name ;
				temp += ";" ;
				counter++ ;
				
			}
			results.close() ;
		Integer wrapper = new Integer(counter) ;
		container = wrapper.toString()  ;
		container += ":" ;
		container += temp ;
			
		// set(1, "testing") ;
		 set(1, container) ;
							
	    	statement.close() ;
		connection.close() ;	
		   }
		catch(Exception exception)
		   {
				try{set(1,exception.toString()) ;}catch(Exception e){}
		   		exception.printStackTrace() ;
		   }

		
	}
}
