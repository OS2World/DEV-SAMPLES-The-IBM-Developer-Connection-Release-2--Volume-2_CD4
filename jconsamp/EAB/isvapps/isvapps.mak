
Isvapps.jar : Isvapps\IsvappsDataId.class Isvapps\Isvapps.class Isvapps\IsvappsBeanInfo.class Isvapps.mf
	cd Isvapps
	copy ..\Isvapps.mf
	jar -cfm Isvapps.jar  Isvapps.mf  *.class

Isvapps\IsvappsDataId.class : IsvappsDataId.java
	javac -d . IsvappsDataId.java

Isvapps\Isvapps.class : Isvapps.java
	javac -d . Isvapps.java

Isvapps\IsvappsBeanInfo.class : IsvappsBeanInfo.java
	javac -d . IsvappsBeanInfo.java

