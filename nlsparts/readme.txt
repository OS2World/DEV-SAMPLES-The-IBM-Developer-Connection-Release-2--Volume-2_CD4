
DISCLAIMER
----------

Nothing in this package should be take to imply any plans are in place
to satisfy any of the requirements listed in this document.  The
interfaces and functions supplied by this prototype will certainly
change before this type of support appears in the product, if it ever
appears in the product.

Support will be provided only as time allows and given our current
release schedule it looks like there will be no time for supporting
this package.

We are however interested in feedback and comments.  Inside IBM, please
use the VACPPVIS forum on IBMPC.  Outside IBM, please use Compuserve
(GO VACPP - section 3 "Visual Building") or via IBM TalkLink to
VACPP CFORUM on IBMPC.  Make the post to the attention of Steve Wasleski.


ABSTRACT
--------

The IBM VisualAge for C++ Visual Builder does not directly support
the creation of reusable National Language Support (NLS) enabled parts.
The problem stems from the Open Class Library's enablement of a single
user resource library for a process and the Visual Builder's dependence
on that user resource library.

Typically reusable visual parts are provided in a dynamic link library
(DLL) and the resources needed by those parts are provided in a separate
resource DLL.  This works fine if all the visual parts needed by an
application are in that single DLL.  If multiple DLLs are used or the
executable program (EXE) has its own resource DLL, there is no safe setting
for the user resource library for the application.

This package introduces techniques and code to overcome this problem.
The problem of renaming and using the Open Class Library resource
library is also addressed.

It is sometimes desireable to be able to dynamically changed the
language an application is using at run time.  This package addresses
that need for both resource DLLs and help libraries.


FILES
-----

Starting in the directory you download the ZIP file to:
  +
  | NLSPARTS.ZIP - Contains all these files except itself. Use
  |                PKUNZIP2 -d NLSPARTS.ZIP to unpack into current directory.
  | README.TXT   - This file.
  |
  +--INCLUDE
  |    IVBDEFS.H    - The version shipped with VisualAge for C++ V3.5 on
  |                   Windows.  The package needs this versionn for both
  |                   Windows and OS/2, so it is included here.
  |    ivbglbls.hpp - IVBGlobals
  |    ivbhelp.hpp  - IVBHelpWindow
  |    ivbhelps.hpp - IVBHelpWindows
  |    ivbres.hpp   - IVBModuleResourceLibrary
  |    ivbreses.hpp - IVBModuleResourceLibraries
  |    ivbstres.cpp - Implementation for IVBStaticModuleResourceLibrary.
  |                   This is compiled and linked into each DLL and EXE.
  |    ivbstres.hpp - IVBStaticModuleResourceLibrary
  |
  +--SOURCE
  |    BUILD.CMD    - Rename the appropriate mak file to nlsparts.mak and run
  |                   this.  Rename this file to BUILD.BAT for Win95.
  |                   Specify /a parameter to force rebuild.
  |    ivbglbls.cpp - Implementation for IVBGlobals
  |    ivbhelp.cpp  - Implementation for IVBHelpWindow
  |    ivbhelps.cpp - Implementation for IVBHelpWindows
  |    ivbres.cpp   - Implementation for IVBModuleResourceLibrary
  |    ivbreses.cpp - Implementation for IVBModuleResourceLibraries
  |    nlsparts.def - Only used on OS/2
  |    nlsparts.os2 - OS/2 make file
  |    nlsparts.win - Windows make file
  |
  +--OS2
  |  |
  |  +--BIN
  |  |    dummy.obj    - Used to link resource DLLs
  |  |    nlsparts.dll - The DLL
  |  |    nlsparts.LIB - The import library
  |  |    VBNLS.vbb    - The VBB file
  |  |
  |  +--SAMPLES
  |     | BUILD.CMD - Builds all the samples.  Specify /a parameter to force
  |     |             rebuild.
  |     |
  |     +--ADDRESS
  |     |  | address.DEF  - DLL module definition file
  |     |  | address.mak  - Make file
  |     |  | ADDRESS.vbb  - Generate part source and main for AddressView
  |     |  |                before building.  You can erase the generated mak
  |     |  |                and app files.  We generate main just to get the
  |     |  |                rcx file.
  |     |  | addressV.cpv - This file is common between OS/2 and Windows, but
  |     |  |                is replicated in both directory structures.
  |     |  | addressV.hpv - This file is common between OS/2 and Windows, but
  |     |  |                is replicated in both directory structures.
  |     |  | BUILD.CMD    - Builds this module and its associated resource
  |     |  |                libraries (English and French).  Specify /a
  |     |  |                parameter to force rebuild.
  |     |  |
  |     |  +--RES
  |     |  |    addres.def - DLL module definition file
  |     |  |    addres.mak - Make file (common for all languages)
  |     |  |    BUILD.CMD  - Builds the English resource library.  Specify
  |     |  |                 /a parameter to force rebuild.
  |     |  |
  |     |  +--RESFR
  |     |       addres.def   - DLL module definition file
  |     |       addressV.rci - French translation (I think) of generated rci.
  |     |       BUILD.CMD    - Builds the French resource library.  Specify
  |     |                      /a parameter to force rebuild.
  |     |
  |     +--ENVELOPE
  |     |  | BUILD.CMD    - Builds this module and its associated resource
  |     |  |                libraries (English and French).  Specify /a
  |     |  |                parameter to force rebuild.
  |     |  | envelope.def - DLL module definition file
  |     |  | envelope.mak - Make file
  |     |  | ENVELOPE.vbb - Generate part source and main for EnvelopeView
  |     |  |                before building.  You can erase the generated mak
  |     |  |                and app files.  We generate main just to get the
  |     |  |                rcx file.
  |     |  | envelopV.cpv - This file is common between OS/2 and Windows, but
  |     |  |                is replicated in both directory structures.
  |     |  | envelopV.hpv - This file is common between OS/2 and Windows, but
  |     |  |                is replicated in both directory structures.
  |     |  | envelopV.hv  - This file is common between OS/2 and Windows, but
  |     |  |                is replicated in both directory structures.
  |     |  | envelopV.rcv - OS/2 bitmap resource definitions.
  |     |  |
  |     |  +--RES
  |     |  |    addressV.rci - A dummy rci file for the embedded address
  |     |  |                   views.  The address view resources are in the
  |     |  |                   address view resource DLL.  We do not want
  |     |  |                   them duplicated here, but the generated
  |     |  |                   envelopV.rci includes it and we do not want
  |     |  |                   to modify a generated file.
  |     |  |    BUILD.CMD    - Builds the English resource library.  Specify
  |     |  |                   /a parameter to force rebuild.
  |     |  |    envres.def   - DLL module definition file
  |     |  |    envres.mak   - Make file (common for all languages)
  |     |  |    STAMP.BMP    - A United States flag bitmap
  |     |  |
  |     |  +--RESFR
  |     |  |    BUILD.CMD    - Builds the French resource library.  Specify
  |     |  |                   /a parameter to force rebuild.
  |     |  |    envelopV.rci - French translation (I think) of generated rci.
  |     |  |    envres.def   - DLL module definition file
  |     |  |    STAMP.BMP    - A France flag bitmap
  |     |  |
  |     |  +--HELP
  |     |  |    BUILD.CMD  - Builds the English help library.  Specify
  |     |  |                 /a parameter to force rebuild.
  |     |  |    ENVLOP.IPF - The English help source
  |     |  |    envlop.mak - Make file (common for all languages)
  |     |  |
  |     |  +--HELPFR
  |     |       BUILD.CMD    - Builds the French help library.  Specify
  |     |                      /a parameter to force rebuild.
  |     |       ENVLOPFR.IPF - The French help source.  Okay, so really is not
  |     |                      French.  I just added an FR to the end of each
  |     |                      help panel title, so I would have visual
  |     |                      feedback of the help library change.  Can
  |     |                      anyone help me out here?
  |     |
  |     +--LETTER
  |     |  | BUILD.CMD    - Builds this module and its associated resource
  |     |  |                libraries (English and French).  Specify /a
  |     |  |                parameter to force rebuild.
  |     |  | LETOOR3U.DLL - A DLLRNAME'ed version of the IOCUI resource
  |     |  |                library for use with the application.  The print
  |     |  |                bitmap on the toolbar comes from here.
  |     |  | letter.mak   - Make file
  |     |  | LETTER.vbb   - Generate part source and main for LetterView
  |     |  |                before building.  You can erase the generated mak
  |     |  |                file.  You can rename the letterV.app file to
  |     |  |                vbmain.cpp, but you do not have to.  The BUILD
  |     |  |                command will copy it.
  |     |  | letterV.cpv  - This file is common between OS/2 and Windows, but
  |     |  |                is replicated in both directory structures.
  |     |  | letterV.hpv  - This file is common between OS/2 and Windows, but
  |     |  |                is replicated in both directory structures.
  |     |  | letterV.hv   - This file is common between OS/2 and Windows, but
  |     |  |                is replicated in both directory structures.
  |     |  | letterV.rcv  - OS/2 bitmap resource definitions.
  |     |  |
  |     |  +--RES
  |     |  |    BUILD.CMD  - Builds the English resource library.  Specify
  |     |  |                 /a parameter to force rebuild.
  |     |  |    letres.def - DLL module definition file
  |     |  |    letres.mak - Make file (common for all languages)
  |     |  |
  |     |  +--RESFR
  |     |  |    BUILD.CMD   - Builds the French resource library.  Specify
  |     |  |                  /a parameter to force rebuild.
  |     |  |    letres.def  - DLL module definition file
  |     |  |    letterV.rci - French translation (I think) of generated rci.
  |     |  |
  |     |  +--HELP
  |     |  |    BUILD.CMD  - Builds the English help library.  Specify
  |     |  |                 /a parameter to force rebuild.
  |     |  |    LETTER.IPF - The English help source
  |     |  |    letter.mak - Make file (common for all languages)
  |     |  |
  |     |  +--HELPFR
  |     |       BUILD.CMD    - Builds the French help library.  Specify
  |     |                      /a parameter to force rebuild.
  |     |       LETTERFR.IPF - The French help source.  Okay, so really is not
  |     |                      French.  I just added an FR to the end of each
  |     |                      help panel title, so I would have visual
  |     |                      feedback of the help library change.  Can
  |     |                      anyone help me out here?
  |     |
  |     +--TestArea
  |          GETLET.CMD - Copies NLSPARTS.DLL and all the sample DLLs and
  |                       help libraries to the TestArea.  You can then run
  |                       the letter.exe sample from here without any changes
  |                       to your environment.  This assumes you have a .;
  |                       near the start of your LIBPATH.
  |
  +--WIN
     |
     +--BIN
     |    dummy.obj    - Used to link resource DLLs
     |    nlsparts.dll - The Windows DLL
     |    nlsparts.LIB - The Windows import library
     |    VBNLS.vbb    - The Windows VBB file
     |
     +--SAMPLES
        | BUILD.BAT - Builds all the samples.  Specify /a parameter to force
        |             rebuild.
        |
        +--ADDRESS
        |  | address.mak  - Windows make file
        |  | ADDRESS.vbb  - Generate part source and main for AddressView
        |  |                before building.  You can erase the generated mak
        |  |                and app files.  We generate main just to get the
        |  |                rcx file.
        |  | addressV.cpv - This file is common between OS/2 and Windows, but
        |  |                is replicated in both directory structures.
        |  | addressV.hpv - This file is common between OS/2 and Windows, but
        |  |                is replicated in both directory structures.
        |  | BUILD.BAT    - Builds this module and its associated resource
        |  |                libraries (English and French).  Specify /a
        |  |                parameter to force rebuild.
        |  |
        |  +--RES
        |  |    addres.mak - Make file (common for all languages)
        |  |    BUILD.BAT  - Builds the English resource library.  Specify
        |  |                 /a parameter to force rebuild.
        |  |
        |  +--RESFR
        |       addressV.rci - French translation (I think) of generated rci.
        |       BUILD.BAT    - Builds the French resource library.  Specify
        |                      /a parameter to force rebuild.
        |
        +--ENVELOPE
        |  | BUILD.BAT    - Builds this module and its associated resource
        |  |                libraries (English and French).  Specify /a
        |  |                parameter to force rebuild.
        |  | envelope.mak - Make file
        |  | ENVELOPE.vbb - Generate part source and main for EnvelopeView
        |  |                before building.  You can erase the generated mak
        |  |                and app files.  We generate main just to get the
        |  |                rcx file.
        |  | envelopV.cpv - This file is common between OS/2 and Windows, but
        |  |                is replicated in both directory structures.
        |  | envelopV.hpv - This file is common between OS/2 and Windows, but
        |  |                is replicated in both directory structures.
        |  | envelopV.hv  - This file is common between OS/2 and Windows, but
        |  |                is replicated in both directory structures.
        |  | envelopV.rcv - Windows bitmap resource definitions.
        |  |
        |  +--RES
        |  |    addressV.rci - A dummy rci file for the embedded address
        |  |                   views.  The address view resources are in the
        |  |                   address view resource DLL.  We do not want
        |  |                   them duplicated here, but the generated
        |  |                   envelopV.rci includes it and we do not want
        |  |                   to modify a generated file.
        |  |    BUILD.BAT    - Builds the English resource library.  Specify
        |  |                   /a parameter to force rebuild.
        |  |    envres.mak   - Make file (common for all languages)
        |  |    STAMP.BMP    - A United States flag bitmap
        |  |
        |  +--RESFR
        |  |    BUILD.BAT    - Builds the French resource library.  Specify
        |  |                   /a parameter to force rebuild.
        |  |    envelopV.rci - French translation (I think) of generated rci.
        |  |    STAMP.BMP    - A France flag bitmap
        |  |
        |  +--HELP
        |  |    BUILD.BAT  - Builds the English help library.  Specify
        |  |                 /a parameter to force rebuild.
        |  |    ENVLOP.IPF - The English help source
        |  |    envlop.mak - Make file (common for all languages)
        |  |
        |  +--HELPFR
        |       BUILD.BAT    - Builds the French help library.  Specify
        |                      /a parameter to force rebuild.
        |       ENVLOPFR.IPF - The French help source.  Okay, so really is not
        |                      French.  I just added an FR to the end of each
        |                      help panel title, so I would have visual
        |                      feedback of the help library change.  Can
        |                      anyone help me out here?
        |
        +--LETTER
        |  | BUILD.BAT    - Builds this module and its associated resource
        |  |                libraries (English and French).  Specify /a
        |  |                parameter to force rebuild.
        |  | letter.mak   - Make file
        |  | LETTER.vbb   - Generate part source and main for LetterView
        |  |                before building.  You can erase the generated mak
        |  |                file.  You can rename the letterV.app file to
        |  |                vbmain.cpp, but you do not have to.  The BUILD
        |  |                command will copy it.
        |  | letterV.cpv  - This file is common between OS/2 and Windows, but
        |  |                is replicated in both directory structures.
        |  | letterV.hpv  - This file is common between OS/2 and Windows, but
        |  |                is replicated in both directory structures.
        |  | letterV.hv   - This file is common between OS/2 and Windows, but
        |  |                is replicated in both directory structures.
        |  | letterV.rcv  - Windows bitmap resource definitions.
        |  | LETWOR3U.DLL - A DLLRNAME'ed version of the IOCUI resource
        |  |                library for use with the application.  The print
        |  |                bitmap on the toolbar comes from here.
        |  |
        |  +--RES
        |  |    BUILD.BAT  - Builds the English resource library.  Specify
        |  |                 /a parameter to force rebuild.
        |  |    letres.mak - Make file (common for all languages)
        |  |
        |  +--RESFR
        |  |    BUILD.BAT   - Builds the French resource library.  Specify
        |  |                  /a parameter to force rebuild.
        |  |    letterV.rci - French translation (I think) of generated rci.
        |  |
        |  +--HELP
        |  |    BUILD.BAT  - Builds the English help library.  Specify
        |  |                 /a parameter to force rebuild.
        |  |    LETTER.IPF - The English help source
        |  |    letter.mak - Make file (common for all languages)
        |  |
        |  +--HELPFR
        |       BUILD.BAT    - Builds the French help library.  Specify
        |                      /a parameter to force rebuild.
        |       LETTERFR.IPF - The French help source.  Okay, so really is not
        |                      French.  I just added an FR to the end of each
        |                      help panel title, so I would have visual
        |                      feedback of the help library change.  Can
        |                      anyone help me out here?
        |
        +--TestArea
             GETLET.BAT - Copies NLSPARTS.DLL and all the sample DLLs and
                          help libraries to the TestArea.  You can then run
                          the letter.exe sample from here without any changes
                          to your environment.


THE SAMPLE
----------

Description:
  The sample is a simple application for writing letters and addressing
  envelopes.  It consists of 2 DLLs and 1 EXE.
    - First DLL contains non-frame window parts (the address view that
      is used twice on the envelope view)
      - Has 2 resource DLLs (English and French)
    - Second DLL contains frame window parts (the envelope view)
      - Uses parts from first DLL
      - Has 2 resource DLLs (English and French)
      - Has 2 help libraries (English and French)
        - Includes modified source for parts in first DLL
    - The EXE contains frame window parts (the letter view)
      - Uses parts from the second DLL
      - Has 2 resource DLLs (English and French)
      - Has 2 help libraries (English and French)
      - Has a renamed version of the ICLUI resource library
        associated with it

Running it:
  1) In the OS2 or WIN SAMPLES subdirectories, generate source from the VBB
     files.  See the notes in the file listing above.

  2) In the OS2 or WIN SAMPLES subdirectory, run the BUILD command or use the
     individual BUILD commands in each of the subdirectories.  Again, see the
     notes above.

  3) In the OS2 or WIN SAMPLES subdirectoy, switch to the TestArea
     subdirectory and run the GETLET command.  Again, see the notes above.

  4) Run letter.exe.

  5) Press the France and United States flags on the toolbar to change
     languages.

  6) Press the Envelope button to open the envelope view with two embedded
     address views.

  7) Closing the letter window will exit the application.


SOME DETAILS
------------

The following delimited sections are from the charts I use when making
presentations on this topic and package.  It is all I have time to provide
right now.  If after using the package, anyone has other information they
believe would be helpful to others trying to learn the package, please
send it to us.

*********************************************************************

VisualAge for C++ Visual Builder
--------------------------------

Creating Reusable NLS Enabled Visual Parts
------------------------------------------

Steven F. Wasleski
VisualAge for C++ Visual Builder Development

IBM's technical and business plans are subject to change.  This
presentation is not intended to create any warranties.  IBM
warranties are contained in the appropriate IBM agreement(s).

*********************************************************************

Agenda
------

  - Requirements
  - Solution overview
  - How to create and reuse DLLs of visual parts
  - Where to get the software
  - Other topics as time permits

  - DISCLAIMER:  All of this works with VisualAge for C++ Version 3.5.
                 For VisualAge for C++ Version 3.0 users, it will only
                 work for string resources that are not offsets from
                 window ids (for example, strings for button labels,
                 static text, etc.).  If you use anything that is offset
                 from a window id (for example, help, fly over text,
                 accelerators, info area) it will not work.

*********************************************************************

Requirements
------------

  - User resource library is inadequate
    - Visual Builder generated applications use the ICLUI user resource
      library for all resources
      - There is only one for the entire application
      - Third party parts (packaged in DLLs) will not be able to find
        their resources
      - Even if the source for all the resources was shipped with the
        DLL and included into the single resource library, resource id
        conflicts would occur
    - Need to be able to have a resource library associated with each
      module (DLL or EXE) in the application
    - Enables binary reuse of visual parts and their resources
      - Several resource translations could be provided
      - The proper resource library could be automatically selected
        when the application is installed

*********************************************************************

Requirements
------------

  - Changing resource libraries at run time would be useful
    - Need resource library naming convention for language suffixes
      - ISO 639 standard
      - Used with ISO 3166 and X Consortium codeset standards in
        naming locales
    - Need an api to change the language for all resource libraries
      in the application
    - Need mechanism for letting all resource libraries know that the
      language is changing
    - Need mechanism for all UI components to be updated when the
      resource library does change

*********************************************************************

Requirements
------------

  - What about the ICLUI resource library
    - A single ICLUI resource library is adequate for an application
    - It does not need any translation
    - It does need to be renamed when the application is shipped
      - ICurrentApplication::setResourceLibrary must be called
      - Visual Builder does not easily support this
      - I have added an easier, but not perfect, way to do this
        (at build time rather than by changing generated main)
      - It should only be used in the application EXE, not in DLLs

*********************************************************************

Requirements
------------

  - Help libraries can be handled similarly except that an
    IHelpWindow object can only be associated with IFrameWindow
    objects.  This makes binary reuse of non-frame window parts
    difficult.
    - When using IPF, multiple help libraries can be used at once by
      an IHelpWindow object, but adding code to add the help library
      associated with another DLL ends up adding a dependency on the
      name of that DLL into the user's code.  This defeats DLL
      renaming for ship purposes.
    - In addition, usually you want to modify the help associated with
      reused non-frame window parts to make the help context sensitive.
    - Finally, when using RTF on Windows, only a single help library
      can be used by an IHelpWindow at any given time.

*********************************************************************

Requirements
------------

  - I recommend creating separate DLLs for reusable frame window
    parts and non-frame window parts.
    - DLLs of frame window parts can be enabled for binary reuse
      of the code, resources, and the help library.
    - DLLs of non-frame window parts can be enabled for binary reuse
      of the code and resources.  The source for the help should be
      shipped, so it can be modified and included in the help library
      associated with frame windows in other DLLs and in EXEs.

*********************************************************************

Solution overview
-----------------

  - The demo application consists of parts in 2 DLLs and 1 EXE
  - First DLL contains non-frame window parts
    - Has 2 resource DLLs (English and French)
  - Second DLL contains frame window parts
    - Uses parts from first DLL
    - Has 2 resource DLLs (English and French)
    - Has 2 help libraries (English and French)
      - Includes modified source for parts in first DLL
    - The EXE contains frame window parts
      - Uses parts from the second DLL
      - Has 2 resource DLLs (English and French)
      - Has 2 help libraries (English and French)
      - Has a renamed version of the ICLUI resource library
        associated with it

*********************************************************************

Solution overview
-----------------

  - New classes have notes to the right that indicate how many
    instances exist in a running application.

  - IStandardNotifier
    - IVBModuleResourceLibrary         1/DLL or EXE

  - IVBStaticModuleResourceLibrary     1/DLL or EXE - compiled in

  - IHelpWindow
    - IVBHelpWindow                    as needed

  - IVBGlobals                         abstract - global chg APIs
    - IVBModuleResourceLibraries       1/process
    - IVBHelpWindows                   1/process

*********************************************************************

How to create and reuse DLLs of visual parts
--------------------------------------------

  - Without dynamic language switching
    - Non-frame window parts in a DLL
      - Use Workframe to get correct makefile
      - ivbstres.hpp in list of required include files in VB class ed
      - Use VB_MODULE_RESOURCES=resDllName macro
      - Use IVB_IMPORT=_Import macro on Windows
      - Compile and link in ivbstres.cpp with your DLL
    - Frame window parts in a DLL
      - Same as previous, plus:
      - Use IVBHelpWindow rather than IHelpWindow
      - Modify and imbed help for reused non-frame window parts
    - The application - frame window parts in an EXE
      - Same as previous, plus:
      - Use VB_ICLUI_RESOURCES=ICLUIresDllName macro
      - Compile and link in ivbstres.cpp with your EXE

*********************************************************************

How to create and reuse DLLs of visual parts
--------------------------------------------

  - To create the resource DLL
    - Build it in a subdirectory and be sure the mak file will be able
      to find the *.rci files via the INCLUDE environment variable.
    - Create dummy *.rci files for parts in other DLLs that you use
      as subparts or create with factories.
      - Your *.rci files will include them, but you do not want that.
    - Create an *.rcx file by hand (easy with version 3.5).  Or,
      create a bogus part that has factories of all the other parts
      in the DLL and generate an rcx from it (may need to throw away
      all the other files it generates).
    - Build the resource DLL.
    - This will isolate the files for easy copying to other
      directories where they can be translated to other languages
      and built into resource DLLs for those languages.
    - Use the samples as a pattern.

*********************************************************************

How to create and reuse DLLs of visual parts
--------------------------------------------

  - To create the help library for frame window parts
    - Imbed and modify help for non-frame window parts that you use
    - Set the help ids for imbedded non-frame window parts
      - If you need to set the help id for a subpart of a non-frame
        window part, the creator of the non-frame window part must
        have promoted the subpart's helpId attribute.
    - As with the resource DLL, do this in another directory to ease
      translation.

*********************************************************************

How to create and reuse DLLs of visual parts
--------------------------------------------

  - Adding dynamic language switching
    - APIs to change language
      - static void VBGlobals::setLanguageSuffix
        ( const IString& suffix );
      - static void IVBGlobals::setLocale
        ( const IString& locale, Boolean setLanguage = true );
    - Resources require changes to the part
      - Must write action for each part that reloads resources
      - Must add and set a variable for the resource library
        (type IVBModuleResourceLibrary*). On a "ready" to "this"
        connection use VBMODULERESOURCELIBRARY macro.
      - Must make an event-to-action connection from the variable's
        library event to the action
      - Obvious area for improvement
    - Help is automatic if you use IVBHelpWindow on version 3.5

*********************************************************************

Where to get the software
-------------------------

  - Use anonymous ftp to service.boulder.ibm.com
  - If that does not work for you, try ftp.software.ibm.com
    - It is another name for the same ftp server
  - Change to directory ps/products/visualagecpp/tools/nlsparts
  - Change to binary mode and download the file nlsparts.zip
  - Unpack it with
    - On OS/2 -- PKUNZIP2 -d nlsparts.zip
    - On Windows -- PKUNZIP -d nlsparts.zip
    - This will unzip it into the current directory with several
      subdirectories
    - Please read the README.TXT file in the current directory

*********************************************************************

Other topics as time permits
----------------------------

  - APIs for dynamically changing resource library and default help
    library name and suffix
    - They are attributes of IVBModuleResourceLibrary and IVBHelpWindow
  - Resource library and help window cursors
    - Both IVBModuleResourceLibraries and IVBHelpWindows have
      static ICursor* newCursor();
  - IPF only global functions in IVBHelpWindows
    - Can set all help window titles and help panel id visibility to
      the same values
  - Specific implementation details
  - How does this relate to future enhancements in the product?
    - There are areas that can be greatly improved by changes in
      code generation (resource reloading) and build process
      (ICLUI resource library name and compiler macros)

*********************************************************************
