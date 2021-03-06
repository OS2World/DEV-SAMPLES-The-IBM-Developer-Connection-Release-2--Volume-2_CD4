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



import com.ibm.sdm.hotel.* ;

public class Test
{
	public static void main(String[] args)
	{
		com.ibm.sdm.hotel.Customer customer = com.ibm.sdm.hotel.CustomerFactory.createCustomer((short)1,"CUSTINFO", 99, "Mickey", 
						'D',"Nix", "205 Kiowa Drive West", " ", "Gainesville", 
						"TX", "76240", "1-940-888-8888") ;
		System.out.println(customer.toString()) ;
	}
}	