#*********************************************************************
#
# MODULE NAME        ANMPC001.MAK
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


ANMPC001.Exe:  ANMPC001.Def ANMPC001.L ANMPC001.Obj ANMPC001.Res
    Link       @ANMPC001.L
    rc         ANMPC001.Res ANMPC001.Exe

ANMPC001.Obj:  ANMPC001.C ANMPC001.H
    $(CC)      $*.C

ANMPC001.Res:  ANMPC001.RC ANMPC001.Ico ANMPC001.H
    rc -r      ANMPC001.RC ANMPC001.Res
