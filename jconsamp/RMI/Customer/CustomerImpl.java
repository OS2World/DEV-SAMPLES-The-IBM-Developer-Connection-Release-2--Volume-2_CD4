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
import java.rmi.* ;


public class CustomerImpl extends PersistentEntityImpl
			implements Customer
{

	public CustomerImpl() throws java.rmi.RemoteException, RemoteException 
	{
		
	}

	public void initialize(	long anId, String aFirstName, char aMiddleInitial, String aLastName, String anAddress1, 
					String anAddress2, String aCity, String aState, String aZip, String aPhone) 
					throws RemoteException
	{
		id = anId ;
		creditCardType = MASTERCARD ;
	
		if(aFirstName != null) firstName = aFirstName ;
			else firstName = " " ;

		if(aMiddleInitial != 0) middleInitial = aMiddleInitial ;
			else middleInitial = ' ' ;

		if(aLastName != null) lastName = aLastName ;
			else lastName = " " ;

		if(anAddress1 != null) address1 = anAddress1 ;
			else address1 = " " ;

		if(anAddress2 != null) address2 = anAddress2 ;
			else address2 = " " ;

		if(aCity != null) city = aCity ;
			else city = " " ;

		if(aState != null) state = aState ;
			else state = " " ;

		if(aZip != null) zip = aZip ;
			else zip = " " ;

		if(aPhone != null) phone = aPhone ;
			else phone = " " ;
	}	


	public void uninitialize() throws RemoteException
	{
	}
	


	

	public void setId(long anId) throws RemoteException
	{
		id = anId ;
	}

	public void setCreditCardType(short type) throws RemoteException
	{
		creditCardType = type ;
	}

	public void setFirstName(String aFirstName) throws RemoteException
	{
		firstName = aFirstName ;
	}

	public void setMiddleInitial(char aMiddleInitial) throws RemoteException
	{
		middleInitial = aMiddleInitial ;
	}

	public void setLastName(String aLastName) throws RemoteException
	{
		lastName = aLastName ;
	}

	public void setAddress1(String anAddress1) throws RemoteException
	{
		address1 = anAddress1 ;
	}

	public void setAddress2(String anAddress2) throws RemoteException
	{
		address2 = anAddress2 ;
	}

	public void setCity(String aCity) throws RemoteException
	{
		city = aCity ;
	}

	public void setState(String aState) throws RemoteException
	{
		state = aState ;
	}

	public void setZip(String aZip) throws RemoteException
	{
		zip = aZip ;
	}

	public void setPhone(String aPhone) throws RemoteException
	{
		phone = aPhone ;
	}

	
	
	public long getId() throws RemoteException
	{
		return id ;
	}

	public short getCreditCardType() throws RemoteException
	{
		return creditCardType ;
	}

	public String getFirstName() throws RemoteException 
	{
		return firstName ;
	}

	public char getMiddleInitial() throws RemoteException 
	{
		return middleInitial ;
	}

	public String getLastName() throws RemoteException 
	{
		return lastName ;
	}

	public String getAddress1() throws RemoteException 
	{
		return address1 ;
	}

	public String getAddress2() throws RemoteException 
	{
		return address2 ;
	}

	public String getCity() throws RemoteException 
	{
		return city ;
	}

	public String getState() throws RemoteException 
	{
		return state ;
	}

	public String getZip() throws RemoteException 
	{
		return zip ;
	}

	public String getPhone() throws RemoteException 
	{
		return phone ;
	}



	public String toString() 
	{
		try
		{
			return(firstName + " " + middleInitial + ". " + lastName + ":  " + getHandle()) ;
		}
		catch(Exception e)
		{
			System.out.println(e.toString()) ;
			return null ;
		}
	}


	public void printSelf() throws RemoteException
	{
		try
		{
			System.out.println(firstName + " " + middleInitial + ". " + lastName + ":  " + getHandle()) ;
		}
		catch(Exception e)
		{
			System.out.println(e.toString()) ;
		}
	}

	public String remoteToString() throws RemoteException
	{
		try
		{
			return(firstName + " " + middleInitial + ". " + lastName + ":  " + getHandle()) ;
		}
		catch(Exception e)
		{
			System.out.println(e.toString()) ;
			return null ;
		}
	}

	
	


	private long id ;
	private short creditCardType ;
	private String firstName, lastName, address1, address2, city, state, zip, phone ;
	private char middleInitial ;

	static final public short VISA = 0, MASTERCARD = 1, DINERSCLUB = 2, AMEX = 3 ;

}

