//-----------------------------------------------------------------------------
// JFCFileChooser.java
//
// A simple example of using the new JFC FileChooser class.
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set
// classes (typically called swing.jar) to be in the Classpath.
//
// Requires JFC v 1.1 (Swing v 1.0).
//
// Demonstrates use of these classes/interfaces:
//
//   FileChooser
//   ChangeListener
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. Note also that there have been
//               significant changes between the 0.4.1 and 0.5.1 versions.
//
// Author : Kelvin R Lawrence.     9th-Nov-1997
//
// History:
//  9-Nov-98  -KRL-  Created it using Swing v 0.5.1.
//  2-Mar-98  -KRL-  Changed for JFC 1.1 (Swing 1.0) release. Now imports from
//                   the com.sun.java.swing.preview package.
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.preview.* ;

public class JFCFileChooser extends Frame implements ActionListener ,
                                                     ChangeListener
{
  private JFileChooser fc     ;
  //private Container    client ;

  public JFCFileChooser( String s )
  {
    super( s ) ;

    System.out.println( "Constructor called" ) ;

    //client = this.getContentPane() ;
    //System.out.println( client.toString() ) ;

    setLayout( new FlowLayout() ) ;

    JButton jb1 = new JButton( "Open" ) ;
    JButton jb2 = new JButton( "Save" ) ;

    jb1.setDoubleBuffered( true ) ;

    jb1.addActionListener( this ) ;
    jb2.addActionListener( this ) ;

    add( jb1 ) ;
    add( jb2 ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    fc = new JFileChooser() ;
  }


  //-------------------------------------------------------------------------
  // stateChanged()
  //
  // Required if implementing the ChangeListener interface.
  //-------------------------------------------------------------------------
  public void stateChanged( ChangeEvent ce )
  {
    System.out.println( "stateChanged() " ) ;
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

      fc.showDialog( this ) ;

    }
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCFileChooser JFCFileChooserFrame ;

     JFCFileChooserFrame = new JFCFileChooser( "JFC Button test." ) ;

     JFCFileChooserFrame.setSize( 300,300 ) ;
     JFCFileChooserFrame.show() ;
  }
}
