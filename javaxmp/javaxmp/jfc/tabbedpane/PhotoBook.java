//-----------------------------------------------------------------------------
// PhotoBook.java
//
// A simple example of displaying Swing ImageIcons in a JTabbedPane.
//
// This program requires:
//    Java JDK level 1.1.0 or higher.
//    Swing version 0.8 or higher.
//
// Demonstrates use of these classes/interfaces:
//    com.sun.java.swing.JFrame
//    com.sun.java.swing.JTabbedPane
//    com.sun.java.swing.JLabel
//    com.sun.java.swing.JScrollPane
//    com.sun.java.swing.ImageIcon
//
// Authors: 
//    Dan O. Becker
//
// History:
//    28-Mar-1998 -DOB-  Created
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import com.sun.java.swing.*;
import com.sun.java.swing.event.* ;

/**
 * PhotoBook
 * <p>
 * A simple example of displaying Swing ImageIcons in a JTabbedPane.
 * <p>
 * Start the program with command line arguments. Each argument
 * will be read and displayed on its own page in a JTabbedPane.
 *
 * @author  Dan O. Becker
 * @version 28Mar1998    
 */
public class PhotoBook extends JFrame 
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
      PhotoBook frame = new PhotoBook( "Photo Book", args );
 
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
   public PhotoBook( String s, String [] args ) 
   {
      // Initialize frame with given title.
      super(s);
  
      if ( args.length == 0 ) {
         System.out.println( "Provide command line arguments for photos for the book. For example:" );
         System.out.println( "    java PhotoBook ../../images/harriet.jpg ../../images/swimming.jpg" ); 
         System.exit( 0 );
      } /* endif */
 
      // Remember content pane for future use. Default layout is BorderLayout.
      content = getContentPane();

      // Add a tabbed pane to the frame.
      content.add( "Center", tabbedPane = new JTabbedPane() );

      // Fetch images for each command line argument.
      for ( int i = 0; i < args.length; i++ ) {
         ImageIcon image = new ImageIcon( args[ i ] );

         // Check that image is O.K.         
         if (( image.getIconWidth() > 0 ) && ( image.getIconHeight() > 0 )) {

            // Figure the name for the tab. Here we use the base of the image name.
            String tabName = "Photo " + i;
            StringTokenizer st = new StringTokenizer( args[ i ], "/\\." );
            if ( st.countTokens() >=2 ) {
               // Get the second to last token which should be the file base name.
               for ( int position = st.countTokens(); position > 2; position-- ) 
                  st.nextToken();
               tabName = st.nextToken();
            } /* endif */
   
            // Add the image to the photo book.
            //    Add the image to a JLabel to make it a Component.
            //    Add the image to a scroll pane in case it is bigger than the page.
            tabbedPane.addTab( tabName, new JScrollPane( new JLabel( image )) );
         } else
            System.out.println( "Got a bad image for argument " + i + " \"" + args[ i ] + "\"." );
      } /* endfor */
 

      // Enable basic listener interfaces listed above.
      // addActionListener( this ); // add to button, menu, list...
   }
 
   //-------------------------------------------------------------------------
   // Fields
   // Objects that describe the state and information of this object.
   //-------------------------------------------------------------------------
   private Container content;
   private JTabbedPane tabbedPane;
}
