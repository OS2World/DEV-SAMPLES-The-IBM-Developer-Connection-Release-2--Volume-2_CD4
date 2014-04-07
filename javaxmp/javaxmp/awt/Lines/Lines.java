//-----------------------------------------------------------------------------
// Lines.java
//
// 
//
// 
// 
// 
//
// 
// 
// 
//
// Author : Kelvin R Lawrence.     5th-March-1997
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;
import java.util.*;

//-----------------------------------------------------------------
// Class: GraphicsThread
//
// A worker thread class that implements the actual drawing code
// that will be done. If the thread yield mode is enabled then
// the thread will pause after it has drawn 10 graphics objects.
// If the thread yield feature is not enabled then the thread
// will just keep drawing. This allows us to compare the
// behaviour of graphics scheduling on different operating systems.
// 
//-----------------------------------------------------------------
class DrawingThread extends Thread 
{
  Graphics g ;

  //--------------------------------------------------------------------
  //
  // Constructor: DrawingThread()
  //
  //--------------------------------------------------------------------
  DrawingThread( Applet ap ) 
  {
    Random rnd = new Random() ;
    Dimension d = ap.size() ;

    int x1,x2,y1,y2 ;

    g = ap.getGraphics() ;

    x1 =  rnd.nextInt() % d.width ;
    y1 =  rnd.nextInt() % d.height ;

    for ( int i = 0; i < 5000; i++ )
    {
      x2 = rnd.nextInt() % d.width ; 
      y2 = rnd.nextInt() % d.height ;

      g.drawLine( x1,y1,x2,y2 ) ;

      x1 = x2 ;
      y1 = y2 ;

    }
  }


  //--------------------------------------------------------------------
  // run()
  //
  // This method is automatically called when a new GraphicsThread object  
  // is created as it extends the Thread class.
  //--------------------------------------------------------------------
  public void run()
  {
  }
}

//---------------------------------------------------------------
// Class: Lines
//
// Main class. Creates the display panels and starts the graphics
// drawing threads.
// 
//---------------------------------------------------------------
public class Lines extends Applet
{

  int paintCount = 0 ;
  // Canvas canvas ;

  //----------------------------------------------------------
  // init()
  //
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {
    System.out.println( "init() method called" ) ;

    Dimension appSize = this.size() ;

    System.out.println( "Applet window width  = " + appSize.width ) ;
    System.out.println( "Applet window height = " + appSize.height ) ;

    //---------------------------------------------------------------
    // Specify which layout manager each of the key panels is to use.
    //---------------------------------------------------------------

    //canvas = new Canvas() ;

    //this.setLayout( new BorderLayout() ) ;

    //add( "Center", canvas ) ;

    this.show() ;
  }

  //----------------------------------------------------------------
  // paint()
  //
  // Paint method, called whenever the application client area needs
  // repainting.
  //----------------------------------------------------------------
  public void paint( Graphics g )
  {

    paintCount++ ;

    DrawingThread dt ;

    System.out.println( "Paint called ("+paintCount+")" ) ;

    dt = new DrawingThread( this ) ;

    dt.start() ;
  }

  //----------------------------------------------------------------
  // action()
  //
  // Method, called whenever the user clicks on a button etc.
  // In partcular, this is where we control the starting and 
  // stopping of the graphics threads.
  // 
  //----------------------------------------------------------------
  public boolean action( Event evt, Object what )
  {
    System.out.println( "Action method called" );

    return true ;
  }
}
