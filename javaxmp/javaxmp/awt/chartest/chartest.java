
public class chartest
{
  static public void main( String [] args )
  {
    boolean rc ;

    rc = Character.isLetterOrDigit('\u0901' ) ;

    if ( rc )
    {
      System.out.println( "Character is a letter or a digit" ) ;
    }
    else
    {
      System.out.println( "Character is not a letter or a digit" ) ;
    }

    rc = Character.isLetter( '\u0901' ) ;

    if ( rc )
    {
      System.out.println( "Character is a letter" ) ;
    }
    else
    {
      System.out.println( "Character is not a letter" ) ;
    }

  }
}
