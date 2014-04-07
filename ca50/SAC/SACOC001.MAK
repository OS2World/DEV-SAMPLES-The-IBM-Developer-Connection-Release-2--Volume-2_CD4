# Compiler Switches:
#
# /c    - Supresses Linking

all:SACOC001.exe

SACOC001.obj: SACOC001.c SACOC001.mak
        cl /AL /Zi /Od /c SACOC001.c

SACOC001.exe: SACOC001.obj SACOC001.mak
        link  /PM:VIO /CO /STACK:20000 SACOC001.obj,SACOC001.exe,,llibce.lib+cclos2.lib+os2.lib;
