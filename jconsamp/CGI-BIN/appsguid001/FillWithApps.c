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



#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys\timeb.h>
#include "appsguid.h"

#define password "password"

int main( int argc, char * argv[] )
{
	BOOL sReturnCode = 0 ;
	PProduct pProduct = ( PProduct ) malloc( sizeof( Product ) ) ;
	char * pBuffer ;
	long int count ;
	struct timeb tstart, tstop ;
	unsigned short tstartmsecs, tstopmsecs ;
	unsigned short tused ;
	char * pszToken ;

	sReturnCode = connect( STR_DBNAME, STR_USERID, password ) ;
	if( sReturnCode )
		printf( "Connect error:  %d\n", sReturnCode ) ;
	else
		printf( "Successful connect...\n" ) ;

	count = queryCount( ) ;	
	//: Get current time
	ftime( &tstart ) ;

	pBuffer = (char *) malloc( count * MAX_ID_AND_NAME_LENGTH ) ;
	memset(pBuffer, 0, count * MAX_ID_AND_NAME_LENGTH ) ;
	sReturnCode = queryProductIDsAndNames( &count, pBuffer ) ;
	//: Get time again
	ftime( &tstop ) ;
	tstartmsecs = ( 1000 * tstart.time ) + tstart.millitm ;
	tstopmsecs = ( 1000 * tstop.time ) + tstop.millitm ;
	tused =  tstopmsecs - tstartmsecs ;	//: returned in seconds

	printf( "Content-Type:  text/html\n\n" ) ;
	printf( "<HTML><HEAD><TITLE>ABC Company Employee Information</TITLE></HEAD>" ) ;
	printf( "<BODY><H3>Employee Details</H3><ul>" ) ;
	printf( "<FORM METHOD=\"POST\" ACTION=\"/cgi/emplinfo\">" ) ;
	printf( "<TABLE COLS=2 FRAME=ALL><TBODY>" ) ;
	printf( "<TR>Select a customer:<TD NOWRAP>" ) ;
	printf( "<SELECT NAME=\"names\" SIZE=\"8\" MAXLENGTH=\"40\">" ) ;
	pszToken = strtok( pBuffer, ";" ) ;
	while( pszToken != NULL ) 
	{
		printf( "<OPTION>%s", pszToken ) ;
		pszToken = strtok(NULL, ";" ) ;
	}
	printf( "</SELECT>" ) ;
	printf( "<TD><TABLE COLS=2 FRAME=ALL WIDTH=100%>" ) ;
	printf( "<TR><TD>Department:<TD>Solution Developer Marketing<TR><TD>Salary:<TD>$70,000<TR><TD>Bonus:<TD>$2,000<//TABLE>" ) ;
	printf( "</TBODY></TABLE><INPUT TYPE=\"SUBMIT\" VALUE=\"Query Employee\">" ) ;
	printf( "<BR><BR>\n\nQuery took %u milliseconds...\n\n", tused ) ;
	printf( "</FORM></ul></BODY></HTML>" ) ;

	exit( 0 ) ;
}	
