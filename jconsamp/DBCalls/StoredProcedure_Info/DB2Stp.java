

//  Source File Name: DB2Stp.java  %I%
//
//  Licensed Materials -- Property of IBM
//
//  (c) Copyright International Business Machines Corporation, 1996, 1997.
//      All Rights Reserved.
//
//  US Government Users Restricted Rights -
//  Use, duplication or disclosure restricted by
//  GSA ADP Schedule Contract with IBM Corp.

//  Sample Program - Java Stored Procedure

//  Steps to run the sample:
//  (1) create and populate the SAMPLE database (db2sampl)
//  (2) compile this java file (javac DB2Stp.java)
//  (3) copy the resultant DB2StpSample.class file into sqllib/function
//  (4) run the sample (java DB2Stp)

//  NOTES: (1) The jdk11_path database manager configuration parameter must
//             be set
//         (2) The CLASSPATH and shared library path environment variables
//             must be set, as for any JDBC application.
//         (3) Visit http://www.software.ibm.com/data/db2/java
//             for current DB2 Java information


//  Class DB2Stp contains four methods:
//  (1) registerStoredProc: register the stored procedure
//  (2) callStoredProc: call the stored procedure
//  (3) main: application body (register and call the stored procedure)
//  Class DB2StpSample contains one method:
//  (1) salaryModification: stored procedure body


import java.sql.*;              // JDBC classes
import java.math.*;             // BigDecimal
import COM.ibm.db2.jdbc.app.*;  // DB2 UDB JDBC classes
import COM.ibm.db2.app.*;       // StoredProc and associated classes

class DB2Stp
{
  static
  {
    try
      {
        System.out.println ();
        System.out.println ("  Java Stored Procedure Sample");
        Class.forName ("COM.ibm.db2.jdbc.app.DB2Driver").newInstance ();
      }
    catch (Exception e)
      {
        System.out.println ("\n  Error loading DB2 Driver...\n");
        e.printStackTrace ();
      }
  }


  // (1) register the stored procedure
  public static void registerStoredProc (Connection con,
                                         String name,
                                         String classMethod,
                                         String mode) throws Exception
  {
    try
      {
        // drop the stored procedure if it exists
        Statement dropStmt = con.createStatement ();
        dropStmt.executeUpdate ("DROP PROCEDURE " + name);
        dropStmt.close ();
      }
    catch (SQLException e)
      {
        // ignore this error
      }

    try
      {
        Statement stmt = con.createStatement ();

        // construct a parameter list for the stored procedure and
        // register it in the system catalogs
        String parameterList =
          "(in    table         varchar(20)," +
          "  in    modifier      int," +
          "  inout department    varchar(10)," +
          "  out   payrollBefore double," +
          "  out   payrollAfter  double," +
          "  out   avgSalBefore  double," +
          "  out   avgSalAfter   double," +
          "  out   numOfUpdates  int," +
          "  out   medianEmpName varchar(40))";

        System.out.println ("\n  Registering Java stored procedure " + name +
                            "\n     as " + classMethod +
                            "\n     in " + mode + " mode");

        stmt.executeUpdate ("CREATE PROCEDURE " + name + parameterList +
                            " LANGUAGE JAVA " +
                            " PARAMETER STYLE DB2GENERAL " + mode +
                            " EXTERNAL NAME '" + classMethod + "'");
        stmt.close ();
      }
    catch (SQLException e)
      {
        System.out.println ("\n  Error received registering stored procedure");
        throw e;
      }
  }


  // (2) call the requested stored procedure and display results
  public static void callStoredProc (Connection con,
                                     String tableName,
                                     String name,
                                     int percentModification,
                                     String department) throws Exception
  {
    // prepare the CALL statement
    CallableStatement stmt;
    String sql = "Call " + name + "(?,?,?,?,?,?,?,?,?) ";
    stmt = con.prepareCall (sql);

    // register the output parameters
    stmt.registerOutParameter (4, Types.DOUBLE);
    stmt.registerOutParameter (5, Types.DOUBLE);
    stmt.registerOutParameter (6, Types.DOUBLE);
    stmt.registerOutParameter (7, Types.DOUBLE);
    stmt.registerOutParameter (8, Types.INTEGER);
    stmt.registerOutParameter (9, Types.CHAR);

    // set all parameters (input and output)
    double totalPayrollBefore = 0.00;
    double totalPayrollAfter = 0.00;
    double averageSalaryBefore = 0.00;
    double averageSalaryAfter = 0.00;
    String medianEmployeeName = "This field is not defined yet";
    int numberOfUpdates = 0;
    stmt.setString (1, tableName);
    stmt.setInt (2, percentModification);
    stmt.setString (3, department);
    stmt.setDouble (4, totalPayrollBefore);
    stmt.setDouble (5, totalPayrollAfter);
    stmt.setDouble (6, averageSalaryBefore);
    stmt.setDouble (7, averageSalaryAfter);
    stmt.setInt (8, numberOfUpdates);
    stmt.setString (9, medianEmployeeName);

    // call the stored procedure
    System.out.println ("\n  Calling stored procedure: " + name);
    stmt.execute ();
    System.out.println ("\n  Returned from stored procedure: " + name);

    // retrieve output parameters
    BigDecimal totPayBefore = new BigDecimal (stmt.getDouble (4));
    BigDecimal totPayAfter = new BigDecimal (stmt.getDouble (5));
    BigDecimal avgSalBefore = new BigDecimal (stmt.getDouble (6));
    BigDecimal avgSalAfter = new BigDecimal (stmt.getDouble (7));
    numberOfUpdates = stmt.getInt (8);
    medianEmployeeName = stmt.getString (9);

    // display the information returned from the stored procedure
    System.out.println ();
    System.out.println ("              Percent modification:   " +
                        percentModification + "%");
    System.out.println ("         Department being modified:   " +
                        department);
    System.out.println ();
    System.out.println ("              Total payroll before: $ " +
                        totPayBefore.setScale (2, totPayBefore.ROUND_HALF_UP));
    System.out.println ("             Average salary before: $ " +
                        avgSalBefore.setScale (2, avgSalBefore.ROUND_HALF_UP));
    System.out.println ();
    System.out.println ("    Number of salary modifications:   " +
                        numberOfUpdates);
    System.out.println ();
    System.out.println ("               Total payroll after: $ " +
                        totPayAfter.setScale (2, totPayAfter.ROUND_HALF_UP));
    System.out.println ("              Average salary after: $ " +
                        avgSalAfter.setScale (2, avgSalAfter.ROUND_HALF_UP));
    System.out.println ();
    System.out.println ("              Median Employee Name:   " +
                        medianEmployeeName);

    stmt.close ();
  }


  // (3) main application: .connect to the database
  //                       .register the stored procedure
  //                       .call the stored procedure
  public static void main (String argv[])
  {
    Connection con = null;
    try
      {
        String url = "jdbc:db2:sample";
        String callName = "SAMPLESTOREDPROC";
        String storedProcName = "DB2StpSample!salaryModification";
        String mode = "not fenced";
        String table = "EMPLOYEE";
        String department = "C01";
        int percentModification = 5;

        // connect with default id/password
        con = DriverManager.getConnection (url);

        // register the stored procedure
        registerStoredProc (con, callName, storedProcName, mode);

        // call the stored procedure
        callStoredProc (con, table, callName, percentModification, department);

        con.close ();
      }
    catch (Exception e)
      {
        try { con.close(); } catch (Exception x) { }
        e.printStackTrace ();
      }
  }
}


///////
// Java stored procedure is in this class
///////
class DB2StpSample extends StoredProc
{
  // (1) stored procedure body - modify employee salaries as requested
  //     return:
  //      . the total payroll before the increase
  //      . the average salary before the increase
  //      . the median employee salary after the increase
  //      . the total payroll after the increase
  //      . the average salary after the increase
  public void salaryModification (String table,
                                  int percentModification,
                                  String department,
                                  double totalPayrollBefore,
                                  double totalPayrollAfter,
                                  double averageSalaryBefore,
                                  double averageSalaryAfter,
                                  int numberOfUpdates,
                                  String medianEmployeeName) throws Exception
  {
    int counter = 0;
    double salary = 0.00;
    double payrollBefore = 0;

    // get caller's connection to the database; inherited from StoredProc
    Connection con = getConnection ();

    // calculate the total payroll and average salary before the increase
    Statement stmt = con.createStatement ();
    String sql = "Select * from " + table;
    ResultSet rs = stmt.executeQuery (sql);

    while (rs.next ())
      {
        salary = rs.getDouble (12);
        payrollBefore += salary;
        counter++;
      }
    rs.close();

    int median = counter / 2;
    double averageBefore = payrollBefore / counter;
    double realPercent = percentModification;
    realPercent = 1.00 + (realPercent / 100.00);

    // update the salaries for the requested department
    int employeeUpdates = 0;
    sql = "Update " + table + " set salary = salary * " + realPercent +
      " where workdept = '" + department + "'";
    employeeUpdates = stmt.executeUpdate (sql);

    // Calculate the total payroll and average salary after the increase
    // and retrieve the median salary as well...
    sql = "Select * from " + table + " order by salary ";
    counter = 0;
    double payrollAfter = 0.00;

    rs = stmt.executeQuery (sql);
    while (rs.next ())
      {
        salary = rs.getDouble (12);
        payrollAfter += salary;
        counter++;
        if (counter == median)
          {
            medianEmployeeName =
              rs.getString (2) + " " +
              rs.getString (3) + " " +
              rs.getString (4);
          }
      }
    rs.close ();

    double averageAfter = payrollAfter / counter;

    // set values for the output parameters
    set (4, payrollBefore);
    set (5, payrollAfter);
    set (6, averageBefore);
    set (7, averageAfter);
    set (8, employeeUpdates);
    set (9, medianEmployeeName);

    // close off everything before we leave
    stmt.close ();
    con.close ();
  }
}
