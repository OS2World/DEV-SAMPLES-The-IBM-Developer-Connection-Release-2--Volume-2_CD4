- compile idl file with:
		idltojava  -fno-cpp Hello.idl

- set classpath (to jdk1.2xxx) and compile with:
		javac -d somepath Hello.java

- tnameserv.exe is in JDK1.2beta2\bin

- Follow these steps to execute HelloClient

	- in a window/session, execute:  tnameserv -ORBInitialPort 1050
	- in another window/session, execute:  java HelloServer -ORBInitialPort 1050
		//: wait for server to print "Wait for invocations from client"
	- in yet another window/session, execute:  java HelloClient -ORBInitialPort 1050
		//: be patient
	  or, if remote, execute:  java HelloClient -ORBInitialPort 1050 -ORBInitialHost hostname

   Note:  The default port is 900; and, to use a port below 1024, you need root access
		on a Unix machine and administrator priviledges on Wintel machines.

- To execute HelloApplet, you'll have to use the appletviewer for now.
