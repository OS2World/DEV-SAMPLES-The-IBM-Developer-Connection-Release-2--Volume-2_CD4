//-----------------------------------------------------------------------------
// gfxpane.java
//
// A simple example of using scrollable panels (scroll panes) in a
// Java application. This sample shows how to display an image in a
// scroll pane. Scroll panes are new in Java 1.1.0. In earlier releases
// of Java to do this would have required doing all of the scrolling
// using user provided code.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   Adjustable
//   Image
//   ScrollPane
//
//
// Author : Kelvin R Lawrence.     26th-May-1997
//
// History:
//    26-Mar-1998 -DOB-  Updated path to images
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.awt.image.* ;

//---------------------------------------------------------------
// Class: gfxpane
//
// Simple class that experiments with the AWT ScrollPane class.
//
//---------------------------------------------------------------

public class gfxpane extends Frame
{
  static final int VERTICAL_SCROLL_UNIT =   4 ;
  static final int HORIZONTAL_SCROLL_UNIT = 4 ;
  private ScrollPane scp ;
  private ImageCanvas canvas ;

  private Adjustable vert ;
  private Adjustable horz ;

  //----------------------------------------------------------
  // Constructor
  //
  // Setup the scroll pane at frame creation time. The actual
  // drawing is done by the ImageCanvas class.
  //
  //----------------------------------------------------------
  public gfxpane( String s )
  {
    super(s);

    //---------------------------------------------------------
    // Specify the layout for the frame window and create a new
    // ScrollPane with scrollbars always visible.
    //---------------------------------------------------------
    setLayout( new BorderLayout() ) ;

    scp = new  ScrollPane( ScrollPane.SCROLLBARS_ALWAYS ) ;

    //-----------------------------------------------------------
    // Get the adjustable object for the vertical and horizontal
    // scrollbars of the scroll pane and set the unit increments
    // that we require.
    //-----------------------------------------------------------
    horz = scp.getHAdjustable() ;
    vert = scp.getVAdjustable() ;

    System.out.println("Initial horiz block increment ==> " + horz.getBlockIncrement() ) ;
    System.out.println("Initial vert block increment ===> " + horz.getBlockIncrement() ) ;
    System.out.println("Initial Unit increment  ========> " + horz.getUnitIncrement() ) ;

    //-------------------------------------------------------
    // Set the vertical and horizontal unit increments to our
    // preferred values. We could also set the block
    // increments but we'll just accept the defaults.
    //-------------------------------------------------------
    horz.setUnitIncrement(HORIZONTAL_SCROLL_UNIT) ;
    vert.setUnitIncrement(VERTICAL_SCROLL_UNIT) ;

    //-------------------------------------------------------
    // Create the image canvas and add it to the scroll pane.
    // Then add the scroll pane to the frame.
    //-------------------------------------------------------
    canvas = new ImageCanvas() ;

    scp.add(canvas) ;
    this.add("Center", scp) ;
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
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
    gfxpane gfxpaneFrame ;

    gfxpaneFrame = new gfxpane( "Scroll Pane test" ) ;

    gfxpaneFrame.resize( 300,300 ) ;
    gfxpaneFrame.show() ;
  }
}

//---------------------------------------------------------------
// Class: ImageCanvas
//
// Simple class that extends canvas so that we can catch paint
// notifications as the canvas is scrolled and redraw the
// appropriate part of the image. Note that as the canvas is
// scrolled the clipping rectangle will be already setup so
// that we only paint to the area that has been invalidated by
// the scroll.
//
//---------------------------------------------------------------
class ImageCanvas extends Canvas implements ImageObserver
{
  boolean initDone = false ;

  Image i = getToolkit().getImage( "../../images/737-500.gif" ) ;

  //----------------------------------------------------------------
  // paint()
  //
  // Paint the canvas by redrawing the image as required.
  //----------------------------------------------------------------
  public void paint( Graphics g )
  {
    int h = i.getHeight(this) ;
    int w = i.getWidth(this) ;

         System.out.println( "paint() called" ) ;

    System.out.println( "Image height ==> " + h ) ;
    System.out.println( "Image width  ==> " + w ) ;

    if ( w != -1 && h != -1)
    {
      if (!initDone)
      {
        System.out.println("Init being done");
        initDone = true ;
        setSize( w,h ) ;
        getParent().doLayout() ;
        repaint() ;
      }

      Dimension d = this.getSize() ;
      Rectangle rect = g.getClipRect() ;
      System.out.println("Paint canvas ==> " + d.width + " " + d.height + " " + rect ) ;

      g.drawImage( i, 0, 0, this ) ;
    }

  }

  //----------------------------------------------------------------
  // imageUpdate()
  //
  // Will get called as the image is loaded because we have chosen
  // to implement the ImageObserver interface and because we did
  // not pass a null as the last parameter to drawImage. Note that
  // returning true says continue to call me while loading and
  // returning false means no need to call me anymore for this
  // image. Becuase the Component class provides an imageUpdate
  // method we actually don't have to override it but we do in this
  // case as a means of an example.
  //----------------------------------------------------------------
  public boolean imageUpdate( Image img, int status, int x, int y, int width, int height )
  {
    //--------------------------------------------------------------
    // If we are still loading the image then just continue loading.
    // When we are done loading force a repaint.
    //--------------------------------------------------------------
    if ( (status & ALLBITS ) == 0 )
    {
      return true ;
    }
    else
    {
      System.out.println( "Done loading image..status = " + status ) ;
      repaint() ;

      return false ;
    }
  }
}
