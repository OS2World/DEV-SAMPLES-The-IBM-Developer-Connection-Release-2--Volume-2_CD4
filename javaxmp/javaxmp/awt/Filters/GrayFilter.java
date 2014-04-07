//-----------------------------------------------------------------------------
// GrayFilter.java
//
// An example of an RGBFilter
//
// This program requires:
//    Java JDK level 1.02 or higher.
//
// Demonstrates use of these classes/interfaces:
//    java.awt.image.RGBFilter
//
// Authors: 
//    Dan O. Becker
//
// History:
//    26-Mar-1998 -DOB-  Created
//-----------------------------------------------------------------------------
import java.awt.image.RGBImageFilter;

/**
 * GrayFilter.java - grays an image by averaging pixel brightness.
 */
public class GrayFilter extends RGBImageFilter {
   // Constructors
   /**
    * The canFilterIndexColorModel is set to false
    * because the pixel rgb and x y values must be used in filtering.
    */
   public GrayFilter() {
      canFilterIndexColorModel = false;
   }

   // RGBImageFilter Role
   /**
    * Called by ImageFilterSource to create a new set of pixels for an image.
    *
    * @param   x   the x position of the pixel
    * @param   y   the y position of the pixel
    * @param   rgb the rgb value of the pixel
    */
   public int filterRGB( int x, int y, int rgb ) {
      int red   = ( rgb & 0xff0000 ) >> 16;
      int green = ( rgb & 0xff00 ) >> 8;
      int blue  = ( rgb & 0xff );
      // calculate the right gray value based on perceived r,g and b intensities
      int gray  = ( red*3 + green*4 + blue*2 ) / 9;  
      return 0xFF000000 | (gray << 16) | (gray << 8) | gray;
   }
}
