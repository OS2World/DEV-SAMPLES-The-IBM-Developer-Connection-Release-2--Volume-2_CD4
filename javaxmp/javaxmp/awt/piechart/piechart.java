//-----------------------------------------------------------------------------
// piechart.java
//
// Simple example of drawing a pie chart using the AWT graphics class.
// 
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes:
//
//   Graphics
//
// Author : Kelvin R Lawrence.     13-Oct-1996.
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: greyscale
//
// Simple class that experiments with Java AWT capabilities.
//
//---------------------------------------------------------------

public class piechart extends Applet
{

  Button plus    ;
  Button minus   ;

  int cSlices = 3 ;

  //----------------------------------------------------------
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {
    setLayout(null) ;

    System.out.println( "init() method called" ) ;

    plus   = new Button("+") ;
    minus  = new Button("-")  ;

    add(plus) ;
    add(minus)  ;

    plus.reshape( 0,0,20,20 ) ;
    minus.reshape( 40,0,20,20 ) ;
  }

  //----------------------------------------------------------------
  // paint()
  //
  // Paint method, called whenever the application client area needs
  // repainting.
  //----------------------------------------------------------------
  public void paint( Graphics g )
  {
    System.out.println("Slice count = " + cSlices ) ;

    DrawPieChart( cSlices, g ) ;
  }

  //----------------------------------------------------------------
  // DrawPieChart()
  //
  // Draw a pie chart with a specified number of slices and scale
  // it to fit into the client window. A side effect of the scaling
  // is that if the window is not roughly square the pie may well
  // apear elliptical !
  //
  //----------------------------------------------------------------
  public void DrawPieChart( int numSlices, Graphics g )
  {
    int start =  0 ;
    int sweep ;
    int x, y, cx, cy ;

    Dimension dim ;

    dim = size() ;

    x  = 10 ;
    y  = 10 ; 
    cx = dim.width  - 20 ;
    cy = dim.height - 20 ;

    System.out.println( "Current width  = " + dim.width ) ;
    System.out.println( "Current height = " + dim.height ) ;
    System.out.println( "(x,y) = " + x + "  " + y ) ;


    if ( numSlices > 8 ) 
    {
      numSlices = 8 ;
    }
    else if ( numSlices < 1 ) 
    {
      numSlices = 1 ;
    }

    sweep = 360/numSlices ; 


    for( int i = 0; i < numSlices; i++ )
    {
      switch( i )
      {
        case 0: g.setColor( g.getColor().blue )    ; break ;
        case 1: g.setColor( g.getColor().red )     ; break ;
        case 2: g.setColor( g.getColor().green )   ; break ;
        case 3: g.setColor( g.getColor().yellow )  ; break ;
        case 4: g.setColor( g.getColor().gray )    ; break ;
        case 5: g.setColor( g.getColor().orange )  ; break ;
        case 6: g.setColor( g.getColor().magenta ) ; break ;
        case 7: g.setColor( g.getColor().cyan )    ; break ;
      }

      g.fillArc( x, y, cx, cy, -start, -sweep ) ;

      g.setColor( g.getColor().black ) ;

      g.drawRect(x, y, cx, cy) ;

      start += sweep ;

      System.out.println( g.toString() ) ;

    }
  }

  //-----------------------------------------------
  // action()
  //
  // Simple event handler. Note that returning true
  // means the event was handled by us.
  //-----------------------------------------------
  public boolean action( Event evt, Object what )
  {
    if ( evt.target == plus )
    {
      cSlices = cSlices + 1 ;
      repaint() ;
      return true ;
    }
    else if ( evt.target == minus) 
    {
      cSlices = cSlices - 1 ;
      repaint() ;
      return true ;
    }
    else
    {
      return false ;
    }
  }

}



