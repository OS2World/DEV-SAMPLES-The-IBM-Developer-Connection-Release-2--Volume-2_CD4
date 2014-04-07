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



import java.rmi.* ;
import com.ibm.sdm.advjava.* ;
import com.ibm.sdm.hotel.* ;

public class TravelAgent
{
	public static void main(java.lang.String []args)
	{
		System.out.println("I'm in main") ;
		try
		{
		  /*
			System.setSecurityManager(new RMISecurityManager()) ;
			String url = new String("//NTSRV:1099/RoomManager") ; //: 1099 is default port for RMI
			RoomManager rm = (RoomManager)Naming.lookup(url) ;
			rm.tell("Got it, Baby!") ;
			// System.out.println(rm.toString()) ;
			String url2 = new String("//NTSRV:1099/Customer") ;
			Customer cust = (Customer)Naming.lookup(url2) ;
			cust.printSelf() ;
			System.out.println(cust.remoteToString()) ;

	         */

			System.setSecurityManager(new RMISecurityManager()) ;
		//	String url = new String("//W95CLIENT:1099/CustomerManager") ; //: 1099 is default port for RMI
			String url = new String("//ntsrv/CustomerManager") ; 
			CustomerManager cm = (CustomerManager)Naming.lookup(url) ;
			cm.addCustomer(619272, "Mickey", "D", "Nix", "205 Kiowa Drive West", " ", "Gainesville", "Tx", "76240", "940-665-1517") ;
			cm.addCustomer(777777, "Steve", "Q", "McAllister", "1101 Hillcrest Lane", " ", "Austin", "Tx", "99999", "666-555-4444") ;
			cm.addCustomer(888888, "Donald", "Q", "Duck", "101 Disney Lane", " ", "Dallas", "Fl", "76543", "111-111-1111") ;
			cm.addCustomer(656565, "David", "A", "Nix", "1118 Stanley Street", " " , "Denton", "Tx", "76021", "817-387-1251") ;
			Customer customer = cm.getCustomerWithId(619272) ;
			if(customer != null)
			{
				customer.printSelf() ;
				System.out.println(customer.remoteToString()) ;
			}
			customer = cm.getCustomerWithId(777777) ;
			if(customer != null)
			{
				customer.printSelf() ;
				System.out.println(customer.remoteToString()) ;
			}
			customer = cm.getCustomerWithId(10) ;
			if(customer != null)
			{
				customer.printSelf() ;
				System.out.println(customer.remoteToString()) ;
			}

		


			customer = cm.getCustomerWithId(656565) ;
			if(customer != null)
			{
				customer.printSelf() ;
				System.out.println(customer.remoteToString()) ;
			}

	
		}
		catch(Exception e)
		{
			System.out.println(e.toString()) ;
		}

		System.exit(0) ;
	}
}