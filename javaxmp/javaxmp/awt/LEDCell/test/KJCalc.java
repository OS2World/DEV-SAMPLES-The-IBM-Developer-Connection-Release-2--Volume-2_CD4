//-----------------------------------------------------------------------------
// KJCalc.java
//
// A simple example of ... 
// 
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   ??????
//
// Author : Kelvin R Lawrence.     6-Oct-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: KJCalc
//
// Simple class that experiments with ...
//
//---------------------------------------------------------------

public class KJCalc extends Frame implements ActionListener
{
  Panel  keypad ;

  Button one   ;
  Button two   ;
  Button three ;
  Button four  ;
  Button five  ;
  Button six   ;
  Button seven ;
  Button eight ;
  Button nine  ;
  Button zero  ;

  TextField display ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public KJCalc( String s )
  {
    super(s);

    enableEvents( AWTEvent.WINDOW_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK ) ;

    one   = new Button( "1" ) ;
    two   = new Button( "2" ) ;
    three = new Button( "3" ) ;
    four  = new Button( "4" ) ;
    five  = new Button( "5" ) ;
    six   = new Button( "6" ) ;
    seven = new Button( "7" ) ;
    eight = new Button( "8" ) ;
    nine  = new Button( "9" ) ;
    zero  = new Button( "0" ) ;

    keypad = new Panel() ;

    keypad.setLayout( new GridLayout( 4,3 ) ) ;

    keypad.add( one   ) ;
    keypad.add( two   ) ;
    keypad.add( three ) ;
    keypad.add( four  ) ;
    keypad.add( five  ) ;
    keypad.add( six   ) ;
    keypad.add( seven ) ;
    keypad.add( eight ) ;
    keypad.add( nine  ) ;
    keypad.add( zero  ) ;


    this.setLayout( new BorderLayout() ) ;

    //this.add( "Center", keypad )  ;
    this.add( "North", keypad )  ;

    LEDPanel temp = new LEDPanel() ;
    temp.setSize( 100,100 ) ;
    this.add( "Center" , temp ) ;
  }

  //-------------------------------------------------------------------------
  // paint()
  //
  //-------------------------------------------------------------------------
  public void paint( Graphics g )
  {
    System.out.println( "Paint() called" ) ;

    Dimension d = getSize() ;

    g.setColor( Color.gray ) ;

    g.fillRect( 0,0,d.width,d.height ) ;
  }

  //-------------------------------------------------------------------------
  // processEvent()
  //
  // Handle high level events. We need this so that the close in the title bar 
  // will actually close the application etc.
  //-------------------------------------------------------------------------
  public void processEvent( AWTEvent evt )
  {
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
    System.out.println( "actionPerformed() called ." ) ;
    System.out.println( "Action command string ==> " + aevt.getActionCommand() ) ;
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     KJCalc KJCalcFrame ;

     KJCalcFrame = new KJCalc( "Testcase : KJCalc" ) ;

     KJCalcFrame.setSize( 300,300 ) ;
     KJCalcFrame.show() ;
  }
}
