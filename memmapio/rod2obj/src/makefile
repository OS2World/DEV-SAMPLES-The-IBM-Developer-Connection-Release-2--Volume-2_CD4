#*****************************************************************************
# Filename : Makefile
# Owner    : Chris Matthews
#
#
#
#
#*****************************************************************************
.SUFFIXES: .hpp .cpp .res .dlg .rc .idl .xih

NEWLFLAG=/PMTYPE:VIO /STACK:1000000

TLKTDRV    = e:\ibmcpp
CCDRV      = e:
ROOT       = \rod2obj

#*****************************
# Environment Macros
#*****************************
RPATH      = $(ROOT)\rel
OPATH      = $(ROOT)\obj
SPATH      = $(ROOT)\src
SUPSRC     = $(SPATH)\support
LOADSRC    = $(SPATH)\loadtest

INCLUDE    =  .;$(CCDRV)\ibmcpp\include;$(CCDRV)\ibmcpp\include\os2;$(TLKTDRV)\toolkit\h;$(INCPATH)\
               ;$(RESPATH);$(INCLUDE);$(WPSRC)
LIB        =   $(CCDRV)\ibmcpp\lib;$(TLKTDRV)\toolkit\lib;$(LIB);

#*****************************
# Tools Macros
#*****************************
CC         =  icc
LINK       =  ilink

#************************************************************************
# Tools Flags
#************************************************************************
# Compiler Options
#-----------------------------------------------------------------------
# -C+      -  Compile Only
# -Q+      -  Quiet Mode ( No Banner )
# -Fb      -  Generate Browser File Information
# -Fo      -  Output Object files into Different directory
# -Ms      -
# -Ti      -  Generate Debug Information
# -Ss      -  Support // Comment Style
#
#************************************************************************
CFLAGS     =  $(NEWCFLAG) -C+ -Q+ -Ms -Ti+ -Fo$(OPATH)\$(@B) -Ss

# ***********************************************************************
# Linker Options
#-----------------------------------------------------------------------
# /CO      -  Code View Information
# /MAP     -  Generate Map File Information
# /NOL     -  Ignore extended library information
# /NOI     -  Case Sensitive ( ON )
# /EXEPACK -  Pack code and data
# ***********************************************************************
LFLAGS     =  $(NEWLFLAG) /CO /MAP /NOL /NOI /BROWSE /EXEPACK:2
LIBS       =  os2386.lib

#*****************************************************************************
# Inference Rules
#*****************************************************************************
{$(SUPSRC)}.c.obj:
        $(CC) $(CFLAGS) /Ge- $<

{$(LOADSRC)}.c.obj:
        $(CC) $(CFLAGS) $<

#*****************************************************************************
# Dependency List
#*****************************************************************************
SUPOBJ     = $(OPATH)\initterm.obj $(OPATH)\stub.obj

#*****************************************************************************
# Dependency List
#*****************************************************************************
all: bldtree bldsupportobj $(RPATH)\loadtest.exe

#--------------------------------------------
#- Build Optional Loadtest program
#--------------------------------------------
$(RPATH)\loadtest.exe: $(OPATH)\loadtest.obj
        $(LINK) /NOFREE $(LFLAGS) $(OPATH)\loadtest.obj,$(RPATH)\loadtest.exe,$(RPATH)\loadtest.map,,,

#--------------------------------------------
#- Build Working Tree if it does not exist
#--------------------------------------------
bldtree:
    Rem Checking Build Tree
    if not exist $(OPATH) md $(OPATH)
    if not exist $(RPATH) md $(RPATH)
    Rem Finished Checking Build Tree

#--------------------------------------------
#- Build ROD2OBJ Support Modules
#--------------------------------------------
bldsupportobj: $(SUPOBJ)
    copy $(OPATH)\initterm.obj $(RPATH) > nul
    copy $(OPATH)\stub.obj     $(RPATH) > nul
    copy $(SUPSRC)\object.def  $(RPATH) > nul
