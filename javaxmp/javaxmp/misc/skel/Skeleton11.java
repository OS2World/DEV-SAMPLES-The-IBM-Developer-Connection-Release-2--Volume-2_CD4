//-----------------------------------------------------------------------------
// Skeleton11.java
//
// A simple example of an AWT frame to be used as a skeleton for other programs
//
// This program requires:
//    Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//    java.awt.Frame
//    java.awt.event.WindowListener
//    java.awt.event.ActionListener
//    java.awt.event.MouseListener
//
// Authors: 
//    Kelvin R. Lawrence
//    Dan O. Becker
//
// History:
//    05-May-1997 -KRL-  Created skeleton for JDK 1.1
//    05-May-1997 -KRL-  Created skeleton for JDK 1.1
//    24-Mar-1998 -DOB-  Updated to use JavaDoc style comments.
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;

/**
 * Skeleton11
 * <p>
 * A simple example of an AWT frame to be used as a skeleton for other programs.
 *
 * @author  Kelvin R. Lawrence
 * @author  Dan O. Becker
 * @version 24Mar1998    
 */
public class Skeleton11 extends Frame implements ActionListener
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
      Skeleton11 frame = new Skeleton11( "Skeleton11" );
 
      // Add anonymous class to handle window exiting.
      frame.addWindowListener( 
         new WindowAdapter() {
            public void windowClosing( WindowEvent event ) { System.exit( 0 ); }
         }
      );
      // Add anonymous class to handle mouse clicking.
      frame.addMouseListener( 
         new MouseAdapter() {
            public void mouseClicked( MouseEvent event ) { 
               System.out.println( "mouseClicked called, x,y=" + event.getX() + "," + event.getY() );
            }
         }
      );

      // Center and show on SVGA screens.
      frame.setBounds( 50, 50, 700, 500 );
      frame.show();
   }

   //-------------------------------------------------------------------------
   // Constructors
   // Create instances of this class
   //-------------------------------------------------------------------------
   public Skeleton11( String s ) 
   {
      // Initialize frame with given title.
      super(s);
  
      // Enable basic listener interfaces listed above.
      // addActionListener( this ); // add to button, menu, list...
   }
 
   //-------------------------------------------------------------------------
   // Component role
   // Methods that define this program's role as a Component
   //-------------------------------------------------------------------------
   /**
    * Called by system when  a component needs to paint its contents.
    * @param graphics The graphics object in which to draw.
    */
   public void paint( Graphics g )
   {
     System.out.println( "paint() called" );
 
     Dimension d = getSize();
     g.setColor( Color.gray );
     g.fillRect( 0, 0, d.width, d.height);
   }

   //-------------------------------------------------------------------------
   // ActionListener role
   // Methods that handle the action events from buttons, menus, lists, etc.
   //-------------------------------------------------------------------------
   /**
    * Called when a user performs an action event, for example
    * presses a button, clicks a menu, or selects from a list.
    * @param event The action event associated with this action.
    */
   public void actionPerformed( ActionEvent event ) {
      System.out.println( "actionPerformed called, action command=" + 
         event.getActionCommand() );
   }
 
   //-------------------------------------------------------------------------
   // Fields
   // Objects that describe the state and information of this object.
   //-------------------------------------------------------------------------
}
