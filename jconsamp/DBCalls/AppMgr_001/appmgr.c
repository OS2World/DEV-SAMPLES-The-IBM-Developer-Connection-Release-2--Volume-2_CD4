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


int main( int argc, char * argv[] )
{
	BOOL sReturnCode = 0 ;
	PProduct pProduct = ( PProduct ) malloc( sizeof( Product ) ) ;
	short int i ;


  	if( argc != 2 )
	{
		printf( "Usage: APPMGR password\n" ) ;
		exit( 1 ) ;
	}

	printf( "About to connect with %s using ID of %s and PW of %s\n", STR_DBNAME, STR_USERID, argv[1] ) ;
	sReturnCode = connect( STR_DBNAME, STR_USERID, argv[1] ) ;
	if( sReturnCode )
		printf( "Connect error:  %d\n", sReturnCode ) ;
	else
		printf( "Successful connect...\n" ) ;

	printf( "About to insert rows into ISVAPPS table...\n" ) ;
	{
					//:    product_id		product_name			short_name		description							supplier_id
					//:    ----------      	------------			----------		-----------							-----------
		char* products[10][5] = {"1001",    	"Ticker Tape", 			"TickerTa", 	"Display you messages in a Java applet",		"2000",
						 "1002", 		"Map Locator",			"MapLoc",		"Find your location thru your web-browser",	"2000",
						 "1003",		"BusWiz for Java", 		"BusWiz",		"Manage your business on your PC",			"2001",
						 "1004",	 	"Busy Clock",			"BusyClck",		"Keep time while your work?",				"2002",
						 "1005",		"Personal Productivty",		"PerProd",		"Be more productive at home and work", 		"2002",
						 "1006",		"Web Analyzer",			"WebAnaly",		"Load balance your Web-Site!",			"2002",
						 "1007",		"Lotus Domino",			"Domino",		"Best groupware and mail server there is!",	"2003",
						 "1008",		"Lotus Domino Go Webserver",	"GOWebSrv",		"Best web-server anywhere!",				"2003",
						 "1009",		"VisualAge for Java",		"VAJava",		"Visually compose and deploy JavaBeans!",		"2003",
						 "1010",		"NetObjects Fusion",		"Fusion",		"Visually design and create web-sites!",		"2003" } ;
						
		for( i = 0; i < 10; i++ )
		{
			sReturnCode = insertProduct( products[i][0], products[i][1], products[i][2], products[i][3], products[i][4] ) ;
			if( sReturnCode )
				printf( "Insert error:  %d\n", sReturnCode ) ;
			else
				printf( "Successful insertion...\n" ) ;

		} //: end for loop
		commit( true ) ;
	}
	


	printf( "About to query a single row of data by product ID...\n" ) ;
	memset( pProduct, 0, sizeof( Product ) ) ;
	strcpy( pProduct->product_id, "1001" ) ;
	sReturnCode = getProduct( pProduct ) ;
	if( sReturnCode )
		printf( "Select error:  %d\n", sReturnCode ) ;
	else
		printf( "\n\nProduct ID:  %s\nProduct Name:  %s\nShort Name:  %s\nDescription:  %s\nSupplier ID:  %s\n\n",
				pProduct->product_id, pProduct->product_name, pProduct->short_name, pProduct->description, pProduct->supplier_id ) ;

	if( pProduct ) free( pProduct ) ;

	

	printf("About to query all product IDs and names...\n\n") ;

	{
		char * pBuffer ;
		long int count = queryCount( ) ;
		struct timeb tstart, tstop ;
		unsigned short tstartmsecs, tstopmsecs ;
		unsigned short tused ;
		
		//: Get current time
		ftime( &tstart ) ;


		printf( "Count is %ld\n", count ) ;
		pBuffer = (char *) malloc( count * MAX_ID_AND_NAME_LENGTH ) ;
		memset(pBuffer, 0, count * MAX_ID_AND_NAME_LENGTH ) ;
		sReturnCode = queryProductIDsAndNames( pBuffer ) ;
		//: Get time again
		ftime( &tstop ) ;
		tstartmsecs = ( 1000 * tstart.time ) + tstart.millitm ;
		tstopmsecs = ( 1000 * tstop.time ) + tstop.millitm ;
		tused =  tstopmsecs - tstartmsecs ;	//: returned in seconds
		printf ( "\n\nQuery took %u milliseconds...\n\n", tused ) ;


		printf( pBuffer ) ;	
		
	}
	


	exit( 0 ) ;
}	
