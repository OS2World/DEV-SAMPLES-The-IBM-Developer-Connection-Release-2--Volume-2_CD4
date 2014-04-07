//---------------------------------------------------------------------------
//
//  Name     : circles.java
//
//  Purpose  : Simple program that shows how circles are constructed
//             using the Java AWT.
//             
//
//
//             Author: Kelvin R. Lawrence
//             25th-February-1997
//
//---------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;
import java.util.* ;

//---------------------------------------------------------------
// Class: circles
//
// Simple class that experiments with Java AWT capabilities.
//
//---------------------------------------------------------------

public class circles extends Applet
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
  // paint()
  //
  // Paint method, called whenever the application client area needs
  // repainting.
  //----------------------------------------------------------------
  public void paint( Graphics g )
  {
    
    System.out.println("Paint generated");
    Dimension d ;

    d = this.size() ;
    System.out.println("Applet (x,y) = " + d.width + "," + d.height ) ;

    //-----------------------------------------------------------------------
    // For interest only in this case:
    //
    // Query the current insets and display them. In general, it seems from
    // the Java ports I have seen that you cannot guarantee where the origin
    // point (0,0) is. Some versions have it top left of frame others have it
    // top left below the title bar. In theory, by adding the insets to the
    // origin you will always get the first useful pixel in the "client"
    // window of a frame.
    //-----------------------------------------------------------------------

    this.insets().toString() ;
    System.out.println( "Top inset    = " + this.insets().top ) ;
    System.out.println( "Bottom inset = " + this.insets().bottom ) ;
    System.out.println( "Left inset   = " + this.insets().left ) ;
    System.out.println( "Right inset  = " + this.insets().right ) ;


    GenerateCircle( g, 10,10,100,100 ) ;
    GenerateCircle( g, 110,10,150,100 ) ;
    GenerateCircle( g, 260,10,100,200 ) ;
    GenerateCircle( g, 10,110,250,100 ) ;
    //GenerateCircle( g, 10,210,350,100 ) ;
    GenerateCircle( g, 360,10,100,400 ) ;
    GenerateCircle( g, 10,210,350,200 ) ;
  }

  //----------------------------------------------------------------
  // GenerateCircle()
  //
  //----------------------------------------------------------------
  public void GenerateCircle( Graphics g, int x, int y, int cx, int cy )
  { 
     g.setColor( Color.blue ) ;

     g.drawOval( x,y,cx,cy ) ;
     //g.fillOval( x,y,cx,cy ) ;

     g.setColor( Color.red ) ;

     g.drawLine( x+cx/2,y+cy/2,x+cx/2,y ) ;
     g.drawLine( x+cx/2,y+cy/2,x,y+cy/2 ) ;

     g.setColor( Color.black ) ;

     g.drawRect(  x,y,cx,cy ) ;
  }
}

