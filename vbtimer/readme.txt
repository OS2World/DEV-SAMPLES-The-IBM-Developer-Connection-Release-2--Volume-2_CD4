
Description:
------------
   VBTimer is a part that signals an event at regular intervals.  It can
   be started and stopped programatically.


   VBTIMER.ZIP contains:
           TIMER.ZIP
           TIMERCOD.ZIP
           TIMERSMP.ZIP
           README 

Use:
----
   The TIMER.ZIP file contains the files that are needed to be able to
   use the VBTimer part.  Currently it is available to use from a DLL but at
   some point in the future I may create a static lib.  The following files
   are in this directory:
                          VBTimer.dll
                          VBTimer.h
                          VBTImer.hpp
                          VBTimer.lib
                          VBTimer.vbb

    To use the part, copy all these files to your development directory and
    load VBTimer.vbb into the visual builder.  You can then add the VBTimer
    part to your palatte and use it in your applications.

Code:
-----
    The actual code and makefile for the VBTimer part is in the TIMERCOD.ZIP
    file.  If you want to modify it or use it as a base for another part, feel
    free.

Sample:
-------
    I have included in the TIMERSMP.ZIP file a file named clock.vbb.
    This is a digital clock sample that uses the VBTimer part.  Load clock.vbb,
    generate out part source and the main, and then compile it.

