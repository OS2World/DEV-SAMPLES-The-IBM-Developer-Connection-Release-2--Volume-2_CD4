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



import java.sql.* ;
import java.math.* ;

public class JDBCTest
{
	public static void main(String []args)
	{
	    String url = "jdbc:db2:HMS" ;  //: For local access
	   //: If network access, be sure to start "db2jstrt portnumber" on server to 
	   //:    start the DB2 JDBC Server.
	   //: String url = "jdbc:db2://9.53.19.169:4333/HMS" ; //: For network access
	  //:  String url = "jdbc:db2://NTSRV:4333/HMS" ; //: For network access with DNS
	//:	String url = "jdbc:db2://192.168.25.120:4333/HMS" ; 
	   
	   try{
		//: Be sure to set classpath=X:\sqllib\java\db2java.zip;%classpath%
		 Class.forName("COM.ibm.db2.jdbc.app.DB2Driver") ; 	//: For local access
		//: Class.forName("COM.ibm.db2.jdbc.net.DB2Driver") ;	//: For network access
		//: Really would not want to hard-code this!
		Connection connection = DriverManager.getConnection(url, "mnix", "password") ;
		System.out.println("Connection made") ;
		Statement statement = connection.createStatement() ;

		System.out.println("About to add employees to HotelEmployees table...") ;
		int numRowsAffected = statement.executeUpdate("INSERT INTO HotelEmployees VALUES(1001, 'David Nix','Manager', '1997-10-30', 100000.00)") ;
		numRowsAffected = statement.executeUpdate("INSERT INTO HotelEmployees VALUES(1002, 'John Doe', 'Clerk', '1995-1-22', 25000.50 )") ;
		numRowsAffected = statement.executeUpdate("INSERT INTO HotelEmployees VALUES(1003, 'Bart Simons','Marketing Specialist', '1992-5-19', 35000.98)") ;
		numRowsAffected = statement.executeUpdate("INSERT INTO HotelEmployees VALUES(1004, 'Karyn Rhodes','Educator', '1994-3-23', 56456.22)") ;
		System.out.println("Employees added--about to print their info...") ;

		ResultSet resultSet = statement.executeQuery("Select * from HotelEmployees") ;
		while(resultSet.next())
		{
			long employee_id = resultSet.getLong("EMPLOYEE_ID") ;
			String name = resultSet.getString("NAME") ;
			String title = resultSet.getString("TITLE") ;
			java.sql.Date date = resultSet.getDate("DATE_HIRED") ;
			java.math.BigDecimal salary = resultSet.getBigDecimal("SALARY", 2) ;
			
			System.out.println(employee_id + ":  " + name + ", " + title + "--hired on " + date.toString() + " at $" + salary) ;
		}
		resultSet.close() ;

		System.out.println("\nAbout to delete employee number 1003...") ;
		numRowsAffected = statement.executeUpdate("DELETE FROM HotelEmployees WHERE EMPLOYEE_ID = 1003") ;
		System.out.println("Employee number 1003 deleted.") ;

		System.out.println("\nAbout to update employee number 1001...") ;
		numRowsAffected = statement.executeUpdate("UPDATE HotelEmployees SET SALARY = 99999.86 WHERE EMPLOYEE_ID = 1004") ;
		System.out.println("Employee record updated") ;

		System.out.println("\nPrinting current employee info...") ;
		resultSet = statement.executeQuery("Select * from HotelEmployees") ;
		while(resultSet.next())
		{
			long employee_id = resultSet.getLong("EMPLOYEE_ID") ;
			String name = resultSet.getString("NAME") ;
			String title = resultSet.getString("TITLE") ;
			java.sql.Date date = resultSet.getDate("DATE_HIRED") ;
			java.math.BigDecimal salary = resultSet.getBigDecimal("SALARY", 2) ;
			
			System.out.println(employee_id + ":  " + name + ", " + title + "--hired on " + date.toString() + " at $" + salary) ;
		}
		resultSet.close() ;
		
		System.out.println("\nPrinting single record of employee number 1004") ;
		resultSet = statement.executeQuery("Select * from HotelEmployees WHERE EMPLOYEE_ID = 1004") ;
		while(resultSet.next())
		{
			long employee_id = resultSet.getLong("EMPLOYEE_ID") ;
			String name = resultSet.getString("NAME") ;
			String title = resultSet.getString("TITLE") ;
			java.sql.Date date = resultSet.getDate("DATE_HIRED") ;
			java.math.BigDecimal salary = resultSet.getBigDecimal("SALARY", 2) ;
			
			System.out.println(employee_id + ":  " + name + ", " + title + "--hired on " + date.toString() + " at $" + salary) ;
		}
		resultSet.close() ;

		System.out.println("\nRemoving all records from HotelEmployees database...") ;
		numRowsAffected = statement.executeUpdate("DELETE FROM HotelEmployees") ;
		System.out.println("Done!  There were " + numRowsAffected + " rows deleted") ;

		statement.close() ;
	   	connection.close() ;

	
	   }
	   catch(Exception exception) {
		System.out.println(exception.toString()) ;
	   }
   
	} //: end of main

}