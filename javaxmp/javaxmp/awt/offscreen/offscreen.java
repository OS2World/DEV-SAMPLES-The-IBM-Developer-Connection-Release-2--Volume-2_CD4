//-----------------------------------------------------------------------------
// offscreen.java
//
// A simple example of drawing to an offscreen memory image (bitmap) and then
// transferring that image to the screen. 
// 
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes:
//
//   Graphics
//   Image
//   Insets
//   Dimension
//
// Author : Kelvin R Lawrence.     5th-May-1997
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;

//---------------------------------------------------------------
// Class: offscreen
//
// Simple class that experiments with Java AWT image processing.
//
//---------------------------------------------------------------

public class offscreen extends Frame
{
  static final int IMAGE_X = 100 ;
  static final int IMAGE_Y = 100 ;

  boolean fImageCreated = false ;

  Image memoryImage ;
  Graphics offscreenGraphics ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public offscreen( String s )
  {
    super(s);

    setLayout( new FlowLayout() ) ;
  }

  //--------------------------------------------------------------------
  // action()
  //
  // Process user actions.
  //
  //--------------------------------------------------------------------
  public boolean action( Event evt, Object what )
  {
    if ( evt.id == Event.ACTION_EVENT )
    {
      System.out.println( "action() method got called" ) ;
    }

    return true ;
  }

  //-------------------------------------------------------------------------
  // handleEvent()
  //
  // Handle events. We need this so that the close in the title bar will
  // actually close the application etc.
  //-------------------------------------------------------------------------
  public boolean handleEvent( Event evt )
  {
    if ( evt.id == Event.WINDOW_DESTROY)
    {
      System.exit(0) ;
      return true ;
    }
    else
    {
      return( super.handleEvent( evt ) );
    }
  }
  
  //--------------------------------------------------------------------
  // paint()
  //
  //--------------------------------------------------------------------
  public void paint( Graphics g )
  {
    Dimension dim = size() ;
    Insets    ins = insets() ;

    System.out.println( "Width/Height==> " + dim.width + "/" + dim.height ) ;

    int rows = (dim.height+IMAGE_Y-1)  / IMAGE_Y  ;
    int cols = (dim.width+IMAGE_X-1)   / IMAGE_X ;

    System.out.println( "Cols/Rows=====> " + cols + "/" + rows ) ;

    //--------------------------------------------------------------------
    // If the memory bitmap has been created then display it tiled as many
    // times as to fill up the client window area.
    //--------------------------------------------------------------------
    if ( fImageCreated )
    {
      int x = ins.left ;
      int y = ins.top  ;

      for ( int row = 0; row < rows ; row++ )
      {
        for ( int col = 0; col < cols; col++ )
        {
           g.drawImage( memoryImage,x,y,this ) ;
           x += IMAGE_X ;
        }
        x = ins.left ;
        y += IMAGE_Y ;
      }
    }
  }
  //--------------------------------------------------------------------
  // runTests()
  //
  // Do some testing.
  //
  //--------------------------------------------------------------------
  public void runTests()
  {
    //----------------------------------------------------------
    // Create a memory bitmap and get a graphics context that we
    // can use for drawing into it.
    //----------------------------------------------------------
    memoryImage = createImage( IMAGE_X, IMAGE_Y ) ;

    if ( memoryImage == null )
    {
      System.out.println( "createImage failed." );
    }

    offscreenGraphics = memoryImage.getGraphics() ;

    //-------------------------------------------
    // Draw something into our new memory bitmap.
    //-------------------------------------------

    offscreenGraphics.setPaintMode();

    offscreenGraphics.setColor( Color.yellow ) ;

    offscreenGraphics.fillRect(0,0,IMAGE_X,IMAGE_Y ) ;

    offscreenGraphics.setColor( Color.blue ) ;

    offscreenGraphics.drawOval( 20,20,20,20 ) ;
    offscreenGraphics.drawOval( 60,20,20,20 ) ;
    offscreenGraphics.drawLine( 50,40,50,70 ) ;
    offscreenGraphics.drawLine( 50,70,45,70 ) ;
    offscreenGraphics.drawArc( 30,60,40,30,180,180 ) ;

    fImageCreated = true ;

    repaint() ;
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     offscreen offscreenFrame ;

     offscreenFrame = new offscreen( "Offscreen Image Tests" ) ;

     offscreenFrame.resize( 400,400 ) ;
     offscreenFrame.show() ;

     offscreenFrame.runTests() ;
  }
}





