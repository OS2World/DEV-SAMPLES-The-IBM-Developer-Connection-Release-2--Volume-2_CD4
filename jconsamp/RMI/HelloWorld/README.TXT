
javac -d c:\nix\java_connectors\rmi\HelloWorld HelloServant.java
javac -d c:\nix\java_connectors\rmi\HelloWorld HelloServantImpl.java
rmic -d c:\nix\java_connectors\rmi\HelloWorld HelloApp.HelloServantImpl


//: In another window/session
set classpath=c:\nix\java_connectors\rmi\HelloWorld;%classpath%
rmiregistry 1099


//: In another window/session
set classpath=c:\nix\java_connectors\rmi\HelloWorld;%classpath%
javac HelloServer.java
java HelloServer

//: Back in original build window/session
javac HelloClient.java
java HelloClient

Note:  Just in case....I removed ,RemoteException from throws clause of HelloServantImpl.java--it was redundant; 
and I changed package name from HelloApp to helloapp.
