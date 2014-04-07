//-----------------------------------------------------------------------------
// Thrower2.java
//
// A simple example of declaring a method as throwing an exception.
// 
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//  -none-
//
// Author : Kelvin R Lawrence.     1st-December-1997
//
// History:
//
//-----------------------------------------------------------------------------

//-------------------------------------------------------------------------
// Class: Thrower2
//
// Simple class that shows how to throw exceptions.
//
//-------------------------------------------------------------------------

public class Thrower2
{
  //-------------------------------------------------------------------------
  // runTests()
  //
  // Note that because runTests2() declares that it can throw an Exception
  // we have to either catch it here or we could declare that we also
  // throw the exception by adding a throws clause to this method. The
  // Java compiler will enforce this.
  //-------------------------------------------------------------------------
  public void runTests()
  {
    try
    {
      runTests2(  3 ) ;
      runTests2( -1 ) ;
    }
    catch( Exception e )
    {
      System.out.println( "Caught excepton " + e.toString() ) ;
    }
  }

  //-------------------------------------------------------------------------
  // runTests2()
  //
  //-------------------------------------------------------------------------
  public void runTests2( int value ) throws Exception
  {
    if ( value < 0 )
    {
      throw new Exception( "Number must be positive" ) ;
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
    Thrower2 it = new Thrower2() ;

    it.runTests() ;
  }
}


