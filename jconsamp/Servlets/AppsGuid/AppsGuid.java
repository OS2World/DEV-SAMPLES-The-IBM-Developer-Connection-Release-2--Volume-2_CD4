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



package com.ibm.sdm.appsguid ;

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

import com.ibm.ServletExpress.personalization.sessiontracking.IBMSessionData;
// import com.ibm.ServletExpress.personalization.userprofile.*;
import com.ibm.ServletExpress.personalization.util.VariableSubstitution ;
// import com.ibm.ServletExpress.util.*  ;
//import com.sun.server.http.session.*;


import java.sql.*;   
import COM.ibm.db2.jdbc.app.*;
import COM.ibm.db2.app.*;   



public class AppsGuid extends javax.servlet.http.HttpServlet
{
	private IBMSessionData session ;
	private Enumeration initParms ;
	private ServletContext context ;	
	private static ConnectionManager connMgr ;
	private Hashtable hashtable ;
	private StringBuffer buffer ;
	//: Place html file in Web server's public html root...
	public static final String htmlUrl  = "ProductInformation.html";

	//: Initializer section
	static
	{
		 try
        	  {
	   		Class.forName ("COM.ibm.db2.jdbc.app.DB2Driver").newInstance () ;
           		connMgr = new ConnectionManager();
           		System.out.println("Connections pool created");
        	  } //end  try
        	catch (Exception exception)
        	  {
           		exception.printStackTrace();
        	  } // end catch
        

	}


	//:  JavaSoft Java Servlet API 1.0 does not allow an init method with no function arguments
	public void init( ServletConfig config ) 
	{
		try {	
			super.init( config ) ; //:  Must call if overriding init( ServletConfig ) ;
			hashtable = new Hashtable() ;
			context = getServletContext( ) ;
			Connection connection = null ;
			try
		  	{
				connection = connMgr.getConnection();
				String sql = "Call GET_AM_ID_AND_NAME(?) " ;
				CallableStatement statement = connection.prepareCall(sql) ;
				statement.registerOutParameter (1, Types.CHAR);  //: Other
				String container = "sample" ;
			
				statement.setString(1,container) ;		//: setObject
				statement.execute() ;
			
				container = statement.getString(1) ;

				//: Parse info

				String temp = container ;
			
				int index = temp.indexOf(':') ;
				String numberString = temp.substring(0, index) ;
				int numberOfItems = Integer.parseInt(numberString) ;
				temp = temp.substring(index + 1) ;
				for( int i=0; i < numberOfItems; i++)
				{
					index = temp.indexOf(':') ;
					String product_id = temp.substring(0,index) ;
					
					temp = temp.substring(index + 1) ;
					index = temp.indexOf(';') ;
					String product_name = temp.substring(0, index) ;
					hashtable.put(product_id, product_name) ;
					temp = temp.substring(index + 1) ;
				}	

			
			
		  	}
			catch(Exception exception)
			  {
				exception.printStackTrace() ;
			  }
			finally
			  {
				try
				  {
					connMgr.putConnection(connection);
			  	} catch(Exception excpt) { excpt.printStackTrace() ; }
		  	  }


			initParms = config.getInitParameterNames( ) ;
			//:  ServletContext::log writes to X:\ServletExpress\logs\ServletExpress\servletservice\event_log
			context.log( "AppsGuid init( ServletConfig ) method invoked.\n" ) ;
		} catch( Exception exception ) {
			context.log( exception, exception.toString( ) ) ;
		}
	}
	


	public void destroy()
	{
		try
        	{
          		connMgr.destroyPool();
			connMgr = null ;
        	}
        	catch (Exception exception)
        	{
           		exception.printStackTrace();
			connMgr = null ;
        	} // end catch
	}
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

		super.service(request, response) ;
	}

  
*/

	//:  Performs the HTTP GET operation; the default implementation 
	//:	reports an HTTP BAD_REQUEST error.
	protected void doGet(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException 
	{
		Connection connection = null ;

		session = (IBMSessionData) request.getSession(true);
		context.log( "Using improvements...") ;
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
		print.println( "<H3>Applications Available for Free Download</H3>" ) ;
		print.println( "<ul>" ) ;
		print.println( "<FORM METHOD=\"POST\" ACTION=\"/servlet/AppsGuid\">" ) ;
		
		print.println( "<BR>Select a product and press REFRESH button:" ) ;
		print.println( "<BR><BR><SELECT NAME=\"List\" SIZE=\"8\" MAXLENGTH=\"52\">" ) ;
		
		if(hashtable.isEmpty() == false)
		  {
			for(Enumeration enum = hashtable.keys() ; enum.hasMoreElements() ; )
			  {
				String key = (String)enum.nextElement() ;
				String value = (String)hashtable.get(key) ;
				print.println( "<OPTION>" + key + " : " + value ) ;
			  }
		  }
		print.println( "</SELECT>" ) ;
		
		print.println( "<BR><BR><INPUT NAME=\"Button\" TYPE=\"SUBMIT\" VALUE=\"Refresh\">" ) ;
		print.println( "<SP><SP><INPUT NAME=\"Button\" TYPE=\"SUBMIT\" VALUE=\"Order\">" ) ;
		print.println( "</FORM>" ) ;
		
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
		Connection connection ;

		context.log( "Got the HTTP_POST request.") ;
		context.log("Post query string is " + request.getQueryString( ) ) ;
	
		//: Get the current sessionid - starts a session, if one does not exist yet.
       		session = (IBMSessionData) request.getSession(true);
		response.setContentType( "text/html" ) ;
	
		PrintWriter print = response.getWriter( ) ;
			

		Enumeration enum = request.getParameterNames( ) ;
		while(enum.hasMoreElements( ) ) 
		{
			String name = (String)enum.nextElement() ;
			String value[] = request.getParameterValues(name);
			for(int i=0; i < value.length; i++)
			{
				if(name.equals("Button") && value[i].equals("Refresh"))
				{
					try
					  {
						String list[] = request.getParameterValues("List") ;
						String product_id = list[0].substring(0,4) ;
						String product_name = list[0].substring(7) ;
					
						connection = connMgr.getConnection();
						String sql = "Call GET_AM_PRODUCT(?,?) " ;
						CallableStatement statement = connection.prepareCall(sql) ;

						statement.registerOutParameter(2, Types.CHAR) ;
						
						statement.setString(1,product_id) ;
						String unknown = new String("unkown") ;
						statement.setString(2,unknown) ;
						statement.execute() ;
						
						String info = statement.getString(2) ;
						
						int index = info.indexOf('\t') ;
						info = info.substring(index + 1) ;
						index = info.indexOf('\t') ;
						product_name = info.substring(0, index) ; 
						session.putValue(new String("product_id"), product_id);
						session.putValue(new String("product_name"), product_name);
											
						info = info.substring(index + 1) ;
						index = info.indexOf('\t') ;
						String description = info.substring(0, index) ;
						session.putValue(new String("description"), description);
						
						info = info.substring(index + 1) ;
						index = info.indexOf('\t') ;
						String supplier_id = info.substring(0, index) ;
						session.putValue(new String("supplier_id"), supplier_id);

						String url = info.substring(index + 1) ;
						session.putValue(new String("url"), url) ;


						
						statement.close() ;
						//: Variable substitution--see <!META VAR...> tags above

						StringBuffer output = VariableSubstitution.substitute(htmlUrl, null, request);
						print.println(new String(output));
     						print.flush();
     						print.close();

					  } catch(Exception exception) { exception.printStackTrace() ; }
				}
			}
		}
	

	
		print.println( "</BODY></HTML>" ) ;
		print.flush() ;
		print.close() ;

          
	}


  
}
