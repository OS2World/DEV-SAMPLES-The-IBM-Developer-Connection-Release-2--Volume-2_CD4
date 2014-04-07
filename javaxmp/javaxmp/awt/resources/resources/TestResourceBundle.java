//-----------------------------------------------------------------------------
// TestResourceBundle.java
//
// An example of retrieving information from a resource bundle.
//
// This program requires:
//    Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//    java.awt.Frame
//    java.util.Locale
//    java.util.ResourceBundle
//    java.util.PropertiesResourceBundle
//
// Authors: 
//    Dan O. Becker
//
// History:
//    26-Mar-1998 -DOB-  Created
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;;

/**
 * TestResourceBundle
 * <p>
 * An example of retrieving information from a resource bundle.
 * 
 *
 * @author  Dan O. Becker
 * @version 26Mar1998    
 */
public class TestResourceBundle extends Frame 
{
   //-------------------------------------------------------------------------
   // Constants
   // Immutable objects for this class
   //-------------------------------------------------------------------------
   public final static String baseName = "compass";

   //--------------------------------------------------------------------
   // main()
   // The entry point into the program.
   //--------------------------------------------------------------------
   public static void main( String [] args ) {
      // Construct a frame with the given title
      TestResourceBundle frame = new TestResourceBundle( "TestResourceBundle", args );
 
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
   // For arguments the program accepts the following
   //    java TestResourceBundle language country variant
   // If no args are provided, the program runs with the defaults.
   //-------------------------------------------------------------------------
   public TestResourceBundle( String s, String [] args ) 
   {
      // Initialize frame with given title.
      super(s);
  
      if ( args.length > 0 ) 
         language = args[ 0 ];
      if ( args.length > 1 ) 
         country = args[ 1 ];
      if ( args.length > 2 ) 
         variant = args[ 2 ];
      if (( null != language ) && ( null != country ) && ( null != variant )) 
         givenLocale = new Locale( language, country, variant );
      else if (( null != language ) && ( null != country ))
         givenLocale = new Locale( language, country );
      else if ( null != language ) 
         // This is potentially illegal, but will throw an exception if so.
         givenLocale = new Locale( language, language );

      if ( null != givenLocale ) 
         Locale.setDefault( givenLocale );
      System.out.println( "System default locale=" + Locale.getDefault() );

      //-------------------------------------------------------------------------
      // The ResourceBundle.getBundle( String name ) call relies on the
      // default locale to determine which properties file to load.
      // The method looks for an appropriate file in the following manner
      //    1. Search for the form basename_language_country_variant
      //    2. Search for the form basename_language_country
      //    3. Search for the form basename_language
      //    4. Search for the form basename
      //
      //  Warning: When loading resources from properties files,
      //           do not append ".properties" to the file name.
      //-------------------------------------------------------------------------
      ResourceBundle bundle = ResourceBundle.getBundle( baseName );

      setLayout( new BorderLayout() );
      add( "North", new Button( bundle.getString( "b1" ) ));
      add( "East", new Button( bundle.getString( "b2" ) ));
      add( "South", new Button( bundle.getString( "b3" ) ));
      add( "West", new Button( bundle.getString( "b4" ) ));

      paintText = bundle.getString( "s1" );
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
      if ( null != paintText) 
         drawStringCenter( g, paintText, 0, 0, getSize().width, getSize().height );
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
   String language = null;
   String country = null;
   String variant = null;

   Locale givenLocale = null;
   String paintText = null;
}
