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

	
public class RegisterAppMgrSP
{
	private Connection connection ;
	static
			  {
			    try
			      {
			        System.out.println ("RegisterAppMgrSP\n\n");
			        Class.forName ("COM.ibm.db2.jdbc.app.DB2Driver").newInstance ();
			      }
			    catch (Exception e)
			      {
			        System.out.println ("\n  Error loading DB2 Driver...\n");
			        e.printStackTrace ();
			      }
  }
	
	
public static void main(String[] args)
  {
  	RegisterAppMgrSP app = new RegisterAppMgrSP() ;
	  
	
		String url = "jdbc:db2:appsguid" ;
		Statement dropStatement = null ;
 try
	{			
		app.connection = DriverManager.getConnection (url,"mnix","password");
	
	
        System.out.println("Dropping the stored procedure if it exists...\n") ;
        dropStatement = app.connection.createStatement ();
		
 
		//: Names of SPs are limited to 18 chars
    	
		dropStatement.executeUpdate("DROP PROCEDURE DELETE_ALL_AM_PROD") ;
	}
    catch (Exception e)
      {
        System.out.println(e.toString()) ; // ignore this error
      }
	  
	  
	   	try
	  	{		
	  		System.out.println("About to drop insertprocedure..\n") ;
	          dropStatement.executeUpdate ("DROP PROCEDURE INSERT_AM_PRODUCT" );
	  		
	        }
	      catch (Exception e)
	        {
	          System.out.println(e.toString()) ; // ignore this error
	        }
			
		
	   	try
	  	{		
	  		System.out.println("About to drop getProduct..\n") ;
	          dropStatement.executeUpdate ("DROP PROCEDURE GET_AM_PRODUCT" );
	  		
	        }
	      catch (Exception e)
	        {
	          System.out.println(e.toString()) ; // ignore this error
	        }


		try
	  	{		
	  		System.out.println("About to drop updateProduct..\n") ;
	          dropStatement.executeUpdate ("DROP PROCEDURE UPDATE_AM_PRODUCT" );
	  		
	        }
	      catch (Exception e)
	        {
	          System.out.println(e.toString()) ; // ignore this error
	        }



		try
	  	{		
	  		System.out.println("About to drop deleteProduct..\n") ;
	          dropStatement.executeUpdate ("DROP PROCEDURE DELETE_AM_PRODUCT" );
	  		
	        }
	      catch (Exception e)
	        {
	          System.out.println(e.toString()) ; // ignore this error
	        }





	
 		try
	  	{		
	  		System.out.println("About to drop getProductIDsAndNames..\n") ;
	          dropStatement.executeUpdate ("DROP PROCEDURE GET_AM_ID_AND_NAME" );
	  		
	        }
	      catch (Exception e)
	        {
	          System.out.println(e.toString()) ; // ignore this error
	        }
			

	   		
		 try{dropStatement.close ();}catch(Exception e){}

	  
    Statement statement = null ;	  

    try
      {
        statement = app.connection.createStatement ();

        // construct a parameter list for the stored procedure and
        // register it in the system catalogs
        String parameterList =
          "(IN PRODUCT_ID  VARCHAR(10)," +
          "  IN PRODUCT_NAME VARCHAR(40)," +
          "  IN SHORT_NAME  VARCHAR(10)," +
          "  IN DESCRIPTION VARCHAR(80)," +
          "  IN SUPPLIER_ID VARCHAR(10)," +
		  "  IN URL VARCHAR(80)," +
		  "  OUT NUMBERROWSAFFECTED INT)" ;
        

		System.out.println(parameterList) ;
        System.out.println("About to register INSERT_AM_PRODUCT stored procedure...\n") ;

        statement.executeUpdate ("CREATE PROCEDURE INSERT_AM_PRODUCT"  + parameterList +
                            " LANGUAGE JAVA " +
                            " PARAMETER STYLE DB2GENERAL NOT FENCED" +
                            " EXTERNAL NAME 'AppMgrSP!insertProduct'");
						
	
	  System.out.println("About to register UPDATE_AM_PRODUCT stored procedure...\n") ;

	  statement.executeUpdate ("CREATE PROCEDURE UPDATE_AM_PRODUCT"  + parameterList +
                            " LANGUAGE JAVA " +
                            " PARAMETER STYLE DB2GENERAL NOT FENCED" +
                            " EXTERNAL NAME 'AppMgrSP!updateProduct'");
							


							
	// construct a parameter list for the stored procedure and
        // register it in the system catalogs
        parameterList =
          "(OUT NUMBERROWSAFFECTED INT)" ; 
        

		System.out.println(parameterList) ;
        System.out.println("About to register DELETE_ALL_AM_PROD stored procedure...\n") ;

        statement.executeUpdate ("CREATE PROCEDURE DELETE_ALL_AM_PROD"  + parameterList +
                            " LANGUAGE JAVA " +
                            " PARAMETER STYLE DB2GENERAL NOT FENCED" +
                            " EXTERNAL NAME 'AppMgrSP!deleteAllProducts'");
	



	// construct a parameter list for the stored procedure and
        // register it in the system catalogs
        parameterList =
		"(IN PRODUCT_ID  VARCHAR(10)," +
          	" OUT NUMBERROWSAFFECTED INT)" ; 
        

		System.out.println(parameterList) ;
        System.out.println("About to register DELETE_AM_PRODUCT stored procedure...\n") ;

        statement.executeUpdate ("CREATE PROCEDURE DELETE_AM_PRODUCT"  + parameterList +
                            " LANGUAGE JAVA " +
                            " PARAMETER STYLE DB2GENERAL NOT FENCED" +
                            " EXTERNAL NAME 'AppMgrSP!deleteProduct'");
	






	
	// construct a parameter list for the stored procedure and
        // register it in the system catalogs
        parameterList =
          "(OUT TABLE VARCHAR(3000))" ;  //: BLOB(100000)
        

		System.out.println(parameterList) ;
        System.out.println("About to register GET_AM_ID_AND_NAME stored procedure...\n") ;

        statement.executeUpdate ("CREATE PROCEDURE GET_AM_ID_AND_NAME"  + parameterList +
                            " LANGUAGE JAVA " +
                            " PARAMETER STYLE DB2GENERAL NOT FENCED" +
                            " EXTERNAL NAME 'AppMgrSP!qetProductIDsAndNames'");
				
		
		
	// construct a parameter list for the stored procedure and
        // register it in the system catalogs
        parameterList =
          "(IN  PRODUCT_ID VARCHAR(10)," + 
	  " OUT PRODUCT VARCHAR(200))" ;  //: Only need 140
        

		System.out.println(parameterList) ;
        	System.out.println("About to register GET_AM_PRODUCT stored procedure...\n") ;

        statement.executeUpdate ("CREATE PROCEDURE GET_AM_PRODUCT"  + parameterList +
                            " LANGUAGE JAVA " +
                            " PARAMETER STYLE DB2GENERAL NOT FENCED" +
                            " EXTERNAL NAME 'AppMgrSP!getProduct'");
				
		




        statement.close ();
		app.connection.close() ;
      }
    catch (Exception e)
      {
	  	try{app.connection.close() ;}catch(Exception exception){}
        e.printStackTrace() ;
      }
  }
}  
