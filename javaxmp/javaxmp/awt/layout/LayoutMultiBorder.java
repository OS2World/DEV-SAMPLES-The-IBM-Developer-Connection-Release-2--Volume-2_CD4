//-----------------------------------------------------------------------------
// LayoutMultiBorder.java
//
// An example of writing your own LayoutManager
//
// This program requires:
//    Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//    java.awt.LayoutManager
//
// Authors: 
//    Dan O. Becker
//
// History:
//    21-Apr-1997 -DOB-  Created
//-----------------------------------------------------------------------------
import java.awt.LayoutManager;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Vector;

/** LayoutMultiBorder
 * <p>
 * A layout manager like BorderLayout, except that it
 * allows multiple objects on its border. The border
 * objects are set to their minimum size. The center
 * object is expanded to its maximum size.
 * <p>
 * Here is an example layout with many components:
 * <pre>
 *     ----------------------------------
 *     |             north 2            |
 *     ----------------------------------
 *     |             north 1            |
 *     ----------------------------------
 *     | | |                        | | |
 *     |w|w|                        |e|e|
 *     |e|e|                        |a|a|
 *     |s|s|         center         |s|s|
 *     |t|t|                        |t|t|
 *     | | |                        | | |
 *     |2|1|                        |1|2|
 *     | | |                        | | |
 *     ----------------------------------
 *     |             south 1            |
 *     ----------------------------------
 *     |             south 2            |
 *     ----------------------------------
 * </pre>
 * <p>
 * The preferred height is the sum of the insets, the north components,
 * the south components and the maximum height of the east, west, and center
 * components. The center and east and west components may be stretched
 * vertically to fit a taller window. The north and south components are
 * never stretched vertically.
 * <p>
 * The preferred width is the sum of the insets, the east, west, and center
 * components. The center and north and south may be stretched horizontally 
 * to fit a wider window. The east and west components are never stretched
 * horizontally.
 * <p>
 * The layout also supports setInsets and getInsets which places additional
 * space around the above layout.
 *
 * @author Dan Becker - becker@beckerdo.io.com
 * @version 04/21/97
 */
public class LayoutMultiBorder implements LayoutManager, Cloneable {
   //-------------------------------------------------------------------------
   // Constants
   // Immutable objects for this class
   //-------------------------------------------------------------------------

   //-------------------------------------------------------------------------
   // Constructors
   // Create instances of this class
   //-------------------------------------------------------------------------
   /**
    * Constructs a LayoutMultiBorder
    */
   public LayoutMultiBorder() {
      super();
      // System.out.println( this.getClass().getName() + ".cons" );

      minSize = new Dimension();
      prefSize = new Dimension();

      north = new Vector();
      south = new Vector();
      east =  new Vector();
      west =  new Vector();
   }

   public LayoutMultiBorder( Insets insets ) {
      this();
      setInsets( insets );
   }

   //-------------------------------------------------------------------------
   // LayoutManager role
   // Implements this classes role as a LayoutManager
   //-------------------------------------------------------------------------
   /**
    * Add a component to the layout. Called by Panel.
    * Supports BorderLayout names "Center", "North", "South", "East", "West".
    * Causes a repaint.
    *
    * @see LayoutManager
    * @see BorderLayout
    * @see Panel
    */
   public void addLayoutComponent( String name, Component comp ) {
      // System.out.println( this.getClass().getName() + ".addLayoutComponent " + name + ", comp=" + comp);

      if ( name.equalsIgnoreCase( "center" ) ) {
         if ( center == null ) 
            center = comp;
         else 
            throw new IllegalArgumentException( this.getClass().getName() + " attempted to add 2 center components" );
      } else if ( name.equalsIgnoreCase( "north" ) ) {
         north.addElement( comp );
      } else if ( name.equalsIgnoreCase( "south" ) ) {
         south.addElement( comp );
      } else if ( name.equalsIgnoreCase( "east" ) ) {
         east.addElement( comp );
      } else if ( name.equalsIgnoreCase( "west" ) ) {
         west.addElement( comp );
      } else
         throw new IllegalArgumentException( this.getClass().getName() + 
            " unknown layout placement " + name );
   }

   /**
    * Remove a component from the layout. Called by Panel.
    * Causes a repaint.
    *
    * @see LayoutManager
    * @see BorderLayout
    * @see Panel
    */
   public void removeLayoutComponent( Component comp ) {
      if (center.equals( comp )) {
         center = null;
      } else {
         if ( !north.removeElement( comp ) )
            if ( !south.removeElement( comp ) )
               if ( !east.removeElement( comp ) )
                  if ( !west.removeElement( comp ) )
                     throw new IllegalArgumentException( this.getClass().getName() + 
                        " component " + comp.toString() + " not found in layout manager" );
      } /* endif */
   }

   /**
    * Calculates the preferred size of the layout.
    * <p>
    * The preferred height is the sum of the insets, the north components,
    * the south components and the maximum height of the east, west, and center
    * components. The center and east and west components may be stretched
    * vertically to fit a taller window. The north and south components are
    * never stretched vertically.
    * <p>
    * The preferred width is the sum of the insets, the east, west, and center
    * components. The center and north and south may be stretched horizontally 
    * to fit a wider window. The east and west components are never stretched
    * horizontally.
    *
    * @see minimumLayoutSize()
    */
   public Dimension preferredLayoutSize( Container parent ) {
      // System.out.println( this.getClass().getName() + ".preferredLayoutSize" );
      Dimension dim = new Dimension();

      setSizes();

      // Insets insets = ( userinsets == null ) ? parent.insets() : userinsets; // 1.02
      Insets insets = ( userinsets == null ) ? parent.getInsets() : userinsets; // 1.1
      dim.width = insets.left + insets.right + prefSize.width;
      dim.height = insets.bottom + insets.top + prefSize.height;
      return dim;
   }

   /**
    * Calculates the minimum size of the layout.
    *
    * @see preferredLayoutSize()
    */
   public Dimension minimumLayoutSize( Container parent ) {
      Dimension dim = new Dimension();

      setSizes();

      // Insets insets = ( userinsets == null ) ? parent.insets() : userinsets; // 1.02
      Insets insets = ( userinsets == null ) ? parent.getInsets() : userinsets; // 1.1
      dim.width = insets.left + insets.right + minSize.width;
      dim.height = insets.bottom + insets.top + minSize.height;

      // System.out.println( this.getClass().getName() + ".minimumLayoutSize=" + dim.width + "," + dim.height );
      return dim;
   }

   /**
    * Called by a panel to layout (reshape) components in a container.
    * May also be called by a user to force a new layout.
    */
   public void layoutContainer( Container parent ) {
      // System.out.println( this.getClass().getName() + ".layoutContainer" );

      // Add parent insets to user insets in case the item is a frame.
      Insets insets = new Insets( 0, 0, 0, 0 );
      // if ( parent.insets() != null ) { // 1.02
      if ( parent.getInsets() != null ) { // 1.1
         // Insets parentinsets = parent.insets(); // 1.02
         Insets parentinsets = parent.getInsets(); // 1.1
         insets.left += parentinsets.left;
         insets.right += parentinsets.right;
         insets.top += parentinsets.top;
         insets.bottom += parentinsets.bottom;
      }
      if (userinsets != null) {
         insets.left += userinsets.left;
         insets.right += userinsets.right;
         insets.top += userinsets.top;
         insets.bottom += userinsets.bottom;
      } /* endif */

      // int containerWidth = parent.size().width - insets.left - insets.right; // 1.02
      int containerWidth = parent.getSize().width - insets.left - insets.right; // 1.1
      // int containerHeight = parent.size().height - insets.top - insets.bottom; // 1.02
      int containerHeight = parent.getSize().height - insets.top - insets.bottom; // 1.1

      // setSizes(); // unnecessary?

      int northY = insets.top;
      // System.out.println( this.getClass().getName() + ".layoutContainer north Y starting at " + northY );
      for ( int i = north.size() - 1; i >= 0; i-- ) {
         Component c = (Component) north.elementAt( i );
         // c.reshape( insets.left, northY, containerWidth, c.preferredSize().height ); // 1.02
         c.setBounds( insets.left, northY, containerWidth, c.getPreferredSize().height ); // 1.1
         // System.out.println( this.getClass().getName() + ".layoutContainer placed north " + i + " at " + 
         //   insets.left + "," + northY + "," + containerWidth + "," + c.preferredSize().height );
         // northY += c.preferredSize().height; // 1.02
         northY += c.getPreferredSize().height; // 1.1
      }
      // int southY = parent.size().height - insets.bottom; // 1.02
      int southY = parent.getSize().height - insets.bottom; // 1.1
      // System.out.println( this.getClass().getName() + ".layoutContainer south Y starting at " + southY );
      for ( int i = south.size() - 1; i >= 0; i-- ) {
         Component c = (Component) south.elementAt( i );
         // southY -= c.preferredSize().height; // 1.02
         southY -= c.getPreferredSize().height; // 1.1
         // c.reshape( insets.left, southY, containerWidth, c.preferredSize().height ); // 1.02
         c.setBounds( insets.left, southY, containerWidth, c.getPreferredSize().height ); // 1.1
         // System.out.println( this.getClass().getName() + ".layoutContainer placed south " + i + " at " + 
         //    insets.left + "," + southY + "," + containerWidth + "," + c.preferredSize().height );
      }

      int stretchedHeight = southY - northY;
      int westX = insets.left;
      // System.out.println( this.getClass().getName() + ".layoutContainer west X starting at " + westX );
      for ( int i = west.size() - 1; i >= 0; i-- ) {
         Component c = (Component) west.elementAt( i );
         // c.reshape( westX, northY, c.preferredSize().width, stretchedHeight ); // 1.02
         c.setBounds( westX, northY, c.getPreferredSize().width, stretchedHeight ); // 1.1
         // System.out.println( this.getClass().getName() + ".layoutContainer placed west " + i + " at " + 
         //    westX + "," + northY + "," + c.preferredSize().width + "," + stretchedHeight );
         // westX += c.preferredSize().width; // 1.02
         westX += c.getPreferredSize().width; // 1.1
      }
      // int eastX = parent.size().width - insets.right; // 1.02
      int eastX = parent.getSize().width - insets.right; // 1.1
      // System.out.println( this.getClass().getName() + ".layoutContainer east X starting at " + eastX );
      for ( int i = east.size() - 1; i >= 0; i-- ) {
         Component c = (Component) east.elementAt( i );
         // eastX -= c.preferredSize().width; // 1.02
         eastX -= c.getPreferredSize().width; // 1.1
         // c.reshape( eastX, northY, c.preferredSize().width, stretchedHeight ); // 1.02
         c.setBounds( eastX, northY, c.getPreferredSize().width, stretchedHeight ); // 1.1
         // System.out.println( this.getClass().getName() + ".layoutContainer placed east " + i + " at " + 
         //    eastX + "," + northY + "," + c.preferredSize().width + "," + stretchedHeight );
      }

      if ( center != null ) {
         // center.reshape( westX, northY, eastX - westX, stretchedHeight ); // 1.02
         center.setBounds( westX, northY, eastX - westX, stretchedHeight ); // 1.1
         // System.out.println( this.getClass().getName() + ".layoutContainer placed center at " + 
         //    westX + "," + northY + "," + (eastX - westX) + "," + stretchedHeight );

      }
   }

   // helper method
   private void setSizes( ) {
      // System.out.println( this.getClass().getName() + ".setSizes" );
      if (sizesSet) return;

      int maxEastWestPrefHeight = 0;
      int maxEastWestMinHeight = 0;

      prefSize.width = prefSize.height = 0;
      minSize.width = minSize.height = 0;

      // Figure width
      for ( int i = 0; i < west.size(); i++ ) {
         Component c = (Component) west.elementAt( i );
         // prefSize.width += c.preferredSize().width; // 1.02
         prefSize.width += c.getPreferredSize().width; // 1.1
         // minSize.width += c.minimumSize().width; // 1.02
         minSize.width += c.getMinimumSize().width; // 1.1

         // maxEastWestPrefHeight = Math.max( maxEastWestPrefHeight, c.preferredSize().height ); // 1.02
         maxEastWestPrefHeight = Math.max( maxEastWestPrefHeight, c.getPreferredSize().height ); // 1.1
         // maxEastWestMinHeight = Math.max( maxEastWestMinHeight, c.minimumSize().height ); // 1.02
         maxEastWestMinHeight = Math.max( maxEastWestMinHeight, c.getMinimumSize().height ); // 1.1
      }
      for ( int i = 0; i < east.size(); i++ ) {
         Component c = (Component) east.elementAt( i );
         // prefSize.width += c.preferredSize().width;  // 1.02
         prefSize.width += c.getPreferredSize().width;  // 1.1
         // minSize.width += c.minimumSize().width; // 1.02
         minSize.width += c.getMinimumSize().width; // 1.1

         // maxEastWestPrefHeight = Math.max( maxEastWestPrefHeight, c.preferredSize().height ); // 1.02
         maxEastWestPrefHeight = Math.max( maxEastWestPrefHeight, c.getPreferredSize().height ); // 1.1
         // maxEastWestMinHeight = Math.max( maxEastWestMinHeight, c.minimumSize().height ); // 1.02
         maxEastWestMinHeight = Math.max( maxEastWestMinHeight, c.getMinimumSize().height ); // 1.1
      }

      if (center != null) {
         // prefSize.width += center.preferredSize().width; // 1.02
         prefSize.width += center.getPreferredSize().width; // 1.1
         // minSize.width += center.minimumSize().width;// 1.02
         minSize.width += center.getMinimumSize().width;// 1.1

         // maxEastWestPrefHeight = Math.max( maxEastWestPrefHeight, center.preferredSize().height ); // 1.02
         maxEastWestPrefHeight = Math.max( maxEastWestPrefHeight, center.getPreferredSize().height ); // 1.1
         // maxEastWestMinHeight = Math.max( maxEastWestMinHeight, center.minimumSize().height ); // 1.02
         maxEastWestMinHeight = Math.max( maxEastWestMinHeight, center.getMinimumSize().height ); // 1.1
      }

      // Figure height
      for ( int i = 0; i < south.size(); i++ ) {
         Component c = (Component) south.elementAt( i );
         // prefSize.height += c.preferredSize().height;  // 1.02
         prefSize.height += c.getPreferredSize().height;  // 1.1
         // minSize.height += c.minimumSize().height; // 1.02
         minSize.height += c.getMinimumSize().height; // 1.1
      }
      for ( int i = 0; i < north.size(); i++ ) {
         Component c = (Component) north.elementAt( i );
         // prefSize.height += c.preferredSize().height;  // 1.02
         prefSize.height += c.getPreferredSize().height;  // 1.1
         // minSize.height += c.minimumSize().height; // 1.02
         minSize.height += c.getMinimumSize().height; // 1.1
         // System.out.println( this.getClass().getName() + ".setSizes adding pref " + c.preferredSize().height );
         // System.out.println( this.getClass().getName() + ".setSizes adding min " + c.minimumSize().height );
      }
      prefSize.height += maxEastWestPrefHeight;
      minSize.height += maxEastWestMinHeight;

      sizesSet = true;
   }

   /**
    * Sets additional space around the layout.
    * Also used when the layout calculates its minimum and preferred sizes.
    *
    * @param i insets of layout from edge of parent
    * @return LayoutManager to allow chaining of methods
    */
   public LayoutManager setInsets( Insets i ) {
      userinsets = i;
      return (LayoutManager) this;
   }

   /**
    * Gets the insets of this layout manager.
    *
    * @return Insets the additional space around components in this layout.
    */
   public Insets getInsets( ) {
      return userinsets;
   }

   public String toString() {
      return "LayoutMultiBorder number of items north,east,south,west=" +
         north.size() + "," + 
         east.size() + "," + 
         south.size() + "," + 
         west.size();
   }

   /**
    * Cloneable role 
    */
   public Object clone() {
      LayoutMultiBorder rl = new LayoutMultiBorder();
      rl.minSize = minSize;
      rl.prefSize = prefSize;
      rl.sizesSet = sizesSet;

      rl.north = (Vector) north.clone();
      rl.south = (Vector) south.clone();
      rl.east  = (Vector) east.clone();
      rl.west  = (Vector) west.clone();
      rl.center = center;
      rl.userinsets = userinsets;
      
      return rl;
   }

   // Data
   private Dimension minSize, prefSize;
   private boolean sizesSet = false;
   
   private Vector north, south, east, west;
   private Component center = null;
   private Insets userinsets = null;
}
