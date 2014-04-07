//-----------------------------------------------------------------------------
// ManyCells.java
//
// A simple test program to test the LEDPanel class.
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
// Class: ManyCells
//
// Simple class that experiments with the LEDPanel class.
//
//---------------------------------------------------------------
public class ManyCells extends Frame implements ActionListener
{
  LEDPanel  display ;
  TextField txt     ;
  
  int       value   ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public ManyCells( String s )
  {
    super(s);

    enableEvents( AWTEvent.WINDOW_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK ) ;

    display  = new LEDPanel( 5 ) ;
    
    value = 12345 ;
                        
    txt = new TextField( "12345" ) ;

    txt.addActionListener( this ) ;
    
    display.setValue( value ) ;

    this.setLayout( new BorderLayout() ) ;

    this.add( "North" , txt     ) ;
    this.add( "Center", display ) ;
  }

  //-----------------------------------------------------------------------
  // paint()
  //
  //-----------------------------------------------------------------------
  public void paint( Graphics g )
  {
    System.out.println( "Paint() called" ) ;

    Dimension d = getSize() ;

    g.setColor( Color.gray ) ;

    g.fillRect( 0,0,d.width,d.height ) ;
  }

  //-----------------------------------------------------------------------
  // actionPerformed()
  //
  // Handle an action for which we have registered interest.
  //
  //-----------------------------------------------------------------------
  public void actionPerformed( ActionEvent aevt )
  {
    System.out.println( "actionPerformed() called ." ) ;
    System.out.println( "Action command string ==> " + aevt.getActionCommand() ) ;

    display.setValue( (Integer.valueOf(aevt.getActionCommand()).intValue() )) ;
  }
  
  //-----------------------------------------------------------------------
  // processEvent()
  //
  // Handle high level events. We need this so that the close in the title 
  // bar will actually close the application etc.
  //-----------------------------------------------------------------------
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

  //-----------------------------------------------------------------------
  // main()
  //
  //-----------------------------------------------------------------------
  static public void main( String[] args )
  {
     ManyCells ManyCellsFrame ;

     ManyCellsFrame = new ManyCells( "Cell" ) ;

     ManyCellsFrame.setSize( 500,150 ) ;
     ManyCellsFrame.show() ;
  }
}


