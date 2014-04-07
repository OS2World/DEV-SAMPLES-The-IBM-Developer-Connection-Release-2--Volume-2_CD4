//-----------------------------------------------------------------------------
// LEDPanel.java
//
// A class that uses the LEDCell class to implement a flexible
// LED display class that allows the construction of counter and
// calculator type applications.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   ??????
//
// Exports these methods:
//   getValue()
//   setValue()
//
// Author : Kelvin R Lawrence.     7-Oct-1997
//
// History:
//
// Later:
//  Could add an addCell() method to add more cells.
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;

//-------------------------------------------------------------------------
// Class: LEDPanel
//
// A custom panel class that allows multiple LEDCell objects to
// be displayed and manipulated together. This class is particularly
// useful for applications such as calculators,counters and other numeric
// display type purposes.
//
//-------------------------------------------------------------------------

public class LEDPanel extends Panel
{
  private static final int DEFAULT_CELLS = 10 ;
  private static final int DEFAULT_VALUE =  0 ;
  
  private int value    ; // The current value we are displaying.
  private int numCells ; // The number of cells we have.
  
  private LEDCell [] cells ;

  //-----------------------------------------------------------------------
  // Constructor #1
  //
  //
  //-----------------------------------------------------------------------
  public LEDPanel()
  {
    this( DEFAULT_CELLS ) ;
  }

  //-----------------------------------------------------------------------
  // Constructor #2
  //
  //
  //-----------------------------------------------------------------------
  public LEDPanel( int num )
  {
    this( num, DEFAULT_VALUE ) ;
  }
  
  //-----------------------------------------------------------------------
  // Constructor #3
  //
  //
  //-----------------------------------------------------------------------
  public LEDPanel( int num, int value )
  {
    this.setBackground( Color.black ) ;

    this.setLayout( new GridLayout( 1,num,5,0 ) );
    
    numCells = num ;

    //--------------------------------------------------------
    // Now create each of the individual cells and add them to 
    // our display panel.
    //--------------------------------------------------------

    cells = new LEDCell[numCells] ;
    
    for ( int i=0; i<numCells; i++ )
    {
      cells[i] = new LEDCell() ;

      this.add( cells[i] ) ;
    }
    
    setValue( value ) ;
  }
  
  //-----------------------------------------------------------------------
  // setValue()
  //
  //
  //-----------------------------------------------------------------------
  public void setValue( int value )
  {
    this.value = value ;
    
    renderValue() ;
  }
  
  //-----------------------------------------------------------------------
  // getValue()
  //
  //
  //-----------------------------------------------------------------------
  public int setValue()
  {
    return this.value ;
  }
  
  //-----------------------------------------------------------------------
  // renderValue()
  //
  // Figure out how to display the current value that the LEDPanel has been
  // asked to display. We need to break the number up into parts and set
  // up the individual LED cells as appropriate.
  //-----------------------------------------------------------------------
  private void renderValue()
  {
    int tmp1 ;
    int tmp2 ;
    int div  ;

    tmp1 = value ;
    div  = (int) Math.pow( (double)10, (double)numCells-1 ) ;
    
    for ( int i=numCells; i>0; i-- )
    {
      tmp2 = tmp1/div ;
      
      tmp1 = tmp1 - tmp2 * div ;
      div  = div/10 ;
      
      if ( cells[numCells-i].getValue() != tmp2 )
      {
        cells[numCells-i].setValue( tmp2 ) ;    
      }
    }
  }
}

