#*********************************************************************
#
# MODULE NAME        SACPC001.MAK
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


SACPC001.Exe:  SACPC001.Def SACPC001.L SACPC001.Obj SACPC001.Res
    Link       @SACPC001.L
    rc         SACPC001.Res SACPC001.Exe

SACPC001.Obj:  SACPC001.C SACPC001.H
    $(CC)      $*.C

SACPC001.Res:  SACPC001.RC SACPC001.Ico SACPC001.H
    rc -r      SACPC001.RC SACPC001.Res
