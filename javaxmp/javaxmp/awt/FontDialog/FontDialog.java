//-----------------------------------------------------------------------------
// FontDialog.java
//
// Example of a Java class that implements a font selection dialog. This 
// involves querying a list of available fonts and displaying each of them in
// a dialog and allowing the user to choose one. The user is shown a preview of
// the current choices in a preview window.
//
// Author : Kelvin R Lawrence.     6th-December-1996
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.applet.* ;

interface FontDialogClient
{
  static final int OK     = 1 ;
  static final int CANCEL = 2 ;
  public void fontDialogNotify( int okcancel ) ;
}

//---------------------------------------------------------------
// Class: FontDialog
//
// Example Java class that implements a font selection dialog.
//
//---------------------------------------------------------------

/**
 * Simple font dialog.
 * @version 0.1    Dec-6-1996
 * @author Kelvin R. Lawrence
 */
public class FontDialog extends Dialog
{
  private int       point_size ;      // Selected point size.
  private String    face_name  ;      // Selected face (font) name.
  private int       font_style ;      // Selected font style.
  private String[]  faces ;           // Names of available fonts.
  private Font      cur_font   ;      // Current font used in preview area.

  private int[]     styles = { Font.PLAIN
                             , Font.BOLD
                             , Font.ITALIC
                             , Font.BOLD+Font.ITALIC } ;

  private final String[] sizes  = { "2","3","4","5","6","7","8","9","10","12"
                                  ,"14","16","18","20","22","24","36","48","72","96" } ;

  private final String[] style_names = { "Normal"
                                       , "Bold"
                                       , "Italic"
                                       , "Bold italic" } ;


  private static final String sample_txt = "Sample text" ;

  private FontDialogClient dialogClient ;

  private Choice     fontlist  ;
  private Choice     sizelist  ;
  private Choice     stylelist ;
  private Button     ok        ;
  private Button     cancel    ;
  private TextCanvas preview   ;
  private Label      label1    ;
  private Label      label2    ;
  private Label      label3    ;

  //-----------------------------------------------------------------------
  // FontDialog()
  //
  // Constructor.
  //
  // Initialize this instance of the FontDialog class by performing the
  // following tasks:
  //
  //-----------------------------------------------------------------------
  /**
   * @param parent Parent frame window.
   * @param title  Title string for this dialog.
   * @param modal  Modality switch. 
   */
  public FontDialog( Frame parent, String title, boolean modal )
  {
    super( parent, modal ) ;

    setLayout( null ) ;

    //---------------------------------------------------------------------
    //
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
    //
    //---------------------------------------------------------------------

    faces = getToolkit().getFontList() ;

    //--------------------------------------------------------------
    // Set the title of the dialog to whatever the caller passed in.
    //--------------------------------------------------------------
    this.setTitle( title ) ;

    //-----------------------------------------------
    // Size the dialog and disble sizing by the user.
    //-----------------------------------------------
    this.resize( 300, 240 ) ;

    this.setResizable( false ) ;

    //------------------------------------------------------------------------------
    // Create the controls that will make up the contents of the font dialog window.
    //------------------------------------------------------------------------------
    fontlist  = new Choice() ;
    sizelist  = new Choice() ;
    stylelist = new Choice() ;
    ok        = new Button( "OK" ) ;
    cancel    = new Button( "Cancel" ) ;
    preview   = new TextCanvas() ;
    label1    = new Label( "Font"  , Label.LEFT ) ;
    label2    = new Label( "Size"  , Label.LEFT ) ;
    label3    = new Label( "Style" , Label.LEFT ) ;

    //----------------------------------------------------------
    // Size and position the various controls within the dialog.
    //----------------------------------------------------------
    ok.reshape( 10,220,100,20 ) ;

    cancel.reshape( 120,220,100,20 ) ;

    fontlist.reshape( 10,50,150,100 ) ;

    label1.reshape( 10,30,100,20 ) ;

    sizelist.reshape( 200,50,60,100 ) ;

    label2.reshape( 200,30,100,20 ) ;

    stylelist.reshape( 10,100,150,100 ) ;

    label3.reshape( 10,80,100,20 ) ;

    preview.reshape( 10,150,280,60 ) ;

    //-----------------------------------------------------------------
    // Add the names of the available fonts to the drop down font list.
    //-----------------------------------------------------------------
    for ( int i = 0; i < faces.length; i++ ) 
    {
      fontlist.addItem( faces[i] ) ;
    }

    //---------------------------------------------------------
    // Add the list of point  sizes to the drop down size list.
    //---------------------------------------------------------
    for ( int i = 0; i < sizes.length; i++ ) 
    {
      sizelist.addItem( sizes[i] ) ;
    }

    //----------------------------------------------------------
    // Add the list of style names to the drop down styles list.
    //----------------------------------------------------------
    for ( int i = 0; i < style_names.length; i++ )
    {
      stylelist.addItem( style_names[i] ) ;
    }

    //------------------------------------------------------
    // Now set the label colours and add the controls to the 
    // dialog window.
    //------------------------------------------------------

    label1.setForeground( Color.blue ) ;
    label2.setForeground( Color.blue ) ;
    label3.setForeground( Color.blue ) ;
    preview.setBackground( Color.white ) ;

    this.add( fontlist ) ;
    this.add( sizelist ) ;
    this.add( stylelist ) ;
    this.add( preview ) ;
    this.add( ok ) ;
    this.add( cancel ) ;
    this.add( label1 ) ;
    this.add( label2 ) ;
    this.add( label3 ) ;

    //---------------------------------------------------
    // Now, finally, initialise key variables by querying
    // current defaults and set the font for the preview
    // label. Note that the defaults are base off the
    // values that we placed in the controls earlier in
    // initialisation.
    //---------------------------------------------------
    point_size = Integer.parseInt(sizelist.getSelectedItem()) ;
    face_name  = fontlist.getSelectedItem() ;
    font_style = stylelist.getSelectedIndex() ;

    cur_font = new Font( face_name, font_style, point_size );

    preview.setText( sample_txt ) ;
    preview.setFont( cur_font ) ;
  }

  //-----------------------------------------------------------------------
  // show()
  //
  // Public method.
  //
  // Cuases the font dialog window to be displayed.
  //-----------------------------------------------------------------------
  /**
   * Causes the font dialog window to be displayed. 
   */
  public void show()
  {
    super.show() ;

    displaySampleString() ;
  }

  //-----------------------------------------------------------------------
  // action()
  //
  // Public method.
  //
  // Handle user actions.
  //-----------------------------------------------------------------------
  public boolean action( Event evt, Object what )
  {
     if ( evt.id == Event.ACTION_EVENT )
     {
       if ( evt.target == sizelist ) 
       {
          point_size = Integer.parseInt(sizelist.getSelectedItem()) ;
       }
       else if ( evt.target == fontlist ) 
       {
         face_name = fontlist.getSelectedItem() ;
       }
       else if ( evt.target == stylelist ) 
       {
         font_style = stylelist.getSelectedIndex() ;
       }
       else if ( evt.target == ok)
       {
         //--------------------------------------------------------
         // User clicked on the OK button so, if the client has
         // implemented the FontDialogClient interface then inform 
         // our client. Regardless, hide the dialog window.
         //--------------------------------------------------------
         System.out.println( "FontDialog-->OK clicked" ) ;

         this.hide() ;

         if ( dialogClient != null )
         {
           dialogClient.fontDialogNotify( FontDialogClient.OK  ) ;
         }
       }
       else if ( evt.target == cancel )
       {
         //--------------------------------------------------------
         // User clicked on the Cancel button so, if the client has
         // implemented the FontDialogClient interface then inform 
         // our client. Regardless, hide the dialog window.
         //--------------------------------------------------------
         System.out.println( "FontDialog-->Cancel clicked" ) ;

         this.hide() ;

         if ( dialogClient != null )
         {
           dialogClient.fontDialogNotify( FontDialogClient.CANCEL ) ;
         }

         //LATER -- On a cancel either set all vals to null or values when
         //         we started. Ie, a later getFontName etc. will not get 
         //         new info after a cancel.
       }

       cur_font = new Font( face_name, font_style, point_size );

       displaySampleString() ;
     }

     return false ;
  }

  //-----------------------------------------------------------------------
  // displaySampleString()
  //
  // Private method.
  //
  // Display the sample text string in the preview area.
  //
  // This is done by updating the font currently stored in the preview
  // area canvas and forcing it to repaint.
  //-----------------------------------------------------------------------
  private void displaySampleString()
  {
    preview.setFont( cur_font ) ;

    preview.repaint() ;
  }

  //-----------------------------------------------------------------------
  // getFontName()
  //
  // Public method.
  //
  // Return the name of the selected font.
  //-----------------------------------------------------------------------
  /**
   * Returns the name of the selected font.
   */
  public String getFontName()
  {
    return this.face_name ;
  }

  //-----------------------------------------------------------------------
  // getFontSize()
  //
  // Public method.
  //
  // Return the size of the selected font.
  //-----------------------------------------------------------------------
  /**
   * Returns the size of the selected font.
   */
  public int getFontSize()
  {
    return this.point_size ;
  }

  //-----------------------------------------------------------------------
  // getFontStyle()
  //
  // Public method.
  //
  // Return the style of the selected font.
  //-----------------------------------------------------------------------
  /**
   * Returns the style of the selected font.
   * It will be an integer matching one of PLAIN,BOLD,ITALIC or BOLD+ITALIC
   * as defined in the Font class.
   */
  public int getFontStyle()
  {
    return this.font_style ;
  }

  //-----------------------------------------------------------------------
  // setClient()
  //
  // Public method.
  //
  // Allows the client of the font dialog to tell us where it wants us
  // to send notifications when the font dialog gets dismissed.
  //-----------------------------------------------------------------------
  public void setClient( FontDialogClient f ) 
  {
     dialogClient = f ;
  }

}
