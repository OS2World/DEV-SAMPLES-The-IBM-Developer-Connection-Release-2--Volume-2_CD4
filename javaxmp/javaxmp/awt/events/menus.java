//-----------------------------------------------------------------------------
// menus.java
//
// A simple example of using menus in a Java application.
//
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of:
//
//   Menu
//   MenuBar
//   menuItem
//
// Author : Kelvin R Lawrence.     30th-April-1997
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: menus
//
// Simple class that experiments with Java AWT menu capabilities.
//
//---------------------------------------------------------------

public class menus extends Frame
{
  //----------------------------------------------------------
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {
    setLayout(null) ;

    System.out.println( "init() method called" ) ;

    //------------------------------------------------
    // Create a frame window and add a menu bar to it.
    // Add two menus to the menu bar each containing
    // some menu items. Add a submenu to the second
    // pulldown menu.
    //------------------------------------------------

    Frame   myFrame   ; // Frame window object.
    MenuBar myMenuBar ; // Menu bar object.
    Menu    myMenu1   ; // First menu item object.
    Menu    myMenu2   ; // Second menu item object.
    Menu    mySubMenu ; // Sub menu object

    myFrame = new Frame("Menu tests" );

    myMenu1 = new Menu( "Menu 1" ) ;
    myMenu2 = new Menu( "Menu 2" , true ) ;

    mySubMenu = new Menu( "Submenu" ) ;

    mySubMenu.add( new MenuItem( "Sub Menu Item 1" ) ) ;
    mySubMenu.add( new MenuItem( "Sub Menu Item 2" ) ) ;
    mySubMenu.add( new MenuItem( "Sub Menu Item 3" ) ) ;

    myMenu1.add( new MenuItem( "Item 1") ) ;
    myMenu1.add( new MenuItem( "Item 2") ) ;
    myMenu1.add( new MenuItem( "Item 3") ) ;

    myMenu2.add( new MenuItem( "Item 1") ) ;
    myMenu2.add( new MenuItem( "Item 2") ) ;
    myMenu2.add( new MenuItem( "Item 3") ) ;
    myMenu2.add( mySubMenu ) ;

    myMenuBar = new MenuBar() ;

    myMenuBar.add( myMenu1 ) ;
    myMenuBar.add( myMenu2 ) ;

    myFrame.setMenuBar( myMenuBar ) ;


    //------------------------------------------------
    // Add our frame window to the Applet's container
    // This way we will get the menu click events sent
    // to our action() method. Then resize the frame
    // window and display it.
    //------------------------------------------------
    //add( myFrame );

    myFrame.resize( 100, 100 ) ;
    myFrame.show() ;

  }

  public boolean action( Event evt, Object what )
  {
    System.out.println( "action() method got called" );

    return true ;
  }

  public boolean mouseEnter( Event evt, int x, int y )
  {
    System.out.println( "mouseEnter() event triggered" ) ;

    return true ;
  }


  public boolean mouseExit( Event evt, int x, int y )
  {
    System.out.println( "mouseExit() event triggered" ) ;

    return true ;
  }

}



