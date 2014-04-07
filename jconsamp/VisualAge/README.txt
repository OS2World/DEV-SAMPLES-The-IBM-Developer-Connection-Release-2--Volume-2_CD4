
What's In This Subdirectory?
----------------------------
05/13/98  03:08p        <DIR>          .
05/13/98  03:08p        <DIR>          ..
04/04/98  07:32a                98,686 Groupbox1.0.dat		<-- Groupbox bean, also in JC_Util.dat
05/13/98  02:49p               115,677 JC_AppsGuid_Start.		<-- Thin AppsGuide.java for lab start
05/13/98  03:01p               260,974 JC_CORBASolution.dat		<-- Lab Solution, CORBA version of AppsGuide.java
05/13/98  03:03p               260,974 JC_JDBCSolution.dat	      <-- Lab Solution, AppsGuide.java that speaks JDBC to DB2
05/13/98  02:55p               260,524 JC_RMI_Solution.dat        <-- Lab Solution, RMI version of AppsGuide.java
05/13/98  03:09p             1,293,375 JC_Util.dat			<-- Utils for the public
05/05/98  07:52a             1,889,049 JC_UtilSolution.dat		<-- Utils for SSYA
03/24/98  09:42a                 2,197 README.txt			<-- This README.txt file
04/04/98  07:33a               106,641 TickerTape1.0.dat		<-- TickerTape bean, also in JC_Util.dat



Tips and Hints
--------------

- You will need to import the JC_Utils.dat into the VisualAge for Java Repository and then add its
  packages to the Workbench.

- To run the CORBA sample, you will need to import the org.omg.CORBA Java package into the Workbench.  
  To do this, unzip the JDK1.2Beta\lib\classes.zip file and import the entire org subdirectory into 
  the Workbench.

- Pre-requisite for Running DB2/JDBC Samples: Import JDBC Drivers into IDE
	If you are using the DB2 for the samples, you have to import 
	the DB2 JDBC drivers into the VisualAge for Java Integrated 
	Development Environment (IDE). To do this:
	
		- In the DB2 SQLLIB\JAVA\directory, unzip the file db2java.zip. 
		  You must use the -d option if you are using pkunzip to ensure 
		  that the directories are created.
		  
		- In the IDE workbench, add project IBM DB2 JDBC Drivers. 
		  Select IBM DB2 JDBC Drivers project and from the File menu, 
		  select Import. 
		  
		- In the SmartGuide - Import Files and Directories, select 
		  Class Files. Click Next>>. Select Browse to locate the 
		  directory for the files to import. Select sqllib\java\Com 
		  directory and click OK. Click Finish. 
		  
	COM.ibm.db2.jdbc.app (for Java application) and COM.ibm.db2.jdbc.net 
	(for Java applets) drivers are imported into the IBM DB2 JDBC Drivers project.
	
	
Starting DB2 and DB2 Applet Server:
The applet uses port 8888 by default, but if you choose a different port number, 
you can match the setting once the applet is running (there is an editable entry 
field in the GUI that allows you to match the port number that the applet uses). 
To start DB2 and the applet server, do the following:

On OS/2, if your machine is not connected to a network, then your host file must have the 
TCP/IP address of your machine in it. To start DB2, enter:
	db2start
	db2jstrt 8888 
	
On Windows, if the applet is running on the DB2 server, you must also start the security server 
(db2ntsecserver), along with DB2 (db2) and the applet server (db2jstrt), as follows):

	net start db2
	net start db2ntsecserver
	db2jstrt a_portnumber

Alternatively, you can set up these services so that they start up automatically when 
you boot up. Go to the Control Panel. Double-click on Services to open the services 
window. Select DB2 from the list and click on the Start Up button. From the dialog 
that pops up, select the Automatic startup radio button and click OK. Repeat this for 
the DB2 Securities Server. With this setup, you need only start up the DB2 applet server. 

On your DB2 server, you may also need to open up a DB2 Command Window and connect to
your database of choice (for the labs, it will be APPSGUID).

You can test and run the applets entirely with the VisualAge for Java environment from this point; and,
if you'd like to test on a Web Server, you can export the applets' .CLASS files into the
public HTML subdirectory of your browser (be sure to maintain their relative subdirectories).




