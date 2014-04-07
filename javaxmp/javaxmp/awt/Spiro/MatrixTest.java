public class MatrixTest
{
  public static void main( String[] args )
  {
    Matrix m = new Matrix() ;

    m.dumpToConsole() ;

    m.setTranslation( 10, 10 ) ;

    m.dumpToConsole() ;

    int[] x = new int[1] ;
    int[] y = new int[1] ;

    x[0] = 30 ;
    y[0] = 40 ;

    m.transformPoints( x,y ) ;

    System.out.println( "x ==> " + x[0] ) ;
    System.out.println( "y ==> " + y[0] ) ;

    m.makeIdentity() ;

    m.dumpToConsole() ;

    m.setScaling( 10,10 ) ;

    m.dumpToConsole() ;

    m.transformPoints( x,y ) ;

    System.out.println( "x ==> " + x[0] ) ;
    System.out.println( "y ==> " + y[0] ) ;

    x[0] = 10;
    y[0] = 10;

    m.makeIdentity() ;

    m.setRotation(0,0,45) ;

    m.dumpToConsole() ;

    m.transformPoints( x,y ) ;

    System.out.println( "x ==> " + x[0] ) ;
    System.out.println( "y ==> " + y[0] ) ;

  }
}
