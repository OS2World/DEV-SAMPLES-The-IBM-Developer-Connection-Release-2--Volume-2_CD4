//---------------------------------------------------------------------------
//
//  Name     : polylots.c
//
//  Purpose  : Stress test and time the polygon API. 
//
//  Special Note: This program is carefully designed to test the polygon
//                drawing API. It is not well behaved and the system
//                will not be responsive while polygons are being drawn.
//
//             GPI functions used:
//
//             GpiSetColor
//             GpiSetCurrentPosition
//             GpiPolygons
//
//             Copyright (C) Kelvin R. Lawrence
//             26th-June-1996
// History:
//   4-Oct-96 -KRL- Added horizontal polygon tests.
//---------------------------------------------------------------------------

#define INCL_32
#define INCL_DOSDATETIME
#define INCL_GPI
#define INCL_WIN
#include <os2.h>

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "polylots.h"

//-----------------
// Global variables
//-----------------

HAB     hab       ; // Application anchor block handle
HWND    hwndFrame ; // Frame window handle
HWND    hwndMenu  ; // Menu bar handle

CHAR    pszTitleBar[80] ;
LONG    lNumReps    = 1 ;
LONG    idRep       = IDM_REPEAT1  ;
LONG    idDirection = IDM_VERT ;
LONG    idDrawMode  = IDM_POLYGONS ;

#define TITLEBARTEXT "polylots - Draw lots of polygons"
#define CLASSNAME    "polylots"

//--------------------------------------
// Local function prototype declarations
//--------------------------------------

MRESULT EXPENTRY ClientWndProc( HWND hwnd ,USHORT msg ,MPARAM mp1 ,MPARAM mp2 );
VOID DrawLotsOfPolygons( HPS hps, PRECTL prectl );

//--------------------------------------------------------------------------
//
//  main() procedure.
//   --> Initialize PM for this process
//   --> Create our message queue
//   --> Create frame and client windows
//   --> Show the window
//   --> Enter our message dispatching loop
//   --> Clean up and terminate when message loop exits
//
// --------------------------------------------------------------------------
int cdecl main(VOID)
{
   HMQ hmq;
   HWND hwndclient;
   QMSG qmsg;

   ULONG flCreateFlags =   FCF_BORDER     | FCF_SHELLPOSITION |
                           FCF_TASKLIST   | FCF_TITLEBAR      | FCF_SYSMENU |
                           FCF_SIZEBORDER | FCF_MINMAX        | FCF_MENU ;

   hab = WinInitialize( (USHORT)NULL );

   hmq=WinCreateMsgQueue( hab,0 );

   WinRegisterClass( hab
                   , CLASSNAME
                   , (PFNWP)ClientWndProc
                   , (ULONG)CS_SIZEREDRAW
                   , (USHORT)256 );

   hwndFrame = WinCreateStdWindow( HWND_DESKTOP
                                 ,  0UL
                                 ,  &flCreateFlags
                                 ,  CLASSNAME
                                 ,  TITLEBARTEXT
                                 ,  WS_VISIBLE
                                 ,  (HMODULE)0
                                 ,  ID_PROG
                                 ,  &hwndclient );

   WinShowWindow( hwndFrame, TRUE );

   hwndMenu = WinWindowFromID( hwndFrame, FID_MENU ) ;

   while ( WinGetMsg( hab,&qmsg, (HWND)0, 0, 0 ) )
   {
     WinDispatchMsg( hab,&qmsg );
   }

   if ( hwndFrame )
   {
     WinDestroyWindow(hwndFrame);
     WinDestroyMsgQueue(hmq);
     WinTerminate(hab);
   }
   return 0;
}

//--------------------------------------------------------------------------
//
//  Client window procedure.
//
// --------------------------------------------------------------------------
MRESULT EXPENTRY ClientWndProc(HWND hwnd, USHORT msg, MPARAM mp1, MPARAM mp2)
{
   switch (msg)
   {
     //---------------------------------------------------------------------
     //
     // WM_COMMAND
     //
     // Process the users menu choices
     //
     //---------------------------------------------------------------------
     case WM_COMMAND:
     {
       switch( SHORT1FROMMP( mp1 ) )
       {
         case IDM_REPEAT1:    
         case IDM_REPEAT5:    
         case IDM_REPEAT10:   
         case IDM_REPEAT25:   
         case IDM_REPEAT50:   
         case IDM_REPEAT100:  
         case IDM_REPEAT150:  
         case IDM_REPEAT200:  
         case IDM_REPEAT250:  
         case IDM_REPEAT300:
         {
           WinCheckMenuItem( hwndMenu, idRep, FALSE ) ;
           idRep = SHORT1FROMMP(mp1) ;
           lNumReps = idRep - IDM_REPEATBASE ;
           WinCheckMenuItem( hwndMenu, idRep, TRUE ) ;
           WinInvalidateRect( hwnd, NULL, FALSE ) ;
         }
         break ;

         case IDM_HORIZ:
         case IDM_VERT:
         {
           WinCheckMenuItem( hwndMenu, idDirection, FALSE ) ;
           idDirection = SHORT1FROMMP(mp1) ;
           WinCheckMenuItem( hwndMenu, idDirection, TRUE ) ;
           WinInvalidateRect( hwnd, NULL, FALSE ) ;
         }
         break ;

         case IDM_POLYGONS:
         case IDM_BOXES:
         {
           WinCheckMenuItem( hwndMenu, idDrawMode, FALSE ) ;
           idDrawMode = SHORT1FROMMP(mp1) ;
           WinCheckMenuItem( hwndMenu, idDrawMode, TRUE ) ;
           WinInvalidateRect( hwnd, NULL, FALSE ) ;
         }
         break ;


         case IDM_EXIT:
         {
           WinPostMsg( hwnd, WM_CLOSE, (MPARAM)0L, (MPARAM)0L );
         }
         break;
       }
     }
     break;

     //---------------------------------------------------------------------
     //
     // WM_PAINT
     //
     // Process paint requests
     //
     //---------------------------------------------------------------------
     case WM_PAINT:
     {
       HPS   hpsPaint ;
       RECTL rectl;

       hpsPaint = WinBeginPaint( hwnd, (HPS)0, &rectl );

       WinQueryWindowRect( hwnd, &rectl );

       WinFillRect( hpsPaint, &rectl, CLR_BLACK );

       DrawLotsOfPolygons( hpsPaint, &rectl );

       WinEndPaint( hpsPaint );
     }
     break;

     //---------------------------------------------------------------------
     //
     // default case
     //
     // Let PM handle messages that we are not interested in
     //
     //---------------------------------------------------------------------
     default:
     {
       return( WinDefWindowProc( hwnd, msg, mp1, mp2 )) ;
     }
   }
}

//--------------------------------------------------------------------------
//
// DrawLotsOfPolygons()
//
// Test performance of GpiPolygons call for individual polygons.
//
// --------------------------------------------------------------------------
VOID DrawLotsOfPolygons( HPS hps, PRECTL prectl )
{
  //------------------------
  // Polygon test parameters
  //------------------------

  #define POLY_XSTART   10 // Starting x of first polygon in a series
  #define POLY_XEND    110 // Ending x of last polygon in a series in horizontal mode
  #define POLY_WIDTH     3 // Width of a single polygon
  #define POLY_YSTART   50 // Starting y of first polygon in a series
  #define POLY_YHEIGHT 100 // Height of an individual polygon in vertical mode
  #define NUM_POLYS    150 // Number of polygons in a single series

  //-----------------------------------------------------------------
  // Calculate how many polygons we are about to draw when all of the
  // repetitions are completed.
  //-----------------------------------------------------------------

  #define NUM_FILLS   ( lNumReps * NUM_POLYS )

	 POINTL   ptlPoints[4];    // Coordinates of one polygon
  POLYGON  polygon ;        // Polygon definition for one polygon
  DATETIME dtStart, dtEnd;  // Timestamp structures

  LONG i          ; // Loop count for one series
  LONG rep        ; // Loop count for number of repetitions
  LONG lStartTime ; // Start time in seconds
  LONG lEndTime   ; // End time in seconds
  LONG lTimeTaken ; // Elapsed time in seconds

  //-----------------------------------
  // Get the time before we start a run
  //-----------------------------------

  DosGetDateTime( &dtStart ) ;

  //--------------------------------------------------
  // Set the mix to overpaint, not really necessary if 
  // the fill pattern is a solid one
  //--------------------------------------------------

  GpiSetBackMix( hps, BM_OVERPAINT ) ;

  //-------------------------
  // Perform our repetiotions
  //-------------------------

  for (rep = 0;  rep < lNumReps;  rep++) 
  {
    sprintf( pszTitleBar, "Drawing polygons ... (%3d)\0", lNumReps-rep ) ;
    WinSetWindowText( hwndFrame, pszTitleBar );

	   for ( i = 0; i < NUM_POLYS; i++ )
    {
      if ( idDirection == IDM_VERT )
      {
		      ptlPoints[0].x = ptlPoints[1].x = POLY_XSTART + (i * POLY_WIDTH) ;
		      ptlPoints[2].x = ptlPoints[3].x = ptlPoints[0].x + POLY_WIDTH ;
        ptlPoints[0].y = ptlPoints[3].y = POLY_YSTART ;
        ptlPoints[1].y = ptlPoints[2].y = ptlPoints[0].y + POLY_YHEIGHT ;
      }
      else  // IDM_HORIZ
      {
		      ptlPoints[0].x = ptlPoints[3].x = POLY_XSTART ;
		      ptlPoints[1].x = ptlPoints[2].x = ptlPoints[0].x + POLY_XEND ;
        ptlPoints[0].y = ptlPoints[1].y = POLY_YSTART + (i * POLY_WIDTH) ;
        ptlPoints[2].y = ptlPoints[3].y = ptlPoints[0].y + POLY_WIDTH ;
      }
    
      polygon.ulPoints = 4;
      polygon.aPointl  = ptlPoints ;     
     
      GpiSetCurrentPosition( hps, ptlPoints ) ;
      GpiSetColor( hps, rand()%CLR_PALEGRAY ) ;
    
      if ( idDrawMode == IDM_POLYGONS )
      {
        GpiPolygons(  hps
                    , 1L
                    , &polygon
                    , POLYGON_BOUNDARY | POLYGON_WINDING
                    , POLYGON_INCL
                    );
      }
      else  // IDM_BOXES
      {
        GpiBox( hps, DRO_OUTLINEFILL, &ptlPoints[2], 0, 0 ) ;
      }
			 }
		}
  //-----------------------------------
  // Get the time at the end of the run
  //-----------------------------------

  DosGetDateTime( &dtEnd ) ;

  //---------------------------------------------------------------
  // Convert all times to seconds and calculate the delta, i.e. the
  // ellapsed time and print the results.
  //---------------------------------------------------------------

  lStartTime = dtStart.hours * 3600 + dtStart.minutes * 60 + dtStart.seconds ;
  lEndTime   = dtEnd.hours   * 3600 + dtEnd.minutes   * 60 + dtEnd.seconds ;

  lTimeTaken = lEndTime - lStartTime ;

  sprintf( pszTitleBar
         , " %4d polygons drawn in %4d seconds\0"
         , NUM_FILLS
         , lTimeTaken ) ;

  WinSetWindowText( hwndFrame, pszTitleBar ) ;
}
