//-----------------------------------------------------------------------------
// testuser.java
//
// A simple example of using a custom layout manager.
// 
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes:
//
//   ??????
//
// Author : Kelvin R Lawrence.     12th-May-1997
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;

//---------------------------------------------------------------
// Class: testuser
//
// Simple class that experiments with our custom layout manager
// UserLayout.
//
//---------------------------------------------------------------
public class testuser extends Frame
{
  private Button b1 ;
  private Button b2 ;
  private Button b3 ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public testuser( String s )
  {
    super(s);
    
    Dimension d ;

    setLayout( new UserLayout() ) ;
    //setLayout( new FlowLayout() ) ;
    //setLayout( null ) ;

    b1 = new Button( "One" ) ;
    b2 = new Button( "Two" ) ;
    b3 = new Button( "Three" ) ;

    add( b1 ) ;
    add( b2 ) ;
    add( b3 ) ;

    d = minimumSize() ;

    System.out.println( "Test : mimimumSize() ==> " + d.width + "," + d.height ) ;

    d = preferredSize() ;

    System.out.println( "Test : preferredSize() ==> " + d.width + "," + d.height ) ;

    System.out.println( "Calling toString() method ==> " + getLayout().toString() ) ;
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
    // 
    // 
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
     testuser testuserFrame ;

     testuserFrame = new testuser( "UserLayout tests" ) ;

     testuserFrame.resize( 300,300 ) ;
     testuserFrame.show() ;
  }
}





