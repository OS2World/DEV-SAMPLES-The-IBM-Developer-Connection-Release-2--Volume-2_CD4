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

import java.lang.*;
import java.sql.*;
import java.util.Stack;

/**
 * This file was modified from 
 * X:\ServletExpress\web\resources\en_US\TicketCentral sample.
 *
 * This class is a Data Base Connection Manager.  It pre-allocate
 * a pool of database connections.  The pool will grow to
 * maxmum of 32 connections.
 *
 */

public class ConnectionManager {

  private int max;
  private int min;
  private int cur;
  private Stack pool;

  // max DB2 connections is 64 (?); should leave some available
  // for other applications though
  public static final int maxConnections = 32;
  public static final int minConnections = 9;
  private String url = "jdbc:db2:appsguid" ;

  

  public ConnectionManager() throws SQLException
  {
    max = maxConnections;
    min = minConnections;
    createPool();
  }

  public ConnectionManager(int max) throws SQLException
  {
    this.max = max;
    min = (max > minConnections) ? minConnections : max;
    createPool();
  }

  private void createPool() throws SQLException
  {
    pool = new Stack();
    int i = 0;
    try
    {
        for (; i<min; i++)
        {
           Connection conn = null;
	   
	   // connect with ...
           conn = DriverManager.getConnection(url, "mnix", "password");
           System.out.println("Got a connection" + (i+1));

           if ( conn != null )
           {
              pool.push(conn);
           }
        } // end of for loop
    } // end of try
    catch (SQLException e)
    {
       if (i > 0)
       {
          System.out.println("TC WARNING: only "+i+" connections available");
          min = i;
       }
       else
       {
          throw e;
       }
    } // end of catch()

    cur = min;
  } // end of createPool()

  public synchronized Connection getConnection() throws SQLException
  {
    Connection conn = null;
    if (!pool.empty())
    {
       conn = (Connection) pool.pop();
    }
    else if (cur < max)
    {
       // connect with default id/password
       conn = DriverManager.getConnection(url, "mnix", "password" );

       cur++;
    }
    else
    {
       System.out.println("No connections available");
    }
    if (conn != null)
       System.out.println("Got a connection from connection pool.");
    return conn;
  } // end of getConnection()

  public synchronized void putConnection(Connection conn)
  {
    pool.push(conn);
    System.out.println("Put a connection back to connection pool.");
  } // end of putConnection()

  public synchronized void destroyPool() throws SQLException
  {
    System.out.println("Destroy the database connections pool.");
    while (!pool.empty())
    {
      Connection conn = (Connection) pool.pop();
      conn.close();
    }
  } // end of destroyPool()
} // end of class ConnectionManager()
