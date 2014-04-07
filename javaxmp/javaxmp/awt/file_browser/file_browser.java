//-----------------------------------------------------------------------------
// file_browser.java
//
// Java class that implements a very simple file browser.
//
// Constructors:
//   file_browser()
//   file_browser( String file_name )
//
// Public Methods:
//   selectNewFile
//   displayFile
//   handleEvent
//   action
//
// Private Methods:
//   createWindows
//
// Author : Kelvin R Lawrence.     21st-November-1996
//
// History:
//
//-----------------------------------------------------------------------------

import java.io.* ;
import java.awt.* ;

public class file_browser extends Frame
{
   //--------------------
   // Private class data.
   //--------------------
   File   file_info ;

   FileInputStream input_file ;

   TextArea txt ;
   Panel    buttons ;
   Button   open ;
   Button   done ;

   //--------------------------------------------------------------------
   // Constructor: file_browser().
   //
   // If no file name is given, simply create the file browser frame
   // window and its controls. There is no file to display at this
   // time.
   //
   //--------------------------------------------------------------------
   public file_browser()
   {
     //----------------------------------------------------------------
     // Call the constructor function for the Frame class that we are a
     // subclass of. This has to be the first thing we do in this method
     // or javac (the Java compiler) will reject us.
     //----------------------------------------------------------------
     super( "File Browser" ) ;

     createWindows() ;
   }

   //--------------------------------------------------------------------
   // Constructor: file_browser( String ).
   //
   // If a file name is given, create the file browser frame window
   // and its controls then call displayFile with the filename passed in.
   //
   //--------------------------------------------------------------------
   public file_browser( String file_name ) 
   {
     //----------------------------------------------------------------
     // Call the constructor function for the Frame class that we are a
     // subclass of. This has to be the first thing we do in this method
     // or javac (the Java compiler) will reject us.
     //----------------------------------------------------------------
     super( "Currently viewing: " + file_name ) ;

     createWindows() ;

     this.reshape( 100,100,400,500 ) ;

     displayFile( file_name ) ;
   }

   //--------------------------------------------------------------------
   // createWindows()
   //
   // Create our frame window and its contents. Position the controls in
   // the frame window and display the window.
   //--------------------------------------------------------------------
   private void createWindows()
   {  
      setLayout( new BorderLayout() ) ;

      //-------------------------------------------------------------
      // Create an empty  TextArea control that we will use later to
      // as the file contents to. As we are a file browser and not
      // an editor, we turn off the edit capability.
      //-------------------------------------------------------------
 
      txt = new TextArea() ;
 
      txt.setEditable( false ) ;

      //-------------------------------------------------------------
      // Create a panel containing some buttons for open and close.
      //-------------------------------------------------------------
      buttons = new Panel() ;

      open = new Button( "Open..." );
      done = new Button( "Done" ) ;

      buttons.add( open ) ;
      buttons.add( done ) ;

      //-------------------------------------------------------------------
      // Add the text area control and the panel of buttons to the frame 
      // and force all the elements // of the frame to their preferred size 
      // by calling pack(). Then display the frame by calling show().
      //-------------------------------------------------------------------
 
      add("South" , buttons ) ;
      add("Center", txt ) ;
 
      pack() ;

      show() ;
   }

   //--------------------------------------------------------------------
   // displayFile()
   //
   // Takes a file name, loads the file and displays its contents in the
   // browser window.
   //--------------------------------------------------------------------
   public void displayFile( String filename )
   {
      int    len ;
      int    cbytes = 0 ;
      byte[] input_data ;
 
      file_info = new File( filename ) ;
 
      System.out.println( "Processing, getPath() -> " + file_info.getPath() ) ;
 
      try
      {
        input_file = new FileInputStream( file_info ) ;
      }
      catch( FileNotFoundException excpt )
      {
        System.out.println( "Input file could not be found" ) ;
        return ;
      }
 
      len = (int) file_info.length() ;
 
      System.out.println( "File length reported as " + len + " byte(s)." ) ;
 
      input_data = new byte[len] ;
 
      //-----------------------------
      // Read the data from the file.
      //-----------------------------
      while ( cbytes < len ) 
      {
        try 
        {
          cbytes += input_file.read( input_data, cbytes, len - cbytes ) ;
        }
        catch( IOException IOexcpt )
        {
          System.out.println( "An IOException was thrown while reading the file" );
        }
 
       // System.out.println( new String( input_data,0 ) ) ;
      }

      //-----------------------------------------------------------------
      // Now reset the file browser window (TextArea control) and display
      // the new data that we just loaded.
      //-----------------------------------------------------------------

      txt.setText( new String( input_data, 0 ) ) ;

      // TBD: Update the title bar
   }

   //--------------------------------------------------------------------
   // selectNewFile()
   //
   // Display the system file dialog and allow a new file to be selected.
   //--------------------------------------------------------------------
   public String selectNewFile()
   {
      String       filename ;
      String       path ;
      FileDialog   fd ;

      fd = new FileDialog( this, "Select a file" ) ;
  
      System.out.println( "About to call fd.show()" ) ;
      fd.show() ;
  
      filename = fd.getFile() ;
      path     = fd.getDirectory() ;

      return( path + filename ) ;
   }

   //----------------------------------------------------------
   // action()
   //
   // Handle user actions.
   //
   // If the done button is pressed then hide our window and
   // get rid of it. If the open button is pressed display 
   // the file dialog and display the newly selected file.
   //
   //----------------------------------------------------------
   public boolean action( Event evt, Object what )
   {
     Button temp = null ;
 
     System.out.println("Start of action() method");
     System.out.println( "Button pressed was : " + what ) ;
 
     //-----------------------------------------
     // Button clicks are sent as ACTION_EVENTs.
     //-----------------------------------------
     if ( evt.id == Event.ACTION_EVENT )
     {
       if ( evt.target == open  ) 
       {
         System.out.println( "Open... clicked." );
         String fn = selectNewFile() ;
         displayFile( fn ) ;
       }
       else if ( evt.target == done ) 
       {
          hide() ;
          dispose() ;
          deliverEvent( new Event( this, Event.WINDOW_DESTROY, null ) ) ;
       }
       else return false ;
     }
     return true ;
   }

   //-------------------------------------------------------------------------
   // handleEvent()
   //
   // Handle events. We need this so that the close button will
   // close the application.
   //-------------------------------------------------------------------------
   public boolean handleEvent( Event evt )
   {
     if ( evt.id == Event.WINDOW_DESTROY)
     {
       System.exit(0) ;
       return true ;
     }
     else
     {
       return( super.handleEvent( evt ) );
     }
   }

   static public void main( String[] args ) 
   {
     file_browser f  ;
     String       fn ;
   
     String filename = "testfile.dat" ;
   
     f = new file_browser( filename ) ;
   
     //fn = f.selectNewFile() ;
     //f.displayFile( fn ) ;
   }
}


