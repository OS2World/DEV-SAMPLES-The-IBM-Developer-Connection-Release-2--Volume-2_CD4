//-----------------------------------------------------------------------------
// scrollbars.java
//
// Simple Java test application that demonstrates how to use scroll
// bars in a java application.
// 
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes:
//
//   Scrollbar
//
// Author : Kelvin R Lawrence.     14th-November-1996
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: scrollbars
//
// Simple class that experiments with scroll bars.
//
//---------------------------------------------------------------

public class scrollbars extends Applet
{

   Scrollbar sbh;
   Scrollbar sbv;

  //----------------------------------------------------------
  // init()
  //
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------

  public void init()
  {
    setLayout( new BorderLayout() ) ;

    System.out.println( "init() method called" ) ;

    sbh = new Scrollbar(Scrollbar.HORIZONTAL, 50, 25, 0, 300);
    sbv = new Scrollbar(Scrollbar.VERTICAL  , 12, 20, 1,  40);

    add( "South", sbh ) ;
    add( "East" , sbv ) ;
  }


  public boolean handleEvent ( Event evt ) 
  {
    if (evt.target.equals( sbh )) 
    {
      System.out.println( evt.toString() );
      System.out.println( ((Scrollbar)evt.target).getValue() );
    } 

      return super.handleEvent(evt);
   }
}


