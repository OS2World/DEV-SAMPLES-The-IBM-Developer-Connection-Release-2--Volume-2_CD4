//-----------------------------------------------------------------------------
// SocketClientTest.java
//
// A simple example of how you can use the Socket class to open a socket
// for a port on a remote server and receive data from that server.
//
// This is the client side of a two part test. For the server side, see
// the file SocketServerTest.java.
// 
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   Socket
//   InputStream
//   IOException
//
// Author : Kelvin R Lawrence.     15-Jun-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.net.*;
import java.io.*;

//---------------------------------------------------------------
// Class: SocketClientTest
//
// Experiment with the Socket class.
//---------------------------------------------------------------
public class SocketClientTest
{
   Socket client ;

   InputStream in ;

   int data ;

   //------------------------------------------------------------------------
   // Constructor.
   //
   // In this particular case, nothing to do.
   //------------------------------------------------------------------------
   public SocketClientTest()
   {
   }

   //------------------------------------------------------------------------
   // runTests()
   //
   // Open a socket for the server. If we are successful in finding the
   // server and connecting on the specified port then try to receive
   // some data. 
   //------------------------------------------------------------------------
   public void runTests( int port )
   {
     try
     {
       System.out.println( "Trying to contact server" );

       client = new Socket( "lawrence2.austin.ibm.com", port ) ;

       System.out.println( "Server contacted" ) ;

       in = client.getInputStream() ;

       for( ;; )
       {
         data = in.read() ;

         System.out.println( "Received ==> " + data ) ;

         if ( data == -1 )
         {
           System.out.println( "End of stream detected" ) ;
           break;
         }
       }

       client.close() ;
     }
     catch ( IOException e )
     {
       System.out.println( "UnknownHostException" ) ;
       System.out.println( "Either the host or the specific port was not found" );
     }
   }

   //------------------------------------------------------------------------
   // main()
   //
   // Look to see if we received any command line arguments. If we did,
   // treat the first argument as the port number. Otherwise, we just
   // default the port number.
   //------------------------------------------------------------------------
   static public void main( String[] args )
   {

     int port = 6000 ;
 
     if ( args.length > 0 )
     {
       port = Integer.parseInt( args[0] ) ; 
     }

     SocketClientTest s = new SocketClientTest() ;

     s.runTests( port ) ;
   }
}
