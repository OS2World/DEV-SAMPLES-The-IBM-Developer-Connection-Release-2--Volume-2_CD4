//-----------------------------------------------------------------------------
// cards.java
//
// A simple example of using the CardLayout layout manager. A CardLayout
// allows you to setup several panels each having one or more controls
// in them and to switch easily between the panels.
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes:
//
//   CardLayout
//   FlowLayout
//   BorderLayout
//   Panel
//   Button
//   Choice
//
// Author : Kelvin R Lawrence.     15th-May-1997
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: Cards
//
// Simple class that experiments with the CardLayout class.
//
//---------------------------------------------------------------
public class Cards extends Applet
{
  static final String pg1 = "Page 1" ;
  static final String pg2 = "Page 2" ;
  static final String pg3 = "Page 3" ;

  static final int FIRSTPAGEIDX  = 0 ;
  static final int LASTPAGEIDX   = 2 ;

  Panel mainPanel ;
  Panel controlPanel ;

  Button next ;
  Button previous ;
  Button first ;
  Button last ;

  Button button1 ;
  List   list1 ;
  Choice choice1 ;

  Panel page1 ;
  Panel page2 ;
  Panel page3 ;

  Choice pageNumbers ;

  //----------------------------------------------------------
  // Init routine. Called whenever the applet is first loaded.
  //----------------------------------------------------------
  public void init()
  {
    setLayout( new BorderLayout() ) ;

    CardLayout cl = new CardLayout() ;

    mainPanel    = new Panel() ;
    controlPanel = new Panel() ;

    mainPanel.setLayout( cl ) ;

    //----------------------------------------------------------
    // Create some buttons that we can use to turn the pages
    // and add them to the control panel.
    //----------------------------------------------------------
    controlPanel.setLayout( new FlowLayout() ) ;

    next     = new Button( "Next"     ) ;
    previous = new Button( "Previous" ) ;
    first    = new Button( "First"    ) ;
    last     = new Button( "Last"     ) ;

    pageNumbers = new Choice() ;

    pageNumbers.addItem( pg1 ) ;
    pageNumbers.addItem( pg2 ) ;
    pageNumbers.addItem( pg3 ) ;

    controlPanel.add( first ) ;
    controlPanel.add( last ) ;
    controlPanel.add( next ) ;
    controlPanel.add( previous ) ;
    controlPanel.add( pageNumbers ) ;

    //----------------------------------------------------------
    // Now setup each of the pages that we will be adding to the
    // card layout window.
    //----------------------------------------------------------

    page1 = new Panel() ;
    page2 = new Panel() ;
    page3 = new Panel() ;

    //---------------
    // Create page 1.
    //---------------
    button1 = new Button( "one" ) ;
    page1.add(button1) ;

    //---------------
    // Create page 2.
    //---------------
    list1   = new List() ;
    page2.add( list1 ) ;

    //---------------
    // Create page 3.
    //---------------
    choice1 = new Choice() ;
    page3.add( choice1 ) ;

    //-----------------------------------------------------------
    // Now add each page to the CardLayout controlled main panel.
    //-----------------------------------------------------------

    mainPanel.add( pg1 , page1 ) ;
    mainPanel.add( pg2 , page2 ) ;
    mainPanel.add( pg3 , page3 ) ;

    //----------------------------------------------------------------
    // Now finally, we can add the controlling buttons panel and the 
    // main panel to the main frame window.
    //----------------------------------------------------------------

    add( "South" , controlPanel ) ;
    add( "Center", mainPanel ) ;
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
    // Handle ACTION_EVENT events.
    // 
    //-----------------------------------------------------------------
    if ( evt.id == Event.ACTION_EVENT )
    {
      System.out.println( "action() method got called" ) ;

      if ( evt.target instanceof Button )
      {
        if ( evt.target == next )
        {
          ((CardLayout)mainPanel.getLayout()).next( mainPanel ) ;
          int idx = pageNumbers.getSelectedIndex() ;
          idx = ( idx < LASTPAGEIDX ) ? idx + 1 : FIRSTPAGEIDX ;
          pageNumbers.select(idx) ;
        }
        else if ( evt.target == previous )
        {
          ((CardLayout)mainPanel.getLayout()).previous( mainPanel ) ;
          int idx = pageNumbers.getSelectedIndex() ;
          idx = ( idx > FIRSTPAGEIDX ) ? idx - 1 : LASTPAGEIDX ;
          pageNumbers.select(idx) ;
        }
        else if ( evt.target == first )
        {
          ((CardLayout)mainPanel.getLayout()).first( mainPanel ) ;
          pageNumbers.select(FIRSTPAGEIDX) ;
        }
        else if ( evt.target == last )
        {
          ((CardLayout)mainPanel.getLayout()).last( mainPanel ) ;
          pageNumbers.select(LASTPAGEIDX) ;
        }
        else return false ;
      }
      else if ( evt.target instanceof Choice )
      {
        if ( evt.target == pageNumbers )
        {
          ((CardLayout)mainPanel.getLayout()).show( mainPanel, (String)what ) ;
        }
        else return false ;
      }
      else return false ;
    }

    return true ;
  }
}





