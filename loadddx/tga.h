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
 *   FUNCTIONS: none
 *
 */
#include "tgasdac.h"
/*
 * File: tga.h
 * Contents:
 *	- The most generic homestake routines.
 */
extern volatile uint	*vramBaseAddress[];
extern uint		ioBaseAddress[];
extern SDAC_regs	SDAC[];
extern tgaScreenPrivateIndex;

typedef struct _tga_screen_priv {
	int is_trio;		
	int chip_id;	  
	int chip_rev;			
        uint gschandle;
} tgaScreenPrivRec, *tgaScreenPrivPtr;
 
struct tga_map     /* structure for returning mapping info to apps */
{
     uint screen_height_mm;     /* Height of screen in mm                  */
     uint screen_width_mm;      /* Height of screen in mm                  */
     uint screen_height_pix;    /* Height of screen in pixels              */
     uint screen_width_pix;     /* Height of screen in pixels              */
     uint color_mono_flag;      /* Flag for color = 1 or mono = 0          */
     uint pixel_depth;          /* Number of bits per pixel                */
     uint  monitor_type;        /* monitor type connected to TGA           */
};
#define DEBUG
#define TVGA_PCI_ID      0x33531188       /* S3    TRIO-64 Vend/Dev ID    */
#define IO_MEM_OFFSET    0x01008000
#define GSC_UPDATE_PALETTE                 1
#define TGAGSCHANDLE(pScreen) (((tgaScreenPrivPtr)\
		(pScreen->devPrivates[tgaScreenPrivateIndex].ptr))->gschandle)
/*
 * Mostly machine dependent defines go here.
 */

#define EIEIO	__iospace_eieio(); /* Enforced in-order execution of I/O. */

/* This chip_id is used for both TRIO-64 and TRIO-64V+, any software that 
 * need to determine the specific chip set, need to check chip_rev which is
 * 0x8? for 64V+ 
 */
#define TRIO_64_ID  		0x8811



/* some crtc struct constants */
#define MODE_ID_LEN     	15
#define DISP_TYPE_LEN   	15
