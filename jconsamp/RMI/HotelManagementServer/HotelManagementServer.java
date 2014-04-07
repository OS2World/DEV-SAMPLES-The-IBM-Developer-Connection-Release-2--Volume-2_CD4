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
import java.io.* ;
import com.ibm.sdm.advjava.* ;
import com.ibm.sdm.hotel.* ;
import java.util.* ;

public class HotelManagementServer
{
	public static void main(java.lang.String[] args)
	{
		System.setSecurityManager(new java.rmi.RMISecurityManager()) ;
		try
		{
		  						
			System.out.println("In main") ;
			//: Load Managers
			CustomerManager cm ;
			try
			{
				FileInputStream fis = new FileInputStream("CustMgr.dat") ;
				ObjectInput oi = new ObjectInputStream(fis) ;
				cm = (CustomerManager)oi.readObject() ;
				System.out.println("CustomerManager retrieved from storage...") ;
				cm.initialize() ;
			}
			catch(Exception e1)
			{
				System.out.println("Creating new CustomerManager...") ;
				cm = CustomerManagerFactory.createCustomerManager((short)1, "hashtable", 1002) ; //: parameters are meaningless for now		
			}

			System.out.println("About to register remote object...") ;
			Naming.rebind("rmi://ntsrv:1099/CustomerManager", cm) ;
			System.out.println("CustomerManager is now ready.") ;

			
			String str = "0" ;
			while(true){
				System.out.println("\n\nSelect:\n1.  Delete all customers from datastore\n2.  Display all customers\n3.  Quit\n\n");
				BufferedReader d = new BufferedReader(new InputStreamReader(System.in)) ;
				str = d.readLine() ;
				if(str.equals("1")) {
					cm.deleteAllCustomers() ;
				}
				else if(str.equals("2")) {
					cm.displayAllCustomers() ;
				}
				else if(str.equals("3")) {
					break ;
				}
			}



			//: Save Managers
			try
			{
				FileOutputStream fos = new FileOutputStream("CustMgr.dat") ;
				ObjectOutput oo = new ObjectOutputStream(fos) ;
				oo.writeObject(cm) ;
			}
			catch(Exception e2)
			{
				System.out.print("Error serializing managers...") ;
				System.out.println(e2) ;
			}
			finally{
				cm.uninitialize() ;
				//: Global.factory().deleteEntity(cm) ;
			}
			System.exit(0) ;



		}
		catch(Exception e)
		{
			System.out.println(e.toString()) ;
		}
	}

}