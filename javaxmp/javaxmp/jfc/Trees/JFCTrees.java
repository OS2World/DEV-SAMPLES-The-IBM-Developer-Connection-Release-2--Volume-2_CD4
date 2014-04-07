//-----------------------------------------------------------------------------
// JFCTrees.java
//
// An example of using the new JFC classes that allow tree views to be constructed
// and manipulated.
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set
// classes (typically called swing.jar) to be in the Classpath.
//
// Developed using Swing ver. 0.7 (pre-Beta)
//
// Demonstrates use of these classes/interfaces:
//
//   Container
//   DefaultMutableTreeNode
//   JFrame
//   JTree
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC.
//
// Author : Kelvin R Lawrence.     13th-Jan-1998
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.* ;
import com.sun.java.swing.tree.*;

public class JFCTrees extends JFrame implements ActionListener
{
  private Container client ;

  //-------------------------------------------------------------------------
  // Constructor
  //
  //-------------------------------------------------------------------------
  public JFCTrees( String s )
  {
    super( s ) ;

    System.out.println( "Constructor called" ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    client = this.getContentPane() ;

    DefaultMutableTreeNode top = new DefaultMutableTreeNode("Music");
    DefaultMutableTreeNode category;
    DefaultMutableTreeNode composer;
    DefaultMutableTreeNode style;

    category = new DefaultMutableTreeNode("Classical");
    top.add(category);

    JTree tree = new JTree(top);
    client.add(new JScrollPane(tree), BorderLayout.CENTER);
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
  // actionPerformed()
  //
  // Handle an action for which we have registered interest.
  //
  //-------------------------------------------------------------------------
  public void actionPerformed( ActionEvent aevt )
  {
    if ( aevt.getSource() instanceof JCheckBoxMenuItem )
    {
      System.out.println( "actionPerformed() got called " ) ;
    }
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCTrees JFCTreesFrame ;

     JFCTreesFrame = new JFCTrees( "JFC Sliders." ) ;

     JFCTreesFrame.setSize( 300,300 ) ;
     //JFCTreesFrame.setBackground( Color.lightGray ) ;

     JFCTreesFrame.show() ;
  }
}
