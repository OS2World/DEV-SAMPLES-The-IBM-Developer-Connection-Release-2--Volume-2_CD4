//-----------------------------------------------------------------------------
// AppletFrameTest.java
//
// This class exercises the AppletFrame and demonstrates an Applet that is
// also an application.
//
// This program requires:
//    Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes/interfaces:
//    java.applet.Applet
//    AppletFrame
//
// Authors: 
//    Dan O. Becker
//
// History:
//    28-Mar-1998 -DOB-  Created
//-----------------------------------------------------------------------------
import java.applet.Applet;
import java.awt.* ;
import java.awt.event.* ;
import java.util.StringTokenizer;

/**
 * AppletFrameTest.java
 * <p>
 * This class exercises the AppletFrame and demonstrates an Applet that is
 * also an application.
 * <p>
 * To demonstrate this program as an Applet, start the html file with
 * appletviewer or a browser.
 * <p>
 * To demonstrate this program as an application, start the program from the
 * environment by typing java AppletFrameTest
 * <p>
 *
 * @author  Dan O. Becker
 * @version 28Mar1998    
 */
public class AppletFrameTest extends Applet
{

   //--------------------------------------------------------------------
   // main()
   // This is the only additional code you must provide to
   // support this applet as an application with the AppletFrame
   //--------------------------------------------------------------------
   public static void main( String [] args ) {
      // Centered on SVGA
      new AppletFrame( new AppletFrameTest(), "AppletFrameTest", args, 50, 50, 700, 500 ); 
   }

   //-------------------------------------------------------------------------
   // Applet role 
   // Methods that define this program's role as an Applet
   //-------------------------------------------------------------------------
   public void init( )
   {
      // Normally the browser or appletviewer support the Applet calls.
      // In application mode, the AppletFrame supports all applet calls.

      // Example of getCodeBase call.
      System.out.println( "AppletFrameTest getCodeBase=" + getCodeBase() );

      // Example of parameter passing.
      String colorString = getParameter( "color" );
      if (( null != colorString ) && ( colorString.length() > 0 )) {
         StringTokenizer st = new StringTokenizer( colorString, "," );
         backgroundColor = new Color(
            Integer.parseInt( st.nextToken() ),
            Integer.parseInt( st.nextToken() ),
            Integer.parseInt( st.nextToken() )
         );
      } 
   }

   //-------------------------------------------------------------------------
   // Component role
   // Methods that define this program's role as a Component
   //-------------------------------------------------------------------------
   /**
    * Called by system when  a component needs to paint its contents.
    * @param graphics The graphics object in which to draw.
    */
   public void paint( Graphics g )
   {
      Dimension d = size();
      g.setColor( backgroundColor );
      g.fillRect( 0, 0, d.width, d.height );

      // Example of setting the applet status
      showStatus( "Background painted" );
   }

   //-------------------------------------------------------------------------
   // Fields
   // Objects that define this program's state
   //-------------------------------------------------------------------------
   Color backgroundColor = Color.gray;
}
