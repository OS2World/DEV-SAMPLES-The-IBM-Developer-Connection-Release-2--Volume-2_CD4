//-----------------------------------------------------------------------------
// JFCToolBar.java
//
// A simple example of creating a tool bar using the new JFC classes.
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set 
// classes (typically called swing.jar) to be in the Classpath.
//
// Developed using Swing ver. 0.5.1 (pre-Beta)
//
// See also: JFCToolbar2.java
//
// Demonstrates use of these classes/interfaces:
//
//   JFrame
//   JToolBar
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. Note also that there have been
//               significant changes between the 0.4.1 and 0.5.1 versions.
//
// Author : Kelvin R Lawrence.     15th-Nov-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;

public class JFCToolBar extends JFrame implements ActionListener
{
  private Container client ;
  
  private JButton jb1 ;
  private JButton jb2 ;
  private JButton jb3 ;
  private JButton jb4 ;
  private JButton jb5 ;

  private JToolBar tb ;
  
  //-------------------------------------------------------------------------
  // Constructor #1
  //
  //-------------------------------------------------------------------------
  public JFCToolBar( String s )
  {
    super( s ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    client = this.getContentPane() ;

    tb = new JToolBar() ;

    jb1 = (JButton) tb.add( new JButton( "1" ) ) ;
    jb2 = (JButton) tb.add( new JButton( "2" ) ) ;
    jb3 = (JButton) tb.add( new JButton( "3" ) ) ;
    jb4 = (JButton) tb.add( new JButton( "4" ) ) ;
    jb5 = (JButton) tb.add( new JButton( "5" ) ) ;

    jb1.setToolTipText( "Button 1" ) ;
    jb2.setToolTipText( "Button 2" ) ;
    jb3.setToolTipText( "Button 3" ) ;
    jb4.setToolTipText( "Button 4" ) ;
    jb5.setToolTipText( "Button 5" ) ;

    client.add( "North", tb ) ;
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
     JFCToolBar JFCToolBarFrame ;

     JFCToolBarFrame = new JFCToolBar( "JFC Tool Bar test." ) ;

     JFCToolBarFrame.setSize( 300,300 ) ;
     JFCToolBarFrame.show() ;
  }
}

