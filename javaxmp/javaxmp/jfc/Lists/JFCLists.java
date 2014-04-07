//-----------------------------------------------------------------------------
// JFCLists.java
//
// A simple example of using the JFC scrolling list capabilities.
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set 
// classes (typically called swing.jar) to be in the Classpath.
//
// Developed using Swing ver. 0.5.1 (pre-Beta)
//
// Demonstrates use of these classes/interfaces:
//
//   BorderFactory
//   JFrame
//   JList
//   JPanel
//   JScrollPane
//   ListSelectionListener
//   ScrollPaneConstants
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. Note also that there have been
//               significant changes between the 0.4.1 and 0.5.1 versions.
//
// Author : Kelvin R Lawrence.     12th-Nov-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;

public class JFCLists extends JFrame implements ListSelectionListener,
                                                ScrollPaneConstants
{
  private Container client ;
  
  private JList list ;
  private JScrollPane scp ;
  
  //-------------------------------------------------------------------------
  // Constructor #1
  //
  //-------------------------------------------------------------------------
  public JFCLists( String s )
  {
    super( s ) ;

    System.out.println( "Constructor called" ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    client = this.getContentPane() ;
    
    client.setLayout( new FlowLayout() ) ;
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
  // valueChanged()
  //
  // Because we are implementing the ListSelectionListener interface, this
  // method will get notified each time the list box selection changes.
  //
  //-------------------------------------------------------------------------
  public void valueChanged( ListSelectionEvent levt )
  {
    System.out.println( "Selected index is " + list.getSelectedIndex() ) ;
  }

  //--------------------------------------------------------------------
  // runTests()
  //
  //--------------------------------------------------------------------
  public void runTests()
  {
    System.out.println( "*** In runTests() ***" ) ;
    System.out.println( client.toString() ) ;
    
    String [] contents = { "One" , "Two"   , "Three" , "Four" , "Five"
                         , "Six" , "Seven" , "Eight" , "Nine" , "Ten" } ;
                         
    list = new JList( contents ) ;
    
    list.addListSelectionListener( this ) ;
    
    scp = new JScrollPane() ;
    scp.setVerticalScrollBarPolicy( VERTICAL_SCROLLBAR_ALWAYS ) ;
    scp.getViewport().setView(list) ;
    
    scp.setBorder( BorderFactory.createRaisedBevelBorder( ) ) ;
    
    //---------------------------------------------------------------
    // Add the components to the panel before we add it to the 
    // content pane.
    //---------------------------------------------------------------
    client.add(scp)  ;
    
    //---------------------------------------------------------------
    // This is how you query how many items are in a list. First you
    // have to get hold of the ListModel object and then get the size
    // from it.
    //---------------------------------------------------------------
    int numItems = list.getModel().getSize() ;
    
    System.out.println( "List contains " + numItems + " item(s)." ) ;
    
    //---------------------------------------------------------------          
    // JFC BUG ?
    //
    // The contents of the ContentPane only seem to show up if you
    // force the frame to resize or if you pack() the frame. I am
    // attempting to find out what the correct behaviour should be.
    //---------------------------------------------------------------
    this.setBounds( 0,0,350,350 ) ;
  }
  
  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCLists JFCListsFrame ;

     JFCListsFrame = new JFCLists( "JFC List test." ) ;

     JFCListsFrame.setSize( 300,300 ) ;
     JFCListsFrame.show() ;
     
     JFCListsFrame.runTests() ;
     
  }
}
