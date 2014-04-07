//-----------------------------------------------------------------------------
// listener.java
//
// A simple example of using event listeners. This is part of the new
// delegation event model that was added to Java as part of the 1.1.0 JDK. 
// See also the file popup.java which shows how to use other JDK 1.1 level
// event processing methods. This program is derived from popup.java.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   PopupMenu
//   MouseListener
//   KeyListener
//   ActionListener
//
// Author : Kelvin R Lawrence.     21st-May-1997
//
// History:
//
//-----------------------------------------------------------------------------
import krl.java.* ;
import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: listener
//
// Simple class that shows how you can use event listeners to
// listen for specific events.
//
//---------------------------------------------------------------

public class listener extends Frame implements MouseListener,
                                               KeyListener , 
                                               ActionListener
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
  public listener( String s )
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

    addMouseListener( this ) ;
    addKeyListener( this ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

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
    //popupMenu.addActionListener( this ) ;
    //submenu.addActionListener( this ) ;

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
  // will actually close the application etc.
  //-------------------------------------------------------------------------
  public void processEvent( AWTEvent evt )
  {
    
    System.out.println( "Process event" ) ;

    if ( evt.getID() == WindowEvent.WINDOW_CLOSING )
    {
      System.out.println( "Window about to close..." ) ;
      System.exit(0) ;
    }
    else
    {
      super.processEvent( evt ) ;
    }
  }

  //-------------------------------------------------------------------------
  // mouseClicked()
  //
  //-------------------------------------------------------------------------
  public void mouseClicked( MouseEvent mevt )
  {
    System.out.println( "Mouse clicked listener" ) ;
    popupMenu.show( (Component)this, mevt.getX(), mevt.getY() ) ;
  }

  //-------------------------------------------------------------------------
  // mouseEntered()
  //
  //-------------------------------------------------------------------------
  public void mouseEntered( MouseEvent mevt )
  {
    System.out.println( "Mouse entered listener" ) ;
  }
  //-------------------------------------------------------------------------
  // mouseExited()
  //
  //-------------------------------------------------------------------------
  public void mouseExited( MouseEvent mevt )
  {
    System.out.println( "Mouse exited listener" ) ;
  }
  //-------------------------------------------------------------------------
  // mousePressed()
  //
  //-------------------------------------------------------------------------
  public void mousePressed( MouseEvent mevt )
  {
    System.out.println( "Mouse pressed listener" ) ;
  }
  //-------------------------------------------------------------------------
  // mouseReleased()
  //
  //-------------------------------------------------------------------------
  public void mouseReleased( MouseEvent mevt )
  {
    System.out.println( "Mouse released listener" ) ;
  }

  //-------------------------------------------------------------------------
  // keyPressed()
  //
  //-------------------------------------------------------------------------
  public void keyPressed( KeyEvent kevt )
  {
    System.out.println( "Key pressed listener" ) ;
  }

  //-------------------------------------------------------------------------
  // keyReleased()
  //
  //-------------------------------------------------------------------------
  public void keyReleased( KeyEvent kevt )
  {
    System.out.println( "Key released listener" ) ;
  }

  //-------------------------------------------------------------------------
  // keyTyped()
  //
  //-------------------------------------------------------------------------
  public void keyTyped( KeyEvent kevt )
  {
    System.out.println( "Key typed listener" ) ;
    popupMenu.show( (Component)this, 50, 50 ) ;
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
     listener listenerFrame ;

     listenerFrame = new listener( "Event listener tests" ) ;

     listenerFrame.setSize( 300,300 ) ;
     listenerFrame.show() ;
  }
}
