//-----------------------------------------------------------------------------
// LEDClockPanel.java
//
// A class that uses the LEDPanel class to implement the basis of a
// simple digital clock display.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   ??????
//
// Exports these methods:
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
// Class: LEDClockPanel
//
// Simple class that uses the LEDPanel class to help build a simple
// time display panel that can display time in 12 or 24 hour formats.
// This class is not a full "clock" in that it does not track time
// itself but relies on the caller to do that.
//
//-------------------------------------------------------------------------
public class LEDClockPanel extends Panel
{
  private static final int MODE_12H = 1 ;
  private static final int MODE_24H = 2 ;
  
  private int hours   ;
  private int minutes ;
  private int seconds ;
  private int mode    ;
  
  private LEDPanel     hoursPanel   ;
  private LEDPanel     minutesPanel ;
  private LEDPanel     secondsPanel ;
  private LEDCell      filler1      ;
  private LEDCell      filler2      ;
  
  private GridBagConstraints constraints ;
  private GridBagLayout      gridBag     ;
  

  //-----------------------------------------------------------------------
  // Constructor #1
  //
  //
  //-----------------------------------------------------------------------
  public LEDClockPanel()
  {
    mode = MODE_12H ;
    
    hoursPanel   = new LEDPanel(2,0) ;
    minutesPanel = new LEDPanel(2,0) ;
    secondsPanel = new LEDPanel(2,0) ;

    filler1 = new LEDCell(5,5,-1,true) ;
    filler2 = new LEDCell(5,5,-1,true) ;
    
    //-----------------------------------------------------
    // Setup the appropriate constraints and initialise our
    // GridBagLayout manager.
    //-----------------------------------------------------
                                    
    constraints = new GridBagConstraints() ;
    gridBag     = new GridBagLayout() ;
                                             
    this.setLayout( gridBag ) ;
    
    this.setBackground( Color.black ) ;
    
    constraints.fill = constraints.BOTH ;
    constraints.gridheight = constraints.REMAINDER ;

    constraints.weightx = 2.0 ;
    constraints.weighty = 1.0 ;
    
    gridBag.setConstraints( hoursPanel, constraints ) ;
    this.add( hoursPanel) ;
    
    constraints.weightx = 1.0 ;
    gridBag.setConstraints( filler1, constraints ) ;
    this.add( filler1 ) ;
    
    constraints.weightx = 2.0 ;
    gridBag.setConstraints( minutesPanel, constraints ) ;
    this.add( minutesPanel ) ;

    constraints.weightx = 1.0 ;
    gridBag.setConstraints( filler2, constraints ) ;
    this.add( filler2 ) ;
    
    constraints.gridwidth  = constraints.REMAINDER ;                                   
    constraints.weightx = 2.0 ;
    gridBag.setConstraints( secondsPanel, constraints ) ;
    this.add( secondsPanel ) ;
  }
  
  
  //-----------------------------------------------------------------------
  // setTime()
  //
  // Set the current time that the clock panel will display.
  //
  //-----------------------------------------------------------------------
  public void setTime( int h, int m, int s )
  {
    this.hours   = h ;
    this.minutes = m ;
    this.seconds = s ;
    
    //LATER: Adjust for mode.
    
    hoursPanel.setValue( h ) ;
    minutesPanel.setValue( m ) ;
    secondsPanel.setValue( s ) ;
  }
  
  //-----------------------------------------------------------------------
  // setMode()
  //
  // Set the current mode to be in either 12 or 24 hour format.
  //
  //-----------------------------------------------------------------------
  public void setMode( int mode )
  {
    this.mode = mode ;
  }
}

