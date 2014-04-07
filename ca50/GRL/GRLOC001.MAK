# Compiler Switches:
#
# /c    - Supresses Linking

all:GRLOC001.exe

GRLOC001.obj: GRLOC001.c GRLOC001.mak
        cl /AL /Zi /Od /c GRLOC001.c

GRLOC001.exe: GRLOC001.obj GRLOC001.mak
        link  /PM:VIO /CO /STACK:20000 GRLOC001.obj,GRLOC001.exe,,llibce.lib+cclos2.lib+os2.lib;
