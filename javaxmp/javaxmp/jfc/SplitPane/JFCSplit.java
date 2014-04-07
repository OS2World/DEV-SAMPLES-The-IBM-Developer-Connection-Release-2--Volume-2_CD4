//-----------------------------------------------------------------------------
// JFCSplit.java
//
// A simple example of using the JFC split pane class.
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set 
// classes (typically called swing.jar) to be in the Classpath.
//
// Developed using Swing ver. 0.5.1 (pre-Beta)
//
// Demonstrates use of these classes/interfaces:
//
//   Button
//   GridLayout
//   JFrame
//   JSplitPane
//   Panel
//   
//   
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. Note also that there have been
//               significant changes between the 0.4.1 and 0.5.1 versions.
//
// Author : Kelvin R Lawrence.     13th-Nov-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;

public class JFCSplit extends JFrame 
{
  
  private Container client ;
  
  private JSplitPane js ;
  
  //-------------------------------------------------------------------------
  // Constructor #1
  //
  //-------------------------------------------------------------------------
  public JFCSplit( String s )
  {
    super( s ) ;

    System.out.println( "Constructor called" ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    client = this.getContentPane() ;
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

  
  //--------------------------------------------------------------------
  // runTests()
  //
  //--------------------------------------------------------------------
  public void runTests()
  {
    System.out.println( "*** In runTests() ***" ) ;
    
    //------------------------------------------------------
    // Create a new split pane with side by side components.
    //------------------------------------------------------
    
    js = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT ) ;
    //js = new JSplitPane( ) ;
    
    //-------------------------------------------------------------
    // Create two panels and add some buttons to the panels then
    // add each panel to the split pane. Finally add the split
    // pane to the content pane (client) window.
    //-------------------------------------------------------------
    
    Panel p1 = new Panel();
    Panel p2 = new Panel();
    
    p1.setLayout( new GridLayout( 2,2 ) ) ;
    p2.setLayout( new GridLayout( 2,1 ) ) ;
    
    p1.add( new Button( "One"   )) ;
    p1.add( new Button( "Two"   )) ;
    p1.add( new Button( "Three" )) ;
    p1.add( new Button( "Four"  )) ;
    
    p2.add( new Button( "Five"  )) ;
    p2.add( new Button( "Six "  )) ;
    
    //------------------------------------------------------------------             
    // Add to the split pane. Note that top and left are the same as are 
    // bottom and right, depending on the way the split pane is divided.
    //------------------------------------------------------------------             
    js.setBottomComponent( p2 ) ;
    js.setTopComponent( p1 ) ;                         

    client.add( "Center", js) ;
    
    //-------------------------------------------------------------          
    // JFC BUG ?
    //
    // The contents of the ContentPane only seem to show up if you
    // force the frame to resize or if you pack() the frame. I am
    // attempting to find out what the correct behaviour should be.
    //-------------------------------------------------------------
    this.setBounds( 0,0,350,350 ) ;
    //this.pack() ;
  }
  
  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCSplit JFCSplitFrame ;

     JFCSplitFrame = new JFCSplit( "JFC Split Pane test." ) ;

     JFCSplitFrame.setSize( 300,300 ) ;
     JFCSplitFrame.show() ;
     
     JFCSplitFrame.runTests() ;
     
  }
}
