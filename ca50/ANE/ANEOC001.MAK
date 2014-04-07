# Compiler Switches:
#
# /c    - Supresses Linking

all:ANEOC001.exe

ANEOC001.obj: ANEOC001.c ANEOC001.mak
        cl /AL /Zi /Od /c ANEOC001.c

ANEOC001.exe: ANEOC001.obj ANEOC001.mak
        link  /PM:VIO /CO /STACK:20000 ANEOC001.obj,ANEOC001.exe,,llibce.lib+cclos2.lib+os2.lib;
