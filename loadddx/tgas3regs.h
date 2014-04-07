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
 *   FUNCTIONS: S3ReadDP
 *		S3ReadData
 *		S3ReadRegister
 *		S3ReadRegisterShort
 *		S3WriteDP
 *		S3WriteData
 *		S3WriteIndex
 *		S3WriteRegister
 *		S3WriteRegisterSwappedLong
 *		S3WriteRegisterSwappedShort
 *
 */
/*
 * ---------------------------------------------------------------------------
 * File: tgas3regs.h
 * 	- Contains the macros for accessing the S3's Vision928 a.k.a., 
 *	"homestake" graphics card's registers.
 * ---------------------------------------------------------------------------
 */

/*                    PCI Bus is big-endian                                  */
/*---------------------------------------------------------------------------*/
/*      define byte reversed inline code                                     */
/*---------------------------------------------------------------------------*/
void st_2r();                           /* prototype for 2-byte store        */
void st_4r();                           /* prototype for 4-byte store        */
#pragma mc_func st_2r { "7C60272C" }    /* sthbrx (vol_short_int)(gr4,0)=gr3 */
#pragma mc_func st_4r { "7C60252C" }    /* stbrx (vol_int)(gr4,0)=gr3        */
#pragma reg_killed_by st_2r             /* (none killed)                     */
#pragma reg_killed_by st_4r             /* (none killed)                     */
#pragma isolated_call (st_2r)
#pragma isolated_call (st_4r)

/*
 * ---------------------------------------------------------------------------
 *	macro: S3WriteRegister/S3ReadRegister
 *	description: This is the lowest level, most generic routine for 
 *		     accessing S3 registers.
 * ---------------------------------------------------------------------------
 */
#ifdef SHADOW
#define S3WriteRegister(__register, __data)				\
{									\
    if (s3extregs->__register != __data)				\
    {									\
	*(volatile unsigned char *)(ioBaseAddress[scrnNum] | (__register)) = (__data);\
	s3extregs->__register = __data;					\
    }									\
}
#else
#define S3WriteRegister(__register, __data)				\
{									\
    *(volatile unsigned char *)(ioBaseAddress[scrnNum] | (__register)) = (__data);\
}
#endif

#define S3ReadRegister(__register, __data)				\
{									\
    __data = *(volatile unsigned char *)(ioBaseAddress[scrnNum] | (__register));	\
}
#define S3ReadRegisterShort(__register, __data)				\
{									\
    __data = *(volatile unsigned short *)(ioBaseAddress[scrnNum] | (__register));\
}

#define S3ReadRegisterLong(__register, __data)				\
{									\
    __data = *(volatile unsigned int *)(ioBaseAddress[scrnNum] | (__register));\
}
/*
 * ---------------------------------------------------------------------------
 *	macro: S3WriteRegisterShort/S3WriteRegisterLong
 *	description: This is the lowest level, most generic routine for
 *		     writing swapped data to the S3 registers.
 * ---------------------------------------------------------------------------
 */
#define S3WriteRegisterShort(__register, __data)                        \
{                                                                       \
    *(volatile unsigned short *)(ioBaseAddress[scrnNum] | (__register)) = (__data);\
}
#define S3WriteRegisterLong(__register, __data)                 \
{                                                                       \
    *(volatile unsigned long *)(ioBaseAddress[scrnNum] | (__register)) = (__data);\
}


/*
 * ---------------------------------------------------------------------------
 *	macro: S3WriteRegisterSwappedShort/S3WriteRegisterSwappedLong
 *	description: This is the lowest level, most generic routine for 
 *		     writing swapped data to the S3 registers.
 * ---------------------------------------------------------------------------
 */
#define S3WriteRegisterSwappedShort(__register, __data)			\
{									\
    st_2r (__data, (ioBaseAddress[scrnNum] | (__register)));			\
}
#define S3WriteRegisterSwappedLong(__register, __data)			\
{									\
    st_4r (__data, (ioBaseAddress[scrnNum] | (__register)));			\
}

/*
 * ---------------------------------------------------------------------------
 *	Wait for the vertical retrace.
 * ---------------------------------------------------------------------------
 */
#define S3WaitVerticalRefresh()  					\
{									\
   short  usData, usStart;						\
									\
   S3ReadRegisterShort( S3CRTInputStatus1Register, usData);		\
   usStart = usData & 0x08;						\
   while ( (usData & 0x08) == usStart) 					\
      S3ReadRegisterShort( S3CRTInputStatus1Register, usData);		\
}
/*
 * ---------------------------------------------------------------------------
 *	Controller Registers.
 * ---------------------------------------------------------------------------
 */
#define S3CRTInputStatus1Register  0x3DA
#define S3CRTControlIndex	0x3D4
#define S3CRTControlData	0x3D5


/*
 * ---------------------------------------------------------------------------
 *	Sequencer Index Register.
 * ---------------------------------------------------------------------------
 */
#define S3SequencerIndex	0x3C4
#define S3SequencerData		0x3C5
#define S3UnlockSequencer	0x08
#define S3ExtendedSequencerSRD	0x0D
/*
 * ---------------------------------------------------------------------------
 * TRIO (tga) extensions to Sequencer registers , IceCube only had SRD
 * ---------------------------------------------------------------------------
 */
#define S3ExtendedSequencerSR9	0x09
#define S3ExtendedSequencerSRA	0x0A
#define S3ExtendedSequencerSRB	0x0B
#define S3ExtendedSequencerSR10	0x10
#define S3ExtendedSequencerSR11	0x11
#define S3ExtendedSequencerSR12	0x12
#define S3ExtendedSequencerSR13	0x13
#define S3ExtendedSequencerSR14	0x14
#define S3ExtendedSequencerSR15	0x15
#define S3ExtendedSequencerSR16	0x16
#define S3ExtendedSequencerSR17	0x17
#define S3ExtendedSequencerSR18	0x18

#define S3MiscOutputWrite	0x3C2
#define S3MiscOutputRead	0x3CC

#define S3VideoDacWriteIndex    0x3c8
#define S3VideoDacWriteData     0x3c9

#define S3_LOCK_SEQUENCER	0x00
#define S3_UNLOCK_SEQUENCER	0x06
#define S3_HVSYNC_NORMAL	0x00
#define S3_HVSYNC_LOW		0x50



/*
 * ---------------------------------------------------------------------------
 * 	macro: S3WriteIndex
 *	description: The CRT Controller Index Register is loaded with a binary 
 *		     value that indexes the CRT Controller register where the 
 *		     data is to be accessed.
 * ---------------------------------------------------------------------------
 */
#define S3WriteIndex(__index)						\
{									\
    S3WriteRegister (S3CRTControlIndex, __index);			\
    EIEIO;								\
}

/*
 * ---------------------------------------------------------------------------
 *	macro: S3WriteData/S3ReadData
 *	description: This register is the data port for the CRT controller 
 *		     register indexed by the CRT Controller Address register.
 * ---------------------------------------------------------------------------
 */
#define S3WriteData(__data)						\
{									\
    S3WriteRegister (S3CRTControlData, __data);				\
    EIEIO;								\
}

#define S3ReadData(__data)						\
{									\
    S3ReadRegister (S3CRTControlData, __data);				\
}


/*
 * ---------------------------------------------------------------------------
 *	macro: S3WriteDP/S3ReadDP
 *	description: This macro should be used when writing to the data port.
 * ---------------------------------------------------------------------------
 */
#define S3WriteDP(__index, __data)					\
{									\
    S3WriteIndex (__index);						\
    S3WriteData  (__data);						\
}
#define S3ReadDP(__index, __data)					\
{									\
    S3WriteIndex (__index);						\
    S3ReadData  (__data);						\
}



/*
 * ---------------------------------------------------------------------------
 *	Index offsets, this list should only contain indexes actually used. 
 * ---------------------------------------------------------------------------
 */
#define S3CursorFGColor	0x0E	/* When in 8bpp mode */
#define	S3CursorBGColor	0x0F	/* When in 8bpp mode */
#define S3RegisterLock2	0x39


/*
 * ---------------------------------------------------------------------------
 *	System control registers
 * ---------------------------------------------------------------------------
 */
#define S3SystemConfiguration	0x40
#define S3CursorMode		0x45
#define S3CursorOriginXHigh	0x46	/* High order 3 bits */
#define S3CursorOriginXLow	0x47	/* Low order 8 bits */
#define S3CursorOriginYHigh	0x48
#define S3CursorOriginYLow	0x49
#define S3CursorFGColorStack	0x4A
#define S3CursorBGColorStack	0x4B
#define S3CursorAddrStartHigh	0x4C	/* High order 4 bits */
#define S3CursorAddrStartLow	0x4D	/* Low order 8 bits */
#define S3CursorHotSpotX	0x4E
#define S3CursorHotSpotY	0x4F


/*
 * ---------------------------------------------------------------------------
 * 	System Extension Registers.
 * ---------------------------------------------------------------------------
 */
#define S3ExtSystemControl1	0x50
#define S3ExtSystemControl2	0x51
#define S3ExtBIOS1		0x52
#define S3ExtMemoryControl1	0x53
#define S3ExtMemoryControl2	0x54
#define S3ExtDACControl		0x55
#define S3ExternalSync1		0x56
#define S3ExternalSync2		0x57
#define S3LinearAddrWindowControl	0x58
#define S3LinearAddrWindowPositionHigh	0x59
#define S3LinearAddrWindowPositionLow	0x5A
#define S3ExtBIOS2		0x5B
#define S3GeneralOutPort	0x5C
#define S3ExtendedHorizontalOverflow	0x5D
#define S3ExtendedVerticalOverflow	0x5E
#define S3BusGrantTerminationPosition	0x5F





/*
 * ---------------------------------------------------------------------------
 *	Enhanced Command Register Descriptions
 * ---------------------------------------------------------------------------
 */
#define S3SubsystemStatus		0x42E8
#define S3SubsystemControl		0x42E8
#define S3AdvancedFunctionControl	0x4AE8
#define S3CurrentY			0x82E8
#define S3CurrentX			0x86E8
#define S3DestinationY			0x8AE8
#define S3AxialStepConst		0x8AE8
#define S3DestinationX			0x8EE8
#define S3DiagonalStepConst		0x8EE8
#define S3ErrorTerm			0x92E8
#define S3MajorAxisPixelCount		0x96E8
#define S3ProcessorStatus		0x9AE8
#define S3DrawingCommand		0x9AE8
#define S3ShortStrokeVectorTransfer	0x9EE8
#define S3BackgroundColor		0xA2E8
#define S3ForegroundColor		0xA6E8
#define S3WriteMask			0xAAE8
#define S3ReadMask			0xAEE8
#define S3ColorCompare			0xB2E8
#define S3BackgroundMix			0xB6E8
#define S3ForegroundMix			0xBAE8
#define S3MultiFuncControl		0xBEE8	/* Upper 4 bits used as index */
#define S3PixelDataTransfer		0xE2E8
#define S3PixelDataTransferExt		0xE2EA

/* 
 * ---------------------------------------------------------------------------
 * TRIO-64++ only 
 * ---------------------------------------------------------------------------
 */
#define S3CurrentY_2			0x82ea
#define S3CurrentX_2			0x86ea
#define S3DestinationY_2		0x8aea
#define S3DestinationX_2		0x8eea
#define S3ErrorTerm_2			0x92ea
#define S3DrawingCommand_2		0x9aea
/*
 * ---------------------------------------------------------------------------
 *	Enhanced Command Register Commands.
 * ---------------------------------------------------------------------------
 */
#define S3_CHAR_BLT_CMD_TYPE_LONG	0x55b3
#define S3_CHAR_BLT_CMD_TYPE_SHORT	0x53b3
#define S3_CHAR_BLT_CMD_TYPE_CHAR	0x51b3
#define S3_SOLID_FILLRECT_CMD		0x40B3
#define S3_BLT_CMD			0xC013
#define S3_SHORT_STROKE_CMD		0x121F
#define S3_TGA_POLYLINE_CMD		0x2811
#define S3_TGA_POLYGON_CMD		0x6011
#define S3_TGA_TRAPEZOID_CMD		0x8011


/*
 * ---------------------------------------------------------------------------
 *	Addresses for Packed MMIO  (Axxxx)
 * ---------------------------------------------------------------------------
 */
#define MMIO_S3AltCurXY			0xA8100
#define MMIO_S3AltCurXY2		0xA8104
#define MMIO_S3AltStep			0xA8108
#define MMIO_S3AltStep2			0xA810C
#define MMIO_S3AltErr			0xA8110
#define MMIO_S3AltCmd			0xA8118
#define MMIO_S3AltMix			0xA8134
#define MMIO_S3AltPcnt			0xA8148
#define MMIO_S3AltPat			0xA8168

/*
 * ---------------------------------------------------------------------------
 *  CRT regs to read chip info 
 * ---------------------------------------------------------------------------
 */
#define S3ChipIdRev		0x30
/* if 0x30, returns 0xEH, Use 0x2f, 0x2d  and 0x2e for chip info */
#define S3Revision		0x2f	
#define S3DeviceIdHigh		0x2d
#define S3DeviceIdLow		0x2e
