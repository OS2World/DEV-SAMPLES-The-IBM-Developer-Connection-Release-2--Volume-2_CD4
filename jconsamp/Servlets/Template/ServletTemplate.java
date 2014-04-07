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


public class ServletTemplate extends javax.servlet.http.HttpServlet
{
	//:  JavaSoft Java Servlet API 1.0 does not allow an init method with no function arguments
	public void init( ServletConfig config ) 
	{
		try {	
			super.init( config ) ; //:  Must call if overriding init( ServletConfig ) ;
			context = getServletContext( ) ;
			initParms = config.getInitParameterNames( ) ;
			//:  ServletContext::log writes to X:\ServletExpress\logs\ServletExpress\servletservice\event_log
			context.log( "ServlateTemplate init( ServletConfig ) method invoked.\n" ) ;
		} catch( Exception exception ) {
			context.log( exception, exception.toString( ) ) ;
		}
	}


	public void destroy()
    	{
                context.log("ServletTemplate destroy() has been called.\n");
    	} // end of destroy()

	
/*
  
	//:  Could override service( ServletRequest, ServletResponse ) ;but, 
	//:	you'll lose default TRACE and OUTPUT methods processing.
	//:	Notice that the service method is not typically overridden. The service method, as provided,
	//:	supports standard HTTP requests by dispatching them to appropriate methods, such as the methods
	//:	listed above that have the prefix "do". In addition, the service method also supports the HTTP 1.1
	//:	protocol's TRACE and OPTIONS methods by dispatching to the doTrace and doOptions
	//:	methods. The doTrace and doOptions methods are not typically overridden. 
	public void service( ServletRequest request, ServletResponse response ) throws ServletException, IOException
	{
		try {
			context.log("Init Parms are:  ") ;
			while( initParms.hasMoreElements( ) )
			{
				context.log((String)initParms.nextElement( ) ) ;
			}
			context.log( context.getServerInfo( ) ) ;
			Enumeration enum = context.getServletNames( ) ;
			while( enum.hasMoreElements( ) )
			{
				context.log( (String)enum.nextElement( ) ) ;
			}

		} catch( Exception exception ) {
			context.log( exception, (String)exception.toString( ) ) ;
		}
		//: If problems, try removing this....
		super.service(request, response) ;
	}

  */
	//:  Performs the HTTP GET operation; the default implementation 
	//:	reports an HTTP BAD_REQUEST error.
	protected void doGet(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException 
	{
		context.log( "Got the HTTP_GET request.") ;
		//:  Invoke servlet with http://host/servlet/ServletTemplate?FN=Mickey&LN=Nix
		context.log(request.getQueryString( ) ) ;
	
	
		//: Add some real HTML
		//: Probably don't want to use "text/plain" with HTML tags
		//: WARNING:  DON'T TRY TO MIX "text/XXX" types in same servlet,
		//:	as you'll get a 404 error
		response.setContentType( "text/html" ) ;

		PrintWriter print = response.getWriter( ) ;
		print.println( "<HTML><HEAD><TITLE>" ) ;
		print.println( "Solution Developer Marketing Application Guide" ) ;
		print.println( "</TITLE></HEAD>" ) ;
		print.println( "<BODY>" ) ;
		print.println( "<H3>NC Consultants</H3>" ) ;
		print.println( "<ul>" ) ;
		print.println( "<FORM METHOD=\"POST\" ACTION=\"/servlet/ServletTemplate\">" ) ;
		print.println( "<TABLE COLS=2 FRAME=ALL WIDTH=600 NOWRAP>" ) ;
		print.println( "<TBODY>" ) ;
		print.println( "<TR>Select a consultant and press REFRESH button:" ) ;
		print.println( "<TD NOWRAP>" ) ;
		print.println( "<SELECT NAME=\"List\" SIZE=\"8\" MAXLENGTH=\"52\">" ) ;
		
		print.println( "<OPTION>Mickey Nix" ) ;
		print.println( "<OPTION>Irene Roberts" ) ;
		print.println( "<OPTION>George Menz" ) ;
		print.println( "<OPTION>Fred Gutierrez" ) ;
		print.println( "<OPTION>Yogesh Shah" ) ;
		
		print.println( "</SELECT>" ) ;
		print.println( "<TD>" ) ;
		print.println( "<TABLE COLS=2 FRAME=ALL WIDTH=600 NOWRAP>" ) ;
		print.println( "<TR>" ) ;
		print.println( "<TD><img src=\"http://dbserver/images/leftspikes.gif\">Name:" ) ;
		print.println( "<TD>" ) ;

		print.println( "<TR>" ) ;
		print.println( "<TD><img src=\"http://dbserver/images/rightspikes.gif\">Location:" ) ;
		print.println( "<TD>" ) ;

		print.println( "<TR>" ) ;
		print.println( "<TD><img src=\"http://dbserver/images/leftspikes.gif\">Department:" ) ;
		print.println( "<TD>" ) ;

		print.println( "<TR>" ) ;
		print.println( "<TD><img src=\"http://dbserver/images/rightspikes.gif\">Status:" ) ;
		print.println( "<TD>" ) ;
		print.println( "</TABLE>" ) ;

		print.println( "</TABLE>" ) ;
		print.println( "<BR><INPUT NAME=\"Button\" TYPE=\"SUBMIT\" VALUE=\"Refresh\">" ) ;
		print.println( "<SP><SP><INPUT NAME=\"Button\" TYPE=\"SUBMIT\" VALUE=\"Order\">" ) ;
		print.println( "</FORM>" ) ;
		print.println( "<BR>" ) ;
		print.println( "<a href=\"http://www.ibm.com\">Press here to goto product's home page.</a>" ) ;
		print.println( "</ul>" ) ;
		print.println( "</BODY></HTML>" ) ;

		print.flush( ) ;
		print.close( ) ;
	
	

	}
	
	//:  Performs the HTTP POST operation; the default implementation 
	//:	reports an HTTP BAD_REQUEST error.
	protected void doPost(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException 
	{
		context.log( "Got the HTTP_POST request.") ;
		context.log("Post query string is " + request.getQueryString( ) ) ;
	
		// Get the current sessionid - starts a session, if one does not exist yet.
       		session = (IBMSessionData)request.getSession(true);
	//	if(request.isRequestedSessionIdValid())
		{
			response.setContentType( "text/html" ) ;
			PrintWriter print = response.getWriter( ) ;
		
			print.println( "<HTML><HEAD><TITLE>" ) ;
			print.println( "<P>Solution Developer Marketing Application Guide" ) ;
			print.println( "</TITLE></HEAD>" ) ;
			print.println( "<BODY>" ) ;
		
			print.println( "<BR>Server Name:  " + request.getServerName( ) ) ;
			print.println( "<BR>Server Port:  " + request.getServerPort( ) ) ;
			print.println( "<HR>" ) ;
			print.println( "<P>Remote Host:  " + request.getRemoteHost( ) ) ;
			print.println( "<BR>Remote Addr:  " + request.getRemoteAddr( ) ) ;
			print.println( "<HR>" ) ;
			print.println( "<BR>getQueryString is " + request.getQueryString() ) ;
			print.println( "<BR>Parameter names from getParameterNames are:" ) ;
			Enumeration enum = request.getParameterNames( ) ;
			while(enum.hasMoreElements( ) ) 
			{
				String name = (String)enum.nextElement() ;
				String value[] = request.getParameterValues(name);
				for(int i=0; i < value.length; i++)
				{
					print.println( "<BR>Name:  " + name + "; Value:  " + value[i] ) ;
				}
			}
	
			print.println( "</BODY></HTML>" ) ;
			print.flush() ;
			print.close() ;

          } //: end if

	}



	
	private Enumeration initParms ;
	private ServletContext context ;
	private IBMSessionData session ;

  
}
