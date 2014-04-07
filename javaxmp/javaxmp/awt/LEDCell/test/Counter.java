//-----------------------------------------------------------------------------
// Counter.java
//
// A simple test program to test the LEDPanel class by implementing
// a simple ticking counter.
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
// Class: Counter
//
// Simple class that experiments with the LEDPanel class.
//
//---------------------------------------------------------------
public class Counter extends Frame implements SimpleTimer
{
  LEDPanel display     ;
  Timer    timerThread ;
  int      count       ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public Counter( String s )
  {
    super(s);

    enableEvents( AWTEvent.WINDOW_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK ) ;

    display  = new LEDPanel( 5 ) ;


    this.setLayout( new GridLayout( 1,1 ) ) ;

    this.add( display ) ;
    
    count = 0 ;
    
    //---------------------------------------------------------------------
    // Kick off the timer thread which will notify us, at one second
    // intervals timerNotify() method.
    //---------------------------------------------------------------------
    
    timerThread = new Timer( this, true ) ;
    timerThread.start() ;
  }

  //--------------------------------------------------------------------
  // timerNotify()
  //
  // This method is called by the Timer object each time the timer
  // goes off to tell us that the timer has "ticked".
  //--------------------------------------------------------------------
  public void timerNotify()
  {
    count++ ;
    
    display.setValue( count ) ;
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
     Counter CounterFrame ;

     CounterFrame = new Counter( "Cell" ) ;

     CounterFrame.setSize( 500,150 ) ;
     CounterFrame.show() ;
  }
}



