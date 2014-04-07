//-----------------------------------------------------------------------------
//
// showimage.java
//
// Simple example of how to load and display an .GIF image.
//
// Author : Kelvin R Lawrence.     13-Oct-1996.
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: showimage
//
// Simple class that experiments with Java AWT capabilities.
//
//---------------------------------------------------------------

public class showimage extends Applet
{
  Image myImage ;

  //----------------------------------------------------------
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {

    System.out.println( "Loading image..." ) ;
    System.out.println( "Document base is " + getDocumentBase() ) ;

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
   g.drawImage( myImage, 0,0, this ) ;
  }
}
