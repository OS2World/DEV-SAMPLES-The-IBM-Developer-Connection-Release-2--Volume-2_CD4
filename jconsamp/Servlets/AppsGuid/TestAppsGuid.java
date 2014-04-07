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
import javax.servlet.ServletOutputStream ;
import javax.servlet.ServletException ;

import javax.servlet.http.HttpServlet ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

import java.sql.*;   
import COM.ibm.db2.jdbc.app.*;
import COM.ibm.db2.app.*;   


public class TestAppsGuid
{
	public static void main(String args[])
	{
		try
		  {
	   /* When I can pass a Java object...
			Class.forName ("COM.ibm.db2.jdbc.app.DB2Driver").newInstance ();
			String url = "jdbc:db2:appsguid" ;
		    	Connection connection = DriverManager.getConnection (url,"mnix","password");
			String sql = "Call GET_AM_ID_AND_NAME(?) " ;
			CallableStatement statement = connection.prepareCall(sql) ;
			statement.registerOutParameter (1, Types.BINARY);  //: Other
			Hashtable table = new Hashtable( ) ;
		
			statement.setObject(1,(Blob)table, Types.BINARY, 1) ;		//: setObject
			statement.execute() ;
	
			table = (Hashtable)statement.getObject(1) ;
			for(Enumeration enum = table.elements() ; enum.hasMoreElements() ; )
			{
				String product_id = (String)enum.nextElement() ;
				String product_name = (String)table.get(product_id) ;
				System.out.println( "<OPTION>" + product_id + " : " + product_name) ;
				
			}
	*/

	//: Until then, ...
			Class.forName ("COM.ibm.db2.jdbc.app.DB2Driver").newInstance ();
			String url = "jdbc:db2:appsguid" ;
		    	Connection connection = DriverManager.getConnection (url,"mnix","password");
			String sql = "Call GET_AM_ID_AND_NAME(?) " ;
			CallableStatement statement = connection.prepareCall(sql) ;
			statement.registerOutParameter (1, Types.CHAR);  //: Other
			String container = "sample" ;
		
			statement.setString(1,container) ;		//: setObject
			statement.execute() ;
	
			container = statement.getString(1) ;
		//	String temp = statement.getString(1) ;
			//: Sample string:  "2:1001          Prod001;1002           Prod002;"
			String temp = container ;
			
			int index = temp.indexOf(':') ;
			String numberString = temp.substring(0, index) ;
			System.out.println(numberString) ;
			int numberOfItems = Integer.parseInt(numberString) ;
			temp = temp.substring(index + 1) ;
			for( int i=0; i < numberOfItems; i++)
			{
				index = temp.indexOf(':') ;
				String product_id = temp.substring(0,index) ;
				System.out.println(product_id) ;
				temp = temp.substring(index + 1) ;
				index = temp.indexOf(';') ;
				String product_name = temp.substring(0, index) ;
				System.out.println(product_name) ;
				temp = temp.substring(index + 1) ;
			}

			
			
			
			
			
		  }
		catch(Exception exception)
		  {
			exception.printStackTrace() ;
		  }
		  
	} //: end main		  
}
