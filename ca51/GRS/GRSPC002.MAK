#*********************************************************************
#
# MODULE NAME        GRSPC002.MAK
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


GRSPC002.Exe:  GRSPC002.Def GRSPC002.L GRSPC002.Obj GRSPC002.Res
    Link       @GRSPC002.L
    rc         GRSPC002.Res GRSPC002.Exe

GRSPC002.Obj:  GRSPC002.C GRSPC002.H
    $(CC)      $*.C

GRSPC002.Res:  GRSPC002.RC GRSPC002.Ico GRSPC002.H
    rc -r      GRSPC002.RC GRSPC002.Res
