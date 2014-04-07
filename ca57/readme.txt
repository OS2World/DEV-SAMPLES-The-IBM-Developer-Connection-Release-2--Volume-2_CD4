ÚÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄ¿
³                                                                     ³
³  MODULE NAME      CA57.ZIP                                          ³     
³                                                                     ³
³  DESCRIPTIVE NAME CICS Clients for Windows - OO Basic Programming   ³
³                   with Complex COMMAREAs                            ³
³                                                                     ³     
³  Statement:       Licensed Materials - Property of IBM              ³     
³                   CA57 SupportPac                                   ³     
³                   (c) Copyright IBM Corp. 1996                      ³     
³                   All rights reserved.                              ³
³                                                                     ³
³                   U.S. Government Users Restricted Rights - use,    ³
³                   duplication or disclosure restricted by GSA       ³
³                   ADP Schedule Contract with IBM Corp.              ³
³                                                                     ³
³  Status:          Version 1 Release 0 Modification 0                ³
³  Last Modified:   18 December 1996                                  ³
³                                                                     ³
³  Author:          Adrian Easton                                     ³
³                                                                     ³
³   NOTES :-                                                          ³
³     DEPENDENCIES = CICS Client V2.0 for Windows NT or Windows 95    ³
³                    (with the OO beta available on the CICS          ³
³                    homepage)                                        ³
³                    Microsoft Visual C++ V4.0 or later               ³
³                    Microsoft Visual Basic V4.0 or later             ³
³     RESTRICTIONS = None                                             ³
³     MODULE TYPE  = InfoZip                                          ³
³     PROCESSOR    = x86                                              ³
³                                                                     ³
ÀÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÄÙ

To install the SupportPac, copy the file CA57.ZIP to a temporary
directory and uncompress using InfoZip's Unzip.  Use the default
options to ensure the subdirectories stored in the ZIP file are
re-created.

The sample application is in two parts, the sub-directory CCLBOSS
contains the Business object server, and is a Microsoft Visual C++
project containing an OLE Automation server.  The sub-directory
VBSAMPLE contains a Microsoft Visual Basic project that calls the OLE
Automation server.

The OLE Automation server needs to be registered.  To do this run the
CCLBOSS executable in the CCLBOSS\DEBUG directory.

Once the OLE Automation server has been registered, the sample can be
seen by running the BUILDBUF executable in the VBSAMPLE dierctory

The OLE Automation server can be 'opened' with the Microsoft Developer
Studio for C++, and the Visual Basic sample with the Microsoft Visual
Basic
 
The sample supplied doesn't communicate the a CICS server, it just
demonstrates how an OLE automation server can be used to construct a
Business object from a complex CICS COMMAREA, and how a CICS COMMAREA
can be constructed from a Business Object.

Calling the business object is demonstrated in the Microsoft Visual
Basic sample, but could be called from any language that supports OLE
Automation servers.  e.g.  Lotus Script, Microsoft Visual J++ etc.

Note:  If you wish to rebuild either of the projects, you will need to
ensure the project settings are as described in the product
documentation.

Building you own OLE Automation Server
--------------------------------------

This is described in some detail in the Microsoft Visual C++ package,
but as almost all the code in the CCLBOSS Sample is generated, the
following steps describe how to build a similar sample (based on
Microsoft Visual C++ V4.0)

1.   Open Microsoft Developer Studio (V4.0)

2.   From the File menu, choose New.

     The New dialog box appears.

3.   Select Project Workspace and click OK.

     The new Project Workspace dialog box appears.

4.   In the name box, type CclBOSS.

     AppWizard create a project directory with this name under the
     main (root) directory specified in the Location box.

5.   In the Type box make sure MFC AppWizard (exe) is specified.

6.   If necessary, use the Location box to specify a different root
     directory for the CclBOSS project files that AppWizard creates
     under the CclBOSS project directory.

     Depending on the directory you last worked in, you may want to
     change where the Location box currently points to. You can use
     the Browse button to navigate to an existing directory, or type
     a directory name directly into the Location box. AppWizard
     creates this directory if it doesnt exist.

7.   If any check boxes other than Win32 appear in the Platforms box,
     clear them.

8.   Click Create

     AppWizard creates the project directory, and the MFC
     AppWizard-Step 1 dialog box appears.

     For more information on the various options that appear in these
     dialog boxes, see Chapter 1, "Creating a new Application Using
     AppWizard," in the Microsoft Visual C++ Users Guide.

9.   In the AppWizard-Step 1 dialog box, select the Dialog based
     check button.

10.  Choose Next.

11.  In the Step 2 dialog box, select the OLE automation check box,
      and remove checks for About box and 3D controls

12.  Choose Next.

13.  In the Step 3 dialog box, accept the defaults and choose Next.

14.  In the Step 4 dialog box, accept the defaults and choose Finish.

     The New Project Information dialog box appears, summarising the
     settings and features AppWizard will generate for you when it
     creates your project.

15.  Click OK in the New Project information dialog box.

     AppWizard creates all the necessary files, and opens the CclBOSS
     project.

Now add the Object Wrappers for the Ccl.Buffer OLE class

1.   From the View menu, select ClassWizard.

     The MFC ClassWizard dialog box appears

2.   Choose the OLE Automation tab.

3.   Click Add Class.

4.   From the list, select From an OLE Typelib...

     The Import from OLE TypeLib dialog box is displayed

5.   Locate and select the CCLOECI.TLB file shipped with the CICS
     Client product.

6.   Click OK, on the Import from OLE TypeLib dialog box

     The Confirm Classes dialog box is displayed

7.   Deselect all classes except CclBuffer, Click OK.

8.   On the MFC ClassWizard dialog box, Click OK.

Add the Shipped sample class

1.   Copy the BOSSamp.h and BOSSamp.cpp files from the CICS Client
     samples directory to your project directory.

2.   Copy the CclBOSS.odl file from the CICS Client samples directory
     to your project directory - Replacing the one generated by the
     AppWizard.

3.   From the CclBOSS Project workspace, open the ClassWizard again

4.   Select Add Class

5.   From the list, select From a file

     The Import Class Information dialog box is displayed

6.   Type BOSSamp in the Class name box

7.   Click OK.

8.   On the MFC ClassWizard dialog box, Click OK.

9.   From the Insert menu, select Files into Project...

     The Insert Files into Project dialog box appears

10.  Select BOSSamp.cpp from the project directory.

11.  Click OK.

Set the Build environment

      Use the Build settings to add environment information, as for
      any other project that uses the CICS Client classes.

Build and Run the OLE server

      Build the project, run the EXE generated to register the OLE
      Server - you are now ready to use the OLE server.

