//-----------------------------------------------------------------------------
// ProgressIndicator.java
//
// A simple example of a class that implements a progress indicator.
//
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   ??????
//
// Exports the following public methods:
//
//   getMode()
//   setMode()
//   getValue()
//   setValue()
//   getAppearance() 
//   setAppearance()
//   getStatusBarColor()
//   setStatusBarColor()
//   getStatusBarTextColor()
//   setStatusBarTextColor()
//
// Author : Kelvin R Lawrence.     6-Aug-1997
//
// History:
//  16-Sep-1997  -KRL-  Added new methods and 3D look options.
//
//-----------------------------------------------------------------------------
//package krl.java;

import java.awt.* ;
import java.awt.event.* ;

//---------------------------------------------------------------
// Class: ProgressIndicator
//
// Simple class that implements a status bar.
//
// Todo:
//  showText()
//  setBorderSize()
//---------------------------------------------------------------
/**
 * An implementation of a simple progress indicator that is useful to show
 * the progress of lengthy tasks such as loading files and searching
 * databases.
 * @author Kelvin R. Lawrence
 * @version 0.1 22-Sept-1997
 */
public class ProgressIndicator extends Panel
{

  public static final int VALUES_PERCENTAGE = 1 ;
  public static final int VALUES_ABSOLUTE   = 2 ;

  public static final int APPEARANCE_SUNKEN = 3 ;
  public static final int APPEARANCE_RAISED = 4 ;

  private static final int   DEFAULTBORDERWIDTH    = 2                ;
  private static final Color PROGRESSBORDERCOLOR1  = Color.black      ;
  private static final Color PROGRESSBORDERCOLOR2  = Color.gray       ;
  private static final Color STATUSBARBACKGNDCOLOR = Color.lightGray  ;
  private static final Color STATUSVALUECOLOR      = Color.blue       ;
  private static final Color STATUSTEXTCOLOR       = Color.white      ;

  private Panel StatusBar ;

  private int value       = 0 ;
  private int oldValue    = 0 ;
  private int mode        = 0 ;
  private int borderWidth = DEFAULTBORDERWIDTH   ;
  private int appearance  = APPEARANCE_SUNKEN    ;

  private boolean textVisible = true ;

  private Color topBorderColor     = PROGRESSBORDERCOLOR1 ;
  private Color botBorderColor     = PROGRESSBORDERCOLOR2 ; 
  private Color statusBarColor     = STATUSVALUECOLOR     ;
  private Color statusBarTextColor = STATUSTEXTCOLOR      ;

  //----------------------------------------------------------
  // Constructor
  //
  /**
   * Create a progress indicator with no initial value. This 
   * will in effect cause the value to be intialized to zero.
   * The mode will be defaulted to percentage mode.
   */
  //----------------------------------------------------------
  public ProgressIndicator()
  {
    this( 0, VALUES_PERCENTAGE ) ;
  }


  //----------------------------------------------------------
  // Constructor
  //
  /**
   * Create a progress indicator specifying its initial value.
   * The mode will be defaulted to percentage mode.
   * @param value The initial value setting of the status bar.
   * @see #setValue
   */
  //----------------------------------------------------------
  public ProgressIndicator( int value )
  {
    this( value, VALUES_PERCENTAGE ) ;
  }

  //----------------------------------------------------------
  // Constructor
  //
  /**
   * Create a progress indicator specifying the initial value and mode.
   * @param value The initial value setting of the status bar.
   * @param mode  The initial mode.
   * @see #setMode
   * @see #setValue
   */
  //----------------------------------------------------------
  public ProgressIndicator( int value, int mode )
  {
    super() ;

    this.mode = mode ;

    InitIndicator( value ) ;
  }

  //-------------------------------------------------------------------------
  // InitIndicator()
  //
  //-------------------------------------------------------------------------
  private void InitIndicator( int value )
  {
    enableEvents( AWTEvent.WINDOW_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK ) ;

    StatusBar = new Panel() ;

    this.setLayout( null ) ;

    StatusBar.setBackground( STATUSBARBACKGNDCOLOR ) ;
    StatusBar.setVisible( true ) ;

    this.value = value ;

    this.add( StatusBar ) ;
  }

  //-------------------------------------------------------------------------
  // setMode()
  //
  //-------------------------------------------------------------------------
  /**
  * Set the current mode to be either absolute or percentage mode.
  * @param newMode The mode being set.
  * @see #getMode
  */

  public void setMode( int newMode )
  {
    this.mode = newMode ;
  }

  //-------------------------------------------------------------------------
  // getMode()
  //
  /**
  * Get the value of the current mode.
  * @param none
  * @see #setMode()
  */
  //-------------------------------------------------------------------------
  public int getMode()
  {
    return this.mode ;
  }

  //-------------------------------------------------------------------------
  // setValue()
  //
  /**
  * Set the current value to be displayed in the status bar. This will
  * either be a percentage or an absolute value depending upong the current
  * mode.
  * @param newValue The value being set.
  * @see #setMode() 
  * @see #getValue()
  */
  //-------------------------------------------------------------------------
  public void setValue( int newValue )
  {
    this.oldValue = this.value ;
    this.value = newValue ;

    paintStatusBar() ;
  }

  //-------------------------------------------------------------------------
  // getValue()
  //
  //-------------------------------------------------------------------------
  public int getValue()
  {
    return this.value  ;
  }

  //-------------------------------------------------------------------------
  // setAppearance()
  //
  //-------------------------------------------------------------------------
  public void setAppearance( int appearance )
  {
    System.out.println( "setAppearance called with " + appearance ) ;

    switch ( appearance )
    {
      case APPEARANCE_RAISED:
      {
        topBorderColor = PROGRESSBORDERCOLOR2 ;
        botBorderColor = PROGRESSBORDERCOLOR1 ;
      }
      break ;

      case APPEARANCE_SUNKEN:
      {
        topBorderColor = PROGRESSBORDERCOLOR1 ;
        botBorderColor = PROGRESSBORDERCOLOR2 ;
      }
      break ;

      default:
      {
        System.out.println( "Invalid parameter passed to setAppearance()" ) ;
      }
    }

    repaint() ;
  }

  //-------------------------------------------------------------------------
  // getAppearance()
  //
  //-------------------------------------------------------------------------
  public int getAppearance()
  {
    return this.appearance ;
  }

  //-------------------------------------------------------------------------
  // setStatusBarColor()
  //
  //-------------------------------------------------------------------------
  public void setStatusBarColor( Color c )
  {
    this.statusBarColor = c ;

    paintStatusBar() ;
  }

  //-------------------------------------------------------------------------
  // getStatusBarColor()
  //
  //-------------------------------------------------------------------------
  public Color getStatusBarColor()
  {
    return this.statusBarColor ;
  }

  //-------------------------------------------------------------------------
  // setStatusBarTextColor()
  //
  //-------------------------------------------------------------------------
  public void setStatusBarTextColor( Color c )
  {
    this.statusBarTextColor = c ;

    paintStatusBar() ;
  }

  //-------------------------------------------------------------------------
  // getStatusBarTextColor()
  //
  //-------------------------------------------------------------------------
  public Color getStatusBarTextColor()
  {
    return this.statusBarTextColor ;
  }
  //-------------------------------------------------------------------------
  // showText()
  //
  //-------------------------------------------------------------------------
  public void showText( boolean show )
  {
    this.textVisible = show ;
  }
  //-------------------------------------------------------------------------
  // paintStatusBar()
  //
  // Update the status bar to display the current status either as a 
  // percentage or as an absolute value depending upon the current mode.
  //
  // If the mode is currently abbsolute (VALUES_ABSOLUTE) then we treat the
  // value variable as an absolute value and just fill the status bar from
  // zero up to that point. If the we are currently in percentage mode
  // (VALUES_PERCENTAGE) then we treat "value" as a percentage of the total
  // size of the status bar. This enables the application to let us figure
  // out how big the bar should be relative to the current size of the status
  // indicator window.
  //
  //-------------------------------------------------------------------------
  private void paintStatusBar()
  {
    int v ;

    Dimension d = StatusBar.getSize() ;
    Graphics  g = StatusBar.getGraphics() ;

    if ( this.value < this.oldValue )
    {
      g.setColor( STATUSBARBACKGNDCOLOR ) ;
  
      g.fillRect( value,0,d.width,d.height ) ;
    }

    g.setColor( statusBarColor ) ;

    if ( this.mode == VALUES_ABSOLUTE )
    {
      v = this.value ;
    }
    else
    {
      v = ( d.width * this.value ) / 100 ;
    }

    g.fillRect( 0,0,v,d.height ) ;

    //------------------------------------------------------------
    // Now, if appropriate, display the text representation of the
    // value as a percentage or as an absolute value.
    //------------------------------------------------------------

    if ( textVisible )
    {
      String str =  Integer.toString((int)value ) ;

      if ( this.mode == VALUES_PERCENTAGE )
      {
        str = str + "%" ;
      }

      g.setColor( statusBarTextColor ) ;
      g.drawString( str, 10,15 ) ;
    }
  }

  //-------------------------------------------------------------------------
  // paint()
  //
  //-------------------------------------------------------------------------
  public void paint( Graphics g )
  {
    //System.out.println( "Paint() called" ) ;

    Dimension d = this.getSize() ;

    g.setColor( STATUSBARBACKGNDCOLOR ) ;

    g.fillRect( 0,0,d.width,d.height ) ;


    //-----------------------------------------------------------------
    // Draw the top and left hand side borders of the status bar in
    // the appropriate color as defined by the current appearance mode.
    //-----------------------------------------------------------------
    g.setColor( topBorderColor ) ;

    //Top
    g.fillRect( 0,0,d.width,borderWidth ) ;

    //Left
    g.fillRect( 0,0,borderWidth,d.height-borderWidth ) ;

    //-----------------------------------------------------------------
    // Draw the bottom and right hand side borders of the status bar in
    // the appropriate color as defined by the current appearance mode.
    //-----------------------------------------------------------------
    g.setColor( botBorderColor ) ;

    //Bottom
    g.fillRect( 0,d.height-borderWidth,d.width,borderWidth ) ;

    //Right
    g.fillRect( d.width-borderWidth,borderWidth-1,borderWidth,d.height ) ;

    //-----------------------------------------------------------------
    // Adjust the size of the status bar so that we don't overwrite the
    // borders when we start filling it in.
    //-----------------------------------------------------------------
    StatusBar.setBounds( borderWidth
                       , borderWidth
                       , d.width-(borderWidth*2)
                       , d.height-(borderWidth*2) ) ;

    //-------------------------------------------------------------------
    // Having drawn the borders now fill in the appropriate amount of the
    // status bar for the current value setting.
    //-------------------------------------------------------------------
    paintStatusBar() ;

  }
}
