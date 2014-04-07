//-----------------------------------------------------------------------------
// tictactoe.java
//
// A simple example of ... 
// 
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes:
//
//   ??????
//
// Author : Kelvin R Lawrence.     14th-May-1997
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;

//---------------------------------------------------------------
// Class: tictactoe
//
// Simple class that experiments with Java AWT menu capabilities.
//
//---------------------------------------------------------------

public class tictactoe extends Frame
{
  static final int FREE     = 0 ;
  static final int PLAYER_1 = 1 ;
  static final int PLAYER_2 = 2 ;

  int numPlayers = 1 ;
  int currentPlayer = 1 ;

  Button [] buttons  ;

  int [] board ;

  boolean computersTurn = false ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public tictactoe( String s )
  {
    super(s);

    setLayout( new GridLayout( 3,3 ) ) ;

    board = new int [9] ;
    buttons = new Button[9] ;

    for (int i = 0; i < 9 ; i++ )
    {
        buttons[i] = new Button( Integer.toString(i) ) ;
        buttons[i].setBackground( Color.yellow ) ;
        add( buttons[i] ) ;
        board[i] = FREE ;
    }
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
        if ( !computersTurn )
        {
          // TODO: Check for valid move needed here

          Button temp = (Button)evt.target ;
          temp.setBackground( Color.green ) ;

          //TODO: Record valid move 
          board[Integer.parseInt(temp.getLabel())] = PLAYER_1 ;

          if ( numPlayers == 1 )
          {
            computersTurn = true ;
            doComputerMove() ;
            computersTurn = false ;
          }

          System.out.println( "Button clicked" ) ;
        }
      }
    }

    return true ;
  }


  //-------------------------------------------------------------------------
  // doComputerMove()
  //
  //-------------------------------------------------------------------------
  private void doComputerMove()
  {
    boolean found = false ;

    int pos = 0 ; 

    while ( !found )
    {
      if ( board[pos] == FREE )
      {
        board[pos] = PLAYER_2 ;
        buttons[pos].setBackground( Color.red ) ;
        found = true ;
      }
      else
      {
        pos++ ;

        //TODO: if ( pos >8 ) no move possible;

      }
    }
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
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     tictactoe tictactoeFrame ;

     tictactoeFrame = new tictactoe( "Simple Noughts and Crosses" ) ;

     tictactoeFrame.resize( 300,300 ) ;
     tictactoeFrame.show() ;
  }
}




