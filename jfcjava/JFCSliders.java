//-----------------------------------------------------------------------------
// JFCSliders.java
//
// An example of using the JFC Slider class to build a Fahrenheit to Centigrade
// conversion program.
//
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set
// classes (typically called swing.jar) to be in the Classpath.
//
// Developed using Swing ver. 0.7 (pre-Beta)
//
// Demonstrates use of these classes/interfaces:
//
//   Container
//   JCheckBoxMenuItem
//   JFrame
//   JLabel
//   JMenuBar
//   JMenuItem
//   JPanel
//   JSlider
//   TitledBorder
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC.
//
// Author : Kelvin R Lawrence.     9th-Jan-1998
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;
import com.sun.java.swing.border.*;
import com.sun.java.swing.event.* ;

public class JFCSliders extends JFrame implements ActionListener
                                                , SwingConstants
                                                , ChangeListener
{
  private static final int MODE_F2C = 1 ;
  private static final int MODE_C2F = 2 ;

  private static final int FMIN =   0 ;
  private static final int FMAX = 140 ;
  private static final int CMIN = -20 ;
  private static final int CMAX =  60 ;

  private static final Dimension vpad10 = new Dimension( 1,10 ) ;

  int mode = MODE_F2C ;

  private Container client ;

  private JSlider fahrenheit  ;
  private JSlider centigrade ;

  private JLabel  fValue ;
  private JLabel  cValue ;

  private JMenuBar menuBar ;

  private JMenu modeMenu ;

  private JCheckBoxMenuItem mitModeF2C ;
  private JCheckBoxMenuItem mitModeC2F ;

  //-------------------------------------------------------------------------
  // Constructor
  //
  // Create menu bar and sliders and register event listeners.
  //
  //-------------------------------------------------------------------------
  public JFCSliders( String s )
  {
    super( s ) ;

    System.out.println( "Constructor called" ) ;

    JPanel fp ;
    JPanel cp ;

    menuBar  = new JMenuBar() ;
    modeMenu = new JMenu( "Mode" ) ;

    mitModeF2C = new JCheckBoxMenuItem( "Convert Fahrenheit to Centigrade" , true  ) ;
    mitModeC2F = new JCheckBoxMenuItem( "Convert Centigrade to Fahrenheit" , false ) ;

    mitModeF2C.addActionListener( this ) ;
    mitModeC2F.addActionListener( this ) ;

    modeMenu.add( mitModeF2C ) ;
    modeMenu.add( mitModeC2F ) ;

    menuBar.add( modeMenu ) ;

    this.setJMenuBar( menuBar ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    client = this.getContentPane() ;

    //client.setLayout( new BoxLayout( client, BoxLayout.X_AXIS ) ) ;
    client.setLayout( new GridLayout( 1,2 ) ) ;

    fValue = new JLabel( "00000" , CENTER ) ;
    cValue = new JLabel( "00000" , CENTER ) ;

    //-----------------------------------
    // Create the Fahrenheit slider panel.
    //-----------------------------------

    fp = new JPanel() ;
    fp.setBorder( new TitledBorder( "Fahrenheit" ) ) ;
    fp.setLayout( new BoxLayout( fp, BoxLayout.Y_AXIS ) ) ;

    fahrenheit = new JSlider( VERTICAL, FMIN, FMAX, 0 ) ;

    fahrenheit.setMajorTickSpacing(20) ;
    fahrenheit.setMinorTickSpacing(5)  ;
    fahrenheit.setPaintTicks( true )   ;

    fahrenheit.setPaintLabels( true ) ;

    fahrenheit.addChangeListener( this ) ;

    fp.add( fahrenheit ) ;
    fp.add( Box.createRigidArea( vpad10) ) ;
    fp.add( fValue ) ;

    //------------------------------------
    // Create the Centigrade slider panel.
    //------------------------------------

    cp = new JPanel() ;
    cp.setBorder( new TitledBorder( "Centigrade" ) ) ;
    cp.setLayout( new BoxLayout( cp, BoxLayout.Y_AXIS ) ) ;

    centigrade = new JSlider( VERTICAL, CMIN, CMAX, 0 ) ;

    centigrade.setMajorTickSpacing(20) ;
    centigrade.setMinorTickSpacing(5)  ;
    centigrade.setPaintTicks( true )   ;

    centigrade.setPaintLabels( true ) ;

    centigrade.addChangeListener( this ) ;

    centigrade.setEnabled( false ) ;

    cp.add( centigrade ) ;
    cp.add( Box.createRigidArea( vpad10) ) ;
    cp.add( cValue ) ;

    //-----------------------------
    // Add the panels to the frame.
    //-----------------------------

    client.add( fp ) ;
    client.add( cp ) ;

    //----------------------------------------------------------------
    // Force the sliders to initialize themselves to the right values.
    //----------------------------------------------------------------
    //fahrenheit.setValue( FMAX ) ;
  }

  //-------------------------------------------------------------------------
  // stateChanged()
  //
  //-------------------------------------------------------------------------
  public void stateChanged( ChangeEvent ce )
  {
    JSlider js = (JSlider) ce.getSource() ;

    int val = js.getValue() ;

    //System.out.println( "State changed ==> " + val ) ;

    if ( js.equals(fahrenheit) && mode == MODE_F2C )
    {
      fValue.setText( Integer.toString(val) ) ;
      updateCentigradeSlider( val ) ;
    }
    else if ( js.equals(centigrade) && mode == MODE_C2F )
    {
      cValue.setText( Integer.toString(val) ) ;
      updateFahrenheitSlider( val ) ;
    }
  }

  //-------------------------------------------------------------------------
  // updateFahrenheitSlider()
  //
  //-------------------------------------------------------------------------
  private void updateFahrenheitSlider( int c )
  {
    int f ;

    f = (int) ((c * 9) / 5 + 32 + 0.5);

    fValue.setText( Integer.toString( f ) ) ;
    fahrenheit.setValue( f ) ;
  }

  //-------------------------------------------------------------------------
  // updateCentigradeSlider()
  //
  //-------------------------------------------------------------------------
  private void updateCentigradeSlider( int f )
  {
    int c ;

    c = (int) ((f-32) * 5 / 9 + 0.5) ;

    cValue.setText( Integer.toString( c ) ) ;
    centigrade.setValue( c ) ;

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
    if ( aevt.getSource() instanceof JCheckBoxMenuItem )
    {
      System.out.println( "actionPerformed() got called " ) ;

      JCheckBoxMenuItem jc = (JCheckBoxMenuItem) aevt.getSource() ;

      if ( jc.equals( mitModeF2C) )
      {
        mitModeC2F.setState( false ) ;
        this.mode = MODE_F2C ;
        fahrenheit.setEnabled( true ) ;
        centigrade.setEnabled( false ) ;
      }
      else
      {
        mitModeF2C.setState( false ) ;
        this.mode = MODE_C2F ;
        centigrade.setEnabled( true ) ;
        fahrenheit.setEnabled( false ) ;
      }
    }
  }


  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCSliders JFCSlidersFrame ;

     JFCSlidersFrame = new JFCSliders( "JFC Sliders." ) ;

     JFCSlidersFrame.setSize( 300,300 ) ;
     JFCSlidersFrame.setBackground( Color.lightGray ) ;

     JFCSlidersFrame.show() ;
  }
}
