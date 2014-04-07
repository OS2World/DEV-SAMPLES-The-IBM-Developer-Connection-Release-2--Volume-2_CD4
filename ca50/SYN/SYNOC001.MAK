# Compiler Switches:
#
# /c    - Supresses Linking

all:synoc001.exe

synoc001.obj: synoc001.c synoc001.mak
        cl /AL /Zi /Od /c synoc001.c

synoc001.exe: synoc001.obj synoc001.mak
        link  /PM:VIO /CO /STACK:20000 synoc001.obj,synoc001.exe,,llibce.lib+cclos2.lib+os2.lib;
