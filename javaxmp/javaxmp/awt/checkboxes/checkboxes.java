//-----------------------------------------------------------------------------
// checkboxes.java
//
// A simple example of using checkboxes.
//
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of:
//
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
// Class: checkboxes
//
// Experiment with Java checkboxes.
//
//---------------------------------------------------------------
public class checkboxes extends Frame 
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

  //-------------------------------------------------------------
  // Constructor: 
  //
  //-------------------------------------------------------------
  public checkboxes( String s )
  {
    super( s ) ;

    setLayout( new GridLayout(4,2) ) ;

    c1 = new Checkbox( "One"   ) ;
    c2 = new Checkbox( "Two"   ) ;
    c3 = new Checkbox( "Three" ) ;
    c4 = new Checkbox( "Four"  ) ;
    c5 = new Checkbox( "Five"  ) ;
    c6 = new Checkbox( "Six"   ) ;
    c7 = new Checkbox( "Seven" ) ;
    c8 = new Checkbox( "Eight" ) ;

    //------------------------------------------------------------
    // Note that checkboxes in a group will appear and behave like
    // what is more traditionally known as radio buttons.
    //------------------------------------------------------------

    g1 = new CheckboxGroup() ;

    c5.setCheckboxGroup( g1 ) ;
    c6.setCheckboxGroup( g1 ) ;
    c7.setCheckboxGroup( g1 ) ;
    c8.setCheckboxGroup( g1 ) ;

    this.add( c1 ) ;
    this.add( c2 ) ;
    this.add( c3 ) ;
    this.add( c4 ) ;
    this.add( c5 ) ;
    this.add( c6 ) ;
    this.add( c7 ) ;
    this.add( c8 ) ;
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     checkboxes chkFrame ;

     chkFrame = new checkboxes( "Checkboxes" ) ;

     chkFrame.resize( 300,300 ) ;
     chkFrame.show() ;
  }

}
