//-----------------------------------------------------------------------------
// filedlg.java
//
// Simple Java test application that demonstrates how to display and
// get data from the system file dialog.
//
// Author : Kelvin R Lawrence.     12th-Sept-1996.
//
// History:
//  04-Nov-1996  -KRL-  Added handleEvent method and Frame subclass.
// 
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: filedlg
//
// Simple class that experiments with Java file dialogs.
//
//---------------------------------------------------------------

public class filedlg extends Applet
{
  myFrameClass  myFrame  ; // Frame window object.
  MenuBar       MenuBar  ; // Menu bar object.
  Menu          FileMenu ; // File menu.
  MenuItem      FileOpen ; // Open item on file menu.

  //----------------------------------------------------------
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {
    setLayout(null) ;

    System.out.println( "init() method called" ) ;

    //------------------------------------------------
    // Create a frame window.
    //------------------------------------------------

    myFrame = new myFrameClass("File dialog tests" );

    FileMenu  = new Menu( "File" );
    FileOpen  = new MenuItem( "Open..." ) ;
    FileMenu.add( FileOpen ) ;
    MenuBar = new MenuBar() ;
    MenuBar.add( FileMenu ) ;
    myFrame.setMenuBar( MenuBar ) ;

    //------------------------------------------------
    // Add our frame window to the Applet's container
    // This way we will get the events we need.
    //------------------------------------------------
    add( myFrame );

    myFrame.resize( 200,200 ) ;
    myFrame.show() ;
  }
}

//------------------------------------------------------------------------------------
// Class: myFrameClass
//
// Simple subclass of Frame so that we can catch action() events for the frame window.
// We need to do this so that we can process menu action events for the frames menu.
//------------------------------------------------------------------------------------
class myFrameClass extends Frame
{
  FileDialog fd       ; // File dialog object.
  String     filename ; // Filename returned from file dialog.
  String     path     ; // Path returned from file dialog.

  myFrameClass( String title )
  {
    super(title) ;
  }

  //-------------------------------------------------------------------------
  // action()
  //
  // This method gets invoked whenever a control message such as a push
  // button click or a menu item selection takes place.
  //-------------------------------------------------------------------------
  public boolean action( Event evt, Object what )
  {
    System.out.println("Start of action() method");

    fd = new FileDialog( this, "Select a file" ) ;
    fd.show() ;

    filename = fd.getFile() ;
    path     = fd.getDirectory() ;

    System.out.println( "Returned with file name :" + filename ) ;
    System.out.println( "Returned with path name :" + path ) ;
   
    return true ;
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



