//-----------------------------------------------------------------------------
// JFCColorChooser.java
//
// A simple example of using the JFC JColorChooser class.
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set
// classes (typically called swing.jar) to be in the Classpath.
//
//
// Demonstrates use of these classes/interfaces:
//
//   Color
//   JColorChooser
//   JMenu
//   JMenuBar
//   JMenuItem
//
// Requires JFC v 1.1 (Swing v 1.0).
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. Note also that there have been
//               significant changes between the 0.4.1 and 0.5.1 versions.
//
// Author : Kelvin R Lawrence.     3rd-December-1997
//
// History:
//  3-Dec-98  -KRL-  Created using Swing 0.6.1.
//  2-Mar-98  -KRL-  Changed for JFC 1.1 (Swing 1.0) release. Now imports from
//                   the com.sun.java.swing.preview package.
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.preview.*;

public class JFCColorChooser extends JFrame implements ActionListener
{
  private Color col = Color.white ;

  private JMenuBar  menuBar ;
  private JMenu     menu    ;
  private JMenuItem chooser ;

  Container client ;

  //-------------------------------------------------------------------------
  // Constructor #1
  //
  //-------------------------------------------------------------------------
  public JFCColorChooser( String s )
  {
    super( s ) ;

    System.out.println( "Constructor called" ) ;

    client = getContentPane() ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    menuBar = new JMenuBar() ;

    menu   = new JMenu( "Colors" ) ;

    chooser = new JMenuItem( "Change color" ) ;

    chooser.addActionListener( this ) ;

    menu.add( chooser ) ;
    menuBar.add( menu ) ;

    this.setJMenuBar( menuBar ) ;
  }

  //-------------------------------------------------------------------------
  // actionPerformed()
  //
  //-------------------------------------------------------------------------
  public void actionPerformed( ActionEvent aevt )
  {
    chooseNewColor() ;

    client.setBackground( col ) ;

    client.repaint() ;
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
  // chooseNewColor()
  //
  // Choose a new color.
  //--------------------------------------------------------------------
  public void chooseNewColor()
  {
    Color newCol ;

    System.out.println( "*** In chooseNewColor() ***" ) ;

    //-------------------------------------------------------------------
    // Display the color chooser dialog and allow a color to be selected.
    // The selected color will be returned when the user clicks on "OK".
    // If the user clicks on "Cancel" then null should be returned.
    //
    // JFC BUG: Never seems to return from this method unless I call it
    //          in response to an event ?
    //-------------------------------------------------------------------
    newCol = JColorChooser.showDialog( this
                                     , "Please select a color"
                                     , col ) ;

    if ( newCol == null )
    {
      System.out.println( "A color was not chosen" ) ;
    }
    else
    {
      System.out.println( "Color selected was " + newCol.toString() ) ;
      col = newCol ;
    }
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCColorChooser JFCColorChooserFrame ;

     JFCColorChooserFrame = new JFCColorChooser( "JFC color chooser test." ) ;

     JFCColorChooserFrame.setSize( 300,300 ) ;
     JFCColorChooserFrame.show() ;
  }
}

