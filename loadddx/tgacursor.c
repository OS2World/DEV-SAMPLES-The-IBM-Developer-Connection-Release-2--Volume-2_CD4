/*
 *                    NOTICE TO USERS OF THE SOURCE CODE
 *
 * THE SOURCE CODE EXAMPLES PROVIDED BY IBM ARE ONLY INTENDED TO ASSIST
 * IN THE DEVELOPMENT OF A 24 BIT DDX.
 *
 * INTERNATIONAL BUSINESS MACHINES CORPORATION PROVIDES THE SOURCE CODE
 * EXAMPLES, BOTH INDIVIDUALLY AND AS ONE OR MORE GROUPS, "AS IS" WITHOUT
 * WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT
 * LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE.
 *
 *   FUNCTIONS: tgaLoadCursorBitPlane
 *		MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP
 *		MERGE_BITS_X_CURSOR_PIXMAP
 *		tgaBlink
 *		tgaCreateCrossHairCursor
 *		tgaCreateMultiColorCursor
 *		tgaDisplayCursor
 *		tgaGetCrossHairCursorInfo
 *		tgaGetMultiColorCursorInfo
 *		tgaHideCursor
 *		tgaMakeCursorPixmap
 *		tgaQueryBestSize
 *		tgaQueryCrossHairCursor
 *		tgaRealizeCursor
 *		tgaRecolorCrossHairCursor
 *		tgaRecolorCursor
 *		tgaSetCursorPosition
 *		tgaUnrealizeCursor
 */
/*
 *
 *   Note: Many of the function descriptions have come from book "The X 
 *	   Window System Server", X Version 11, Release 5.  By Elias
 *	   Israel and Erik Fortune.
 */

#include <sys/types.h>
#include <stdio.h>

#include "misc.h"
#include "X.h"
#define NEED_EVENTS
#include "Xproto.h"
#include "scrnintstr.h"
#include "pixmapstr.h"
#include "input.h"
#include "cursorstr.h"
#include "mipointer.h"
#include "misprite.h"
#include "servermd.h"

#include <X11/AIX.h>
#include <gai/gai.h>
#include <gai/misc.h>
#include <gai/cursor.h>

#include "aixPrivates.h"
#include "aixCursor.h"

#include "tgas3regs.h"
#include "tga.h"
#include "tgacursor.h"
#include "tgautils.h"

extern int		AIXCurrentX;
extern int		AIXCurrentY;


extern CursorPtr	pCurrentCursor;
static uchar		hot_x;
static uchar		hot_y;

static int		cursorHidden;


/* 
 *  ---------------------------------------------------------------------------
 *	function:	tgaQueryBestSize
 *	description:
 *		If class is CursorShape, it will return the largest possible
 *		cursor size by setting the width and height values.
 *		If class is TileShape || StippleShape, width and height will
 *		point to the tile's or stipple's width adjusted to the to the
 *		nearest convenient size for tiles and stipples.
 *  ---------------------------------------------------------------------------
 */
void
tgaQueryBestSize (class, pwidth, pheight)
    int   class;
    short *pwidth;
    short *pheight;
{
    if (class == CursorShape)
    {
	*pwidth = *pheight = 64;
    }
    else /* (TileShape || StippleShape). */
    {
/* 
 * Round width to nearest multiple of 8.
 */
	*pwidth = ( *pwidth + 0x7 ) & ~ 0x7;
    }

    return;
}


/* 
 *  ---------------------------------------------------------------------------
 *	function:	tgaSetCursorPosition
 *	description:
 *		The DIX layer calls this routine to change the position of
 *		cursor.  The cursor image on the specified screen must be moved
 *		so that its hot spot is at the location specified by x and y.
 *  ---------------------------------------------------------------------------
 */
void
tgaSetCursorPosition (x, y)
    int x, y;
{
    register int scrnNum = aixCurrentScreen;
    int adj_x, adj_y;

    adj_x = x - hot_x;
    adj_y = y - hot_y;
/* 
 * XXX:SM - The example from the spec stipulates that the settings need to
 *          occur in the following order.
 */
    if ((adj_x < 0) || (adj_y < 0))
    {
	int patternStartX = 0,
	    patternStartY = 0;

	if (adj_x < 0)
	{
	    patternStartX = -(adj_x);
	    adj_x = 0;
	}
	if (adj_y < 0)
	{
	    patternStartY = -(adj_y);
	    adj_y = 0;
	}

	S3WriteDP (S3CursorOriginXHigh, ((adj_x>>8) & 0x07));
	S3WriteDP (S3CursorOriginXLow,  (adj_x & 0xff));
	S3WriteDP (S3CursorOriginYLow,  (adj_y & 0xff));

	S3WriteDP (S3CursorHotSpotX, patternStartX);
	S3WriteDP (S3CursorHotSpotY, patternStartY);

	S3WriteDP (S3CursorOriginYHigh, ((adj_y>>8) & 0x07));
    }
    else
    {
	S3WriteDP (S3CursorOriginXHigh, ((adj_x>>8) & 0x07));
	S3WriteDP (S3CursorOriginXLow,  (adj_x & 0xff));
	S3WriteDP (S3CursorOriginYLow,  (adj_y & 0xff));
	S3WriteDP (S3CursorHotSpotX, 0);
	S3WriteDP (S3CursorHotSpotY, 0);
	S3WriteDP (S3CursorOriginYHigh, ((adj_y>>8) & 0x07));
    }

}


/* 
 *  ---------------------------------------------------------------------------
 *	function:	tgaRealizeCursor
 *	description:
 *		The DIX layer calls this routine before displaying a cursor
 *		on a screen for the first time.  This routine allocates and
 *		initializes device-dependent resources.
 *  ---------------------------------------------------------------------------
 */
Bool
tgaRealizeCursor (pScreen, pCur)
    ScreenPtr pScreen;
    CursorPtr pCur;	/* a SERVER-DEPENDENT cursor */
{
    CursPrivPtr pPrivCur;
    uint       *pGlyph;
    int         width;
    int         height;

    cursorHidden = FALSE;

    width = pCur->bits->width;
    height= pCur->bits->height;

/*
 * Limit the max cursor pixmap size to 64x64 before creating it.
 */
    if (width > 64) width = 64;
    if (height > 64) height = 64;

/*
 *  Malloc space for a 2-bit-per-pixel pixmap that is 64 x 64,
 *  which is 64 scanlines of up to 128 bits (16 bytes) each.
 *  Each scanline will be padded to a word.
 */
    pPrivCur = (CursPrivPtr) xalloc (sizeof(CursPriv) +
				      ((((width + 15) >> 2) & ~0x3) * height));
    pGlyph = (uint *) (pPrivCur + 1);

    pCur->devPriv[pScreen->myNum] = (pointer) pPrivCur;

    pPrivCur->type   = X_CURSOR_TYPE;   /* X Windows cursor (2-color) */
    pPrivCur->pGlyph = pGlyph;

/*
 * If no current cursor, make first realized cursor current.
 */
    if (!aixCurrentCursor (pScreen)) 
    {
	aixCurrentCursor (pScreen) = pCur;
        aixCursorHotX (pScreen)    = pCur->bits->xhot;
        aixCursorHotY (pScreen)    = pCur->bits->yhot;
    }

    return (TRUE);
}


/* 
 *  ---------------------------------------------------------------------------
 *	function:	tgaUnrealizeCursor
 *	description:
 *		The DIX layer calls UnrealizeCursor when a cursor is about to
 *		be destroyed.  This will free any resources allocated by
 *		RealizeCursor.
 *  ---------------------------------------------------------------------------
 */
Bool
tgaUnrealizeCursor (pScreen, pCur)
    ScreenPtr pScreen;
    CursorPtr pCur;
{
    cursorHidden = FALSE;
 
    if (pCurrentCursor == pCur)
	pCurrentCursor = NULL;

    if (pCur->devPriv[pScreen->myNum])
    {
        xfree (pCur->devPriv[pScreen->myNum]);
        pCur->devPriv[pScreen->myNum] = NULL;
    }

    return (TRUE);
}

/*
 *  ---------------------------------------------------------------------------
 *      set up one plane of 64x64 bitmap
 *
 *              source bitmap is padded to 4 byte boundary,
 *              or 32-pixel boundary
 *
 *  ---------------------------------------------------------------------------
 */
static int lastCursorWidth = 64;
static int lastCursorHeight = 64;

tgaLoadCursorBitPlane(srcBits, mskBits, width, height, pScreen, pCur)       
uint		*srcBits;	/* pointer to source bitmap */
uint		*mskBits;	/* pointer to mask bitmap */
int		width;		/* bitmap width */
int		height;		/* bitmap height */
ScreenPtr	pScreen;
CursorPtr	pCur;
{                                                         
  
    register int scrnNum = pScreen->myNum;
    int i, j;                                        
    uint sbits, mbits;
    uint mask;
    uchar tmp;


    u_int dramAddress = (u_int) vramBaseAddress[pScreen->myNum];
    char   *cursorArea;
    u_int  data;

    S3ReadDP  (S3ExtDACControl, tmp);

/*
 * Get the address of the tail end of memory.
 */
    dramAddress += 0x200000 - 0x800;
    cursorArea = (char *) dramAddress;

/*
 * Clear out the last glyph.
 */
    for (i=0; i<64; i++)
    {
	for (j=0; j<8; j++)		/* Fills one scan line */
	{
	    *cursorArea++ = 0x00; /* each pass takes care of 8 cursor pixels */
	    *cursorArea++ = 0x00;
	    *cursorArea++ = 0x00;
	    *cursorArea++ = 0x00;
	}
    }

    cursorArea = (char *) dramAddress;
    if (width <= 32)
    {
	mask = 0xffffffff << (32 - width);
	for (j=0; j<height ; j++)                        
	{                                                
	    mbits = (*mskBits++ & mask);
	    sbits = (*srcBits++ & mask);

	    *cursorArea++ = (mbits >> 24);
	    *cursorArea++ = (mbits >> 16);
	    *cursorArea++ = (sbits >> 24);
	    *cursorArea++ = (sbits >> 16);

	    *cursorArea++ = (mbits >> 8);
	    *cursorArea++ = mbits;
	    *cursorArea++ = (sbits >> 8);
	    *cursorArea++ = sbits;

	    for (i=0; i<8; i++)
		cursorArea++;  /* each increment moves over 4 cursor pixels. */
	}
    }

    else if (width <= 64)
    {
	mask = 0xffffffff << (64 - width);

	for (j=0; j<height ; j++)                        
	{                                                
	    mbits = *mskBits++;
	    sbits = *srcBits++;

	    *cursorArea++ = (mbits >> 24);
	    *cursorArea++ = (mbits >> 16);
	    *cursorArea++ = (sbits >> 24);
	    *cursorArea++ = (sbits >> 16);

	    *cursorArea++ = (mbits >> 8);
	    *cursorArea++ = mbits;
	    *cursorArea++ = (sbits >> 8);
	    *cursorArea++ = sbits;

	    mbits = (*mskBits++ & mask);
	    sbits = (*srcBits++ & mask);

	    *cursorArea++ = (mbits >> 24);
	    *cursorArea++ = (mbits >> 16);
	    *cursorArea++ = (sbits >> 24);
	    *cursorArea++ = (sbits >> 16);

	    *cursorArea++ = (mbits >> 8);
	    *cursorArea++ = mbits;
	    *cursorArea++ = (sbits >> 8);
	    *cursorArea++ = sbits;
	}                                                
    }
    else /* width > 64 - defect 148273 */
    {
	for (j=0; j<64; j++)
	{
	    mbits = *mskBits++;
	    sbits = *srcBits++;

	    *cursorArea++ = (mbits >> 24);
	    *cursorArea++ = (mbits >> 16);
	    *cursorArea++ = (sbits >> 24);
	    *cursorArea++ = (sbits >> 16);

	    *cursorArea++ = (mbits >> 8);
	    *cursorArea++ = mbits;
	    *cursorArea++ = (sbits >> 8);
	    *cursorArea++ = sbits;

	    mbits = *mskBits++;
	    sbits = *srcBits++;

	    *cursorArea++ = (mbits >> 24);
	    *cursorArea++ = (mbits >> 16);
	    *cursorArea++ = (sbits >> 24);
	    *cursorArea++ = (sbits >> 16);

	    *cursorArea++ = (mbits >> 8);
	    *cursorArea++ = mbits;
	    *cursorArea++ = (sbits >> 8);
	    *cursorArea++ = sbits;

/*
 * Point to the next scanline, skipping the rest of the cursor bits.
 */
	    mskBits += (((width + 31) >> 5) -2);
	    srcBits += (((width + 31) >> 5) -2);
	}
    }

    for (j = height; j < 64; j++)
    {
	*cursorArea++ = 0;
	*cursorArea++ = 0;
	*cursorArea++ = 0;
	*cursorArea++ = 0;

	*cursorArea++ = 0;
	*cursorArea++ = 0;
	*cursorArea++ = 0;
	*cursorArea++ = 0;
    }
}


/*
 *  ---------------------------------------------------------------------------
 *	function:	tgaDisplayCursor
 *	description:
 *		The DIX layer calls this routine to display a particular
 *		cursor image on a given screen.  The cusor specified by pCur
 *		must be displayed on the Screen such that its hot spot is at
 *		the same point as the hot spot of the previous cursor image.
 *  ---------------------------------------------------------------------------
 */
Bool
tgaDisplayCursor (pScreen, pCur)
    ScreenPtr pScreen;
    CursorPtr pCur;
{
register int scrnNum = pScreen->myNum;
/*
 * If the screen isn't active (e.g. screen is hotkeyed away, or
 * the cursor is on another display in a multi-head configuration),
 * don't touch the adapter.
 */
    if (aixScreenState(screenInfo.screens[aixCurrentScreen]) != SCREEN_ACTIVE)
    {
         return (TRUE);
    }

 /*
  * If this is a not the current cursor, we do
  * 1. set cursor_state to reflect what type of cursor conf.
  * 2. if xcursor then
  *       load the cursor glyph down to cursor ram on the Bt485.
  */

    if ( (pCurrentCursor != pCur) || cursorHidden )
    {
	CursPrivPtr pPrivCur	= (CursPrivPtr) pCur->devPriv[pScreen->myNum];
	uint *pGlyph		= pPrivCur->pGlyph;
	int    glyph_width	= pCur->bits->width;
	int    glyph_height	= pCur->bits->height;

	if (glyph_height > 64)
	{
	    glyph_height = 64;
	}

	if (pPrivCur->type == X_CURSOR_TYPE)
	{
            /* Load cursor glyph down to cursor ram */
            uint *source_bits, *mask_bits;

            source_bits = (uint *) (pCur->bits->source);
            mask_bits   = (uint *) (pCur->bits->mask);

            /****************************************************
            *    Now copy the cursor pixmap to the cursor ram
            *    on the S3.
            ******************************************************/

            EIEIO;
            S3WaitVerticalRefresh();	/* can loose data if too many changes */
            tgaLoadCursorBitPlane ((uchar *)source_bits, (uchar *) mask_bits,
				    glyph_width, glyph_height, pScreen, pCur);


        }   /* End if (pPrivCur->type == X_CURSOR_TYPE) */

        /*****************************************************
        *     Make this cursor the current cursor for screen
        ******************************************************/
        pCurrentCursor  = pCur;
        hot_x		= pCur->bits->xhot;
        hot_y		= pCur->bits->yhot;


        /****************************************************
        *    Set up the cursor colors
        *****************************************************/
        pScreen->RecolorCursor (pScreen, pCur, TRUE);
        EIEIO;

	cursorHidden = FALSE;

    } /* endif pCurrentCursor != pCur) */

    /**************************
    *    Set Cursor position
    ***************************/
    tgaSetCursorPosition (AIXCurrentX, AIXCurrentY);

    TGATurnCursor (CURSOR_ON);	/* This may need to change */
    EIEIO;
    S3WaitVerticalRefresh();	/* can loose data if too many changes */
				/* too fast */

    return (TRUE);
}



/*
 *  ---------------------------------------------------------------------------
 * 	function:	tgaRecolorCursor
 * 	description:
 *		Changes the cursor's colormap by:
 *
 *    	pixel value     x-cursor   crosshaircursor      multicolorcursor
 *    	(mask bit, source bit)
 *       0 = 00       transparent
 *       1 = 01       transparent 
 *       2 = 10       background   n/a                   n/a
 *       3 = 11       foreground   n/a             	 n/a
 *
 *   Note: the pixel value definition is reversed of what X server
 *         defined it to be, because we loaded the cursor ram in
 *         reverse order:
 *         plane 0 == source bitmap
 *         plane 1 == mask   bitmap
 *         where as X defines as ( souce bit, mask bit ).
 *  ---------------------------------------------------------------------------
 */
void
tgaRecolorCursor (pScreen, pCur, visible) 
    register ScreenPtr pScreen; 
    register CursorPtr pCur;
    register int       visible;
{
    register int scrnNum = pScreen->myNum;
    CursPrivPtr pPrivCur	= (CursPrivPtr) pCur->devPriv[pScreen->myNum];
    char	color;
    char	reset;
    uchar 	tmp;

    if (visible && (pCurrentCursor == pCur))
    {
	switch (pPrivCur->type)
	{
	    case X_CURSOR_TYPE:
		   S3ReadDP (S3CursorMode, tmp); /* reset the ptr */
		   S3WriteDP (S3CursorFGColorStack, (u_char)pCur->foreBlue);
		   S3WriteDP (S3CursorFGColorStack, (u_char)pCur->foreGreen);
		   S3WriteDP (S3CursorFGColorStack, (u_char)pCur->foreRed);

		   S3ReadDP (S3CursorMode, tmp); /* reset the ptr */
		   S3WriteDP (S3CursorBGColorStack, (u_char)pCur->backBlue);
		   S3WriteDP (S3CursorBGColorStack, (u_char)pCur->backGreen);
		   S3WriteDP (S3CursorBGColorStack, (u_char)pCur->backRed);
		   break;

	    case CROSSHAIR_TYPE:
                   break;

	    case THREECOLOR_TYPE:
                   break;
	}   /* end switch pPrivCur->type  */
     }  /* end if visible current cursor */
}


/*
 *  ---------------------------------------------------------------------------
 *	function:	tgaHideCursor
 *	description:
 *		Make the cursor invisible.  This is used when you move the
 *		pointer to another screen on multihead displays and want to 
 *		make the cursor on the old screen invisible.  It is also used 
 *		when hotkeying away.
 *  ---------------------------------------------------------------------------
 */
void
tgaHideCursor (pScreen, pCur)
    ScreenPtr pScreen;
    CursorPtr pCur;
{
    register int scrnNum = pScreen->myNum;
    uint dramAddress = (uint) vramBaseAddress[scrnNum];
    char   *cursorArea;
    uchar  tmp;
    u_int  data;
    int    i, j;
    
    cursorHidden = TRUE;

    S3ReadDP  (S3ExtDACControl, tmp);
/*
 * Get the address of the tail end of memory.
 */
    dramAddress += 0x200000 - 0x800;
    cursorArea = (char *) dramAddress;

/*
 * Clear out the last glyph.
 */
    for (i=0; i<64; i++)
    {
	for (j=0; j<8; j++)		/* Fills one scan line */
	{
	    *cursorArea++ = 0x00; /* each pass takes care of 8 cursor pixels */
	    *cursorArea++ = 0x00;
	    *cursorArea++ = 0x00;
	    *cursorArea++ = 0x00;
	}
    }

}



#define MERGE_BITS_X_CURSOR_PIXMAP(count)                 \
{                                                         \
    int k;                                                \
    for (k = count; k > 0; k--)                           \
    {                                                     \
        combined <<= 2;                                   \
        combined |= ((msk >> 30) & 0x2) | (((src & msk) >> 31) & 0x1);    \
        msk <<= 1;                                        \
        src <<= 1;                                        \
    }                                                     \
}

#define MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP(count)        \
{                                                         \
    int k;                                                \
    for (k = count; k > 0; k--)                           \
    {                                                     \
        combined <<= 2;                                   \
        combined |= ((msk >> 30) & 0x2) | ((src >> 31) & 0x1);    \
        msk <<= 1;                                        \
        src <<= 1;                                        \
    }                                                     \
}

/*
 *  ---------------------------------------------------------------------------
 * 	function:     tgaMakeCursorPixmap
 * 	description:
 *		Creates the cursor glyph from the source and mask
 *			bitmaps.
 *			plane 0 == source bitmap
 *			plane 1 == mask   bitmap
 *  ---------------------------------------------------------------------------
 */

void
tgaMakeCursorPixmap(pCur, pCurPriv)
    CursorPtr   pCur;
    CursPrivPtr pCurPriv;
{
    u_int      glyph_width, glyph_height;
    uint       *source_bits, *mask_bits;
    uint        src, msk;
    uint       combined;
    int         i, j;
    uint       *pGlyph;
    
    pGlyph = (uint *) pCurPriv->pGlyph;

    glyph_width  = pCur->bits->width;      /* in bytes */
    glyph_height = pCur->bits->height;

/*
 *  Each scanline of mask_bits and source_bits are padded to the word.
 *  The pixmap destination scanline is also padded to the word.
 */
    source_bits = (uint *) (pCur->bits->source);
    mask_bits   = (uint *) (pCur->bits->mask);

    if (glyph_width <= 16)
    {
        for (i = glyph_height; i > 0; i--)
        {
           src = *source_bits++;
           msk = *mask_bits++;
           
           if (pCurPriv->type == X_CURSOR_TYPE)
           {
              MERGE_BITS_X_CURSOR_PIXMAP(glyph_width);
           }
           else
           {
              MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP(glyph_width);
           }

           *pGlyph++ = combined << (2 * (16 - glyph_width));
        }
    }
    else if (glyph_width <= 32)
    {
        for (i = glyph_height; i > 0; i--)
        {
           src = *source_bits++;
           msk = *mask_bits++;
           if (pCurPriv->type == X_CURSOR_TYPE)
           {
              MERGE_BITS_X_CURSOR_PIXMAP(16);             
           }
           else
           {
              MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP(16);               
           }


           *pGlyph++ = combined;

           if (pCurPriv->type == X_CURSOR_TYPE)
           {
              MERGE_BITS_X_CURSOR_PIXMAP(glyph_width-16);
           }
           else
           {
              MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP(glyph_width-16);
           }

           *pGlyph++ = combined << (2 * (32 - glyph_width));
        }
    }
    else if (glyph_width <= 48)
    {
        for (i = glyph_height; i > 0; i--)
        {
           src = *source_bits++;
           msk = *mask_bits++;

           if (pCurPriv->type == X_CURSOR_TYPE)
           {
              MERGE_BITS_X_CURSOR_PIXMAP(16);
           }
           else
           {
              MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP(16);
           }

           *pGlyph++ = combined;

           if (pCurPriv->type == X_CURSOR_TYPE)
           {
              MERGE_BITS_X_CURSOR_PIXMAP(16);
           }
           else
           {
              MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP(16);
           }

           *pGlyph++ = combined;

           src = *source_bits++;
           msk = *mask_bits++;

           if (pCurPriv->type == X_CURSOR_TYPE)
           {
              MERGE_BITS_X_CURSOR_PIXMAP(glyph_width-32);
           }
           else
           {
              MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP(glyph_width-32);
           }

           *pGlyph++ = combined << (2 * (48 - glyph_width));
        }
    }
    else /* glyph_width <= 64 */
    {
        for (i = glyph_height; i > 0; i--)
        {
           src = *source_bits++;
           msk = *mask_bits++;

           if (pCurPriv->type == X_CURSOR_TYPE)
           {
              MERGE_BITS_X_CURSOR_PIXMAP(16);
           }
           else
           {
              MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP(16);
           }

           *pGlyph++ = combined;

           if (pCurPriv->type == X_CURSOR_TYPE)
           {
              MERGE_BITS_X_CURSOR_PIXMAP(16);
           }
           else
           {
              MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP(16);
           }

           *pGlyph++ = combined;

           src = *source_bits++;
           msk = *mask_bits++;

           if (pCurPriv->type == X_CURSOR_TYPE)
           {
              MERGE_BITS_X_CURSOR_PIXMAP(16);
           }
           else
           {
              MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP(16);
           }

           *pGlyph++ = combined;

           if (pCurPriv->type == X_CURSOR_TYPE)
           {
              MERGE_BITS_X_CURSOR_PIXMAP(glyph_width - 48);
           }
           else
           {
              MERGE_BITS_MULTICOLOR_CURSOR_PIXMAP(glyph_width - 48);
           }

           *pGlyph++ = combined << (2 * (64 - glyph_width));
        }
    }
}

/*
 *  ---------------------------------------------------------------------------
 * 	function:	tgaGetCrossHairCursorInfo
 *	description:
 *		Returns the minimum width, maximum width, best width, number 
 *		of colors, and type of based support for the crosshair cursor 
 *		for the specified screen.
 *  ---------------------------------------------------------------------------
 */
int  tgaGetCrossHairCursorInfo(pScreen, min_width, max_width,
                               best_width, base)
ScreenPtr       pScreen;
ushort         *min_width;
ushort         *max_width;
ushort         *best_width;
ushort         *base;
{
    *min_width = 0;
    *max_width = 0;
    *best_width = 0;
    *base = NO_CROSSHAIR;

    return(Success);
}

/*
 *  ---------------------------------------------------------------------------
 *	function: 	tgaRecolorCrossHairCursor
 *	description:
 *		Recolors the crosshair cursor with the given color
 *		ERROR CONDITION: BadCursor
 *  ---------------------------------------------------------------------------
 */
int tgaRecolorCrossHairCursor(pScreen, pCursor, xcolor)
ScreenPtr       pScreen;
CursorPtr       pCursor;
xColorItem      xcolor;
{
    return (BadRequest);
}




/*
 *  ---------------------------------------------------------------------------
 *	function:	tgaGetMultiColorCursorInfo
 *	description:
 *		Returns the number of hardware cursor_planes, the 
 *		transparent_pixel value, the maximum width and height for the 
 *		screen specified by the drawable.  If transparent_pixel 
 *		value = -1, then transparent_pixel is *	not supported.
 *  ---------------------------------------------------------------------------
 */
int
tgaGetMultiColorCursorInfo(pScreen, cursor_planes, transparent_pixel,
			   width, height)
ScreenPtr      pScreen;
ushort         *cursor_planes;
int            *transparent_pixel;
ushort         *width;
ushort         *height;
{

    *cursor_planes     = 0;
    *transparent_pixel = -1;
    *width             = 0;
    *height            = 0;

    return(Success);
}


/*
 *  ---------------------------------------------------------------------------
 * 	function:	tgaQueryCrossHairCursor
 *	description:
 *		Returns the type of cross hair cursor that is supported.
 *  ---------------------------------------------------------------------------
 */
void 
tgaQueryCrossHairCursor(pScreen, min_width, max_width, best_width, colors, base)
ScreenPtr      pScreen;
int            *min_width;
int            *max_width;
int            *best_width;
int            *colors;
int            *base;
{   
    *min_width   = 0;                                     
    *max_width   = 0;                                       
    *best_width  = 0;                                           
    *colors      = 0;          
    *base        = NO_CROSSHAIR;
    return;
}


/*
 *  ---------------------------------------------------------------------------
 * 	function:	tgaCreateCrossHairCursor
 *	description:
 *		Pseudo Based cross hair support, 64x64 bit image.
 *  ---------------------------------------------------------------------------
 */
int 
tgaCreateCrossHairCursor(pScreen, pCur, hair_width, line_color, base)
ScreenPtr       pScreen;
CursorPtr       pCur;
int             hair_width;
xColorItem      line_color;
int             base;
{
    return (BadRequest);
}


/*
 *  ---------------------------------------------------------------------------
 * 	function: tgaCreateMultiColorCursor
 *	description:
 *		No three color support, is returning BadValue okay?
 *  ---------------------------------------------------------------------------
 */
int 
tgaCreateMultiColorCursor(pScreen, pCur, pPixmap, ncolor, xcolors)
ScreenPtr       pScreen;
CursorPtr       pCur;
PixmapPtr       pPixmap;
int             ncolor;
xColorItem     *xcolors;
{
    return (BadRequest);
}


/*
 *  ---------------------------------------------------------------------------
 * 	function:	tgaBlink
 *	description:
 *           tga doesn't support blink
 *  ---------------------------------------------------------------------------
 */
int 
tgaBlink(pScreen, pCmap, duration, xcolor)
ScreenPtr       pScreen;
ColormapPtr     pCmap;
int             duration;
xColorItem      xcolor;
{
   /* do nothing */
}
