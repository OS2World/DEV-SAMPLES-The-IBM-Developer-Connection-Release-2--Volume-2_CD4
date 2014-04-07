//-----------------------------------------------------------------------------
// JFCToolBar2.java
//
// A simple example of creating a tool bar with graphical images in
// the buttons. This example is a logical next step from the 
// JFCToolBar example.
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set 
// classes (typically called swing.jar) to be in the Classpath.
//
// Developed using Swing ver. 0.5.1 (pre-Beta)
//
// Demonstrates use of these classes/interfaces:
//
//   ImageIcon
//   Insets
//   JButton
//   JFrame
//   JToolBar
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. Note also that there have been
//               significant changes between the 0.4.1 and 0.5.1 versions.
//
// Author : Kelvin R Lawrence.     17th-Nov-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;

public class JFCToolBar2 extends JFrame implements ActionListener
{
  private Container client ;
  
  private JButton cut   ;
  private JButton copy  ;
  private JButton paste ;
  private JButton open  ;
  private JButton save  ;
  private JButton print ;
  private JButton help  ;

  private JToolBar tb ;
  
  private ImageIcon imgOpen  ;
  private ImageIcon imgSave  ;
  private ImageIcon imgCut   ;
  private ImageIcon imgPaste ;
  private ImageIcon imgCopy  ;
  private ImageIcon imgPrint ;
  private ImageIcon imgHelp  ;
  
  //-------------------------------------------------------------------------
  // Constructor #1
  //
  //-------------------------------------------------------------------------
  public JFCToolBar2( String s )
  {
    super( s ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    client = this.getContentPane() ;

    tb = new JToolBar() ;

    //------------------------------------------------------------
    // Load the images we are going to use in our toolbar buttons.
    //------------------------------------------------------------
    imgOpen  = new ImageIcon( "../../images/fopen.gif" , "Open"  ) ;
    imgSave  = new ImageIcon( "../../images/fsave.gif" , "Save"  ) ;
    imgCut   = new ImageIcon( "../../images/cut.gif"   , "Cut"   ) ;
    imgCopy  = new ImageIcon( "../../images/copy.gif"  , "Copy"  ) ;
    imgPaste = new ImageIcon( "../../images/paste.gif" , "Paste" ) ;
    imgPrint = new ImageIcon( "../../images/print.gif" , "Print" ) ;
    imgHelp  = new ImageIcon( "../../images/help.gif"  , "Help"  ) ;
    
    //------------------------------------------------------------
    // Add the buttons to the toolbar, note we get back a new
    // JButton object from the toolbar.
    //------------------------------------------------------------  
    open  = (JButton) tb.add( new JButton( imgOpen  ) ) ;
    save  = (JButton) tb.add( new JButton( imgSave  ) ) ;
    cut   = (JButton) tb.add( new JButton( imgCut   ) ) ;
    copy  = (JButton) tb.add( new JButton( imgCopy  ) ) ;
    paste = (JButton) tb.add( new JButton( imgPaste ) ) ;
    print = (JButton) tb.add( new JButton( imgPrint ) ) ;
    help  = (JButton) tb.add( new JButton( imgHelp  ) ) ;

    //------------------------------------------------------------
    // Finally set the hover help text that will appear if the
    // mouse pointer is left over the button without clicking it.
    //------------------------------------------------------------
    open.setToolTipText( "Open file" ) ;
    save.setToolTipText( "Save file" ) ;
    cut.setToolTipText( "Cut" ) ;
    copy.setToolTipText( "Copy" ) ;
    paste.setToolTipText( "Paste" ) ;
    print.setToolTipText( "Print" ) ;
    help.setToolTipText( "Help" ) ;

    //------------------------------------------------------------    
    // Now size the button so it is just big enough to contain the
    // bitmap and no more. If we did not do this the button would
    // be larger with the small bitmap centered in the middle.
    //------------------------------------------------------------    
    
    Insets zeroMargin = new Insets( 0,0,0,0 ) ;
    
    open.setMargin( zeroMargin );
    save.setMargin( zeroMargin ); 
    cut.setMargin( zeroMargin );         
    copy.setMargin( zeroMargin );         
    paste.setMargin( zeroMargin );         
    print.setMargin( zeroMargin );         
    help.setMargin( zeroMargin );         
    
    open.addActionListener( this ) ;
            
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
     JFCToolBar2 JFCToolBar2Frame ;

     JFCToolBar2Frame = new JFCToolBar2( "JFC Tool Bar test." ) ;

     JFCToolBar2Frame.setSize( 300,300 ) ;
     
     JFCToolBar2Frame.setBackground( Color.lightGray ) ;
     
     JFCToolBar2Frame.show() ;
  }
}

