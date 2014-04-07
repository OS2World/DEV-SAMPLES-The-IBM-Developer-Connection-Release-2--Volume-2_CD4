static char sqla_program_id[40] = 
{111,65,65,66,65,73,65,69,77,78,73,88,32,32,32,32,68,66,67,65,
76,76,83,32,77,66,98,70,87,68,70,79,48,49,49,49,49,50,32,32};


#include "sqladef.h"

static struct sqla_runtime_info sqla_rtinfo = 
{{'S','Q','L','A','R','T','I','N'}, sizeof(wchar_t), 0, {' ',' ',' ',' '}};


#line 1 "dbcalls.sqc"
/*
(c) Copyright IBM Corp. 1997  All rights reserved.

These sample programs are owned by International Business Machines
Corporation or one of its subsidiaries ("IBM") and are copyrighted and
licensed, not sold.

You may copy, modify, and distribute these sample programs in any
form without payment to IBM,  for any purpose including developing,
using, marketing or distributing programs that include or are
derivative works of the sample programs.

The sample programs are provided to you on an "AS IS" basis, without
warranty of any kind.  IBM HEREBY EXPRESSLY DISCLAIMS ALL WARRANTIES,
EITHER EXPRESS OR IMPLIED, INCLUDING , BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
Some jurisdictions do not allow for the exclusion or limitation of
implied warranties, so the above limitations or exclusions may not
apply to you.  IBM shall not be liable for any damages you suffer
as a result of using, modifying or distributing the sample programs
or their derivatives.

Each copy of any portion of this/these sample program(s) or any
derivative work, must include a the above copyright notice and warranty.
*/




/* Sample using stored procedures */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sqlenv.h>
#include <sql.h>
#include <sqlda.h>
#include <sqlca.h>
#include "util.h"
#include "appsguid.h"



/*
EXEC SQL INCLUDE SQLCA;
*/

/* SQL Communication Area - SQLCA - structures and constants */
#include "sqlca.h"
struct sqlca sqlca;


#line 43 "dbcalls.sqc"


/*
EXEC SQL BEGIN DECLARE SECTION;
*/

#line 44 "dbcalls.sqc"
 
      	char product_id[11] ;
      	char product_name[41] ;
      	char short_name[11] ;
		char description[80] ;
		char supplier_id[11] ;
		long int count ;
		char spQueryProductIDsAndNames[50] ;

/*
EXEC SQL END DECLARE SECTION;
*/

#line 52 "dbcalls.sqc"


  
#define  CHECKERR( CE_STR )   if ( check_error ( CE_STR, &sqlca ) != 0 ) return 1;


BOOL connect( char * pDatabase, char * pUserID, char * pPassword )
{
	
/*
EXEC SQL BEGIN DECLARE SECTION;
*/

#line 60 "dbcalls.sqc"
 
      	char database[13];
      	char userid[9];
      	char password[19];
   	
/*
EXEC SQL END DECLARE SECTION;
*/

#line 64 "dbcalls.sqc"

	struct sqle_start_options startOptions ;


	//: Start Database Manager
	printf( "About to start Database Manager...\n" ) ;
	/*
		char                   sqloptid[8]; // Structure Eye Catcher          
        	unsigned long          isprofile; // Profile specified?               
        	char                   profile[SQL_PROFILE_SZ+1]; // Profile          
        	unsigned long          isnodenum; // Node number specified?           
        	SQL_PDB_NODE_TYPE      nodenum; // Node number                        
        	unsigned long          option; // Start option                        
        	unsigned long          ishostname; // Hostname specified?             
        	char                   hostname[SQL_HOSTNAME_SZ+1]; // Hostname       
        	unsigned long          isport; // Port specified?                     
        	SQL_PDB_PORT_TYPE      port;   // Port                                
        	unsigned long          isnetname; // Netname specified?               
        	char                   netname[SQL_HOSTNAME_SZ+1]; // Netname         
        	unsigned long          tblspace_type; // Addnode tablespace type      
        	SQL_PDB_NODE_TYPE      tblspace_node; // Addnode tablespace node      
        	unsigned long          iscomputer; // Computername specified?         
        	char                   computer[SQL_COMPUTERNAME_SZ+1]; 
        	char                   *pUserName; // Logon Account user name         
        	char                   *pPassword; // Logon Account password           
	*/
	/*********** DON'T NEED DURING DEVELOPEMENT--start dbm from command line...

	memset( &startOptions, 0, sizeof(struct sqle_start_options ) ) ;
	sqlepstart( &startOptions, &sqlca ) ;		//: No affect on client or if DBM already started
	printf( "Database Manager is started...\n" ) ;

	***************************/
	

	printf( "About to connect to %s...\n", pDatabase ) ;

	strcpy ( userid, pUserID );
      strcpy ( password, pPassword );
	strcpy ( database, pDatabase ) ;
      
/*
EXEC SQL CONNECT TO :database USER :userid USING :password ;
*/

{
#line 104 "dbcalls.sqc"
  sqlastrt(sqla_program_id, &sqla_rtinfo, &sqlca);
#line 104 "dbcalls.sqc"
  sqlaaloc(2,3,1,0L);
    {
      struct sqla_setd_list sql_setdlist[3];
#line 104 "dbcalls.sqc"
      sql_setdlist[0].sqltype = 460; sql_setdlist[0].sqllen = 13;
#line 104 "dbcalls.sqc"
      sql_setdlist[0].sqldata = (void*)database;
#line 104 "dbcalls.sqc"
      sql_setdlist[0].sqlind = 0L;
#line 104 "dbcalls.sqc"
      sql_setdlist[1].sqltype = 460; sql_setdlist[1].sqllen = 9;
#line 104 "dbcalls.sqc"
      sql_setdlist[1].sqldata = (void*)userid;
#line 104 "dbcalls.sqc"
      sql_setdlist[1].sqlind = 0L;
#line 104 "dbcalls.sqc"
      sql_setdlist[2].sqltype = 460; sql_setdlist[2].sqllen = 19;
#line 104 "dbcalls.sqc"
      sql_setdlist[2].sqldata = (void*)password;
#line 104 "dbcalls.sqc"
      sql_setdlist[2].sqlind = 0L;
#line 104 "dbcalls.sqc"
      sqlasetd(2,0,3,sql_setdlist,0L);
    }
#line 104 "dbcalls.sqc"
  sqlacall((unsigned short)29,5,2,0,0L);
#line 104 "dbcalls.sqc"
  sqlastop(0L);
}

#line 104 "dbcalls.sqc"
 
	  CHECKERR ( "CONNECT TO DATABASE" );

	return 0 ;		
}



BOOL insertProduct( char * pProduct_ID, char * pProduct_Name, 
				 char * pShort_Name, char * pDescription,
				 char * pSupplier_ID )
{
	printf( "About to add %s to ISVAPPS database...\n", pProduct_Name ) ;

	strcpy ( product_id, pProduct_ID ) ;
	strcpy ( product_name, pProduct_Name ) ;
	strcpy ( short_name, pShort_Name ) ;
	strcpy ( description, pDescription ) ;
	strcpy ( supplier_id, pSupplier_ID ) ;

      
/*
EXEC SQL INSERT INTO ISVAPPS 
			VALUES ( :product_id, :product_name, :short_name, 
				   :description, null, null, null, null, null, null,
				   :supplier_id, null, null, null ) ;
*/

{
#line 127 "dbcalls.sqc"
  sqlastrt(sqla_program_id, &sqla_rtinfo, &sqlca);
#line 127 "dbcalls.sqc"
  sqlaaloc(2,5,2,0L);
    {
      struct sqla_setd_list sql_setdlist[5];
#line 127 "dbcalls.sqc"
      sql_setdlist[0].sqltype = 460; sql_setdlist[0].sqllen = 11;
#line 127 "dbcalls.sqc"
      sql_setdlist[0].sqldata = (void*)product_id;
#line 127 "dbcalls.sqc"
      sql_setdlist[0].sqlind = 0L;
#line 127 "dbcalls.sqc"
      sql_setdlist[1].sqltype = 460; sql_setdlist[1].sqllen = 41;
#line 127 "dbcalls.sqc"
      sql_setdlist[1].sqldata = (void*)product_name;
#line 127 "dbcalls.sqc"
      sql_setdlist[1].sqlind = 0L;
#line 127 "dbcalls.sqc"
      sql_setdlist[2].sqltype = 460; sql_setdlist[2].sqllen = 11;
#line 127 "dbcalls.sqc"
      sql_setdlist[2].sqldata = (void*)short_name;
#line 127 "dbcalls.sqc"
      sql_setdlist[2].sqlind = 0L;
#line 127 "dbcalls.sqc"
      sql_setdlist[3].sqltype = 460; sql_setdlist[3].sqllen = 80;
#line 127 "dbcalls.sqc"
      sql_setdlist[3].sqldata = (void*)description;
#line 127 "dbcalls.sqc"
      sql_setdlist[3].sqlind = 0L;
#line 127 "dbcalls.sqc"
      sql_setdlist[4].sqltype = 460; sql_setdlist[4].sqllen = 11;
#line 127 "dbcalls.sqc"
      sql_setdlist[4].sqldata = (void*)supplier_id;
#line 127 "dbcalls.sqc"
      sql_setdlist[4].sqlind = 0L;
#line 127 "dbcalls.sqc"
      sqlasetd(2,0,5,sql_setdlist,0L);
    }
#line 127 "dbcalls.sqc"
  sqlacall((unsigned short)24,1,2,0,0L);
#line 127 "dbcalls.sqc"
  sqlastop(0L);
}

#line 127 "dbcalls.sqc"
 
	  CHECKERR ( "INSERT INTO TABLE" );

	
	return 0 ;
}	

BOOL getProduct( PProduct pProduct )
{
	
	strcpy( product_id, pProduct->product_id ) ;
	printf( "About to select a product with id of %s\n", pProduct->product_id ) ;

	
/*
EXEC SQL SELECT  PRODUCT_NAME, SHORT_NAME, DESCRIPTION, SUPPLIER_ID
			INTO :product_name, :short_name, :description, :supplier_id
			FROM ISVAPPS WHERE PRODUCT_ID = :product_id ;
*/

{
#line 142 "dbcalls.sqc"
  sqlastrt(sqla_program_id, &sqla_rtinfo, &sqlca);
#line 142 "dbcalls.sqc"
  sqlaaloc(2,1,3,0L);
    {
      struct sqla_setd_list sql_setdlist[1];
#line 142 "dbcalls.sqc"
      sql_setdlist[0].sqltype = 460; sql_setdlist[0].sqllen = 11;
#line 142 "dbcalls.sqc"
      sql_setdlist[0].sqldata = (void*)product_id;
#line 142 "dbcalls.sqc"
      sql_setdlist[0].sqlind = 0L;
#line 142 "dbcalls.sqc"
      sqlasetd(2,0,1,sql_setdlist,0L);
    }
#line 142 "dbcalls.sqc"
  sqlaaloc(3,4,4,0L);
    {
      struct sqla_setd_list sql_setdlist[4];
#line 142 "dbcalls.sqc"
      sql_setdlist[0].sqltype = 460; sql_setdlist[0].sqllen = 41;
#line 142 "dbcalls.sqc"
      sql_setdlist[0].sqldata = (void*)product_name;
#line 142 "dbcalls.sqc"
      sql_setdlist[0].sqlind = 0L;
#line 142 "dbcalls.sqc"
      sql_setdlist[1].sqltype = 460; sql_setdlist[1].sqllen = 11;
#line 142 "dbcalls.sqc"
      sql_setdlist[1].sqldata = (void*)short_name;
#line 142 "dbcalls.sqc"
      sql_setdlist[1].sqlind = 0L;
#line 142 "dbcalls.sqc"
      sql_setdlist[2].sqltype = 460; sql_setdlist[2].sqllen = 80;
#line 142 "dbcalls.sqc"
      sql_setdlist[2].sqldata = (void*)description;
#line 142 "dbcalls.sqc"
      sql_setdlist[2].sqlind = 0L;
#line 142 "dbcalls.sqc"
      sql_setdlist[3].sqltype = 460; sql_setdlist[3].sqllen = 11;
#line 142 "dbcalls.sqc"
      sql_setdlist[3].sqldata = (void*)supplier_id;
#line 142 "dbcalls.sqc"
      sql_setdlist[3].sqlind = 0L;
#line 142 "dbcalls.sqc"
      sqlasetd(3,0,4,sql_setdlist,0L);
    }
#line 142 "dbcalls.sqc"
  sqlacall((unsigned short)24,2,2,3,0L);
#line 142 "dbcalls.sqc"
  sqlastop(0L);
}

#line 142 "dbcalls.sqc"

	  CHECKERR ( "SELECT FROM TABLE BY ID" );

	strcpy( pProduct->product_name, product_name ) ;
	strcpy( pProduct->short_name, short_name ) ;
	strcpy( pProduct->description, description ) ;
	strcpy( pProduct->supplier_id, supplier_id ) ;
	return 0 ;

}

long int queryCount( )
{
	
/*
EXEC SQL SELECT COUNT(*) 
		INTO :count
		FROM isvapps ;
*/

{
#line 157 "dbcalls.sqc"
  sqlastrt(sqla_program_id, &sqla_rtinfo, &sqlca);
#line 157 "dbcalls.sqc"
  sqlaaloc(3,1,5,0L);
    {
      struct sqla_setd_list sql_setdlist[1];
#line 157 "dbcalls.sqc"
      sql_setdlist[0].sqltype = 496; sql_setdlist[0].sqllen = 4;
#line 157 "dbcalls.sqc"
      sql_setdlist[0].sqldata = (void*)&count;
#line 157 "dbcalls.sqc"
      sql_setdlist[0].sqlind = 0L;
#line 157 "dbcalls.sqc"
      sqlasetd(3,0,1,sql_setdlist,0L);
    }
#line 157 "dbcalls.sqc"
  sqlacall((unsigned short)24,3,0,3,0L);
#line 157 "dbcalls.sqc"
  sqlastop(0L);
}

#line 157 "dbcalls.sqc"

	   CHECKERR( "COUNT" ) ;

	return count ;	
}


/* Good candidate for a remote procedure. */
BOOL queryProductIDsAndNames( long int *p_lBufferSize, char * p_Buffer )
{
	
/*
EXEC SQL INCLUDE SQLCA;
*/

/* SQL Communication Area - SQLCA - structures and constants */
#include "sqlca.h"
struct sqlca sqlca;


#line 167 "dbcalls.sqc"

	short int bufferind = 0 ;
	short int sizeind = 0 ;
	struct sqlda *inout_sqlda = NULL ;
	
	strcpy( spQueryProductIDsAndNames, "AMFuncs.dll!spQueryProductIDsAndNames" ) ;

	inout_sqlda = ( struct sqlda * )malloc( SQLDASIZE( 1 ) );
	memset( inout_sqlda, 0, sizeof( SQLDASIZE( 1 ) ) ) ;
	//: sqlda header info
	
   	inout_sqlda->sqln = 1 ; 		//: number of SQLVARs--unchanged by DBM
   	inout_sqlda->sqld = 1 ;			//: number of columns in result table--set by DBM

 	//:  sqlda->sqlvar base info
   	inout_sqlda->sqlvar[0].sqltype = SQL_TYP_CLOB ;  //: Up to 2GBs of storage
   	inout_sqlda->sqlvar[0].sqldata = p_Buffer ;
   	inout_sqlda->sqlvar[0].sqllen  = 0 ;
   	inout_sqlda->sqlvar[0].sqlind  = &bufferind ;

	//: sqlda->sqlvar secondary info--required for CLOB
	SETSQLDOUBLED( inout_sqlda, SQLDOUBLED ) ;  //: For CLOB
	SETSQLDALONGLEN( inout_sqlda, 0 , *p_lBufferSize ) ;  
	SETSQLDALENPTR( inout_sqlda, 0, p_lBufferSize ) ;

		
	printf( "About to call storedprocedure...\n" ) ;

	
/*
EXEC SQL CALL :spQueryProductIDsAndNames USING DESCRIPTOR :*inout_sqlda;
*/

{
#line 195 "dbcalls.sqc"
  sqlastrt(sqla_program_id, &sqla_rtinfo, &sqlca);
#line 195 "dbcalls.sqc"
  sqlaaloc(2,1,6,0L);
    {
      struct sqla_setd_list sql_setdlist[1];
#line 195 "dbcalls.sqc"
      sql_setdlist[0].sqltype = 460; sql_setdlist[0].sqllen = 50;
#line 195 "dbcalls.sqc"
      sql_setdlist[0].sqldata = (void*)spQueryProductIDsAndNames;
#line 195 "dbcalls.sqc"
      sql_setdlist[0].sqlind = 0L;
#line 195 "dbcalls.sqc"
      sqlasetd(2,0,1,sql_setdlist,0L);
    }
#line 195 "dbcalls.sqc"
  sqlausda(1,&*inout_sqlda,0L);
#line 195 "dbcalls.sqc"
  sqlacall((unsigned short)42,4,2,1,0L);
#line 195 "dbcalls.sqc"
  sqlastop(0L);
}

#line 195 "dbcalls.sqc"
 
   		CHECKERR ("SP CALL WITH SQLDA");

	printf( "Stored procedure has returned...\n" ) ;
	*p_lBufferSize = *(inout_sqlda->sqlvar[0].sqlind) ;
	free( inout_sqlda ) ;
 	return 0 ;
		
}


BOOL commit( BOOL bool )
{
	if( bool )
	{
		
/*
EXEC SQL COMMIT ;
*/

{
#line 210 "dbcalls.sqc"
  sqlastrt(sqla_program_id, &sqla_rtinfo, &sqlca);
#line 210 "dbcalls.sqc"
  sqlacall((unsigned short)21,0,0,0,0L);
#line 210 "dbcalls.sqc"
  sqlastop(0L);
}

#line 210 "dbcalls.sqc"

			CHECKERR( "COMMIT" ) ;
	}
	else
	{
		
/*
EXEC SQL ROLLBACK ;
*/

{
#line 215 "dbcalls.sqc"
  sqlastrt(sqla_program_id, &sqla_rtinfo, &sqlca);
#line 215 "dbcalls.sqc"
  sqlacall((unsigned short)28,0,0,0,0L);
#line 215 "dbcalls.sqc"
  sqlastop(0L);
}

#line 215 "dbcalls.sqc"

			CHECKERR( "ROLLBACK" ) ;
	}
}
