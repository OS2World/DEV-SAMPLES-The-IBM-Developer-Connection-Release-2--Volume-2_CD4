#*********************************************************************
#
# MODULE NAME        SASPC001.MAK
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


SASPC001.Exe:  SASPC001.Def SASPC001.L SASPC001.Obj SASPC001.Res
    Link       @SASPC001.L
    rc         SASPC001.Res SASPC001.Exe

SASPC001.Obj:  SASPC001.C SASPC001.H
    $(CC)      $*.C

SASPC001.Res:  SASPC001.RC SASPC001.Ico SASPC001.H
    rc -r      SASPC001.RC SASPC001.Res
