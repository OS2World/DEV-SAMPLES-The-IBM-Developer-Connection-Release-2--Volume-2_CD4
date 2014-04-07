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

#include <os2.h>
#include "stub.h"

VOID *DataOffset = 0;

#ifdef CRUNTIME_IF_NEEDED
int _CRT_init();
#endif

#pragma linkage (_DLL_InitTerm, system)
ULONG _DLL_InitTerm( ULONG ModHandle, ULONG Flags )
{
   ULONG      retCode = TRUE;
   ModHandle  = 1; /* Compiler Warning Overide */

   if(!Flags)
   {
#ifdef CRUNTIME_IF_NEEDED
      if(_CRT_init())
      {
         retCode = FALSE;
      }
      else
      {
#endif
         DataOffset = (VOID *)((int)(&ROStub) + (sizeof( ULONG ) * 2));

#ifdef CRUNTIME_IF_NEEDED
      }
#endif
   }
   return( retCode );
}

