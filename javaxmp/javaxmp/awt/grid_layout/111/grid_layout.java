//-----------------------------------------------------------------------------
// grid_layout.java
//
// Simple Java test application that demonstrates how to use the
// GridLayout class for laying out buttons in a container.
//
// Author : Kelvin R Lawrence.     11th-September-1996
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: grid_layout
//
// Simple class that experiments with Java file dialogs.
//
//---------------------------------------------------------------

public class grid_layout extends Frame implements ActionListener
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
  // Constructor
  //
  //----------------------------------------------------------------
  grid_layout( String s )
  {
    super( s ) ;

  //--------------------------------------------------
  // This shows how you can write to the Java console.
  // This is useful for simple debugging.
  //--------------------------------------------------
   System.out.println( "Constructor called" ) ;

  //-------------------------------------------------------------------
  // Create a new GridLayout for a 3 by 3 grid and estalish that as the 
  // layout manager for the frame.
  //-------------------------------------------------------------------
   setLayout( new GridLayout( 3,3 ) ) ;

    one   = new Button( "1" ) ;
    two   = new Button( "2" ) ;
    three = new Button( "3" ) ;
    four  = new Button( "4" ) ;
    five  = new Button( "5" ) ;
    six   = new Button( "6" ) ;
    seven = new Button( "7" ) ;
    eight = new Button( "8" ) ;
    nine  = new Button( "9" ) ;

    //--------------------------------------------------
    // Just for fun, give each of the buttons an initial 
    // colour that is not the default.
    //--------------------------------------------------

    one.setBackground( Color.yellow ) ;
    two.setBackground( Color.red ) ;
    three.setBackground( Color.yellow ) ;
    four.setBackground( Color.red ) ;
    five.setBackground( Color.yellow ) ;
    six.setBackground( Color.red ) ;
    seven.setBackground( Color.yellow ) ;
    eight.setBackground( Color.red ) ;
    nine.setBackground( Color.yellow ) ;

    //------------------------------------------------------------------------
    // Add the buttons to the frame window. The GridLayout layout manager will
    // arrange the buttons nicely in a grid for us.
    //------------------------------------------------------------------------
    add(one)   ;
    add(two)   ;
    add(three) ;
    add(four)  ;
    add(five)  ;
    add(six)   ;
    add(seven) ;
    add(eight) ;
    add(nine)  ;

    //----------------------------------------------------------
    // Register an action listener for each of the buttons. Note
    // that the same method can listen for more than one button.
    //----------------------------------------------------------

    one.addActionListener( this ) ; 
    two.addActionListener( this ) ; 
    three.addActionListener( this ) ;
    four.addActionListener( this ) ;
    five.addActionListener( this ) ;
    six.addActionListener( this ) ; 
    seven.addActionListener( this ) ;
    eight.addActionListener( this ) ;
    nine.addActionListener( this ) ;

    //-----------------------------------------------------------
    // Enable the evnts that we are interested in for this frame.
    // Note that we do not have to specifically enable action
    // events as these will have been enabled when we registered 
    // our action listeners for the buttons.
    //-----------------------------------------------------------

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;
  }

  //---------------------------------------------------------------
  // actionPerformed()
  //
  // This method will be called whenever any of the buttons that we
  // registered an action listener with is clicked.
  //---------------------------------------------------------------
  public void actionPerformed( ActionEvent evt )
  {
    Button temp ;
    Color  c    ;

    System.out.println( "actionPerformed()" ) ;


    //-----------------------------------------------------------
    // Just for fun, let's query the current colour of the button
    // that was clicked and change it to a different one.
    //-----------------------------------------------------------
    temp = (Button)evt.getSource() ;

    c = temp.getBackground() ;

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

  //-------------------------------------------------------------------------
  // processEvent()
  //
  // Handle high level events. We need this so that the close in the title bar 
  // will actually close the application etc.
  //-------------------------------------------------------------------------
  public void processEvent( AWTEvent evt )
  {
    System.out.println( "processEvent() called" ) ;

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
  
  //--------------------------------------------------------------------
  // main()
  //
  // Called when application is first loaded.
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
    grid_layout gridFrame ;

    gridFrame = new grid_layout( "GridLayout example" ) ;

    gridFrame.setSize( 300,300 ) ;
    gridFrame.show() ;
  }
}
                 
