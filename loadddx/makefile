#                    NOTICE TO USERS OF THE SOURCE CODE
# 
#  THE SOURCE CODE EXAMPLES PROVIDED BY IBM ARE ONLY INTENDED TO ASSIST
#  IN THE DEVELOPMENT OF A 24 BIT DDX.
# 
#  INTERNATIONAL BUSINESS MACHINES CORPORATION PROVIDES THE SOURCE CODE
#  EXAMPLES, BOTH INDIVIDUALLY AND AS ONE OR MORE GROUPS, "AS IS" WITHOUT
#  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT
#  LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
#  PARTICULAR PURPOSE.

PROGRAMS	= loadddx

ILIST		= ${PROGRAMS}
IDIR		= /usr/lpp/gai/pci3353c088/

LIBS		= -lm -lc -lgair4 -lodm -lcfg -bnoquiet

INCMI 	= -I$(TOP)/XTOP/server/ddx/mi
INCCFB24= -I$(TOP)/XTOP/server/ddx/cfb24
INCMFB 	= -I$(TOP)/XTOP/server/ddx/mfb


INCFLAGS	= -I. $(INCMI) $(INCCFB24) $(INCMFB) 

CFLAGS		=  $(SERVERDEFINES) -DR4_FONT_STRUCTURES -DAIX_PERF  \
		  -URAMSERVER -ULITTLE_ENDIAN -DUSE_HDWR -qsrcmsg	\
		  -qsrcmsg					\
		  -DUSE_TRIO_HW_FILLS					\
		  -DUSE_TRIO_HW_LINES

IMPORTS		= ${MAKETOP}gos/rms/sysgsc.exp		\
		  ${MAKETOP}gos/2d/XTOP/server/X.exp	\
		  ${MAKETOP}gos/2d/XTOP/server/ddx.exp	\
		  ${MAKETOP}gos/2d/XTOP/server/gai.exp

loadddx_ENTRYPOINT	=tga_ddxInit

OFILES	=		\
	tgaglobals.o	\
	tgacursor.o	\
	tgainit.o	\
	tgamonitor.o	\
	tgamonitor.o	\
	tgavisl.o	\


.include <${MAKETOP}/gos/gos.mk>

.include <${RULES_MK}>
