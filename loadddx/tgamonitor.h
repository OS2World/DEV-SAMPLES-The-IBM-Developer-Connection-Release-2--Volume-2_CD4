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
#define CRTC_LIST_TOTAL 	25
#define EXTENDED_LIST_TOTAL	11  

/*
 * ---------------------------------------------------------------------------
 * 	structure:	crt_control
 * 	description:
 *		Holds the values used to set-up the modes used by different
 *		monitors.
 * ---------------------------------------------------------------------------
 */
typedef struct tga_crt_control
{
    char		mode_id[MODE_ID_LEN+1];
    int			x_resolution;
    int			y_resolution;
    uchar		AdvancedFunctionControl;
    uchar		pll_mclk_low;	/* PLL R/N value for MCLK */
    uchar		pll_mclk_high;  /* PLL M value for MCLK   */
    uchar		pll_dclk_low;	/* PLL R/N value for DCLK */
    uchar		pll_dclk_high;  /* PLL M value for DCLK   */
    Bool		double_dot_clock;
    char		double_clock_setting;
			/* value for 0x00 to 0x18 */
    volatile uchar	crtc_settings[CRTC_LIST_TOTAL];
			/* index, value */
    volatile uchar	extended_settings[2*EXTENDED_LIST_TOTAL];
} tga_crt_control_rec;
typedef struct tga_crt_control *tga_crt_control_ptr;

/* The ref frequency for PLL is 14.318 MHz */
/* We assume an MCLK freq of 57 MHz (MAX) */

static struct tga_crt_control ts_crt_control[] = 
{
    {
	"640x480@60Hz",		/* Dot Clock 25.255 MHz */
	640, 480,		/* XY resolution */
	0x15,			/* Advanced Function Control Register */
	0x45,			/* PLL R/N value for MCLK (57MHz)     */
	0x6d,			/* PLL M value for MCLK (57MHz)       */
	0x67,			/* PLL R/N value for DCLK (25.255 MHz)*/
	0x7d,			/* PLL M value for DCLK (25.255 MHz   */
	FALSE,			/* clock doubling enabled */
	0x00,			/* Clock doubling bit for enhanced command */
	/* crtc */
        0x5f, 0x4f, 0x50, 0x82, 0x54, 0x80, 0x0b, 0x3e, 0x00, 0x40,
        0x00, 0x00, 0x00, 0x00, 0x00, 0xff, 0xea, 0x8c, 0xdf, 0x50,
        0x00, 0xe7, 0x04, 0xab, 0xff,
        /* extended - index, value */
        0x3b, 0x5e,
        0x50, 0x70,             /* Screen Width and Pixel Length */
        0x5d, 0x00,
        0x5e, 0x00,
        0x54, 0x90,
        0x60, 0x07,
        0x67, 0xd0,		/* Mode 13 24-bit true-color */
        0x34, 0x00, 
	0x66, 0x8d,
        0x13, 0x40,             /* Screen Offset CR13_7-0   0x140 = 320d */
        0x51, 0x10,             /* Screen Offset CR51_54 */
    },

    {
	"640x480@72Hz",		/* Dot Clock 31.56 MHz */
	640, 480,		/* XY resolution */
	0x15,			/* Advanced Function Control Register */
	0x45,			/* PLL R/N value for MCLK (57MHz)     */
	0x6d,			/* PLL M value for MCLK (57MHz)       */
	0x63,			/* PLL R/N value for DCLK (31.56 MHz)*/
	0x56,			/* PLL M value for DCLK (31.56 MHz   */
	FALSE,			/* clock doubling enabled */
	0x00,			/* Clock doubling bit for enhanced command */
	/* crtc */
        0x63, 0x4f, 0x4f, 0x80, 0x53, 0x18, 0x06, 0x3e, 0x00, 0x40,
        0x00, 0x00, 0x00, 0x00, 0x00, 0xff, 0xe8, 0x8b, 0xdf, 0x50,
        0x00, 0xdf, 0x06, 0xab, 0xff,
        /* extended - index, value */
        0x3b, 0x5e,
        0x50, 0x70,             /* Screen Width and Pixel Length */
        0x5d, 0x00,
        0x5e, 0x00,
        0x54, 0xc0,
        0x60, 0x07,
        0x67, 0xd0,		/* Mode 13 24-bit true-color */
        0x34, 0x00, 
	0x66, 0x8d,
        0x13, 0x40,             /* Screen Offset CR13_7-0   0x140 = 320d */
        0x51, 0x10,             /* Screen Offset CR51_54 */
    },

    {
	"640x480@75Hz",		/* Dot Clock 31.61 MHz */
	640, 480,		/* XY resolution */
	0x15,			/* Advanced Function Control Register */
	0x45,			/* PLL R/N value for MCLK (57MHz)     */
	0x6d,			/* PLL M value for MCLK (57MHz)       */
	0x63,			/* PLL R/N value for DCLK (31.61 MHz)*/
	0x56,			/* PLL M value for DCLK (31.61 MHz   */
	FALSE,			/* clock doubling enabled */
	0x00,			/* Clock doubling bit for enhanced command */
	/* crtc */
        0x64, 0x4f, 0x4f, 0x00, 0x52, 0x1a, 0xf2, 0x1f, 0x00, 0x40,
        0x00, 0x00, 0x00, 0x00, 0x00, 0xff, 0xe0, 0x83, 0xdf, 0x50,
        0x60, 0xdf, 0x00, 0xab, 0xff,
        /* extended - index, value */
        0x3b, 0x5e,
        0x50, 0x70,             /* Screen Width and Pixel Length */
        0x5d, 0x00,
        0x5e, 0x00,
        0x54, 0xa0,
        0x60, 0xff,
        0x67, 0xd0,		/* Mode 13 24-bit true-color */
        0x34, 0x00, 
	0x66, 0x8d,
        0x13, 0x40,             /* Screen Offset CR13_7-0   0x140 = 320d */
        0x51, 0x10,             /* Screen Offset CR51_54 */
    },

    {
	"640x480@85Hz",		/* Dot Clock 36.152 MHz */
	640, 480,		/* XY resolution */
	0x15,			/* Advanced Function Control Register */
	0x45,			/* PLL R/N value for MCLK (57MHz)     */
	0x6d,			/* PLL M value for MCLK (57MHz)       */
	0x49,			/* PLL R/N value for DCLK (36.152 MHz)*/
	0x6d,			/* PLL M value for DCLK (36.152 MHz   */
	FALSE,			/* clock doubling enabled */
	0x00,			/* Clock doubling bit for enhanced command */
	/* crtc */
        0x63, 0x4f, 0x50, 0x00, 0x52, 0x1b, 0xfd, 0x1f, 0x00, 0x40,
        0x00, 0x00, 0x00, 0x00, 0x00, 0xff, 0xe1, 0x84, 0xdf, 0x50,
        0x60, 0xe0, 0xfd, 0xab, 0xff,
        /* extended - index, value */
        0x3b, 0x5e,
        0x50, 0x70,		/* Screen Width and Pixel Length */
        0x5d, 0x00,
        0x5e, 0x00,
        0x54, 0xa1,
        0x60, 0xff,
        0x67, 0xd0,		/* Mode 13 24-bit true-color */
        0x34, 0x00, 
	0x66, 0x8d,
	0x13, 0x40,		/* Screen Offset CR13_7-0   0x140 = 320d */
        0x51, 0x10,		/* Screen Offset CR51_54 */
    },
    
    {
	"800x600@56Hz",		/* Dot Clock 36.093 MHz */
	800, 600,		/* XY resolution */
	0x13,			/* Advanced Function Control Register */
	0x45,			/* PLL R/N value for MCLK (57MHz)     */
	0x6d,			/* PLL M value for MCLK (57MHz)       */
	0x41,			/* PLL R/N value for DCLK (37 MHz)*/
	0x1d,			/* PLL M value for DCLK (37 MHz   */
	FALSE,			/* clock doubling enabled */
	0x00,			/* Clock doubling bit for enhanced command */
	/* crtc */
        0x7f, 0x63, 0x63, 0x80, 0x69, 0x19, 0x72, 0xf0, 0x00, 0x60,
        0x00, 0x00, 0x00, 0x00, 0x00, 0xff, 0x58, 0x8c, 0x57, 0x64,
        0x00, 0x57, 0x00, 0xe3, 0xff,
        /* extended - index, value */
        0x3b, 0x78,
        0x50, 0xb2,             /* Screen Width and Pixel Length */
        0x5d, 0x00,
        0x5e, 0x00,
        0x54, 0x58,
        0x60, 0x07,
        0x67, 0xd0,		/* Mode 13 24-bit true-color */
        0x34, 0x00, 
	0x66, 0x89,
	0x13, 0x90,		/* Screen Offset CR13_7-0   0x140 = 320d */
        0x51, 0x10,		/* Screen Offset CR51_54 */
    },

    {
	"800x600@60Hz",		/* 40.025 MHz */
	800, 600,		/* XY resolution */
	0x13,			/* Advanced Function Control Register */
	0x45,			/* PLL R/N value for MCLK (57MHz)     */
	0x6d,			/* PLL M value for MCLK (57MHz)       */
	0x44,			/* PLL R/N value for DCLK (40.025 MHz)*/
	0x41,			/* PLL M value for DCLK (40.025 MHz   */
	FALSE,			/* clock doubling enabled */
	0x00,			/* Clock doubling bit for enhanced command */
	/* crtc */
        0x7f, 0x63, 0x63, 0x80, 0x69, 0x19, 0x72, 0xf0, 0x00, 0x60,
        0x00, 0x00, 0x00, 0x00, 0x00, 0xff, 0x58, 0x8c, 0x57, 0x64,
        0x00, 0x57, 0x00, 0xe3, 0xff,
        /* extended - index, value */
        0x3b, 0x78,
        0x50, 0xb2,             /* Screen Width and Pixel Length */
        0x5D, 0x00,
        0x5e, 0x00,
        0x54, 0x58,
        0x60, 0x07,
        0x67, 0xd0,		/* Mode 13 24-bit true-color */
        0x34, 0x00, 
	0x66, 0x89,
	0x13, 0x90,		/* Screen Offset CR13_7-0   0x140 = 320d */
        0x51, 0x10,		/* Screen Offset CR51_54 */
    },

    {
	"800x600@75Hz",		/* Dot Clock 49.516 MHz */
	800, 600,		/* XY resolution */
	0x13,			/* Advanced Function Control Register */
	0x45,			/* PLL R/N value for MCLK (57MHz)     */
	0x6d,			/* PLL M value for MCLK (57MHz)       */
	0x43,			/* PLL R/N value for DCLK (49.516 MHz)*/
	0x43,			/* PLL M value for DCLK (49.516 MHz   */
	FALSE,			/* clock doubling enabled */
	0x00,			/* Clock doubling bit for enhanced command */
	/* crtc */
        0x7f, 0x63, 0x63, 0x00, 0x66, 0x10, 0x6f, 0xe0, 0x00, 0x60,
        0x00, 0x00, 0x00, 0x00, 0x00, 0xff, 0x58, 0x8b, 0x57, 0x64,
        0x00, 0x57, 0x00, 0xe3, 0xff,
        /* extended - index, value */
        0x3b, 0x78,
        0x50, 0xb2,             /* Screen Width and Pixel Length */
        0x5d, 0x08,
        0x5e, 0x00,
        0x54, 0x48,
        0x60, 0xff,
        0x67, 0xd0,		/* Mode 13 24-bit true-color */
        0x34, 0x00, 
	0x66, 0x89,
	0x13, 0x90,		/* Screen Offset CR13_7-0   0x140 = 320d */
        0x51, 0x10,		/* Screen Offset CR51_54 */
    },

    {
	"800x600@76Hz",		/* Dot Clock 49.51 MHz */
	800, 600,		/* XY resolution */
	0x13,			/* Advanced Function Control Register */
	0x45,			/* PLL R/N value for MCLK (57MHz)     */
	0x6d,			/* PLL M value for MCLK (57MHz)       */
	0x43,			/* PLL R/N value for DCLK (49.51 MHz)*/
	0x43,			/* PLL M value for DCLK (49.51 MHz   */
	FALSE,			/* clock doubling enabled */
	0x00,			/* Clock doubling bit for enhanced command */
	/* crtc */
        0x7f, 0x63, 0x63, 0x80, 0x69, 0x19, 0x72, 0xf0, 0x00, 0x60,
        0x00, 0x00, 0x00, 0x00, 0x00, 0xff, 0x58, 0x8c, 0x57, 0x64,
        0x00, 0x57, 0x00, 0xe3, 0xff,
        /* extended - index, value */
        0x3b, 0x78,
        0x50, 0xb2,             /* Screen Width and Pixel Length */
        0x5d, 0x00,
        0x5e, 0x00,
        0x54, 0x58,
        0x60, 0x07,
        0x67, 0xd0,		/* Mode 13 24-bit true-color */
        0x34, 0x00, 
	0x66, 0x89,
	0x13, 0x90,		/* Screen Offset CR13_7-0   0x140 = 320d */
        0x51, 0x10,		/* Screen Offset CR51_54 */
    },

    {
	"800x600@85Hz",		/* Dot Clock 56.377 MHz */
	800, 600,		/* XY resolution */
	0x13,			/* Advanced Function Control Register */
	0x45,			/* PLL R/N value for MCLK (57MHz)     */
	0x6d,			/* PLL M value for MCLK (57MHz)       */
	0x45,			/* PLL R/N value for DCLK (56.377 MHz)*/
	0x6c,			/* PLL M value for DCLK (56.377 MHz   */
	FALSE,			/* clock doubling enabled */
	0x00,			/* Clock doubling bit for enhanced command */
	/* crtc */
        0x7e, 0x63, 0x64, 0x00, 0x68, 0x10, 0x76, 0xe0, 0x00, 0x60,
        0x00, 0x00, 0x00, 0x00, 0x00, 0xff, 0x59, 0x8c, 0x57, 0x64,
        0x00, 0x57, 0x00, 0xe3, 0xff,
        /* extended - index, value */
        0x3b, 0x78,
        0x50, 0xb2,             /* Screen Width and Pixel Length */
        0x5d, 0x00,
        0x5e, 0x00,
        0x54, 0x48,
        0x60, 0xff,
        0x67, 0xd0,		/* Mode 13 24-bit true-color */
        0x34, 0x00, 
	0x66, 0x89,
	0x13, 0x90,		/* Screen Offset CR13_7-0   0x140 = 320d */
        0x51, 0x10,		/* Screen Offset CR51_54 */
    },

/***********************************************/
/* 1024x768 is not supported in 2M true-color  */

/***********************************************/
/* 1280x1024 is not supported in 2M true-color */


/*
 * This is the end of the structure, so if more display modes are to be
 * added, do so ABOVE THIS LINE, as the scanning algorithm looks for the
 * 0 in the first field to stop.
 */
    {
        "0",
        0, 0,
        0,
        0,0,0,
        0,
        0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0,
        0, 0,
        0, 0,
        0, 0,
        0, 0,
        0, 0,
        0, 0,
        0, 0,
        0, 0
    }
};


