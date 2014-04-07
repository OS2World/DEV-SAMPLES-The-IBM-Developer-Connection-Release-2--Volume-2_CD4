//-----------------------------------------------------------------------------
// PhotoTree.java
//
// A simple example of displaying Swing ImageIcons in a JTree.
//
// This example shows how to override the renderer in a tree, so
// you can show your own type of nodes.
//
// It also shows how you can create tree nodes of any type by
// implementing the DefaultMutableTreeNode interface.
//
// This program requires:
//    Java JDK level 1.1.0 or higher.
//    Swing version 0.8 or higher.
//
// Demonstrates use of these classes/interfaces:
//    com.sun.java.swing.JFrame
//    com.sun.java.swing.JTree
//    com.sun.java.swing.tree.TreeNode
//    com.sun.java.swing.DefaultMutableTreeNode
//    com.sun.java.swing.plaf.basic.BasicTreeCellRenderer;
//    com.sun.java.swing.JLabel
//    com.sun.java.swing.JScrollPane
//    com.sun.java.swing.ImageIcon
//
// Authors: 
//    Kelvin R Lawrence
//    Dan O. Becker
//
// History:
//    13-Jan-1998 -KRL-  Created example based on JFCTrees
//    28-Mar-1998 -DOB-  Updated to use ImageIcons and images.
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import com.sun.java.swing.*;
import com.sun.java.swing.event.* ;
import com.sun.java.swing.tree.*;
import com.sun.java.swing.plaf.basic.*;

/**
 * PhotoTree
 * <p>
 * A simple example of displaying Swing ImageIcons in a JTree.
 * <p>
 * This example shows how to override the renderer in a tree, so
 * you can show your own type of nodes.
 * <p>
 * It also shows how you can create tree nodes of any type by
 * implementing the DefaultMutableTreeNode interface.
 *
 * @author  Dan O. Becker
 * @version 28Mar1998    
 */
public class PhotoTree extends JFrame 
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
      PhotoTree frame = new PhotoTree( "Photo Tree", args );
 
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
   public PhotoTree( String s, String [] args ) 
   {
      // Initialize frame with given title.
      super(s);
  
      if ( args.length == 0 ) {
         System.out.println( "Provide command line arguments for additional photos for the tree. For example:" );
         System.out.println( "    java PhotoTree ../../images/harriet.jpg ../../images/swimming.jpg" ); 
      } /* endif */
 
      // Remember content pane for future use. Default layout is BorderLayout.
      content = getContentPane();

      // Add a root node for the tree.
      DefaultMutableTreeNode top = new DefaultMutableTreeNode( "Photos" );

      // Add a few default branches in the tree.
      DefaultMutableTreeNode graphics = new DefaultMutableTreeNode( "Graphics" );
      top.add( graphics );
      graphics.add( new PhotoTreeNode( "../../images/javalogo.gif" ) );
      graphics.add( new PhotoTreeNode( "../../images/olympic.gif" ) );
      DefaultMutableTreeNode dogs = new DefaultMutableTreeNode( "Dog Pictures" );
      top.add( dogs );
      dogs.add( new PhotoTreeNode( "../../images/harriet.jpg" ) );
      dogs.add( new PhotoTreeNode( "../../images/swimming.jpg" ) );
      DefaultMutableTreeNode planes = new DefaultMutableTreeNode( "Plane Pictures" );
      top.add( planes );
      planes.add( new PhotoTreeNode( "../../images/737-500.gif" ) );
      DefaultMutableTreeNode others = new DefaultMutableTreeNode( "Other Pictures" );
      top.add( others );

      // Fetch images for each command line argument.
      for ( int i = 0; i < args.length; i++ ) 
         others.add( new PhotoTreeNode( args[ i ] ));

      // Instantiate the tree and add it to this frame.
      JTree tree = new JTree( top );
      // System.out.println( "Default tree cell renderer=" + tree.getCellRenderer() );
      // Change the tree cell renderer so we can draw images.
      tree.setCellRenderer( new ExtendedTreeCellRenderer() );
      content.add( "Center", new JScrollPane( tree ) );
   }
 
   //-------------------------------------------------------------------------
   // PhotoTreeNode
   // A node for a JTree that extends a JLabel so that is can show
   // a text or an image label.
   // This node does not support children and only can be used as a leaf.
   //-------------------------------------------------------------------------
   public class PhotoTreeNode implements MutableTreeNode {
      //-------------------------------------------------------------------------
      // Constants
      // Immutable objects for this class
      //-------------------------------------------------------------------------
      public final IllegalArgumentException noKids = 
         new IllegalArgumentException( "A PhotoTreeNode does not support children." );

      //-------------------------------------------------------------------------
      // Constructors
      // Create instances of this class
      //-------------------------------------------------------------------------
      public PhotoTreeNode( String fileName ) {
         // Create the view of the image.
         ImageIcon image = new ImageIcon( fileName );
         if (( image.getIconWidth() > 0 ) && ( image.getIconHeight() > 0 )) 
            nodeView = new JLabel( image );
         else
            System.out.println( "Got a bad image for argument \"" + fileName + "\"." );
      }

      //-------------------------------------------------------------------------
      // TreeNode role
      // Since a PhotoTreeNode supports no children, many methods simply throw
      // an exception, and others simply return legal default values.
      //-------------------------------------------------------------------------
      public TreeNode getChildAt( int childIndex ) { throw noKids; }
      public int getChildCount() { return 0; }
      public int getIndex(TreeNode node) { return -1; }
      public Enumeration children() { throw noKids; }
      public boolean getAllowsChildren() { return false; }
      public boolean isLeaf() { return true; }
      public TreeNode getParent() { return parent; }
  
      //-------------------------------------------------------------------------
      // MutableTreeNode role
      // Since a PhotoTreeNode supports no children, many methods simply throw
      // an exception, and others simply return legal default values.
      //-------------------------------------------------------------------------
      public void insert( MutableTreeNode child, int index ) { throw noKids; }
      public void remove( int index ) { throw noKids; }
      public void remove( MutableTreeNode node ){ throw noKids; } 
      public void setUserObject( Object object ) {}
      public void removeFromParent() {}
      public void setParent( MutableTreeNode newParent ) { this.parent = newParent; }
      // Not part of Mutable tree node, but it seems needed.  
      public Object getUserObject( ) { 
         System.out.println( "getUseObject called" );
         return nodeView;
      }

      public MutableTreeNode parent;
      public JLabel nodeView;
   }

   //-------------------------------------------------------------------------
   // ExtendedTreeCellRenderer
   // This renderer extends the capabilities of the BasicTreeCellRenderer.
   // Normally the renderer displays a default icon for the tree node.
   // Here, we look if the tree node supports photos and, if yes, return that.
   //-------------------------------------------------------------------------
   public class ExtendedTreeCellRenderer extends BasicTreeCellRenderer implements TreeCellRenderer {
      //-------------------------------------------------------------------------
      // TreeCellRenderer role
      // This is the call that actually draws the tree cell.
      //-------------------------------------------------------------------------
      public Component getTreeCellRendererComponent( 
         JTree tree, Object value,
         boolean selected, boolean expanded,
         boolean leaf, int row, boolean hasFocus ) {

         // If this is a photo node, render it with the photo.
         if ( value instanceof PhotoTreeNode ) 
            return ((PhotoTreeNode)value).nodeView;

         // Otherwise, render the  node with the default tree renderer.
         return super.getTreeCellRendererComponent( 
            tree, value, selected, expanded, leaf, row, hasFocus );
      }
  }

   //-------------------------------------------------------------------------
   // Fields
   // Objects that describe the state and information of this object.
   //-------------------------------------------------------------------------
   private Container content;
   private JTree tree;
}
