 ---------------------------------------------------------------------
Ý                                                                     Ý
Ý  MODULE NAME      CA5A.ZIP                                          Ý     
Ý                                                                     Ý
Ý  DESCRIPTIVE NAME CICS Clients for Windows - Programming with       Ý
Ý                   LotusScript                                       Ý
Ý                                                                     Ý     
Ý  Statement:       Licensed Materials - Property of IBM              Ý     
Ý                   CA5A SupportPac                                   Ý 
Ý                   (c) Copyright IBM Corp. 1997                      Ý     
Ý                   All rights reserved.                              Ý
Ý                                                                     Ý
Ý                   U.S. Government Users Restricted Rights - use,    Ý
Ý                   duplication or disclosure restricted by GSA       Ý
Ý                   ADP Schedule Contract with IBM Corp.              Ý
Ý                                                                     Ý
Ý  Status:          Version 1 Release 1                               Ý
Ý  Last Modified:   11 February 1997                                  Ý
Ý                                                                     Ý  
Ý  Change History:                                                    Ý
Ý     11 Feb 1997 - Fix corrupt Lotus DB (unable to view Design)      Ý
Ý                                                                     Ý
Ý  Author:          Richard Chamberlain                               Ý
Ý                                                                     Ý
Ý   NOTES :-                                                          Ý
Ý     DEPENDENCIES = CICS Client V2.0.1 or later for Windows NT       Ý
Ý                    or Windows 95                                    Ý
Ý                    Lotus Notes V4.0 or later for Windows NT         Ý
Ý                    or Windows 95                                    Ý
Ý     RESTRICTIONS = None                                             Ý
Ý     MODULE TYPE  = InfoZip                                          Ý
Ý     PROCESSOR    = x86                                              Ý
Ý                                                                     Ý
 ---------------------------------------------------------------------
                                                                       
                                                                       
INTRODUCTION
------------

This SupportPac provides a sample Lotus Notes Database containing two 
Forms showing how to use the CICS Client V2.0 OLE interfaces to access
data from CICS. 'ECIForm' uses the CICS External Call Interface (ECI)
to call a CICS server program, 'EPIForm' uses the CICS External 
Presentation Interface (EPI) to run a BMS transaction on the CICS server. 

The CICS Client OLE Interfaces are fully documented in "Object Oriented
Programming for CICS Clients" SC33-1639-00. In CICS Client V2.0.1, the
documentation has been updated and is now published as a separate book
"OO Programming in Basic for CICS Clients" SC33-1924. 
 
INSTALLATION
------------

To install the SupportPac, copy the file CA5A.ZIP to a temporary
directory and uncompress using InfoZip's Unzip.  Use the default
options to ensure the subdirectories stored in the ZIP file are
re-created.

The following subdirectories will be created:

  lotus    - Sample LotusNotes Database

  server   - Sample CICS 'back-end' C programs


USAGE
-----

1. Sample Lotus Notes Database

You should load the Lotus Database file CCLSAMP.NSF into Lotus Notes
R4.0 for Windows NT or Windows 95. You can then look at the designs
of the two supplied Forms. You must have the full version of Lotus
Notes (not Lotus Notes Desktop), in order to view the Form designs.


  ECIForm:  This form has display fields for sample data accessed from
  CICS, and a pushbutton that invokes a LotusScript program. The script
  uses the CICS Client OLE External Call Interface (ECI) objects to
  call the sample CICS back-end program "ECISAMP". The server selected
  is the default CICS server (i.e. the first configured in CICSCLI.INI).
  The server program returns a 66 byte commarea containing the data,
  time and CICS system information. This data is extracted, formatted 
  and displayed on the Notes document.

  EPIForm:   Like ECIForm, this form has display fields and a pushbutton
  that invokes a LotusScript program. The script uses the CICS Client
  OLE External Presentation Interface (EPI) objects to run the BMS
  transaction "EPIC". The script navigates through two 3270 screens
  using the CICS EPI Map object (Ccl.Map). The Map object uses data
  included from file EPIINQ.BAS, which is generated from the server 
  program .BMS file using the CICS V2.0 BMS conversion utility
  (CICSBMSC.EXE). Fields from the BMS screens are output on the Lotus
  Notes form.        

Notes: 

  To run the ECI sample, you must have CICS Client V2.0 installed, and
  you must have registered the CICS ECI OLE Automation Server by running
  running CCLOECI.EXE. You also need to have the supplied sample
  back-end program ECISAMP installed on your CICS server (see below).

  To run the EPI sample, you must have CICS Client V2.0.1 installed,
  you must have registered the CICS EPI OLE Automation Server by running
  running CCLOEPI.EXE. You also need to have the supplied sample
  back-end transaction EPIC defined and program EPIINQ installed on your
  CICS server (see below).

2. Sample back-end CICS server programs (ECISAMP and EPIINQ)

Back-end CICS C programs are provided in subdirectory /server.  These
programs have been designed to run under Transaction Server for OS/2, and
can be built and installed using the utility CICSCTCL.CMD.

ECISAMP.CCS

  CICS C program to query the CICS server details and date/time,
  returning this information in the commarea. Lotus Notes sample ECIForm
  calls this program.

EPIINQ.CCS EPIINQ.BMS

  CICS C program to query the CICS server details and date/time, 
  returning this information as two 3270 (BMS) screens. File EPIINQ.BMS 
  contains the associated BMS map source. A transaction should be
  defined to run program EPIINQ, the Lotus Notes sample EPIForm 
  expects the transaction name to be "EPIC".   
