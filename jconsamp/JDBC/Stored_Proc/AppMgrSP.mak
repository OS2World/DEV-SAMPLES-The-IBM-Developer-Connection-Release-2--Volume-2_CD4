all : RegisterAppMgrSP.class AppMgrSP.class TestAppMgrSP.class

AppMgrSP.class : AppMgrSP.java
	javac AppMgrSP.java
	copy AppMgrSP.class d:\sqllib\function
	java RegisterAppMgrSP
	
RegisterAppMgrSP.class : RegisterAppMgrSP.java
	javac RegisterAppMgrSP.java
	
TestAppMgrSP.class : TestAppMgrSP.java
	javac TestAppMgrSP.java