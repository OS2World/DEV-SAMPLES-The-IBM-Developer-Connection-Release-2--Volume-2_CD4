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
 *   FUNCTIONS: SDAC_register_read
 *		SDAC_register_write
 *		SDAC_set_color
 *		SDAC_turn_cursor
 *
 */

#include <sys/types.h>


/*
 *  ---------------------------------------------------------------------------
 *	structure:	_SDACRegPtrs
 *	description:
 *		Stores register addresses and the encoded register bank index.
 *  ---------------------------------------------------------------------------
 */
typedef struct _SDACRegPtrs {
    int lut_index;
    int lut_data;
    int pixel_read_mask;
    int read_lut_index;
    int pll_index;
    int pll_data;
    int enh_cmd;
    int read_pll_index;
} SDAC_regs;

/*
 *  ---------------------------------------------------------------------------
 *	sdac registers:
 *
 *	RS[2:0]		Register		Bytes
 *      ------------------------------------------------
 *	000		Write LUT Index		1
 *	001		LUT Read/Write Data	3
 *	010		Pixel Read Mask		1
 *	011		Read LUT Index		1
 *	100		Write PLL Index		1
 *	101		PLL Read/Write Data	2 or 1
 *	110		Enhanced Command	1
 *	111		Read PLL Index		1	
 *
 *  On the S3 registers 3c8, 3c9, 3c6, 3c7 cause 00, 01, 10, 11 to be driven
 *  to RS[1:0] respectively. 
 *
 *  The second byte from the left is the register bank select, since there
 *  are only two banks on the sdac it will be either a 1 or a 0.
 *  ---------------------------------------------------------------------------
 */

/* RS:0xx */ 
#define SDAC_LUT_INDEX		0x000003c8
#define SDAC_LUT_DATA		0x000003c9
#define SDAC_PIXEL_READ_MASK	0x000003c6
#define SDAC_READ_LUT_INDEX	0x000003c7

/* RS:1xx */
#define SDAC_PLL_INDEX		0x010003c8
#define SDAC_PLL_DATA		0x010003c9
#define SDAC_ENH_CMD		0x010003c6
#define SDAC_READ_PLL_INDEX	0x010003c7


/*
 *  ---------------------------------------------------------------------------
 *      macro:       SDAC_register_write
 *      description:
 *              Writes to the extended DAC control registers on the SDAC.
 *  ---------------------------------------------------------------------------
 */
#define SDAC_register_write(register, data)				\
{									\
    uchar SDAC_tmp;							\
/*									\
 * Select and clear the DAC register select bits. XXX:SM - optimize	\
 */									\
    S3ReadDP  (S3ExtDACControl, SDAC_tmp);				\
    SDAC_tmp &= 0xFC;							\
									\
/* 									\
 * We have to select which bank we will be writing into, this is done 	\
 * by setting RS3, (the left most bit).					\
 */									\
    S3WriteDP (S3ExtDACControl, 					\
	((volatile unsigned char)(SDAC[scrnNum].register >> 24)) | 	\
	(SDAC_tmp));							\
									\
/*									\
 * (register & 0xf0ffffff) clears the encoded register bank select 	\
 * bits.								\
 */									\
    S3WriteRegister ((SDAC[scrnNum].register & 0xf0ffffff), data);	\
}

/*
 *  ---------------------------------------------------------------------------
 *      macro:       SDAC_register_read
 *      description:
 *              Reads to the extended DAC control registers on the SDAC.
 *  ---------------------------------------------------------------------------
 */
#define SDAC_register_read(register, data)				\
{									\
    uchar SDAC_tmp;							\
									\
    S3ReadDP  (S3ExtDACControl, SDAC_tmp);				\
    SDAC_tmp &= 0xFC;							\
    S3WriteDP (S3ExtDACControl, SDAC_tmp);				\
									\
    S3WriteData    ((SDAC[scrnNum].register >> 24));			\
    S3ReadRegister ((SDAC[scrnNum].register & 0xf0ffffff), data);	\
}


/*
 *  ---------------------------------------------------------------------------
 *      macro:       SDAC_set_color
 *      description:
 *              Loads the color Look Up Table (LUT).
 *  ---------------------------------------------------------------------------
 */
#define SDAC_set_color(red,green,blue)					\
{									\
    S3WriteRegister (SDAC[scrnNum].lut_data, red);			\
    S3WriteRegister (SDAC[scrnNum].lut_data, green);			\
    S3WriteRegister (SDAC[scrnNum].lut_data, blue);			\
}


/*
 *  ---------------------------------------------------------------------------
 *      macro:       SDAC_turn_cursor
 *      description:
 *              Enables the hardware cursor.
 *  ---------------------------------------------------------------------------
 */
#define OFF  0x0
#define ON   0x1
#define SDAC_turn_cursor(cursor_state)					\
{									\
    if (cursor_state == ON)						\
    {									\
	S3WriteDP (S3CursorMode, ON);					\
    }									\
    else								\
    {									\
	S3WriteDP (S3CursorMode, OFF);					\
    }									\
}
