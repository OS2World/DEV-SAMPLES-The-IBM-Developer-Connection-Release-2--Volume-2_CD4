//-----------------------------------------------------------------------------
// choices.java
//
// A simple example of using choices in a Java application. A choice is
// a drop down list box control.
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes:
//
//   Choice
//
// Author : Kelvin R Lawrence.     2nd-May-1997
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: choices
//
// Simple class that experiments with Java AWT menu capabilities.
//
//---------------------------------------------------------------

public class choices extends Frame
{
  static final int ENGLAND = 0 ;
  static final int FRANCE  = 1 ;
  static final int ITALY   = 2 ;
  static final int GERMANY = 3 ;
  static final int SPAIN   = 4 ;

  Choice ch1 ;
  List   listbox ;

  DefaultListModel model ;
  
  String [] countries = { "England"
                        , "France"
                        , "Italy"
                        , "Germany"
                        , "Spain"
                        } ;

  String [] [] cities =
      { { "London" , "Manchester", "Birmingham" , "Leeds"    }
      , { "Paris"  , "Lyon"      , "Marseilles" , "Dieppe"   }
      , { "Rome"   , "Milan"     , "Naples"     , "Florence" }
      , { "Bonn"   , "Munich"    , "Hamburg"    , "Berlin"   }
      , { "Madrid" , "Barcelona" , "Benidorm"   , "Alicante" }
      } ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public choices( String s )
  {

    super(s);

    setLayout( new FlowLayout() ) ;

    ch1 = new Choice() ;

    //dummy = new Choice() ;

    listbox = new List() ;

    for ( int i = 0; i < countries.length; i++ )
    {
      ch1.addItem( countries[i] ) ;
    }

    displayPlaces( ENGLAND ) ;

    this.add( ch1 ) ;
    this.add( listbox ) ;
    //this.add( dummy ) ;
  }

  //--------------------------------------------------------------------
  // action()
  //
  // Process user actions.
  //
  //--------------------------------------------------------------------
  public boolean action( Event evt, Object what )
  {
    int idx ;

    //-----------------------------------------------------------------
    // In this example, we look to see if one of the checkbox menus was
    // selected. If they were then we uncheck the other one. This shows
    // how to implement a set of mutually exclusive menu choices.
    //
    //-----------------------------------------------------------------
    if ( evt.id == Event.ACTION_EVENT )
    {
      System.out.println( "action() method got called" ) ;

      if ( evt.target == ch1 )
      {
         System.out.println( "Current selection is : " + ch1.getSelectedItem() ) ;

         idx = ch1.getSelectedIndex() ;

         System.out.println( "Selected index is : " + idx ) ;

         displayPlaces( idx ) ;
      }
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
  // displayPlaces()
  //
  // Erase the current contents (if any) of the listbox. Then, add the
  // names of the cities for the current country to the listbox.
  //
  //--------------------------------------------------------------------
  void displayPlaces( int country )
  {
    int numItems = listbox.countItems() ;

    if ( numItems > 0 )
    {
      listbox.delItems( 0, numItems-1  ) ;
    }

    for ( int i = 0; i < cities[country].length; i++ )
    {
      listbox.addItem( cities[country][i] ) ;
    }
  }


  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     choices choiceFrame ;

     choiceFrame = new choices( "Choice Tests" ) ;

     choiceFrame.resize( 300,300 ) ;
     choiceFrame.show() ;
  }
}




