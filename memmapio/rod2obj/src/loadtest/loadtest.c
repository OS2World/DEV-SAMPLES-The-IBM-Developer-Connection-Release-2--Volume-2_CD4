/***************************************************************************
 * Module Name : initterm.c
 *
 * Description :
 *
 * CopyRight Information
 *
 ***************************************************************************/
#define INCL_DOS
#define INCL_BASE

#include <stdio.h>
#include <os2.h>

VOID main( ULONG argc, CHAR *argv[] )
{
   ULONG    rc        = 0;
   ULONG    Address   = 0;
   ULONG   *ROBase    = 0;
   ULONG   *ROContent = 0;
   CHAR     pszError[256];
   HMODULE  hModule;
   INT      i = 1;

   printf("Loading Test of R/O Data to Code Dll\n");

   rc = DosLoadModule( pszError,
                       sizeof( pszError ),
                       "object",
                       &hModule );

   if( rc )
   {
      printf("Error trying to load module [%d] on module [%s]\n",rc, pszError );
   }
   else
   {
      printf("Loaded Successfully\n");

      rc = DosQueryProcAddr( hModule,
                             0,
                             "DataOffset",
                             &Address );

      if(!rc)
      {
         ROBase    = (ULONG *)Address;
         ROContent = (ULONG *)*ROBase;

         printf("Address to RO Data Query Addr  [0x%8.8x]\n" \
                "                   Load  Addr  [0x%8.8x]\n" \
                "                   Ptr Content [0x%8.8x]\n", Address,
                                                             *ROBase,
                                                             *ROContent );
      }

   }

   while( i )
   {
      i = 1;
   }


   return;
}
