static char sqla_program_id[40] = 
{111,65,65,66,65,73,65,69,77,78,73,88,32,32,32,32,65,77,70,85,
78,67,83,32,119,65,118,109,87,68,70,79,48,49,49,49,49,50,32,32};


#include "sqladef.h"

static struct sqla_runtime_info sqla_rtinfo = 
{{'S','Q','L','A','R','T','I','N'}, sizeof(wchar_t), 0, {' ',' ',' ',' '}};


#line 1 "AMFuncs.sqc"
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



#include <memory.h>
#include <string.h>
#include <sqlenv.h>
#include <sqlutil.h>
#include <sqlda.h>
#include <sqlca.h>
#include "util.h"
#include "appsguid.h"

#define  CHECKERR( CE_STR )   if ( check_error ( CE_STR, &sqlca ) != 0 ) return 1;
 
SQL_API_RC SQL_API_FN spQueryProductIDsAndNames(
					void 			*reserved1,     
           				void 			*reserved2,
           				struct sqlda   	*inout_sqlda,
           				struct sqlca   	*ca )
{
   	//: Declare a local SQLCA
   	
/*
EXEC SQL INCLUDE SQLCA;
*/

/* SQL Communication Area - SQLCA - structures and constants */
#include "sqlca.h"
struct sqlca sqlca;


#line 47 "AMFuncs.sqc"

	
/*
EXEC SQL BEGIN DECLARE SECTION;
*/

#line 48 "AMFuncs.sqc"
 
      	char product_id[11] ;
      	char product_name[41] ;
      
/*
EXEC SQL END DECLARE SECTION;
*/

#line 51 "AMFuncs.sqc"

	char * p_Buffer = ( char * ) inout_sqlda->sqlvar[0].sqldata ;


	
/*
EXEC SQL DECLARE c1 CURSOR FOR  
            SELECT PRODUCT_ID, PRODUCT_NAME FROM isvapps ;
*/

#line 56 "AMFuncs.sqc"

            

   	
/*
EXEC SQL OPEN c1;
*/

{
#line 59 "AMFuncs.sqc"
  sqlastrt(sqla_program_id, &sqla_rtinfo, &sqlca);
#line 59 "AMFuncs.sqc"
  sqlacall((unsigned short)26,1,0,0,0L);
#line 59 "AMFuncs.sqc"
  sqlastop(0L);
}

#line 59 "AMFuncs.sqc"
  
   		CHECKERR ( "OPEN CURSOR" ) ;

   	do {
      	
/*
EXEC SQL FETCH c1 INTO :product_id, :product_name ;
*/

{
#line 63 "AMFuncs.sqc"
  sqlastrt(sqla_program_id, &sqla_rtinfo, &sqlca);
#line 63 "AMFuncs.sqc"
  sqlaaloc(3,2,1,0L);
    {
      struct sqla_setd_list sql_setdlist[2];
#line 63 "AMFuncs.sqc"
      sql_setdlist[0].sqltype = 460; sql_setdlist[0].sqllen = 11;
#line 63 "AMFuncs.sqc"
      sql_setdlist[0].sqldata = (void*)product_id;
#line 63 "AMFuncs.sqc"
      sql_setdlist[0].sqlind = 0L;
#line 63 "AMFuncs.sqc"
      sql_setdlist[1].sqltype = 460; sql_setdlist[1].sqllen = 41;
#line 63 "AMFuncs.sqc"
      sql_setdlist[1].sqldata = (void*)product_name;
#line 63 "AMFuncs.sqc"
      sql_setdlist[1].sqlind = 0L;
#line 63 "AMFuncs.sqc"
      sqlasetd(3,0,2,sql_setdlist,0L);
    }
#line 63 "AMFuncs.sqc"
  sqlacall((unsigned short)25,1,0,3,0L);
#line 63 "AMFuncs.sqc"
  sqlastop(0L);
}

#line 63 "AMFuncs.sqc"
  
      	if (SQLCODE != 0) break;
		strcat( p_Buffer, product_id ) ;
		strcat( p_Buffer, " " ) ;
		strcat( p_Buffer, product_name ) ;
		strcat( p_Buffer, ";" ) ;
      	
   	} while ( true );

   	
/*
EXEC SQL CLOSE c1;
*/

{
#line 72 "AMFuncs.sqc"
  sqlastrt(sqla_program_id, &sqla_rtinfo, &sqlca);
#line 72 "AMFuncs.sqc"
  sqlacall((unsigned short)20,1,0,0,0L);
#line 72 "AMFuncs.sqc"
  sqlastop(0L);
}

#line 72 "AMFuncs.sqc"
  
   		CHECKERR ("CLOSE CURSOR");

 
  
 
   return(SQLZ_HOLD_PROC);  		//: HOLD keeps DLL loaded, while DISCONNECT frees it from memory
}

