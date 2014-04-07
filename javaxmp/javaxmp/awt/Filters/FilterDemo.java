//-----------------------------------------------------------------------------
// FilterDemo.java
//
// A simple example of running filters on an input image.
//
// This program requires:
//    Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//    FilteredImageSource
//    Graphics
//    MediaTracker
//    dynamic loading of classes
//
// Authors: 
//    Kelvin R. Lawrence
//    Dan O. Becker
//
// History:
//    02-Dec-1997 -KRL-  Created
//    26-Mar-1998 -DOB-  Updated to load image and filters from args.
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.image.* ;
import java.awt.event.* ;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * FilterDemo
 * <p>
 * A simple example of running filters on an imput image
 *
 * @author  Kelvin R. Lawrence
 * @author  Dan O. Becker
 * @version 26Mar1998    
 */
public class FilterDemo extends Frame
{
   //-------------------------------------------------------------------------
   // Constants
   // Immutable objects for this class
   //-------------------------------------------------------------------------

   //--------------------------------------------------------------------
   // main()
   // The entry point into the program.
   //--------------------------------------------------------------------
   public static void main( String [] args ) {
      // Construct a frame with the given title
      FilterDemo frame = new FilterDemo( "FilterDemo", args );
 
      // Add anonymous class to handle window exiting.
      frame.addWindowListener( 
         new WindowAdapter() {
            public void windowClosing( WindowEvent event ) { System.exit( 0 ); }
         }
      );

      // Center and show on SVGA screens.
      frame.setBounds( 50, 50, 700, 500 );
      frame.show();
   }

   //-------------------------------------------------------------------------
   // Constructors
   // Create instances of this class
   //-------------------------------------------------------------------------
   public FilterDemo( String s, String [] args ) 
   {
      // Initialize frame with given title.
      super(s);

      if (args.length == 0) {
         System.out.println( "Give arguments for image file and filters. For example:" );
         System.out.println( "   java FilterDemo ../../images/737-500.gif HalftoneImageFilter" );
         System.exit( 0 );
      } /* endif */

      parseArgs( args );
   }
 
   //----------------------------------------------------------------
   // parseArgs( String [] args )
   //
   // Process command line args.
   // The first arg is the input image
   // Args 2..N are the fitlers to run.
   //----------------------------------------------------------------
   public void parseArgs( String [] args ) { 
      if (args.length < 1) 
         throw new IllegalArgumentException( "input argument 1 should ne an input file name" );

      // Load input image.
      image = getImage( args[ 0 ] );
      
      // Run filters
      for (int i = 1; i < args.length; i++ ) {
         // Dynamically load image filter given by argument.
         System.out.println( "Loading filter " + args[ i ] );
         RGBImageFilter imageFilter = loadFilter( args[ i ] );

         // Run filter on image bits.
         if ( null != imageFilter ) {
            Image convertedImage = Toolkit.getDefaultToolkit().createImage( 
               new FilteredImageSource( image.getSource(), imageFilter ));
            image = convertedImage;
         } /* endif */
      } /* endfor */
      imageLoaded = true;
      repaint();
   }
   
   //-------------------------------------------------------------------------
   // getImage()
   // Loads and waits for an input image
   //-------------------------------------------------------------------------
   public Image getImage( String fileName )
   {
     Image image = getToolkit().getImage( fileName ) ;
 
     // Wait until the image has been loaded (yes this may be overkill).
     MediaTracker tracker = new MediaTracker( this ); 
     tracker.addImage( image, 1 ) ;
     try {
        tracker.waitForID ( 1 );
     } catch ( InterruptedException e ) { }
     // System.out.println( "getImage tracker wait completed" );

     // Check if an image was found.
     if ((image.getWidth( null ) < 1) || ( image.getHeight( null ) < 1 ))
        throw new IllegalArgumentException( "\n   problem with image width,height for argument " + fileName );

     return image;
   }

   //-------------------------------------------------------------------------
   // loadFilter()
   // Calls the system class loader to dynamically create an image filter.
   // Can throw exceptions if the class name is garbled or non-existant.
   //-------------------------------------------------------------------------
   public RGBImageFilter loadFilter( String className ) 
   {
      SimpleClassLoader loader = new SimpleClassLoader();
      try {
         Class loadedClass = loader.loadClass( className + ".class" );
         return (RGBImageFilter) loadedClass.newInstance();
      } catch (Exception e) {
         System.out.println( "Could not load filter class " + className );
         return null;
      } /* endcatch */
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
      if ( imageLoaded ) {
         Insets insets = getInsets();
         g.drawImage( image, insets.left, insets.top, this );
      } else {
         Dimension mySize = getSize();
         drawStringCenter( g, "Image conversion in process", 0, 0, mySize.width, mySize.height );
      }
   }
 
   //-------------------------------------------------------------------------
   // drawStringCenter
   // Draw a string centered in the given bounds.
   // Technically, we should factor in the descenders and the leading.
   //-------------------------------------------------------------------------
   public void drawStringCenter( Graphics g, String text, int x, int y, int width, int height )
   {
      // Uses the current font to center the text horizontally and vertically.
      FontMetrics fm = g.getFontMetrics( );
      int textwidth = fm.stringWidth( text );
      int textheight = fm.getHeight();

      int textX = x + ( width - textwidth ) / 2;
      int textY = y + ( height - textheight ) / 2;
      g.drawString( text, textX, textY );
   }
      
   //-------------------------------------------------------------------------
   // Fields
   // Objects that describe the state and information of this object.
   //-------------------------------------------------------------------------
   private boolean imageLoaded = false;
   private Image image;
}

/**
 * SimpleClassLoader
 * <p>
 * One of the simplest class loaders you can create.
 *
 * @author  Dan O. Becker
 * @version 26Mar1998    
 */
class SimpleClassLoader extends ClassLoader {
   // ClassLoader role
   public Class loadClass( String name, boolean resolve ) 
      throws ClassNotFoundException {
      // Attempt to find class in current table.
      Class newClass = (Class) classes.get( name );

      if ( newClass == null ) { 
         // Look for class in system. Eventually all loaded classes are system classes.
         try {
            newClass = findSystemClass( name );
            if (newClass != null) return newClass;
         } catch ( ClassNotFoundException e ) { } /* endcatch */

         try {
            byte[] buffer = bytesForClass( name );
            newClass = defineClass( null, buffer, 0, buffer.length );
            classes.put( name, newClass );
   
            if (resolve) 
               resolveClass( newClass );
         } catch ( IOException e ) {
            // Convert exception and rethrow.
            throw new ClassNotFoundException( e.toString() );
         } /* endcatch */
      } /* endif */
      return newClass;
   }

   protected byte[] bytesForClass( String name ) 
      throws IOException, ClassNotFoundException {
      // Read the class from a file.
      FileInputStream fis = new FileInputStream( name );
      int length = fis.available();
      if (0 == length) 
         throw new ClassNotFoundException( name );
      
      byte[] buffer = new byte[ length ];
      fis.read( buffer );
      return buffer;
   }

   // Private data
   private Hashtable classes = new Hashtable();
}
