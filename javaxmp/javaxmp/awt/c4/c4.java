//-----------------------------------------------------------------------------
// c4.java
//
// A simple example of how you might implement the game of Connect4 in Java.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   ??????
//
// Author : Kelvin R Lawrence.     13-Jun-1997
//
// History:
//
// TO DO:
//
//  1. Add AI player.
//  2. Animate piece placement - DONE.
//  3. TCP/IP Socket support for network play.
//  4. Implement 2 player basic play.
//  5. Repaint pieces when board is sized - DONE.
//  6. Status panel.
//  7. Sound
//  8. Animate new game sequence.
//  9. Add win/tie checking.
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: c4
//
// Simple class that experiments with Java AWT menu capabilities.
//
//---------------------------------------------------------------
public class c4 extends Frame implements ActionListener, MouseListener,
                                         ItemListener
{
  private static final int NUM_ROWS   = 5 ;
  private static final int NUM_COLS   = 7 ;
  private static final int NUM_PANELS = NUM_ROWS * NUM_COLS ;

  private Panel mainPanel ;
  private Panel gamePanel ;
  private Panel statusPanel ;
  private GameSquare [][] gameBoard ;

  private int [] colStatus ;

  private int currentPlayer = GameSquare.P1 ;

  private int computerPlayer = GameSquare.NONE ;

  private int freeSquares = NUM_PANELS ;

  private Label l1 ;
  private TextField player1 ;
  private TextField player2 ;
  private Panel currentColor ;

  private MenuBar   menuBar ;
  private Menu      menuGame ;
  private MenuItem  newGame ;

  private CheckboxMenuItem  animatedMoves ;

  private boolean fAnimatedMoves = true ;

  private boolean fDebugMode ;

  //----------------------------------------------------------
  // Constructor
  //
  // Perform necessary initialisation.
  //
  //----------------------------------------------------------
  public c4( String s, boolean dbg )
  {
    super(s);

    fDebugMode = dbg ;

    mainPanel   = new Panel() ;
    gamePanel   = new Panel() ;
    statusPanel = new Panel() ;

    mainPanel.setLayout( new BorderLayout() ) ;
    gamePanel.setLayout( new GridLayout(NUM_ROWS,NUM_COLS,5,5) ) ;

    //----------------------------------------------------------------
    // Create the menu bar and menus and add the necessary menu items.
    // Also, specify where we want menu generated events to be sent.
    //----------------------------------------------------------------
    menuBar = new MenuBar() ;

    menuGame = new Menu( "Game" ) ;
    newGame  = new MenuItem( "New game" ) ;

    animatedMoves = new CheckboxMenuItem( "Animated moves" ) ;
    animatedMoves.setState( fAnimatedMoves ) ;

    menuGame.add( newGame ) ;
    menuGame.add( animatedMoves ) ;

    menuBar.add( menuGame ) ;

    newGame.addActionListener( this ) ;
    animatedMoves.addItemListener( this ) ;

    this.setMenuBar( menuBar ) ;

    //---------------------------------------------------------------
    // Create and initialise the status panel. The status panel shows
    // the names of the players, whose turn it is and the number of
    // free squares remaining.
    //---------------------------------------------------------------
    l1 = new Label( Integer.toString(freeSquares) ) ;

    player1 = new TextField( "Player 1" ) ;
    player2 = new TextField( "Player 2" ) ;


    currentColor = new Panel() ;
    currentColor.setBackground( GameSquare.P1_COLOR ) ;

    player1.setForeground( GameSquare.P1_COLOR ) ;
    player1.setBackground( Color.black ) ;
    player2.setForeground( GameSquare.P2_COLOR ) ;
    player2.setBackground( Color.black ) ;

    statusPanel.setLayout( new GridLayout( 2,2 ) ) ;
    statusPanel.add( l1 ) ;
    statusPanel.add( currentColor ) ;
    statusPanel.add( player1 ) ;
    statusPanel.add( player2 ) ;

    //--------------------------------------------------------------------------
    // Create all of the graphics panel objects into which the pieces will be
    // drawn as they are placed. Finally add the main panel to the frame.
    //--------------------------------------------------------------------------
    gameBoard = new GameSquare[NUM_ROWS][NUM_COLS] ;

    for ( int row = NUM_ROWS-1; row >= 0; row-- )
    {
      for ( int col = 0; col < NUM_COLS ; col++ )
      {
        gameBoard[row][col] = new GameSquare(row,col) ;
        gameBoard[row][col].setBackground( Color.black ) ;
        gameBoard[row][col].addMouseListener( this ) ;
        gamePanel.add( gameBoard[row][col] ) ;
      }
    }
    mainPanel.add( "Center" , gamePanel ) ;
    mainPanel.add( "South"  , statusPanel ) ;

    this.add( mainPanel ) ;

    //------------------------------------------------------------------
    // Turn on event notification for the events that we are interested
    // in. Note that because we are implementing the MouseListener
    // interface, mouse events will be turned on anyway when we add
    // ourselves as a listener.
    //------------------------------------------------------------------

    enableEvents( AWTEvent.WINDOW_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK
                | AWTEvent.ACTION_EVENT_MASK ) ;

    //------------------------------------------------------------
    // Setup the accelerator table that will allow us to quickly
    // check on the status of each column witout referrring to the
    // game grid itself.
    //------------------------------------------------------------

    colStatus = new int[NUM_COLS] ;

    for ( int i = 0; i< NUM_COLS; i++ )
    {
      colStatus[i] = 0 ;
    }
  }

  //-------------------------------------------------------------------------
  // paint()
  //
  //-------------------------------------------------------------------------
  public void paint( Graphics g )
  {
    debugPrintln( "Paint() called for frame window." ) ;

    Dimension d = getSize() ;

    g.setColor( Color.gray ) ;

    g.fillRect( 0,0,d.width,d.height ) ;
  }

  //-------------------------------------------------------------------------
  // resetGameBoard()
  //
  // Reset the game back to the initial state.
  //-------------------------------------------------------------------------
  private void resetGameBoard()
  {
    for ( int c=0; c < NUM_COLS; c++ )
    {
      for ( int r=0; r < NUM_ROWS; r++ )
      {
        gameBoard[r][c].setOwner( GameSquare.NONE ) ;
        gameBoard[r][c].repaint() ;
      }
      colStatus[c] = 0 ;
    }

    l1.setText( Integer.toString(freeSquares = NUM_PANELS) ) ;
  }

  //-------------------------------------------------------------------------
  // processEvent()
  //
  // Handle high level events. We need this so that the close in the title bar
  // will actually close the application etc.
  //-------------------------------------------------------------------------
  public void processEvent( AWTEvent evt )
  {
    //debugPrintln( "Process event" ) ;

    if ( evt.getID() == WindowEvent.WINDOW_CLOSING )
    {
      debugPrintln( "Window about to close..." ) ;
      System.exit(0) ;
    }
    else
    {
      super.processEvent( evt ) ;
    }
  }

  //-------------------------------------------------------------------------
  // debugPrintln()
  //
  //-------------------------------------------------------------------------
  private void debugPrintln( String s )
  {
    if ( fDebugMode )
    {
      System.out.println( s ) ;
    }
  }

  //-------------------------------------------------------------------------
  // checkForAWinner()
  //
  // Check to see if the player who just placed a piece has made a winning
  // move. We can optimise the search by starting from the location of the
  // move that was just made. Otherwise we would have to search the whole
  // board each time we wanted to check for a win.
  //-------------------------------------------------------------------------
  private boolean checkForAWinner( int row, int col )
  {
    boolean wins = false ;

    debugPrintln( "Checking for a winner." ) ;

    //----------------------------------------------------------------------
    // Horizontal win ?
    //
    // First check for any horizontal win in the row that was just added to.
    // Rather than search in two directions from the most recent move it is
    // just as easy and effecient to check the whole row.
    //----------------------------------------------------------------------
    for ( int c = 0; c < NUM_COLS-3; c++ )
    {
      if ( gameBoard[row][c].owner != currentPlayer)
      {
        continue ;
      }
      else
      {
        if ( gameBoard[row][c].owner == currentPlayer
             && gameBoard[row][c+1].owner == currentPlayer
             && gameBoard[row][c+2].owner == currentPlayer
             && gameBoard[row][c+3].owner == currentPlayer )
        {
          wins = true ;
          debugPrintln( "Horizontal win line found starting at " + row + "," + c ) ;
          break;
        }
      }
    }

    //----------------------------------------------------------------------
    // Vertical win ?
    //
    // Next check for any vertical win in the column that was just added to.
    // Rather than search in two directions from the most recent move it is
    // just as easy and effecient to check the whole column.
    //----------------------------------------------------------------------
    for ( int r = 0; r < NUM_ROWS-3; r++ )
    {
      if ( gameBoard[r][col].owner != currentPlayer)
      {
        continue ;
      }
      else
      {
        if ( gameBoard[r][col].owner == currentPlayer
             && gameBoard[r+1][col].owner == currentPlayer
             && gameBoard[r+2][col].owner == currentPlayer
             && gameBoard[r+3][col].owner == currentPlayer )
        {
          wins = true ;
          debugPrintln( "Vertical win line found starting at " + r + "," + col ) ;
          break;
        }
      }
    }

    //----------------------------------------------------------------------
    // Diagonal win ?
    //
    // Finally, check to see if there are any diagonal wins.
    //----------------------------------------------------------------------

    //LATER: Animate winning pieces

    return wins ;
  }

  //-------------------------------------------------------------------------
  // itemStateChanged()
  //
  // Handle item events for checkbox menus.
  //
  //-------------------------------------------------------------------------
  public void itemStateChanged( ItemEvent ievt )
  {
    debugPrintln( "itemStateChanged() got called for a checkox menu item." ) ;

    fAnimatedMoves = animatedMoves.getState() ;
  }


  //-------------------------------------------------------------------------
  // actionPerformed()
  //
  // Handle an action for which we have registered interest.
  //
  //-------------------------------------------------------------------------
  public void actionPerformed( ActionEvent aevt )
  {
    debugPrintln( "actionPerformed() got called for a menu item." ) ;
    debugPrintln( "Action command string ==> " + aevt.getActionCommand() ) ;

    resetGameBoard() ;
  }

  //-------------------------------------------------------------------------
  // processMouseEvent()
  //
  // Process mouse events. Notice that mouse move and drag events are
  // not sent here. They go to the processMouseMoveEvent() method if there
  // is one.
  //
  // In this particular case we only do something with the MOUSE_CLICKED
  // event. The event ID will be one of:
  //
  //       MOUSE_CLICKED
  //       MOUSE_PRESSED
  //       MOUSE_RELEASED
  //       MOUSE_ENTERED
  //       MOUSE_EXITED
  //
  //-------------------------------------------------------------------------
  public void processMouseEvent( MouseEvent mevt )
  {
    switch( mevt.getID() )
    {
      case MouseEvent.MOUSE_CLICKED:
      {
        debugPrintln( "MOUSE_CLICKED event." ) ;
      }
    }
  }

  //-------------------------------------------------------------------------
  // mouseClicked()
  //
  //-------------------------------------------------------------------------
  public void mouseClicked( MouseEvent mevt )
  {
    //debugPrintln( "Mouse clicked listener" ) ;
  }

  //-------------------------------------------------------------------------
  // mouseEntered()
  //
  //-------------------------------------------------------------------------
  public void mouseEntered( MouseEvent mevt )
  {
    //debugPrintln( "Mouse entered listener" ) ;
  }

  //-------------------------------------------------------------------------
  // mouseExited()
  //
  //-------------------------------------------------------------------------
  public void mouseExited( MouseEvent mevt )
  {
    //debugPrintln( "Mouse exited listener" ) ;
  }

  //-------------------------------------------------------------------------
  // mousePressed()
  //
  // When the user selects a cell with the mouse figure out the next free
  // (i.e. valid) cell in that column and place a game piece in that cell.
  //
  //-------------------------------------------------------------------------
  public void mousePressed( MouseEvent mevt )
  {
    Graphics g ;
    GameSquare p ;
    Dimension d ;
    int v,row,col;

    //debugPrintln( "Mouse pressed listener" ) ;

    p = (GameSquare)mevt.getSource() ;

    row = p.getRow() ;
    col = p.getCol() ;

    debugPrintln( "Clicked location ==> " + row + "," + col ) ;

    v = colStatus[col] ;

    //------------------------------------------------------------------
    // If the column is not full then place the piece in the appropriate
    // square on the game board otherwise signal an invalid selection.
    //------------------------------------------------------------------
    if ( v < NUM_ROWS )
    {
       if ( fAnimatedMoves )
       {
         for ( int i = NUM_ROWS-1; i > colStatus[col]; i-- )
         {
           try
           {
             p = gameBoard[i][col] ;
             p.setOwner(currentPlayer) ;
             Thread.sleep(100) ;
             p.setOwner(GameSquare.NONE) ;
             //Thread.sleep(500) ;
           }
           catch( InterruptedException e )
           {
             debugPrintln( "Interrupted during sleep()" ) ;
           }
         }
       }

      colStatus[col]++;

      p = gameBoard[v][col] ;

      p.setOwner( currentPlayer ) ;

      freeSquares--;
      l1.setText( Integer.toString(freeSquares) ) ;

      // LATER: having checked for win need to take action here.
      checkForAWinner(v,col) ;

      selectNextPlayer() ;
    }
    else
    {
      // LATER: Beep Beep
    }
  }


  //-------------------------------------------------------------------------
  // mouseReleased()
  //
  //-------------------------------------------------------------------------
  public void mouseReleased( MouseEvent mevt )
  {
    //debugPrintln( "Mouse released listener" ) ;
  }

  //--------------------------------------------------------------------
  // selectNextPlayer()
  //
  // Figure out whose turn it is and enable that player.
  //
  //--------------------------------------------------------------------
  private void selectNextPlayer()
  {
    if ( currentPlayer == GameSquare.P1 )
    {
      currentPlayer = GameSquare.P2 ;
      currentColor.setBackground( GameSquare.P2_COLOR ) ;
    }
    else
    {
      currentPlayer = GameSquare.P1 ;
      currentColor.setBackground( GameSquare.P1_COLOR ) ;
    }

    // if computer players turn , trigger his move now.
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
    boolean dbg = false ;

    if ( args.length >= 1 )
    {
      if ( args[0].equalsIgnoreCase("-d") )
      {
        dbg = true ;
        System.out.println( "Debug mode enabled." ) ;
      }
    }

    c4 c4Frame ;

    c4Frame = new c4( "Connect Four", dbg ) ;

    c4Frame.setSize( 300,300 ) ;
    c4Frame.show() ;
  }
}

//---------------------------------------------------------------
// Class: GameSquare
//
// Simple class that implements a single square of the game board.
//
//
//
//---------------------------------------------------------------
class GameSquare extends Panel
{
  static final int P1   = 1 ;
  static final int P2   = 2 ;
  static final int NONE = 0 ;

  static final Color P1_COLOR = Color.red ;
  static final Color P2_COLOR = Color.yellow ;

  private int row ;
  private int col ;

  public  int owner ;

  //--------------------------------------------------------------------
  // Constructor #1
  //
  //--------------------------------------------------------------------
  public GameSquare()
  {
    super() ;

    owner = NONE ;
  }

  //--------------------------------------------------------------------
  // Constructor #2
  //
  //--------------------------------------------------------------------
  public GameSquare( int r, int c )
  {
    super();

    this.row = r ;
    this.col = c ;

    owner = NONE ;
  }

  //--------------------------------------------------------------------
  // getRow()
  //
  //--------------------------------------------------------------------
  public int getRow()
  {
    return this.row ;
  }

  //--------------------------------------------------------------------
  // getCol()
  //
  //--------------------------------------------------------------------
  public int getCol()
  {
    return this.col ;
  }

  //--------------------------------------------------------------------
  // setRow()
  //
  //--------------------------------------------------------------------
  public void setRow( int r )
  {
    this.row = r ;
  }

  //--------------------------------------------------------------------
  // setCol()
  //
  //--------------------------------------------------------------------
  public void setCol( int c )
  {
    this.col = c ;
  }

  //--------------------------------------------------------------------
  // setGridLocation()
  //
  //--------------------------------------------------------------------
  public void setGridLocation( int r, int c )
  {
    this.row = r ;
    this.col = c ;
  }

  //--------------------------------------------------------------------
  // paint()
  //
  //--------------------------------------------------------------------
  public void paint( Graphics g )
  {
    if ( this.owner != NONE )
    {
      paintSquare( g ) ;
    }
  }

  //--------------------------------------------------------------------
  // setOwner()
  //
  // Give this square an owner and draw the piece in the appropriate
  // color for the new owner.
  //--------------------------------------------------------------------
  public void setOwner( int newOwner )
  {
    this.owner = newOwner ;

    if ( newOwner != NONE)
    {
      paintSquare( this.getGraphics() ) ;
    }
    else
    {
      Dimension d = this.getSize() ;

      Graphics g = this.getGraphics() ;

      g.setColor( Color.black ) ;

      g.fillRect( 0,0,d.width,d.height ) ;
      //repaint() ;
    }
  }

  //--------------------------------------------------------------------
  // getOwner()
  //
  // Return details of who currently owns this square. The answer will
  // be one of : P1, P2 or NONE.
  //
  //--------------------------------------------------------------------
  public int getOwner()
  {
    return this.owner ;
  }


  //--------------------------------------------------------------------
  // paintSquare()
  //
  // Display a game piece in the appropriate colour for the current
  // owner of the game square.
  //--------------------------------------------------------------------
  public void paintSquare( Graphics g )
  {
    Dimension d ;

    g.setColor( (this.owner==P1) ? P1_COLOR : P2_COLOR  ) ;

    d = this.getSize() ;

    g.fillOval( 0,0,d.width,d.height ) ;
  }
}
