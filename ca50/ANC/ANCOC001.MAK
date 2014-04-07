# Compiler Switches:
#
# /c    - Supresses Linking

all:ANCOC001.exe

ANCOC001.obj: ANCOC001.c ANCOC001.mak
        cl /AL /Zi /Od /c ANCOC001.c

ANCOC001.exe: ANCOC001.obj ANCOC001.mak
        link  /PM:VIO /CO /STACK:20000 ANCOC001.obj,ANCOC001.exe,,llibce.lib+cclos2.lib+os2.lib;
