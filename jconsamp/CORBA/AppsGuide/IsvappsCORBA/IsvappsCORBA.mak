IsvappsCORBA.jar :  IsvappsManager.class  IsvappsManagerHelper.class IsvappsManagerHolder.class _IsvappsManagerImplBase.class  _IsvappsManagerStub.class IsvappsCORBA.mf
	jar -cfm IsvappsCORBA.jar  IsvappsCORBA.mf  *.class



IsvappsManager.class : IsvappsManager.java
	javac -d ..\ IsvappsManager.java

IsvappsManagerHelper.class : IsvappsManagerHelper.java
	javac -d ..\ IsvappsManagerHelper.java

IsvappsManagerHolder.class : IsvappsManagerHolder.java
	javac -d ..\ IsvappsManagerHolder.java

_IsvappsManagerImplBase.class : _IsvappsManagerImplBase.java
	javac -d ..\ _IsvappsManagerImplBase.java

_IsvappsManagerStub.class : _IsvappsManagerStub.java
	javac -d ..\ _IsvappsManagerStub.java
