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

char **getcgivars() ;

int main ( int argc, char* argv[], char* envp[] )
{
	int i ;
	char **cgivars ;
	char szEvent[9] ;
	char szListItem [53] ;
	char szButton[9] ;

	
	strcpy( szListItem, "unknown" ) ;
	strcpy( szButton, "unknown" ) ;
	strcpy( szEvent, argv[1] ) ;

	if( !strcmp( szEvent, "init" ) )
	{
		BOOL sReturnCode ;
		char * pBuffer ;
		long int count ;
		char * pszToken ;
		struct timeb tstart, tstop ;
		unsigned short tstartmsecs, tstopmsecs ;
		unsigned short tused ;


		sReturnCode = connect( "appsguid", "mnix", "password" ) ;
		ftime( &tstart ) ;
		count = queryCount( ) ;	
		pBuffer = (char *) malloc( count * MAX_ID_AND_NAME_LENGTH ) ;
		memset(pBuffer, 0, count * MAX_ID_AND_NAME_LENGTH ) ;
		sReturnCode = queryProductIDsAndNames( &count, pBuffer ) ;
		ftime( &tstop ) ;
		tstartmsecs = ( 1000 * tstart.time ) + tstart.millitm ;
		tstopmsecs = ( 1000 * tstop.time ) + tstop.millitm ;
		tused =  tstopmsecs - tstartmsecs ;	//: returned in seconds

		
		printf( "Content-Type:  text/html\n\n" ) ;
		printf( "<HTML><HEAD><TITLE>" ) ;
		printf( "Solution Developer Marketing Application Guide" ) ;
		printf( "</TITLE></HEAD>" ) ;
		printf( "<BODY>" ) ;
		printf( "<H3>Applications Available for Free Download</H3>" ) ;
		printf( "<ul>" ) ;
		printf( "<FORM METHOD=\"POST\" ACTION=\"/cgi-bin/appsguid.exe?action\">" ) ;
		printf( "<TABLE COLS=2 FRAME=ALL WIDTH=600 NOWRAP>" ) ;
		printf( "<TBODY>" ) ;
		printf( "<TR>Select a product and press REFRESH button:" ) ;
		printf( "<TD NOWRAP>" ) ;
		printf( "<SELECT NAME=\"List\" SIZE=\"8\" MAXLENGTH=\"52\">" ) ;
		pszToken = strtok( pBuffer, ";" ) ;
		while( pszToken != NULL ) 
		{
			printf( "<OPTION>%s", pszToken ) ;
			pszToken = strtok(NULL, ";" ) ;
		}
		printf( "</SELECT>" ) ;
		printf( "<TD>" ) ;
		printf( "<TABLE COLS=2 FRAME=ALL WIDTH=600 NOWRAP>" ) ;
		printf( "<TR>" ) ;
		printf( "<TD><img src=\"http://dbserver/blueball.gif\">Product Name:" ) ;
		printf( "<TD>" ) ;

		printf( "<TR>" ) ;
		printf( "<TD><img src=\"http://dbserver/blueball.gif\">Product ID:" ) ;
		printf( "<TD>" ) ;

		printf( "<TR>" ) ;
		printf( "<TD><img src=\"http://dbserver/blueball.gif\">Description:" ) ;
		printf( "<TD>" ) ;

		printf( "<TR>" ) ;
		printf( "<TD><img src=\"http://dbserver/blueball.gif\">Supplier ID:" ) ;
		printf( "<TD>" ) ;
		printf( "</TABLE>" ) ;

		printf( "</TABLE>" ) ;
		printf( "<BR><INPUT NAME=\"Button\" TYPE=\"SUBMIT\" VALUE=\"Refresh\">" ) ;
		printf( "<SP><SP><INPUT NAME=\"Button\" TYPE=\"SUBMIT\" VALUE=\"Order\">" ) ;
		printf( "</FORM>" ) ;
		printf( "<BR>" ) ;
		printf( "<a href=\"http://www.ibm.com\">Press here to goto product's home page.</a>" ) ;
		printf( "</ul>" ) ;
		printf( "<BR><BR>Note: it took %ld milliseconds to load data.", tused ) ;
		printf( "</BODY></HTML>" ) ;

	}
	else //: Must be an action
	{
		i = 0 ;
           	//: Get the CGI variables
    		cgivars= getcgivars() ;

	


		
		//: Determine passed-in parameters 
    		for ( i = 0; cgivars[i]; i += 2)
		{
			if( !strcmp( cgivars[i], "Button" ) )
			{
				strcpy( szButton, cgivars[i+1] ) ;
			}

			if( !strcmp( cgivars[i], "List" ) )
			{
				strcpy( szListItem, cgivars[i+1] ) ;
			} 
		}
	
		

		//: Take action
		if( !strcmp( szButton, "Refresh" ) )
		{
			BOOL sReturnCode ;
			char * pBuffer ;
			char szProductID[5] ;
			PProduct pProduct = (PProduct) malloc( sizeof( Product ) ) ;
			long int count ;
			char * pszToken ;
			struct timeb tstart, tstop ;
			unsigned short tstartmsecs, tstopmsecs ;
			unsigned short tused ;


			if( !strcmp( szListItem, "unknown" ) )
				exit( 1 ) ;
			memset( pProduct, 0, sizeof( Product ) ) ;
			strncpy( pProduct->product_id, szListItem, 4 ) ;
	

			sReturnCode = connect( "appsguid", "mnix", "password" ) ;
			sReturnCode = getProduct( pProduct ) ;
			ftime( &tstart ) ;
			count = queryCount( ) ;	
			pBuffer = (char *) malloc( count * MAX_ID_AND_NAME_LENGTH ) ;
			memset(pBuffer, 0, count * MAX_ID_AND_NAME_LENGTH ) ;
			sReturnCode = queryProductIDsAndNames( &count, pBuffer ) ;
			ftime( &tstop ) ;
			tstartmsecs = ( 1000 * tstart.time ) + tstart.millitm ;
			tstopmsecs = ( 1000 * tstop.time ) + tstop.millitm ;
			tused =  tstopmsecs - tstartmsecs ;	//: returned in seconds


			printf( "Content-Type:  text/html\n\n" ) ;
			printf( "<HTML><HEAD><TITLE>" ) ;
			printf( "Solution Developer Marketing Application Guide" ) ;
			printf( "</TITLE></HEAD>" ) ;
			printf( "<BODY>" ) ;
			printf( "<H3>Applications Available for Free Download</H3>" ) ;
			printf( "<ul>" ) ;
			printf( "<FORM METHOD=\"POST\" ACTION=\"/cgi-bin/appsguid.exe?action\">" ) ;
			printf( "<TABLE COLS=2 FRAME=ALL WIDTH=600 NOWRAP>" ) ;
			printf( "<TBODY>" ) ;
			printf( "<TR>Select a product and press REFRESH button:" ) ;
			printf( "<TD NOWRAP>" ) ;
			printf( "<SELECT NAME=\"List\" SIZE=\"8\" MAXLENGTH=\"52\">" ) ;
			pszToken = strtok( pBuffer, ";" ) ;
			while( pszToken != NULL ) 
			{
				printf( "<OPTION>%s", pszToken ) ;
				pszToken = strtok(NULL, ";" ) ;
			}
			printf( "</SELECT>" ) ;
			printf( "<TD>" ) ;
			printf( "<TABLE COLS=2 FRAME=ALL WIDTH=600 NOWRAP>" ) ;
			printf( "<TR>" ) ;
			printf( "<TD><img src=\"http://dbserver/blueball.gif\">Product Name:" ) ;
			printf( "<TD>%s", pProduct->product_name  ) ;
	
			printf( "<TR>" ) ;
			printf( "<TD><img src=\"http://dbserver/blueball.gif\">Product ID:" ) ;
			printf( "<TD>%s", pProduct->product_id ) ;
	
			printf( "<TR>" ) ;
			printf( "<TD><img src=\"http://dbserver/blueball.gif\">Description:" ) ;
			printf( "<TD>%s", pProduct->description ) ;

			printf( "<TR>" ) ;
			printf( "<TD><img src=\"http://dbserver/blueball.gif\">Supplier ID:" ) ;
			printf( "<TD>%s", pProduct->supplier_id ) ;
			printf( "</TABLE>" ) ;

			printf( "</TABLE>" ) ;
			printf( "<BR><INPUT NAME=\"Button\" TYPE=\"SUBMIT\" VALUE=\"Refresh\">" ) ;
			printf( "<SP><SP><INPUT NAME=\"Button\" TYPE=\"SUBMIT\" VALUE=\"Order\">" ) ;
			printf( "</FORM>" ) ;
			printf( "<BR>" ) ;
			printf( "<a href=\"%s\">Press here to goto product's home page.</a>", pProduct->url ) ;
			printf( "</ul>" ) ;
			printf( "<BR><BR>Note: it took %ld milliseconds to load data.", tused ) ;
			printf( "</BODY></HTML>" ) ;

	
		}

		else if( !strcmp( szButton, "Order" ) ) 
		{
		}

		else
		{
			printf( "Content-Type:  text/html\n\n" ) ;
			printf( "<HTML><HEAD><TITLE>" ) ;
			printf( "Solution Developer Marketing Application Guide" ) ;
			printf( "</TITLE></HEAD>" ) ;
			printf( "<BODY>" ) ;
			printf( "Missed the boat" ) ;
			printf( "</BODY></HTML>" ) ;

		}

     
      
	     	
	}

	exit( 0 ) ;
}


/** Read the CGI input and place all name/val pairs into list.        **/
/** Returns list containing name1, value1, name2, value2, ... , NULL  **/
char **getcgivars() {
    register int i ;
    char *request_method ;
    int content_length;
    char *cgiinput ;
    char **cgivars ;
    char **pairlist ;
    int paircount ;
    char *nvpair ;
    char *eqpos ;

    /** Depending on the request method, read all CGI input into cgiinput **/
    /** (really should produce HTML error messages, instead of exit()ing) **/
    request_method= getenv("REQUEST_METHOD") ;
    if (!strcmp(request_method, "GET")) {
        cgiinput= strdup(getenv("QUERY_STRING")) ;
    }
    else if (!strcmp(request_method, "POST")) {
        /* strcasecmp() is not supported in Windows-- use strcmpi() instead */
        if ( strcmpi(getenv("CONTENT_TYPE"), "application/x-www-form-urlencoded")) {
            printf("getcgivars(): Unsupported Content-Type.\n") ;
            exit(1) ;
        }
        if ( !(content_length = atoi(getenv("CONTENT_LENGTH"))) ) {
            printf("getcgivars(): No Content-Length was sent with the POST request.\n") ;
            exit(1) ;
        }
        if ( !(cgiinput= (char *) malloc(content_length+1)) ) {
            printf("getcgivars(): Could not malloc for cgiinput.\n") ;
            exit(1) ;
        }
        if (!fread(cgiinput, content_length, 1, stdin)) {
            printf("Couldn't read CGI input from STDIN.\n") ;
            exit(1) ;
        }
        cgiinput[content_length]='\0' ;
    }
    else {
        printf("getcgivars(): unsupported REQUEST_METHOD\n") ;
        exit(1) ;
    }

    /** Change all plusses back to spaces **/
    for(i=0; cgiinput[i]; i++) if(cgiinput[i] == '+') cgiinput[i] = ' ' ;

    /** First, split on "&" to extract the name-value pairs into pairlist **/
    pairlist= (char **) malloc(256*sizeof(char **)) ;
    paircount= 0 ;
    nvpair= strtok(cgiinput, "&") ;
    while (nvpair) {
        pairlist[paircount++]= strdup(nvpair) ;
        if (!(paircount%256))
            pairlist= (char **) realloc(pairlist,(paircount+256)*sizeof(char **)) ;
        nvpair= strtok(NULL, "&") ;
    }
    pairlist[paircount]= 0 ;    /* terminate the list with NULL */

    /** Then, from the list of pairs, extract the names and values **/
    cgivars= (char **) malloc((paircount*2+1)*sizeof(char **)) ;
    for (i= 0; i<paircount; i++) {
        if (eqpos=strchr(pairlist[i], '=')) {
            *eqpos= '\0' ;
            cgivars[i*2+1]= strdup(eqpos+1) ;
        } else {
            cgivars[i*2+1]= strdup("") ;
        }
        cgivars[i*2]= strdup(pairlist[i]) ;
    }
    cgivars[paircount*2]= 0 ;   /* terminate the list with NULL */
    
    /** Free anything that needs to be freed **/
    free(cgiinput) ;
    for (i=0; pairlist[i]; i++) free(pairlist[i]) ;
    free(pairlist) ;

    /** Return the list of name-value strings **/
    return cgivars ;
    
}
