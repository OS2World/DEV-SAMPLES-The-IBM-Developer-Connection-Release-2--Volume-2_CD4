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
 *   FUNCTIONS: tgaCloseScreen
 *		tgaScreenInit
 *		tgaSetScreenVectors
 *		tgaSetupScreen
 *		tga_ddxInit
 *		tgaSaveState
 *		tgaRestoreState
 *
 */
#include <stdio.h>
#include <sys/types.h>

/***************************************************************
*       ODM includes
***************************************************************/
#include <odmi.h>
#include <cf.h>
#include <sys/cfgodm.h>
#include <sys/cfgdb.h>

#include "X.h"
#include "servermd.h"
#include "scrnintstr.h"
#include "cursorstr.h"
#include "colormapst.h"
#include "shrmem.h"
#include "cfb24.h"

#include "dix.h"
#include "mi.h"
#include <sys/rcm_win.h>
#include <sys/aixgsc.h>
#include <sys/rcmioctl.h>
#include <gai/gai.h>
#include <gai/misc.h>
#include "aixPrivates.h"
#include "aixIO.h"
#include <sys/errno.h>
#include "tgas3regs.h"
#include "tga.h"
#include "tgacursor.h"
#include "tgautils.h"

#include "pixmapstr.h"
#include "mibstore.h"
/*
 *  ---------------------------------------------------------------------------
 *	structure:	tgaBSFuncRec
 *	description:
 *		Stores the list of function pointers that are used by mi's
 *		backing store routines.
 *  ---------------------------------------------------------------------------
 */
static miBSFuncRec	tgaBSFuncRec =
{
    cfb24SaveAreas,
    cfb24RestoreAreas,
    (void (*)()) 0,
    (PixmapPtr (*)()) 0,
    (PixmapPtr (*)()) 0,
};

/*
 *  ---------------------------------------------------------------------------
 *	structure:	tgaPixmapFormats
 *	description:
 *		Stores the list of supported pixmap formats.
 *  ---------------------------------------------------------------------------
 */
static PixmapFormatRec	tgaPixmapFormats[] =
{
    { 24, 32, 32 }
};

static gsc_handle gscHandle[MAXSCREENS];

static unsigned long	ddxGeneration=0;
unsigned long		ddxGCPrivateIndex;

extern Bool		aixAllocatePrivates();
extern SDAC_regs	SDAC[];
extern DisplayRecPtr	SavedDisplayPtr;


extern void		tgaHideCursor();


/*
 * ---------------------------------------------------------------------------
 *	Global declarations.
 * ---------------------------------------------------------------------------
 */
static pointer 	p_crt_control[MAXSCREENS];
int tgaScreenPrivateIndex;

/*---------------------------------------------------------------------------*/

/*
 * ---------------------------------------------------------------------------
 * function:		GetCurDisplayMode
 * description:
 *	Draws information from the odm database and then calls 
 *	tgaGetMatchedModeID to find the matching set of attributes.
 * ---------------------------------------------------------------------------
 */
#define TFT_DISPLAY 0x06

void
GetCurDisplayMode (pScreen, disp_name, p_crt_control, monitorID)
ScreenPtr pScreen;
char      disp_name[];
pointer	  *p_crt_control;
int	  monitorID;
{
    /* Initialize the ODM database for access */
    if ( (odm_initialize() != -1) && (monitorID != TFT_DISPLAY) )
    {
        struct CuAt *disp_type,
                    *disp_mode;
        char cur_disp_type [DISP_TYPE_LEN+1];
        char cur_mode_id [MODE_ID_LEN+1];
	int rc;
	int str_len;

	/*
	 * get the current display type - either default value or
	 * selected by a user.
	 */ 
        if ( (disp_type = (struct CuAt *) getattr(disp_name, "monitor_type",
              FALSE, &rc)) != NULL )
	{
	    /*
	     * get the display type with length of DISP_TYPE_LEN from either
	     * CuAt or PdAt and use it as a key to do the following get
	     */
            strncpy (cur_disp_type, disp_type->value, DISP_TYPE_LEN);

	    /* 
	     * get the display mode on a given display from CuAt or PdAt
	     */
            if ( (disp_mode = (struct CuAt *) getattr(disp_name, cur_disp_type,
                  FALSE, &rc)) != NULL )
	    {
	 	/* 
	 	 * get the length for the mode id which is the sub-string of 
	 	 * disp_mode->value.
         	 */
		str_len = strcspn (disp_mode->value, "Hz") + 2;

		/* 
	 	 * get the mode id which is the key of the structure 
		 * s_crt_control (exp. 1024x768@76Hz)
	 	 */
        	strncpy (cur_mode_id, disp_mode->value, str_len);

        	/* 
	  	 * find the matched mode id
  	 	 */
		tgaGetMatchedModeID (cur_mode_id, str_len, p_crt_control);
	    }
	    else 
	    {
		/* getattr for the current mode fail, use the default mode */
		*p_crt_control = (pointer)tgaGetDefaultMode();
	    }
	}
	else
	{    /* getattr for the current disp type fail, use the default mode */
	     *p_crt_control = (pointer)tgaGetDefaultMode();
        }
        odm_terminate();
    }
    else
    {
/* 
 * Initialize odm fail, and a tft is not connected then use the default mode.
 */
	if (monitorID != TFT_DISPLAY)
	{
		*p_crt_control = (pointer)tgaGetDefaultMode();
	}
	else
	{
		tgaGetMatchedModeID ("640x480@60Hz", 12, p_crt_control);
	}
    }
}



/*
 * ---------------------------------------------------------------------------
 * 	function:		tga_SaveScreen
 * 	description:
 *              - call display power management if told to do so
 *              - otherwise, do the normal aixSaveScreen thing
 * ---------------------------------------------------------------------------
 */
Bool
tga_SaveScreen(pScreen, on, phase)
    ScreenPtr pScreen;
    int on;
    int phase;
{
   aixScreenPrivatePtr  pScrDevPriv;
   int	error = 0;
   register int	scrnNum = pScreen->myNum;

    if (on == SCREEN_SAVER_POWER_MANAGER)
    {
        /********************************************************************
        *   Make the gsc call to change the power manager phase             *
        *********************************************************************/
        if ((error=aixgsc(gscHandle[scrnNum].handle,
		DISP_PM, CHANGE_POWER_STATE,phase)) < 0)
	{
	    /* not supported return true anyway */
	}
    }
    /* else just do the normal aixSaveScreen thing */
    else
        return (aixSaveScreen (pScreen, on));

    if (phase == 1)
    {
	tgaResetMonitor (pScreen, p_crt_control[pScreen->myNum]);
    }
    return (TRUE);
}


/*
 * ---------------------------------------------------------------------------
 * 	function:		tgaSaveState
 * 	description:
 *              - Called by Power Mgmt processing to save the state on the
 *                adapter and prepare for power-down.
 * ---------------------------------------------------------------------------
 */
Bool tgaSaveState(pScreen)
    ScreenPtr   pScreen;
{

    tgaHideCursor(pScreen, aixCurrentCursor(pScreen));

    return (TRUE);
}


/*
 * ---------------------------------------------------------------------------
 * 	function:		tgaRestoreState
 * 	description:
 *              - Called by Power Mgmt processing to restore the state on the
 *                adapter at power-up time.
 * ---------------------------------------------------------------------------
 */
Bool
tgaRestoreState(pScreen)
    ScreenPtr   pScreen;
{
   register int scrnNum = pScreen->myNum;
/*   extern void   tgaRefreshColormaps();
*/
   tgaResetMonitor(pScreen, p_crt_control[scrnNum]);

/*
 * XXX:SM The book says low might be better if it is set 0xff.
 */
    S3WriteDP (S3CursorAddrStartHigh, 0x07);
    S3WriteDP (S3CursorAddrStartLow, 0xfe);
    S3WriteDP (S3ExtDACControl, 0x10); /* set bit 4. */
    TGATurnCursor(CURSOR_OFF);

    TGAResetScissors();
/* 
    tgaRefreshColormaps(scrnNum);
*/
    return (TRUE);
}

/*
 * ---------------------------------------------------------------------------
 * 	function:		tgaScreenInit
 * 	description:
 *		Allocates and initializes screen and device structures.
 * ---------------------------------------------------------------------------
 */
Bool
tgaScreenInit(scrnNum, pScreen, argc, argv)
    int		scrnNum;
    ScreenPtr	pScreen;
    int		argc;
    char	**argv;
{
/*
 * ---------------------------------------------------------------------------
 *	X Server level decarations.
 * ---------------------------------------------------------------------------
 */
    uint tvar = 0x40000000;
    PixmapPtr			pPixmap;
    ColormapPtr			pColormap;
    VisualRec 			*pVisual;
    DepthRec 			*pDepth;
    aixScreenPrivatePtr		pScrDevPriv;
    aixExtScreenPrivatePtr	pExtScrDevPriv;

/*
 * ---------------------------------------------------------------------------
 * 	Device/AIX level decarations.
 * ---------------------------------------------------------------------------
 */
    make_gp			makegp_info;	/* MAKE_GP calling params */
    struct tga_map     		tga_makegp_info;
    int				count;
    char 			*cmdlist[1] = { (char *) NULL };
    gAdapterPtr			pAdapter = NULL;
    extern gAdapterPtr		gaiDevAdpIndex[];

/*
 * ---------------------------------------------------------------------------
 * 	Local level decarations.
 * ---------------------------------------------------------------------------
 */
    int				visualIndex;
    short			swap_tmp;
    char			registerContents;
    Bool			visual_requested = FALSE;
    int				requestedDepth, requestedVisual;
    int				index;
    int				rootDepth;
    short			mmWidth, mmHeight;

/*
 * ---------------------------------------------------------------------------
 * 	Function decarations.
 * ---------------------------------------------------------------------------
 */
    extern int			tgaBlink();
    extern void			tgaSetCursorPosition();
    extern Bool			tgaInitVisuals();
#ifndef XXX
    extern void                 tgaSetUpFromDataFile();
    int 			width, height;
#endif
    extern Bool			tga_SaveScreen();
    tgaScreenPrivPtr		tgaScreenPriv;
    volatile unsigned char 	read_val;

/*
 * ---------------------------------------------------------------------------
 *      Initialize Private Indices.
 * ---------------------------------------------------------------------------
 */
    if (ddxGeneration != serverGeneration)
    {
	ddxGeneration		= serverGeneration;
	tgaScreenPrivateIndex   = AllocateScreenPrivateIndex();
	if(tgaScreenPrivateIndex < 0)
		return (FALSE);
    }

    if (aixAllocatePrivates (pScreen))
    {
	pScrDevPriv = (aixScreenPrivatePtr)
			pScreen->devPrivates[aixScreenPrivateIndex].ptr;

	pScrDevPriv->aix_DisplayRecPtr   = SavedDisplayPtr;
    }
    else
	return (FALSE);

/*
 *  ---------------------------------------------------------------------------
 *      Go into Monitor mode to gain access to the adater.
 *  ---------------------------------------------------------------------------
 */
    strcpy (gscHandle[scrnNum].devname, aixScreenName(pScreen));
    if (ioctl (screenInfo.devPrivate->rcm_fd, GSC_HANDLE, &gscHandle[scrnNum]) < 0)
    {
	perror ("ioctl");
	return (FALSE);
    }

/*
 *  ---------------------------------------------------------------------------
 *	Create Adapter Resource
 *		See IBM's RMS (Resource Management System)
 *		documentation for an explanation of CreateAdapter.
 *        
 *	Homestake ddx does not use loadrms only libgair4.a for the basic
 *	CreateAdapter call to get pAdapter structure.
 *	Note that InitScreen is NOT allowed to modify argc, argv, or
 *	any of the strings pointed to by argv, since they may be passed to
 *	multiple screens.
 *  ---------------------------------------------------------------------------
 */
    pAdapter = CreateAdapter (aixDeviceID (pScreen), 
			      gscHandle[scrnNum].handle, count, cmdlist);	


    gaiDevAdpIndex[pScreen->myNum] = pAdapter;

/*
 *  ---------------------------------------------------------------------------
 *  	Mark the calling process as a graphic process. This RCM call
 *  	will return all the adapter information needed to initialize the
 *  	adapter and monitor attributes.
 *  ---------------------------------------------------------------------------
 */
    makegp_info.error   = 0;
    makegp_info.pData   = (char *) &tga_makegp_info;
    makegp_info.length  = sizeof (tga_makegp_info);
    makegp_info.access  = EXCLUSIVE_ACCESS | ADDR_ARRAY_VERSION;
    makegp_info.NumAddrRanges = 2;
    makegp_info.AddrRanges = (gAddrRanges*)xalloc(2*sizeof(gAddrRanges));
    makegp_info.access |= ALL_ADDR_RANGES;

    if (aixgsc (gscHandle[scrnNum].handle, MAKE_GP, &makegp_info))
    {
        printf ("makegp_info.error = %d\n", makegp_info.error);
        return (FALSE);
    }

    /*
     * 864:      First address is the frame buffer, and second is the IO.
     * Trio64V+: First address covers both Frame Buffer and IO (all 64meg).
     */
    vramBaseAddress[scrnNum] =(uint *)(makegp_info.AddrRanges[0].BaseAddr);
    vramBaseAddress[scrnNum]+=(uint)(0x02000000>>2);/* Set address to upper 32M */
 
    if (makegp_info.AddrRanges[0].AddrRangeID == TVGA_PCI_ID)
    {
        ioBaseAddress[scrnNum] =
          (uint)(makegp_info.AddrRanges[0].BaseAddr) + IO_MEM_OFFSET;
    }
    else
    {
        ioBaseAddress[scrnNum] =  (uint)(makegp_info.AddrRanges[1].BaseAddr);
    }
    ioBaseAddress[scrnNum] +=(uint)(0x02000000>>2);/* Set address to upper 32M */
    rootDepth = 24;
/*
 * ----------------------------------------------------------------------------
 * Check for IceCube (S3 864 + SDAC) or Trio-64 chipsets here
 * ----------------------------------------------------------------------------
 */
    tgaScreenPriv = (tgaScreenPrivPtr)Xalloc(sizeof(tgaScreenPrivRec));
    if(!tgaScreenPriv)
	return FALSE;
    pScreen->devPrivates[tgaScreenPrivateIndex].ptr = (pointer)tgaScreenPriv;

    /* Save gscHandle[].handle for use in set color routines */
    tgaScreenPriv->gschandle = gscHandle[scrnNum].handle;

    S3ReadDP(S3ChipIdRev, read_val);
    /* If the higher nibble of this register is 0xe 
     * then we need to read 0x2f, 0x2d and 0x2e regs 
     * to get the chip_id and revision
     * else the higher nibble is the chip id and lower nibble
     * is the revision
     */
    if((read_val & 0xf0) == 0xe0) {
	S3ReadDP(S3Revision, read_val);
	tgaScreenPriv->chip_rev = read_val;
	S3ReadDP(S3DeviceIdHigh, read_val);
	tgaScreenPriv->chip_id = read_val << 8;
	S3ReadDP(S3DeviceIdLow, read_val);
	tgaScreenPriv->chip_id |= read_val;
    }
    else {
	tgaScreenPriv->chip_rev = read_val & 0x0f;
	tgaScreenPriv->chip_id = (read_val >> 4) & 0x0f;
    }
    if((tgaScreenPriv->chip_id & 0xffff) == TRIO_64_ID) {
	tgaScreenPriv->is_trio = TRUE;
    }
    else  {
	tgaScreenPriv->is_trio  = FALSE;
    }
#ifdef DEBUG
    printf("is_trio %d chip_rev 0x%x chip_id 0x%x\n", 
	tgaScreenPriv->is_trio, tgaScreenPriv->chip_rev, tgaScreenPriv->chip_id);
#endif
/* 
 *  ---------------------------------------------------------------------------
 *	Initialize addresss for SDAC  (IceCube Only)
 *  ---------------------------------------------------------------------------
 */

    /* use the card itself to clear memory */
    /* this method causes a double flash on bringup */
    TGAWaitForFiFo (FiveSlots);
    TGAFillBlockSolid( 0, 0, 640, 480);
    EIEIO;
    TGAProcessorIdle();

/*
 *  ---------------------------------------------------------------------------
 * Set the screen dimentions and scissor to screen size. 
 *  ---------------------------------------------------------------------------
 */
    strcpy(gscHandle[scrnNum].devname, aixScreenName(pScreen));
    GetCurDisplayMode (pScreen, aixScreenName(pScreen), 
                       &p_crt_control[pScreen->myNum],
                       tga_makegp_info.monitor_type);
    tgaResetMonitor (pScreen, p_crt_control[pScreen->myNum]);

    TGAWaitForFiFo(FourSlots);
    S3WriteRegisterSwappedShort (S3MultiFuncControl, (short)0x1000);
    S3WriteRegisterSwappedShort (S3MultiFuncControl, (short)0x2000);
    S3WriteRegisterSwappedShort (S3MultiFuncControl, (short)0x3000 |
						(short)(pScreen->height));
    S3WriteRegisterSwappedShort (S3MultiFuncControl, (short)0x4000 |
						(short)(pScreen->width));
/*
 * XXX:SM The book says low might be better if it is set 0xff.
 */
 /*
  * Storing cursor data at 2 Meg boundary
  */
    S3WriteDP (S3CursorAddrStartHigh, 0x07); 
    S3WriteDP (S3CursorAddrStartLow, 0xfe); 
    S3WriteDP (S3ExtDACControl, 0x10); /* set bit 4. */

    TGATurnCursor (CURSOR_OFF);

/* 
 *  ---------------------------------------------------------------------------
 *	Set the screen dimentions.
 *  ---------------------------------------------------------------------------
 */
    mmWidth	= (uint) tga_makegp_info.screen_width_mm;
    mmHeight	= (uint) tga_makegp_info.screen_height_mm;


/* 
 *  ---------------------------------------------------------------------------
 *	Allocate the screen's device private area.
 *  ---------------------------------------------------------------------------
 */
    if (!(pPixmap = (PixmapPtr) xalloc (sizeof (PixmapRec))))
    {
	ErrorF("Fatal Memory error, unable to aquire memory! Exiting\n");
	return FALSE;
    }
    pPixmap->drawable.type          = DRAWABLE_WINDOW;
    pPixmap->drawable.depth         = rootDepth;
    pPixmap->drawable.pScreen       = pScreen;
    pPixmap->drawable.serialNumber  = 0;
    pPixmap->drawable.x             = 0;
    pPixmap->drawable.y             = 0;
    pPixmap->drawable.width         = pScreen->width;
    pPixmap->drawable.height        = pScreen->height;
    pPixmap->refcnt                 = 1;
    pPixmap->devPrivate.ptr 	    = (uchar *)(vramBaseAddress[scrnNum]);
    pPixmap->devKind 		    = PixmapBytePad (pScreen->width, rootDepth);

    pScreen->devPrivate     	    = (pointer) pPixmap;


/*
 *  ---------------------------------------------------------------------------
 *  *X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*
 *
 *	This is where a *large* number of things are setup.  Anything we
 *	wish to reset should *always* be done after this call.
 *
 *  *X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*X*
 *  ---------------------------------------------------------------------------
 */

    if (!tgaSetupScreen (pScreen, pPixmap->devPrivate.ptr, mmWidth, mmHeight))
	return FALSE;

    if (!tgaInitVisuals (pScreen, argc, argv))
	return FALSE;

/*
 *  ---------------------------------------------------------------------------
 *	Allocate aixScreenPrivate and initialize with default values.
 *  ---------------------------------------------------------------------------
 */
    pScrDevPriv->aix_NumFormats      = 1;
    pScrDevPriv->aix_ScreenFormats   = tgaPixmapFormats;

    pScrDevPriv->aix_ClipNotify      = NoopDDA;
    pScrDevPriv->aix_EndClipNotify   = NoopDDA;
    pScrDevPriv->aix_DDXGiveUp       = NoopDDA;
    pScrDevPriv->aix_AbortDDX        = NoopDDA;

/*
 *  ---------------------------------------------------------------------------
 *	Allocate Screen extensions and initialize with default values.
 *  ---------------------------------------------------------------------------
 */
    pExtScrDevPriv = (aixExtScreenPrivatePtr)
		     pScreen->devPrivates[aixExtScreenPrivateIndex].ptr;

    pExtScrDevPriv->aix_QueryCrossHairCursor    = NoopDDA;
    pExtScrDevPriv->aix_CreateCrossHairCursor   = (int(*)())NoopDDA;
    pExtScrDevPriv->aix_CreateMultiColorCursor  = (int(*)())NoopDDA;
    pExtScrDevPriv->aix_RecolorMultiColorCursor = (int(*)())NoopDDA;
    pExtScrDevPriv->aix_Blink                   = tgaBlink;

/*
 *  ---------------------------------------------------------------------------
 *      Set the screen cursor vectors.
 *  ---------------------------------------------------------------------------
 */
    pScrDevPriv->aix_ShowCursor		= tgaSetCursorPosition;
    pScrDevPriv->aix_HideCursor		= tgaHideCursor;

/*
 *  ---------------------------------------------------------------------------
 *      Set the screen bounds...
 *  ---------------------------------------------------------------------------
 */
    pScrDevPriv->aix_ScreenBounds.x1 = 0;
    pScrDevPriv->aix_ScreenBounds.x2 = pScreen->width - 1;
    pScrDevPriv->aix_ScreenBounds.y1 = 0;
    pScrDevPriv->aix_ScreenBounds.y2 = pScreen->height - 1;

/*
 *  ---------------------------------------------------------------------------
 *      Set the screen vectors for Power Mgmt.
 *  ---------------------------------------------------------------------------
 */
    pScrDevPriv->aix_SaveFunc		= tgaSaveState;
    pScrDevPriv->aix_RestoreFunc	= tgaRestoreState;

/*
 *  ---------------------------------------------------------------------------
 *      Create the Screen's default colormap.
 *  ---------------------------------------------------------------------------
 */
    {
	int i;

	for (i = 0; i < pScreen->numVisuals; i++)
	    if (pScreen->visuals[i].vid == pScreen->rootVisual)
		break;

	CreateColormap (pScreen->defColormap, pScreen, &pScreen->visuals[i],
			&pColormap, AllocNone, 0);

	aixAllocBlackAndWhitePixels (pColormap, 
				     aixBlackPixelText, aixWhitePixelText);

	pScreen->InstallColormap (pColormap);
    }


    if (pScreen->backingStoreSupport != NotUseful)
    {
	miInitializeBackingStore (pScreen, &tgaBSFuncRec);
    }

    mfbRegisterCopyPlaneProc (pScreen, cfb24CopyPlane);

/* ---------------------------------------------------------------------------
 * Checks card status
 *
 *
 */
#ifdef DEBUG
    dump_registers(pScreen);
#endif
    return (TRUE);
}

/*
 * ---------------------------------------------------------------------------
 * function:		tgaSetupScreen
 * description:
 *	Allocates private areas used by the ddx.
 *	Calls tgaSetScreenVectors() which sets most of the screen vectors.
 * ---------------------------------------------------------------------------
 */
Bool
tgaSetupScreen(pScreen, pbits, mmWidth, mmHeight)
    ScreenPtr	pScreen;
    pointer	pbits;			/* pointer to screen bitmap */
    int		mmWidth, mmHeight;	/* dots per inch */
{

    void	tgaSetScreenVectors();
    extern int	defaultColorVisualClass;

    if (!mfbAllocatePrivates(pScreen,
			     &cfbWindowPrivateIndex, 
			     &cfbGCPrivateIndex))
    {
	return FALSE;
    }

    if (!AllocateWindowPrivate(pScreen, cfbWindowPrivateIndex,
			       sizeof(cfbPrivWin)) ||
	!AllocateGCPrivate(pScreen, cfbGCPrivateIndex, sizeof(cfbPrivGC)))
    {
	return FALSE;
    }


    tgaSetScreenVectors (pScreen, pbits, mmWidth, mmHeight);

    return TRUE;
}



/*
 * ---------------------------------------------------------------------------
 * function:		tgaSetScreenVectors
 * description:
 *	Sets most of the screen vectors.
 * ---------------------------------------------------------------------------
 */
void
tgaSetScreenVectors(pScreen, pbits, mmWidth, mmHeight)
    ScreenPtr	pScreen;
    pointer	pbits;			/* pointer to screen bits */
    int		mmWidth, mmHeight;	/* dots per inch */
{

    extern Bool defaultBackingStore;
/*
 * ---------------------------------------------------------------------------
 * 	Function call declarations.  (Alphabetical listing)
 * ---------------------------------------------------------------------------
 */
    extern void					cfb24CopyWindow();
    extern Bool					cfb24CreateGC(); 
    extern Bool					tgaCloseScreen();
    extern Bool					tgaDisplayCursor();
    extern void					tgaHideCursor();
    extern void					cfb24InstallColormap(); 
    extern Bool					cfb24InitializeColormap(); 
    extern int					cfb24ListInstalledColormaps();
    extern void					tgaQueryBestSize();
    extern Bool					tgaRealizeCursor();
    extern void					tgaRecolorCursor();
    extern void					cfb24ResolveColor();         
    extern void					tgaSetCursorPosition();
    extern void					tgaStoreColors();	     
    extern void					cfb24UninstallColormap();   
    extern Bool					tgaUnrealizeCursor();
/*
 * ---------------------------------------------------------------------------
 * 	Set all screen vectors here. 
 *	Note: The order is the same as shown in scrnintstr.h
 * ---------------------------------------------------------------------------
 */
    pScreen->mmWidth				= mmWidth;
    pScreen->mmHeight				= mmHeight;
    pScreen->defColormap			= FakeClientID(0);
    pScreen->minInstalledCmaps			= 1;
    pScreen->maxInstalledCmaps			= 1;

    if (defaultBackingStore == WhenMapped)
	pScreen->backingStoreSupport		= WhenMapped;
    else if (aixAllowBackingStore)
	pScreen->backingStoreSupport		= Always;
    else
	pScreen->backingStoreSupport		= NotUseful;

    pScreen->saveUnderSupport			= NotUseful;
/*
             whitePixel				set in tgaInitVisuals
             blackPixel				set in tgaInitVisuals
	     rgf,				?????????? She's Hungarian?
	     GCperDepth,			??????????
	     PixmapPerDepth,			??????????
	     devPrivate,			set in tgaScreenInit 
             numVisuals				set in tgaInitVisuals
             visuals				set in tgaInitVisuals
	     WindowPrivateLen,			??????????
	     *WindowPrivateSizes,		??????????
	     totalWindowSize,			??????????
	     GCPrivateLen,			??????????
	     *GCPrivateSizes,			??????????
	     totalGCSize,			??????????
*/
    pScreen->CloseScreen 			= tgaCloseScreen;
    pScreen->QueryBestSize 			= tgaQueryBestSize;
    pScreen->SaveScreen				= tga_SaveScreen;
    pScreen->GetImage				= cfb24GetImage;
    pScreen->GetSpans				= cfb24GetSpans;
    pScreen->PointerNonInterestBox		= AIXPointerNonInterestBox;
    pScreen->SourceValidate			= (void (*)()) 0;
    pScreen->CreateWindow			= cfb24CreateWindow;
    pScreen->DestroyWindow			= cfb24DestroyWindow;
    pScreen->PositionWindow			= cfb24PositionWindow;
    pScreen->ChangeWindowAttributes		= cfb24ChangeWindowAttributes;
    pScreen->RealizeWindow			= cfb24MapWindow;
    pScreen->UnrealizeWindow			= cfb24UnmapWindow;
    pScreen->ValidateTree			= miValidateTree;
    pScreen->PostValidateTree			= (void (*)()) 0;
    pScreen->WindowExposures			= miWindowExposures;
    pScreen->PaintWindowBackground		= cfb24PaintWindow;
    pScreen->PaintWindowBorder			= cfb24PaintWindow;
    pScreen->CopyWindow				= cfb24CopyWindow; 
    pScreen->ClearToBackground			= miClearToBackground;
    pScreen->ClipNotify				= (void (*)()) 0;
    pScreen->CreatePixmap			= cfb24CreatePixmap;
    pScreen->DestroyPixmap			= cfb24DestroyPixmap;
/*
	     SaveDoomedAreas,			set in miInitializeBackingStore
	     RestoreAreas,			set in miInitializeBackingStore
	     ExposeCopy,			set in miInitializeBackingStore
	     TranslateBackingStore,		set in miInitializeBackingStore
	     ClearBackingStore,			set in miInitializeBackingStore
	     DrawGuarantee,			set in miInitializeBackingStore
*/
    pScreen->RealizeFont			= mfbRealizeFont;
    pScreen->UnrealizeFont			= mfbUnrealizeFont;
    pScreen->ConstrainCursor			= AIXConstrainCursor;
    pScreen->CursorLimits			= AIXCursorLimits;
    pScreen->DisplayCursor			= tgaDisplayCursor;
    pScreen->RealizeCursor			= tgaRealizeCursor;
    pScreen->UnrealizeCursor			= tgaUnrealizeCursor;
    pScreen->RecolorCursor			= tgaRecolorCursor;
    pScreen->SetCursorPosition			= AIXSetCursorPosition;
    pScreen->CreateGC				= cfb24CreateGC;  
    pScreen->CreateColormap			= cfb24InitializeColormap; 
    pScreen->DestroyColormap			= NoopDDA;
    pScreen->InstallColormap 			= cfb24InstallColormap;   
    pScreen->UninstallColormap			= cfb24UninstallColormap;
    pScreen->ListInstalledColormaps		= cfb24ListInstalledColormaps;
    pScreen->StoreColors			= NoopDDA;    
    pScreen->ResolveColor			= cfb24ResolveColor;
    pScreen->RegionCreate			= miRegionCreate;
    pScreen->RegionInit				= miRegionInit;
    pScreen->RegionCopy				= miRegionCopy;
    pScreen->RegionDestroy			= miRegionDestroy;
    pScreen->RegionUninit			= miRegionUninit;
    pScreen->Intersect				= miIntersect;
    pScreen->Union				= miUnion;
    pScreen->Subtract				= miSubtract;
    pScreen->Inverse				= miInverse;
    pScreen->RegionReset			= miRegionReset;
    pScreen->TranslateRegion			= miTranslateRegion;
    pScreen->RectIn				= miRectIn;
    pScreen->PointInRegion			= miPointInRegion;
    pScreen->RegionNotEmpty			= miRegionNotEmpty;
    pScreen->RegionEmpty			= miRegionEmpty;
    pScreen->RegionExtents			= miRegionExtents;
    pScreen->RegionAppend			= miRegionAppend;
    pScreen->RegionValidate			= miRegionValidate;
    pScreen->BitmapToRegion			= mfbPixmapToRegion;
    pScreen->RectsToRegion			= miRectsToRegion;
    pScreen->SendGraphicsExpose			= miSendGraphicsExpose;
    pScreen->BlockHandler			= AIXBlockHandler;
    pScreen->WakeupHandler			= AIXWakeupHandler;
    pScreen->blockData				= (pointer) 0;
    pScreen->wakeupData				= (pointer) 0;
/*
	     *devPrivates,			??????????
*/
    pScreen->PostValidateTree			= (void (*)()) 0;
/*
	     PixmapPrivateLen,			??????????
	     *PixmapPrivateSizes,		??????????
	     totalPixmapSize,			??????????
	     rootLayer,				??????????
	     numLayers,				??????????
	     layersCount,			??????????
	     layers				??????????
*/


}

/*
 * ---------------------------------------------------------------------------
 * function:            tgaCloseScreen
 * description:
 *	This routine should to the following:
 *		- Disable the cursor.
 *		- Clear the screen.		XXX:Done automagically
 *		- Do an UNMAKE_GP.
 * ---------------------------------------------------------------------------
 */
Bool
tgaCloseScreen(num, pScreen)
    int		num;
    ScreenPtr	pScreen;
{
    register int        scrnNum = pScreen->myNum;
    extern void		tgaDestroyVisuals();
    static unmake_gp 	unmake_gp_info;
    tgaScreenPrivPtr	tgaScreenPriv;
    unsigned char       read_val;
    int                 i;

    TGATurnCursor (CURSOR_OFF);

/* Disable clock doubling mode */
S3WriteRegister(S3SequencerIndex, S3ExtendedSequencerSR18);
S3ReadRegister(S3SequencerData, read_val);
read_val &= ~(0x80);
S3WriteRegister(S3SequencerData, read_val);

    S3WriteDP(0x67,0x00);
    if (pScreen->devPrivate)
	xfree (pScreen->devPrivate);

    tgaDestroyVisuals (pScreen);

    unmake_gp_info.error = 0;
    if (aixgsc (gscHandle[scrnNum].handle, UNMAKE_GP, &unmake_gp_info))
    {
	/* UNMAKE_GP failed */
	ErrorF (stderr, "unmake_gp_info.error = %d\n", unmake_gp_info.error);
	printf ("unmake_gp_info.error = %d\n", unmake_gp_info.error);
    }

    /* deallocate private */
    tgaScreenPriv = (tgaScreenPrivPtr)
			pScreen->devPrivates[tgaScreenPrivateIndex].ptr;
    if(tgaScreenPriv)
	Xfree(tgaScreenPriv);
    pScreen->devPrivates[tgaScreenPrivateIndex].ptr = NULL;

    return (TRUE);
}



/*
 * ---------------------------------------------------------------------------
 * function:            tga_ddxinit
 *
 * description:
 *     ddx-specific initializaion routine.  Will set a pointer to point to
 *     adapter specific initalization routine.  We also set the default visuals
 *     here.
 * ---------------------------------------------------------------------------
 */
int
tga_ddxInit(screenInfo, display, argc, argv)
    ScreenInfo		*screenInfo;
    DisplayRecPtr	display;
    int			argc;
    char		*argv[];
{
    Bool            (*ScrInit)();

    int             fmtNdx;
    PixmapFormatPtr pixmapFormats;
    PixmapFormatPtr oldFormat,
                    newFormat;

    SavedDisplayPtr = display;

    ScrInit       = tgaScreenInit;
    pixmapFormats = tgaPixmapFormats;

    if (!aixFormatExists (screenInfo,pixmapFormats))
    {
	newFormat               = pixmapFormats;
	oldFormat  = &screenInfo->formats[screenInfo->numPixmapFormats++];
	oldFormat->depth        = newFormat->depth;
	oldFormat->bitsPerPixel = newFormat->bitsPerPixel;
	oldFormat->scanlinePad  = newFormat->scanlinePad;

	if (screenInfo->numPixmapFormats > MAXFORMATS)
	{
	    ErrorF ("Too many PixmapFormats! Exiting\n");
	    exit (1);
	}
    }
      
    AddScreen (ScrInit, argc, argv); 

    return 1;
}

#ifdef DEBUG
/* Dumps some basic registers for TRIO-64 */
dump_registers(pScreen)
ScreenPtr pScreen;
{
 	register int scrnNum = pScreen->myNum;
	#define NUM_ENH_REGS 14
	unsigned short enh_regs[] = {0x42e8, 0x4ae8, 0x82e8, 0x86e8, 
				  0x8ae8, 0x8ee8, 0x92e8, 0x96e8,
				  0x9ae8, 0xa2e8, 0xa6e8, 0xaae8, 
				  0xaee8, 0xb2e8 };
	unsigned short tmp;
        unsigned int ltmp;
	register int i;
	fprintf(stderr,"Register Dump for S3\n");
	fprintf(stderr,"---------------------------------------------------\n");
	/* General or External Registers */
	fprintf(stderr,"General Registers\n");
	fprintf(stderr,"---------------------------------------------------\n");
	S3ReadRegister(0x3c2, tmp);
	fprintf(stderr,"Input Status 0		0x3c2 =	0x%x\n",tmp);
	S3ReadRegister(0x3cc, tmp);
	fprintf(stderr,"Micellaneous Output 	0x3cc =	0x%x\n",tmp);
	S3ReadRegister(0x3ca, tmp);
	fprintf(stderr,"Feature Control 	0x3ca =	0x%x\n",tmp);
	S3ReadRegister(0x3da, tmp);
	fprintf(stderr,"Input Status 1 		0x3da =	0x%x\n",tmp);
	fprintf(stderr,"---------------------------------------------------\n");


	fprintf(stderr,"Sequencer  Registers\n");
	fprintf(stderr,"---------------------------------------------------\n");
	/* Unlock register value */
	
    	S3WriteRegister (S3SequencerIndex, S3UnlockSequencer);
    	S3WriteRegister (S3SequencerData,  S3_UNLOCK_SEQUENCER);
	for(i = 0; i<= 0x4; i++) {
		S3WriteRegister(S3SequencerIndex, i);
		S3ReadRegister(S3SequencerData, tmp);
		if(!(i % 3))
			fprintf(stderr,"\n");
		fprintf(stderr,"0x%x = 0x%x, ",i,tmp);
	}
	for(i = 0x8; i<= 0xd; i++) {
		S3WriteRegister(S3SequencerIndex, i);
		S3ReadRegister(S3SequencerData, tmp);
		if(!(i % 3))
			fprintf(stderr,"\n");
		fprintf(stderr,"0x%x = 0x%x, ",i,tmp);
	}
        for(i = 0x10; i<= 0x1C; i++) {
	        S3WriteRegister(S3SequencerIndex, i);
	        S3ReadRegister(S3SequencerData, tmp);
	        if(!(i % 3))
		         fprintf(stderr,"\n");
	        fprintf(stderr,"0x%x = 0x%x, ",i,tmp);
        }
    	S3WriteRegister (S3SequencerIndex, S3UnlockSequencer);
    	S3WriteRegister (S3SequencerData,  S3_LOCK_SEQUENCER);
	fprintf(stderr,"\n-------------------------------------------------\n");

	fprintf(stderr,"CRTC  Registers\n");
	fprintf(stderr,"---------------------------------------------------\n");
	for(i = 0x0; i<= 0x6f; i++) {
		if(i == 0x6f ) {
			unsigned char save_val;
			S3ReadDP(0x39, save_val);
			S3WriteDP(0x39, 0xa5);
			S3ReadDP(i,tmp);
			S3WriteDP(0x39, save_val);
		}
		else {
			S3ReadDP(i, tmp);
		}
		if(!(i % 6))
			fprintf(stderr,"\n");
		fprintf(stderr,"0x%2x = 0x%2x, ",i,tmp);
	}
	fprintf(stderr,"\n-------------------------------------------------\n");
	fprintf(stderr,"Enhanced Registers \n");
	fprintf(stderr,"-------------------------------------------------\n");
	for(i=0;i<NUM_ENH_REGS;i++) {
		S3ReadRegisterShort(enh_regs[i], tmp);
		if(!(i % 3))
			fprintf(stderr,"\n");
		fprintf(stderr,"0x%4x = 0x%4x, ",enh_regs[i],tmp);
	}
	S3ReadRegisterLong(0xaee8,ltmp);
	fprintf(stderr,"0xaae8 = 0x%8x\n",ltmp);
	
        fprintf(stderr,"\n-------------------------------------------------\n");
        fprintf(stderr,"Graphics Controller Registers \n");
        fprintf(stderr,"-------------------------------------------------\n");
        for(i=0;i<9;i++){
	   S3WriteRegister(0x3ce,i);
           S3ReadRegister (0x3cf,tmp);
	   if(!(i % 3))
		fprintf(stderr,"\n");
	   fprintf(stderr,"0x%x = 0x%4x, ",i,tmp);
        } 
	fprintf(stderr,"\n-------------------------------------------------\n");
}
#endif

