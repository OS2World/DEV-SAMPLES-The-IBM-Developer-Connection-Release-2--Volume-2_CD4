/* AppsGuid.h:  Common Definitions */



#define STR_DBNAME "APPSGUID"
#define STR_TABLENAME "ISVAPPS"
#define STR_USERID "db2admin"
#define MAX_ID_AND_NAME_LENGTH 53

typedef short int BOOL ;
enum BOOL { false = 0 , true = 1 } ;
typedef struct _Product 
{
	char product_id[11] ;
	char product_name[41] ;
	char short_name[11] ;
	char description[81] ;
	char supplier_id[11] ;
} Product, * PProduct ;

BOOL connect( char * pDatabase, char * pUserID, char * pPassword ) ;
BOOL insertProduct( char * pProduct_ID, char * pProduct_Name, 
				 char * pShort_Name, char * pDescription,
				 char * pSupplier_ID ) ;
BOOL getProduct( PProduct pProduct ) ;
long int queryCount( ) ;
BOOL queryProductIDsAndNames( long int * p_lBufferSize, char * p_Buffer ) ;
BOOL commit( BOOL bool ) ;



