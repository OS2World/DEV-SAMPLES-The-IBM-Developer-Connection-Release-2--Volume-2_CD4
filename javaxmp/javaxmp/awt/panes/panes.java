//-----------------------------------------------------------------------------
// panes.java
//
// A simple example of using scrollable panels (scroll panes) in a
// Java application.
// 
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes:
//
//   ScrollPane
//
// Author : Kelvin R Lawrence.     10th-May-1997
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;

//---------------------------------------------------------------
// Class: panes
//
// Simple class that experiments with the AWT ScrollPane class.
//
//---------------------------------------------------------------

public class panes extends Frame
{
  protected ScrollPane scp ;

  protected Button b1 ;
  protected Button b2 ;
  protected Button b3 ;
  protected Button b4 ;
  protected Button b5 ;
  protected Button b6 ;
  protected Button b7 ;
  protected Button b8 ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public panes( String s )
  {
    super(s);

    setLayout( new GridLayout(1,1) ) ;

    scp = new  ScrollPane( ScrollPane.SCROLLBARS_ALWAYS ) ;

    Panel p1 = new Panel() ;

    // p1.setLayout( new GridLayout(8,1) ) ;
    p1.setLayout( new FlowLayout() ) ;

    b1 = new Button( "One" ) ;
    b2 = new Button( "Two" ) ;
    b3 = new Button( "Three" ) ;
    b4 = new Button( "Four" ) ;
    b5 = new Button( "Five" ) ;
    b6 = new Button( "Six" ) ;
    b7 = new Button( "Seven" ) ;
    b8 = new Button( "Eight" ) ;

    p1.add(b1) ;
    p1.add(b2) ;
    p1.add(b3) ;
    p1.add(b4) ;
    p1.add(b5) ;
    p1.add(b6) ;
    p1.add(b7) ;
    p1.add(b8) ;

    scp.add(p1) ;

    this.add(scp) ;
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
     panes panesFrame ;

     panesFrame = new panes( "Scroll Pane test" ) ;

     panesFrame.resize( 300,300 ) ;
     panesFrame.show() ;
  }
}




