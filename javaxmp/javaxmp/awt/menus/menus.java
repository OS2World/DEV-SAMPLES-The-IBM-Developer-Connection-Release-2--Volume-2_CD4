//-----------------------------------------------------------------------------
// menus.java
//
// A simple example of using menus in a Java application.
//
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes:
//
//   Menu
//   MenuBar
//   menuItem
//
// Author : Kelvin R Lawrence.     30th-April-1997
//
// History:
//  27-Jul-1997 -KRL- Updated to test menu separators.
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
  MenuBar myMenuBar ; // Menu bar object.
  Menu    myMenu1   ; // First menu item object.
  Menu    myMenu2   ; // Second menu item object.
  Menu    mySubMenu ; // Sub menu object

  CheckboxMenuItem chk1 ;
  CheckboxMenuItem chk2 ;

  //----------------------------------------------------------
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public menus( String s )
  {

    super(s);

    setLayout(null) ;

    //------------------------------------------------
    // Create a menu bar and add some menus to it.
    // We add two menus to the menu bar each containing
    // some menu items. Add a submenu to the second
    // pulldown menu.
    //------------------------------------------------

    myMenu1 = new Menu( "Menu 1" ) ;
    myMenu2 = new Menu( "Menu 2" , true ) ;

    mySubMenu = new Menu( "Submenu" ) ;

    mySubMenu.add( new MenuItem( "Sub Menu Item 1" ) ) ;
    mySubMenu.add( new MenuItem( "Sub Menu Item 2" ) ) ;
    mySubMenu.add( new MenuItem( "Sub Menu Item 3" ) ) ;

    myMenu1.add( new MenuItem( "Simple Item 1") ) ;
    myMenu1.add( new MenuItem( "Simple Item 2") ) ;
    myMenu1.add( new MenuItem( "Simple Item 3") ) ;
    myMenu1.addSeparator() ;

    chk1 = new CheckboxMenuItem( "Checkbox Item 4" ) ;
    chk2 = new CheckboxMenuItem( "Checkbox Item 5" ) ;

    myMenu1.add( chk1 ) ;
    myMenu1.add( chk2 ) ;

    myMenu2.add( new MenuItem( "Item 1") ) ;
    myMenu2.add( new MenuItem( "Item 2") ) ;
    myMenu2.add( new MenuItem( "Item 3") ) ;
    myMenu2.add( mySubMenu ) ;

    myMenuBar = new MenuBar() ;

    myMenuBar.add( myMenu1 ) ;
    myMenuBar.add( myMenu2 ) ;

    this.setMenuBar( myMenuBar ) ;
    System.out.println( "Menu bar has " + myMenuBar.countMenus() + " items." ) ;
    System.out.println( "Menu 1 has " + myMenu1.countItems() + " items." );
    System.out.println( "Menu 2 has " + myMenu2.countItems() + " items." );

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

      if ( evt.target == chk1 )
      {
        chk2.setState( !chk1.getState() ) ;   
      }
      else if ( evt.target == chk2 )
      {
        chk1.setState( !chk2.getState() ) ;   
      }
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
     menus mnuFrame ;

     mnuFrame = new menus( "Menu Tests" ) ;

     mnuFrame.resize( 300,300 ) ;
     mnuFrame.show() ;
  }
}



