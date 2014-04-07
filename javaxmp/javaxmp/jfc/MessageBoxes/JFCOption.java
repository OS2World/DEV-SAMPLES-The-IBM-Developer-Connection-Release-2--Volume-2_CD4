//-----------------------------------------------------------------------------
// JFCOption.java
//
// A simple example of using the new JFC JOptionPane to display popup messages.
//
// This version requires Java JDK level 1.1.2  or higher and the Swing Set 
// classes (typically called swing.jar) to be in the Classpath.
//
// Developed using Swing ver. 0.5.1 (pre-Beta)
//
// Demonstrates use of these classes/interfaces:
//
//   JFrame
//   JOptionPane
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. Note also that there have been
//               significant changes between the 0.4.1 and 0.5.1 versions.
//
// Author : Kelvin R Lawrence.     12th-Nov-1997
//
// History:
//  8-Dec-1997  -KRL-  Tested using Swing 0.6.1.
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;

public class JFCOption extends JFrame implements ActionListener
{
  private Container client ;
  
  private JButton jb1 ;
  private JButton jb2 ;
  private JButton jb3 ;
  private JButton jb4 ;
  
  //-------------------------------------------------------------------------
  // Constructor #1
  //
  //-------------------------------------------------------------------------
  public JFCOption( String s )
  {
    super( s ) ;

    System.out.println( "Constructor called" ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    System.out.println( "Constructor.Frame ==>" + this.getInsets().toString() ) ;

    client = this.getContentPane() ;

    client.setLayout( new FlowLayout() ) ;
    
    client.setBackground( Color.lightGray ) ;
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
    if ( aevt.getSource() instanceof JButton )
    {

      JButton jb = (JButton)aevt.getSource() ;
      
      if ( jb == jb1 )
      {
        JOptionPane.showMessageDialog( null
                                     , "Simple message"
                                     , "Information Dialog"
                                     , JOptionPane.INFORMATION_MESSAGE ) ;  
      }
      else if ( jb == jb2 )
      {
        JOptionPane.showConfirmDialog( null
                                     , "Are you sure ?"
                                     , "Confirmation Dialog"
                                     , JOptionPane.YES_NO_OPTION
                                     , JOptionPane.QUESTION_MESSAGE ) ;  
      }
      else if ( jb == jb3 )   
      {

        JOptionPane.showInputDialog( null
                                   , "Enter some text:" ) ;
      }
      else
      {
        String [] values = { "one", "two", "three", "four", "five" } ;
    
        JOptionPane.showInputDialog( null
                                   , "Please select a value."
                                   , "Selection dialog"
                                   , JOptionPane.PLAIN_MESSAGE
                                   , null
                                   , values
                                   , "one"
                                   ) ;
      }
    }
  }

  //--------------------------------------------------------------------
  // runTests()
  //
  //--------------------------------------------------------------------
  public void runTests()
  {
    System.out.println( "In runTests()" ) ;
    System.out.println( client.toString() ) ;
    Dimension d = client.getSize() ;
    System.out.println( "runTests() ==>" + d.toString() ) ;

    jb1 = new JButton( "Information" ) ;
    jb2 = new JButton( "Confirmation" ) ;
    jb3 = new JButton( "Input" ) ;
    jb4 = new JButton( "Limited input" ) ;
    
    jb1.addActionListener( this ) ;
    jb2.addActionListener( this ) ;
    jb3.addActionListener( this ) ;
    jb4.addActionListener( this ) ;
    
    //-------------------------------------------------------------          
    // JFC BUG ?
    //
    // The contents of the ContentPane only seem to show up if you
    // force the frame to resize or if you pack() the frame. I am
    // attempting to find out what the correct behaviour should be.
    //-------------------------------------------------------------

    client.add( jb1 ) ;
    client.add( jb2 ) ;
    client.add( jb3 ) ;
    client.add( jb4 ) ;

    client.repaint() ;
  }
  
  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCOption JFCOptionFrame ;

     JFCOptionFrame = new JFCOption( "JFC JOptionPane test." ) ;

     JFCOptionFrame.setSize( 400,300 ) ;
     JFCOptionFrame.show() ;
     
     JFCOptionFrame.runTests() ;
     
  }
}
