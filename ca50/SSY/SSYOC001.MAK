# Compiler Switches:
#
# /c    - Supresses Linking

all:ssyoc001.exe

ssyoc001.obj: ssyoc001.c ssyoc001.mak
        cl /AL /Zi /Od /c ssyoc001.c

ssyoc001.exe: ssyoc001.obj ssyoc001.mak
        link  /PM:VIO /CO /STACK:20000 ssyoc001.obj,ssyoc001.exe,,llibce.lib+cclos2.lib+os2.lib;
