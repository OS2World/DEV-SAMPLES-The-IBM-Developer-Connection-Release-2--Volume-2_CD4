All : ConnectionManager.class AppsGuid.class  html

html : ProductInformation.html
	copy ProductInformation.html d:\www\html
	copy *.gif d:\www\html
	copy ProductInformation.html html

ConnectionManager.class : ConnectionManager.java
	javac  -d d:\ServletExpress\servlets ConnectionManager.java
	copy d:\ServletExpress\servlets\com\ibm\sdm\appsguid\ConnectionManager.class

AppsGuid.class : AppsGuid.java
	javac  -d d:\ServletExpress\servlets AppsGuid.java
	copy d:\ServletExpress\servlets\com\ibm\sdm\appsguid\AppsGuid.class
	copy d:\ServletExpress\servlets\com\ibm\sdm\appsguid\AppsGuid.class d:\servletexpress\servlets
	
