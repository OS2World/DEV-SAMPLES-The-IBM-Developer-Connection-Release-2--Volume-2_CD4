//-----------------------------------------------------------------------------
// Spiro.java
//
// A simple example of using sine and cosine to draw a series of rotated lines.
//
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   Graphics
//   Math
//   Random
//
// Author : Kelvin R Lawrence.     20th-January-1998
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;
import java.util.* ;

//---------------------------------------------------------------
// Class: Spiro
//
// Simple class that experiments with
//
//---------------------------------------------------------------

public class Spiro extends Frame implements ActionListener
{
  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public Spiro( String s )
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
    Dimension d = getSize() ;
    Insets  ins = getInsets() ;

    Rectangle rectl = new Rectangle() ;

    rectl.x = ins.left ;
    rectl.y = ins.top  ;
    rectl.width = d.width - ins.left - ins.right ;
    rectl.height = d.height - ins.top - ins.bottom ;

    //--------------------------------------
    // Initialise a random number generator.
    //--------------------------------------
    Random rnd = new Random() ;

    int midx = rectl.x + (rectl.width / 2 );
    int midy = rectl.y + ( rectl.height / 2) ;

    double r    = (double) ((double)Math.min(rectl.width,rectl.height)/2)  ;

    //-----------------------------------------------------------------------
    // In a loop draw a series of lines that will be rotated by an increasing
    // number of degrees
    //-----------------------------------------------------------------------
    for ( int angle = 0; angle < 360; angle+=5 )
    {
      //---------------------------------------------------------------
      //Convert angle into into radians as trig functions require that.
      //---------------------------------------------------------------
      double degs = angle * Math.PI/180 ;

      //----------------------------------------------------------------------------
      //Calculate the end point of the line. Note this can be done using simple trig
      //calculations even though we don't have explicit transform matrix functions.
      //----------------------------------------------------------------------------

      int x = midx + (int)(r * Math.cos( degs )) ;
      int y = midy + (int)(r * Math.sin( degs )) ;

      //--------------------------------
      // Choose a somewhat random color.
      //--------------------------------

      g.setColor( new Color( (rnd.nextInt() & 0x0FFFFFFF) % 255
                           , (rnd.nextInt() & 0x0FFFFFFF) % 255
                           , (rnd.nextInt() & 0x0FFFFFFF) % 255 )) ;

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


  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     Spiro SpiroFrame ;

     SpiroFrame = new Spiro( "Testcase : Spiro" ) ;

     SpiroFrame.setSize( 300,300 ) ;
     SpiroFrame.show() ;
  }
}
