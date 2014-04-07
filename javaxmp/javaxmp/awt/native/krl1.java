class krl1
{
  int a ;
  int b ;
  String c ;

  native int addup( int a, int b ) ;

  public int addem( int a, int b )
  {
    int sum ;

    sum = addup( a,b ) ;

    return sum ;
  }
}
