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



import HelloApp.* ;
import org.omg.CosNaming.* ;
import org.omg.CosNaming.NamingContextPackage.* ;
import org.omg.CORBA.* ;

class HelloServant extends _HelloImplBase
{
	public String sayHello()
	{
		return ("\nHello World!!\n") ;
	}
}


public class HelloServer
{
	public static void main(String args[])
	{
		try
		{
			System.out.println("//: Create an initialize the ORB") ;
			ORB orb = ORB.init(args, null) ;
			
			System.out.println("//: Create servant and register it with the ORB") ;
			HelloServant HelloRef = new HelloServant() ;
			orb.connect(HelloRef) ;
			
			System.out.println("//: Get the root naming context") ;
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService") ;
			NamingContext ncRef = NamingContextHelper.narrow(objRef) ;
			
			System.out.println("//: Bind the Object Reference in Naming") ;
			NameComponent nc = new NameComponent("Hello", "") ;
			NameComponent path[] = {nc} ;
			ncRef.rebind(path, HelloRef) ;
			
			System.out.println("//: Wait for invocations from clients") ;
			java.lang.Object sync = new java.lang.Object() ;
			synchronized (sync)
			{
				sync.wait() ;
			}
			System.out.println("//: Finished") ;
		} catch (Exception exception)
		  {
			System.out.println(exception.toString()) ;
		  	exception.printStackTrace() ;
		  } //: end catch
	} //: end main
} //: end class
