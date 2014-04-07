//-----------------------------------------------------------------------------
// IntTest.java
//
// A simple example of a few simple uses of the static methods provided
// as part of the Integer class.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//  Integer
//
// Author : Kelvin R Lawrence.     21st-November-1997
//
// History:
//
//-----------------------------------------------------------------------------

//---------------------------------------------------------------
// Class: IntTest
//
// Simple class that 
//
//---------------------------------------------------------------

public class IntTest
{
  //-------------------------------------------------------------------------
  // showRadixTable()
  //
  //-------------------------------------------------------------------------
  public void showRadixTable() 
  {
    String bin ;
    String oct ;
    String dec ;
    String hex ;

    System.out.println( "Example of using Integer.toString( number,radix)" ) ;
    System.out.println( "================================================" ) ;
    System.out.println( " " ) ;

    for ( int i=0; i<=100; i++)
    {
      bin = Integer.toString( i,  2 ) ;
      oct = Integer.toString( i,  8 ) ;
      dec = Integer.toString( i, 10 ) ;
      hex = Integer.toString( i, 16 ) ;

      System.out.println( i+": bin="+bin+" oct="+oct+" dec="+dec+" hex="+hex ) ;
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
    IntTest it = new IntTest() ;

    it.showRadixTable() ;
  }
}

