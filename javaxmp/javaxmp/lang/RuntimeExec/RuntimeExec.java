//------------------------------------------------------------------------
// File Name:     RuntimeExec.java 
// Description:   Tests the exec method of the runtime class.
//                Executes operating system specific commands.
//
// Modification History
// Date   Developer Defect  Tag         Description
// ------ --------- ------- ----------- ----------------------------------
// 041498 DOBecker                      Created.
//------------------------------------------------------------------------
import java.io.*;

public class RuntimeExec {

   public static void main( String [] args ) {
      System.out.println( "Java version is " + System.getProperty( "java.version" ) + 
         " by " + System.getProperty( "java.vendor" ) + "." );
      System.out.println( "The operating system name is " + System.getProperty( "os.name" ) + 
         ", version " + System.getProperty( "os.version" ) + ".\n" );

      // Run each arg as a command.
      for ( int i = 0; i < args.length; i++ ) {
         try {
            // Some command sends information to stdout. Other commands send info to stderr.
            // For example, run this command with "net" or "attrib".
            // Does not work with inherent commands like dir and cd.
            Process p = Runtime.getRuntime().exec( args[ i ] );
   
            String line;
            System.out.println( "The contents of stdout is:" );
            DataInputStream dis = new DataInputStream( p.getInputStream() );
            while ( null != ( line = dis.readLine() )) 
               System.out.println( line );

            System.out.println( "The contents of stderr is:" );
            dis = new DataInputStream( p.getErrorStream() );
            while ( null != ( line = dis.readLine() )) 
               System.out.println( line );
   
            // Wait for pricess to finish and print exit value.
            p.waitFor();
            System.out.println( "The process exit value of " + args[ i ] + " is " + p.exitValue() + "." );
         } catch (Exception e ) {
            e.printStackTrace();
         } /* endcatch */
      } /* endfor */
   }
}
