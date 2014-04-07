//-----------------------------------------------------------------------------
// JFCTables.java
//
// A simple example of Swing tables, a multicolumn listbox with headers.
//
// This program requires:
//    Java JDK level 1.1.0 or higher.
//    Swing version 0.8 or higher.
//
// Demonstrates use of these classes/interfaces:
//    com.sun.java.swing.JFrame
//    com.sun.java.swing.JTable
//    com.sun.java.swing.table.TableHeader
//    com.sun.java.swing.table.TableColumn
//    com.sun.java.swing.table.AbstractTableModel
//
// Authors: 
//    Dan O. Becker
//
// History:
//    25-Mar-1998 -DOB-  Created.
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import com.sun.java.swing.*;
import com.sun.java.swing.event.* ;
import com.sun.java.swing.table.*;
import com.sun.java.swing.border.*;

/**
 * JFCTables
 * <p>
 * A simple example of Swing tables, a multicolumn listbox with headers.
 *
 * @author  Dan O. Becker
 * @version 25Mar1998    
 */
public class JFCTables extends JFrame 
{
   //-------------------------------------------------------------------------
   // Constants
   // Immutable objects for this class
   //-------------------------------------------------------------------------
   public final static String [] columnNames = 
      { "Name", "Sport", "Years Played", "Expertise" };

   //--------------------------------------------------------------------
   // main()
   // The entry point into the program.
   //--------------------------------------------------------------------
   public static void main( String [] args ) {
      // Construct a frame with the given title
      JFCTables frame = new JFCTables( "JFCTables" );
 
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
   public JFCTables( String s ) 
   {
      // Initialize frame with given title.
      super(s);
  
      // Remember content pane for future use. Default layout is BorderLayout.
      content = getContentPane();

      // Create a data model and a table to hold the data.
      data = new ArrayVectorModel();
      table = new JTable( data );

      // Update the table header
      JTableHeader header = table.getTableHeader();
      header.setReorderingAllowed( true );
      header.setResizingAllowed( true );

      // Add column names to the table
      for ( int i = 0; i < columnNames.length; i++ ){
          // Create a column object for each column of data
          TableColumn newColumn = new TableColumn( i );
          newColumn.setHeaderValue( columnNames[ i ] );
          newColumn.setMinWidth( 100 );
          table.addColumn( newColumn );
      }

      // Add data to table data model.
      data.addRow( new String [] { "Fred",    "Running",    "11", "84" } );
      data.addRow( new String [] { "Kelvin",  "Soccer",     "16", "95" } );
      data.addRow( new String [] { "Jane",    "Swimming",    "5", "77" } );
      data.addRow( new String [] { "Dave",    "Drinking",   "20", "88" } );
      data.addRow( new String [] { "Michael", "Basketball", "18", "65" } );

      // Show "expertise" as a bar in a range of 0 to 100.
      table.getColumn( "Expertise" ).setCellRenderer( new PercentRenderer() ); 

      // Place the table in a scroll pane so if it gets big, we can scroll.
      JScrollPane scrollpane = JTable.createScrollPaneForTable( table );
      content.add( "Center", scrollpane );
   }

   //-------------------------------------------------------------------------
   // PercentRenderer
   // Following is an example of how to create a Renderer for a table.
   // A renderer is responsible for drawing the value of a cell in a table.
   // The renderer is set into a cell via the TableColumn setCellRenderer
   // method. This renderer simply uses a JProgressBar to display a value
   // between 0 and 100.
   //-------------------------------------------------------------------------
   class PercentRenderer extends JProgressBar
      implements TableCellRenderer {
      protected Border noFocusBorder; 

      public PercentRenderer() {
         super();
         setOrientation( JProgressBar.HORIZONTAL );
         setMinimum( 0 );
         setMaximum( 100 );
         setBackground( Color.white );
         setOpaque(true);
         noFocusBorder = new EmptyBorder(1, 2, 1, 2);
         setBorder(noFocusBorder);
      }

      public Component getTableCellRendererComponent(
         JTable table, Object value,
         boolean isSelected, boolean hasFocus, int row, int column ) {
   
         // Should implement isSelected coloring.
   
         // Implement focus bordering.
         if (hasFocus) 
             setBorder( UIManager.getBorder( "Table.focusCellHighlightBorder" ) );
         else 
             setBorder( noFocusBorder );
   
         setValue( value ); 
          
         return this;
      }
      
      protected void setValue( Object value ) {
         if ( value instanceof Integer ) 
            super.setValue( ((Integer)value).intValue() );
         if ( value instanceof String ) 
            super.setValue( Integer.parseInt( (String) value ) );
         else
            super.setValue( 0 );
      }
   
   }

   //-------------------------------------------------------------------------
   // Fields
   // Objects that describe the state and information of this object.
   //-------------------------------------------------------------------------
   private Container content;
   private JTable table;
   private ArrayVectorModel data;
}

/**
 * ArrayVectorModel
 * <p>
 * A simple data model for use with JFC JTables.
 * This model is implemented as a Vector of Object arrays.
 * <p>
 * In addition for methods listed in AbstractTableModel,
 * this class implements methods for adding and removing rows.
 *
 * @see com.sun.java.swing.table.AbstractTableModel
 * @author  Dan Becker, beckerdo@io.com
 * @version 03/20/98     
*/
class ArrayVectorModel extends AbstractTableModel {
   // Methods
   /**
    * Adds a row of data to the end of the data model.
    *
    * @param row An Object [] of data.
    */
   public void addRow( Object [] row ) {
      data.addElement( row );
      fireTableRowsInserted( data.size(), data.size() );
   }

   /**
    * Adds a row of data to the 0-based row position in the model.
    *
    * @param row An Object [] of data.
    */
   public void addRowAt( Object [] row, int index ) {
      data.insertElementAt( row, index );
      fireTableRowsInserted( index, index );
   }

   /**
    * Removes the row of data at the 0-based row position in the model.
    *
    * @param row An Object [] of data.
    */
   public void removeRow( int index ) {
      data.removeElementAt( index );
      fireTableRowsDeleted( index, index );
   }

   // AbstractTableModel role
   public int getColumnCount() { 
      if ( getRowCount() == 0 ) return 0;
      return ((Object []) data.elementAt( 0 )).length;
   }
   public int getRowCount() { return data.size(); }
   public Object getValueAt( int row, int col ) {
      if ( row > getRowCount() ) return null;
      return ((Object []) data.elementAt( row ))[ col ];
   }
   public void setValueAt( Object value, int row, int col ) { 
      int rows = getRowCount();
      if ( row >= getRowCount() )
         throw new IllegalArgumentException( "attempted to set row " + row + ", total rows=" + rows );
      ((Object []) data.elementAt( row ))[ col ] = value;
   }

   // Fields
   Vector data = new Vector();
};
