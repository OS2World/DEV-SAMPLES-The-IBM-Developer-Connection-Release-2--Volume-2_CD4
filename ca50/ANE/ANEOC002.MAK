# Compiler Switches:
#
# /c    - Supresses Linking

all:ANEOC002.exe

ANEOC002.obj: ANEOC002.c ANEOC002.mak
        cl /AL /Zi /Od /c ANEOC002.c

ANEOC002.exe: ANEOC002.obj ANEOC002.mak
        link  /PM:VIO /CO /STACK:20000 ANEOC002.obj,ANEOC002.exe,,llibce.lib+cclos2.lib+os2.lib;
