//-------------------------------------------------------------------------
//
//  Name     : ClassView.h
//
//  Purpose  : Prototypes, constants and class definitions for the 
//             ClassView application.
//
//             Author: Kelvin R. Lawrence
//             22nd-December-1996
//
// The information needed to construct these data types, constants and
// classes is derived from the book ... 
//-------------------------------------------------------------------------
#include <stdio.h>
#include <io.h>
#include <fcntl.h>

typedef unsigned short USHORT ;
typedef unsigned long  ULONG  ;
typedef unsigned char  BYTE   ;
typedef char *         PSZ    ;
typedef unsigned char  BOOL   ;

#define TRUE  1
#define FALSE 0

#define JAVA_CLASS_MAGIC 0xCAFEBABE
#define JAVA_VERSION_102 45

typedef union _poolEntry
{
  float f ;
  int   i ;
  long  l ;
  PSZ   p ;
} poolEntry ;

// -----------------------------------------------------------------------                                                                          
// Internal representation of Java class file used by the ClassView class.
// -----------------------------------------------------------------------

typedef struct _ClassData
{
  ULONG  magic    ; // Magic number.
  USHORT minor    ; // Minor version number.
  USHORT major    ; // Major version number.
  USHORT nPool    ; // Number of entries in constant pool.
  PSZ   *pPool    ; // Table of string pointers that point to the constant pool entries.


} ClassData ;

enum
{
  CONSTANT_Utf8 = 1  ,
  CONSTANT_Reserved  ,
  CONSTANT_Integer   ,
  CONSTANT_Float     ,
  CONSTANT_Long      ,
  CONSTANT_Double    ,
  CONSTANT_Class     ,
  CONSTANT_String    ,
  CONSTANT_Fieldref  ,
  CONSTANT_Methodref ,
  CONSTANT_InterfaceMethodref ,
  CONSTANT_NameAndType
} ;

//------------------------------------------------------------
// Definition of the ClassView class. See also ClassView.cpp .
//------------------------------------------------------------
class ClassView
{
  public:

    //----------------
    // Public methods.
    //----------------

    ClassView()  ;
    ~ClassView() ;

    BYTE   read1Byte()  ;
    USHORT read2Bytes() ;
    ULONG  read4Bytes() ;
    BOOL   openAndReadClassFile( PSZ pszFilename ) ;

  private:

    BYTE      *pData  ;
    BYTE      *ptr    ;
    ClassData *cd     ;
    int        cbData ;

} ;

//-------------------------
// Useful debugging macros.
//-------------------------

#ifdef KRL_DEBUG
  #define DEBUGPRT(s)                                                              \
      printf( "DEBUG: Line %04d in function %s\n" , __LINE__, __FUNCTION__ ) ;     \
      printf( "DEBUG: %s\n", s ) ;
      
  #define DEBUGPRTN(s,n)                                                           \
      printf( "DEBUG: Line %04d in function %s\n" , __LINE__, __FUNCTION__ ) ;     \
      printf( "DEBUG: %s %08x\n",s,n ) ;
#else
  #define DEBUGPRT(s)
  #define DEBUGPRTN(s,n)
#endif
  
                     
                     
