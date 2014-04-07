//-----------------------------------------------------------------------------
// popup.java
//
// A simple example of using popup menus. This feature was
// added to Java as part of the 1.1.0 JDK. Popup menus were
// not available in earlier releases. This program also uses the
// new AWT event model. See also the file listener.java for examples
// of using event listeners to capture events as they happen.
//
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   PopupMenu
//
// Author : Kelvin R Lawrence.     17th-May-1997
//
// History:
//   21-May-1997 -KRL- Ported to new event model. Replaced deprecated APIs.
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: popup
//
// Simple class that experiments with Java AWT menu capabilities.
//
//---------------------------------------------------------------

public class popup extends Frame implements ActionListener
{
  private Color colour = Color.white ;

  private PopupMenu popupMenu ;

  private MenuItem  menuRed    ;
  private MenuItem  menuBlue   ;
  private MenuItem  menuGreen  ;
  private MenuItem  menuYellow ;

  private MenuItem  menuGrey    ;
  private MenuItem  menuMagenta ;
  private MenuItem  menuCyan    ;
  private MenuItem  menuBlack   ;

  private Menu submenu ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public popup( String s )
  {
    super(s);

    setLayout( new BorderLayout() ) ;

    popupMenu = new PopupMenu() ;

    popupMenu.add( menuRed    = new MenuItem( "Red" ) ) ;
    popupMenu.add( menuBlue   = new MenuItem( "Blue" ) ) ;
    popupMenu.add( menuGreen  = new MenuItem( "Green" ) ) ;
    popupMenu.add( menuYellow = new MenuItem( "Yellow" ) ) ;

    popupMenu.add( submenu = new Menu( "More colours" ) ) ;

    submenu.add( menuGrey    = new MenuItem( "Grey" ) ) ;
    submenu.add( menuCyan    = new MenuItem( "Cyan" ) ) ;
    submenu.add( menuMagenta = new MenuItem( "Magenta" ) ) ;
    submenu.add( menuBlack   = new MenuItem( "Black" ) ) ;

    add( popupMenu ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK
                | AWTEvent.KEY_EVENT_MASK ) ;

    //------------------------------------------------------------------
    // Now register to receive action events from the popup menu.
    //
    // Note that if you register for events for the whole menu you
    // get called with the menu itself as the source and the menu
    // string will be the name of the menu item. I prefer to refer
    // to menu items using their object names rather than string names
    // as when working with string tables and translations this can get
    // rather messy. Therefore, I will register to receive events from 
    // the individual menu items. Seems long winded to me but it makes
    // for a cleaner processActionEvent() method body.
    //
    // Is there a way to get the menu ID's back for each item ???
    //------------------------------------------------------------------

    menuRed.addActionListener( this ) ;
    menuBlue.addActionListener( this ) ;
    menuGreen.addActionListener( this ) ;
    menuYellow.addActionListener( this ) ;
    menuGrey.addActionListener( this ) ;
    menuCyan.addActionListener( this ) ;
    menuMagenta.addActionListener( this ) ;
    menuBlack.addActionListener( this ) ;
  }

  //-------------------------------------------------------------------------
  // paint()
  //
  //-------------------------------------------------------------------------
  public void paint( Graphics g )
  {
    System.out.println( "Paint() called" ) ;

    Dimension d = getSize() ;

    g.setColor( colour ) ;

    g.fillRect( 0,0,d.width,d.height ) ;
  }

  //-------------------------------------------------------------------------
  // processEvent()
  //
  // Handle high level events. We need this so that the close in the title bar 
  // will actually close the application etc. We must pass all events 
  // that we don't handle up to our super class.
  //-------------------------------------------------------------------------
  public void processEvent( AWTEvent evt )
  {
    if ( evt.getID() == WindowEvent.WINDOW_CLOSING )
    {
      System.out.println( "processEvent()  : Window about to close..." ) ;
      System.exit(0) ;
    }
    else
    {
      super.processEvent( evt ) ;
    }
  }

  //-------------------------------------------------------------------------
  // processMouseEvent()
  //
  // Process mouse events. Notice that mouse move and drag events are
  // not sent here. They go to the processMouseMoveEvent() method if there
  // is one.
  //
  // In this particular case we only do something with the MOUSE_CLICKED
  // event. The event ID will be one of:
  //
  //       MOUSE_CLICKED
  //       MOUSE_PRESSED
  //       MOUSE_RELEASED
  //       MOUSE_ENTERED
  //       MOUSE_EXITED
  //
  //-------------------------------------------------------------------------
  public void processMouseEvent( MouseEvent mevt )
  {
    switch( mevt.getID() )
    {
      case MouseEvent.MOUSE_CLICKED:
      {
        System.out.println( "MOUSE_CLICKED event." ) ;
        popupMenu.show( (Component)this, mevt.getX(), mevt.getY() ) ;
      }
    }
  }

  //-------------------------------------------------------------------------
  // processKeyEvent()
  //
  // Process key events. 
  //
  // In this particular case we only do something with the KEY_TYPED event.
  // The event ID will be one of:
  //
  //  KEY_TYPED
  //  KEY_PRESSED
  //  KEY_RELEASED
  //
  //-------------------------------------------------------------------------
  public void processKeyEvent( KeyEvent kevt )
  {
    switch( kevt.getID() )
    {
      case KeyEvent.KEY_TYPED:
      {
        System.out.println( "KEY_TYPED event." ) ;
        popupMenu.show( (Component)this, 50, 50 ) ;
      }
    }
  }

  //-------------------------------------------------------------------------
  // actionPerformed()
  //
  // Handle an action for which we have registered interest.
  //
  //-------------------------------------------------------------------------
  public void actionPerformed( ActionEvent aevt )
  {
    if ( aevt.getSource() instanceof MenuItem )
    {
      System.out.println( "actionPerformed() got called for a menu item." ) ;
      System.out.println( "Action command string ==> " + aevt.getActionCommand() ) ;

      MenuItem source = (MenuItem)aevt.getSource() ;

      if ( source == menuRed )
      {
        colour = Color.red ;
      }
      else if ( source == menuGreen )
      {
        colour = Color.green ;
      }
      else if ( source == menuBlue )
      {
        colour = Color.blue ;
      }
      else if ( source == menuYellow )
      {
        colour = Color.yellow ;
      }
      else if ( source == menuGrey )
      {
        colour = Color.gray ;
      }
      else if ( source == menuCyan )
      {
        colour = Color.cyan ;
      }
      else if ( source == menuMagenta )
      {
        colour = Color.magenta ;
      }
      else if ( source == menuBlack )
      {
        colour = Color.black ;
      }

      repaint() ;
    }
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     popup popupFrame ;

     popupFrame = new popup( "Popup Menu Tests" ) ;

     popupFrame.setSize( 300,300 ) ;
     popupFrame.show() ;
  }
}
