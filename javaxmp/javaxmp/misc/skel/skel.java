//-----------------------------------------------------------------------------
// xxxxxx.java
//
// A simple example of ... 
// 
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes:
//
//   ??????
//
// Author : Kelvin R Lawrence.     dddddd
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;

//---------------------------------------------------------------
// Class: xxxxxx
//
// Simple class that experiments with Java AWT menu capabilities.
//
//---------------------------------------------------------------

public class xxxxxx extends Frame
{

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public xxxxxx( String s )
  {
    super(s);

    setLayout( new FlowLayout() ) ;
  }

  //--------------------------------------------------------------------
  // action()
  //
  // Process user actions.
  //
  //--------------------------------------------------------------------
  public boolean action( Event evt, Object what )
  {

    //-----------------------------------------------------------------
    //
    // Handle ACTION_EVENT events.
    // 
    //-----------------------------------------------------------------
    if ( evt.id == Event.ACTION_EVENT )
    {
      System.out.println( "action() method got called" ) ;
    }

    return true ;
  }

  //-------------------------------------------------------------------------
  // handleEvent()
  //
  // Handle events. We need this so that the close in the title bar will
  // actually close the application etc.
  //-------------------------------------------------------------------------
  public boolean handleEvent( Event evt )
  {
    if ( evt.id == Event.WINDOW_DESTROY)
    {
      System.exit(0) ;
      return true ;
    }
    else
    {
      return( super.handleEvent( evt ) );
    }
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     xxxxxx xxxxxxFrame ;

     xxxxxxFrame = new xxxxxx( "Choice Tests" ) ;

     xxxxxxFrame.resize( 300,300 ) ;
     xxxxxxFrame.show() ;
  }
}




