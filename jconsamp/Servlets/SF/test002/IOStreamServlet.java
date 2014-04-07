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



import java.io.IOException ;
import java.io.PrintStream ;
import java.io.PrintWriter ;

import java.util.Enumeration ;
import java.util.Hashtable ;

import javax.servlet.ServletConfig ;
import javax.servlet.ServletContext ;
import javax.servlet.ServletRequest ;
import javax.servlet.ServletResponse ;
import javax.servlet.ServletInputStream ;
import javax.servlet.ServletOutputStream ;
import javax.servlet.ServletException ;

import javax.servlet.http.HttpServlet ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import javax.servlet.http.HttpUtils ;
import javax.servlet.http.HttpSession ;
import com.ibm.ServletExpress.personalization.sessiontracking.IBMSessionData ;


public class IOStreamServlet extends javax.servlet.http.HttpServlet
{

	//:  Performs the HTTP GET operation; the default implementation 
	//:	reports an HTTP BAD_REQUEST error.
	protected void doGet(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException 
	{
	
		response.setContentType( "text/html" ) ;

		PrintWriter print = response.getWriter( ) ;
		print.println("Got it") ;

		print.flush( ) ;
		print.close( ) ;
	
	

	}
	

  
}
