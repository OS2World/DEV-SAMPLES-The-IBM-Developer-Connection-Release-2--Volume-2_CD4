//-----------------------------------------------------------------------------
// Catcher.java
//
// A simple example of catching thrown exceptions.
// 
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes/interfaces:
//
// -none-
//
// See also:
//  Thrower.java
//  Thrower2.java
//
// Author : Kelvin R Lawrence.     1st-December-1997
//
// History:
//
//-----------------------------------------------------------------------------

//---------------------------------------------------------------
// Class: Catcher
//
// Simple class that shows how to use try/catch blocks to catch 
// exceptions. 
//
//---------------------------------------------------------------

public class Catcher
{

  //-------------------------------------------------------------------------
  // runTests()
  //
  //-------------------------------------------------------------------------
  public void runTests()
  {
    int d = 0 ;
    int b = 3 ;
    int c ;

    //-------------------------------------------------------------
    // Deliberately divide something by zero to force an arithmetic
    // exception to be thrown by Java.
    //-------------------------------------------------------------
    try 
    {
      c = b/d ;
    }
    catch ( Exception e )
    {
      System.out.println( "Caught exception " + e.toString() ) ;
    }
  }

  //-------------------------------------------------------------------------
  // main()
  //
  // Kick off a few simple tests. As most of these tests write their
  // output to the standard Java console you may want to redirect the
  // output to a file.
  //-------------------------------------------------------------------------
  static public void main( String[] args )
  {
    Catcher it = new Catcher() ;

    it.runTests() ;
  }
}


