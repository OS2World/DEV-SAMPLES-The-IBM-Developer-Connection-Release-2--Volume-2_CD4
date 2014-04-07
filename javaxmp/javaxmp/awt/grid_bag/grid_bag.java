//-----------------------------------------------------------------------------
// grid_bag.java
//
// A simple example of using the GridBagLayout layout manager to manage
// the laying out of components in a window.
// 
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   GridBagLayout
//   GridBagConstraints
//
// Author : Kelvin R Lawrence.     26-Jun-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: grid_bag
//
// Simple class that experiments with Java AWT menu capabilities.
//
//---------------------------------------------------------------

public class grid_bag extends Frame
{
  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public grid_bag( String s )
  {
    super(s);

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;
  }

  //-------------------------------------------------------------------------
  // paint()
  //
  //-------------------------------------------------------------------------
  public void paint( Graphics g )
  {
    System.out.println( "Paint() called" ) ;
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

  //--------------------------------------------------------------------
  // runTests()
  //
  //--------------------------------------------------------------------
  public void runTests()
  {
    GridBagLayout gridbag = new GridBagLayout() ;

    GridBagConstraints constraints = new GridBagConstraints() ;

    Button [] btns = new Button[10] ;

    setLayout( gridbag ) ;

    for ( int i = 0; i < 10; i++ )
    {
      btns[i] = new Button( Integer.toString(i) ) ;
    }

    constraints.fill = constraints.BOTH ;

    constraints.weightx = 1.0 ;

    addButton( btns[0] , gridbag, constraints ) ;
    addButton( btns[1] , gridbag, constraints) ;
    addButton( btns[2] , gridbag, constraints) ;

    constraints.gridwidth = constraints.REMAINDER ;

    addButton( btns[3] , gridbag, constraints) ;

    constraints.weightx = 0.0 ;

    addButton( btns[4] , gridbag, constraints) ;

    constraints.gridwidth = constraints.RELATIVE ;

    addButton( btns[5] , gridbag, constraints ) ;

    constraints.gridwidth = constraints.REMAINDER ;

    addButton( btns[6], gridbag, constraints ) ; 

    constraints.gridwidth  = 1   ;
    constraints.gridheight = 2   ;
    constraints.weighty    = 1.0 ;

    addButton( btns[7] , gridbag, constraints ) ; 

    constraints.weighty    = 0.0 ;
    constraints.gridwidth  = constraints.REMAINDER ;
    constraints.gridheight = 1 ;

    addButton( btns[8] , gridbag, constraints ) ; 
    addButton( btns[9] , gridbag, constraints ) ; 

    pack() ;
    setSize( 400,140 ) ;
  }

  private void addButton( Button b, GridBagLayout gb, GridBagConstraints gbc )
  {
    gb.setConstraints( b, gbc ) ;
    add( b ) ;
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     grid_bag grid_bagFrame ;

     grid_bagFrame = new grid_bag( "Testcase : grid_bag" ) ;

     grid_bagFrame.setSize( 300,300 ) ;
     grid_bagFrame.show() ;

     grid_bagFrame.runTests() ;
  }
}
