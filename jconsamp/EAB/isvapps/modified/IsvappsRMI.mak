
IsvappsRMI.jar : IsvappsRMI\IsvappsDataId.class IsvappsRMI\IsvappsRMI.class IsvappsRMI\IsvappsRMIBeanInfo.class IsvappsRMI.mf
	cd IsvappsRMI
	copy ..\IsvappsRMI.mf
	jar -cfm IsvappsRMI.jar  IsvappsRMI.mf  *.class

IsvappsRMI\IsvappsDataId.class : IsvappsDataId.java
	javac -d . IsvappsDataId.java

IsvappsRMI\IsvappsRMI.class : IsvappsRMI.java
	javac -d . IsvappsRMI.java

IsvappsRMI\IsvappsRMIBeanInfo.class : IsvappsRMIBeanInfo.java
	javac -d . IsvappsRMIBeanInfo.java

