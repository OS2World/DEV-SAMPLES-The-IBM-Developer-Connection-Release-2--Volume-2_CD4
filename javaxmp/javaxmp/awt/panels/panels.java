//-----------------------------------------------------------------------------
// panels.java
//
// A simple example of using panels to group things together.
//
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of:
//
//   Panel
//   Button
//   Checkbox
//   CheckboxGroup
//
// Author : Kelvin R Lawrence.     30th-April-1997
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;


//---------------------------------------------------------------
// Class: panels
//
// Experiment with Java panels.
//
//---------------------------------------------------------------
public class panels extends Frame 
{
  Checkbox c1 ;
  Checkbox c2 ;
  Checkbox c3 ;
  Checkbox c4 ;
  Checkbox c5 ;
  Checkbox c6 ;
  Checkbox c7 ;
  Checkbox c8 ;

  CheckboxGroup g1 ;

  Panel p1 ;
  Panel p2 ;
  Panel p3 ;

  Button b1 ;
  Button b2 ;
  Button b3 ;

  //-------------------------------------------------------------
  // Constructor: 
  //
  //-------------------------------------------------------------
  public panels( String s )
  {
    super( s ) ;

    setLayout( new GridLayout(3,1) ) ;

    p1 = new Panel() ;
    p2 = new Panel() ;
    p3 = new Panel() ;

    p2.setLayout( new GridLayout( 2,2 ) ) ;

    c1 = new Checkbox( "One"   ) ;
    c2 = new Checkbox( "Two"   ) ;
    c3 = new Checkbox( "Three" ) ;
    c4 = new Checkbox( "Four"  ) ;
    c5 = new Checkbox( "Five"  ) ;
    c6 = new Checkbox( "Six"   ) ;
    c7 = new Checkbox( "Seven" ) ;
    c8 = new Checkbox( "Eight" ) ;

    b1 = new Button( "ONE"   ) ;
    b2 = new Button( "TWO"   ) ;
    b3 = new Button( "THREE" ) ;

    //------------------------------------------------------------
    // Note that checkboxes in a group will appear and behave like
    // what is more traditionally known as radio buttons.
    //------------------------------------------------------------

    g1 = new CheckboxGroup() ;

    c5.setCheckboxGroup( g1 ) ;
    c6.setCheckboxGroup( g1 ) ;
    c7.setCheckboxGroup( g1 ) ;
    c8.setCheckboxGroup( g1 ) ;

    //--------------------------
    // Give the panels a colour.
    //--------------------------

    p1.setBackground( Color.gray ) ;
    p2.setBackground( Color.lightGray ) ;
    p3.setBackground( Color.yellow ) ;

    //---------------------------------------------
    // Add the controls to their respective panels.
    //---------------------------------------------

    p1.add( c1 ) ;
    p1.add( c2 ) ;
    p1.add( c3 ) ;
    p1.add( c4 ) ;

    p2.add( c5 ) ;
    p2.add( c6 ) ;
    p2.add( c7 ) ;
    p2.add( c8 ) ;

    p3.add( b1 ) ;
    p3.add( b2 ) ;
    p3.add( b3 ) ;

    //------------------------------------
    // Add the panels to the frame window.
    //------------------------------------

    this.add( p1 ) ;
    this.add( p2 ) ;
    this.add( p3 ) ;

  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     panels pnlFrame ;

     pnlFrame = new panels( "Panels" ) ;

     pnlFrame.resize( 300,300 ) ;
     pnlFrame.show() ;
  }
}
