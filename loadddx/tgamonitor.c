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
 *   FUNCTIONS: 
 *		tgaGetMatchedModeID
 *		tgaGetDefaultMode
 *		tgaResetMonitor
 *
 */
#include <stdio.h>
#include <scrnintstr.h>
#include "tgautils.h"
#include "tgas3regs.h"
#include "tga.h"
#include "tgamonitor.h"


/*
 * ---------------------------------------------------------------------------
 * function:		tgaGetMatchedModeID
 * description:
 *	Compares what we support with what the user has selected.
 * ---------------------------------------------------------------------------
 */
void
tgaGetMatchedModeID (mode_id, str_len, p_crt_control)
char			mode_id[];
int			str_len;
tga_crt_control_ptr 	*p_crt_control;		
{
    int	   matched = FALSE;
 
    *p_crt_control = ts_crt_control;

    /* search in s_crt_contrl to find out a matched mode_id */
    while ( strcmp((const unsigned char*)*p_crt_control, "0") )
    {
	if ( strncmp((*p_crt_control)->mode_id, mode_id, str_len) )
	    (*p_crt_control) ++;
	else
	{
	    matched = TRUE;
	    break;
	}
    }

    /* if no matched mode_id found, use the default mode */
    if ( !matched)
	*p_crt_control = ts_crt_control;
}

/*
 * ---------------------------------------------------------------------------
 * function:		tgaGetDefaultMode
 * description:
	Returns the default mode for trio
 * ---------------------------------------------------------------------------
 */
pointer tgaGetDefaultMode()
{
	return (pointer)ts_crt_control;
}

/*
 * ---------------------------------------------------------------------------
 * function:		tgaResetMonitor
 * description:
 *	Resets the monitor control registers to correspond to the user's
 *	selection.
 * ---------------------------------------------------------------------------
 */
void
tgaResetMonitor (pScreen, p_crt_control)
ScreenPtr       		pScreen;
tga_crt_control_ptr		p_crt_control;
{
    register int 	scrnNum = pScreen->myNum;
    int			index;
    short		data;
    short 		mask;
    uint		tmp;
    volatile uchar	*pData;
    extern uint	tgaScreenWidth[], tgaScreenHeight[];
    volatile uchar		read_val;


    tgaScreenWidth[scrnNum]  = pScreen->width  
	= (uint) p_crt_control->x_resolution;
    tgaScreenHeight[scrnNum] = pScreen->height 
	= (uint) p_crt_control->y_resolution;

/*
 * Disable horizontal and vertical sync.
 */
    S3WriteRegister (S3SequencerIndex, S3UnlockSequencer);
    S3WriteRegister (S3SequencerData,  S3_UNLOCK_SEQUENCER);
    S3WriteRegister (S3SequencerIndex, S3ExtendedSequencerSRD);
    S3WriteRegister (S3SequencerData,  S3_HVSYNC_LOW);

    /* Also turn of Screen */
    S3WriteRegister(S3SequencerIndex, 0x1);
    S3ReadRegister(S3SequencerData, read_val);
    read_val |= 0x20;
    S3WriteRegister(S3SequencerData, read_val);

    /* make sure we are in LPB mode */

    S3WriteDP (0x39, 0xa5); /* unlock */
    S3WriteDP (0x36, 0x8a);
    S3WriteDP (0x68, 0x8f);
    S3WriteDP (0x6f, 0x1e);
 
    pData = p_crt_control->crtc_settings;
    for (index=0; index<CRTC_LIST_TOTAL; index++)
    {
        S3WriteDP (index, *pData++);
    }

    pData = p_crt_control->extended_settings;
    for (index=0; index<EXTENDED_LIST_TOTAL; index++)
    {
        S3WriteDP (*pData++, *pData++);
    }

#if 1
    /* This has been moved to CR66 */
    S3WriteRegisterSwappedShort (S3AdvancedFunctionControl,
                (short) p_crt_control->AdvancedFunctionControl);
#endif

    S3ReadRegister(S3MiscOutputRead, read_val);
    read_val |= 0x0C;	/* Selects CLKs from PLL */
    S3WriteRegister(S3MiscOutputWrite, read_val);
    
#if 1
    /* Program MCLK first - this might be already set by the low level
     * driver code.. 
     */
    S3WriteRegister(S3SequencerIndex, S3ExtendedSequencerSR10);
    S3WriteRegister(S3SequencerData, p_crt_control->pll_mclk_low);
    S3WriteRegister(S3SequencerIndex, S3ExtendedSequencerSR11);
    S3WriteRegister(S3SequencerData, p_crt_control->pll_mclk_high);
    /* Undocumented */
    S3WriteRegister(S3SequencerIndex, 0x1B);
    S3WriteRegister(S3SequencerData, p_crt_control->pll_mclk_low);
    S3WriteRegister(S3SequencerIndex, 0x1A);
    S3WriteRegister(S3SequencerData, p_crt_control->pll_mclk_high);
#endif

    /* Now write the DCLK frequency */
    S3WriteRegister(S3SequencerIndex, S3ExtendedSequencerSR12);
    S3WriteRegister(S3SequencerData, p_crt_control->pll_dclk_low);
    S3WriteRegister(S3SequencerIndex, S3ExtendedSequencerSR13);
    S3WriteRegister(S3SequencerData, p_crt_control->pll_dclk_high);


    /* Disable clock doubling mode */
    S3WriteRegister(S3SequencerIndex, S3ExtendedSequencerSR18);
    S3ReadRegister(S3SequencerData, read_val);
    read_val &= ~(0x80);
    S3WriteRegister(S3SequencerData, read_val);

    /* Now load both the DCLK and MCLK immediately */
    S3WriteRegister(S3SequencerIndex, S3ExtendedSequencerSR15);
    S3ReadRegister(S3SequencerData, read_val);

   /* Turn bit 5 to 1 and then to 0 , Also set bit 4 for clock doubling */
    read_val |= 0x20;
    S3WriteRegister(S3SequencerData, read_val);
    S3ReadRegister(S3SequencerData, read_val);
    read_val &= ~(0x20);
    S3WriteRegister(S3SequencerData, read_val);

   /* Convert Big to Little Indian */
    S3ReadDP(0x53,read_val);
    read_val &= 0xf9;
    read_val |= 0x04;
    S3WriteDP(0x53,read_val);

    /* Let the CPU write to video memory */
    S3WriteRegister(S3SequencerIndex,0x02); 
    S3WriteRegister(S3SequencerData,0x0f); 

   /* Done with MCLK and DCLK programming  */
/*
 * Enable horizontal and vertical sync.
 */
    S3WriteRegister (S3SequencerIndex, S3ExtendedSequencerSRD);
    S3WriteRegister (S3SequencerData,  S3_HVSYNC_NORMAL);

    /* Turn on Screen */
    S3WriteRegister(S3SequencerIndex, 0x1);
    S3ReadRegister(S3SequencerData, read_val);
    read_val &= ~(0x20);
    S3WriteRegister(S3SequencerData, read_val);

    S3WriteRegister (S3SequencerIndex, S3UnlockSequencer);
    S3WriteRegister (S3SequencerData,  S3_LOCK_SEQUENCER);
}

