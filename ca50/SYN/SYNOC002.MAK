# Compiler Switches:
#
# /c    - Supresses Linking

all:synoc002.exe

synoc002.obj: synoc002.c synoc002.mak
        cl /AL /Zi /Od /c synoc002.c

synoc002.exe: synoc002.obj synoc002.mak
        link  /PM:VIO /CO /STACK:20000 synoc002.obj,synoc002.exe,,llibce.lib+cclos2.lib+os2.lib;
