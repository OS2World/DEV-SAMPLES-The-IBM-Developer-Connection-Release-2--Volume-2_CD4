/*static char *SCCSID = "@(#)gpihlp.c   6.2 91/09/01";*/
/************************************************************************/
/*                                                                      */
/* module name        Gpihlp                                            */
/*                                                                      */
/* description        Transform helper routines :-                      */
/*                                                  GpiTranslate        */
/*                                                  GpiRotate           */
/*                                                  GpiScale            */
/*                                                  (exported API)      */
/*                                                                      */
/*                                                  GpiValidate         */
/*                                                  (internal routine)  */
/*                                                                      */
/* 02021989 copyright IBM Corp. 1989. Program property of IBM,          */
/* refer to copyright instructions form number G120-2083                */
/*                                                                      */
/* CHANGE ACTIVITY:                                                     */
/* FLAG REASON   RLSE DATE   ORIGIN   COMMENTS                          */
/* ---- -------- ---- ------ ------- ---------------------------------- */
/* @P1= PTR7565  r121 891003 HDGPL :  Reduce conforming segment sizes   */
/* @P2= SM08654  R202 920122 DLO   :  GpiRotate does not rotate         */
/* GPIB PTR12067 R205 920506 v$IvanB  Conversion to IBM C Set/2         */
/*137791 137791 wp.ps10 9509 Ray Yee  change fxmultiply to double       */
/* @TN  114416   ps10 951004 TANNGO   Avoid traps and define errors     */
/************************************************************************/

#include <GPINAMES.H>
#define INCL_32
#define INCL_SHLERRORS
#define INCL_GPI
#define INCL_NOCOMMON
#define INCL_ERRORS
#define INCL_WINSEI
#define INCL_GPITRANSFORMS
#include <os2.h>

#define INCL_GRE_XFORMS
#include <PMDDI.H>

#include <32pmgpi.h>                                            // GPIB
#include <gpihlp.h>
#include <aabf.h>
#include <gpictlf.h>

#define  FX_0_DEGREES            MAKEFIXED(0,0)                  /*@P2A*/
#define  FX_45_DEGREES           MAKEFIXED(45,0)                 /*@P2A*/
#define  FX_90_DEGREES           MAKEFIXED(90,0)                 /*@P2A*/
#define  FX_180_DEGREES          MAKEFIXED(180,0)                /*@P2A*/
#define  FX_270_DEGREES          MAKEFIXED(270,0)                /*@P2A*/
#define  FX_360_DEGREES          MAKEFIXED(360,0)                /*@P2A*/

/***********************************************************************
 *
 *
 *      GpiTranslate  -  Exported API. Applies a translation to a
 *                       transform matrix, setting it first to the
 *                       UNIT matrix first if required.
 *
 *
 *                       Validate HPS, Options and passed matrix, setting
 *                       matrix to the unit matrix if reqd.
 *                       If OK then update the MATRIXLF structure
 *                       directly.
 *
 *
 *
 *
 **********************************************************************/

BOOL APIENTRY GpiTranslate( HAB       hps
                          , PMATRIXLF pmatlfXform
                          , LONG      lOptions
                          , PPOINTL   pptlTrans
                          )
{
   XFORM    xfrmTarget;

   /*----------------------------------------------------------------*/
   /* This function is not valid with an Ink PS.                     */
   /*----------------------------------------------------------------*/

   if ( GpiQueryMicro( hps ) && GpiQueryInkPS( hps ) )
   {
       if (!ValidateDCHandle( hps ))
          LogError( PMERR_INV_HDC );
       else
          LogError( PMERR_INV_INKPS_FUNCTION );
       return ( FALSE );
   }

   //
   // Validate params & record errors
   // @TN

   if (pptlTrans == NULL)
   {
       LogError( PMERR_INVALID_PARM );
       return ( FALSE );
   }
   if (GpiValidate(hps,
                   pmatlfXform,
                   &xfrmTarget,
                   lOptions) == (HDC)NULL){
       return(FALSE);
   }
   /*
    * set up result in target matrix
    */
   pmatlfXform->fxM11 = xfrmTarget.fxM11;
   pmatlfXform->fxM12 = xfrmTarget.fxM12;
   pmatlfXform->fxM21 = xfrmTarget.fxM21;
   pmatlfXform->fxM22 = xfrmTarget.fxM22;
   pmatlfXform->lM31 = xfrmTarget.lM41 + pptlTrans->x;
   pmatlfXform->lM32 = xfrmTarget.lM42 + pptlTrans->y;

   return(TRUE);

}


/***********************************************************************
 *
 *
 *      GpiScale  -  Exported API.  Applies a scaling component to a
 *                   transform matrix about a given point. First setting
 *                   the matrix to the UNIT matrix if required.
 *
 *                   Validate the hps, options and passed matrix setting
 *                   it to unit matrix if required. Translate scaling
 *                   centre to origin, apply scaling by multiplying
 *                   matrixes and translate back to the scaling centre.
 *
 *
 **********************************************************************/

BOOL APIENTRY GpiScale( HAB       hps
                      , PMATRIXLF pmatlfXform
                      , LONG      lOptions
                      , PFIXED    afxScale
                      , PPOINTL   pptlCentre
                      )
{
   XFORM    xfrmSource;
   XFORM    xfrmTarget;
   HDC      hdc;

   /*----------------------------------------------------------------*/
   /* This function is not valid with an Ink PS.                     */
   /*----------------------------------------------------------------*/

   if ( GpiQueryMicro( hps ) && GpiQueryInkPS( hps ) )
   {
       if (!ValidateDCHandle( hps ))
          LogError( PMERR_INV_HDC );
       else
          LogError( PMERR_INV_INKPS_FUNCTION );
       return ( FALSE );
   }

   //
   // Validate passed params & record errors
   // @TN

   if((!pptlCentre) || (!afxScale))
   {
       LogError( PMERR_INVALID_PARM );
       return ( FALSE );
   }


   if ((hdc = GpiValidate(hps,
                          pmatlfXform,
                          &xfrmTarget,
                          lOptions)) == (HDC)NULL){
       return(FALSE);
   }
   /*
    * translate internal copy of matrix to origin
    */
   xfrmTarget.lM41 -= pptlCentre->x;
   xfrmTarget.lM42 -= pptlCentre->y;
   /*
    * set up scaling transform matrix and mutiply with
    * translated copy of passed matrix
    */
   xfrmSource.fxM11 = afxScale[0];
   xfrmSource.fxM12 = 0;
   xfrmSource.fxM21 = 0;
   xfrmSource.fxM22 = afxScale[1];
   xfrmSource.lM41 = 0;
   xfrmSource.lM42 = 0;
   if (GreMultiplyXforms(hdc, &xfrmTarget, &xfrmSource, CONCAT_AFTER)){
      /*
       * if successful fill out the target matrix with the
       * result, translating it back in the process
       */
       pmatlfXform->fxM11 = xfrmTarget.fxM11;
       pmatlfXform->fxM12 = xfrmTarget.fxM12;
       pmatlfXform->fxM21 = xfrmTarget.fxM21;
       pmatlfXform->fxM22 = xfrmTarget.fxM22;
       pmatlfXform->lM31 = xfrmTarget.lM41 + pptlCentre->x;
       pmatlfXform->lM32 = xfrmTarget.lM42 + pptlCentre->y;
       return(TRUE);
   } else {
       return(FALSE);
   } /* endif */
}


/***********************************************************************
 *
 *
 *      GpiRotate  -  Exported API.  Applies a rotation to a
 *                    transform matrix about a given point. First setting
 *                    the matrix to the UNIT matrix if required.
 *
 *                    Validate the hps, options and passed matrix setting
 *                    it to unit matrix if required. Map passed angle
 *                    into 0-360 range and get it's sine and cosine.
 *                    Translate scaling centre to origin, apply rotation
 *                    by multiplying matrixes and translate
 *                    back to the centre.
 *
 *
 *
 **********************************************************************/

BOOL APIENTRY GpiRotate( HAB       hps
                       , PMATRIXLF pmatlfXform
                       , LONG      lOptions
                       , FIXED     fxAngle
                       , PPOINTL   pptlCentre
                       )
{

   FIXED    fxSin, fxCos;
   HDC      hdc;
   XFORM    xfrmSource;
   XFORM    xfrmTarget;
   FIXED    fxOriginalAngle;                                     /*@P2A*/

   //
   // Validate passed params & record errors
   // @TN

   if (pptlCentre == NULL)
   {
       LogError( PMERR_INVALID_PARM );
       return ( FALSE );
   }

   /*----------------------------------------------------------------*/
   /* This function is not valid with an Ink PS.                     */
   /*----------------------------------------------------------------*/

   if ( GpiQueryMicro( hps ) && GpiQueryInkPS( hps ) )
   {
       if (!ValidateDCHandle( hps ))
          LogError( PMERR_INV_HDC );
       else
          LogError( PMERR_INV_INKPS_FUNCTION );
       return ( FALSE );
   }

   if ((hdc = GpiValidate(hps,
                          pmatlfXform,
                          &xfrmTarget,
                          lOptions)) == (HDC)NULL){
       return(FALSE);
   }

   /*
    * map angle into 0 - 360 range, ie no -ve angles or angles > 360
    */
   if (fxAngle < 0){
       fxAngle = -fxAngle;
       fxAngle = MAKEFIXED(360, 0) - MAKEFIXED(FIXEDINT(fxAngle)%360,
                                                  FIXEDFRAC(fxAngle));
   } else {
       fxAngle = MAKEFIXED(FIXEDINT(fxAngle)%360, FIXEDFRAC(fxAngle));
   } /* endif */

                      /* convert angle to quadrant zero     @P2A*/

      fxOriginalAngle = fxAngle;                          /*@P2A*/
      if (fxAngle > FX_90_DEGREES)                        /*@P2A*/
      {                                                   /*@P2A*/
                                                          /*@P2A*/
         if (fxAngle > FX_270_DEGREES)                    /*@P2A*/
         {                                                /*@P2A*/
            fxAngle = FX_360_DEGREES - fxAngle;           /*@P2A*/
         }                                                /*@P2A*/
         else if (fxAngle > FX_180_DEGREES)               /*@P2A*/
         {                                                /*@P2A*/
            fxAngle = fxAngle - FX_180_DEGREES;           /*@P2A*/
         }                                                /*@P2A*/
         else                                             /*@P2A*/
         {                                                /*@P2A*/
            fxAngle = FX_180_DEGREES - fxAngle;           /*@P2A*/
         }                                                /*@P2A*/
      }                                                   /*@P2A*/

   /*
    * obtain sine and cosine
    */

   fxSin = calculate_sine(fxAngle);                       /*@P2A*/
   fxCos = calculate_cosine(fxAngle);                     /*@P2A*/

     /* determine quadrant based on the original angle      @P2A*/

   if ((fxOriginalAngle > FX_90_DEGREES)                  /*@P2A*/
           &&                                             /*@P2A*/
           (fxOriginalAngle < FX_270_DEGREES))            /*@P2A*/
       {                                                  /*@P2A*/
          fxCos = -fxCos;                                 /*@P2A*/
       }                                                  /*@P2A*/
       if (fxOriginalAngle > FX_180_DEGREES)              /*@P2A*/
       {                                                  /*@P2A*/
          fxSin = -fxSin;                                 /*@P2A*/
       }                                                  /*@P2A*/


   /*
    * translate centre to origin
    */
   xfrmTarget.lM41 -= pptlCentre->x;
   xfrmTarget.lM42 -= pptlCentre->y;
   /*
    * set up rotation matrix and multiply with
    * translated copy of passed matrix
    */
   xfrmSource.fxM11 = fxCos;
   xfrmSource.fxM12 = fxSin;
   xfrmSource.fxM21 = -fxSin;
   xfrmSource.fxM22 = fxCos;
   xfrmSource.lM41 = 0;
   xfrmSource.lM42 = 0;
   if (GreMultiplyXforms(hdc, &xfrmTarget, &xfrmSource, CONCAT_AFTER)){
      /*
       * if successful fill out the target matrix with the
       * result, translating it back in the process
       */
       pmatlfXform->fxM11 = xfrmTarget.fxM11;
       pmatlfXform->fxM12 = xfrmTarget.fxM12;
       pmatlfXform->fxM21 = xfrmTarget.fxM21;
       pmatlfXform->fxM22 = xfrmTarget.fxM22;
       pmatlfXform->lM31 = xfrmTarget.lM41 + pptlCentre->x;
       pmatlfXform->lM32 = xfrmTarget.lM42 + pptlCentre->y;
       return(TRUE);
   } else {
       return(FALSE);
   } /* endif */



}


/***********************************************************************
 *
 *      GpiValidate  - Gets HDC associated with the HPS, check option
 *                     for either TRANSFORM_REPLACE or TRANSFORM_ADD.
 *                     If TRANSFORM_ADD checks validity of elements
 *                     lM13, lM23 and lM33, i.e. 0, 0 and 1 respectively.
 *                     Copy passed matrix into engine structure if
 *                     TRANSFORM_ADD, else sets it to the UNITY matrix.
 *                     If any check fails log error and return a NULL
 *                     HDC otherwise the assoicaited HDC.
 *
 **********************************************************************/
HDC GPI_PRIVATE GpiValidate (                                   // GPIB
                             HPS       hps,                     // GPIB
                             PMATRIXLF pmatlfXform,             // GPIB
                             PXFORM    pxfrmTarget,             // GPIB
                             LONG      lOptions                 // GPIB
                            )                                   // GPIB
{
   HDC hdc;

   //
   // Validate passed params
   //
   // @TN  gabages in gabages out !

   if ((pmatlfXform == NULL) || (pxfrmTarget == NULL))
   {
       LogError( PMERR_INV_MATRIX_ELEMENT );
       return ( FALSE );
   }

   /*----------------------------------------------------------------*/
   /* This function is not valid with an Ink PS.                     */
   /*----------------------------------------------------------------*/
   if ( GpiQueryMicro( hps ) && GpiQueryInkPS( hps ) )
   {
       if (!ValidateDCHandle( hps ))
          LogError( PMERR_INV_HDC );
       else
          LogError( PMERR_INV_INKPS_FUNCTION );
       return ( (HDC) NULL );
   }

   /*
    * get HDC from HPS
    */
   hdc = GpiQueryDevice(hps);
   if (hdc == (HDC) NULL) {

        WinSetErrorInfo32 (
                           MAKEERRORID (SEVERITY_WARNING, PMERR_INV_HDC),
                           SEI_NOBEEP
                          );
       return((HDC)NULL);
   } /* endif */

   if (hdc == HDC_ERROR){
       return((HDC)NULL);
   } /* endif */
   /*
    * check option param
    */
   if (lOptions != TRANSFORM_REPLACE &&
       lOptions != TRANSFORM_ADD) {

      WinSetErrorInfo32 (
                         MAKEERRORID (SEVERITY_WARNING, PMERR_INV_TRANSFORM_TYPE),
                         SEI_NOBEEP
                        );
       return ((HDC) NULL);
   }
   /*
    * check passed matrix elements and copy into engine structure
    * if TRANSFORM_ADD
    */
   if (lOptions == TRANSFORM_ADD) {
       if (pmatlfXform->lM13 != 0 ||
           pmatlfXform->lM23 != 0 ||
           pmatlfXform->lM33 != 1) {

            WinSetErrorInfo32( MAKEERRORID( SEVERITY_WARNING,
                                          PMERR_INV_MATRIX_ELEMENT )
                           , SEI_NOBEEP
                           );
           return((HDC)NULL);
       }
       pxfrmTarget->fxM11 = pmatlfXform->fxM11;
       pxfrmTarget->fxM12 = pmatlfXform->fxM12;
       pxfrmTarget->fxM21 = pmatlfXform->fxM21;
       pxfrmTarget->fxM22 = pmatlfXform->fxM22;
       pxfrmTarget->lM41  = pmatlfXform->lM31;
       pxfrmTarget->lM42  = pmatlfXform->lM32;
   /*
    * else set engine structure to UNITY matrix
    */
   } else {
       pxfrmTarget->fxM11 = UNITY;
       pxfrmTarget->fxM12 = 0;
       pxfrmTarget->fxM21 = 0;
       pxfrmTarget->fxM22 = UNITY;
       pxfrmTarget->lM41  = 0;
       pxfrmTarget->lM42  = 0;

       pmatlfXform->lM13 = 0;
       pmatlfXform->lM23 = 0;
       pmatlfXform->lM33 = 1;
   }

   return(hdc);
}


/*
 *    calculate_cosine()
 *
 *    This function computes cosine of the given angle using the
 *    trigonometric cofunction identity COS(angle) = SIN(90 - angle).
 *
 *    NOTE: IT IS ASSUMED THAT THE GIVEN ANGLE IS
 *             a. IN DEGREES;
 *             b. IN FIXED 16.16 FORMAT; AND
 *             c. >= 0 DEGREES AND <= 90 DEGREES.
 *
 *    History:
 *    05-Jan-92   Wes Bell @ MGX
 *       Rewrote it.
 *    22-Jan-92   DLO  added to GPI for use by GpiRotate     SM08654
 */

FIXED GPI_PRIVATE calculate_cosine (                            // GPIB
                                    FIXED fxTheta               // GPIB
                                   )                            // GPIB
{
   return (calculate_sine (FX_90_DEGREES - fxTheta));
}

/*
 *    calculate_sine()
 *
 *    This function computes sine of the given angle using
 *    interpolation and/or a trigonmetric table.
 *
 *    NOTE: IT IS ASSUMED THAT THE GIVEN ANGLE IS
 *             a. IN DEGREES;
 *             b. IN FIXED 16.16 FORMAT; AND
 *             c. >= 0 DEGREES AND <= 90 DEGREES.
 *
 *    History:
 *    05-Jan-92   Wes Bell @ MGX
 *       Rewrote it.
 *    22-Jan-92   DLO added to GPI for use by GpiRotate  SM08654
 *
 */

FIXED GPI_PRIVATE calculate_sine (                              // GPIB
                                  FIXED fxTheta                 // GPIB
                                 )                              // GPIB
{
   LONG  fl;
   FIXED fxSine;
   LONG  lArcSineTableIndex;
   QUAD  qWork;

   /// if SIN(fxTheta) canNOT be found in the trigonometric table

   if (fxTheta & ARC_SINE_TABLE_ANGLE_BITS_DELTA)
   {
      /// compute FLOOR(fxTheta) such that FLOOR(fxTheta) is a valid
      /// index into the trigonometric table

      lArcSineTableIndex = fxTheta >> ARC_SINE_TABLE_FRAC_BITS_DELTA;

      /// interpolate and return the computed result

      fxSine =
         fxmultiply (&qWork,
                     (LONG)(fxArcSineTable [lArcSineTableIndex + 1] -
                                  fxArcSineTable [lArcSineTableIndex]),
                     (LONG)(fxTheta & ARC_SINE_TABLE_ANGLE_BITS_DELTA),
                     &fl);
      return (fxSine + fxArcSineTable [lArcSineTableIndex]);
   }
   else
   {
      /// return the value for SIN(fxTheta) from the trigonometric table

      return (fxArcSineTable [fxTheta >> ARC_SINE_TABLE_FRAC_BITS_DELTA]);
   }
}

//------------------------------------------------------------------------
// 137791 - begin - changed from Long multiply to double multiply
//
//#ifdef POWERPC
///***********************************************************************/
///* fxmultiply                                                          */
///* Multiplies two 32 bit fixed point numbers.  FIXED is a signed 32    */
///* bit number with 16 bits of integer and 16 bits of fraction.         */
///* Returns : fixed format integer or NULL if GRE_OVERFLOW.  The QUAD   */
///* value will be unaffected.                                           */
///***********************************************************************/
//FIXED fxmultiply(QUAD *pQuad, LONG x, LONG y, LONG *pOFlag)
//{
//   SHORT  int1,int2,inttot;
//   USHORT frac1,frac2,fractot;
//   LONG   lng1,lng2,lng3,lng4,lngtot;
//   FIXED  fxnum;
//
//   *pOFlag = 0L;
//   frac1 = FIXEDFRAC(x);
//   int1  = FIXEDINT(x);
//   frac2 = FIXEDFRAC(y);
//   int2  = FIXEDINT(y);
//
//   /********************************************************/
//   /* multiply the parts x * y = xfraction * yfraction +   */
//   /*                            xinteger  * yfraction +   */
//   /*                            yinteger  * xfraction +   */
//   /*                            xinteger  * yinteger      */
//   /* Note that longs are used to increase accuracy of the */
//   /* multiplication.                                      */
//   /********************************************************/
//   lng1 = (int1  * int2);
//   lng2 = (frac1 * frac2);
//   lng3 = (int1  * frac2);
//   lng4 = (int2  * frac1);
//   lngtot = ((lng2 >> 16)   & 0x0000FFFFL) +
//            (((USHORT)lng3) & 0x0000FFFFL) +
//            (((USHORT)lng4) & 0x0000FFFFL);
//
//
//   /* add in the high word of lngtot for possible carry. */
//   inttot  = (SHORT)lng1 + (SHORT)(lng3>>16) +
//             (SHORT)(lng4>>16) + (SHORT)(lngtot>>16);
//
//   /* get rid of the carry-over for the fractional part. */
//   fractot = (USHORT)lngtot & 0x0000FFFFL;
//
//
//   if ( inttot > 32767 || inttot < -32768 || fractot > 32767 )
//      *pOFlag = 4L;
//
//   fxnum = MAKEFIXED(inttot,fractot);
//
//   pQuad->lDWlo = fractot;
//   pQuad->lDWhi = inttot;
//
//   return( fxnum );
//}
//#endif

#ifdef POWERPC
/***********************************************************************/
/* fxmultiply                                                          */
/* Multiplies two 32 bit fixed point numbers.  FIXED is a signed 32    */
/* bit number with 16 bits of integer and 16 bits of fraction.         */
/* Returns : fixed format integer or NULL if GRE_OVERFLOW.  The QUAD   */
/* value will be unaffected.                                           */
/***********************************************************************/
FIXED fxmultiply(QUAD *pQuad, LONG x, LONG y, LONG *pOFlag)     // 137791
{                                                               // 137791
   return (FIXED) ( (double)x * (double)y / 6.5536e+04 ) ;      // 137791
}                                                               // 137791
#endif
//
// 137791 - end - changed from Long multiply to double multiply
//-----------------------------------------------------------------------


