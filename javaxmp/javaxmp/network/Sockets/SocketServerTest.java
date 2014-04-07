//-----------------------------------------------------------------------------
// SocketServerTest.java
//
// A simple example of how you can use the ServerSocket class to set up
// a simple server on a specific port that remote clients can connect to.
//
// This is the server side of a two part test. For the client side, see
// the file SocketClientTest.java.
// 
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   ServerSocket
//   Socket
//   OutputStream
//   InetAddress
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
// Class: SocketServerTest
//
// Experiment with the ServerSocket class.
//---------------------------------------------------------------
public class SocketServerTest
{
   ServerSocket srv ;
   Socket client ;
   InetAddress clientAddress ;

   int clientPort ;

   OutputStream out ;

   //------------------------------------------------------------------------
   // Constructor.
   //
   // In this particular case, nothing to do.
   //------------------------------------------------------------------------
   public SocketServerTest()
   {
   }

   public void runTests( int port)
   {
     try
     {
       System.out.println( "Attempting to setup server" ) ;
       System.out.println( "Local host is " + InetAddress.getLocalHost() ) ;
       srv = new ServerSocket( port ) ;

       if ( srv == null )
       {
         System.out.println( "ServerSocket returned null" ) ;   
       }
       else
       {
         System.out.println( srv.toString() ) ;
         System.out.println( "Waiting for someone to connect to me..." ) ;
       }
     }
     catch ( IOException e )
     {
       System.out.println( "IO Exception during ServerSocket() creation." ) ;
     }

     try
     {
       if ( srv == null )
       {
         System.out.println( "ServerSocket is null" ) ;   
       }
       else
       {
         client = srv.accept() ;
         System.out.println( "A client has contacted us" ) ;
         clientAddress = client.getInetAddress() ;
         clientPort = client.getPort() ;
         System.out.println( "The client is " + clientAddress + ":" + clientPort ) ;

         out = client.getOutputStream() ;

         System.out.println( "Sending data to the client..." ) ;
         out.write(54) ;

         out.close() ;
         client.close() ;
         srv.close() ;
       }
     }
     catch ( IOException e )
     {
       System.out.println( "IO Exception has ocurred" ) ;
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

     SocketServerTest s = new SocketServerTest() ;

     s.runTests( port ) ;
   }
}
