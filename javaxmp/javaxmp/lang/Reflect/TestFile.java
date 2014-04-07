//-----------------------------------------------------------------------------
// TestFile.java
//
// A (dummy) test class that can be used as an input file (testcase)
// for the Loader.java and View.java  examples.
//
// NOTE: This file will compile and can be run stand alone but that is not
//       its intended purpose.
//
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
// Author : Kelvin R Lawrence.     21st-November-1997
//
// History:
//
//-----------------------------------------------------------------------------


//---------------------------------------------------------------
// Class: TestFile
//
// Test class with a few simple methods for testing Loader.java
//
//---------------------------------------------------------------
public class TestFile implements TestInterface
{

  public int publicInt1 ;

  private int privateInt1 ;

  //--------------------------------
  // Constructor with no parameters.
  //--------------------------------
  public TestFile()
  {
    System.out.println( "Constructor" ) ;
  }

  //------------------------------
  // Constructor with a parameter.
  //------------------------------
  public TestFile( String s )
  {
    System.out.println( "Constructor passed ==> " + s ) ;
  }

  //----------------------------------------------
  // Method with no return type and no parameters.
  //----------------------------------------------
  public void method1()
  {
    System.out.println( "One" ) ;
  }

  //-------------------------------------------
  // Method with a return type and a parameter.
  //-------------------------------------------
  public int  method2( int x )
  {
    System.out.println( "Two" ) ;

    return x + 1 ;
  }

  //--------------------------------------------------
  // Method with a return type and an array parameter.
  //--------------------------------------------------
  public int  method3(int [] num )
  {
    int tot = 0 ;

    System.out.println( "Three" ) ;

    for ( int i=0; i<num.length; i++)
    {
      tot = tot + num[i] ;
    }
    return tot ;
  }

  //----------------------------------------------------
  // Method that returns an array and has no parameters.
  //----------------------------------------------------
  public int[] method4()
  {
    int [] array = new int[4] ;
    array[0] = 1 ;
    array[1] = 2 ;
    array[2] = 3 ;
    array[3] = 4 ;

    return array ;
  }

  public void go()
  {
    int [] num = new int[4] ;
    int sum ;
    int plus1;

    method1() ;

    plus1 = method2(3) ;

    num[0] = 1 ;
    num[1] = 2 ;
    num[2] = 3 ;
    num[3] = 4 ;

    sum = method3( num ) ;

  }

  static public void main( String args[] )
  {
    System.out.println( "Executing main() inside TestFile." ) ;
    TestFile tf = new TestFile() ;
    tf.go() ;
  }

  //--------------
  // Nested class.
  //--------------
  public class dummy
  {
    public int a ;
    public int b ;

    public dummy()
    {
      a = 1 ;
      b = 2 ;
    }
  }
}

