- you'll need a Java 1.X enabled broswer for this
- build with simply, JAVAC xxxx.java (no package to worry about)
- copy CheckBookApplet.class, ErrorMsgFrame.class and TestCBA.html to X:\inetpub\wwwroot on ntsrv (or any host)
- copy SFCheckBookServlet.class to d:\servletexpress\servlets on ntsrv (or any host)
- demonstrates Servlet Express as gateway to SF logical network.
- add X:\sf110 to servlet express classpath and to system's classpath
- make sure SF is started on the host (ntsrv in this example).

- the deprecated API's in the CheckBookApplet will be fixed later

- Notes:
	- I took the original CheckBookApplet in the SF examples (TK1) and
	  split the SF logic out into a Java servlet.  I left the GUI stuff
	  in the CheckBookApplet and added just a little.

	- I had to create a rudamentary messaging system.  In each message, the first value
	  is the "action" and, depending on the action, specific parameters follow (kind
	  of like Windows API or Presentation Manager control messages).

	- when a client attaches, Servlet Express engine grabs a secondary thread from
	  a pool it creates--obviously, these threads are not SF threads and don't have
	  an associated WorkArea.  So, I assigned each client a SessionNumber and used
	  it to associate a separate WorkArea with each client in a HashTable.  I had
	  hoped to use Servlet Express SessionTracking instead; but, you can't add
          an object to SE's SessionTracking table unless that object is "serializable"
          and COM.ibm.sf.gf.Control is not serializable.  Bummer!

	
