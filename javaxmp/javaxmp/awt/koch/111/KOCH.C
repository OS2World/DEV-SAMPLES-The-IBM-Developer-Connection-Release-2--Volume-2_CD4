//---------------------------------------------------------------------------
//
//  Name     : koch.c
//
//  Purpose  : Simple program that demonstrates a way to construct (to
//             various depths) the Koch snowflake curve fractal.
//
//             Note that for accuracy all calculations are done
//             using floating point values. 
//
//             See the function comment header blocks for exact details
//             of the algorithm used.
//
//  For any given depth, the number of lines to be drawn will
//  be as follows:
//
//           Depth  Number of lines
//
//             0            3
//             1           12
//             2           48
//             3          192
//             4          768
//             5         3072
//             6        12288
//             7        49152
//             8       196608
//
//  Or, alternatively, the number of lines at any depth can be
//  calculated using the formula:
//
//                   depth
//              3 * 4
//
// At greater depths, the amount of actual memory required to
// hold all of the line definitions will grow considerably.
// Each line will require four "double" data types to hold it.
// Additionally, before the lines are rendered, they are converted
// to integers and stored as two POINTLs (four LONGs) per line.
//
//             GPI functions used:
//
//             GpiSetColor
//             GpiSetCurrentPosition
//             GpiPolyLine
//             GpiBox
//             GpiBeginPath
//             GpiEndPath
//             GpiFillPath
//
//             Copyright (C) Kelvin R. Lawrence
//             9th-July-1996 (0.a)
//
// History:
//   Oct-21-1996 -KRL- Converted to use floating point maths.             0.b
//   Oct-31-1996 -KRL- New show all depths algorithm for screen division. 0.c
//   Nov-01-1996 -KRL- Allow choice of starting shapes.                   0.d
//
// Still to do:
//   Sierpinski triangle.
//   Multi threaded.
//   Status "processing" window.
//---------------------------------------------------------------------------

#define INCL_32
#define INCL_GPI
#define INCL_WIN
#include <os2.h>

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#include "koch.h"
#include "about.h"

#define ABS(a) (((a)<0) ? -(a) : (a))

#define KRL_DEBUG

//-----------------------------------------------
// Define simple structures to represent a double
// precision x,y point and a line . We cannot use
// the standard POINTL integer types as when we
// start iterating we will get divide by zero
// errors caused by rounding all calculations
// are done using DPOINTL types and converted
// to integers before the final lines are
// rendered to the screen.
//-----------------------------------------------

// Double precision POINTL

typedef struct _DPOINTL
{
  double x ;
  double y ;
} DPOINTL, *PDPOINTL ;


// Integer line definition

typedef struct _LINE_AB
{
  POINTL a ;
  POINTL b ;
} LINE_AB, *PLINE_AB ;

// Double precision line definition

typedef struct _DLINE_AB
{
  DPOINTL a ;
  DPOINTL b ;
} DLINE_AB, *PDLINE_AB ;



//--------------------------------------
// Local function prototype declarations
//--------------------------------------

MRESULT EXPENTRY ClientWndProc( HWND hwnd ,USHORT msg ,MPARAM mp1 ,MPARAM mp2 );
VOID DoKoch( HPS hps, PRECTL prectl, LONG lDepth, ULONG ulInitial, BOOL fSolid );
VOID ReplaceLine( PDLINE_AB ptlLine, PDLINE_AB ptlNew );
VOID ShowAllDepths( HPS hps, LONG lDepth, PRECTL prectl );

//-----------------
// Global variables
//-----------------

HAB     hab       ; // Application anchor block handle
HWND    hwndFrame ; // Frame window handle
HWND    hwndMenu  ; // Menu bar handle

LONG    lDepth     = 1 ;                   // Iteration depth.
ULONG   idCurDepth = IDM_DEPTH1  ;         // Current depth menu choice.
ULONG   idCurStyle = IDM_OUTLINE ;         // Current fill menu choice.
ULONG   idMode     = IDM_SHOWCURRENT ;     // Current mode menu choice.
ULONG   idShape    = IDM_TRIANGLE ;        // Current shape menu choice.
BOOL    fSolid     = FALSE ;               // Fill mode. TRUE = Solid, FALSE = Outline.

#define TITLEBARTEXT "koch - Von Koch Snowflake Fractal Curve"
#define CLASSNAME    "koch"

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
      SHORT m1 = SHORT1FROMMP( mp1 );
      switch( m1 )
      {
        case IDM_DEPTH0 :
        case IDM_DEPTH1 :
        case IDM_DEPTH2 :
        case IDM_DEPTH3 :
        case IDM_DEPTH4 :
        case IDM_DEPTH5 :
        case IDM_DEPTH6 :
        case IDM_DEPTH7 :
        case IDM_DEPTH8 :
        {
          lDepth = m1 - IDM_DEPTH0 ;
          WinCheckMenuItem( hwndMenu, idCurDepth, FALSE );
          idCurDepth = m1;
          WinCheckMenuItem( hwndMenu, idCurDepth, TRUE );
          WinInvalidateRect( hwnd, 0L, FALSE );
        }
        break;

        case IDM_SOLID:
        case IDM_OUTLINE:
        {
          WinCheckMenuItem( hwndMenu, idCurStyle, FALSE );
          idCurStyle = m1 ;
          WinCheckMenuItem( hwndMenu, idCurStyle, TRUE );

          fSolid = ( idCurStyle == IDM_SOLID ) ;

          WinInvalidateRect( hwnd, 0L, FALSE );
        }
        break;

        case IDM_SHOWCURRENT:
        case IDM_SHOWALL:
        case IDM_SHOWANIMATION:
        {
          WinCheckMenuItem( hwndMenu, idMode, FALSE );
          idMode = m1 ;
          WinCheckMenuItem( hwndMenu, idMode, TRUE );
          WinInvalidateRect( hwnd, 0L, FALSE );
        }
        break;

       case IDM_STRAIGHT:
       case IDM_TRIANGLE:
       {
         WinCheckMenuItem( hwndMenu, idShape, FALSE );
         idShape = m1 ;
         WinCheckMenuItem( hwndMenu, idShape, TRUE );
         WinInvalidateRect( hwnd, 0L, FALSE );
       }
       break;

       case IDM_ABOUT:
       {
         WinDlgBox( HWND_DESKTOP
                  , hwnd
                  , (PFNWP)AboutDlgProc
                  , 0
                  , IDD_ABOUT
                  , NULL
                  );

       }
       break;

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
    //   Fill the client window with black ( our drawing background color)
    //   Draw snowflakes in the currently selected mode
    //     - One big one
    //     - An animation of all snowflakes (full size)
    //     - A grid showing the construction sequence to the current depth
    //---------------------------------------------------------------------
    case WM_PAINT:
    {
      HPS   hpsPaint ;
      RECTL rectl;

      hpsPaint = WinBeginPaint( hwnd, (HPS)0, &rectl );

      WinQueryWindowRect( hwnd, &rectl );

      WinFillRect( hpsPaint, &rectl, CLR_BLACK );

      //--------------------------------------------------------------------
      // Either draw one large snowflake at the currently selected depth or
      // Show the construction of a series of depths.
      //--------------------------------------------------------------------

      GpiSetColor( hpsPaint, CLR_CYAN ) ;

      if ( idMode == IDM_SHOWCURRENT )
      {
        DoKoch( hpsPaint, &rectl, lDepth, idShape, fSolid );
      }
      else if ( idMode == IDM_SHOWANIMATION )
      {
        LONG i ;

        for ( i=0; i <= lDepth; i++ )
        {
          DoKoch( hpsPaint, &rectl, i, idShape, fSolid );
        }
      }
      else  // idMode == IDM_SHOWALL
      {
        if ( lDepth == 0 )
        {
          DoKoch( hpsPaint, &rectl, lDepth, idShape, fSolid ) ;
        }
        else
        {
          ShowAllDepths( hpsPaint, lDepth, &rectl ) ;
        }
      }

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
// DoKoch()
//
// Perform the iterations necessary to generate the fractal to the
// required depth. The fractal will either be drawn as an outline
// or as a filled shape based on the setting of fSolid.
//
// --------------------------------------------------------------------------
VOID DoKoch( HPS hps, PRECTL prectl, LONG lDepth, ULONG ulInitial, BOOL fSolid )
{
  PDLINE_AB pLines, pNew ;
  PDLINE_AB pl, pn ;
  PLINE_AB pOutput ;
  LONG  i,j;
  LONG  cLines ;

  if ( ulInitial == IDM_TRIANGLE )
  {
    const  double SIN_60 = 0.87 ;
    const  double COS_60 = 0.5  ;
    double dist ;

    cLines = 3 ;

    //--------------------------------------------------------------------
    // Set up three lines to define the initial equilateral triangle.
    // The calculations below attempt to generate the largest possble
    // equilateral triangle that will fit in the current client window
    // and still leave enough room for expansion as the fractal evolves.
    // The triangle will also be centred in the window.
    //--------------------------------------------------------------------

    pLines = malloc( cLines * sizeof(DLINE_AB)) ;

    dist = min( prectl->xRight - prectl->xLeft, prectl->yTop - prectl->yBottom)/5*4 ;

    pLines[0].a.x = (double) prectl->xLeft + ((prectl->xRight-prectl->xLeft-dist)/2) ;
    pLines[0].a.y = (double) ( prectl->yBottom + dist/3 ) ;
    pLines[0].b.x = pLines[0].a.x + dist * COS_60 ;
    pLines[0].b.y = pLines[0].a.y + dist * SIN_60 ;
    pLines[1].a.x = pLines[0].b.x ;
    pLines[1].a.y = pLines[0].b.y ;
    pLines[1].b.x = pLines[0].a.x + dist ;
    pLines[1].b.y = pLines[0].a.y ;
    pLines[2].a.x = pLines[1].b.x ;
    pLines[2].a.y = pLines[1].b.y ;
    pLines[2].b.x = pLines[0].a.x ;
    pLines[2].b.y = pLines[0].a.y ;
  }
  else  // ulInitial == IDM_STRAIGHT
  {
    //----------------------------------------------------
    // Setup the coordinates of the initial straight line.
    //----------------------------------------------------

    cLines = 1 ;

    pLines = malloc( cLines * sizeof(DLINE_AB)) ;

    pLines[0].a.x = prectl->xLeft ;
    pLines[0].a.y = prectl->yBottom + (( prectl->yTop - prectl->yBottom ) / 4) ;
    pLines[0].b.x = prectl->xRight ;
    pLines[0].b.y = pLines[0].a.y ;
  }
  pl = pNew = pLines ;

  //----------------------------------------------------
  // Iterate and generate the new lines. If the depth is
  // zero we just draw the starting triangle.
  //----------------------------------------------------

  for ( i=1; i <= lDepth; i++ )
  {

#ifdef KRL_DEBUG
    printf( "Starting iteration : %4d\n" , i ) ;
    printf( "=========================\n\n" );
#endif

    //----------------------------------------------------
    // Allocate a buffer big enough to hold the new points
    // we are about to generate. Each new set of lines
    // will have four times as many lines as the previous
    // set.
    //----------------------------------------------------

    pNew = malloc( sizeof(DLINE_AB) * cLines * 4 ) ;

    //------------------------------------------------------------------
    // Now break each line in the current line buffer into smaller lines
    //------------------------------------------------------------------

    for ( j=1, pn=pNew; j <= cLines; j++, pl++, pn+=4 )
    {
      ReplaceLine( pl, pn ) ;
    }

    free( pLines ) ;

    pl = pLines = pNew ;
    cLines = cLines * 4 ;
  }

  //------------------------------------------------------
  // Before we can render the lines we have to convert
  // the results of our computations from floating point
  // "double" values back to long integers so that the 
  // GPI line functions can draw the points.
  //------------------------------------------------------

  pOutput = malloc( cLines * 2 * sizeof(POINTL)) ;

  for ( i=0; i<cLines; i++ )
  {
    pOutput[i].a.x = (LONG) pNew[i].a.x ;
    pOutput[i].a.y = (LONG) pNew[i].a.y ;
    pOutput[i].b.x = (LONG) pNew[i].b.x ;
    pOutput[i].b.y = (LONG) pNew[i].b.y ;
  }

  //------------------------------------------------------
  // Now the iterations are over and the points have been
  // converted back to integers draw the resultant lines.
  // If we are in outline mode just draw the lines. If
  // We need to render a solid filled shape then bracket
  // the line drawing inside a path and fill the path.
  //------------------------------------------------------

  if ( fSolid )
  {
    GpiBeginPath( hps, 1 ) ;
  }

  GpiSetCurrentPosition( hps, &pOutput[0].a ) ;

  GpiPolyLine( hps, cLines*2, &pOutput[0].a ) ;

  if ( idCurStyle == IDM_SOLID )
  {
    GpiEndPath( hps ) ;
    GpiFillPath( hps, 1, FPATH_ALTERNATE ) ;
  }

  free( pNew ) ;
  free( pOutput ) ;

}

//--------------------------------------------------------------------------
//
// ReplaceLine()
//
// Takes a line and applies the Koch substitution to the passed in line and
// generates the points that define four new lines.
//
//
// The line:                       would become:
//
//                                             E
//                                             +
//                                        ce  / \  de
//                                           /   \
//                                     ac   /     \   db
//   ---------------------          -------+       +-------
//   A         ab        B          A      C   F   D      B
//
// Where A,B,C,D,E are the points of the new lines, F is the midpoint of the line
// AB and ac, ce, de and da are the lengths of those respective lines.
// Note that the triangle CED will be an equilateral triangle with all
// sides equal in length to the lines AC and DB.
//
// This function assumes that the source line pointer ptlLine always points
// to three  DLINE_AB structures that define a straight line and also assumes
// the new lines pointer ptlNew points to a buffer big enough to hold four
// DLINE_AB structures (i.e. enough points to define four connected lines).
//
// Algorithm summary. The new points are generated as follows:
//
//   Point A = original point A
//   Point B = original point B
//   Point C = Point A + one third x and one third y
//   Point D = Point D - one third x and one third y
//   Point E = Use sine/cosine formulae to calculate
//
// We also have to take into account the slope of the line when generating
// point E. The slope of the line combined with the direction of the
// line will tell us on which side of the line AB the new point E resides.
//
// TRIGONOMETRY BACKGROUND RECAP:
//
// For those interested, the following describes the trigonometrical formulae
// used to help generate the new points.
//
// Most of the points in the new lines are trivial to generate and can be generated
// without using any trig functions or any kind of complex mathematics.
// The points A and B are unchanged in the new lines, the points C and D are
// derived simply by adding or subtracting one third the length of the line ab
// from A and B respectively, very straightforward. This is not quite true for
// the point E. This point has to be derived using simple trig formulae.
//
// Note that The length of each new line is one third the length of the old lines.
// In other words, the lines ac, ce, ed and db are all equal in length. Note also
// that the triangle CDE will always be an equilateral triangle and that the triangle
// formed by the points CFE will always have a right angle at the point F where the
// line FE (not shown above) meets the line AB.
//
// Given that we know the lengths of the lines CF and CE we know (pythagaros) that
// the length of the line FE can be found using the formula:
//
//                                    2     2
//                       FE = sqrt( CE  - CF  )
//
// However, we don't need to use line FE to find point E.
// We know that if we know the length of a line and its start point and the
// angle that line makes with another line, we can find the end point of the line
// using the formula:
//
//                       x2 = x1 + length * cosine(angle)
//                       y2 = y1 + length * sine(angle)
//
// We also know that for a right angled triangle, we don't need to know the angle
// if we know the length of the sides, to calculate the sine and cosine values. We
// can use the formulae (where C is the angle of the line ce)
//
//                       cosine(C) = cf/ce   (adjacent/hypoteneuse)
//                       sine(C)   = ef/ce   (opposite/hypoteneuse)
//
//
// As you will see in the code below, because we are dealing with equilateral
// triangles, the mathematics gets simplified quite a bit.
//
// We also look at the gradient (slope) of the line. The formula for a straight line
// is defined as y = mx + c where m is the gradient and c is the point where the line
// crosses the y axis. If we wanted to know the gradient of the line CE (for example)
// we would simply take the tangent of the angle. Tangent is defined as
// oppsoite/adjacent or in this case de/cd.  Another way to think of this is
// y difference/x difference.
//
// Put all of this together and you have an easy way to calculate the point E of the
// new line set.
//
// NOTE: I feel that this algorithm can be refined further given a little
// more time. I put this together to demo the basic concepts rather than
// the perfect way to draw Von Koch snowflakes.
// -----------------------------------------------------------------------------------
VOID ReplaceLine( PDLINE_AB ptlLine, PDLINE_AB ptlNew )
{
   double  x1,x2,y1,y2 ;
   double  xDelta, yDelta ;
   double  xSign, ySign, xStart, yStop ;
   DPOINTL midpoint ;

   double gradient, ac, af, cf, fe, s1, s2 ;

#ifdef KRL_DEBUG
   printf("Entering ReplaceLine() : x1= %f y1 = %f x2 = %f y2 = %f\n\n"
          , ptlLine->a.x , ptlLine->a.y
          , ptlLine->b.x , ptlLine->b.y  );
#endif

   xSign = ySign = 1 ;

   //------------------------------------------------------------
   // The first point of our new set of lines and the last point
   // are easy to derive. They will be the same as for the passed
   // in line that we are replacing.
   //------------------------------------------------------------

   ptlNew[0].a.x = ptlLine->a.x ;    // Point A
   ptlNew[0].a.y = ptlLine->a.y ;

   ptlNew[3].b.x = ptlLine->b.x ;    // Point B
   ptlNew[3].b.y = ptlLine->b.y ;

   //-----------------------------------------------------------
   // Calculate one third of the x and y distance along the line
   //-----------------------------------------------------------

   //xDelta = (double)(((ptlLine->b.x - ptlLine->a.x)  / 3 ) +0.5 ) ;
   //yDelta = (double)(((ptlLine->b.y - ptlLine->a.y)  / 3 ) +0.5 ) ;

   xDelta = (double)((ptlLine->b.x - ptlLine->a.x)  / 3 )  ;
   yDelta = (double)((ptlLine->b.y - ptlLine->a.y)  / 3 )  ;

   //-----------------------------------------------------
   // Now calculate the points for the ending of the first
   // new line, the starting point of the second line and
   // the starting point of the last line.
   //-----------------------------------------------------

   ptlNew[0].b.x =  ptlNew[1].a.x = ptlLine->a.x + xDelta ;  // Point C
   ptlNew[0].b.y =  ptlNew[1].a.y = ptlLine->a.y + yDelta ;

   ptlNew[3].a.x =  ptlNew[2].b.x = ptlLine->b.x - xDelta ;  // Point D
   ptlNew[3].a.y =  ptlNew[2].b.y = ptlLine->b.y - yDelta ;

   //-------------------------------------------------------------------
   // Finally prepare to calculate point E, the peak of the new triangle.
   // We first calculate the midpoint of the line AB. The temp values
   // x1,x2, y1,y2 are used for code readability to represent the
   // coordinates of the original line.
   //--------------------------------------------------------------------

   x1 = ptlLine->a.x ; y1 = ptlLine->a.y ;
   x2 = ptlLine->b.x ; y2 = ptlLine->b.y ;

   midpoint.x =   min(x2,x1) + ABS((( ptlLine->b.x - ptlLine->a.x ) /2 )) ;
   midpoint.y =   min(y2,y1) + ABS((( ptlLine->b.y - ptlLine->a.y ) /2 )) ;

   //-----------------------------------------------------------------------
   // Calculate the length of the line ac which is the same as the length of
   // the line ce (see function comment block ).
   //-----------------------------------------------------------------------

   s1 = (double) ABS( ptlNew[1].a.x - ptlLine->a.x ) ;
   s2 = (double) ABS( ptlNew[1].a.y - ptlLine->a.y ) ;

   ac = sqrt( s1 * s1 + s2 * s2 ) ;

   //-------------------------------------------------------------------------
   // Calculate the length of the line cf by calculating the length of the line
   // af and subtracting the length of the line ac.
   //--------------------------------------------------------------------------

   s1 = (double) ABS( midpoint.x - ptlLine->a.x ) ;
   s2 = (double) ABS( midpoint.y - ptlLine->a.y ) ;

   af = sqrt( s1 * s1 + s2 * s2 ) ;
   cf = af - ac ;

   //-------------------------------------
   // Calculate the length of the line fe.
   //-------------------------------------

   fe = sqrt( ac * ac - cf * cf ) ;

   //-----------------------------------------------------------------
   // We need to take into account the gradient (slope) of the line AB.
   // This is so that we can decide which side of the line the point E
   // lies. The gradient (same as TAN angle A) is derived by dividing
   // the y difference by the x difference between the points A and B.
   // We also have to allow for the direction of the line. We adjust
   // the starting point of our point Ecalculation also based on the
   // gradient. We special case the situation where the line AB is
   // horizontal, i.e. gradient=0.
   //
   // WARNING: The if tests that follow appear as if they can be
   // compressed. Do not do this! They must be in the order they are
   // in for the xSign calculations to be valid.
   //-----------------------------------------------------------------

   gradient = (double) (( y2-y1 ) / ( x2-x1 )) ;

   if ( gradient !=0 )
   {
     if ( gradient > 1 || gradient > -1 )
     {
       xSign = -1 ;
     }

     if ( x2 < x1 && gradient != 0 )
     {
       xSign  = -xSign ;
     }

     if ( gradient < 0 )
     {
       xStart = ptlNew[3].a.x ;
       yStop  = ptlNew[1].a.y ;
     }
     else
     {
       xStart = ptlNew[1].a.x ;
       yStop  = ptlNew[3].a.y ;
     }

     //----------------------------------------------------------
     // Now calculate the point E using the sine/cosine formulae.
     // See function comment block for more details.
     //----------------------------------------------------------

     ptlNew[1].b.x = xStart + (( (cf/ac) * ac) * xSign  )   ;
     ptlNew[1].b.y = yStop ;
   }
   else  // Gradient is zero
   {
     if ( x2 < x1 )
     {
       ySign  = -1 ;
     }
     //----------------------------------------------
     // Calculate point E for the zero gradient case.
     //----------------------------------------------

     ptlNew[1].b.x = midpoint.x  ;
     ptlNew[1].b.y = midpoint.y + fe * ySign ;
   }

   ptlNew[2].a.x = ptlNew[1].b.x ;
   ptlNew[2].a.y = ptlNew[1].b.y ;


#ifdef KRL_DEBUG
   printf("Leaving ReplaceLine() : New points follow: \n\n" );
   printf("(%f,%f),(%f,%f),(%f,%f),(%f,%f)\n"
          "(%f,%f),(%f,%f),(%f,%f),(%f,%f)\n\n"
         ,ptlNew[0].a.x, ptlNew[0].a.y
         ,ptlNew[0].b.x, ptlNew[0].b.y
         ,ptlNew[1].a.x, ptlNew[1].a.y
         ,ptlNew[1].b.x, ptlNew[1].b.y
         ,ptlNew[2].a.x, ptlNew[2].a.y
         ,ptlNew[2].b.x, ptlNew[2].b.y
         ,ptlNew[3].a.x, ptlNew[3].a.y
         ,ptlNew[3].b.x, ptlNew[3].b.y
         );
#endif
}


//--------------------------------------------------------------------------
//
// ShowAllDepths()
//
// Display all evolutions of the Koch Snowflake from depth 0 to the 
// specified depth. This function calculates how best to divide up the
// client window and display each of the iterations of the Koch Snowflake
// from zero to the specified depth.
//
//--------------------------------------------------------------------------
VOID ShowAllDepths( HPS hps, LONG lDepth, PRECTL prectl )
{
  LONG   i,xDelta, yDelta ;
  RECTL  rectlCurrent ;
  POINTL ptlCentre ;
  LONG   lRects ;
  CHAR   ch ;

  #define ASCII_ZERO 48

  //-------------------------------------------------------
  // Calculate how many rectangles we will need to draw and
  // round up to the nearest multiple of two.
  //-------------------------------------------------------

  lRects = ( (lDepth + 2) & 0xFFFFFFFE ) ;

  //-------------------------------------------------------
  // Calculate how wide and how tall each rectangle needs
  // to be.
  //-------------------------------------------------------

  xDelta = prectl->xRight / (lRects/2) ;
  yDelta = prectl->yTop   / 2 ;

  //-------------------------------------------------------
  // Calculate the coordinates of the first rectangle.
  //-------------------------------------------------------

  rectlCurrent.xLeft   =  prectl->xLeft ;
  rectlCurrent.yBottom =  prectl->yTop - yDelta ;
  rectlCurrent.xRight  =  prectl->xLeft + xDelta ;
  rectlCurrent.yTop    =  prectl->yTop  ;

  //-------------------------------------------------------
  // Now draw each snowflake in turn up to the specified
  // depth.
  //-------------------------------------------------------

  for ( i=0 ; i <= lDepth ; i++ )
  {
    GpiSetColor( hps, CLR_YELLOW ) ;
    GpiSetCurrentPosition( hps, (PPOINTL)&rectlCurrent ) ;
    GpiBox( hps, DRO_OUTLINE, (PPOINTL)&rectlCurrent.xRight, 0, 0 );
    GpiSetColor( hps, CLR_CYAN ) ;
    DoKoch( hps, &rectlCurrent, i, idShape, fSolid ) ;

    //----------------------------------------------------------
    // Number this snowflake at the centre point of its bounding
    // rectangle so the order of construction is obvious.
    //----------------------------------------------------------

    if ( idCurStyle == IDM_SOLID )
    {
      GpiSetColor( hps, CLR_BLACK ) ;
    }
    else
    {
      GpiSetColor( hps, CLR_WHITE ) ;
    }
    ptlCentre.x = rectlCurrent.xLeft   + ( rectlCurrent.xRight - rectlCurrent.xLeft ) / 2 ;
    ptlCentre.y = rectlCurrent.yBottom + ( rectlCurrent.yTop   - rectlCurrent.yBottom ) / 2 ;
    ch = (CHAR) ASCII_ZERO + i ;
    GpiCharStringAt( hps, &ptlCentre, 1, &ch ) ;

    //-------------------------------------------------------
    // Calculate the coordinates of the next rectangle and if
    // we have reached the end of the line then wrap to the 
    // next line.
    //-------------------------------------------------------
    
    rectlCurrent.xLeft  += xDelta ;
    rectlCurrent.xRight += xDelta ;

    if ( i+1 == lRects/2 )
    {
      rectlCurrent.xLeft    = prectl->xLeft ;
      rectlCurrent.xRight   = prectl->xLeft + xDelta ;
      rectlCurrent.yTop    -= yDelta ;
      rectlCurrent.yBottom -= yDelta ;
    }
  }
}


