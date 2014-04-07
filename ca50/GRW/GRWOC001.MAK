# Compiler Switches:
#
# /c    - Supresses Linking

all:GRWOC001.exe

GRWOC001.obj: GRWOC001.c GRWOC001.mak
        cl /AL /Zi /Od /c GRWOC001.c

GRWOC001.exe: GRWOC001.obj GRWOC001.mak
        link  /PM:VIO /CO /STACK:20000 GRWOC001.obj,GRWOC001.exe,,llibce.lib+cclos2.lib+os2.lib;
