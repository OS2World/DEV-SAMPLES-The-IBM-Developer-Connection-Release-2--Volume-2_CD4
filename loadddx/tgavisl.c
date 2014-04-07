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
 *   FUNCTIONS: getVisualFromID
 *		tgaDestroyVisuals
 *		tgaInitVisuals
 *
 */
#include "X.h"
#include "servermd.h"
#include "scrnintstr.h"
#include "cursor.h"
#include "pixmapstr.h"
#include "resource.h"
#include "miscstruct.h"
#include "colormap.h"
#include "mibstore.h"
#include "os.h"

#include "aixIO.h"
#include "aixPrivates.h"


/******************************************************************************
*                                                                             *
*                           VisualRec                                         *
*                                                                             *
******************************************************************************/
#define RM 0x00ff0000
#define GM 0x0000ff00
#define BM 0x000000ff
#define RS 0
#define GS 8
#define BS 16

#define	NUM_VISUAL_ENTRIES \
	((sizeof visuals_template)/(sizeof visuals_template[0]))


static VisualRec visuals_template[] = 
{/* vid  class        bpRGB cmpE nplan rMask gMask bMask oRed oGreen oBlue */
  {0,  PseudoColor,  8,     256,  8,    0,    0,    0,    0,    0,   0, NULL},
  {0,  GrayScale  ,  8,     256,  8,    0,    0,    0,    0,    0,   0, NULL},
  {0,  StaticColor,  8,     256,  8,    0,    0,    0,    0,    0,   0, NULL},
  {0,  StaticGray ,  8,     256,  8,    0,    0,    0,    0,    0,   0, NULL},
  {0,  TrueColor,    8,     256,  24,   RM,   GM,   BM,   BS,  GS,  RS, NULL},
  {0,  TrueColor,    8,     256,  24,   RM,   GM,   BM,   BS,  GS,  RS, NULL},
  {0,  TrueColor,    8,     256,  24,   RM,   GM,   BM,   BS,  GS,  RS, NULL},
  {0,  DirectColor,  8,     256,  24,   RM,   GM,   BM,   BS,  GS,  RS, NULL},
  {0,  DirectColor,  8,     256,  24,   RM,   GM,   BM,   BS,  GS,  RS, NULL},
  {0,  DirectColor,  8,     256,  24,   RM,   GM,   BM,   BS,  GS,  RS, NULL},
};
#define STATIC_GRAY_INDEX	3
#define GRAY_SCALE_INDEX	1	
#define STATIC_COLOR_INDEX	2	
#define PSEUDO_COLOR_INDEX	0	
#define TRUE_COLOR_INDEX	4
#define DIRECT_COLOR_INDEX	7	

/******************************************************************************
*                                                                             *
*                            DepthRec                                         *
*                                                                             *
******************************************************************************/

#define NUM_DEPTHS_ENTRIES \
		((sizeof depths_template)/(sizeof depths_template[0]))

static DepthRec depths_template[] = 
{ /* depth	numVid		vids */
   { 24,         NUM_VISUAL_ENTRIES,            NULL}
};

#define DEPTH_24_INDEX	0

/*  Visual structures only get set up for depth8 and up  */

#define MIN_DEPTH	DEPTH_24_INDEX
#define MAX_DEPTH	DEPTH_24_INDEX


/******************************************************************************
*                                                                             *
* FUNCTION:  tgaInitVisuals                                                   *
*                                                                             *
* IN:  pScreen  Screen that visual and depths need to be setup                *
* OUT: return   Returns True if sucessful                                     *
*                                                                             *
* PURPOSE:                                                                    *
*     Initializes the following fields in pScreen:                            *
*                                                                             *
*       pScreen->numDepths, pScreen->allowedDepths,                           *
*       pScreen->numVisuals, pScreen->visuals,                                *
*       pScreen->rootDepth, pScreen->rootVisual,                              *
*       pScreen->blackPixel, pScreen->whitePixel.                             *
*                                                                             *
*                                                                             *
*                                                                             *
*                                                                             *
******************************************************************************/


Bool tgaInitVisuals(pScreen, argc, argv)
	ScreenPtr  pScreen;
	int        argc;
	char      *argv[];
{
    int			i;
    int			vid_size, total_size;

    VisualPtr		visuals;
    DepthPtr		depths;
    VisualID		*vids;


    int			DefaultVisual, DefaultDepth;
    Bool		visualNotSpecified, depthNotSpecified;
    Bool		supported;

    int			rootIndex;

    extern DisplayRecPtr SavedDisplayPtr;


    /**************************************************************************
    *                                                                         *
    *	Setup depth and visual array                                          *
    *                                                                         *
    *		xalloc space for them and initialize them from template.      *
    *                                                                         *
    *		Note: the space for them is allocated in one contiguous       *
    *                 chunk and then is partitioned into pieces. This         *
    *		      saves memory, but remember to just do one xfree.        *
    *                                                                         *
    *		Be careful here: good opportunity for memory bugs.            *
    *                                                                         *
    **************************************************************************/

    vid_size   = NUM_VISUAL_ENTRIES * sizeof(VisualID);
    total_size = sizeof(visuals_template) + sizeof(depths_template) + vid_size;

    if (!(visuals = (VisualPtr) xalloc(total_size) ))
	return FALSE;
    
    depths      = (DepthPtr) (visuals + NUM_VISUAL_ENTRIES);
    vids	= (VisualID *) (depths + NUM_DEPTHS_ENTRIES);


    /****************************************
    *                                       *
    *	Initialize the visuals and depths   *
    *		from template structure     *
    *                                       *
    *	Initialize visual_priv to all zeros *
    *                                       *
    ****************************************/

    memcpy(visuals, visuals_template, sizeof(visuals_template));
    memcpy(depths,  depths_template,  sizeof(depths_template));


    /****************************************
    *                                       *
    *	Initialize vid array of DepthRec[A    *
    *                                       *
    ****************************************/

    for (i=MIN_DEPTH; i< MAX_DEPTH + 1; i++)
    {
	if (depths[i].numVids)
	{
		depths[i].vids = vids;
		vids += depths[i].numVids;
	}
    }



    /**************************************************************************
    *                                                                         *
    *	Setup pScreen's visuals and allowedDepths array                       *
    *                                                                         *
    **************************************************************************/

    pScreen->allowedDepths = depths;
    pScreen->visuals       = visuals;
  
    pScreen->numVisuals =  NUM_VISUAL_ENTRIES;
    pScreen->numDepths  =  NUM_DEPTHS_ENTRIES;



    /**************************************************************************
    *                                                                         *
    *	Get a resource ID for every supported visual                          *
    *                                                                         *
    **************************************************************************/

    for (i=0; i<pScreen->numVisuals; i++)
    {
        visuals[i].vid = (unsigned long) FakeClientID(0); 
    }


    /**************************************************************************
    *                                                                         *
    *	Get root Depth and Visual from command line                           *
    *                                                                         *
    **************************************************************************/

    visualNotSpecified = !aixGetRequestedVisual(argc,argv,
		SavedDisplayPtr->name,
		SavedDisplayPtr->display_number,
		&DefaultVisual /* RETURN */);

    depthNotSpecified = !aixGetRequestedDepth(argc,argv,
		SavedDisplayPtr->name,
		SavedDisplayPtr->display_number,
		&DefaultDepth /* RETURN */);

    if (visualNotSpecified)
	DefaultVisual = TrueColor;

    if (depthNotSpecified)
	DefaultDepth = 24;

    /**************************************************************************
    *                                                                         *
    *	Check to see if the DefaultDepth and DefaultVisual is supported       *
    *                                                                         *
    **************************************************************************/


    supported = ((DefaultDepth == 24) && 
		   ((DefaultVisual == StaticGray)  ||
		    (DefaultVisual == GrayScale)   ||
		    (DefaultVisual == StaticColor) ||
		    (DefaultVisual == PseudoColor) ||
		    (DefaultVisual == TrueColor)   ||
		    (DefaultVisual == DirectColor)));
	

    if (!supported)
    {
	AbortDDX();
	/* See com/XTOP/server/ddx/ibm/AIX/X.msg */
        /* M_MSG_13 1362-013 Specified depth and/or visual not supported.*/


	aixErrMsg(13); 
	exit(1);
    }


    pScreen->rootDepth  = 24;
    pScreen->whitePixel = (Pixel) 1;
    pScreen->blackPixel = (Pixel) 0;


    switch (DefaultVisual) {

        case DirectColor:
			rootIndex = DIRECT_COLOR_INDEX;
			break;
        case TrueColor:
			rootIndex = TRUE_COLOR_INDEX;
			break;
        case PseudoColor:
			rootIndex = PSEUDO_COLOR_INDEX;
			break;
	case StaticColor:
			rootIndex = STATIC_COLOR_INDEX;
			break;
	case GrayScale:
			rootIndex = GRAY_SCALE_INDEX;
			break;
        case StaticGray:
			rootIndex = STATIC_GRAY_INDEX;
			break;
        default:
			/* ERROR */
			break;
    }

    pScreen->rootVisual = visuals[rootIndex].vid;


    /**************************************************************************
    *                                                                         *
    *	Fill in vids array in DepthRec                                        *
    *                                                                         *
    **************************************************************************/
    depths[DEPTH_24_INDEX].vids[0] = visuals[PSEUDO_COLOR_INDEX].vid;
    depths[DEPTH_24_INDEX].vids[1] = visuals[GRAY_SCALE_INDEX].vid;
    depths[DEPTH_24_INDEX].vids[2] = visuals[STATIC_COLOR_INDEX].vid;
    depths[DEPTH_24_INDEX].vids[3] = visuals[STATIC_GRAY_INDEX].vid;
    depths[DEPTH_24_INDEX].vids[4] = visuals[TRUE_COLOR_INDEX].vid;
    depths[DEPTH_24_INDEX].vids[5] = visuals[TRUE_COLOR_INDEX+1].vid;
    depths[DEPTH_24_INDEX].vids[6] = visuals[TRUE_COLOR_INDEX+2].vid;
    depths[DEPTH_24_INDEX].vids[7] = visuals[DIRECT_COLOR_INDEX].vid;
    depths[DEPTH_24_INDEX].vids[8] = visuals[DIRECT_COLOR_INDEX+1].vid;
    depths[DEPTH_24_INDEX].vids[9] = visuals[DIRECT_COLOR_INDEX+2].vid;

    return (TRUE);
}



/******************************************************************************
*                                                                             *
* FUNCTION: tgaDestroyVisuals                                                 *
*                                                                             *
* IN:  pScreen  Screen that visual and depths need to be setup                *
* OUT: none                                                                   *
*                                                                             *
* PURPOSE:                                                                    *
*     Frees the memory alloc'ed in tgaInitVisuals().                          *
*                                                                             *
******************************************************************************/

void tgaDestroyVisuals(pScreen)
	ScreenPtr pScreen;
{

    /**************************************************************************
    *                                                                         *
    * Since there was only one large alloc that was done and subsequently     *
    * partitioned out into visuals, depths, etc, we only need to do 1 free    *
    *                                                                         *
    **************************************************************************/

    xfree(pScreen->visuals);

    return;
}	


/******************************************************************************
*                                                                             *
* FUNCTION: getVisualFromID						      *
*                                                                             *
* IN:  pScreen  containing visual list					      *	
*      vid	visual ID to search for					      *
* OUT: visual pointer corresponding the visual ID vid			      *
*                                                                             *
*                                                                             *
******************************************************************************/



VisualPtr getVisualFromID(pScreen, vid)
    ScreenPtr	pScreen;
    VisualID	vid;
{
    VisualPtr   pVisual;
    int		i;

    for (i=pScreen->numVisuals, pVisual=pScreen->visuals; i--; pVisual++)
    {
	if (pVisual->vid == vid)
	    return (pVisual);
    }

    return (NULL);
}

