//-----------------------------------------------------------------------------
// simple.java
//
// A simple example of using the AWT Button class. This shows how to
// put a few buttons on the screen without using a layout manager.
// This is a very simple example, designed to experiment with the
// applet control methods start(), init(), stop() and term().
// 
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes:
//
//   Button
//
// Author : Kelvin R Lawrence.     27th-August-1996
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//--------------------------------------------
// Definition of the simple class which inherits
// from the Applet class.
//--------------------------------------------

public class simple extends Applet
{
  Button yes   ;
  Button no    ;
  Button maybe ;

  int i = 0 ;
  int s = 0 ;
  int t = 0 ;

  //----------------------------------------------------------
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {
    i++ ;

    System.out.println( "init() method called" ) ;
    
    setLayout(null) ;

    //--------------------------------------
    // Create and display three pushbuttons.
    //--------------------------------------

    yes   = new Button("Yes") ;
    no    = new Button("No")  ;
    maybe = new Button("Maybe") ;

    add(yes)   ;
    add(no)    ;
    add(maybe) ;

    yes.reshape( 0,0,100,20 ) ;
    no.reshape( 120,0,100,20 ) ;
    maybe.reshape( 240,0,100,20 ) ;

    //---------------------------------------------
    // This is how you print to the console window.
    //---------------------------------------------

    System.out.println( "This should appear on the console" ) ;

    yes.setLabel( Integer.toString(i) ) ;
  }

  //--------------------------------------------------------------------
  // start()
  //
  // Start method is called every time the browser re-loads the Web page
  // containing this applet.
  //--------------------------------------------------------------------
  public void start()
  {
    s++;
    System.out.println( "start() method called" ) ;
    no.setLabel( Integer.toString(s) ) ;
  }
  
  //--------------------------------------------------------------------
  // stop()
  //
  // Start method is called every time the browser re-loads the Web page
  // containing this applet.
  //--------------------------------------------------------------------
  public void stop()
  {
    t++ ;
    System.out.println( "stop() method called" ) ;
    maybe.setLabel( Integer.toString(t) ) ;
  }

  //-----------------------------------------------
  // action()
  //
  // Simple event handler. Note that returning true
  // means the event was handled by us.
  //-----------------------------------------------
  public boolean action( Event evt, Object what )
  {
    if ( evt.target == yes )
    {
      System.out.println( "Yes button clicked" ) ;
      yes.setLabel( "Clicked" ) ;
      no.disable() ;
      System.out.println( "No button disabled" ) ;
    }
    else if ( evt.target == maybe )
    {
      System.out.println( "Maybe button clicked" ) ;
      no.enable() ;
      yes.setLabel("Yes") ;
      System.out.println( "No button enabled" ) ;
    }
    else
    {
      return false ;
    }
    return true ;
  }
}


