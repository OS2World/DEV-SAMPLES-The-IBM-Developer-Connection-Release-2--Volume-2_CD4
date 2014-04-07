//-----------------------------------------------------------------------------
// border_layout.java
//
// Simple Java test application that demonstrates how to use the
// BorderLayout class for laying out four buttons and a TextArea
// in a container.
//
// Author : Kelvin R Lawrence.     12th-November-1996
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: border_layout
//
// Simple class that experiments with the BorderLayout class.
//
//---------------------------------------------------------------

public class border_layout extends Applet
{
  Button one   ;
  Button two   ;
  Button three ;
  Button four  ;
  String txt  = "With the BorderLayout class the first four controls are placed\n"
                +"at the sides of the frame and the remaining space is left for\n"
                +"whatever control you decide to place in the center.\n" ;

  //----------------------------------------------------------
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {
    System.out.println( "init() method called" ) ;

    one   = new Button("N") ;
    two   = new Button("S") ;
    three = new Button("W") ;
    four  = new Button("E") ;

    setLayout( new BorderLayout() ) ;

    one.setBackground( Color.yellow ) ;
    two.setBackground( Color.red ) ;
    three.setBackground( Color.yellow ) ;
    four.setBackground( Color.red ) ;

    add( "North", one )  ;
    add( "South", two )  ;
    add( "West" , three) ;
    add( "East" , four)  ;
    add( "Center",new TextArea(txt) )  ;
  }


  //----------------------------------------------------------
  // action()
  //
  // Handle user actions (i.e. when a push button is pressed).
  //----------------------------------------------------------
  public boolean action( Event evt, Object what )
  {
    Button temp = null ;

    System.out.println("Start of action() method");
    System.out.println( "Button pressed was : " + what ) ;

    if ( evt.id == Event.ACTION_EVENT )
    {
      temp = ( (Button)evt.target ) ;

      Color c = temp.getBackground() ;

      if ( c == Color.green )
      {
        temp.setBackground( Color.red ) ;
      }
      else if ( c == Color.red )
      {
        temp.setBackground( Color.yellow ) ;
      }
      else if ( c == Color.yellow )
      {
        temp.setBackground( Color.green ) ;
      }
      else
      {
        temp.setBackground( Color.red ) ;
      }
    }
    else
    {
      return false ;
    }

    return true ;
  }

}

