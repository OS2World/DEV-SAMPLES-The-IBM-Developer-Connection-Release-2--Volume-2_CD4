//-----------------------------------------------------------------------------
// notepad.java
//
// Simple Java test application that demonstrates how the 
// TextListener interface can be used to monitor text events in
// a TextArea class.
//
// This version requires Java JDK level 1.1 or higher.
//
// Author : Kelvin R Lawrence.     27th-July-1997
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.event.* ;
import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: notepad
//
// Simple class that experiments with the BorderLayout class.
//
//---------------------------------------------------------------

public class notepad extends Applet implements TextListener
{
  Button   process_button   ;
  Button   open_button      ;
  Panel    buttons          ;
  TextArea mle              ;

  String txt  = "Try typing in here, click Process when done." ;

  //----------------------------------------------------------
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {
    System.out.println( "init() method called" ) ;

    process_button   = new Button("Process") ;
    open_button      = new Button("Open...") ;

    setLayout( new BorderLayout() ) ;

    buttons = new Panel() ;

    buttons.add( open_button ) ;
    buttons.add( process_button ) ;

    add( "South", buttons ) ;

    mle = new TextArea(txt) ;
    mle.setBackground( Color.yellow ) ;

    mle.addTextListener( this ) ;

    add( "Center", mle )  ;
  }


  //----------------------------------------------------------
  // textValueChanged()
  //
  //----------------------------------------------------------
  public void textValueChanged( TextEvent te )
  {
    System.out.println( "textValueChanged() was called" ) ;
    System.out.println( te.toString() ) ;
    System.out.println( "Event type id was " + te.getID() ) ;
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

      if ( temp == process_button ) 
      {
        int    numrows  = mle.getRows() ;
        int    numcols  = mle.getColumns() ;
        String mle_text = mle.getText() ;

        System.out.println( "-*-*-*-*-*-*-*" ) ;
        System.out.println( "Calling getRows() returned " + numrows + " rows." ) ;
        System.out.println( "Calling getColumns() returned " + numcols + " columns." ) ;
        System.out.println( "The current text in the text area is : " ) ;
        System.out.println( mle_text ) ;
      }
      else if ( temp == open_button ) 
      {
         System.out.println( "Open button clicked." ) ;
      }
    }
    else
    {
      return false ;
    }

    return true ;
  }

}

