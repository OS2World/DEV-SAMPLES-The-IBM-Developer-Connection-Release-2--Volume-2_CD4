//-----------------------------------------------------------------------------
// ClassView.java
//
//
//
// Author : Kelvin R Lawrence.     12th-December-1996
//
// History:
//
//-----------------------------------------------------------------------------

//---------------------------------------------------------------
// Class: ClassView
//
//
//
//---------------------------------------------------------------
import java.io.* ;

public class ClassView
{
  public int  magic ;
  public char major ;
  public char minor ;

  static public char[] toHex( byte a )
  {
    char[] hex = { '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F' };

    int b ;
    int c ;

    char[] hexchars = new char[2];

    System.out.println( "toHex got passed the value: " + a ) ;

    b = (a >> 4 ) & 0x0f ;
    c = a & 0x0f ;

    hexchars[0] = hex[b] ; 
    hexchars[1] = hex[c] ;

    System.out.println( "First char = " + hex[b] ) ;
    System.out.println( "Second char = " + hex[c] ) ;

    return hexchars ;
  }

  static public void main( String[] args )
  {
    File            file_info ;
    DataInputStream dis ;
    FileInputStream input_file ;

    int p ;
    int len, i ;

    System.out.println( "DEBUG: main() was passed " + args.length + " parameters." );

    for ( i=0; i < args.length; i++ )
    {
      System.out.println( "DEBUG: args[" + i + "] = " + args[i] ) ;
    }

    if ( args.length < 1 )
    {
      System.out.println( "Usage: ClassView filename" ) ;
      return ;
    }

    file_info = new File( args[0] ) ;

    try
    {
      input_file = new FileInputStream( file_info ) ;
    }
    catch( FileNotFoundException excpt )
    {
      System.out.println( "Input file " + args[0] + "  could not be found." ) ;
      return ;
    }

    dis = new DataInputStream( input_file ) ;

    len = (int) file_info.length() ;

    System.out.println( "File length reported as " + len + " byte(s)." ) ;

    //-----------------------------
    // Read the data from the file.
    //-----------------------------

    byte[] data = new byte[len] ;

    try
    {
      dis.readFully( data ) ;
    }
    catch( IOException io_excpt )
    {
      System.out.println( "IO Exception while reading file data." ) ;
      return ;
    }

    for ( i=0; i<20; i++ ) { System.out.println( data[i] & 0x00FF ) ; }

    //System.out.write( data,0,len-1 ) ;

    ClassView cv = new ClassView() ;

    //cv.magic = (int) data[1] + data[0] << 8 ;
    //cv.major = (char) data[2] ;
    //cv.minor = (char) data[4] ;

    char[] tmp = cv.toHex( data[0] ) ;

    System.out.println( "First char returned is " + (int)tmp[0] & 0x00FF) ;
    System.out.print( tmp[1] ) ;

    //System.out.println( "Magic = " + cv.magic ) ;
    //System.out.println( "Major = " + cv.major ) ;
    //System.out.println( "Minor = " + cv.minor ) ;
  }
}
