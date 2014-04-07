//-----------------------------------------------------------------------------
// WorldClocks.java
//
// A simple travel clock/world clock.
//
// This simple Java test application demonstrates how to query the current time
// relative to Greenwich Mean Time (GMT) and display the time in a few cities
// around the World.
//
// Also, this program can be run either as an applet or as a standalone
// Java application which might be of interest to some readers.
//
// This version requires Java JDK level 1.0.2 or higher.
//
//
// Author : Kelvin R Lawrence.     28th-January-1997
//
// History:
//   13-Oct-1997  -KRL- Fixed bug for Hawaii time (does not change).
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;
import java.util.*;

//---------------------------------------------------------------
// Class: Timer
//
// Simple timer class that will setup a timer using a secondary
// thread and notify the caller each time the timer "ticks".
//---------------------------------------------------------------
class Timer extends Thread
{
  WorldClocks clks ;

  boolean debugMode = false ;

  //--------------------------------------------------------------------
  //
  // Constructor: Timer( WorldClocks c, boolean dbg )
  //
  // Caller passes us a WorldClocks object that we will call back to when the
  // timer goes off by calling the timerNotify() method of the WorldClocks
  // object.
  //--------------------------------------------------------------------
  Timer( WorldClocks c, boolean dbg  )
  {
    clks = c ;

    debugMode = dbg ;
  }

  //--------------------------------------------------------------------
  // run()
  //
  // This method is automatically called when a new Timer object is
  // created as the Timer class extends Thread. In this example we
  // set ourselves up to generate timer "ticks" at one second intervals.
  //--------------------------------------------------------------------
  public void run()
  {
    try
    {
      if ( debugMode )
      {
        System.out.println( "Timer: Timer thread is running" ) ;
      }

      while( 1 == 1)
      {
        sleep( 1000 ) ;
        //System.out.println( "Timer: Tick-Tock" ) ;
        clks.timerNotify() ;
      }
    }
    catch( InterruptedException e )
    {
      return ;
    }
  }
}

//---------------------------------------------------------------
// Class: WorldClocks
//
// Simple class that shows the time in a few places around the
// World. This class uses the Timer class to generate one second
// timer notifications.
//
//---------------------------------------------------------------
public class WorldClocks extends Applet
{
  static final int NUM_CLOCKS = 12 ;
  static final int NUM_ROWS   =  4 ;
  static final int NUM_COLS   =  3 ;

  //-------------------------------------------------------------------------
  // Temporary "hack" until Java 1.1 Timezone class is available and works.
  // If it is daylight savings time in the UK then this value should be set
  // to true otherwise false. Note that in Java versions up to and including
  // Java 1.1.3 the Timezone class is buggy and incorrectly determines
  // daylight time for almost all time zones in the Southern hemisphere.
  // When that class is working correctly this program can be usefully
  // migrated to Java version 1.1. For now, we'll go with the hack.
  //
  // Still true as of Aug-14th-1997 (fails on JDK 1.1.4)
  // I have opened defect xxxxxxxx against Java Soft for this.
  //-------------------------------------------------------------------------
  static final boolean summerTimeUK = false ;

  //-----------------------------------------------------------------------
  // Define some constants that we can use to refer to certain geographic
  // locations. The names of these constants are based on the international
  // system of naming conventions used to refer to international airports.
  //
  //-----------------------------------------------------------------------
  static final int JFK =  0 ;  // New York City JFK
  static final int DFW =  1 ;  // Dallas Fort Worth
  static final int DEN =  2 ;  // Denver
  static final int LAX =  3 ;  // Los Angeles
  static final int HNL =  4 ;  // Honolulu
  static final int LHR =  5 ;  // London Heathrow
  static final int ORY =  6 ;  // Paris (Orly)
  static final int MOS =  7 ;  // Moscow
  static final int HKG =  8 ;  // Hong Kong
  static final int NRT =  9 ;  // Tokyo (Narita)
  static final int SYD = 10 ;  // Sydney
  static final int AKL = 11 ;  // Auckland NZ

  //--------------------------------------------------------
  // Setup timezones, relative to Greenwich Mean Time (GMT).
  // Note that depending upon the time of year, some of the
  // timezones will be in Daylight Savings Time (DST) which
  // means an additional hour will be added to GMT. As
  // mentioned in another comment when this program is
  // moved up to Java version 1.1 then the Timezone class
  // can do this work for us (when it is fully debugged by
  // JavaSoft).
  //--------------------------------------------------------

  static int[] timezones = {       -5   // New York     EST
                                 , -6   // Dallas       CST
                                 , -7   // Denver       MST
                                 , -8   // LA           PST
                                 , -10  // Honolulu     HST
                                 , +0   // London       GMT
                                 , +1   // Paris        CET
                                 , +3   // Moscow
                                 , +8   // Hong Kong
                                 , +9   // Tokyo        JST
                                 , +11  // Sydney       AET
                                 , +13  // Auckland     NST
                                 };

  Timer timer_thread ;
  int[] hours ;

  ClockButton[] locations ;
  Button[] clocks ;
  Panel[]  panels ;

  //--------------------------------------------------------------
  // appletMode will be set to true if we are running as an applet.
  // If we are running as an application, appletMode will be set
  // to false by the main() method.
  //--------------------------------------------------------------

  boolean appletMode = true  ;
  boolean debugMode  = false ;

  //----------------------------------------------------------------
  // init()
  //
  // Init routine. Called whenever the applet is first loaded or by
  // the main() method if we are started as an application.
  //
  //----------------------------------------------------------------
  public void init()
  {
    if ( debugMode )
    {
      System.out.println( "init() method called" ) ;
    }

    setLayout( new GridLayout( NUM_ROWS,NUM_COLS ) ) ;

    //----------------------------------------------------------------------
    // If we are running as an applet, query information from the HTML file.
    // This is done as an example. At present we do not use this data later.
    //----------------------------------------------------------------------
    if ( appletMode )
    {
      System.out.println( "Running in applet mode" ) ;
      System.out.println( "Parameter Title = " + getParameter("Title") ) ;
    }

    //-----------------------------------------------------------------------------
    // Setup nine panels, each containg a button to display the place name and
    // a button to display the time in that location. Also set the initial colours
    // and fonts for the buttons. Finally add each panel we create to the frame.
    //-----------------------------------------------------------------------------
    hours     = new int[NUM_CLOCKS] ;
    locations = new ClockButton[NUM_CLOCKS] ;
    panels    = new Panel[NUM_CLOCKS] ;
    clocks    = new Button[NUM_CLOCKS] ;

    Font fnt = new Font( "TimesRoman", Font.BOLD, 24 ) ;

    for ( int i = 0; i < NUM_CLOCKS; i++ )
    {
      locations[i] = new ClockButton( i,debugMode ) ;
      clocks[i]    = new Button( "00:00:00" ) ;

      locations[i].setBackground( Color.yellow ) ;

      locations[i].setFont( fnt ) ;
      clocks[i].setFont( fnt ) ;

      //----------------------------------------------
      // Create a panel and add our new buttons to it.
      //----------------------------------------------
      panels[i] = new Panel() ;
      panels[i].setLayout( new GridLayout( 2,1 )) ;
      panels[i].add( locations[i] ) ;
      panels[i].add( clocks[i] ) ;

      //-------------------------------------
      // Now add this panel to the main grid.
      //-------------------------------------
      add( panels[i] ) ;

    }

    //---------------------------------------------------------------------
    // Kick off the timer thread which will notify us, at one second
    // intervals to update the clocks, by calling back to our timerNotify()
    // method.
    //---------------------------------------------------------------------
    timer_thread = new Timer( this, debugMode ) ;
    timer_thread.start() ;

    //------------------------------------------------------------------
    // As Java 1.0.2 does not have the Timezone classes that Java 1.1.1
    // has, if it is summer time in the UK then we adjust all of the GMT
    // offsets to allow for that. This is not an exact way of doing this
    // but it works for the specific cities we have chosen to use.
    // Ultimately this will be replaced by using the Java 1.1 Timezone
    // class.
    //------------------------------------------------------------------
    if ( summerTimeUK )
    {
      timezones[JFK] =  -4  ;
      timezones[DFW] =  -5  ;
      timezones[DEN] =  -6  ;
      timezones[LAX] =  -7  ;
      //timezones[HNL] =  -9  ; HAWAII does not adjust.
      timezones[LHR] =  +1  ;
      timezones[ORY] =  +2  ;
      timezones[MOS] =  +4  ;
      timezones[HKG] =  +8  ;
      timezones[NRT] =  +9  ;
      timezones[SYD] =  +10 ;
      timezones[AKL] =  +12 ;
    }
  }

  //--------------------------------------------------------------------
  // timerNotify()
  //
  // This method is invoked by the Timer object each time the timer
  // goes off.
  //--------------------------------------------------------------------
  public void timerNotify()
  {
    updateClocks() ;
  }

  //--------------------------------------------------------------------
  // updateClocks()
  //
  // Called whenever the clocks need updating. Typically this will be
  // when they are first created and each time they need updating (i.e.
  // at one second intervals ).
  //
  //--------------------------------------------------------------------
  public void updateClocks()
  {
     //----------------------------------------------------
     // Firstly, get the current time on the local machine.
     //----------------------------------------------------

     Date date = new Date() ;

     int h ; // current hours
     int m ; // current minutes
     int s ; // current seconds
     int tmp ;

     h  = date.getHours()   ;
     m  = date.getMinutes() ;
     s  = date.getSeconds() ;


     //--------------------------------------------------------------------------
     // Query the locale information on the local machine to get the offset
     // from GMT and calculate the current hour for the GMT timezone by applying
     // that offset. We can then do all of our subsequent calculations relative
     // to GMT. Note that the GMT offset is returned in minutes not hours. This
     // is because not all timezones are an exact number of whole hours apart.
     // Some (like those in parts of India) are a few hours and 30 minutes apart.
     // At present this program only displays time in timezones that are whole
     // numbers of hours apart. To support all of the Worlds timezones would
     // require a little extra work.
     //--------------------------------------------------------------------------
     int gmtOffset = date.getTimezoneOffset() ;
     int gmtHour   = ( h + gmtOffset/60 ) % 24 ;

     //System.out.println( "gmtOffset=" + gmtOffset ) ;
     //System.out.println( "gmtHour=" + gmtHour ) ;

     //-----------------------------------------------
     // TBD:
     //
     // Is daylight savings time in effect ?
     // Which countries change in which direction ?
     // Which timezone am I running in ?
     // What day of the year is it ?
     // Will do this the right way once Java 1.1.1
     // Timezone classes are available and working.
     //-----------------------------------------------

     //----------------------------------------------------------------
     // Calculate the time in the other timezones we are interested in.
     //----------------------------------------------------------------
     for ( int i = 0; i < NUM_CLOCKS; i++ )
     {
        tmp = ( gmtHour + timezones[i] ) % 24 ;

        if ( tmp < 0 )
        {
          tmp = tmp + 24 ;
        }

        hours[i] = tmp ;
     }

     //----------------------------------------
     // Update the time display for each clock.
     //----------------------------------------

     for ( int i = 0; i < NUM_CLOCKS; i++ )
     {
       clocks[i].setLabel( getTimeString( hours[i],m,s ) );
     }
  }


  //----------------------------------------------------------
  // action()
  //
  // Handle user actions.
  //
  // If a push button was pressed change the color of the
  // button. Also display the number of the button pressed on
  // the console window.
  //----------------------------------------------------------
  public boolean action( Event evt, Object what )
  {
    if ( debugMode )
    {
      System.out.println("Start of action() method");
      System.out.println( "Button pressed was : " + what ) ;
      //System.out.println( evt.toString() ) ;
    }

    if ( evt.id == Event.ACTION_EVENT )
    {
      for ( int i = 0; i < NUM_CLOCKS; i++ )
      {
        if ( evt.target == locations[i] )
        {
          locations[i].changePlace() ;
          break ;
        }
      }

    }
    else
    {
      return false ;
    }

    return true ;
  }

  //-----------------------------------------------------------------------
  // getTimeString()
  //
  // Takes three integers representing the curent time in hours, minutes
  // and seconds and returns a string (zero padded as necessary) that
  // represents that time (in standard 24 hour clock notation).
  //
  //-----------------------------------------------------------------------
  public String getTimeString( int h, int m, int s )
  {
    String hs,ms,ss ;

    if ( h < 10 )
    {
      hs = "0" + Integer.toString( h ) ;
    }
    else
    {
      hs = Integer.toString( h ) ;
    }

    if ( m < 10 )
    {
      ms = "0" + Integer.toString( m ) ;
    }
    else
    {
      ms = Integer.toString( m ) ;
    }

    if ( s < 10 )
    {
      ss = "0" + Integer.toString( s ) ;
    }
    else
    {
      ss = Integer.toString( s ) ;
    }

    return( hs + ":" + ms + ":" + ss ) ;
  }

  //-----------------------------------------------------------------------
  // main()
  //
  // Program entry point when we are being run as a stand alone application.
  //
  //-----------------------------------------------------------------------
  static public void main( String[] args )
  {
    boolean dbg = false ;

    if ( args.length >= 1 )
    {
      if ( args[0].equalsIgnoreCase("-d") )
      {
        dbg = true ;
        System.out.println( "Debug mode enabled" ) ;
        System.out.println( "Running in application mode" ) ;
        System.out.println( "Number of arguments = " + args.length ) ;
      }
    }

    ClockFrame f = new ClockFrame( "Java World Clocks" , dbg ) ;

    WorldClocks clks = new WorldClocks() ;

    //---------------------------------------------------------------
    // Tell this instance of the WorldClocks object that we are running
    // as a standalone application and invoke the init routine that
    // would be called if we were running as an applet.
    //---------------------------------------------------------------

    clks.appletMode = false ;
    clks.debugMode  = dbg ;

    clks.init() ;

    //-----------------------------------------------------------------
    // If we had a start method, and we were started as an application,
    // we would call the start() method here.
    //-----------------------------------------------------------------
    //clks.start() ;

    f.add( "Center", clks );
    f.resize( 300, 300 );
    f.show();
  }
}

//-----------------------------------------------------------------
// Class : ClockFrame
//
// Because we are a bi-modal application (i.e. we can be run as an
// applet or an application) we need to provide a simple frame
// subclass that allows us to catch the window close event when the
// user terminates us when we are running in application mode.
//-----------------------------------------------------------------
class ClockFrame extends Frame
{
  boolean debugMode = false ;

  //-------------
  // Constructor.
  //-------------
  ClockFrame( String s, boolean dbg )
  {
    super( s ) ;

    debugMode = dbg ;
  }

  //-------------------------------------------------------------------------
  // handleEvent()
  //
  // Handle events. We need this so that the close in the title bar will
  // actually close the application etc.
  //-------------------------------------------------------------------------
  public boolean handleEvent( Event evt )
  {
    if ( debugMode )
    {
      System.out.println( "handleEvent() method called." ) ;
      //System.out.println( evt.toString() ) ;
    }
    if ( evt.id == Event.WINDOW_DESTROY)
    {
      if ( debugMode )
      {
        System.out.println( "Application is terminating." ) ;
      }

      System.exit(0) ;
      return true ;
    }
    else
    {
      return( super.handleEvent( evt ) );
    }
  }
}

//-----------------------------------------------------------------
// Class : ClockButton
//
// Simple class that extends the base AWT Button class to produce
// a class that can keep track of the name of the place that the
// button represents and provide a way to rotate through a series
// of place names in the same time zone.
//-----------------------------------------------------------------
class ClockButton extends Button
{
  static final int NUM_PLACES_PER_TZ = 5 ;

  boolean debugMode = false ;

  //-----------------------------------------------------------
  // Names of places we will display for each of the timezones.
  //-----------------------------------------------------------
  static final String[] [] places =
    { { "New York"    , "Washington"    , "Miami"     , "Raleigh"     , "Atlanta"    }
    , { "Dallas"      , "Austin"        , "Chicago"   , "Houston"     , "St. Louis"  }
    , { "Denver"      , "Alberquerque"  , "Aspen"     , "Vail"        , "Cheyenne"   }
    , { "Los Angeles" , "San Francisco" , "San Diego" , "Seattle"     , "Portland"   }
    , { "Honolulu"    , "Maui"          , "Kuaui"     , "Lanai"       , "Hawaii"     }
    , { "London"      , "Hursley"       , "Dublin"    , "Manchester"  , "Birmingham" }
    , { "Paris"       , "Rome"          , "Brussels"  , "Madrid"      , "Berlin"     }
    , { "Moscow"      , "Leningrad"     , "Nairobi"   , "Addis Ababa" , "Moscow"     }
    , { "Hong Kong"   , "Singapore"     , "Beijing"   , "Manila"      , "Hong Kong"  }
    , { "Tokyo"       , "Seoul"         , "Nagasaki"  , "Yokohama"    , "Tokyo"      }
    , { "Sydney"      , "Melbourne"     , "Sydney"    , "Melbourne"   , "Sydney"     }
    , { "Auckland"    , "Wellington"    , "Wellington", "Auckland"    , "Auckland"   }
    } ;

  //--------------------------------------------------------------
  // Variables that keep track of the place name that a particular
  // button represents.
  //--------------------------------------------------------------
  int index = 0 ;  // Current place within a timezone we are displaying.
  int tz ;         // Timezone this button is in.

  //------------------------------------------------------------
  // Constructor: ClockButton( int i, boolean dbg )
  //
  // Create a button with the initial name being the first place
  // specified by the timezone value i.
  //------------------------------------------------------------
  ClockButton( int i, boolean dbg )
  {
    super( "" ) ;
    tz = i ;
    this.setLabel( places[tz][index] ) ;

    debugMode = dbg ;
  }


  //-------------------------------------------------------------------
  // changePlace()
  //
  // Update the index for the current place and display the name of the
  // next place in the list. Reset to the first place if we go off the
  // end. This provides an easy way for our parent class to cycle names
  // without having to track anything.
  //-------------------------------------------------------------------
  public void changePlace()
  {
    index++ ;

    if ( index > NUM_PLACES_PER_TZ - 1 )
    {
      index = 0 ;
    }

    if ( debugMode )
    {
      System.out.println( "changePlace() called, index= " + index ) ;
    }

    this.setLabel( places[tz][index] ) ;
  }
}

