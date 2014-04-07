//-----------------------------------------------------------------------------
// fonttest.java
//
// Simple Java test application that demonstrates how to work with fonts,
// including querying a list of available fonts and displaying each of the
// fonts in various styles (i.e. plain, bold, italic and bold italic ).
//
// Author : Kelvin R Lawrence.     3rd-December-1996
//
// History:
//
//-----------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;

//---------------------------------------------------------------
// Class: fonttest
//
// Simple class that experiments with Java file dialogs.
//
//---------------------------------------------------------------

public class fonttest extends Applet
{
  final int POINT_SIZE = 24 ; // Java point size we will use.
  final int NUM_STYLES = 4 ;  // Number of available styles.
  int       num_fonts ;       // Number of available unique fonts.
  int       num_logical ;     // Number of logical font objects we will
                              // be creating; num_fonts * num_styles

  Font[]    fonts ;           // Array of font objects we will create.

  int[] styles = { Font.PLAIN
                 , Font.ITALIC
                 , Font.BOLD
                 , Font.BOLD+Font.ITALIC } ;

  //---------------------------------------------------------------
  // Query the names of all the fonts that Java supports.
  //
  // By default, according to Java docs, at a minimum, the following
  // font names should be returned (not necessarily in this order)
  //
  //   Helvetica
  //   Courier
  //   TimesRoman
  //   Dialog
  //   DialogInput
  //   ZapfDingbats
  //
  // The operating system that the Java applet is running on will
  // map these names to real fonts aavailable on that OS.
  // These real names should be available, after a font object has
  // been created by calling the getFamily() method.

  //---------------------------------------------------------------

  String[] faces = getToolkit().getFontList() ;

  //----------------------------------------------------------------
  // init()
  //
  // Init routine. Called whenever the applet is first loaded.
  //
  //----------------------------------------------------------------

  public void init()
  {
    int i,j,k ;
    
    System.out.println( "init() method called" ) ;


    //----------------------------------------------------------
    // Calculate how many fonts we found and how many total text
    // lines we are going to output.
    
    //----------------------------------------------------------
    num_fonts = faces.length ;

    num_logical = num_fonts * NUM_STYLES ;

    //----------------------------------------------------------------
    // Allocate space for each logical font objectand then create it.
    //
    // For each physical font that we found we will create four new
    // logical font objects (one for each style i.e. italic, bold etc.
    //----------------------------------------------------------------
    fonts = new Font[num_logical]; 
     
    System.out.println( "Java reported the following " + num_fonts + " font(s):" ) ;
    
    for( i = 0, k = 0; i < num_logical; i += NUM_STYLES, k++ )
    {
      for ( j = 0; j < NUM_STYLES; j++ )
      {
        fonts[i+j] = new Font( faces[k], styles[j], POINT_SIZE );
      }
    }             

    //-----------------------------------------------------------------
    // DEBUG: Write the names of all the fonts we found to the console.
    //-----------------------------------------------------------------
    for ( i=0; i< faces.length; i++) 
    {
      System.out.println( faces[i] ) ;
    }
  }

  //----------------------------------------------------------------
  // paint()
  //
  // Paint method, called whenever the application client area needs
  // repainting.
  //
  // Display each of the different fonts in each of the available
  // styles.
  //----------------------------------------------------------------
  public void paint( Graphics g )
  {
    int x = 10 ;
    int y = 30 ;
    int i,j ;

    for( i = 0, j = -1; i < num_logical; i++ )
    {
      if ( i % NUM_STYLES == 0 ) 
      {
        j++ ;
      }

      g.setFont( fonts[i] ) ;

      //--------------------------------------------------------
      // DEBUG: Dump out some info from the current font object.
      //--------------------------------------------------------
      System.out.println( "OS Family Name is " + fonts[i].getFamily() ) ;
      System.out.println( "Logical name is " + fonts[i].getName() ) ;
      System.out.println( fonts[i].toString() ) ;
      
      g.drawString(faces[j], x,y ) ;

      y += 20 ;
    }
  }



}
