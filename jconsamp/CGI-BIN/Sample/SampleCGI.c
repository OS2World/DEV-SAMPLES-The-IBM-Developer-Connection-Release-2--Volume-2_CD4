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

char **getcgivars() ;

int main ( int argc, char* argv[], char* envp[] )
{
	int i ;
	char **cgivars ;
        
    	/** First, get the CGI variables into a list of strings         **/
    	cgivars= getcgivars() ;
	
	printf( "Content-Type:  text/html\n\n\n" ) ;
	printf( "<HTML><HEAD><TITLE>CGI Sample Program</TITLE></HEAD><BODY>\n") ;
	printf( "<H2>Sample Heading Goes Here</H2>\n" ) ;
	printf( "<P>Number of arguments passed was %ld\n", argc ) ;
	for(i = 0; i < argc; i++)
	{
		printf( "<P>Argument number %ld is %s\n", i, argv[i] ) ;
	}
	
	printf( "<BR><BR>QUERY_STRING:  %s", getenv( "QUERY_STRING" ) ) ;
	printf( "<BR>REQUEST_METHOD:  %s", getenv( "REQUEST_METHOD" ) ) ;
	printf( "<BR>PATH_INFO:  %s", getenv( "PAHT_INFO" ) ) ;
	printf( "<BR>PATH_TRANSLATED:  %s", getenv( "PATH_TRANSLATED" ) ) ;
	printf( "<BR>REMOTE_USER:  %s", getenv( "REMOTE_USER" ) ) ;
	printf( "<BR>REMOTE_ADDR:  %s", getenv( "REMOTE_ADDR" ) ) ;
	printf( "<BR>REMOTE_HOST:  %s", getenv( "REMOTE_HOST" ) ) ;
	printf( "<BR>SERVER_SOFTWARE:  %s", getenv( "SERVER_SOFTWARE" ) ) ;
	printf( "<BR>SERVER_NAME:  %s", getenv( "SERVER_NAME" ) ) ;
	printf( "<BR>GATEWAY_INTERFACE:  %s", getenv( "GATEWAY_INTERFACE" ) ) ;
	printf( "<BR>SERVER_PROTOCOL:  %s", getenv( "SERVER_PROTOCOL" ) ) ;
	printf( "<BR>SERVER_PORT:  %s", getenv( "SERVER_PORT" ) ) ;
	printf( "<BR>REQUEST_METHOD:  %s", getenv( "REQUEST_METHOD" ) ) ;
	printf( "<BR>HTTP_ACCEPT:  %s", getenv( "HTTP_ACCEPT" ) ) ;
	printf( "<BR>HTTP_REFERER:  %s", getenv( "HTTP_REFERER" ) ) ;
	printf( "<BR>SCRIPT_NAME:  %s", getenv( "SCRIPT_NAME" ) ) ;
	printf( "<BR>AUTH_TYPE:  %s", getenv( "AUTH_TYPE" ) ) ;
	printf( "<BR>CONTENT_TYPE:  %s", getenv( "CONTENT_TYPE" ) ) ;
	printf( "<BR>CONTENT_LENGTH:  %s", getenv( "CONTENT_LENGTH" ) ) ;
	printf( "<BR>Parmameters passed in:" ) ;

	printf("<ul>") ;
    
    /** Print the CGI variables sent by the user.  Note the list of **/
    /**   variables alternates names and values, and ends in NULL.  **/
    for (i=0; cgivars[i]; i+= 2)
        printf("<li>%s = %s\n", cgivars[i], cgivars[i+1]) ;
        
    printf("</ul>") ;





	printf( "</BODY></HTML>\n" ) ;
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
