//-----------------------------------------------------------------------------
// AppletFrame.java
//
// A dummy applet frame that allows one to run applets as if they were applications.
// All calls to Applet are now supported by the applet.
//
// This program requires:
//    Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these classes/interfaces:
//    java.awt.Frame
//
// Authors: 
//    Dan O. Becker
//
// History:
//    20-Feb-1997 -DOB-  Created
//-----------------------------------------------------------------------------
import java.applet.Applet;
import java.applet.AudioClip;
import java.applet.AppletStub;
import java.applet.AppletContext;
import java.awt.BorderLayout;           
import java.awt.Dimension;
import java.awt.Event;           
import java.awt.Frame;           
import java.awt.Label;           
import java.awt.Toolkit;
import java.awt.Image;           
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Enumeration;          
import java.util.Vector;

/**
 * AppletFrame
 * <p>
 * A dummy applet frame that allows one to run applets
 * as if they were applications.
 * For instance, if you ran an applet by
 * <pre>
 *    applet run.html
 *    netscape run.html
 * </pre>
 * Where run.html contains a tag such as
 * <pre>
 *    <applet code=MyApp.class>
 *    You need a Java-enabled browser to see this applet.
 *    </applet> 
 * </pre>
 * You can now say from the command line.
 *    java MyApp.class
 * <p>
 * Ensure you add this method to your class
 * <pre>
 *    public static void main( String [] args ) {
 *       new AppletFrame( new MyApp(), "Title", args, 20, 40, 600, 400 ); // centered on VGA
 *    }
 * </pre>
 * <p>
 * You may also use the simplfied constructors to guess the size and name and args:
 * <pre>
 *    public static void main( String [] args ) {
 *       new AppletFrame( new MyApp() );
 *    }
 * </pre>
 * <p>
 * Applet parameters of the form
 * <pre>
 *    <param name=parsefile value="info.txt" >
 * </pre>
 * are supported in a "name=value" manner on the command line:
 * <pre>
 *    java MyApp parsefile=info.txt
 * </pre>
 * <p>
 * Warning, you cannot call the applet methods provided by this class in
 * your applet constructor. You must wait until init or start before you
 * begin using these methods.
 *
 * @author  Dan Becker, beckerdo@us.ibm.com
 * @version 2/20/97      
*/
public class AppletFrame extends Frame 
   implements AppletStub, AppletContext, Runnable {

   //-------------------------------------------------------------------------
   // Constructors
   // Create instances of this class
   //-------------------------------------------------------------------------
   /**
    * Creates an applet frame window that mimics the applet or appletviewer.
    *
    * @param applet A new instance of the class.
    * @param title  Title for Frame titlebar
    * @param args   Commandline arguments to the applet in the form "name=value"
    * @param x,y,w,h Frame position and height on the display
    * @see getParameter
    * @see java.applet.Applet
    */
   public AppletFrame( Applet applet, String title, String [] args, int x, int y, int w, int h ) {
      init( applet, title, args, x, y, w, h );
   }

   public AppletFrame( Applet applet ) {
      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

      init( applet, applet.getClass().getName(), null, 20, 20, screenSize.width-40, screenSize.height-40 );
   }

   public AppletFrame( Applet applet, String title, String [] args ) {
      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

      init( applet, title, args, 20, 20, screenSize.width-40, screenSize.height-40 );
   }

   private void init( Applet applet, String title, String [] args, 
      int x, int y, int w, int h ) {
      this.applet = applet;
      this.args = args;

      applet.setStub( this ); // Causes applet methods to call this.
      setTitle( title );

      // Lay out components.
      reshape( x, y, w, h ); // 1.02
      // setBounds( x, y, w, h ); // 1.1
      setLayout( new BorderLayout() );
      add( "Center", applet );
      add( "South", label = new Label( "" ) );

      // Start a separate thread (which calls the applet's init() and start()).
      (new Thread( this )).start();
   }

   //-------------------------------------------------------------------------
   // Event Handler Role
   //-------------------------------------------------------------------------
   public boolean handleEvent( Event evt ) {
      if (evt.id == Event.WINDOW_DESTROY) {
         applet.handleEvent( evt ); // Maybe the applet wants to do something.
         System.exit( 0 );
      }
      return super.handleEvent( evt );
   }

   //-------------------------------------------------------------------------
   // AppletStub Role
   //-------------------------------------------------------------------------
   public boolean isActive() { return true; }
   public URL getDocumentBase() { 
      String dir = System.getProperty( "user.dir" );
      String urlDir = dir.replace( File.separatorChar, '/' );
      try {
         return new URL( "file:" + urlDir + "/");
      } catch ( MalformedURLException e ) {}
      return null;
   }
   public URL getCodeBase() { 
      // This is the string returned from an applet in appletviewer.
      // "file:/D:/java/examples/jarloader/"
      String dir = System.getProperty( "user.dir" );
      String urlDir = dir.replace( File.separatorChar, '/' );
      try {
         return new URL( "file:" + urlDir + "/");
      } catch ( MalformedURLException e ) {}
      return null;
   }

   /**
    * Returns the string value of the requested parameter
    *
    * @param name The name/value key.
    * @returns The value of the requester parameter.
    */
   public String getParameter( String name ) { 
      // Search the args array of strings.
      if (args != null) {
         for ( int i = 0; i < args.length; i++ ) {
            int eq;
            if ( -1 != ( eq = args[ i ].indexOf( '=' ) )) {
               String key = args[ i ].substring( 0, eq );
               String val = args[ i ].substring( eq + 1, args[ i ].length() );
   
               // System.out.println( "Arg=" + args[ i ] + ", Key=" + key + ", Val=" + val );
               if ( key.equals( name ) ) 
                  return val;
            } /* endif */
         } /* endfor */
      } /* endif */
      System.out.println( "Warning: Applet requires parameter \"" + name + "\" and no argument was found." );
      System.out.println( "Use \"" + name + "=something\" to set this parameter on the command line." );
      return null;

      // Consider:
      // Let the calling program decide what is printed and what to do.
      // throw new IllegalArgumentException( 
      //    "Warning: Applet requires parameter \"" + name + "\" and no argument was found." +
      //    "Use \"" + name + "=something\" to set this parameter on the command line." );
   }
   public AppletContext getAppletContext() { return this; }
   public void appletResize( int width, int height ) {
      // Change the frames size by the applet's size change.
      Dimension frameSize = size(); // 1.02
      // Dimension frameSize = getSize(); // 1.1
      Dimension appletSize = applet.size(); // 1.02
      // Dimension appletSize = applet.getSize(); 1.1
      frameSize.width += width - appletSize.width;
      frameSize.height += height - appletSize.height;
      resize( frameSize ); // 1.02
      // setSize( frameSize ); // 1.1
   }

   //-------------------------------------------------------------------------
   // AppletContext Role
   //-------------------------------------------------------------------------
   public Applet getApplet( String name ) { 
      // Returns this applet.
      if ( name.equals( applet.getClass().getName() ) )
          return applet;
      return null;
   }
   public Enumeration getApplets( ) { 
      // Creates a new vector of this.
      Vector v = new Vector();
      v.addElement( applet );
      return v.elements();
   }
   public AudioClip getAudioClip( URL url ) { 
      return new sun.applet.AppletAudioClip( url );
   }
   public Image getImage( URL url ) { 
      try {
         ImageProducer producer = (ImageProducer) url.getContent();
         return Toolkit.getDefaultToolkit().createImage( producer );
      } catch ( IOException e ) {}
      return null;
   }
   public void showDocument( URL url ) {}
   public void showDocument( URL url, String target ) {}
   public void showStatus( String status ) { label.setText( status ); }

   //-------------------------------------------------------------------------
   // Runnable role
   //-------------------------------------------------------------------------
   public void run() {
      String name = getTitle();
      // BUG: Must call show before init or else Applet.createImage,
      // which depends on a peer, returns null. The peer is created
      // as a result of a call to show.
      show(); 

      showStatus( name + " initializing..." );
      applet.init();
      // Called by the browser or applet viewer to inform this applet that 
      // it has been loaded into the system. It is always called before the
      // first time that the start method is called. 
      // A subclass of Applet should override this method if it has 
      // initialization to perform. For example, an applet with threads would
      // use the init method to create the threads and the destroy method to
      // kill them. 
      // The implementation of this method provided by the Applet class does nothing.
      showStatus( name + " starting..." );
      applet.start();
      // Called by the browser or applet viewer to inform this applet that it
      // should start its execution. It is called after the init method and 
      // each time the applet is revisited in a Web page. 
      // A subclass of Applet should override this method if it has any 
      // operation that it wants to perform each time the Web page containing
      // it is visited. For example, an applet with animation might want to
      // use the start method to resume animation, and the stop method to 
      // suspend the animation. 
      // The implementation of this method provided by the Applet class does nothing. 
      showStatus( name + " started." );

      // for some reason, applets not laying out correctly. Call show to layout.
      show();
   }

   //-------------------------------------------------------------------------
   // Private instances
   //-------------------------------------------------------------------------
   private Applet applet;
   private Label label;
   private String [] args;
}
