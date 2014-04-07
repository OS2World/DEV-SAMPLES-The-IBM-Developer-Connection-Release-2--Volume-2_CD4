//-----------------------------------------------------------------------------
// BarChart.java
//
// Simple example of drawing a bar chart using the AWT graphics class.
// Based on towers.java, this version takes command line parameters
// to attempt to provide useful bar charts.
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
 * BarChart
 * <p>
 * Simple example of drawing a bar chart using the AWT graphics class.
 * Based on towers.java, this version takes command line parameters
 * to attempt to provide useful bar charts.
 * <p>
 * Arguments are of the form title,value.
 * Example:
 *    java BarChart Bicycle,6 Swimming,1 Running,4 Transitions,0.5
 *
 * @author  Kelvin R. Lawrence
 * @author  Dan O. Becker
 * @version 26Mar1998    
 */
public class BarChart extends Frame 
{
   //-------------------------------------------------------------------------
   // Constants
   // Immutable objects for this class
   //-------------------------------------------------------------------------
   public final static int chartSpacing = 40;
   public final static int towerSpacing = 20;

   //--------------------------------------------------------------------
   // main()
   // The entry point into the program.
   // Arguments are of the form title,value.
   // Example:
   //    java BarChart Bike,6 Swim,1 Run,4 Transition,0.5
   //--------------------------------------------------------------------
   public static void main( String [] args ) {
      // Construct a frame with the given title
      BarChart frame = new BarChart( "BarChart Example", args );
 
      // Add anonymous class to handle window exiting.
      frame.addWindowListener( 
         new WindowAdapter() {
            public void windowClosing( WindowEvent event ) { System.exit( 0 ); }
         }
      );

      // Center and show on SVGA screens.
      frame.setBounds(  50, 50, 700, 500 );
      frame.show();
   }

   //-------------------------------------------------------------------------
   // Constructors
   // Create instances of this class
   //-------------------------------------------------------------------------
   public BarChart( String s, String [] args ) 
   {
      // Initialize frame with given title.
      super(s);

      if (args.length == 0) {
         System.out.println( "Give several arguments of the form title,value. For example:" );
         System.out.println( "   java BarChart Bike,6 Swim,1 Run,4 Transition,0.5" );
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
     drawBarChart( g, titles, values ) ;
   }
 
   //----------------------------------------------------------------
   // drawBarChart()
   //----------------------------------------------------------------
   public void drawBarChart( Graphics g, String [] titles, double [] values )
   { 
     int x,y,cx,cy ;
 
     Dimension dim = getSize();
     g.clearRect( 0, 0, dim.width, dim.height ) ;
 
     // Provide a small inset from the edges.
     // Must account for any frame insets.
     Insets insets = getInsets();
     x  = chartSpacing + insets.left;
     y = dim.height - chartSpacing - insets.bottom;
     cx = dim.width  - 2*chartSpacing - insets.right - insets.left;
     cy = dim.height - 2*chartSpacing - insets.bottom - insets.top;

     // Find tallest tower
     double tallest = Double.MIN_VALUE;
     for ( int i = 0; i < values.length; i++ ) 
        if ( values[ i ] > tallest) tallest = values[ i ];

     // Find horizontal width of a single tower.
     int width = ( cx - ( values.length * towerSpacing )) / values.length;
     for ( int i = 0; i < titles.length; i++ )
     {
        switch( i % 8 )
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

        int height = (int) ( cy * ( values[ i ] / tallest ));
        drawTower( g, x, y, width, height );
        drawTowerText( g, titles[ i ], x, y, width, height );
 
        x  += width + towerSpacing;
     }
   }
 
 
   //----------------------------------------------------------------
   // drawTower()
   // Draw a tower at the given x,y of the given heightwidth cx,cy
   //----------------------------------------------------------------
   public void drawTower( Graphics g, int x, int y, int cx, int cy )
   {
     System.out.println( "Drawing tower at x,y=" + x + "," + y + ", cx,cy=" + cx + "," + cy );
     Color basicColor = g.getColor();
     int[] p1x, p1y, p2x, p2y, p3x, p3y ;
 
     int xDelta = 2 * towerSpacing;
     int yDelta = 2 * towerSpacing;
 
     //------------------------------------------------
     // Calculate the coordinates of the front polygon.
     //------------------------------------------------
     p1x = new int[5];             p1y = new int[5];
                                 
     p1x[0] =  x ;                 p1y[0] =  y ;
     p1x[1] =  x + cx ;            p1y[1] =  y ;
     p1x[2] =  p1x[1] ;            p1y[2] =  p1y[1] - cy ;
     p1x[3] =  p1x[0] ;            p1y[3] =  p1y[2] ;   
     p1x[4] =  x ;                 p1y[4] =  y ;
 
     g.setColor( basicColor ) ;
     g.fillPolygon( p1x, p1y, 5 ) ;
     g.setColor( Color.black ) ;
     g.drawPolygon( p1x, p1y, 5 ) ;
 
     //-----------------------------------------------
     // Calculate the coordinates of the side polygon.
     //-----------------------------------------------
     p2x = new int[5] ;            p2y = new int[5] ;
 
     p2x[0] = p1x[1]  ;            p2y[0] = p1y[1]  ;
     p2x[1] = p1x[1] + xDelta  ;   p2y[1] = p1y[1] - yDelta  ;
     p2x[2] = p1x[2] + xDelta  ;   p2y[2] = p1y[2] - yDelta  ;
     p2x[3] = p1x[2]  ;            p2y[3] = p1y[2]  ;
     p2x[4] = p2x[0]  ;            p2y[4] = p2y[0]  ;
 
     Color darker = basicColor.darker();
     g.setColor( darker ) ;
     g.fillPolygon( p2x, p2y, 5 ) ;
     g.setColor( Color.black ) ;
     g.drawPolygon( p2x, p2y, 5 ) ;
 
     //----------------------------------------------
     // Calculate the coordinates of the top polygon.
     //----------------------------------------------
     p3x = new int[5] ;            p3y = new int[5] ;
 
     p3x[0] = p1x[3] ;             p3y[0] = p1y[3] ;
     p3x[1] = p1x[2] ;             p3y[1] = p1y[2] ;
     p3x[2] = p2x[2] ;             p3y[2] = p2y[2] ;
     p3x[3] = p1x[3] + xDelta ;    p3y[3] = p1y[3] - yDelta ;
     p3x[4] = p3x[0]  ;            p3y[4] = p3y[0]  ;
 
     // Brighter is invisible with saturated colors.
     Color brighter = basicColor.brighter();
     g.setColor( brighter ) ;
     g.fillPolygon( p3x, p3y, 5 ) ;
     g.setColor( Color.black ) ;
     g.drawPolygon( p3x, p3y, 5 ) ;
   }

   //----------------------------------------------------------------
   // drawTowerText()
   // Draw the text centered, above the tower
   //----------------------------------------------------------------
   public void drawTowerText( Graphics g, String text, int x, int y, int cx, int cy )
   {
      // Uses the current font to center the text horizontally and vertically.
      FontMetrics fm = g.getFontMetrics( );
      int textwidth = fm.stringWidth( text );
      int textheight = fm.getHeight();

      int textX = x + ( cx - textwidth ) / 2;
      int textY = y - cy - fm.getMaxDescent() - fm.getLeading();
      
      g.setColor( Color.black ) ;
      g.drawString( text, textX + 1, textY + 1);
      g.setColor( Color.white ) ;
      g.drawString( text, textX, textY );
   }

   //-------------------------------------------------------------------------
   // Fields
   // Objects that describe the state and information of this object.
   //-------------------------------------------------------------------------
   private String [] titles;
   private double [] values;
}
