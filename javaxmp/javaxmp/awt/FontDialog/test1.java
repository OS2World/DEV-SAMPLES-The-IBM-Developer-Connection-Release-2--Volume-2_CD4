//-----------------------------------------------------------------------------
// test1.java
//
// Simple test class to launch the FontDialog class from a browser or an
// applet viewer so we can test it out a bit.
// 
//
// Author : Kelvin R Lawrence.     10th-December-1996
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: test1
//
//---------------------------------------------------------------
public class test1 extends Applet implements FontDialogClient
{
  Frame frame;
  FontDialog fd ;

  //----------------------------------------------------------------
  // init()
  //
  // Init routine. Called whenever the applet is first loaded.
  //
  //----------------------------------------------------------------
  public void init()
  {
    System.out.println( "init() method called" ) ;

    frame = new Frame( "Testing the font dialog" ) ;
    frame.resize( 400,400 );

    frame.show() ;

    fd = new FontDialog( frame, "Select a font...", false ) ;

    //------------------------------------------------------------------
    // Tell the font dialog that we want it to call our fontDialogNotify
    // method when the user dismisses the dialog.
    //------------------------------------------------------------------
    fd.setClient( this ) ;

    //--------------------
    // Display the dialog.
    //--------------------
    fd.show() ;

    System.out.println( "In init() again after fd.show()" ) ;
  }

  //-------------------------------------------------------------------------
  // fontDialogNotify()
  //
  // Wil get called when font dialog needs to notify us of an event.
  //-------------------------------------------------------------------------
  public void fontDialogNotify( int okcancel )
  {
    System.out.println( "fontDialogNotify() called." ) ;
    System.out.println( "fontDialogNotify() passed us a " + okcancel ) ;
  }

  //-------------------------------------------------------------------------
  // handleEvent()
  //
  // Handle events. We need this so that the close button will
  // actually close the application.
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
}
