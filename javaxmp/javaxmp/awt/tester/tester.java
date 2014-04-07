// package mypackage ;

// This is work in progress. Do not copy.

class bitmap_data_2
{
  int   type ;
  int   cbFix;            /* Length of structure                    */
  int   cx;               /* Bit-map width in pels                  */
  int   cy;               /* Bit-map height in pels                 */
  short cPlanes;          /* Number of bit planes                   */
  short cBitCount;        /* Number of bits per pel within a plane  */
  int   ulCompression;    /* Compression scheme used to store the bitmap */
  int   cbImage;          /* Length of bit-map storage data in bytes*/
  int   cxResolution;     /* x resolution of target device          */
  int   cyResolution;     /* y resolution of target device          */
  int   cclrUsed;         /* Number of color indices used           */
  int   cclrImportant;    /* Number of important color indices      */
  short usUnits;          /* Units of measure                       */
  short usReserved;       /* Reserved                               */
  short usRecording;      /* Recording algorithm                    */
  short usRendering;      /* Halftoning algorithm                   */
  int   cSize1;           /* Size value 1                           */
  int   cSize2;           /* Size value 2                           */
  int   ulColorEncoding;  /* Color encoding                         */
  int   ulIdentifier;     /* Reserved for application use           */

  bitmap_data_2()
  {
    System.out.println( "Constructor called for bitmap_data_2" );

    type = 2;
  }
}

class tester
{
  short a ;
  short b ;

  public static void main ( String[] args )
  {

    System.out.println( "Main called" );

    bitmap_data_2 bd2 = new bitmap_data_2 () ;

    System.out.println( bd2.type ) ;
    //tester s = new tester ();
    //s.b = 4 ;
    //byte[] b = new byte[s.b];
    //s.a = (short) b[0] ;
  }
}

class bitmap_data_1
{
  int type ;
}


