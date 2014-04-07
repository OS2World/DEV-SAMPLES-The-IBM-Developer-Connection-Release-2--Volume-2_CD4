//-----------------------------------------------------------------------------
// grid_layout.java
//
// Simple Java test application that demonstrates how to use the
// GridLayout class for laying out buttons in a container.
//
// Author : Kelvin R Lawrence.     12th-November-1996
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: grid_layout
//
// Simple class that experiments with Java file dialogs.
//
//---------------------------------------------------------------

public class grid_layout extends Applet
{
  Button one   ;
  Button two   ;
  Button three ;
  Button four  ;
  Button five  ;
  Button six   ;
  Button seven ;
  Button eight ;
  Button nine  ;

  //----------------------------------------------------------------
  // init()
  //
  // Init routine. Called whenever the applet is first loaded.
  //
  // Create nine push buttons.
  // Select the gridLayout to help us arrange a 3x3 grid of buttons.
  // Set the color of each button.
  // Add the buttons to the component window (i.e. applet window).
  //
  //----------------------------------------------------------------

  public void init()
  {
    System.out.println( "init() method called" ) ;

    setLayout( new GridLayout(3,3) ) ;

    one   = new Button("1") ;
    two   = new Button("2") ;
    three = new Button("3") ;
    four  = new Button("4") ;
    five  = new Button("5") ;
    six   = new Button("6") ;
    seven = new Button("7") ;
    eight = new Button("8") ;
    nine  = new Button("9") ;

    one.setBackground( Color.yellow ) ;
    one.repaint();
    two.setBackground( Color.red ) ;
    three.setBackground( Color.yellow ) ;
    four.setBackground( Color.red ) ;
    five.setBackground( Color.yellow ) ;
    six.setBackground( Color.red ) ;
    seven.setBackground( Color.yellow ) ;
    eight.setBackground( Color.red ) ;
    nine.setBackground( Color.yellow ) ;

    add(one)   ;
    add(two)   ;
    add(three) ;
    add(four)  ;
    add(five)  ;
    add(six)   ;
    add(seven) ;
    add(eight) ;
    add(nine)  ;
  }


  //----------------------------------------------------------
  // action()
  //
  // Handle user actions.
  //
  // If a push button was pressed change the color of the 
  // button. Also display the number of the button pressed on
  // the console window.
  //----------------------------------------------------------
  public boolean action( Event evt, Object what )
  {
    Button temp = null ;

    System.out.println("Start of action() method");
    System.out.println( "Button pressed was : " + what ) ;

    if ( evt.id == Event.ACTION_EVENT )
    {
      temp = ((Button)evt.target )  ;

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

