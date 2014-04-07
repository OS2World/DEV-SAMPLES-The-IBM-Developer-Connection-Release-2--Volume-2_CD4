//-----------------------------------------------------------------------------
// Timer.java
//
// A simple timer implementation.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   ??????
//
// Author : Kelvin R Lawrence.     21-Oct-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: Timer
//
// Simple timer class that will setup a timer using a secondary
// thread and notify the caller each time the timer "ticks".
//---------------------------------------------------------------
class Timer extends Thread
{
  SimpleTimer client ;

  boolean debugMode = false ;

  //--------------------------------------------------------------------
  //
  // Constructor
  //
  // Caller passes us an object that is implementing the SimpleTimer 
  // interface and then notifies that object when the timer goes off by 
  // calling the timerNotify() method of that class.
  //--------------------------------------------------------------------
  Timer( SimpleTimer t, boolean dbg  )
  {
    client = t ;

    debugMode = dbg ;
  }

  //--------------------------------------------------------------------
  // run()
  //
  // This method is automatically called when a new Timer object is
  // created as the Timer class extends Thread. In this example we
  // set ourselves up to generate timer "ticks" at one second intervals.
  //--------------------------------------------------------------------
  public void run()
  {
    try
    {
      if ( debugMode )
      {
        System.out.println( "Timer: Timer thread is running" ) ;
      }

      while( 1 == 1)
      {
        sleep( 1000 ) ;
        
        if ( debugMode )
        {
          System.out.println( "Timer: Tick-Tock" ) ;
        }
        
        client.timerNotify() ;
      }
    }
    catch( InterruptedException e )
    {
      return ;
    }
  }
}

