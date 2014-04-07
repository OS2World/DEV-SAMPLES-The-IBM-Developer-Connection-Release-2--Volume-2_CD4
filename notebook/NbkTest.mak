# nbktest.mak
# Created by IBM WorkFrame/2 MakeMake at 12:15:58 on 4 Oct 1995
#
# The actions included in this make file are:
#  Compile::Resource Preprocess
#  Compile::Resource Compiler
#  Compile::C++ Compiler
#  Link::Linker
#  Bind::Resource Bind

.SUFFIXES: .cpp .obj .rc .rcx .res 

.all: \
    e:\avalon\client\nbktest\nbktest.exe

.rcx.rc:
    @echo " Compile::Resource Preprocess "
    icc.exe -c /Pc+ /Pe+ /Pd /I. %s > %|dpfF.rc

.rc.res:
    @echo " Compile::Resource Compiler "
    rc.exe -r %s %|dpfF.RES

.cpp.obj:
    @echo " Compile::C++ Compiler "
    icc.exe /DIC_TRACE_DEVELOP /Ti /Gm /Gd /Fo"%|dpfF.obj" /C %s

e:\avalon\client\nbktest\nbktest.exe: \
    e:\avalon\client\nbktest\NbkTest.obj \
    e:\avalon\client\nbktest\vbmain.obj \
    e:\avalon\client\nbktest\NbkPage.obj \
    e:\avalon\client\nbktest\NbkTest.res
    @echo " Link::Linker "
    @echo " Bind::Resource Bind "
    icc.exe @<<
    /Tdp 
     /Gm /Gd 
     /B" /de /pmtype:pm"
     /Fee:\avalon\client\nbktest\nbktest.exe 
     e:\avalon\client\nbktest\NbkTest.obj
     e:\avalon\client\nbktest\vbmain.obj
     e:\avalon\client\nbktest\NbkPage.obj
<<
    rc.exe e:\avalon\client\nbktest\NbkTest.res e:\avalon\client\nbktest\nbktest.exe

e:\avalon\client\nbktest\NbkTest.rc: \
    e:\avalon\client\nbktest\NbkTest.rcx

e:\avalon\client\nbktest\NbkTest.res: \
    e:\avalon\client\nbktest\NbkTest.rc

e:\avalon\client\nbktest\NbkTest.obj: \
    e:\avalon\client\nbktest\NbkTest.cpp \
    {e:\avalon\client\nbktest;$(INCLUDE);}NbkTest.hpp \
    {e:\avalon\client\nbktest;$(INCLUDE);}NbkTest.h \
    {e:\avalon\client\nbktest;$(INCLUDE);}NbkPage.hpp \
    {e:\avalon\client\nbktest;$(INCLUDE);}NbkPage.h

e:\avalon\client\nbktest\NbkPage.obj: \
    e:\avalon\client\nbktest\NbkPage.cpp \
    {e:\avalon\client\nbktest;$(INCLUDE);}NbkPage.hpp \
    {e:\avalon\client\nbktest;$(INCLUDE);}NbkPage.h

e:\avalon\client\nbktest\vbmain.obj: \
    e:\avalon\client\nbktest\vbmain.cpp \
    {e:\avalon\client\nbktest;$(INCLUDE);}NbkTest.hpp \
    {e:\avalon\client\nbktest;$(INCLUDE);}NbkTest.h
