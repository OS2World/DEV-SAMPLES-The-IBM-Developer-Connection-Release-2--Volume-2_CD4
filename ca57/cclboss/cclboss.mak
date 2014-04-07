# Microsoft Developer Studio Generated NMAKE File, Format Version 4.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Application" 0x0101

!IF "$(CFG)" == ""
CFG=CclBoss - Win32 Debug
!MESSAGE No configuration specified.  Defaulting to CclBoss - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "CclBoss - Win32 Release" && "$(CFG)" !=\
 "CclBoss - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE on this makefile
!MESSAGE by defining the macro CFG on the command line.  For example:
!MESSAGE 
!MESSAGE NMAKE /f "CclBoss.MAK" CFG="CclBoss - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "CclBoss - Win32 Release" (based on "Win32 (x86) Application")
!MESSAGE "CclBoss - Win32 Debug" (based on "Win32 (x86) Application")
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
# PROP Target_Last_Scanned "CclBoss - Win32 Debug"
CPP=cl.exe
RSC=rc.exe
MTL=mktyplib.exe

!IF  "$(CFG)" == "CclBoss - Win32 Release"

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

ALL : "$(OUTDIR)\CclBoss.exe" "$(OUTDIR)\CclBoss.tlb"

CLEAN : 
	-@erase ".\Release\CclBoss.tlb"
	-@erase ".\Release\CclBoss.exe"
	-@erase ".\Release\CclBoss.obj"
	-@erase ".\Release\CclBoss.pch"
	-@erase ".\Release\BOSSamp.obj"
	-@erase ".\Release\ccloeci.obj"
	-@erase ".\Release\StdAfx.obj"
	-@erase ".\Release\CclBoss.res"
	-@erase ".\Release\CclBossD.obj"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

# ADD BASE CPP /nologo /MD /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /Yu"stdafx.h" /c
# ADD CPP /nologo /MD /W3 /GX /O2 /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /D " CICS_W32" /Yu"stdafx.h" /c
CPP_PROJ=/nologo /MD /W3 /GX /O2 /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D\
 "_AFXDLL" /D "_MBCS" /D " CICS_W32" /Fp"$(INTDIR)/CclBoss.pch" /Yu"stdafx.h"\
 /Fo"$(INTDIR)/" /c 
CPP_OBJS=.\Release/
CPP_SBRS=
# ADD BASE MTL /nologo /D "NDEBUG" /win32
# ADD MTL /nologo /D "NDEBUG" /win32
MTL_PROJ=/nologo /D "NDEBUG" /win32 
# ADD BASE RSC /l 0x809 /d "NDEBUG" /d "_AFXDLL"
# ADD RSC /l 0x809 /d "NDEBUG" /d "_AFXDLL"
RSC_PROJ=/l 0x809 /fo"$(INTDIR)/CclBoss.res" /d "NDEBUG" /d "_AFXDLL" 
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
BSC32_FLAGS=/nologo /o"$(OUTDIR)/CclBoss.bsc" 
BSC32_SBRS=
LINK32=link.exe
# ADD BASE LINK32 /nologo /subsystem:windows /machine:I386
# ADD LINK32 cclcpw32.lib /nologo /subsystem:windows /machine:I386
LINK32_FLAGS=cclcpw32.lib /nologo /subsystem:windows /incremental:no\
 /pdb:"$(OUTDIR)/CclBoss.pdb" /machine:I386 /out:"$(OUTDIR)/CclBoss.exe" 
LINK32_OBJS= \
	"$(INTDIR)/CclBoss.obj" \
	"$(INTDIR)/BOSSamp.obj" \
	"$(INTDIR)/ccloeci.obj" \
	"$(INTDIR)/StdAfx.obj" \
	"$(INTDIR)/CclBossD.obj" \
	"$(INTDIR)/CclBoss.res"

"$(OUTDIR)\CclBoss.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "CclBoss - Win32 Debug"

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

ALL : "$(OUTDIR)\CclBoss.exe" "$(OUTDIR)\CclBoss.tlb"

CLEAN : 
	-@erase ".\Debug\vc40.pdb"
	-@erase ".\Debug\CclBoss.pch"
	-@erase ".\Debug\vc40.idb"
	-@erase ".\Debug\CclBoss.tlb"
	-@erase ".\Debug\CclBoss.exe"
	-@erase ".\Debug\ccloeci.obj"
	-@erase ".\Debug\BOSSamp.obj"
	-@erase ".\Debug\CclBoss.obj"
	-@erase ".\Debug\StdAfx.obj"
	-@erase ".\Debug\CclBoss.res"
	-@erase ".\Debug\CclBossD.obj"
	-@erase ".\Debug\CclBoss.ilk"
	-@erase ".\Debug\CclBoss.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

# ADD BASE CPP /nologo /MDd /W3 /Gm /GX /Zi /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /Yu"stdafx.h" /c
# ADD CPP /nologo /MDd /W3 /Gm /GX /Zi /Od /D "_DEBUG" /D "WIN32" /D "_WINDOWS" /D "_AFXDLL" /D "_MBCS" /D " CICS_W32" /Yu"stdafx.h" /c
CPP_PROJ=/nologo /MDd /W3 /Gm /GX /Zi /Od /D "_DEBUG" /D "WIN32" /D "_WINDOWS"\
 /D "_AFXDLL" /D "_MBCS" /D " CICS_W32" /Fp"$(INTDIR)/CclBoss.pch" /Yu"stdafx.h"\
 /Fo"$(INTDIR)/" /Fd"$(INTDIR)/" /c 
CPP_OBJS=.\Debug/
CPP_SBRS=
# ADD BASE MTL /nologo /D "_DEBUG" /win32
# ADD MTL /nologo /D "_DEBUG" /win32
MTL_PROJ=/nologo /D "_DEBUG" /win32 
# ADD BASE RSC /l 0x809 /d "_DEBUG" /d "_AFXDLL"
# ADD RSC /l 0x809 /d "_DEBUG" /d "_AFXDLL"
RSC_PROJ=/l 0x809 /fo"$(INTDIR)/CclBoss.res" /d "_DEBUG" /d "_AFXDLL" 
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
BSC32_FLAGS=/nologo /o"$(OUTDIR)/CclBoss.bsc" 
BSC32_SBRS=
LINK32=link.exe
# ADD BASE LINK32 /nologo /subsystem:windows /debug /machine:I386
# ADD LINK32 cclcpw32.lib /nologo /subsystem:windows /debug /machine:I386
LINK32_FLAGS=cclcpw32.lib /nologo /subsystem:windows /incremental:yes\
 /pdb:"$(OUTDIR)/CclBoss.pdb" /debug /machine:I386 /out:"$(OUTDIR)/CclBoss.exe" 
LINK32_OBJS= \
	"$(INTDIR)/ccloeci.obj" \
	"$(INTDIR)/BOSSamp.obj" \
	"$(INTDIR)/CclBoss.obj" \
	"$(INTDIR)/StdAfx.obj" \
	"$(INTDIR)/CclBossD.obj" \
	"$(INTDIR)/CclBoss.res"

"$(OUTDIR)\CclBoss.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
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

# Name "CclBoss - Win32 Release"
# Name "CclBoss - Win32 Debug"

!IF  "$(CFG)" == "CclBoss - Win32 Release"

!ELSEIF  "$(CFG)" == "CclBoss - Win32 Debug"

!ENDIF 

################################################################################
# Begin Source File

SOURCE=.\ReadMe.txt

!IF  "$(CFG)" == "CclBoss - Win32 Release"

!ELSEIF  "$(CFG)" == "CclBoss - Win32 Debug"

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\CclBoss.cpp
DEP_CPP_CCLBO=\
	".\StdAfx.h"\
	".\CclBoss.h"\
	".\CclBossD.h"\
	

"$(INTDIR)\CclBoss.obj" : $(SOURCE) $(DEP_CPP_CCLBO) "$(INTDIR)"\
 "$(INTDIR)\CclBoss.pch"


# End Source File
################################################################################
# Begin Source File

SOURCE=.\CclBoss.odl

!IF  "$(CFG)" == "CclBoss - Win32 Release"


"$(OUTDIR)\CclBoss.tlb" : $(SOURCE) "$(OUTDIR)"
   $(MTL) /nologo /D "NDEBUG" /tlb "$(OUTDIR)/CclBoss.tlb" /win32 $(SOURCE)


!ELSEIF  "$(CFG)" == "CclBoss - Win32 Debug"


"$(OUTDIR)\CclBoss.tlb" : $(SOURCE) "$(OUTDIR)"
   $(MTL) /nologo /D "_DEBUG" /tlb "$(OUTDIR)/CclBoss.tlb" /win32 $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\StdAfx.cpp
DEP_CPP_STDAF=\
	".\StdAfx.h"\
	

!IF  "$(CFG)" == "CclBoss - Win32 Release"

# ADD CPP /Yc"stdafx.h"

BuildCmds= \
	$(CPP) /nologo /MD /W3 /GX /O2 /D "NDEBUG" /D "WIN32" /D "_WINDOWS" /D\
 "_AFXDLL" /D "_MBCS" /D " CICS_W32" /Fp"$(INTDIR)/CclBoss.pch" /Yc"stdafx.h"\
 /Fo"$(INTDIR)/" /c $(SOURCE) \
	

"$(INTDIR)\StdAfx.obj" : $(SOURCE) $(DEP_CPP_STDAF) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CclBoss.pch" : $(SOURCE) $(DEP_CPP_STDAF) "$(INTDIR)"
   $(BuildCmds)

!ELSEIF  "$(CFG)" == "CclBoss - Win32 Debug"

# ADD CPP /Yc"stdafx.h"

BuildCmds= \
	$(CPP) /nologo /MDd /W3 /Gm /GX /Zi /Od /D "_DEBUG" /D "WIN32" /D "_WINDOWS"\
 /D "_AFXDLL" /D "_MBCS" /D " CICS_W32" /Fp"$(INTDIR)/CclBoss.pch" /Yc"stdafx.h"\
 /Fo"$(INTDIR)/" /Fd"$(INTDIR)/" /c $(SOURCE) \
	

"$(INTDIR)\StdAfx.obj" : $(SOURCE) $(DEP_CPP_STDAF) "$(INTDIR)"
   $(BuildCmds)

"$(INTDIR)\CclBoss.pch" : $(SOURCE) $(DEP_CPP_STDAF) "$(INTDIR)"
   $(BuildCmds)

!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\CclBoss.rc
DEP_RSC_CCLBOS=\
	".\res\CclBoss.ico"\
	".\res\CclBoss.rc2"\
	

!IF  "$(CFG)" == "CclBoss - Win32 Release"


"$(INTDIR)\CclBoss.res" : $(SOURCE) $(DEP_RSC_CCLBOS) "$(INTDIR)"
   $(RSC) /l 0x809 /fo"$(INTDIR)/CclBoss.res" /i "Release" /d "NDEBUG" /d\
 "_AFXDLL" $(SOURCE)


!ELSEIF  "$(CFG)" == "CclBoss - Win32 Debug"


"$(INTDIR)\CclBoss.res" : $(SOURCE) $(DEP_RSC_CCLBOS) "$(INTDIR)"
   $(RSC) /l 0x809 /fo"$(INTDIR)/CclBoss.res" /i "Debug" /d "_DEBUG" /d\
 "_AFXDLL" $(SOURCE)


!ENDIF 

# End Source File
################################################################################
# Begin Source File

SOURCE=.\BOSSamp.cpp
DEP_CPP_BOSSA=\
	".\StdAfx.h"\
	".\CclBoss.h"\
	".\BOSSamp.h"\
	".\ccloeci.h"\
	{$(INCLUDE)}"\CICSECI.HPP"\
	{$(INCLUDE)}"\CCLDEFS.HPP"\
	{$(INCLUDE)}"\CCLEXC.HPP"\
	{$(INCLUDE)}"\CCLBUF.HPP"\
	{$(INCLUDE)}"\CCLCON.HPP"\
	{$(INCLUDE)}"\CCLECI.HPP"\
	{$(INCLUDE)}"\CCLFLO.HPP"\
	{$(INCLUDE)}"\CCLUOW.HPP"\
	

"$(INTDIR)\BOSSamp.obj" : $(SOURCE) $(DEP_CPP_BOSSA) "$(INTDIR)"\
 "$(INTDIR)\CclBoss.pch"


# End Source File
################################################################################
# Begin Source File

SOURCE=.\ccloeci.cpp
DEP_CPP_CCLOE=\
	".\StdAfx.h"\
	".\ccloeci.h"\
	

"$(INTDIR)\ccloeci.obj" : $(SOURCE) $(DEP_CPP_CCLOE) "$(INTDIR)"\
 "$(INTDIR)\CclBoss.pch"


# End Source File
################################################################################
# Begin Source File

SOURCE=.\CclBossD.cpp
DEP_CPP_CCLBOSS=\
	".\StdAfx.h"\
	".\CclBoss.h"\
	".\CclBossD.h"\
	

"$(INTDIR)\CclBossD.obj" : $(SOURCE) $(DEP_CPP_CCLBOSS) "$(INTDIR)"\
 "$(INTDIR)\CclBoss.pch"


# End Source File
# End Target
# End Project
################################################################################
