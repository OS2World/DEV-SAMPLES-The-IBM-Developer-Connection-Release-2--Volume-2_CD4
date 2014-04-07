# makefile for Notes API sample program main.
#               OS/2 2.x Mixed 32/16 bit version
#
# This makefile is designed for use under OS/2 2.x with the
# IBM C/C++ Tools 2.0 compiler.

# This makefile assumes that the INCLUDE and LIB environment variables
# are set up to point at the Notes and C "include" and "lib" directories.

# The name of the program.

PROGNAME = DEMO1

# It appears that the program (main) must be marked as permanent
# in the PPT entry otherwise subsequent transactions abend

all: demo1.dll


demo1.obj: demo1.c
           icc /DOS2 /DOS2_MIXED3216 /c /Gm- /Ge+  /O- /Ti+ /W3 $*.C

demo1.C: demo1.CCS
        faacppml $*.ccs main

demo1.dll: demo1.OBJ  cicsutil.obj scclmsg.obj
       icc /B" /PM:PM /ALIGN:16 /MAP /DE" \
           /Fe$*.dll /Fm$*.map \
            demo1.obj FAAOTSCC.LIB FAASR32.LIB faaistrt.def
