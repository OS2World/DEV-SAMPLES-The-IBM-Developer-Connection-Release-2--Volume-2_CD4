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
 *   FUNCTIONS: TGAFillBlockSolid
 *		TGAHandleRasterAttrs
 *		TGAProcessorIdle
 *		TGAWaitForFiFo
 *		TGASetLogicalOp
 *		TGASetScissorRegion
 *		TGAResetScissors
 *
 */

extern char ops_table[];


#define ZeroSlots	0xFF
#define OneSlots	0x7F
#define TwoSlots	0x3F
#define ThreeSlots 	0x1F
#define FourSlots	0x0F
#define FiveSlots	0x07
#define SixSlots	0x03
#define SevenSlots	0x01
#define EightSlots	0x00
/*
 * ---------------------------------------------------------------------------
 *	macro: TGAWaitForFiFo
 *	description:
 *		- Wait for the fifo to have at least "slotsNeeded", slots
 *		open.  This loop is counter-intuitive because the greater
 *		the number returned from the processor query the fewer
 *		slots there are availiable.
 * ---------------------------------------------------------------------------
 */
#define TGAWaitForFiFo(slotsNeeded)					\
{									\
    short slotsOpen=ZeroSlots;						\
    EIEIO;								\
    do									\
    {									\
	S3ReadRegister (S3ProcessorStatus, slotsOpen);			\
    }									\
    while (slotsOpen > slotsNeeded);					\
}

/*
 * ---------------------------------------------------------------------------
 *	macro: TGAProcessorIdle()
 *	description:
 *		- Waits for the S3 graphics processor to be idle.
 * ---------------------------------------------------------------------------
 */
#define TGAProcessorIdle()						\
{									\
    short ProcessorStatus = 0;						\
    do									\
    {									\
	S3ReadRegisterShort (S3ProcessorStatus, ProcessorStatus);	\
    }									\
    while ((ProcessorStatus & 0x02));					\
}

/*
 * ---------------------------------------------------------------------------
 *	macro: TGASetLogicalOp()
 *	description:
 *		- Set the logical op, the match table is in tgaglobals.c.
 *		  It maps the 864's logical op identifiers to X's.
 * ---------------------------------------------------------------------------
 */
#define TGASetLogicalOp(pGC)						\
{									\
    TGAWaitForFiFo(OneSlots);						\
    S3WriteRegisterSwappedShort (S3ForegroundMix,			\
                                (short)(0x00000020 |			\
                                (0x000000ff & ops_table[pGC->alu])))	\
}

/*
 * ---------------------------------------------------------------------------
 *	macro: TGAHandleRasterAttrs()
 *	description:
 *		- Set the adapter context to match the GC.
 * ---------------------------------------------------------------------------
 */
#define TGAHandleRasterAttrs(pGC)					\
{									\
    TGASetLogicalOp (pGC);						\
    TGAWaitForFiFo(ThreeSlots);						\
    S3WriteRegisterLong  (S3WriteMask, (u_int)pGC->planemask);	\
    S3WriteRegisterLong  (S3ForegroundColor, (u_int)pGC->fgPixel);\
    S3WriteRegisterSwappedShort (S3MultiFuncControl, (short)(0xA000));	\
}

/*
 * ---------------------------------------------------------------------------
 *	macro: TGAFillBlockSolid()
 *	description:
 *		- Fills a solid rectangular region.
 * ---------------------------------------------------------------------------
 */

#define TGAFillBlockSolid(x, y, w, h)                                   \
{                                                                       \
    TGAWaitForFiFo(SevenSlots);                                         \
    S3WriteRegisterSwappedShort (S3CurrentX, (short) x);                \
    S3WriteRegisterSwappedShort (S3CurrentY, (short) y);                \
    S3WriteRegisterSwappedShort (S3MajorAxisPixelCount, (short)(w-1));  \
    S3WriteRegisterSwappedShort (S3MultiFuncControl, (short)(h-1));     \
    S3WriteRegisterSwappedShort (S3MultiFuncControl, (short)(0xA000));  \
    EIEIO;                                                              \
    S3WriteRegisterSwappedShort (S3DrawingCommand,                      \
                                (short)S3_SOLID_FILLRECT_CMD);          \
    S3WriteRegisterSwappedShort (S3MultiFuncControl, (short)(0xA080));  \
}

/*
 * ---------------------------------------------------------------------------
 *	macro: TGASetScissorRegion()
 *	description:
 *		- Sets the clip regions
 * ---------------------------------------------------------------------------
 */
#define TGASetScissorRegion( _pBox )					\
{									\
    extern uint tgaScreenWidth[], tgaScreenHeight[];			\
									\
    TGAWaitForFiFo (EightSlots);					\
    S3WriteRegisterSwappedShort (S3MultiFuncControl, ((short)0x1000 | 	\
						      _pBox->y1));	\
    S3WriteRegisterSwappedShort (S3MultiFuncControl, ((short)0x2000 | 	\
						      _pBox->x1));	\
    if (_pBox->y2 < tgaScreenHeight[scrnNum])				\
    {									\
	S3WriteRegisterSwappedShort (S3MultiFuncControl, ((short)0x3000 |\
						      _pBox->y2-1));	\
    }									\
    else								\
    {									\
	S3WriteRegisterSwappedShort (S3MultiFuncControl, ((short)0x3000 |\
				      tgaScreenHeight[scrnNum]-1));\
    }									\
    if (_pBox->x2 < tgaScreenWidth[scrnNum])					\
    {									\
	S3WriteRegisterSwappedShort (S3MultiFuncControl, ((short)0x4000 |\
						      _pBox->x2-1));	\
    }									\
    else								\
    {									\
	S3WriteRegisterSwappedShort (S3MultiFuncControl, ((short)0x4000 |\
				      tgaScreenWidth[scrnNum]-1));\
    }									\
}

/*
 * ---------------------------------------------------------------------------
 *	macro: TGAResetScissors()
 *	description:
 *		- Resets the scissor registers to the screen extents.
 * ---------------------------------------------------------------------------
 */
#define TGAResetScissors()						\
{									\
    extern uint tgaScreenWidth[], tgaScreenHeight[];			\
    BoxRec tgaBoxRec = {0, 0, tgaScreenWidth[scrnNum],			\
		tgaScreenHeight[scrnNum]};				\
    BoxPtr _ScreenExtents = &tgaBoxRec;					\
									\
    TGASetScissorRegion (_ScreenExtents);				\
}

/*
 *  ---------------------------------------------------------------------------
 *      macro:       TGATurnCursor
 *      description:
 *              Enables the hardware cursor.
 *  ---------------------------------------------------------------------------
 */
#define TGATurnCursor(cursor_state)					\
{									\
	S3WriteDP (S3CursorMode, cursor_state);				\
}
