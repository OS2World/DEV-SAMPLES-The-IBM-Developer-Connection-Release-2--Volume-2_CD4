# Compiler Switches:
#
# /c    - Supresses Linking

all:SAEOC002.exe

SAEOC002.obj: SAEOC002.c SAEOC002.mak
        cl /AL /Zi /Od /c SAEOC002.c

SAEOC002.exe: SAEOC002.obj SAEOC002.mak
        link  /PM:VIO /CO /STACK:20000 SAEOC002.obj,SAEOC002.exe,,llibce.lib+cclos2.lib+os2.lib;
