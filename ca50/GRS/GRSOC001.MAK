# Compiler Switches:
#
# /c    - Supresses Linking

all:GRSOC001.exe

GRSOC001.obj: GRSOC001.c GRSOC001.mak
        cl /AL /Zi /Od /c GRSOC001.c

GRSOC001.exe: GRSOC001.obj GRSOC001.mak
        link  /PM:VIO /CO /STACK:20000 GRSOC001.obj,GRSOC001.exe,,llibce.lib+cclos2.lib+os2.lib;
