//-----------------------------------------------------------------------------
// JFCButtons.java
//
// A simple example of using the new JFC JButton class.
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set 
// classes (typically called swing.jar) to be in the Classpath.
//
// Developed using Swing ver. 0.4.1 (pre-Beta)
//
// Demonstrates use of these classes/interfaces:
//
//   JButton
//   ActionListener
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. Note also that there have been
//               significant changes between the 0.4.1 and 0.5.1 versions.
//
// Author : Kelvin R Lawrence.     8th-Nov-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;

public class JFCButtons extends Frame implements ActionListener
{
  public JFCButtons( String s )
  {
    super( s ) ;

    System.out.println( "Constructor called" ) ;

    setLayout( new FlowLayout() ) ;

    JButton jb1 = new JButton( "Button 1" ) ;
    JButton jb2 = new JButton( "Button 2" ) ;
    JButton jb3 = new JButton( "Button 3" ) ;

    jb1.addActionListener( this ) ;
    jb2.addActionListener( this ) ;
    jb3.addActionListener( this ) ;

    add( jb1 ) ;
    add( jb2 ) ;
    add( jb3 ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;
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
      System.out.println( "actionPerformed() got called " ) ;

      JButton jb = (JButton)aevt.getSource() ;

      System.out.println( "Button label ==> " + jb.getText() ) ;

      jb.setText( "Clicked" ) ;
    }
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCButtons JFCButtonsFrame ;

     JFCButtonsFrame = new JFCButtons( "JFC Button test." ) ;

     JFCButtonsFrame.setSize( 300,300 ) ;
     JFCButtonsFrame.show() ;
  }
}
