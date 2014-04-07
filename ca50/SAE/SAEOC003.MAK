# Compiler Switches:
#
# /c    - Supresses Linking
# /Ti   - Debug              

all:saeoc003.exe

saeoc003.obj: saeoc003.c saeoc003.mak
        icc /c /Ti saeoc003.c

saeoc003.exe: saeoc003.obj saeoc003.mak
        link386  /PM:VIO /CO /DEBUG /STACK:20000 saeoc003.obj,saeoc003.exe,,dde4sbs.lib+cclos232.lib+os2386.lib;
