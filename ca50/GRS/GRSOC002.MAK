# Compiler Switches:
#
# /c    - Supresses Linking

all:GRSOC002.exe

GRSOC002.obj: GRSOC002.c GRSOC002.mak
        cl /AL /Zi /Od /c GRSOC002.c

GRSOC002.exe: GRSOC002.obj GRSOC002.mak
        link  /PM:VIO /CO /STACK:20000 GRSOC002.obj,GRSOC002.exe,,llibce.lib+cclos2.lib+os2.lib;
