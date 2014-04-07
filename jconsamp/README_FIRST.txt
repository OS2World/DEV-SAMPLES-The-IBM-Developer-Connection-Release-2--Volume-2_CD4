(c) Copyright IBM Corp. 1997  All rights reserved.

These sample programs are owned by International Business Machines
Corporation or one of its subsidiaries ("IBM") and are copyrighted and
licensed, not sold.

You may copy, modify, and distribute these sample programs in any
form without payment to IBM,  for any purpose including developing,
using, marketing or distributing programs that include or are
derivative works of the sample programs.

The sample programs are provided to you on an "AS IS" basis, without
warranty of any kind.  IBM HEREBY EXPRESSLY DISCLAIMS ALL WARRANTIES,
EITHER EXPRESS OR IMPLIED, INCLUDING , BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
Some jurisdictions do not allow for the exclusion or limitation of
implied warranties, so the above limitations or exclusions may not
apply to you.  IBM shall not be liable for any damages you suffer
as a result of using, modifying or distributing the sample programs
or their derivatives.

Each copy of any portion of this/these sample program(s) or any
derivative work, must include a the above copyright notice and warranty.

The sample code in these subdirectories has been written to supplement a 
VisualAge for Java and Java Connectors workshop and seminar.  The following subdirectories contain:


	CGI-BIN:  CGI-BIN version of AppsGuide.exe and a few smaller samples.  AppsGuide.exe uses
			DB2 stored procedures written in C (stored procedures can be found below)
	CORBA:  CORBA version of AppsGuide.java and a HelloWorld sample.
	DBCALLS:  Contains DB2 stored procedures.
	DB_BACKUP:  Contains backup of APPSGUID DB2 database used in samples.
	EAB:  Contain custom DB2 enterprise access beans (ISVAppsMgr.jar and ISVAppsMgrRMI.jar)
	JDBC:  Contains Java Stored Procedures and utilites to register them.
	RMI:  Contains RMI version of AppsGuide.java, a HelloWorld sample, and a simple "Hotel Management
              System" framework built using RMI and JDBC calls.
	SERVLETS:  Sample servlets, an AppsGuide.java version which uses servlets, and a San Francisco
		sample that uses servets.
	VISUALAGE:  Several versions of the COM.ibm.sdm.appsguide package that can be imported into
		the VisualAge for Java repository.



Software, Tools and SDK's You Will Need to Obtain:
--------------------------------------------------

Software
	Windows NT Server 4.0
	Any JDK1.1 enabled Web browser, such as:
		Netscape Communicator 4.05 or earlier version with JDK 1.1 AWT patch (http://developer.netscape.com) OR
		Microsoft Internet Explorer 4.0
	Universal DB2 (http://www.software.ibm.com)
	Servlet Express (http://www.ibm.com/java/servexp
	Any Web Server that Servlet Express supports (eg., Domino Go Webserver 4.6)
	Any C/C++ compiler (for CGI-BIN demos)
	VisualAge for Java 1.0 or higher, Enterprise Edition (http://www.software.ibm.com/ad/vajava)

Utilities
	idltojava.exe (http://java.sun.com)
      nmake.exe (only need if you wish to use the supplied MAKE files--http://www.microsoft.com)

SDK's
	JDK1.1.4 or higher
	JDK1.2Beta2 or higher (http://java.sun.com)


