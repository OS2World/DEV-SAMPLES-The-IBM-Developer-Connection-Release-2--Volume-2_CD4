# Compiler Switches:
#
# /c    - Supresses Linking

all:ASYOC001.exe

ASYOC001.obj: ASYOC001.c ASYOC001.mak
        cl /AL /Zi /Od /c ASYOC001.c

ASYOC001.exe: ASYOC001.obj ASYOC001.mak
        link  /PM:VIO /CO /STACK:20000 ASYOC001.obj,ASYOC001.exe,,llibce.lib+cclos2.lib+os2.lib;
