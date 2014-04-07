
#include <stdio.h>
#include "Pal.h"

long
Pal_findPalindrome(struct HPal * self, long length )
{
  struct Hjava_lang_String * myString;
  char                       string[1000];
  char                     * string2;

  myString = unhand(self)->str;
  javaString2CString(myString, string, sizeof(string));

  string2 = strdup(string);
  if( string2 && (strcmp(string, strrev(string2)) == 0) )
    return 1;

  return 0;
}

