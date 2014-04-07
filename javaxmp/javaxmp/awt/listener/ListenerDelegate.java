//-----------------------------------------------------------------------------
// ListenerDelegate.java
//
// A simple example of using event listeners. This is part of the new
// delegation event model that was added to Java as part of the 1.1.0 JDK. 
// See also the file popup.java which shows how to use other JDK 1.1 level
// event processing methods. This program is derived from popup.java.
//
// Unlike listener.java, this class implements the WindowListener and the
// MouseListener as "delegates." The delegate is a class that handles
// a given responsibility for its containing class.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   PopupMenu
//   MouseListener
//   KeyListener
//   ActionListener
//   Anonymous inner classes
//   Delegates
//
// Author : 
//    Kelvin R. Lawrence
//    Dan O. Becker
//
// History:
//    21-May-1997 -KRL-  Created.
//    26-Mar-1998 -DOB-  Updated to show inner classes, delegates.
//
//-----------------------------------------------------------------------------
// import krl.java.* ;
import java.awt.* ;
import java.awt.event.* ;

/**
 * ListenerDelegate
 * <p>
 * Simple class that shows how you can use event delegates to
 * listen for specific events.
 * <p>
 * Unlike listener.java, this class implements the WindowListener and the
 * MouseListener as "delegates." The delegate is a class that handles
 * a given responsibility for its containing class.
 *
 * @author  Kelvin R. Lawrence
 * @author  Dan O. Becker
 * @version 26Mar1998    
 */
public class ListenerDelegate extends Frame implements KeyListener, ActionListener
{
   //-------------------------------------------------------------------------
   // Constants
   // Immutable objects for this class
   //-------------------------------------------------------------------------

   //--------------------------------------------------------------------
   // main()
   // The entry point into the program.
   //--------------------------------------------------------------------
   public static void main( String [] args ) {
      // Construct a frame with the given title
      ListenerDelegate frame = new ListenerDelegate( "Listener Delegate Example" );
 
      // Add anonymous inner class to handle window exiting.
      frame.addWindowListener( 
         new WindowAdapter() {
            public void windowClosing( WindowEvent event ) { System.exit( 0 ); }
         }
      );

      // Center and show on SVGA screens.
      frame.setBounds( 50, 50, 700, 500 );
      frame.show();
   }

   //----------------------------------------------------------
   // Constructors
   //----------------------------------------------------------
   public ListenerDelegate( String s )
   {
     super(s);

     setLayout( new BorderLayout() ) ;

     addPopupMenu();

     // Add anonymous inner class to handle mouse listener responsibility.
     // Notice syntax in popupMenu.show how inner class refers to outer container class.
     addMouseListener( 
        new MouseListener() {
           //-------------------------------------------------------------------------
           // MouseListener role
           //-------------------------------------------------------------------------
           public void mouseClicked( MouseEvent mevt )
           {
             System.out.println( "Mouse clicked" ) ;
             popupMenu.show( (Component) ListenerDelegate.this, mevt.getX(), mevt.getY() ) ;
           }
           public void mouseEntered( MouseEvent mevt )
           {
             System.out.println( "Mouse entered" ) ;
           }
           public void mouseExited( MouseEvent mevt )
           {
             System.out.println( "Mouse exited" ) ;
           }
           public void mousePressed( MouseEvent mevt )
           {
             System.out.println( "Mouse pressed" ) ;
           }
           public void mouseReleased( MouseEvent mevt )
           {
             System.out.println( "Mouse released" ) ;
           }
        }
     );
     addKeyListener( this ) ;
   }
 
   //-------------------------------------------------------------------------
   // Component role
   //-------------------------------------------------------------------------
   public void paint( Graphics g )
   {
     System.out.println( "Paint() called" ) ;
 
     Dimension d = getSize() ;
 
     g.setColor( colour ) ;
 
     g.fillRect( 0,0,d.width,d.height ) ;
   }
 
   //-------------------------------------------------------------------------
   // KeyListener role
   //-------------------------------------------------------------------------
   //-------------------------------------------------------------------------
   // keyPressed()
   //-------------------------------------------------------------------------
   public void keyPressed( KeyEvent kevt )
   {
     System.out.println( "Key pressed ListenerDelegate" ) ;
   }
   //-------------------------------------------------------------------------
   // keyReleased()
   //-------------------------------------------------------------------------
   public void keyReleased( KeyEvent kevt )
   {
     System.out.println( "Key released ListenerDelegate" ) ;
   }
   //-------------------------------------------------------------------------
   // keyTyped()
   //-------------------------------------------------------------------------
   public void keyTyped( KeyEvent kevt )
   {
     System.out.println( "Key typed ListenerDelegate" ) ;
     popupMenu.show( (Component)this, 50, 50 ) ;
   }
 
   //-------------------------------------------------------------------------
   // ActionListener role
   //-------------------------------------------------------------------------
   //-------------------------------------------------------------------------
   // actionPerformed()
   // Handle an action for which we have registered interest.
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

   //-------------------------------------------------------------------------
   // addPopupMenu - performs all actions to create and init a popup menu.
   //-------------------------------------------------------------------------
   protected void addPopupMenu() 
   {
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
   // Fields
   // Objects that describe the state and information of this object.
   //-------------------------------------------------------------------------
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
}
