# Compiler Switches:
#
# /c    - Supresses Linking

all:synoc003.exe

synoc003.obj: synoc003.c synoc003.mak
        cl /AL /Zi /Od /c synoc003.c

synoc003.exe: synoc003.obj synoc003.mak
        link  /PM:VIO /CO /STACK:20000 synoc003.obj,synoc003.exe,,llibce.lib+cclos2.lib+os2.lib;
