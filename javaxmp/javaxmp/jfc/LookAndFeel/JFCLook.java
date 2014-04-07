//-----------------------------------------------------------------------------
// JFCLook.java
//
// A simple example of using the new pluggable look and feel (PLAF) of the JFC
// to dynamically change a program's appearance.
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set
// classes (typically called swing.jar) to be in the Classpath.
//
// Requires Swing ver. 1.0 (JFC 1.1)
//
// Demonstrates use of these classes/interfaces:
//
//   ButtonGroup
//   JButton
//   JFrame
//   JPanel
//   JRadioButton
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. The look and feel area has so far
//               changed in every drop up to and including JFC 1.1. This
//               version has been updated to run on Swing 1.0 (JFC 1.1) in
//               fact having been updated it now requires it to run.
//
//
// Author : Kelvin R Lawrence.     12th-Nov-1997
//
// History:
//  12-Nov-1997 -KRL-  Created it using Swing-0.5.1
//   5-Dec-1997 -KRL-  Updated for version 0.6.1 look and feel changes.
//  27-Dec-1997 -KRL-  Updated for version 0.7 look and feel changes.
//  24-Feb-1998 -KRL-  Updated for changes in Swing-1.0 (JFC 1.1)
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import com.sun.java.swing.plaf.* ;
import com.sun.java.swing.plaf.metal.* ;

public class JFCLook extends JFrame implements ActionListener
{
  private Container client ;

  private JButton jb1 ;

  private JRadioButton jr1 ;
  private JRadioButton jr2 ;
  private JRadioButton jr3 ;
  private JRadioButton jr4 ;
  private JRadioButton jr5 ;
  private JRadioButton jr6 ;
  private JRadioButton jr7 ;

  private ButtonGroup  bg  ;

  //-------------------------------------------------------------------------
  // Constructor #1
  //
  //-------------------------------------------------------------------------
  public JFCLook( String s )
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

  //-------------------------------------------------------------------------
  // actionPerformed()
  //
  // Handle an action for which we have registered interest.
  //
  //-------------------------------------------------------------------------
  public void actionPerformed( ActionEvent aevt )
  {
    String plaf ;

    //-------------------------------------------------------------------
    // If one of the radio buttons is clicked then switch to the selected
    // look and feel. As of 12-Nov-1997 the JFC only supported two unique
    // looks, basic and rose.
    //
    // UPDATE. When Swing-0.6.1 came out the look and feel options had
    //         been changed to a new set of motif, jlf (Java) and Win95.
    //         Updated 5-Dec-1997 to use new look and feel options.
    //-------------------------------------------------------------------
    if ( aevt.getSource() instanceof JRadioButton )
    {
      JRadioButton jr = (JRadioButton)aevt.getSource() ;

      if ( jr == jr1 )
      {
        //plaf = "com.sun.java.swing.motif.MotifLookAndFeel" ;
        //plaf = "com.sun.java.swing.basic.BasicLookAndFeel" ;
        plaf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel" ;
        //plaf = "MyLook" ;
      }
      else if ( jr == jr2 )
      {
        //com.sun.java.swing.jlf.JLFLookAndFeel.setCurrentTheme( new com.sun.java.swing.jlf.JLFDefaultTheme());
        plaf = "com.sun.java.swing.plaf.organic.OrganicLookAndFeel" ;
      }
      else if ( jr == jr3 )
      {
        //com.sun.java.swing.jlf.JLFLookAndFeel.setCurrentTheme( new com.sun.java.swing.jlf.JLFGrayTheme());
        plaf = "com.sun.java.swing.jlf.JLFLookAndFeel" ;
      }
      else if ( jr == jr4 )
      {
        //plaf = "com.sun.java.swing.windows.WindowsLookAndFeel" ;
        plaf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" ;
      }
      else if ( jr == jr5 )
      {
        plaf = "com.sun.java.swing.plaf.mac.MacLookAndFeel" ;
      }
      else if ( jr == jr6 )
      {
        plaf = "com.sun.java.swing.plaf.metal.MetalLookAndFeel" ;
        MetalLookAndFeel.setCurrentTheme( new DefaultMetalTheme() ) ;
      }
      else
      {
        plaf = "com.sun.java.swing.plaf.metal.MetalLookAndFeel" ;
        MetalLookAndFeel.setCurrentTheme( new MyRedMetalTheme() ) ;

      }

      try
      {
        //-----------------------------------------------------------------
        // Set the new look and feel and notify the parent component of the
        // change. I found that some of the components would only update if
        // I forced a paint() to happen as well. JFC BUG ?
        //-----------------------------------------------------------------
        UIManager.setLookAndFeel( plaf ) ;
        SwingUtilities.updateComponentTreeUI( this ) ;
        //SwingUtilities.updateComponentTreeUI(getRootComponent());
        this.repaint() ;
      }
      catch ( UnsupportedLookAndFeelException e )
      {
        System.out.println ("Look & Feel not supported on this platform." );
      }
      catch ( IllegalAccessException e )
      {
        System.out.println ("Look & Feel could not be accessed. " ) ;
      }
      catch ( ClassNotFoundException e )
      {
        System.out.println ("Look & Feel could not found." ) ;
      }
      catch ( InstantiationException e )
      {
        System.out.println ("Look & Feel could not be instantiated." ) ;
      }
      catch ( Exception e )
      {
        System.out.println( "***ERROR: Unexpected exception setting look and feel" ) ;
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

    //---------------------------------
    // Create an internal frame window.
    //---------------------------------

    JInternalFrame jif = new JInternalFrame( "Sample Frame", true, true, true, true ) ;

    client.add( jif ) ;

    Container jifClient = jif.getContentPane() ;

    jif.setSize( 300,400 ) ;

    //----------------------------------------------------------
    // Creat some components and add them to the internal frame.
    //----------------------------------------------------------

    jb1 = new JButton( "JFC Button" ) ;

    jr1 = new JRadioButton( "Motif"   ) ;
    jr2 = new JRadioButton( "Java (Santa Fe)" ) ;
    jr3 = new JRadioButton( "Java (Vancouver)"  ) ;
    jr4 = new JRadioButton( "Windows" ) ;
    jr5 = new JRadioButton( "Mac" ) ;
    jr6 = new JRadioButton( "Metal (default)" ) ;
    jr7 = new JRadioButton( "Metal (Kelvin)" ) ;

    bg  = new ButtonGroup() ;

    bg.add( jr1 ) ;
    bg.add( jr2 ) ;
    bg.add( jr3 ) ;
    bg.add( jr4 ) ;
    bg.add( jr5 ) ;
    bg.add( jr6 ) ;
    bg.add( jr7 ) ;

    jr1.addActionListener( this ) ;
    jr2.addActionListener( this ) ;
    jr3.addActionListener( this ) ;
    jr4.addActionListener( this ) ;
    jr5.addActionListener( this ) ;
    jr6.addActionListener( this ) ;
    jr7.addActionListener( this ) ;

    JPanel jp = new JPanel() ;

    jp.setLayout( new GridLayout( 5,1 ) );

    jr4.setSelected( true ) ;

    //----------------------------------------------------------------------
    // Add the components to the panel before we add it to the content pane.
    //----------------------------------------------------------------------
    jp.add(jb1)  ;
    jp.add(jr1)  ;
    jp.add(jr2)  ;
    jp.add(jr3)  ;
    jp.add(jr4)  ;
    jp.add(jr5)  ;
    jp.add(jr6)  ;
    jp.add(jr7)  ;

    //-------------------------------------------------------------
    // JFC BUG ?
    //
    // The contents of the ContentPane only seem to show up if you
    // force the frame to resize or pack the frame. I am attempting
    // to find out what the correct behaviour should be.
    //-------------------------------------------------------------

    jifClient.add( "Center", jp ) ;
    //pack() ;

    System.out.println( "client ==> " + client.toString() ) ;
    System.out.println( "frame  ==> " + this.toString() ) ;

  }
  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCLook JFCLookFrame ;

     JFCLookFrame = new JFCLook( "JFC Look and Feel test" ) ;

     JFCLookFrame.setSize( 500,400 ) ;
     JFCLookFrame.show() ;

     JFCLookFrame.runTests() ;
  }
}
