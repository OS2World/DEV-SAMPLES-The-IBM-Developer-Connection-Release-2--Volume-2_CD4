#include <iostream.h>
#include <fstream.h>
#include <iomanip.h>

main()
{
  ifstream input ;
  unsigned char ch ;

  unsigned long  magic ;
  short minor ;
  short major ;

  input.open(  "test2.class"  ) ;

  cout << hex ;

  #if 0
  
  for ( int i = 0; i<20; i++ )
  {
    ch = input.get() ;
    cout << (int)ch << " ";
  }
  #endif
  
  input.get((char *)&magic, 5) ;
  input.get((char *)&minor, 3);

  cout << magic << endl;
  cout << minor ;

  input.close() ;


}
