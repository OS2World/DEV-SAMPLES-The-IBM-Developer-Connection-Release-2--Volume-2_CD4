#*********************************************************************
#
# MODULE NAME        ancpc001.mak
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


ancpc001.Exe:  ancpc001.Def ancpc001.L ancpc001.Obj ancpc001.Res
    Link       @ancpc001.L
    rc         ancpc001.Res ancpc001.Exe

ancpc001.Obj:  ancpc001.C ancpc001.H
    $(CC)      $*.C

ancpc001.Res:  ancpc001.RC ancpc001.Ico ancpc001.H
    rc -r      ancpc001.RC ancpc001.Res
