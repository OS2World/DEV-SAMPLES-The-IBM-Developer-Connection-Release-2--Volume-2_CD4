//-----------------------------------------------------------------------------
// ClockFace.java
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
// Author : Kelvin R Lawrence.     19th-January-1998
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: ClockFace
//
// Simple class that experiments with
//
//---------------------------------------------------------------

public class ClockFace extends Frame implements ActionListener
{

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public ClockFace( String s )
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
    Insets  ins = getInsets() ;

    Rectangle rectl = new Rectangle() ;

    rectl.x = ins.left ;
    rectl.y = ins.top  ;
    rectl.width = d.width - ins.left - ins.right ;
    rectl.height = d.height - ins.top - ins.bottom ;

    g.setColor( Color.black ) ;

    //g.fillRect( 0,0,d.width,d.height ) ;

    g.drawOval( rectl.x, rectl.y, rectl.width, rectl.height ) ;

    int midx = rectl.x + (rectl.width / 2 );
    int midy = rectl.y + ( rectl.height / 2) ;

    double r    = (double) ((double)rectl.width/2) - 10 ;

    //for ( int angle = 0; angle < 360; angle++ )
    for ( int angle = 0; angle < 360; angle++ )
    {
      //Convert angle into into radians as trig functions require that.
      double degs = angle * Math.PI/180 ;
      int x = midx + (int)(r * Math.cos( degs )) ;
      int y = midy + (int)(r * Math.sin( degs )) ;

      System.out.println( " midx = " + midx ) ;
      System.out.println( " midy = " + midy ) ;
      System.out.println( " x = " + x ) ;
      System.out.println( " y = " + y ) ;
      System.out.println( " r = " + r ) ;
      g.drawLine( midx,midy,x,y ) ;
    }
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
     ClockFace ClockFaceFrame ;

     ClockFaceFrame = new ClockFace( "Testcase : ClockFace" ) ;

     ClockFaceFrame.setSize( 300,300 ) ;
     ClockFaceFrame.show() ;
  }
}
