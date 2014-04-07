//---------------------------------------------------------------------------
//
//  Name     : towers.java
//
//  Purpose  : Simple program that shows how easily a basic 3D bar
//             (or tower) chart can be constructed.
//
//
//             Copyright (C) Kelvin R. Lawrence
//             29th-January-1997
//
//---------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;
import java.util.* ;

//---------------------------------------------------------------
// Class: towers
//
// Simple class that experiments with Java AWT capabilities.
//
//---------------------------------------------------------------

public class towers extends Applet
{

  //----------------------------------------------------------
  // init()
  //
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {
    setLayout(null) ;

    System.out.println( "init() method called" ) ;
  }


  //----------------------------------------------------------
  // stop()
  //
  //----------------------------------------------------------
  public void stop()
  {
    System.out.println( "stop() method called" ) ;
  }

  //----------------------------------------------------------
  // start()
  //
  //----------------------------------------------------------
  public void start()
  {
    System.out.println( "start() method called" ) ;
  }

  //----------------------------------------------------------
  // destroy()
  // 
  //----------------------------------------------------------
  public void destroy()
  {
    System.out.println( "destroy() method called" ) ;
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
    GenerateTowers( g ) ;
  }

  //----------------------------------------------------------------
  // GenerateTowers()
  //
  //----------------------------------------------------------------
  public void GenerateTowers( Graphics g )
  { 
    Random rnd = new Random() ;
    int x,y,cx,cy ;

    Dimension dim = size();

    g.clearRect( 0,0,dim.width,dim.height ) ;

    int maxHeight = dim.height - 80 ;

    x = 40  ;
    y = dim.height - 40 ;

    cx =  60 ;
    cy =  ( rnd.nextInt() & 0x0FFFFFFF ) % maxHeight ;
    
    for ( int i=0; i<4; i++ )
    {
      DrawTower( g,x,y,cx,cy ) ;

      x  += 100 ;
      cy = ( rnd.nextInt() & 0x0FFFFFFF ) % maxHeight ; 
    }
  }


  //----------------------------------------------------------------
  // DrawTower()
  //
  //----------------------------------------------------------------
  public void DrawTower( Graphics g, int x, int y, int cx, int cy )
  {

    int[] p1x, p1y, p2x, p2y, p3x, p3y ;

    int xDelta = cx/2 ; 
    int yDelta = cx/2 ;

    //------------------------------------------------
    // Calculate the coordinates of the front polygon.
    //------------------------------------------------
     
    p1x = new int[5] ;
    p1y = new int[5] ;

    p1x[0] =  x ; 
    p1y[0] =  y ;
    p1x[1] =  x + cx ; 
    p1y[1] =  y ;
    p1x[2] =  p1x[1] ; 
    p1y[2] =  p1y[1] - cy ;
    p1x[3] =  p1x[0] ; 
    p1y[3] =  p1y[2] ;   
    p1x[4] =  x ;
    p1y[4] =  y ;

    g.setColor( Color.red ) ;

    g.fillPolygon( p1x, p1y, 5 ) ;

    g.setColor( Color.black ) ;
    g.drawPolygon( p1x, p1y, 5 ) ;

    //-----------------------------------------------
    // Calculate the coordinates of the side polygon.
    //-----------------------------------------------

    p2x = new int[5] ;
    p2y = new int[5] ;

    p2x[0] = p1x[1]  ; 
    p2y[0] = p1y[1]  ;
    p2x[1] = p1x[1] + xDelta  ; 
    p2y[1] = p1y[1] - yDelta  ;
    p2x[2] = p1x[2] + xDelta  ; 
    p2y[2] = p1y[2] - yDelta  ;
    p2x[3] = p1x[2]  ; 
    p2y[3] = p1y[2]  ;
    p2x[4] = p2x[0]  ;
    p2y[4] = p2y[0]  ;

    g.setColor( Color.red ) ;
    g.fillPolygon( p2x, p2y, 5 ) ;

    g.setColor( Color.black ) ;
    g.drawPolygon( p2x, p2y, 5 ) ;

    //----------------------------------------------
    // Calculate the coordinates of the top polygon.
    //----------------------------------------------

    p3x = new int[5] ;
    p3y = new int[5] ;

    p3x[0] = p1x[3] ; 
    p3y[0] = p1y[3] ;
    p3x[1] = p1x[2] ; 
    p3y[1] = p1y[2] ;
    p3x[2] = p2x[2] ; 
    p3y[2] = p2y[2] ;
    p3x[3] = p1x[3] + xDelta ; 
    p3y[3] = p1y[3] - yDelta ;
    p3x[4] = p3x[0]  ;
    p3y[4] = p3y[0]  ;

    g.setColor( Color.yellow ) ;
    g.fillPolygon( p3x, p3y, 5 ) ;

    g.setColor( Color.black ) ;
    g.drawPolygon( p3x, p3y, 5 ) ;
  }
}





