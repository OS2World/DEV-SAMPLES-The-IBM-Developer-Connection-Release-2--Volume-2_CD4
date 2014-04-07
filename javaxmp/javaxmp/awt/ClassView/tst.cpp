#include <stdio.h>

main()
{
  int  a = 0xCA ;
  char b = 0 ;
  char c = 0 ;
  char hex[] = { '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F' };

  printf( "%d %d" , a >> 4, a & 0x0f ) ;

  b = a >> 4 ;
  c = a & 0x0f ;

  printf( "%c%c", hex[b], hex[c] ) ;
}
