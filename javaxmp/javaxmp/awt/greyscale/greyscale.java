//-----------------------------------------------------------------------------
//
// greyscale.java
//
// Simple Java test application that demonstrates how to draw a series of
// filled polygons, each one filled with a different grey scale.
//
// Author : Kelvin R Lawrence.     4th-Oct-1996.
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: greyscale
//
// Simple class that experiments with Java AWT menu capabilities.
//
//---------------------------------------------------------------

public class greyscale extends Applet
{
  //----------------------------------------------------------
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {
    setLayout(null) ;

    System.out.println( "init() method called" ) ;
  }

  //----------------------------------------------------------------
  // Paint method, called whenever the application client area needs
  // repainting.
  //----------------------------------------------------------------
  public void paint( Graphics g )
  {
    int[] aptsX ;
    int[] aptsY ;

    aptsX = new int[5] ;
    aptsY = new int[5] ;

    aptsX[0] =  10  ;  aptsY[0] =  10 ;
    aptsX[1] =  20  ;  aptsY[1] =  10 ;
    aptsX[2] =  20  ;  aptsY[2] = 100 ;
    aptsX[3] =  10  ;  aptsY[3] = 100 ;
    aptsX[4] =  10  ;  aptsY[4] =  10 ;

    for( int i = 0; i < 255; i += 8 )
    {
      g.setColor( new Color( i, i, i )) ;

      //g.drawPolygon( aptsX, aptsY, 5 ) ;
      g.fillPolygon( aptsX, aptsY, 5 ) ;

      aptsX[0] += 10 ;
      aptsX[1] += 10 ;
      aptsX[2] += 10 ;
      aptsX[3] += 10 ;
      aptsX[4] += 10 ;
    }
  }
}



