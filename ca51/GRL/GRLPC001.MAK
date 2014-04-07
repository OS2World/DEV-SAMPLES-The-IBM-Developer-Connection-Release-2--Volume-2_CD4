#*********************************************************************
#
# MODULE NAME        GRLPC001.MAK
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


GRLPC001.Exe:  GRLPC001.Def GRLPC001.L GRLPC001.Obj GRLPC001.Res
    Link       @GRLPC001.L
    rc         GRLPC001.Res GRLPC001.Exe

GRLPC001.Obj:  GRLPC001.C GRLPC001.H
    $(CC)      $*.C

GRLPC001.Res:  GRLPC001.RC GRLPC001.Ico GRLPC001.H
    rc -r      GRLPC001.RC GRLPC001.Res
