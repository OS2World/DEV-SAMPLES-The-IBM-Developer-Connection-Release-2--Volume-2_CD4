//-----------------------------------------------------------------------------
// BroadcastServerTest.java
//
// A simple example of how you can broadcast to everyone on a specific
// subnet and see if anyone replies to you.
//
// This is the server side of a two part test. For the client side, see
// the file BroadcastClientTest.java.
// 
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   DatagramSocket
//   DatagramPacket
//
// Author : Kelvin R Lawrence.     19-Jun-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.net.*;
import java.io.*;

//---------------------------------------------------------------
// Class: BroadcastServerTest
//
// Experiment with the ServerSocket class.
//---------------------------------------------------------------
public class BroadcastServerTest
{
   private InetAddress    local ;
   private DatagramSocket ds ;
   private DatagramPacket dp ;

   static final int CLIENT_PORT    = 6000 ; // Port client must listen on.
   static final int BROADCAST_PORT = 6001 ; // Port we will send on.
   static final int PACKET_SIZE    = 10   ; // Number of bytes per packet.

   //----------------------------------------------------------
   // Hard coded for now, don't know how to query this in Java.
   //----------------------------------------------------------
   static final byte [] subnetMask = { (byte)255,(byte)255,(byte)254,(byte)0 } ;

   //------------------------------------------------------------------------
   // Constructor.
   //
   // In this particular case, nothing to do.
   //------------------------------------------------------------------------
   public BroadcastServerTest( )
   {
   }

   //------------------------------------------------------------------------
   // getBroadcastAddress()
   //
   // Using the address of the machine we are running on and the subnet mask
   // for our subnet, we can calculate the broadcast address for this 
   // specific subnet. The way this is done is to use the following
   // formula :
   //
   //    broadcast = my_address OR ( NOT subnet mask )
   //
   //------------------------------------------------------------------------
   static public byte [] getBroadcastAddress( byte [] thisMachine )
   {

     byte [] broadcast = new byte[4] ;

     System.out.println( "Address of local host folows" ) ;
     displayIPAddress( thisMachine ) ;
 
     broadcast[0] =  (byte)(thisMachine[0] | (~subnetMask[0]) ) ;
     broadcast[1] =  (byte)(thisMachine[1] | (~subnetMask[1]) ) ;
     broadcast[2] =  (byte)(thisMachine[2] | (~subnetMask[2]) ) ;
     broadcast[3] =  (byte)(thisMachine[3] | (~subnetMask[3]) ) ;

     System.out.println( "Calculated broadcast address follows" ) ;
     displayIPAddress( broadcast ) ;

     return( broadcast ) ;
   }
   //------------------------------------------------------------------------
   // convertIPToString()
   //
   // Take an IP address represented as four bytes and return the dot
   // separated IP address as a string.
   //
   // Note, this method assumes that the bytes are encoded high to low in
   // the byte array, as follows:
   //
   //    Assuming the IP address was, say, 9.53.127.17 then the bytes would
   //    be encoded as byte[0] = 9, byte[1] = 53 etc.
   //
   // Also note, that as all Java numbers are treated as signed values
   // we ensure that each byte is converted to its unsigned equivalent
   // before building the string.
   //------------------------------------------------------------------------
   static public String convertIPToString( byte [] theIP ) 
   {
     String tmp ;

     if ( theIP.length < 4 )
     {
       return null ;
     }
     else
     {
       int [] unsignedIP = new int[4] ;
  
       for ( int i=0; i<4; i++ )
       {
         unsignedIP[i] = ((int)theIP[i] & 0x000000FF ) ;
       }

       tmp = Integer.toString( unsignedIP[0] ) + "." 
           + Integer.toString( unsignedIP[1] ) + "."
           + Integer.toString( unsignedIP[2] ) + "."
           + Integer.toString( unsignedIP[3] ) ;
     }

     return( tmp ) ;
   }

   //------------------------------------------------------------------------
   // displayIPAddress()
   //
   // Useful debugging method.
   //------------------------------------------------------------------------
   static public void displayIPAddress( byte [] addr )
   {
     System.out.println( convertIPToString( addr ) ) ;
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
     byte [] dg = new byte[PACKET_SIZE] ;

     byte [] broadcastAddress ;

     String broadcastName ;

     InetAddress broadcastIP ;

     try
     {
       local = InetAddress.getLocalHost() ; 
       System.out.println( "Attempting to setup server" ) ;
       System.out.println( "Local host is " + local ) ;

       broadcastAddress = getBroadcastAddress( local.getAddress() ) ;

       broadcastName = convertIPToString( broadcastAddress ) ;

       broadcastIP = InetAddress.getByName( broadcastName ) ;

     }
     catch ( IOException e )
     {
       System.out.println( "IO Exception during initialization." ) ;
       return;
     }

     //------------------------------------------------------
     // Now create the datagram socket and the actual packet.
     //------------------------------------------------------
     try
     {
       ds = new DatagramSocket( BROADCAST_PORT ) ;

       dp = new DatagramPacket( dg, PACKET_SIZE, broadcastIP, CLIENT_PORT ) ;

       try
       {
         for ( ;; )
         {
           for ( int i=0; i<10; i++ )
           {
             System.out.println( "Sending broadcast packet." ) ;

             ds.send( dp );

             System.out.println( "Packet sent." ) ;
           }

           try 
           {
             Thread.sleep( 10000 ) ;
           }
           catch( InterruptedException e )
           {

           }
         }
       }
       catch( IOException e )
       {
         System.out.println( "IOException sending datagram packet." ) ;
       }

       ds.close() ;
     }
     catch ( SocketException se )
     {
       System.out.println( "SocketException while creating datagram." ) ;

     }
   }

   //------------------------------------------------------------------------
   // main()
   //  
   // Main entry point for the BroadcastServerTest class.
   // 
   //------------------------------------------------------------------------
   static public void main( String[] args )
   {
     if ( args.length > 0 )
     {
     }

     BroadcastServerTest s = new BroadcastServerTest() ;

     s.runTests( ) ;
   }
}
