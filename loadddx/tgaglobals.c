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
#include "types.h"
#include "aixFindisp.h"
#include "tgasdac.h"
#include "cursorstr.h"

unsigned int	cursor_state[MAXSCREENS] = {0,0,0,0,0,0};	
	/* What type of cursor on active. */
DisplayRecPtr	SavedDisplayPtr;

volatile u_int 	*vramBaseAddress[MAXSCREENS];
uint		ioBaseAddress[MAXSCREENS];
int		cursorOffsetX[MAXSCREENS];
int		cursorOffsetY[MAXSCREENS];
SDAC_regs	SDAC[MAXSCREENS];
CursorPtr	pCurrentCursor = NULL;

/*
 * Globally accessable screen width and height mostly used to reset the
 * scissor registers.
 */
uint tgaScreenWidth[MAXSCREENS];
uint tgaScreenHeight[MAXSCREENS];


/*
 * Table that maps X's logical ops to S3's.
 *	- Ops will be de-referenced as follows:
 *	  register = op_table[ pGC->alu ];
 */
char ops_table[16] = {	0x1, 0xC, 0xD, 0x7, 0xE, 0x3, 0x5, 0xB,
			0xF, 0x6, 0x0, 0xA, 0x4, 0x9, 0x8, 0x2 };

