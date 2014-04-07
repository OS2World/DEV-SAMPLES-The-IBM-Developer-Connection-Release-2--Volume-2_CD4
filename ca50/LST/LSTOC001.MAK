# Compiler Switches:
#
# /c    - Supresses Linking

all:LSTOC001.exe

LSTOC001.obj: LSTOC001.c LSTOC001.mak
        cl /AL /Zi /Od /c LSTOC001.c

LSTOC001.exe: LSTOC001.obj LSTOC001.mak
        link  /PM:VIO /CO /STACK:20000 LSTOC001.obj,LSTOC001.exe,,llibce.lib+cclos2.lib+os2.lib;
