//-----------------------------------------------------------------------------
//
// ImgObserver.java
//
// Simple example of using the ImageObserver interface to load an image in the
// background and only display it when it is ready. This is done by implementing
// the imageUpdate method which will be called by the drawImage method as it
// loads and constructs the image on one of the AWT's background worker threads.
// By default, the drawImage method provided by the AppletViewer will draw the
// parts of the image as they become available.
//
// Author : Kelvin R Lawrence.     27-Feb-1997.
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.awt.image.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: ImgObserver
//
// Simple class that experiments with Java AWT capabilities.
//
//---------------------------------------------------------------

public class ImgObserver extends Applet implements ImageObserver
{
  Image myImage ;

  //----------------------------------------------------------
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {

    System.out.println( "Document base is " + getDocumentBase() ) ;
    System.out.println( "About to call getImage" ) ;

    myImage = getImage( getDocumentBase(), "737-500.gif" ) ;
  }

  //----------------------------------------------------------------
  // paint()
  //
  // Paint method, called whenever the application client area needs
  // repainting.
  //----------------------------------------------------------------
  public void paint( Graphics g )
  {
    System.out.println( "About to call draw image" ) ;
    g.drawImage( myImage, 0,0, this ) ;
  }

  //----------------------------------------------------------------
  // imageUpdate()
  //
  // Called by the drawImage worker threads to give us updates on
  // the status of our image as it is loaded and created. When the
  // status bit ALLBITS (32) is set, we know the image is ready.
  // When the image is ready we force a repaint to cause it to
  // be displayed.
  //----------------------------------------------------------------
  public boolean imageUpdate( Image img, int status, int x, int y, int width, int height )
  {
    if ( (status & ALLBITS ) == 0 )
    {
      System.out.println( "Still processing...status=" + status ) ;
      return true ;
    }
    else
    {
      System.out.println( "Done processing." ) ;
      repaint() ;
      return false ;
    }
  }
}
