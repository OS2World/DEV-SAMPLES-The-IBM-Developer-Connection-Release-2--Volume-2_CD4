//-----------------------------------------------------------------------------
// test2.java
//
// Simple test class to test the FontDialog class when used from a
// stand alone java program.
//
//
// Author : Kelvin R Lawrence.     11th-December-1996
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;

//---------------------------------------------------------------
// Class: test2
//
//---------------------------------------------------------------
public class test2 extends Frame implements FontDialogClient
{
  FontDialog fd ;

  public test2( String s )
  {
    super( s ) ;
  }

  //-------------------------------------------------------------------------
  // fontDialogNotify()
  //
  // Wil get called when font dialog needs to notify us of an event.
  //-------------------------------------------------------------------------
  public void fontDialogNotify( int okcancel )
  {
    System.out.println( "fontDialogNotify() called with okcancel = " + okcancel ) ;

    FontMetrics fm ;

    String name  = fd.getFontName()   ;
    int    size  = fd.getFontSize()   ;
    int    style = fd.getFontStyle()  ;

    System.out.println( "Font selection information follows" ) ;
    System.out.println( "----------------------------------" ) ;
    System.out.println( "getFontName()  ==> " + name  ) ;
    System.out.println( "getFontSize()  ==> " + size  ) ;
    System.out.println( "getFontStyle() ==> " + style ) ;

    // If frame is null here causes JIT trap ??.
    Graphics g = this.getGraphics() ;

    Font fnt   = new Font( name, style, size ) ;

    int x=0, y=0;

    g.setFont( fnt ) ;

    for ( int i=0; i<20; i++)
    {
      g.drawString( "abcdfeghijklmnopqrstuvwxyz", x,y ) ;
      y += 20 ;
    }

    fm = g.getFontMetrics() ;

    System.out.println( "Font metrics information follows" ) ;
    System.out.println( "--------------------------------" ) ;

    System.out.println( "Height =======> " + fm.getHeight() ) ;
    System.out.println( "Leading ======> " + fm.getLeading() ) ;
    System.out.println( "Ascent =======> " + fm.getAscent() ) ;
    System.out.println( "Descent=======> " + fm.getDescent() ) ;
    System.out.println( "Max ascent  ==> " + fm.getMaxAscent() ) ;
    System.out.println( "Max descent ==> " + fm.getMaxDescent() ) ;
    System.out.println( "Max advance ==> " + fm.getMaxAdvance() ) ;

    //System.out.println( fm.toString() ) ;
  }

  //-------------------------------------------------------------------------
  // runTests()
  //
  //-------------------------------------------------------------------------
  public void runTests()
  {

    fd = new FontDialog( this, "Select a font...", false ) ;

    //------------------------------------------------------------------
    // Tell the font dialog that we want it to call our fontDialogNotify
    // method when the user dismisses the dialog.
    //------------------------------------------------------------------

    fd.setClient( this ) ;

    //--------------------
    // Display the dialog.
    //--------------------

    fd.show() ;

    System.out.println( "Back from initiating dialog display" ) ;
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


  //-----------------------------------------------------------------------
  // main()
  //
  // Program entry point.
  //
  // Make some calls to test out the FontDialog class
  //-----------------------------------------------------------------------
  static public void main( String[] args )
  {
    test2 t ;

    t = new test2( "Testing the font dialog" ) ;

    t.resize( 400,400 );

    t.show() ;

    t.runTests() ;

    System.out.println( "In main() again after launching tests." ) ;
  }
}
