//-----------------------------------------------------------------------------
// RedFilter.java
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
 * RedFilter.java - saves only the red channel of a color image.
 */
public class RedFilter extends RGBImageFilter {
   // Constructors
   /**
    * The canFilterIndexColorModel is set to false
    * because the pixel rgb and x y values must be used in filtering.
    */
   public RedFilter() {
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
      return rgb | 0xFFFF0000;
   }
}
