# Compiler Switches:
#
# /c    - Supresses Linking
# /Ti   - Debug              

all:aneoc003.exe

aneoc003.obj: aneoc003.c aneoc003.mak
        icc /c aneoc003.c

aneoc003.exe: aneoc003.obj aneoc003.mak
        link386  /PM:VIO /CO /DEBUG /STACK:20000 aneoc003.obj,aneoc003.exe,,dde4sbs.lib+cclos232.lib+os2386.lib;
