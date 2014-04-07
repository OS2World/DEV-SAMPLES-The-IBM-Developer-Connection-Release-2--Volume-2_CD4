- You'll have to go to the DB2 Control Center, select DB2 and 
	choose Selected->Configure to add JDK11_PATH = X:\JDK1.1.4
- Add X:\SQLLIB\FUNCTION to PATH and copy .CLASS file containing 
	storedprocedure to it
- Net start DB2
- Matching the actual data types between the database, registering 
	of the stored proc, formal parameters of the sp, and setting/getting is a real BEAR!  Be careful.
- The DB2*SP files don't work; but, are good for reference for data types.
- When writing SPs, start slow and add parameters slowly.

Files you will need:

	- AppMgrSP.java:  stored procedures written in Java
	- RegisterAppMgr.java:  registers stored procedures with DB2
	- TestAppMgrSP:  simple test engine
	- DB2Stp.java:  sample from DB2 install
	- DB2spHMS.java:  non-working sample with good info
	- DB2RegisterSP.java:  	" " "
	- DB2TestSP.java: 		" " "
	
	