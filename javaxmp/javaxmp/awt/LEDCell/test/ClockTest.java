//-----------------------------------------------------------------------------
// ClockTest.java
//
// A simple test program to test the LEDClockPanel class by implementing
// a simple ticking ClockTest.
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
import java.util.* ;

//---------------------------------------------------------------
// Class: ClockTest
//
// Simple class that experiments with the LEDPanel class.
//
//---------------------------------------------------------------
public class ClockTest extends Frame implements SimpleTimer
{
  LEDClockPanel clockPanel ;

  Timer    timerThread ;
  int      count       ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public ClockTest( String s )
  {
    super(s);

    enableEvents( AWTEvent.WINDOW_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK ) ;

    clockPanel  = new LEDClockPanel() ;

    //this.setLayout( new GridLayout( 1,1 ) ) ;
    this.setLayout( new BorderLayout() );

    this.add( "Center", clockPanel ) ;

    //------------------------------------------------------------
    // Force the frame to scale all components up to the max, thus
    // filling the frame.
    //------------------------------------------------------------
    this.pack() ;

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
     Date date = new Date() ;

     int h ; // current hours
     int m ; // current minutes
     int s ; // current seconds

     h  = date.getHours()   ;
     m  = date.getMinutes() ;
     s  = date.getSeconds() ;

    clockPanel.setTime( h,m,s ) ;
  }

  //-----------------------------------------------------------------------
  // paint()
  //
  //-----------------------------------------------------------------------
  public void paint( Graphics g )
  {
    System.out.println( "ClockTest:: paint() called" ) ;
    System.out.println( getSize().width + " " + getSize().height ) ;
    Dimension d = clockPanel.getSize() ;
    System.out.println( d.width + " " + d.height ) ;
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
     ClockTest ClockTestFrame ;

     ClockTestFrame = new ClockTest( "Simple clock" ) ;

     ClockTestFrame.setSize( 500,150 ) ;
     ClockTestFrame.show() ;
  }
}



