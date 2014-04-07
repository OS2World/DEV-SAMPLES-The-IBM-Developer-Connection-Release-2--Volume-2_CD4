//-----------------------------------------------------------------------------
// TestLayout.java
//
// An example of testing a layout manager.
//
// This program requires:
//    Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//    java.awt.Frame
//
// Authors: 
//    Dan O. Becker
//
// History:
//    26-Mar-1998 -DOB-  Created
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;

/**
 * TestLayout
 * <p>
 * An example of testing a layout manager.
 *
 * @author  Dan O. Becker
 * @version 26Mar1998    
 */
public class TestLayout extends Frame 
{
   //--------------------------------------------------------------------
   // main()
   // The entry point into the program.
   //--------------------------------------------------------------------
   public static void main( String [] args ) {
      // Construct a frame with the given title
      TestLayout frame = new TestLayout( "TestLayout" );
 
      // Add anonymous class to handle window exiting.
      frame.addWindowListener( 
         new WindowAdapter() {
            public void windowClosing( WindowEvent event ) { System.exit( 0 ); }
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
   public TestLayout( String s ) 
   {
      // Initialize frame with given title.
      super(s);
  
      setLayout( new LayoutMultiBorder() );
      add( "North", new TextField( "This is a TextField. It was added north" ));
      add( "North", new TextField( "This is also a TextField. It also was added north" ));
      add( "East", new Button( "East 1" ));
      add( "East", new Button( "East 2" ));
      add( "West", new Button( "West 1" ));
      add( "West", new Button( "West 2" ));
      add( "South", new TextArea( "This is a TextArea. It was added South" ));
      add( "South", new TextArea( "This is also a TextArea. It also was added South" ));
   }
 
}
