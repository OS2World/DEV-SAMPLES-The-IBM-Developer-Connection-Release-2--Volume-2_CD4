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



import java.sql.*;   
import COM.ibm.db2.jdbc.app.*;
import COM.ibm.db2.app.*;   

public class TestAppMgrSP
{
	private Connection connection ;
	
	static
	  {
	    try
	      {
	        System.out.println ();
	        System.out.println ("TestAppMgrSP\n\n");
	        Class.forName ("COM.ibm.db2.jdbc.app.DB2Driver").newInstance ();
	      }
	    catch (Exception e)
	      {
	        System.out.println ("\n  Error loading DB2 Driver...\n");
	        e.printStackTrace ();
	      }
  }
	public static void main(String args[])
  {
  	TestAppMgrSP app = new TestAppMgrSP() ;
    try
	  {		
	  	String url = "jdbc:db2:appsguid" ;
		app.connection = DriverManager.getConnection (url,"mnix","password");
		int numberRowsAffected = 0 ;
		CallableStatement statement = null ;
		String sql = null ;


	
/*	   
	//: Delete any existing entries in AppsGuide::isvapps
	
	sql = "Call DELETE_ALL_AM_PROD(?) " ;
	statement = app.connection.prepareCall(sql) ;
	statement.registerOutParameter(1, Types.INTEGER) ;
	statement.setInt(1,numberRowsAffected) ;
	statement.execute() ;
	System.out.println("Number or rows affected is " + statement.getInt(1)) ;

*/

	sql = "Call DELETE_AM_PRODUCT(?,?) " ;
	statement = app.connection.prepareCall(sql) ;
	statement.registerOutParameter(2, Types.INTEGER) ;
	statement.setString(1,"7873") ;  //: product_id of product to delete
	statement.setInt(2,numberRowsAffected) ;
	statement.execute() ;
	System.out.println("Number or rows affected is " + statement.getInt(2)) ;
	   
	sql = "Call INSERT_AM_PRODUCT(?,?,?,?,?,?,?) ";
	statement = app.connection.prepareCall(sql);
	  
	System.out.println("About to register the output parameters...\n") ;
	statement.registerOutParameter (7, Types.INTEGER);
	 
	  
	System.out.println("About to set all parameters (input and output)...\n") ;
	statement.setString(1, "7873");  			//: product_id
	statement.setString(2, "QuickCode") ; 		//: product_name 
	statement.setString(3, "Quick") ;				//: short_name
	statement.setString(4, "Best doggone graphics editor in the world") ; //: description
	statement.setString(5, "9999") ; 			//: supplier_id
	statement.setString(6, "http://www.ibm.com") ;  //: url
	statement.setInt(7,numberRowsAffected ) ; //: numberRowsAffected
	
	System.out.println ("About to call stored procedure...\n");
	statement.execute ();
    System.out.println ("Successfully returned from stored procedure...\n") ;
	numberRowsAffected = statement.getInt(7);
	System.out.println("Number of rows affected is " + numberRowsAffected ) ;





sql = "Call UPDATE_AM_PRODUCT(?,?,?,?,?,?,?) ";
	statement = app.connection.prepareCall(sql);
	  
	System.out.println("About to register the output parameters...\n") ;
	statement.registerOutParameter (7, Types.INTEGER);
	 
	  
	System.out.println("About to set all parameters (input and output)...\n") ;
	statement.setString(1, "7873");  			//: product_id
	statement.setString(2, "QuickCode") ; 		//: product_name 
	statement.setString(3, "Quicker") ;				//: short_name
	statement.setString(4, "Best doggone graphics editor in the world") ; //: description
	statement.setString(5, "9999") ; 			//: supplier_id
	statement.setString(6, "http://www.quicker.com") ;  //: url
	statement.setInt(7,numberRowsAffected ) ; //: numberRowsAffected
	
	System.out.println ("About to call stored procedure...\n");
	statement.execute ();
    System.out.println ("Successfully returned from stored procedure...\n") ;
	numberRowsAffected = statement.getInt(7);
	System.out.println("Number of rows affected is " + numberRowsAffected ) ;



	sql = "Call GET_AM_PRODUCT(?,?) " ;
	statement = app.connection.prepareCall(sql) ;

	statement.registerOutParameter(2, Types.CHAR) ;
	String id = new String("7873") ;
	statement.setString(1,id) ;
	String unknown = new String("unkown") ;
	statement.setString(2,unknown) ;
	statement.execute() ;
	System.out.println("result is:  " + statement.getString(2)) ;
	statement.close() ;
	app.connection.close() ;
	  }
	catch(Exception exception)
	  {
	  	try{app.connection.close() ;} catch(Exception e){} 
	  	exception.printStackTrace() ;
	  }      
  	  
	  
	}  
	      
}	  
