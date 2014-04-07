//-----------------------------------------------------------------------------
// JFCChoice.java
//
// A simple example of using the JFC scrolling list capabilities.
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set 
// classes (typically called swing.jar) to be in the Classpath.
//
// Developed using Swing ver. 0.5.1 (pre-Beta)
//
// Demonstrates use of these classes/interfaces:
//
//   BorderFactory
//   DefaultListModel
//   ItemListener
//   JFrame
//   JList
//   JPanel
//   JScrollPane
//   ListSelectionListener
//   ScrollPaneConstants
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. Note also that there have been
//               significant changes between the 0.4.1 and 0.5.1 versions.
//
// Author : Kelvin R Lawrence.     12th-Nov-1997
//
// History:
//  12-Nov-1997  -KRL-  Ported from original AWT sample Choices.java to JFC.
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;

public class JFCChoice extends JFrame implements ListSelectionListener,
                                                 ItemListener ,
                                                 ScrollPaneConstants
{
  static final int ENGLAND = 0 ;
  static final int FRANCE  = 1 ;
  static final int ITALY   = 2 ;
  static final int GERMANY = 3 ;
  static final int SPAIN   = 4 ;
  
  private Container client ;
  
  private JList       list  ;
  private JScrollPane scp   ;
  private JComboBox   combo ;
  
  private DefaultListModel model ;
  
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
  
  //-------------------------------------------------------------------------
  // Constructor #1
  //
  //-------------------------------------------------------------------------
  public JFCChoice( String s )
  {
    super( s ) ;

    System.out.println( "Constructor called" ) ;

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    client = this.getContentPane() ;
    
    client.setLayout( new FlowLayout() ) ;
  }
  
  //-------------------------------------------------------------------------
  // processEvent()
  //
  // Handle high level events. We need this so that the close in the title bar 
  // will actually close the application etc.
  //-------------------------------------------------------------------------
  public void processEvent( AWTEvent evt )
  {
    System.out.println( "Process event" ) ;

    if ( evt.getID() == WindowEvent.WINDOW_CLOSING )
    {
      System.out.println( "Window about to close..." ) ;
      System.exit(0) ;
    }
    else
    {
      super.processEvent( evt ) ;
    }
  }

  //-------------------------------------------------------------------------
  // valueChanged()
  //
  // Because we are implementing the ListSelectionListener interface, this
  // method will get notified each time the list box selection changes.
  //
  //-------------------------------------------------------------------------
  public void valueChanged( ListSelectionEvent levt )
  {
    System.out.println( "Selected list index is " + list.getSelectedIndex() ) ;
    System.out.println( "Selected city is " + (String)list.getSelectedValue() ) ;
  }
  
  //-------------------------------------------------------------------------
  // itemStateChanged()
  //
  // Because we are implementing the ItemListener interface, this
  // method will get notified each time the combo box selection changes.
  //
  //-------------------------------------------------------------------------
  public void itemStateChanged( ItemEvent ie )
  {
    int i = combo.getSelectedIndex() ;
    System.out.println( "Index was " + i ) ;

    displayPlaces( combo.getSelectedIndex() ) ;
  }

  //--------------------------------------------------------------------
  // displayPlaces()
  //
  // Erase the current contents (if any) of the list. Then, add the
  // names of the cities for the current country to the list.
  //
  //--------------------------------------------------------------------
  void displayPlaces( int country )
  {
    int numItems = model.getSize() ;

    if ( numItems > 0 )
    {
      model.clear() ;
    }

    for ( int i = 0; i < cities[country].length; i++ )
    {
      model.addElement( cities[country][i] ) ;
    }
  }
  
  //--------------------------------------------------------------------
  // runTests()
  //
  //--------------------------------------------------------------------
  public void runTests()
  {
    System.out.println( "*** In runTests() ***" ) ;
    
    //----------------------------------------------------------------------
    // Create the combo box and our data model that we will use in the list.
    //----------------------------------------------------------------------
    combo = new JComboBox() ;
    
    model = new DefaultListModel() ;
    
    //-------------------------------------------------                                    
    // Add the names of the countries to the combo box.
    //-------------------------------------------------                                    
    for ( int i = 0; i < countries.length; i++ )
    {
      combo.addItem( countries[i] ) ;
    }
    
    //-------------------------------------------
    // Create our list using our list data model.
    //-------------------------------------------
    list  = new JList( model ) ;
    
    //------------------------------------------------------
    // Register our listeners so we will know when items in 
    // the lists get selected.
    //------------------------------------------------------
    list.addListSelectionListener( this ) ;
    combo.addItemListener( this ) ;

    displayPlaces( ENGLAND ) ;
                     
    //--------------------------------------------------------
    // Create a scroll pane to manage our list box for us. By
    // default, JFC lists do not support scrolling. Also, we
    // use the BorderFactory to create a border for our scroll
    // pane to make it look nicer.
    //--------------------------------------------------------
    scp = new JScrollPane() ;
    scp.setVerticalScrollBarPolicy( VERTICAL_SCROLLBAR_ALWAYS ) ;
    scp.setHorizontalScrollBarPolicy( HORIZONTAL_SCROLLBAR_AS_NEEDED ) ;
    scp.getViewport().setView(list) ;
    
    scp.setBorder( BorderFactory.createRaisedBevelBorder( ) ) ;
    
    //-------------------------------------------------------------
    // Add the components to the panel before we add it to the 
    // content pane.
    //-------------------------------------------------------------
    client.add(combo) ;
    client.add(scp)   ;
    
    //-------------------------------------------------------------          
    // JFC BUG ?
    //
    // The contents of the ContentPane only seem to show up if you
    // force the frame to resize or if you pack() the frame. I am
    // attempting to find out what the correct behaviour should be.
    //-------------------------------------------------------------
    //this.setBounds( 0,0,350,350 ) ;
    this.pack() ;

  }
  
  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     JFCChoice JFCChoiceFrame ;

     JFCChoiceFrame = new JFCChoice( "JFC List test." ) ;

     JFCChoiceFrame.setSize( 300,300 ) ;
     JFCChoiceFrame.show() ;
     
     JFCChoiceFrame.runTests() ;
     
  }
}
