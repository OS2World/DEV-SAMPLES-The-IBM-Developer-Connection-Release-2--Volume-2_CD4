- Be sure that X:\SQLLIB\JAVA\DB2JAVA.zip is in the class path; otherwise, Servlet will fail with:

	"java.lang.ClassNotFoundException:  COM.ibm.db2.jdbc.app.DB2Driver"

  You'll have to use the ServletExpress Manager to add to classpath--if you 
  elect to "use local classpath" from Manager, be sure to add ALL the
  ServletExpress classes to the classpath; else, you'll have to reinstall.

- You'll also need to add X:\nix\java_connectors to your path as the
  servlets are placed in a package

- Also, you'll want to load the AppsGuid servlet at server startup
  for a better demo
