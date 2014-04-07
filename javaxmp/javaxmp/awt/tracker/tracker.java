//-----------------------------------------------------------------------------
// tracker.java
//
// A simple example of using the Java image MediaTracker to load multiple
// images and then display them.
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes:
//
//   MediaTracker
//   Thread
//   Event
//
// Author : Kelvin R Lawrence.     5th-May-1997
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: tracker
//
// Simple class that experiments with Java AWT menu capabilities.
//
//---------------------------------------------------------------

public class tracker extends Frame implements Runnable
{

  static final int NUM_IMAGES = 16 ; // Number of images in our set.
  static final int IMAGE_SET  =  0 ; // The set we will assign the images to.
  static final int DELAY_TIME = 10 ; // Frame rate in milliseconds.

  Thread animator ;
  Image  [] images ;
  MediaTracker media ;
  int ctr = 0 ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public tracker( String s )
  {
    super(s);

    setLayout( new FlowLayout() ) ;

    media = new MediaTracker( this ) ;

    animator = new Thread( this ) ;
    animator.start() ;
  }

  //--------------------------------------------------------------------
  // run()
  //--------------------------------------------------------------------
  public void run()
  {
    String fn ; // Filename of image being loaded.

    System.out.println( "Animation/Image loader thread started" ) ;

    images = new Image[NUM_IMAGES] ;

    //------------------------------------------------------
    // Get the image and add it to the media tracker object.
    //------------------------------------------------------
    for ( int i=0; i < NUM_IMAGES; i++ )
    {
      fn = Integer.toString(800+1+i) + ".GIF" ;

      System.out.println( "Loading image ==> " + fn ) ;

      images[i] = getToolkit().getImage( fn ) ;

      media.addImage( images[i] , IMAGE_SET ) ;
    }

    //--------------------------------------------------------------------
    // Wait until all of the images have been loaded by the media tracker.
    //--------------------------------------------------------------------
    try
    {
      media.waitForID ( IMAGE_SET );
    }
    catch ( InterruptedException e )
    {
      System.out.println( "*** The image load was interrupted." ) ;
      return ;
    }

    //----------------------
    // Now do the animation.
    //----------------------
    while ( true )
    {
      try
      {
        Thread.sleep( 10 ) ;
      }
      catch ( InterruptedException e )
      {
        break ;
      }

      this.repaint() ;

      ctr = ( ctr < NUM_IMAGES-1 ) ? ctr + 1 : 0 ;
    }
  }

  //---------------------------------------------------------------------
  // update()
  //
  // Update gets called before paint. This gives us the chance to stop
  // the background from being repainted if we wish. The default update()
  // method that Java provides will repaint the window background which
  // can cause our images to flicker.
  //---------------------------------------------------------------------
  public void update (Graphics g)
   {
     paint (g) ;
   }

  //--------------------------------------------------------------------
  // paint()
  //
  //--------------------------------------------------------------------
  public void paint (Graphics g)
   {
      //---------------------------------------------------------------
      // Double check that the images loaded and are available. If they
      // are, which they should be, then go ahead and paint the current
      // image.
      //---------------------------------------------------------------

      Insets insets = this.insets() ;

      if (media.statusID( IMAGE_SET, false ) == MediaTracker.COMPLETE)
      {
        g.drawImage ( images[ctr]
                    , insets.left
                    , insets.top
                    , this );
      }
   }

  //--------------------------------------------------------------------
  // action()
  //
  // Process user actions.
  //
  //--------------------------------------------------------------------
  public boolean action( Event evt, Object what )
  {

    //-----------------------------------------------------------------
    // In this example, we look to see if one of the checkbox menus was
    // selected. If they were then we uncheck the other one. This shows
    // how to implement a set of mutually exclusive menu choices.
    //
    //-----------------------------------------------------------------
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
     tracker trkFrame ;

     trkFrame = new tracker( "MediaTracker test" ) ;

     trkFrame.resize( 300,300 ) ;
     trkFrame.show() ;
  }
}
