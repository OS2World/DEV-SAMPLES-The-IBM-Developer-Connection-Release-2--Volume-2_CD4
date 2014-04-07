- Be sure that X:\SQLLIB\JAVA\DB2JAVA.zip is in the class path; otherwise, Servlet will fail with:

	"java.lang.ClassNotFoundException:  COM.ibm.db2.jdbc.app.DB2Driver"

  You'll have to use the ServletExpress Manager to add to classpath--if you 
  elect to "use local classpath" from Manager, be sure to add ALL the
  ServletExpress classes to the classpath; else, you'll have to reinstall.

- For that matter, here's a sample working classpath:
	d:\jdk1.1.4\lib\classes.zip;d:\ServletExpress\lib\servexp.jar;
		d:\ServletExpress\lib\jst.jar;d:\ServletExpress\lib\jsdk.jar;
		d:\ServletExpress\lib\x509v1.jar;d:\ServletExpress\lib;
		d:\ServletExpress\web\classes\ibmjbrt.jar;d:\ServletExpress\web\classes;
		d:\sf110;d:\ServletExpress\servlets;d:\sqllib\java\db2java.zip;

- Also, don't forget to start support utilities on server:

	C:\>net start db2
	The requested service has already been started.

	More help is available by typing NET HELPMSG 2182.


	C:\>net start db2ntsecserver
	The DB2 Security Server service is starting.
	The DB2 Security Server service was started successfully.


	C:\>db2jstrt
	Licensed Materials -- Property of IBM
	(c) Copyright International Business Machines Corporation, 1996, 1997.
    		All Rights Reserved.
	In Canada, (c) Copyright IBM Canada Ltd., 1996, 1997.
	US Government Users
	Restricted Rights -
	Use, uplication or disclosure restricted by
	GSA ADP Schedule Contract with IBM Corp.


	C:\>rmiregistry

************************************************************************************/




For lab, you can start AppsGuid in one of two ways:

	in browser, http://dbserver/servlet/AppsGuid     OR (note the missing e on AppsGuid)
	in browser, http://dbserver/TestAppsGuide.shtml     (note the s in shtml)
