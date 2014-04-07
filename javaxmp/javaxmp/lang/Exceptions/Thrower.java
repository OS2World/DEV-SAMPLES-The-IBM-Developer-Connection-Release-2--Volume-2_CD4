//-----------------------------------------------------------------------------
// Thrower.java
//
// A simple example of throwing and catching exceptions.
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
// Class: Thrower
//
// Simple class that shows how to throw exceptions.
//
//-------------------------------------------------------------------------

public class Thrower
{
  //-------------------------------------------------------------------------
  // runTests()
  //
  // Simple try/catch block where the exception is caught within the same
  // method. Note that the finally block will always get called regardless
  // of whether or not an exception occurs.
  //-------------------------------------------------------------------------
  public void runTests( int value )
  {
    try
    {
      if ( value > 99 )
      {
        throw new Exception( "Number greater than 99" ) ;
      }
      else if ( value < 0 )
      {
        throw new Exception( "Number must be positive" ) ;
      }
    }
    catch (Exception e )
    {
      System.out.println( "Caught exception " + e.toString() ) ;
    }
    finally
    {
      System.out.println( "Finally..." ) ;
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
    Thrower it = new Thrower() ;
    int number ;

    try 
    {
      if ( args.length == 0 )
      {
        throw new NumberFormatException( "First argument must be a number" ) ;
      }
      number = Integer.valueOf(args[0]).intValue() ;

      it.runTests( number ) ;
    }
    catch ( NumberFormatException nfe )
    {
      System.out.println( "Caught exception " + nfe.toString() ) ;
      System.out.println( "Usage: Java Thrower <integer>" ) ;
    }
  }
}


