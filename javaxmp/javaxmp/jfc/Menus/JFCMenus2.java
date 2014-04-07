//-----------------------------------------------------------------------------
// JFCMenus2.java
//
// An progression from the JFCMenus example that goes one step further and
// adds icons to the file and edit menus.
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set 
// classes (typically called swing.jar) to be in the Classpath.
//
// Developed using Swing ver. 0.5.1 (pre-Beta)
//
// Demonstrates use of these classes/interfaces:
//
//   ImageIcon
//   JCheckBoxMenuItem
//   JFrame
//   JMenu
//   JMenuBar
//   JMenuItem
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. Note also that there have been
//               significant changes between the 0.4.1 and 0.5.1 versions.
//
// Author : Kelvin R Lawrence.     14th-Nov-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;

public class JFCMenus2 extends JFrame implements ActionListener
{
  private Container client ;
  
  private JMenuBar  menuBar  ;
  
  private JMenu     fileMenu ;
  private JMenu     editMenu ;
  private JMenu     tearMenu ;
  private JMenu     chekMenu ;
  private JMenu     subMenu  ;

  private JMenuItem mitOpen  ;
  private JMenuItem mitSave  ;
  private JMenuItem mitPrint ;

  private JMenuItem mitCopy  ;
  private JMenuItem mitCut   ;
  private JMenuItem mitPaste ;
  
  private JMenuItem mitTear1 ;
  private JMenuItem mitTear2 ;
  private JMenuItem mitTear3 ;

  private JMenuItem mitOne   ;
  private JMenuItem mitTwo   ;
  private JMenuItem mitThree ;
  private JMenuItem mitFour  ;
           
  private JCheckBoxMenuItem mitChk1 ;
  private JCheckBoxMenuItem mitChk2 ;
  private JCheckBoxMenuItem mitChk3 ;

  private ImageIcon imgOpen  ;
  private ImageIcon imgSave  ;
  private ImageIcon imgCut   ;
  private ImageIcon imgPaste ;
  private ImageIcon imgCopy  ;
  private ImageIcon imgPrint ;

  //-------------------------------------------------------------------------
  // Constructor #1
  //
  //-------------------------------------------------------------------------
  public JFCMenus2( String s )
  {
    super( s ) ;

    System.out.println( "Constructor called" ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    client = this.getContentPane() ;

    menuBar = new JMenuBar() ;

    fileMenu = new JMenu( "File" ) ;
    editMenu = new JMenu( "Edit" ) ;
    chekMenu = new JMenu( "Check marks" ) ;
    subMenu  = new JMenu( "More" ) ;
    
    //----------------------------------
    // Create a tear off menu. 
    // Not implemented as of Swing 0.5.1
    //----------------------------------
    tearMenu = new JMenu( "Tear off" , true ) ;

    //------------------------------------------------------------
    // Load the images we are going to use in some of our menus.
    //------------------------------------------------------------
    imgOpen  = new ImageIcon( "../../images/fopen.gif" , "Open"  ) ;
    imgSave  = new ImageIcon( "../../images/fsave.gif" , "Save"  ) ;
    imgCut   = new ImageIcon( "../../images/cut.gif"   , "Cut"   ) ;
    imgCopy  = new ImageIcon( "../../images/copy.gif"  , "Copy"  ) ;
    imgPaste = new ImageIcon( "../../images/paste.gif" , "Paste" ) ;
    imgPrint = new ImageIcon( "../../images/print.gif" , "Print" ) ;

    mitOpen  = new JMenuItem( "Open" , imgOpen  ) ;
    mitSave  = new JMenuItem( "Save" , imgSave  ) ;
    mitPrint = new JMenuItem( "Exit" , imgPrint ) ;

    mitCopy  = new JMenuItem( "Copy"  , imgCopy  ) ;
    mitCut   = new JMenuItem( "Cut"   , imgCut   ) ;
    mitPaste = new JMenuItem( "Paste" , imgPaste ) ;
    
    mitOpen.setHorizontalTextPosition(JButton.RIGHT);
    mitSave.setHorizontalTextPosition(JButton.RIGHT); 
    mitPrint.setHorizontalTextPosition(JButton.RIGHT);  
    mitCopy.setHorizontalTextPosition(JButton.RIGHT);   
    mitCut.setHorizontalTextPosition(JButton.RIGHT);    
    mitPaste.setHorizontalTextPosition(JButton.RIGHT);  

    mitChk1  = new JCheckBoxMenuItem( "Choice 1" ) ;
    mitChk2  = new JCheckBoxMenuItem( "Choice 2" ) ;
    mitChk3  = new JCheckBoxMenuItem( "Choice 3" ) ;
    
    mitTear1 = new JMenuItem( "Tear" ) ;
    mitTear2 = new JMenuItem( "Me"   ) ;
    mitTear3 = new JMenuItem( "Off"  ) ;
    
    mitOne   = new JMenuItem( "One"   ) ;
    mitTwo   = new JMenuItem( "Two"   ) ;
    mitThree = new JMenuItem( "Three" ) ;
    mitFour  = new JMenuItem( "Four"  ) ;

    fileMenu.add( mitOpen ) ;
    fileMenu.add( mitSave ) ;
    fileMenu.addSeparator() ;
    fileMenu.add( mitPrint ) ;

    editMenu.add( mitCopy ) ;
    editMenu.add( mitCut  ) ;
    editMenu.add( mitPaste) ;
    
    chekMenu.add( mitChk1 ) ;
    chekMenu.add( mitChk2 ) ;
    chekMenu.add( mitChk3 ) ;
                              
    subMenu.add( mitOne ) ;
    subMenu.add( mitTwo ) ;
    subMenu.add( mitThree ) ;
    subMenu.add( mitFour ) ;
    
    tearMenu.add( mitTear1 ) ;
    tearMenu.add( mitTear2 ) ;
    tearMenu.add( mitTear3 ) ;
    tearMenu.add( subMenu  ) ;

    menuBar.add( fileMenu ) ;
    menuBar.add( editMenu ) ;
    menuBar.add( chekMenu ) ;
    menuBar.add( tearMenu ) ;

    this.setJMenuBar( menuBar ) ;

    mitCopy.addActionListener( this ) ;
    mitCut.addActionListener( this ) ;
    mitPaste.addActionListener( this ) ;

    mitOpen.addActionListener( this ) ;
    mitSave.addActionListener( this ) ;
    mitPrint.addActionListener( this ) ;
    
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
    if ( aevt.getSource() instanceof JMenuItem )
    {
      System.out.println( "actionPerformed() got called " ) ;
    }
  }

  
  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCMenus2 JFCMenus2Frame ;

     JFCMenus2Frame = new JFCMenus2( "JFC Menus test 2." ) ;

     JFCMenus2Frame.setSize( 300,300 ) ;
     JFCMenus2Frame.setBackground( Color.lightGray ) ;
     
     JFCMenus2Frame.show() ;
  }
}
