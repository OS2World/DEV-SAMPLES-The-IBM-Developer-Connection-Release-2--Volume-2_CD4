//-----------------------------------------------------------------------------
// SinCos.java
//
// A simple example of using the Sine and cosine functions of the Math class.
//
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   Math
//
// Author : Kelvin R Lawrence.     20th-January-1998
//
// History:
//
//-----------------------------------------------------------------------------
public class SinCos
{

  public static void main( String [] args )
  {
    for( int angle = 0; angle <= 360; angle += 5 )
    {
      //------------------------------------------------------------------------------------
      // The trig functions require the angle to be specified in radians so first we have to
      // convert the degrees to radians and then we can call the trig functions.
      //------------------------------------------------------------------------------------

      double rads = angle * Math.PI/180 ;

      //--------------------------------------------------------------------
      // Now call the trig functions and display the sine and cosine values.
      //--------------------------------------------------------------------

      double sine   = Math.sin(rads) ;
      double cosine = Math.cos(rads) ;

      System.out.println( "Angle= " + angle + " Sin= " + sine + " Cos= " + cosine );
    }

    //------------------------------------------------------------------------------
    // JAVA BUG ?
    // Test accuracy of Java sine and cosine. The sine of 90 degrees should be
    // 1 and the cosine should be 0. However, that is not quite what is happening.
    // I have to question why the Java Math API requires all angles to be specified
    // in radians rather than degrees but the conversion below should be yielding
    // sufficient accuracy to get the expected results. In reality, what this shows
    // is a loss of accuracy from having to convert angles from dgrees to radians
    // before calling the trig functions. It would be nicer if Java had trig
    // functions that took angles in whole degrees.
    //------------------------------------------------------------------------------

    //double PI = 3.14159265359 ;
    //double t = PI/180 ;
    double t = Math.PI/180 ;
    double r = t * 90 ;

    System.out.println( t ) ;
    System.out.println( r ) ;
    System.out.println( Math.cos(r) ) ;
    System.out.println( Math.sin(r) ) ;

    System.out.println( Math.cos(Math.PI/2) ) ;
    System.out.println( Math.cos( 90 * 2 * Math.PI / 360 )) ;
  }
}
