//-----------------------------------------------------------------------------
// TestPI.java
//
// Simple test harness class for the ProgressIndicator class.
//
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   ??????
//
// Author : Kelvin R Lawrence.     6-Aug-1997
//
// History:
//
//-----------------------------------------------------------------------------
//import krl.java.*;
//import krl.java.ProgressIndicator;

import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: TestPI
//
// Simple class that tests the ProgressIndicator class.
//
//---------------------------------------------------------------

public class TestPI extends Frame
{

  public TestPI( String s )
  {
    super( s ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK ) ;

    setLayout( null ) ;
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

  public void paint( Graphics g )
  {
  }

  //--------------------------------------------------------------------
  // runTests()
  //
  //--------------------------------------------------------------------
  public void runTests()
  {
    ProgressIndicator pi1 ;
    ProgressIndicator pi2 ;
    ProgressIndicator pi3 ;
    ProgressIndicator pi4 ;

    pi1 = new ProgressIndicator( 0 ) ;
    pi2 = new ProgressIndicator( 0 ) ;
    pi3 = new ProgressIndicator( 0 ) ;
    pi4 = new ProgressIndicator( 0 ) ;

    this.add( pi1 ) ;
    this.add( pi2 ) ;
    this.add( pi3 ) ;
    this.add( pi4 ) ;

    pi1.setBounds( 10,100,300,30 ) ;
    pi2.setBounds( 10,200,300,30 ) ;
    pi3.setBounds( 10,300,300,30 ) ;
    pi4.setBounds( 10,400,300,30 ) ;

    //------------------------------------------
    // Now change the colors of the status bars.
    //------------------------------------------

    pi1.setStatusBarColor( Color.red ) ;
    pi2.setStatusBarColor( Color.green ) ;
    pi3.setStatusBarColor( Color.yellow ) ;
    pi4.setStatusBarColor( Color.blue ) ;

    pi1.setStatusBarTextColor( Color.white ) ;
    pi2.setStatusBarTextColor( Color.black ) ;
    pi3.setStatusBarTextColor( Color.black ) ;
    pi4.setStatusBarTextColor( Color.white ) ;


    pi1.setVisible( true ) ;
    pi2.setVisible( true ) ;
    pi3.setVisible( true ) ;
    pi4.setVisible( true ) ;

    try
    {
      //--------------------------
      // First test absolute mode.
      //--------------------------

      pi1.setMode( pi1.VALUES_ABSOLUTE ) ;

      System.out.println( "Testing absolute mode." ) ;

      for ( int j=0; j < 1; j++ )
      {
        for ( int i=0; i<100; i+=2 )
        {
          pi1.setValue( i ) ;
          pi2.setValue( i ) ;
          pi3.setValue( i ) ;
          pi4.setValue( i ) ;

          Thread.sleep(10);
        }
        for ( int i=100; i>=0; i-=2 )
        {
          pi1.setValue( i ) ;
          pi2.setValue( i ) ;
          pi3.setValue( i ) ;
          pi4.setValue( i ) ;

          Thread.sleep(10);
        }
      }

      //--------------------------
      // Now test percentage mode.
      //--------------------------

      System.out.println( "Testing percentage mode." ) ;

      pi1.setMode( pi1.VALUES_PERCENTAGE ) ;

      for ( int j=0; j < 1; j++ )
      {
        for ( int i=0; i<100; i+=2 )
        {
          pi1.setValue( i ) ;
          pi2.setValue( i ) ;
          pi3.setValue( i ) ;
          pi4.setValue( i ) ;

          Thread.sleep(10);
        }
        for ( int i=100; i>=0; i-=2 )
        {
          pi1.setValue( i ) ;
          pi2.setValue( i ) ;
          pi3.setValue( i ) ;
          pi4.setValue( i ) ;

          Thread.sleep(10);
        }
      }

    }
    catch( InterruptedException e )
    {
   
    }

    //--------------------------------------------------
    // Now set some arbitrary values in percentage mode.
    //--------------------------------------------------

    pi1.setValue( 25 ) ;
    pi2.setValue( 50 ) ;
    pi3.setValue( 75 ) ;
    pi4.setValue( 100 ) ;


    //-----------------------------------------------------------
    // Now change the appearance to be raised rather than sunken.
    //-----------------------------------------------------------

    pi1.setAppearance( pi1.APPEARANCE_RAISED ) ;

  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     TestPI PIFrame ;

     PIFrame = new TestPI( "Testcase for ProgressIndicator" ) ;

     PIFrame.setSize( 500,500 ) ;
     PIFrame.show() ;

     PIFrame.runTests() ;
  }
}
