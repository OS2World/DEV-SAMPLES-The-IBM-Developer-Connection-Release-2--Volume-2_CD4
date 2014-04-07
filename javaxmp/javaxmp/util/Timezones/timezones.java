//-----------------------------------------------------------------------------
// timezones.java
//
// A simple set of experiments with the Java 1.1 TimeZone class and other
// new NLS and Locale specific enhancements present in Java 1.1.
//
//
// Author : Kelvin R Lawrence.     16th-April-1997
//
//
// This program requires Java JDK level 1.1.0 or higher.
//
// Java classes used:
//
//      Date
//      Calendar
//      Locale
//      TimeZone
//
// History:
//
//-----------------------------------------------------------------------------

import java.util.*;
import java.util.Date;

class timezones
{
   
  //-----------------------------------------------------------------------
  // displayTime()
  //
  // Display the time for a given Calendar object.
  //
  //-----------------------------------------------------------------------
  static public void displayTime( Calendar cal )
  {
    TimeZone tz      = cal.getTimeZone() ;
    TimeZone tzLocal = TimeZone.getDefault() ;

    //--------------------------------------------------------------
    // Figure out if the given timezone is in daylight savings time.
    // BUG: Does not seem to be working ?
    //--------------------------------------------------------------
    boolean  localDST = tzLocal.inDaylightTime( new Date() ) ;
    boolean  calDST   = tz.inDaylightTime( new Date() ) ; 

    String ampm      = "am" ;
    String advstd    = "Standard Time" ;
    String currentID = tz.getID() ;
    
    if ( calDST )
    {
      advstd = "Daylight Time" ; 
    }

    //--------------------------------------------------------------
    // Calculate if it's morning or afternoon in the given timezone.
    //--------------------------------------------------------------
    if ( cal.get(cal.AM_PM) > 0 )
    {
      ampm = "pm" ;
    }

    int h = cal.get(cal.HOUR)   ;
    int m = cal.get(cal.MINUTE) ;
    int s = cal.get(cal.SECOND) ;

    if ( h == 0 )
    {
      h = 12 ;   
    }

    String hs = Integer.toString( h ) ;
    String ms = Integer.toString( m ) ;
    String ss = Integer.toString( s ) ;

    if ( h < 10 )
    {
      hs = "0" + hs ; 
    }

    if ( m < 10 )
    {
      ms = "0" + ms ; 
    }

    if ( s < 10 )
    {
      ss = "0" + ss ; 
    }

    System.out.println( "Time for Calendar in:" + currentID ) ;
    System.out.println( hs + ":" + ms + ":" + ss + " " + ampm + " " + advstd ) ;
  }

  //-----------------------------------------------------------------------
  // main()
  //
  // Program entry point when we are being run as a stand alone application.
  //
  //-----------------------------------------------------------------------
  static public void main( String[] args )
  {
     //------------------------------------------------------------------
     // Get a default TimeZone object that we can play with. The TimeZone
     // class cannot be instantiated by itself. Also get a Date object.
     //------------------------------------------------------------------

     TimeZone tz = TimeZone.getDefault() ;

     Date dt = new Date() ;

     //---------------------------------------------------------
     // Print the date as a string. BUG: Always prints in PDT ??
     //---------------------------------------------------------
     System.out.println( dt.toString() ) ;
 
     //-------------------------------------------------------------------
     // Query which time zone we are currently in.
     //
     //-------------------------------------------------------------------

     System.out.println( "Our current time zone is " + tz.getID() ) ;

     //-------------------------------------------------------------------
     // This test will check to see if the time zone that we are currently
     // in uses daylight savings time at all.
     //-------------------------------------------------------------------

     if ( tz.useDaylightTime() )
     {
       System.out.println( "Current time zone uses daylight savings time." ) ;
     }
     else
     {
       System.out.println( "Current time zone does not use daylight savings time." ) ;
     }

     //-----------------------------------------------------------------
     // This test will see if the time zone we are in is currently using
     // daylight savings time.
     //-----------------------------------------------------------------

     if ( tz.inDaylightTime( new Date() ) )
     {
       System.out.println( "Current time zone is in daylight savings time now." ) ;
     }
     else
     {
       System.out.println( "Current time zone is not in daylight savings time now." ) ;
     }

     //----------------------------------------------------------------------
     // This query will get us a list of all the available time zones, like,
     // GMT,EST,CST etc... that Java supports. We can then dump the list out.
     //----------------------------------------------------------------------

     String [] ids = tz.getAvailableIDs() ;

     System.out.println( "List of supported time zones..." ) ;

     for ( int i=0; i< ids.length; i++ )
     {
       System.out.println( ids[i] ) ;  
     }

     //----------------------------------------------------------------
     // Query the raw offset, that is not modified for daylight savings
     // time from GMT for the current time zone. Note that the raw
     // offset is returned in milliseconds.
     //----------------------------------------------------------------

     int gmtOffsetMillis  = tz.getRawOffset() ;
     int gmtOffsetMinutes = gmtOffsetMillis/ (1000 * 60 ) ;

     System.out.println( "Raw offset from GMT in millis is " + gmtOffsetMillis ) ;
     System.out.println( "Offset from GMT in minutes is " + gmtOffsetMinutes ) ;

     //------------------------------------------------------------------
     // Get an instance of a Calendar object. Use this to get the current
     // time. Note that the hour is returned in 12 hour format.
     //------------------------------------------------------------------

     Calendar cal = Calendar.getInstance();

     displayTime( cal ) ;

     //----------------------------------------------------------------------
     // Get the Date object back that represents the current time. Note that
     // most of the methods in the Date class are in fact deprecated. This
     // seems like a design flaw in Java 1.1. It is odd that the Calendar
     // class would return what is basically an almost fully deprecated
     // object to us.
     //----------------------------------------------------------------------

     Date dt2 = cal.getTime() ;

     System.out.println( "Time derived from deprectated Date methods:" ) ;
     
     int h = dt2.getHours()   ; //DEPRECATED
     int m = dt2.getMinutes() ; //DEPRECATED 
     int s = dt2.getSeconds() ; //DEPRECATED 

     System.out.println( h + ":" + m + ":" + s ) ;

     System.out.println( "AM_PM value follows (0=AM, 1=PM) ..." ) ;
     System.out.println( cal.get( cal.AM_PM ) ) ;

     //-------------------------------------------------------------
     // Now let's play with a combination of TimeZone, Calendar
     // and Locale objects to calculate the time in a few different
     // timezones around the World. It is unclear how daylight
     // savings time is handled here . (???)
     //-------------------------------------------------------------

     TimeZone gmt = TimeZone.getTimeZone( "GMT" ) ;  // GMT

     TimeZone hst = TimeZone.getTimeZone( "HST" ) ;  // Hawaiin
     TimeZone pst = TimeZone.getTimeZone( "PST" ) ;  // Pacific
     TimeZone mst = TimeZone.getTimeZone( "MST" ) ;  // Mountain
     TimeZone cst = TimeZone.getTimeZone( "CST" ) ;  // Central
     TimeZone est = TimeZone.getTimeZone( "EST" ) ;  // Eastern

     TimeZone aet = TimeZone.getTimeZone( "AET" ) ;  // Australia East
     TimeZone act = TimeZone.getTimeZone( "ACT" ) ;  // Australia Central
     TimeZone jst = TimeZone.getTimeZone( "JST" ) ;  // Japan
     TimeZone nst = TimeZone.getTimeZone( "NST" ) ;  // New Zealand

     Locale usa = new Locale( "US", "EN" ) ;

     Calendar gmtCal = Calendar.getInstance( gmt, usa ) ;

     Calendar hstCal = Calendar.getInstance( hst, usa ) ;
     Calendar pstCal = Calendar.getInstance( pst, usa ) ;
     Calendar mstCal = Calendar.getInstance( mst, usa ) ;
     Calendar cstCal = Calendar.getInstance( cst, usa ) ;
     Calendar estCal = Calendar.getInstance( est, usa ) ;

     Calendar jstCal = Calendar.getInstance( jst, usa ) ;
     Calendar actCal = Calendar.getInstance( act, usa ) ;
     Calendar aetCal = Calendar.getInstance( aet, usa ) ;
     Calendar nstCal = Calendar.getInstance( nst, usa ) ;

     displayTime( gmtCal ) ;

     displayTime( hstCal ) ;
     displayTime( pstCal ) ;
     displayTime( mstCal ) ;
     displayTime( cstCal ) ;
     displayTime( estCal ) ;

     displayTime( jstCal ) ;
     displayTime( actCal ) ;
     displayTime( aetCal ) ;
     displayTime( nstCal ) ;
  }
}
