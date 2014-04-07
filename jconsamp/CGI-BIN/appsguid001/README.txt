- Before starting, make sure the file AMFuncs.dll is in the PATH

- From a DB2 Command Window:
	- start database manager
	- connect to APPSGUID (database name)
	   (D:\Java_Connectors\CGI-BIN\appsguid001>db2 connect to AppsGuid user db2admin)
	- delete from ISVAPPS (table name)
	- nmake APPMGR.MF (to rebuild application--make sure dbm is started and that you have already connected)
	

- To configure remote db on client machine
	- Start DB2 Control Center
	- Button 2 on Systems and add remote system info
	- Button 2 on Instances and add DB instance info
	- Button 2 on Database and add specific database


- Files
	- AppMgr.c:  client application--uses DBCalls.sqc
	- DBCalls.sqc:  manages db access and calls--one of its methods calls a stored procedure
	- AMFuncs.sqc:  stored procedures
	- AppsGuid.h:  common header file
	- AppMgr.mf:  makefile