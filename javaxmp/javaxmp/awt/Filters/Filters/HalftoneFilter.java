//-----------------------------------------------------------------------------
// HalftoneFilter
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
 * HalftoneFilter - halftones an image by graying every other pixel.
 */
public class HalftoneFilter extends RGBImageFilter {
   // Constructors
   /**
    * The canFilterIndexColorModel is set to false
    * because the pixel rgb and x y values must be used in filtering.
    */
   public HalftoneFilter() {
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
      if ( ( ( x % 2 ) ^ ( y % 2 ) ) == 1 ) 
          return 0xFF808080; // make opaque
      return rgb;
   }
}
