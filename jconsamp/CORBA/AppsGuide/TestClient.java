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




import IsvappsCORBA.* ;
import org.omg.CosNaming.* ;
import org.omg.CORBA.* ;

//: To run remotely:
//:   java TestClient -ORBInitialPort 1050 -ORBInitialHost hostname
//: To run locally:
//: 	java TestClient -ORBInitialPort 1050

public class TestClient
{
	public static void main(String args[])
	{
		try
		{
			System.out.println("//: Create and initialize the ORB") ;
			ORB orb = ORB.init(args, null) ;
			
			System.out.println("//: Get the root naming context") ;
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService") ;
			NamingContext ncRef = NamingContextHelper.narrow(objRef) ;
			
			System.out.println("//: Resolve the Object Reference in Naming") ;
			NameComponent nc = new NameComponent("IsvappsManager", "") ;
			NameComponent path[] = {nc} ;
			IsvappsManager mgr = IsvappsManagerHelper.narrow(ncRef.resolve(path)) ;
			
			System.out.println("//: Attach to the IsvappsManagerServant object and print results") ;
			String products = mgr.getProducts() ;
			System.out.println(products) ;
			String product = mgr.getProduct("1001") ;
			System.out.println(product) ;
						
		} catch(Exception exception)
		  {
			System.out.println(exception.toString()) ;
		  	exception.printStackTrace() ;
		  } //: end catch
	} //: end main
} //: end class

