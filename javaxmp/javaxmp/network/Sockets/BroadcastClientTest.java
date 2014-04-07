//-----------------------------------------------------------------------------
// BroadcastClientTest.java
//
// A simple example of how you can listen for datagram packets being 
// around the network (or sub-network).
//
// This is the client side of a two part test. For the server side, see
// the file BroadcastServerTest.java.
// 
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   DatagramSocket
//   DatagramPacket
//
// Author : Kelvin R Lawrence.     25-Jun-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.net.*;
import java.io.*;

//---------------------------------------------------------------
// Class: BroadcastClientTest
//
// Experiment with the ServerSocket class.
//---------------------------------------------------------------
public class BroadcastClientTest
{
   private DatagramSocket ds ;
   private DatagramPacket dp ;

   //------------------------------------------------------------------------
   // Constructor.
   //
   // In this particular case, nothing to do.
   //------------------------------------------------------------------------
   public BroadcastClientTest( )
   {
   }

   //------------------------------------------------------------------------
   // displayIPAddress()
   //
   // Useful debugging method.
   //------------------------------------------------------------------------
   private void displayIPAddress( byte [] addr )
   {
     System.out.println( BroadcastServerTest.convertIPToString( addr ) ) ;
   }

   //------------------------------------------------------------------------
   // runTests()
   //
   // Simple experiment that uses datagrams to broadcast to anyone who
   // might be out on our subnet listening for our broadcast.
   //
   //------------------------------------------------------------------------
   public void runTests( )
   {
     byte [] buffer = new byte[BroadcastServerTest.PACKET_SIZE] ;

     System.out.println( "Starting client..." ) ;

     dp = new DatagramPacket( buffer, BroadcastServerTest.PACKET_SIZE ) ;
     try
     {
       ds = new DatagramSocket( BroadcastServerTest.CLIENT_PORT ) ;

       System.out.println( "Client is running and listening for packets." ) ;
       for( ;; )
       {
         ds.receive(dp) ;

         System.out.println( "Received broadcast packet." ) ;
         System.out.println( "Packet size was " + dp.getLength() + "byte(s)" ) ;
       }
     }
     catch ( IOException e )
     {
       System.out.println( "IO Exception." ) ;
       return;
     }
   }

   //------------------------------------------------------------------------
   // main()
   //  
   // Main entry point for the BroadcastClientTest class.
   // 
   //------------------------------------------------------------------------
   static public void main( String[] args )
   {
     if ( args.length > 0 )
     {
     }

     BroadcastClientTest c = new BroadcastClientTest() ;

     c.runTests( ) ;
   }
}

