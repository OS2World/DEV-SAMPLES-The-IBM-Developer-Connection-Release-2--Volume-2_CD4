//-----------------------------------------------------------------------------
// JFCLogon.java
//
// A simple example of displaying a simple logon panel where the user is
// prompted to enter a username and a password and then told if the logon
// was successful.
//
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set
// classes (typically called swing.jar) to be in the Classpath.
//
// Developed using Swing ver. 0.6.1 (pre-Beta)
//
// Demonstrates use of these classes/interfaces:
//
//   JOptionPane
//   JPasswordField
//   JTextField
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. Note also that there have been
//               significant changes between the 0.4.1 and 0.5.1 versions.
//
// Author : Kelvin R Lawrence.     8th-December-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;

public class JFCLogon extends JFrame implements ActionListener
{
  private Container client ;

  private JButton logon ;

  private static final String VALID_USERID   = "guest" ;
  private static final String VALID_PASSWORD = "guest" ;

  //-------------------------------------------------------------------------
  // Constructor #1
  //
  //-------------------------------------------------------------------------
  public JFCLogon( String s )
  {
    super( s ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    client = this.getContentPane() ;

    logon = new JButton( "Logon..." ) ;
    logon.addActionListener( this ) ;

    client.setLayout( new BorderLayout() ) ;
    client.add( "North" , logon ) ;
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
    displayLogonPanel() ;
  }

  //-------------------------------------------------------------------------
  // displayLogonPanel()
  //
  // Example of how you might display a logon panel using the JOptionPane and
  // do some simple validation of the data entered.
  //
  //-------------------------------------------------------------------------
  private void displayLogonPanel()
  {
    String [] txt = { "Userid:" , "Password" } ;

    Object [] objs = new Object[4] ;

    int rc ;

    JPasswordField pwd = new JPasswordField(10) ;
    JTextField     uid = new JTextField(10) ;

    pwd.setEchoChar( '*' ) ;

    objs[0] = txt[0] ;
    objs[1] = uid    ;
    objs[2] = txt[1] ;
    objs[3] = pwd    ;

    rc = JOptionPane.showConfirmDialog( null
                                      , objs
                                      , "User verification"
                                      , JOptionPane.OK_CANCEL_OPTION
                                      , JOptionPane.PLAIN_MESSAGE
                                      ) ;

    switch( rc)
    {
      case JOptionPane.OK_OPTION:
      {
        System.out.println( "User selected OK" ) ;

        String user  = uid.getText() ;
        String pword = pwd.getText() ;

        if ( user.equals( VALID_USERID ) && pword.equals(VALID_PASSWORD) )
        {
          JOptionPane.showMessageDialog( null
                                       , "You have successfully logged on."
                                       , "Logon complete"
                                       , JOptionPane.INFORMATION_MESSAGE ) ;
        }
        else
        {
          JOptionPane.showMessageDialog( null
                                       , "The userid and/or password was invalid."
                                       , "Logon unsuccessful"
                                       , JOptionPane.ERROR_MESSAGE ) ;
        }
      }
      break ;

      case JOptionPane.CANCEL_OPTION:
      {
        System.out.println( "User selected Cancel" ) ;
      }
      break ;
    }
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCLogon JFCLogonFrame ;

     JFCLogonFrame = new JFCLogon( "JFC Frame test." ) ;

     JFCLogonFrame.setSize( 300,300 ) ;
     JFCLogonFrame.setBackground( Color.lightGray ) ;
     JFCLogonFrame.show() ;
  }
}

