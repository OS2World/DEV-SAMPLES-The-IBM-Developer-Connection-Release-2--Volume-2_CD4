# Microsoft Developer Studio Generated NMAKE File, Format Version 4.20
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Application" 0x0101

!IF "$(CFG)" == ""
CFG=cclosamp - Win32 Debug
!MESSAGE No configuration specified.  Defaulting to cclosamp - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "cclosamp - Win32 Release" && "$(CFG)" !=\
 "cclosamp - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE on this makefile
!MESSAGE by defining the macro CFG on the command line.  For example:
!MESSAGE 
!MESSAGE NMAKE /f "cclosamp.mak" CFG="cclosamp - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "cclosamp - Win32 Release" (based on "Win32 (x86) Application")
!MESSAGE "cclosamp - Win32 Debug" (based on "Win32 (x86) Application")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 
################################################################################
# Begin Project
# PROP Target_Last_Scanned "cclosamp - Win32 Debug"
CPP=cl.exe
MTL=mktyplib.exe
RSC=rc.exe

!IF  "$(CFG)" == "cclosamp - Win32 Release"

# PROP BASE Use_MFC 6
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 6
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release"
# PROP Target_Dir ""
OUTDIR=.\Release
INTDIR=.\Release

ALL : "$(OUTDIR)\cclosamp.exe" "$(OUTDIR)\cclosamp.tlb"

CLEAN : 
	-@erase "$(INTDIR)\CcloDlg.obj"
	-@erase "$(INTDIR)\cclosamp.obj"
	-@erase "$(INTDIR)\cclosamp.pch"
	-@erase "$(INTDIR)\cclosamp.res"
	-@erase "$(INTDIR)\cclosamp.tlb"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(OUTDIR)\cclosamp.exe"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

# ADD BASE CPP /nologo /MD /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /Yu"stdafx.h" /c
# ADD CPP /nologo /MDd /W3 /GX /O2 /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /D "CICS_W32" /Yu"stdafx.h" /c
CPP_PROJ=/nologo /MDd /W3 /GX /O2 /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D\
 "_AFXDLL" /D "_MBCS" /D "CICS_W32" /Fp"$(INTDIR)/cclosamp.pch" /Yu"stdafx.h"\
 /Fo"$(INTDIR)/" /c 
CPP_OBJS=.\Release/
CPP_SBRS=.\.
# ADD BASE MTL /nologo /D "NDEBUG" /win32
# ADD MTL /nologo /D "NDEBUG" /win32
MTL_PROJ=/nologo /D "NDEBUG" /win32 
# ADD BASE RSC /l 0x809 /d "NDEBUG" /d "_AFXDLL"
# ADD RSC /l 0x809 /d "NDEBUG" /d "_AFXDLL"
RSC_PROJ=/l 0x809 /fo"$(INTDIR)/cclosamp.res" /d "NDEBUG" /d "_AFXDLL" 
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
BSC32_FLAGS=/nologo /o"$(OUTDIR)/cclosamp.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
# ADD BASE LINK32 /nologo /subsystem:windows /machine:I386
# ADD LINK32 cclcpw32.lib /nologo /subsystem:windows /machine:I386
# SUBTRACT LINK32 /nodefaultlib
LINK32_FLAGS=cclcpw32.lib /nologo /subsystem:windows /incremental:no\
 /pdb:"$(OUTDIR)/cclosamp.pdb" /machine:I386 /out:"$(OUTDIR)/cclosamp.exe" 
LINK32_OBJS= \
	"$(INTDIR)\CcloDlg.obj" \
	"$(INTDIR)\cclosamp.obj" \
	"$(INTDIR)\cclosamp.res" \
	"$(INTDIR)\StdAfx.obj"

"$(OUTDIR)\cclosamp.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "cclosamp - Win32 Debug"

# PROP BASE Use_MFC 6
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 6
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Debug"
# PROP Intermediate_Dir "Debug"
# PROP Target_Dir ""
OUTDIR=.\Debug
INTDIR=.\Debug

ALL : "$(OUTDIR)\cclosamp.exe" "$(OUTDIR)\cclosamp.tlb"

CLEAN : 
	-@erase "$(INTDIR)\CcloDlg.obj"
	-@erase "$(INTDIR)\cclosamp.obj"
	-@erase "$(INTDIR)\cclosamp.pch"
	-@erase "$(INTDIR)\cclosamp.res"
	-@erase "$(INTDIR)\cclosamp.tlb"
	-@erase "$(INTDIR)\StdAfx.obj"
	-@erase "$(INTDIR)\vc40.idb"
	-@erase "$(INTDIR)\vc40.pdb"
	-@erase "$(OUTDIR)\cclosamp.exe"
	-@erase "$(OUTDIR)\cclosamp.ilk"
	-@erase "$(OUTDIR)\cclosamp.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

# ADD BASE CPP /nologo /MDd /W3 /Gm /GX /Zi /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /Yu"stdafx.h" /c
# ADD CPP /nologo /MDd /W3 /Gm /GX /Zi /Od /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /D "CICS_W32" /Yu"stdafx.h" /c
CPP_PROJ=/nologo /MDd /W3 /Gm /GX /Zi /Od /D "_DEBUG" /D "WIN32" /D "_WINDOWS"\
 /D "_AFXDLL" /D "_MBCS" /D "CICS_W32" /Fp"$(INTDIR)/cclosamp.pch" /Yu"stdafx.h"\
 /Fo"$(INTDIR)/" /Fd"$(INTDIR)/" /c 
CPP_OBJS=.\Debug/
CPP_SBRS=.\.
# ADD BASE MTL /nologo /D "_DEBUG" /win32
# ADD MTL /nologo /D "_DEBUG" /win32
MTL_PROJ=/nologo /D "_DEBUG" /win32 
# ADD BASE RSC /l 0x809 /d "_DEBUG" /d "_AFXDLL"
# ADD RSC /l 0x809 /d "_DEBUG" /d "_AFXDLL"
RSC_PROJ=/l 0x809 /fo"$(INTDIR)/cclosamp.res" /d "_DEBUG" /d "_AFXDLL" 
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
BSC32_FLAGS=/nologo /o"$(OUTDIR)/cclosamp.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
# ADD BASE LINK32 /nologo /subsystem:windows /debug /machine:I386
# ADD LINK32 cclcpw32.lib /nologo /subsystem:windows /debug /machine:I386
# SUBTRACT LINK32 /nodefaultlib
LINK32_FLAGS=cclcpw32.lib /nologo /subsystem:windows /incremental:yes\
 /pdb:"$(OUTDIR)/cclosamp.pdb" /debug /machine:I386\
 /out:"$(OUTDIR)/cclosamp.exe" 
LINK32_OBJS= \
	"$(INTDIR)\CcloDlg.obj" \
	"$(INTDIR)\cclosamp.obj" \
	"$(INTDIR)\cclosamp.res" \
	"$(INTDIR)\StdAfx.obj"

"$(OUTDIR)\cclosamp.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ENDIF 

.c{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cpp{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cxx{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.c{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

.cpp{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

.cxx{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

################################################################################
# Begin Target

# Name "cclosamp - Win32 Release"
# Name "cclosamp - Win32 Debug"

!IF  "$(CFG)" == "cclosamp - Win32 Release"

!ELSEIF  "$(CFG)" == "cclosamp - Win32 Debug"

!ENDIF 

################################################################################
# Begin Source File

SOURCE=.\ReadMe.txt

!IF  "$(CFG)" == "cclosamp - Win32 Release"

!ELSEIF  "$(CFG)" == "cclosamp - Win32 Debug"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\cclosamp.cpp

!IF  "$(CFG)" == "cclosamp - Win32 Release"

DEP_CPP_CCLOS=\
	".\CcloDlg.h"\
	".\CcloSamp.h"\
	".\StdAfx.h"\
	{$(INCLUDE)}"\CCLBUF.HPP"\
	{$(INCLUDE)}"\CCLCON.HPP"\
	{$(INCLUDE)}"\CCLDEFS.HPP"\
	{$(INCLUDE)}"\CCLECI.HPP"\
	{$(INCLUDE)}"\CCLEXC.HPP"\
	{$(INCLUDE)}"\CCLFLO.HPP"\
	{$(INCLUDE)}"\CCLUOW.HPP"\
	{$(INCLUDE)}"\CICSECI.HPP"\
	

"$(INTDIR)\cclosamp.obj" : $(SOURCE) $(DEP_CPP_CCLOS) "$(INTDIR)"\
 "$(INTDIR)\cclosamp.pch"


!ELSEIF  "$(CFG)" == "cclosamp - Win32 Debug"

DEP_CPP_CCLOS=\
	".\CcloDlg.h"\
	".\CcloSamp.h"\
	".\StdAfx.h"\
	{$(INCLUDE)}"\CCLBUF.HPP"\
	{$(INCLUDE)}"\CCLCON.HPP"\
	{$(INCLUDE)}"\CCLDEFS.HPP"\
	{$(INCLUDE)}"\CCLECI.HPP"\
	{$(INCLUDE)}"\CCLEXC.HPP"\
	{$(INCLUDE)}"\CCLFLO.HPP"\
	{$(INCLUDE)}"\CCLUOW.HPP"\
	{$(INCLUDE)}"\CICSECI.HPP"\
	

"$(INTDIR)\cclosamp.obj" : $(SOURCE) $(DEP_CPP_CCLOS) "$(INTDIR)"\
 "$(INTDIR)\cclosamp.pch"


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\cclosamp.odl

!IF  "$(CFG)" == "cclosamp - Win32 Release"


"$(OUTDIR)\cclosamp.tlb" : $(SOURCE) "$(OUTDIR)"
   $(MTL) /nologo /D "NDEBUG" /tlb "$(OUTDIR)/cclosamp.tlb" /win32 $(SOURCE)


!ELSEIF  "$(CFG)" == "cclosamp - Win32 Debug"


"$(OUTDIR)\cclosamp.tlb" : $(SOURCE) "$(OUTDIR)"
   $(MTL) /nologo /D "_DEBUG" /tlb "$(OUTDIR)/cclosamp.tlb" /win32 $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\StdAfx.cpp
DEP_CPP_STDAF=\
	".\StdAfx.h"\
	

!IF  "$(CFG)" == "cclosamp - Win32 Release"

# ADD CPP /Yc"stdafx.h"

BuildCmds= \
	$(CPP) /nologo /MDd /W3 /GX /O2 /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D\
 "_AFXDLL" /D "_MBCS" /D "CICS_W32" /Fp"$(INTDIR)/cclosamp.pch" /Yc"stdafx.h"\
 /Fo"$(INTDIR)/" /c $(SOURCE) \
	

"$(INTDIR)\StdAfx.obj" : $(SOURCE) $(DEP_CPP_STDAF) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\cclosamp.pch" : $(SOURCE) $(DEP_CPP_STDAF) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "cclosamp - Win32 Debug"

# ADD CPP /Yc"stdafx.h"

BuildCmds= \
	$(CPP) /nologo /MDd /W3 /Gm /GX /Zi /Od /D "_DEBUG" /D "WIN32" /D "_WINDOWS"\
 /D "_AFXDLL" /D "_MBCS" /D "CICS_W32" /Fp"$(INTDIR)/cclosamp.pch" /Yc"stdafx.h"\
 /Fo"$(INTDIR)/" /Fd"$(INTDIR)/" /c $(SOURCE) \
	

"$(INTDIR)\StdAfx.obj" : $(SOURCE) $(DEP_CPP_STDAF) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\cclosamp.pch" : $(SOURCE) $(DEP_CPP_STDAF) "$(INTDIR)"
   $(BuildCmds)

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\cclosamp.rc
DEP_RSC_CCLOSA=\
	".\res\cclosamp.ico"\
	".\res\cclosamp.rc2"\
	

!IF  "$(CFG)" == "cclosamp - Win32 Release"


"$(INTDIR)\cclosamp.res" : $(SOURCE) $(DEP_RSC_CCLOSA) "$(INTDIR)"
   $(RSC) /l 0x809 /fo"$(INTDIR)/cclosamp.res" /i "Release" /d "NDEBUG" /d\
 "_AFXDLL" $(SOURCE)


!ELSEIF  "$(CFG)" == "cclosamp - Win32 Debug"


"$(INTDIR)\cclosamp.res" : $(SOURCE) $(DEP_RSC_CCLOSA) "$(INTDIR)"
   $(RSC) /l 0x809 /fo"$(INTDIR)/cclosamp.res" /i "Debug" /d "_DEBUG" /d\
 "_AFXDLL" $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\CcloDlg.cpp
DEP_CPP_CCLOD=\
	".\CcloDlg.h"\
	".\CcloSamp.h"\
	".\StdAfx.h"\
	{$(INCLUDE)}"\CCLBUF.HPP"\
	{$(INCLUDE)}"\CCLCON.HPP"\
	{$(INCLUDE)}"\CCLDEFS.HPP"\
	{$(INCLUDE)}"\CCLECI.HPP"\
	{$(INCLUDE)}"\CCLEXC.HPP"\
	{$(INCLUDE)}"\CCLFLO.HPP"\
	{$(INCLUDE)}"\CCLUOW.HPP"\
	{$(INCLUDE)}"\CICSECI.HPP"\
	

"$(INTDIR)\CcloDlg.obj" : $(SOURCE) $(DEP_CPP_CCLOD) "$(INTDIR)"\
 "$(INTDIR)\cclosamp.pch"


# End Source File
# End Target
# End Project
################################################################################
