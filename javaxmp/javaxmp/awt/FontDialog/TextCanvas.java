//-----------------------------------------------------------------------------
// TextCanvas.java
//
// Simple extension to the Canvas class. Used by the font_dialog class.
// 
//
// Author : Kelvin R Lawrence.     9th-December-1996
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.* ;

//---------------------------------------------------------------
// Class: TextCanvas
//
// Simple class that extends the base Canvas class to build
// a simple text string display class. This allows the 
// FontDialog class to use a TextCanvas area in which it can
// display samples of the selected text in the selected font.
// We have to subclass Canvas in order to handle paint requests
// correctly.
//
//---------------------------------------------------------------
public class TextCanvas extends Canvas
{
   private Font   font ;
   private String text ;

   //----------------------------------------------------------------------
   // TextCanvas()
   //
   // Constructor.
   //----------------------------------------------------------------------
   public TextCanvas()
   {
     super() ;
   }

   //----------------------------------------------------------------------
   // paint()
   //
   // Handle paint requests. Redraw the current string in the current font.
   //----------------------------------------------------------------------
   public void paint( Graphics g )
   {
     g.setFont( font ) ;
     g.setColor( Color.black ) ;
     g.drawString( text,5,55 ) ;
   }

   //----------------------------------------------------------------------
   // setFont()
   //
   // Set the current font to be used by future paint requests.
   //----------------------------------------------------------------------
   public void setFont( Font f )
   {
     this.font = f ;
   }

   //----------------------------------------------------------------------
   // setText()
   //
   // Set the current text string to be used by future paint requests. 
   //----------------------------------------------------------------------
   public void setText( String txt )
   {
     this.text = txt ;
   }
}
