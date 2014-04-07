//-----------------------------------------------------------------------------
// Chisel.java
//
// A simple example of a technique for drawing text in such a way that
// it appears to have been "chiselled" into the window.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   Dimension
//   Graphics
//   Insets
//
// Author : Kelvin R Lawrence.     21st-November-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: Chisel
//
// Simple class that experiments with Java AWT menu capabilities.
//
//---------------------------------------------------------------

public class Chisel extends Frame
{
  private static String txt = "This text should look as if it has " + 
                              "been chiselled into the window." ;

  private static final Color HALFTONE_GRAY = new Color(80,80,80) ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public Chisel( String s )
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
    Dimension d = getSize() ;
    Insets i    = getInsets() ;
    
    g.setColor( Color.lightGray ) ;

    g.fillRect( 0,0,d.width,d.height ) ;

    drawChiselledText( g, txt, i.left+20, i.top+20  ) ;
  }

  //-------------------------------------------------------------------------
  // drawChiselledText
  //
  //-------------------------------------------------------------------------
  public void drawChiselledText( Graphics g, String str, int x, int y )
  {
    g.setColor( Color.white ) ;
    g.drawString( str, x+1, y+1 ) ;
    g.setColor( HALFTONE_GRAY ) ;
    g.drawString( str, x,y ) ;
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

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     Chisel ChiselFrame ;

     ChiselFrame = new Chisel( "Testcase : Chisel" ) ;

     ChiselFrame.setSize( 500,300 ) ;
     ChiselFrame.show() ;
  }
}
