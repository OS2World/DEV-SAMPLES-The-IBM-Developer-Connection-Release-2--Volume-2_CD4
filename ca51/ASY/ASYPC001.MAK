#*********************************************************************
#
# MODULE NAME        ASYPC001.MAK
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


ASYPC001.Exe:  ASYPC001.Def ASYPC001.L ASYPC001.Obj ASYPC001.Res
    Link       @ASYPC001.L
    rc         ASYPC001.Res ASYPC001.Exe

ASYPC001.Obj:  ASYPC001.C ASYPC001.H
    $(CC)      $*.C

ASYPC001.Res:  ASYPC001.RC ASYPC001.Ico ASYPC001.H
    rc -r      ASYPC001.RC ASYPC001.Res
