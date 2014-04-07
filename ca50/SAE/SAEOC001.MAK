# Compiler Switches:
#
# /c    - Supresses Linking

all:SAEOC001.exe

SAEOC001.obj: SAEOC001.c SAEOC001.mak
        cl /AL /Zi /Od /c SAEOC001.c

SAEOC001.exe: SAEOC001.obj SAEOC001.mak
        link  /PM:VIO /CO /STACK:20000 SAEOC001.obj,SAEOC001.exe,,llibce.lib+cclos2.lib+os2.lib;
