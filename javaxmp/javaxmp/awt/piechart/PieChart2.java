//-----------------------------------------------------------------------------
// PieChart2.java
//
// Simple example of drawing a pie chart using the AWT graphics class.
// Based on piechart.java, this version takes command line parameters
// to attempt to provide useful pie charts.
//
// This version requires Java JDK level 1.1 or higher.
//    (Uses the 1.1 event model.)
//
// Demonstrates use of these classes:
//
//   Graphics
//   java.util.StringTokenizer
//
// Authors: 
//    Kelvin R. Lawrence
//    Dan O. Becker
//
// History:
//    13-Oct-1996 -KRL-  Created.
//    26-Mar-1998 -DOB-  Updated to take command line inputs.
//-----------------------------------------------------------------------------

import java.applet.* ;
import java.awt.* ;
import java.awt.event.*;
import java.util.StringTokenizer;

/**
 * PieChart2
 * <p>
 * Simple example of drawing a pie chart using the AWT graphics class.
 * Based on piechart.java, this version takes command line parameters
 * to attempt to provide useful pie charts.
 * <p>
 * Arguments are of the form title,value.
 * Example:
 *    java PieChart2 Bicycle,6 Swimming,1 Running,4 Transitions,0.5
 *
 * @author  Kelvin R. Lawrence
 * @author  Dan O. Becker
 * @version 24Mar1998    
 */
public class PieChart2 extends Frame 
{
   //-------------------------------------------------------------------------
   // Constants
   // Immutable objects for this class
   //-------------------------------------------------------------------------
   public final static double DEGREESTORADIANS = 2 * Math.PI / 360.0;

   //--------------------------------------------------------------------
   // main()
   // The entry point into the program.
   // Arguments are of the form title,value.
   // Example:
   //    java PieChart2 Bike,6 Swim,1 Run,4 Transition,0.5
   //--------------------------------------------------------------------
   public static void main( String [] args ) {
      // Construct a frame with the given title
      PieChart2 frame = new PieChart2( "PieChart Example", args );
 
      // Add anonymous class to handle window exiting.
      frame.addWindowListener( 
         new WindowAdapter() {
            public void windowClosing( WindowEvent event ) { System.exit( 0 ); }
         }
      );

      // Center and show on SVGA screens.
      frame.setBounds( 150, 50, 500, 500 );
      frame.show();
   }

   //-------------------------------------------------------------------------
   // Constructors
   // Create instances of this class
   //-------------------------------------------------------------------------
   public PieChart2( String s, String [] args ) 
   {
      // Initialize frame with given title.
      super(s);

      if (args.length == 0) {
         System.out.println( "Give several arguments of the form title,value. For example:" );
         System.out.println( "   java PieChart2 Bike,6 Swim,1 Run,4 Transition,0.5" );
         System.exit( 0 );
      } /* endif */

      parseArgs( args );
   }
 
   //----------------------------------------------------------------
   // parseArgs( String [] args )
   //
   // Convert strings of the form name,value into two arrays
   // String [] titles and double [] values
   //----------------------------------------------------------------
   public void parseArgs( String [] args ) { 
      titles = new String [ args.length ];
      values = new double [ args.length ];
      for (int i = 0; i < args.length; i++ ) {
         StringTokenizer st = new StringTokenizer( args[ i ], ","  );
         if ( st.countTokens() != 2 ) 
            throw new IllegalArgumentException( "token " + i + " \"" + args[ i ] + "\" must be of the form string,double" );

         titles[ i ] = st.nextToken();
         values[ i ] = Double.valueOf( st.nextToken() ).doubleValue();
         if ( values[ i ] < 0.0 ) 
            throw new IllegalArgumentException( "token " + i + " \"" + args[ i ] + "\" must have nonnegative argument" );
      } /* endfor */
   }

   //----------------------------------------------------------------
   // paint()
   //
   // Paint method, called whenever the application client area needs
   // repainting.
   //----------------------------------------------------------------
   public void paint( Graphics g )
   {
     drawPieChart( g, titles, values ) ;
   }
 
   //----------------------------------------------------------------
   // drawPieChart()
   //
   // Draw a pie chart with a specified number of slices and scale
   // it to fit into the client window. A side effect of the scaling
   // is that if the window is not roughly square the pie may well
   // apear elliptical !
   //----------------------------------------------------------------
   public void drawPieChart( Graphics g, String [] titles, double [] values )
   {
     Dimension dim = getSize();
     int start =  0 ;
     int sweep ;
     int x, y, cx, cy ;
     int centerX, centerY;
     double totalValue;
     double radius;
 
     // Provide a small inset from the edges.
     // Must account for any frame insets.
     Insets insets = getInsets();
     x  = 10 + insets.left;
     y  = 10 + insets.top; 
     cx = dim.width  - 20 - insets.right - insets.left;
     cy = dim.height - 20 - insets.bottom - insets.top;
     centerX = x + cx / 2;
     centerY = y + cy / 2;
     radius = cx / 2;
 
     totalValue = 0.0;
     for( int i = 0; i < values.length; i++ )
        totalValue += values[ i ];

     for( int i = 0; i < values.length; i++ )
     {
       switch( i )
       {
         case 0: g.setColor( Color.blue )    ; break ;
         case 1: g.setColor( Color.red )     ; break ;
         case 2: g.setColor( Color.green )   ; break ;
         case 3: g.setColor( Color.yellow )  ; break ;
         case 4: g.setColor( Color.gray )    ; break ;
         case 5: g.setColor( Color.orange )  ; break ;
         case 6: g.setColor( Color.magenta ) ; break ;
         case 7: g.setColor( Color.cyan )    ; break ;
       }
 
       sweep = (int) (360.0 / ( totalValue / values[ i ] ));
       if ( i == ( values.length - 1 ) ) sweep = 360 - start; // Usually some rounding error
       System.out.println( "Slice " + i + " " + titles[ i ] + " start,sweep=" + start + "," + sweep );

       // fillArc start and sweep are in degrees. 0 degree start is a 3 o'clock.
       g.fillArc( x, y, cx, cy, start, sweep ) ;
 
       // Draw text in the pie slice.
       double textAngle = DEGREESTORADIANS * ( start + sweep / 2 );
       int textX = centerX + (int) ( radius * Math.cos( textAngle ) ) / 2;
       int textY = centerY - (int) ( radius * Math.sin( textAngle ) ) / 2;
       
       g.setColor( Color.black ) ;
       g.drawString( titles[ i ], textX + 1, textY + 1);
       g.setColor( Color.white ) ;
       g.drawString( titles[ i ], textX, textY );
 
       start += sweep ;
     }

     // Draw a line border around the window
     g.setColor( g.getColor().black ) ;
     g.drawRect( x, y, cx, cy ) ;
   }

   //-------------------------------------------------------------------------
   // Fields
   // Objects that describe the state and information of this object.
   //-------------------------------------------------------------------------
   private String [] titles;
   private double [] values;
}
