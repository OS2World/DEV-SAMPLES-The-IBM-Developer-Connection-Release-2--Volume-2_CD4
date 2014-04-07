//-------------------------------------------------------------------------
//
//  Name     : ClassView.cpp
//
//  Purpose  : Dump the contents of a Java class file.
//
//             Author: Kelvin R. Lawrence
//             22nd-December-1996
//
//-------------------------------------------------------------------------
#include <stdio.h>
#include <io.h>
#include <fcntl.h>
#include <sys\stat.h>

#define KRL_DEBUG

#include "ClassView.h"


//-------------------------------------------------------------------------
//
//              
//-------------------------------------------------------------------------              
ClassView::ClassView()
{
  DEBUGPRT( "Executing constructor method" ) ;

  cbData = 0 ;
  pData  = 0 ;
  cd     = 0 ;
  ptr    = 0 ;
}

//-------------------------------------------------------------------------
//
//              
//-------------------------------------------------------------------------              
ClassView::~ClassView()
{
  DEBUGPRT( "Executing destructor method" ) ;

  if ( pData )
  {
    delete pData  ;
  }
}


//-------------------------------------------------------------------------
// ClassView::read2Bytes()
//              
// Note: Java class files use big endian encoding so we have to handle that
//       conversion as we will be running on an Intel (little endian)
//       system.  We cannot perform a simple cast such as the following
//       "temp = *(USHORT*)ptr" as the compiler will assume the source is
//       little endian encoded and we will not get the conversion we need.
//
//-------------------------------------------------------------------------              
USHORT ClassView::read2Bytes()
{
  USHORT temp ;

  temp = (USHORT)(*ptr << 8) + (USHORT)*(ptr+1) ;
  ptr += sizeof(USHORT)   ;

  return temp ;
}

//-------------------------------------------------------------------------
// ClassView::read4Bytes()
//              
// Note: Java class files use big-endian encoding so we have to handle that
//       conversion as we will be running on an Intel (little endian)
//       system.
//
//-------------------------------------------------------------------------              
ULONG  ClassView::read4Bytes() 
{
  ULONG temp ;

  temp =  (ULONG)(*ptr<<24) + (ULONG)(*(ptr+1)<<16) + (ULONG)(*(ptr+2)<<8) + (ULONG)(*(ptr+3)) ;
  ptr += sizeof(ULONG)   ;

  return temp ;
}

//-------------------------------------------------------------------------
// ClassView::read1Byte()
//
//              
//-------------------------------------------------------------------------              
BYTE    ClassView::read1Byte()
{
  BYTE temp ;

  temp = *ptr++ ;

  return temp;
}

//-------------------------------------------------------------------------
//
//              
//-------------------------------------------------------------------------              
BOOL ClassView::openAndReadClassFile( PSZ pszFilename )
{
  BOOL rc     = TRUE ;
  int  hFile  = 0    ;

  //-------------------------------------------------------------------
  // Find out how big the file is, allocate memory and read in the data
  // and then close the file.
  //-------------------------------------------------------------------

  hFile = _open( pszFilename, O_RDONLY | O_BINARY, S_IREAD );

  if ( hFile != -1 )
  {
    cbData =  _filelength( hFile );
    printf( "DEBUG: File length came back as %4d byte(s)\n", cbData ) ;

    pData = new BYTE [ cbData ] ;

    if ( !pData )
    {
      DEBUGPRT( "Could not allocate memory to load class file.\n" ) ;
      _close( hFile );
      rc = FALSE ;
    }

    ptr = pData ;

    _read( hFile, pData, cbData );
    _close( hFile );
  }
  else
  {
    printf( "DEBUG: File (%s) could not be opened\n\n", pszFilename );
    rc = FALSE ;
  }

  return rc ;
}

//-------------------------------------------------------------------------
// main()
//              
//-------------------------------------------------------------------------              
int main( int argc, char *args[] ) 
{
  ClassView cv ;
  ClassData cd ;
  BOOL rc ;

  //---------------------------------------------------------------
  // Process command line arguments and try to open the input file.
  //---------------------------------------------------------------

  if ( argc > 1 )
  {
    rc = cv.openAndReadClassFile( args[1] ) ;

    if ( !rc )
    {
      printf( "Error opening class file, program aborted.\n" ) ;
      return(0) ;
    }
  }
  else
  {
    printf( "\n\nUsage: ClassView filename.\n" );
    return(0);
  }


  //-----------------------------------------------------------
  // Process the magic number. If we don't recognize it, abort.
  //-----------------------------------------------------------

  cd.magic = cv.read4Bytes() ;
  DEBUGPRTN( "Magic number = ", cd.magic ) ;

  if ( cd.magic != JAVA_CLASS_MAGIC )
  {
    printf( "File %s is not a valid Java class file.\n", args[1] ) ;
    return(0);
  }

  //----------------------------------------------------------- 
  // Process the minor version number. For now ignore it.
  //-----------------------------------------------------------

  cd.minor = cv.read2Bytes() ;
  DEBUGPRTN( "Minor version = ", cd.minor ) ;

  //----------------------------------------------------------- 
  // Process the major version number. If it isn't one we
  // recognise then abort.
  //-----------------------------------------------------------

  cd.major = cv.read2Bytes() ;
  DEBUGPRTN( "Major version = ", cd.major ) ;

  if ( cd.major != JAVA_VERSION_102 )
  {
    printf( "File %s does not contain a valid version number.\n" );
    return(0) ;
  }

  //---------------------------------------------------------------
  // Process the constant pool. First find out how big the pool is.
  //---------------------------------------------------------------

  cd.nPool = cv.read2Bytes() ;
  DEBUGPRTN( "Number of constant pool entries = ", cd.nPool ) ;

  //----------------------------------------------------------------------
  // Now loop through the constant pool.
  //
  // Note: Entry zero in the constant pool is reserved for use by the Java
  //       virtual machine and is not present in the .class file.
  //----------------------------------------------------------------------

  BYTE tag ;

  for ( int i = 1; i <= cd.nPool; i++ )
  {
    tag = cv.read1Byte() ;

    DEBUGPRTN( "Processing tag ==>", tag ) ;

    switch( tag )
    {
      case CONSTANT_Utf8:
      {
        
      }
      break;

      case CONSTANT_Reserved:
      {
        DEBUGPRTN( "Don't know how to handle reserved tag ==>", tag ) ;
      }
      break;

      case CONSTANT_Integer:
      case CONSTANT_Float:
      {

      }
      break;

      case CONSTANT_Long:
      case CONSTANT_Double:
      {

      }
      break;

      case CONSTANT_Class:
      case CONSTANT_String:
      case CONSTANT_Fieldref:
      case CONSTANT_Methodref:
      case CONSTANT_InterfaceMethodref:
      case CONSTANT_NameAndType:
      {

      }
      break ;


      default:
      {
        DEBUGPRT( "Encountered an unrecognised constant pool tag." ) ;
      }
    }
  }


  //-------------------------------------
  // Allocate memory to hold the entries.
  //-------------------------------------

  // cd.pPool = (PSZ*) new PSZ[4] ;
}



