//-----------------------------------------------------------------------------
// xxxxxx.java
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
// Author : Kelvin R Lawrence.     dddddd
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: xxxxxx
//
// Simple class that experiments with Java AWT menu capabilities.
//
//---------------------------------------------------------------

public class xxxxxx extends Frame implements ActionListener
{
  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public xxxxxx( String s )
  {
    super(s);

    enableEvents( AWTEvent.WINDOW_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK ) ;
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
    System.out.println( "actionPerformed() got called for a menu item." ) ;
    System.out.println( "Action command string ==> " + aevt.getActionCommand() ) ;
  }

  //-------------------------------------------------------------------------
  // processMouseEvent()
  //
  // Process mouse events. Notice that mouse move and drag events are
  // not sent here. They go to the processMouseMoveEvent() method if there
  // is one.
  //
  // In this particular case we only do something with the MOUSE_CLICKED
  // event. The event ID will be one of:
  //
  //       MOUSE_CLICKED
  //       MOUSE_PRESSED
  //       MOUSE_RELEASED
  //       MOUSE_ENTERED
  //       MOUSE_EXITED
  //
  //-------------------------------------------------------------------------
  public void processMouseEvent( MouseEvent mevt )
  {
    switch( mevt.getID() )
    {
      case MouseEvent.MOUSE_CLICKED:
      {
        System.out.println( "MOUSE_CLICKED event." ) ;
      }
    }
  }


  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     xxxxxx xxxxxxFrame ;

     xxxxxxFrame = new xxxxxx( "Testcase : xxxxxx" ) ;

     xxxxxxFrame.setSize( 300,300 ) ;
     xxxxxxFrame.show() ;
  }
}
