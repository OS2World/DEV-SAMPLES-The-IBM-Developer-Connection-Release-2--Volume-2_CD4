#*********************************************************************
#
# MODULE NAME        SAMPC001.MAK
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


SAMPC001.Exe:  SAMPC001.Def SAMPC001.L SAMPC001.Obj SAMPC001.Res
    Link       @SAMPC001.L
    rc         SAMPC001.Res SAMPC001.Exe

SAMPC001.Obj:  SAMPC001.C SAMPC001.H
    $(CC)      $*.C

SAMPC001.Res:  SAMPC001.RC SAMPC001.Ico SAMPC001.H
    rc -r      SAMPC001.RC SAMPC001.Res
