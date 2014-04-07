#*********************************************************************
#
# MODULE NAME        GRSPC001.MAK
#
# DESCRIPTIVE NAME   Sample ECI program
#
# Statement:         Licensed Materials - Property of IBM
#
#                    53G3861,53G3862
#                    (c) Copyright IBM Corp. 1988, 1993.
#
#                    See Copyright Instructions.
#
#                    All rights reserved.
#
#                    U.S. Government Users Restricted Rights - use,
#                    duplication or disclosure restricted by GSA
#                    ADP Schedule Contract with IBM Corp.
#
# Status:            Version 2 Release 0
#
#  NOTES :-
#    DEPENDENCIES = None
#                   None
#    RESTRICTIONS = none
#    MODULE TYPE  = Module definition file
#    PROCESSOR    = PS/2 and PC
#
#*********************************************************************
#
StdOpts = -c -Gsw -Zp -W3 -Od -Zd -Zi
MemOpts = -AL -B1 C1L.EXE -Gt2
LstOpts = -Fs

CC = cl $(StdOpts) $(MemOpts) $(LstOpts)


GRSPC001.Exe:  GRSPC001.Def GRSPC001.L GRSPC001.Obj GRSPC001.Res
    Link       @GRSPC001.L
    rc         GRSPC001.Res GRSPC001.Exe

GRSPC001.Obj:  GRSPC001.C GRSPC001.H
    $(CC)      $*.C

GRSPC001.Res:  GRSPC001.RC GRSPC001.Ico GRSPC001.H
    rc -r      GRSPC001.RC GRSPC001.Res
