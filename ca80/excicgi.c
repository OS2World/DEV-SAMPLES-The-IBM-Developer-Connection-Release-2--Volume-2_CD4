#pragma XOPTS(EXCI,NOS)
/*:refstep.excicgi *************************************** EXCICGI 1 **/
/*                                                                    */
/*                                                                    */
/* MODULE NAME      EXCICGI.C                                         */
/*                                                                    */
/* DESCRIPTIVE NAME CICS for MVS/ESA EXCI CGI Sample Program          */
/*                                                                    */
/* Statement:       Licensed Materials - Property of IBM              */
/*                                                                    */
/*                  CA1C SupportPac                                   */
/*                  (c) Copyright IBM Corp. 1996                      */
/*                                                                    */
/*                  All rights reserved.                              */
/*                                                                    */
/*                  U.S. Government Users Restricted Rights - use,    */
/*                  duplication or disclosure restricted by GSA       */
/*                  ADP Schedule Contract with IBM Corp.              */
/*                                                                    */
/* Status:          Version 1 Release 0 Last Modified:   22 May 1996  */
/* Author:          Steve Wood                                        */
/*                                                                    */
/*  NOTES :-                                                          */
/*    DEPENDENCIES = CICS for MVS/ESA V4.1                            */
/*    CHARACTER SET = Use is made of Trigraphs to represent square    */
/* brackets                                                           */
/*                              throughout the source to attempt to   */
/*                              minimize code page conversion         */
/* problems as                                                        */
/*                              the source is move between systems.   */
/* The                                                                */
/*                              trigraphs used are ??( for left       */
/* square                                                             */
/*                              bracket and ??) for right square      */
/* bracket.                                                           */
/*                                                                    */
/* This program is a CGI program which aims to demonstrate the use of */
/* an EXCI call into a CICS Region in order to invoke a CICS          */
/* Transaction as the result of HTML Forms input from a WEB browser.  */
/*                                                                    */
/* Initially include the necessary definitions and functions.         */
/*                                                                    */
/* When initially invoked the first action that is necessary is to    */
/* set the content type of the information that this program is going */
/* to return to the browser. This is set in the HTTP header and for   */
/* this program is HTML in all cases. The HTTP header MUST be         */
/* followed by a blank line hence the specification of two line       */
/* feeds.                                                             */
/*                                                                    */
/* If this program has been driven directly either via an HTML        */
/* reference or directly by a URL specification then no content will  */
/* be associated with the input and the 'content_length' will be 0.   */
/* In this case the initial fillout form is returned.                 */
/*                                                                    */
/* If this program has been driven as a result of a submit on the     */
/* initial fillout form then the content_length will be non zero and  */
/* the input stream can be read from stdin.                           */
/*                                                                    */
/* The input stream will contain the CICS applid to be used. The      */
/* applid will be the first value in the stream and will placed after */
/* the first '=' that appears in the stream. The Input stream is      */
/* tokenised so as to set the applid pointer to the CICS applid value */
/* in the input stream.                                               */
/*                                                                    */
/* The applid may contain 'blanks' which are represented in the input */
/* stream by '+', the applid must therefore be passed and the '+'     */
/* characters changed to spaces.                                      */
/*                                                                    */
/* Once the applid has been derived the EXCI function can be driven   */
/* and the results returned.                                          */
/*                                                                    */
/**********************************************************************/
 
/*:refstep.dxcc_intro ************************************ DFHDXCC 1 **/
/*                                                                    */
/* PROGRAM NAME: DFH$DXCC                                             */
/*                                                                    */
/* TITLE: C Sample Batch Client Program.                              */
/*                                                                    */
/* PROGRAM DESCRIPTION:                                               */
/*                                                                    */
/* This program will show an example of how to code a simple batch    */
/* type application using the EXCI call and EXEC level APIs.          */
/*                                                                    */
/* SPECIAL REQUIREMENTS: The program must be translated with the EXCI */
/* option, and run amode 31 in order to use the External CICS         */
/* interface.                                                         */
/*                                                                    */
/* SYSTEM LEVEL:  CICS 4.1 or higher.                                 */
/*                                                                    */
/* INPUT:                                                             */
/*                                                                    */
/* The target CICS system with which this batch client program        */
/* communicates can be specified via the input form. otherwise it     */
/* defaults to applid DBDCCICS. The target file is hardcoded as       */
/* FILEA.                                                             */
/*                                                                    */
/* OUTPUT: The whole of the target file will be output to stdout.     */
/*                                                                    */
/* CHARACTER SET = Use is made of Trigraphs to represent square       */
/*                 brackets throughout the source to attempt to       */
/*                 minimize code page conversion problems as the      */
/*                 source is move between systems. The trigraphs used */
/*                 are ??( for left square bracket                    */
/*                 and ??) for right square bracket                   */
/*                                                                    */
/*                                                                    */
/*                                                                    */
/*                                                                    */
/**********************************************************************/
/*:erefstep.dxcc_intro ************************************************/
/*:refstep.Includes ************************************ EXCICGI 125 **/
/*                                                                    */
/* Inlcude the header files necessary for the excicgi program         */
/*                                                                    */
/* - stdio - standard C input/output functions                        */
/*                                                                    */
/* - stdlib - standard  C functions                                   */
/*                                                                    */
/* - string - C string manipulation functions                         */
/*                                                                    */
/* Include the headers required for the EXCI sample.                  */
/*                                                                    */
/**********************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*:refstep.dxcc_includes ******************************** DFHDXCC 30 **/
/*                                                                    */
/* Include standard libraries and the EXCI C parameter list           */
/* definition.                                                        */
/*                                                                    */
/**********************************************************************/
 #include <ctype.h>
 #include "dfhxcplh.h"
/*:erefstep.dxcc_includes *********************************************/
/*:erefstep.Includes **************************************************/
/*:refstep.Type_definitions **************************** EXCICGI 141 **/
/*                                                                    */
/* Define the Constants, Types and Structures used internally by      */
/* excicgi.                                                           */
/*                                                                    */
/* - LF - a constant representing the ASCII character value for Line  */
/*   Feed.                                                            */
/*                                                                    */
/* - HTML_BREAK - a blank line in the form.                           */
/*                                                                    */
/* Include the defines required for the EXCI C Sample program.        */
/*                                                                    */
/**********************************************************************/
#define LF      '\n'
#define HTML_BREAK      printf("<P>%c", LF);
/*:refstep.dxcc_defines ********************************* DFHDXCC 37 **/
/*                                                                    */
/* First define program flags general program flags no_abort and yes. */
/* Define server call-type codes followed by the Server return codes. */
/* Define the Commarea Length and Data Length constants(in bytes).    */
/* Define the program name required for the Call level function.      */
/* Define types for the Commarea map, the Unit of work id map and the */
/* Bit options.                                                       */
/*                                                                    */
/**********************************************************************/
 #define no_abort   0
 #define yes        1
 
 #define init_call  1
 #define read_call  2
 #define close_call 3
 
 #define ret_browse 0
 #define ret_nobrws 4
 #define ret_eof    8
 #define ret_nofile 12
 
 #define COMMAREA_LENGTH 98
 #define DATA_LENGTH     18
 
 #define PROGRAM_NAME    "BATCHCLI"
 
                                              /* Commarea Map         */
 typedef struct { long int call_type;         /*  Server Call-type    */
                  char file_name??(8??);          /* Target-file name area*/
                  char rid_field??(6??);          /*  Rid_Field area      */
                  char record_area??(80??);       /*  Return area for the */
                } comms;                      /*   retrieved records  */
                                              /*======================*/
                                              /*======================*/
                                              /* Unit of Work Id Map  */
 typedef struct { unsigned int length :8;     /*                      */
                  unsigned int logunitlen :8; /*                      */
                  char logunitname??(17??);       /*                      */
                  char stck??(6??);               /*                      */
                  char sequence??(2??);           /*                      */
                } uow;                        /*                      */
                                              /*======================*/
                                              /*======================*/
                                              /* Bit Def'n for options*/
 typedef struct { unsigned int option_bit :8; } bit;
                                              /*======================*/
/*:erefstep.dxcc_defines **********************************************/
/*:refstep.dxcc_message_constants ********************** DFHDXCC 106 **/
/*                                                                    */
/* Character constants for messages to be output.                     */
/*                                                                    */
/**********************************************************************/
 char *Msg00="";
 char *Msg01="*===================== EXCI Sample Batch"
             " Client Program =======================*";
 char *Msg02="*                                       "
             "                                       *";
 char *Msg03="*  EXEC Level Processor.                "
             "                                       *";
 char *Msg04="*    Setting up the EXEC level call.    "
             "                                       *";
 char *Msg05="*    The Link Request has successfully c"
             "ompleted.                              *";
 char *Msg06="*    Server Response:                   "
             "                                       *";
 char *Msg07="*      The file is set to a browsable st"
             "ate.                                   *";
 char *Msg08="*      The file could not be found.     "
             "                                       *";
 char *Msg09="*      The file could not be successfull"
             "y initialised                          *";
 char *Msg10="*      A serious error was detected.    "
             "                                       *";
 char *Msg11="*    The Link Request has failed.  Retur"
             "n codes are:                           *";
 char *Msg13="*    A message was received from the tar"
             "get CICS system:                       *";
 char *Msg14="*    Aborting further processing        "
             "                                       *";
 char *Msg15="*  CALL Level Processor.                "
             "                                       *";
 char *Msg16="*    Initialise_User call complete.     "
             "                                       *";
 char *Msg17="*    Allocate_Pipe call complete.       "
             "                                       *";
 char *Msg18="*    Open_Pipe call complete.           "
             "                                       *";
 char *Msg19="*    The connection has been successful."
             "                                       *";
 char *Msg20="*      The target file follows:         "
             "                                       *";
 char *Msg21="*=========================== Top of File"
             " ======================================*";
 char *Msg22="*=========================== End of File"
             " ======================================*";
 char *Msg23="*    The connection has been unsuccessfu"
             "l.  Return codes are:                  *";
 char *Msg25="*      The DPL request has failed.      "
             "                                       *";
 char *Msg26="*      Link return codes are:           "
             "                                       *";
 char *Msg27="*      DPL return codes are:            "
             "                                       *";
 char *Msg28="*    Closing DPL Request has been attemp"
             "ted.                                   *";
 char *Msg29="*    Close_Pipe call complete.          "
             "                                       *";
 char *Msg30="*    Deallocate_Pipe call complete.     "
             "                                       *";
 char *Msg31="*=================== End of EXCI Sample "
             "Batch Client Program ==================*";
 
 int  batch_return_code;
/*:erefstep.dxcc_message_constants ************************************/
/*:erefstep.Type_definitions ******************************************/
/*:refstep.Internal_functions ************************** EXCICGI 156 **/
/*                                                                    */
/* Declare the prototypes for the internal functions, then include    */
/* the implementations of these functions.                            */
/*                                                                    */
/**********************************************************************/
void send_doc(int page_number);
/*:refstep.dxcc_functions ******************************* DFHDXCC 84 **/
/*                                                                    */
/* Define internal DFH$DXCC function prototypes, then include each    */
/* function.                                                          */
/*                                                                    */
/**********************************************************************/
 int  dxcc_sample(char*);
 int  exec_call(char??(8??) ,char??(8??) ,char??(8??) ,char??(8??));
 void call_level(char??(8??) ,char??(8??) ,char??(8??) ,char??(8??));
 void getfile(char??(8??),char??(4??) ,char??(8??) ,char??(8??) , long
int ,long int);
 void linkrets(exci_exec_return_code *);
 void callrets(exci_return_code *);
 
/*:refstep.dxcc_function ******************************* DFHDXCC 173 **/
/*                                                                    */
/* Invoke the CICS identified by 'applid' via the EXCI batch          */
/* inteface, initially use the EXEC level call to intiialse the       */
/* target file, then use the CALL level to retrieve the file          */
/* contents.                                                          */
/*                                                                    */
/**********************************************************************/
 
 int dxcc_sample(char *applid)
 {
 /*===================================================================*/
 /*   Declare Target information variables.                           */
 /*===================================================================*/
    char target_file??(8??);
    char target_transid??(4??);
    char target_program??(8??);
    char target_system??(8??);
    char *ptarget_system = target_system;
 
 /*===================================================================*/
 /*   Declare function-specific variables.                            */
 /*===================================================================*/
    int  abort_needed;
    int  parm_len;
                                               /*=====================*/
    printf("%80.80s\n", Msg00);                /* Msg=''              */
    printf("%80.80s\n", Msg01);                /*Msg='*=EXCI Sample=*'*/
    printf("%80.80s\n", Msg02);                /* Msg#'*     *'       */
                                               /*=====================*/
 /*===================================================================*/
 /*   Get the passed applid of CICS system if specified */
 /*===================================================================*/
    parm_len = strlen(applid);
    if (parm_len > 8)
      {
        parm_len=8;
      }
    if (parm_len > 0)
    {
      strncpy(target_system,"        ",8);
      strncpy(target_system,applid,parm_len);
    }
    else
    {
      strncpy(target_system,"DBDCCICS",8);
    }
 /*===================================================================*/
 /*   Initialise Target information to point to a file residing on the*/
 /*   target_system, where the correct connection, session, & program */
 /*   definitions have been installed.  The target_transaction should */
 /*   point to the DFHMIRS program.  The target_program is the CICS   */
 /*   application server which is to be invoked by the DPL call.      */
 /*===================================================================*/
 
    strncpy(target_file,"FILEA   ",8);
    strncpy(target_transid,"EXCI",4);
    strncpy(target_program,"DFH$AXCS",8);
    batch_return_code = 0;
 
 /*===================================================================*/
 /*   Perform the EXEC level call to attempt to initialise the target */
 /*   file.  If this is successful then further processing via the    */
 /*   EXCI call level function to retrieve the whole target file.     */
 /*===================================================================*/
    abort_needed=exec_call(target_file,
                           target_system,
                           target_transid,
                           target_program);
    if (abort_needed==no_abort)
    {
       call_level(target_file,
                  target_system,
                  target_transid,
                  target_program);
    }
                                               /*=====================*/
    printf("%80.80s\n", Msg02);                /* Msg='*      *'      */
    printf("%80.80s\n", Msg31);                /* Msg='*= END =*'     */
    return (batch_return_code);                /*=====================*/
 }
/*:erefstep.dxcc_function *********************************************/
/*:refstep.dxcc_exec_call ****************************** DFHDXCC 254 **/
/*                                                                    */
/* This routine will use an EXEC level EXCI link request to call into */
/* the target CICS system, and invoke the BATCHSER sample server      */
/* program with a call-type of INIT.  The server will then query the  */
/* existence of the target file, and if it exists,will attempt to     */
/* initialise it to a browsable state.  If the call or the server     */
/* fails in any way, all further processing will be aborted.          */
/*                                                                    */
/**********************************************************************/
 int exec_call(char file??(8??),
               char system??(8??),
               char transid??(4??),
               char program??(8??))
 {
 /*===================================================================*/
 /*   Declare local variables.                                        */
 /*===================================================================*/
                                               /*=====================*/
 exci_exec_return_code link_retarea;           /* EXEC Return area.   */
 comms     commarea;                           /* EXEC Commarea       */
 short int link_commlength;                    /* Length of Commarea  */
 short int link_datalength;                    /* Len of outbound area*/
 short int abort_needed;                       /* Abort flag.         */
 long  int server_retcode;                     /* Return code area    */
                                               /*                     */
    printf("%80.80s\n", Msg03);                /* Msg='EXEC level'    */
                                               /*=====================*/
 /*===================================================================*/
 /*   Set the Commarea Length and Data Length constants(in bytes).    */
 /*===================================================================*/
    link_commlength = COMMAREA_LENGTH;
    link_datalength = DATA_LENGTH;
 
 /*===================================================================*/
 /*   Set up the Outbound Commarea for transmission.                  */
 /*===================================================================*/
                                               /*=====================*/
    printf("%80.80s\n", Msg04);                /*Msg='Setting up Link'*/
    commarea.call_type = init_call;            /* Server calltype INIT*/
    strncpy(commarea.file_name,file,8);        /* Target file name    */
                                               /*=====================*/
 /*===================================================================*/
 /*                                                                   */
 /*   Perform the Link Request.                                       */
 /*                                                                   */
 /*===================================================================*/
    /* EXEC CICS LINK PROGRAM(program)
                      APPLID(system)
                      RETCODE(&link_retarea)
                      COMMAREA(&commarea)
                      LENGTH(link_commlength)
                      DATALENGTH(link_datalength)
                      TRANSID(transid)
                      SYNCONRETURN */
    {
    dfhb0020 = link_commlength;
    dfhb0021 = link_datalength;
    dfhxcie("\x0E\x02\xF7\x00\x0F\x00\x80\x00\x00\xF3\xF2\xF1\xF0\xF0\x\
F0\xF0\xF0",program,&commarea,dfhbp020 ,&link_retarea,dfhdummy,dfhbp021
,system,transid);
     }
 /*===================================================================*/
 /*                                                                   */
 /*   Check on how successful the call was.                           */
 /*                                                                   */
 /*===================================================================*/
                                            /*========================*/
   if (link_retarea.exec_resp==0)           /* Check high lvl response*/
   {                                        /*                        */
      printf("%80.80s\n", Msg05);           /* Msg='Link successful'  */
      server_retcode=commarea.call_type;    /*                        */
      printf("%80.80s\n", Msg06);           /* Msg='Server Response:' */
      switch(server_retcode)                /*  Find out about Server:*/
      {                                     /*                        */
      case ret_browse:                      /*                        */
         printf("%80.80s\n", Msg07);        /* Msg='File browsable'   */
         abort_needed=no_abort;             /*                        */
         break;                             /*                        */
      case ret_nobrws:                      /*                        */
         printf("%80.80s\n", Msg09);        /*Msg='File not browsable'*/
         printf("%80.80s\n", Msg14);        /* Msg='Aborting...'      */
         abort_needed=yes;                  /*                        */
         batch_return_code = 16;            /*                        */
         break;                             /*                        */
      case ret_nofile:                      /*                        */
         printf("%80.80s\n", Msg08);        /* Msg='File not found'   */
         printf("%80.80s\n", Msg14);        /* Msg='Aborting...'      */
         abort_needed=yes;                  /*                        */
         batch_return_code = 16;            /*                        */
         break;                             /*                        */
      default:                              /*                        */
         printf("%80.80s\n", Msg10);        /* Msg='A serious error'  */
         printf("return code= %d\n",server_retcode);
         printf("%80.80s\n", Msg14);        /* Msg='Aborting...'      */
         abort_needed=yes;                  /*                        */
         batch_return_code = 16;            /*                        */
         break;                             /*                        */
      }                                     /*                        */
   }                                        /*                        */
   else                                     /*                        */
   {                                        /*                        */
      printf("%80.80s\n", Msg11);           /* Msg='Link has failed'  */
      linkrets(&link_retarea);              /* Output response codes  */
      printf("%80.80s\n", Msg14);           /* Msg='Aborting...'      */
      batch_return_code = link_retarea.exec_resp;
      abort_needed=yes;                     /*                        */
   }                                        /*                        */
   return(abort_needed);                    /*========================*/
 }
/*:erefstep.dxcc_exec_call ********************************************/
/*:refstep.dxcc_call_level ***************************** DFHDXCC 364 **/
/*                                                                    */
/* This routine will use the EXCI call level API to initiate a        */
/* connection to the target CICS system manually, by performing       */
/* Initialise User, Allocate Pipe, and Open Pipe calls in sequence.   */
/* If the connection has been successful, a series of DPL requests    */
/* will be performed in succession, each time retrieving a single     */
/* record from the target file until all of the file has been         */
/* retrieved.  If the Connection is unsuccessful, or any of the DPL   */
/* calls fail before the end of the target file has been reached, or  */
/* the server fails in any way, then further processing will be       */
/* aborted, and the Connection will be terminated.                    */
/*                                                                    */
/**********************************************************************/
 
 void call_level(char file??(8??),
                 char system??(8??),
                 char transid??(4??),
                 char program??(8??))
 {
 /*===================================================================*/
 /*   Declare local variables.                                        */
 /*===================================================================*/
                                          /*==========================*/
 exci_return_code  call_retarea;          /* Call level Return area.  */
 long int version;                        /* Version no. of EXCI API  */
 long int user_token;                     /* For the token passed back*/
 long int pipe_token;                     /* Ditto for the Pipe       */
 long int call_type;                      /* EXCI call type fullword  */
 char     application??(8??);                 /* Name of this program.    */
 char     userid??(8??) ={'C','I','C','S','U','S','E','R'};
 bit      pipe_type;                      /* Allocate option bit.     */
                                          /*==========================*/
 /*===================================================================*/
 /*   Initialise local variables.                                     */
 /*      These variables are specific to the call level API.          */
 /*===================================================================*/
    version = VERSION_1;
    strncpy(application,PROGRAM_NAME,8);
    pipe_type.option_bit = SPECIFIC_PIPE;
                                          /*==========================*/
    printf("%80.80s\n", Msg02);           /* Msg='*      *'           */
    printf("%80.80s\n", Msg15);           /*Msg='Call level processor'*/
                                          /*==========================*/
 /*===================================================================*/
 /*   Set up and perform the Initialise_User call.                    */
 /*===================================================================*/
                                          /*==========================*/
    call_type=INIT_USER;                  /* Set up call type.        */
    dfhxcis(&version,                     /* Version no. of EXCI API  */
            &call_retarea,                /* Return code area         */
            &user_token,                  /* Use to refer to this User*/
            &call_type,                   /* Call is an Init_User     */
            &application);                /* Name of this User Program*/
    printf("%80.80s\n", Msg16);           /* Msg='Init_User complete' */
                                          /*==========================*/
 /*===================================================================*/
 /*   Set up and perform the Allocate_Pipe call.                      */
 /*===================================================================*/
                                          /*==========================*/
    call_type=ALLOCATE_PIPE;              /* Set up call type         */
    dfhxcis(&version,                     /* Version no. of EXCI API  */
            &call_retarea,                /* Return code area         */
            &user_token,                  /* Refer to the defined User*/
            &call_type,                   /* Call is an Allocate_Pipe */
            &pipe_token,                  /* Use to refer to this Pipe*/
            system,                       /* Target CICS System.      */
            &pipe_type);                  /* Allocate Option bit      */
    printf("%80.80s\n", Msg17);           /* Msg='Alloc_Pipe complete'*/
                                          /*==========================*/
 /*===================================================================*/
 /*   Set up and perform the Open_Pipe call.                          */
 /*===================================================================*/
                                          /*==========================*/
    call_type=OPEN_PIPE;                  /* Set up call type         */
    dfhxcis(&version,                     /* Version no. of EXCI API  */
            &call_retarea,                /* Return code area         */
            &user_token,                  /* Refer to the defined User*/
            &call_type,                   /* Call is an Open_Pipe     */
            &pipe_token);                 /* Refer to allocated Pipe  */
    printf("%80.80s\n", Msg18);           /* Msg='Open__Pipe complete'*/
                                          /*==========================*/
 /*===================================================================*/
 /*                                                                   */
 /*   Has the Pipe been successfully initiated?                       */
 /*                                                                   */
 /*===================================================================*/
                                          /*==========================*/
    if (call_retarea.exci_response==0)    /*Is high level response ok?*/
    {                                     /*                          */
       printf("%80.80s\n", Msg19);        /* Msg='The Pipe is good..' */
       printf("%80.80s\n", Msg20);        /* Msg='Target file ..'     */
       printf("%80.80s\n", Msg02);        /* Msg='*           *'      */
       printf("%80.80s\n", Msg21);        /* Msg='*====Top====*'      */
       getfile(file,                      /* Call the getfile function*/
               transid,                   /*  to retrieve the target  */
               program,                   /*  file.  Give it the User */
               userid,                    /*  and Pipe tokens so it   */
               user_token,                /*  can use the connection. */
               pipe_token);               /*                          */
    }                                     /*                          */
    else                                  /*                          */
    {                                     /*                          */
       printf("%80.80s\n", Msg23);        /* Msg='The Pipe was not ok'*/
       callrets(&call_retarea);           /* Ouput Response codes     */
       batch_return_code = call_retarea.exci_response;
       printf("%80.80s\n", Msg14);        /* Msg='Aborting...'        */
    }                                     /*                          */
                                          /*==========================*/
 /*===================================================================*/
 /*                                                                   */
 /*   The Pipe is no longer required, or is unusable, so an attempt to*/
 /*   terminate it's existence is made by performing Close_Pipe and   */
 /*   Deallocate_Pipe calls in succession.                            */
 /*                                                                   */
 /*===================================================================*/
 
 /*===================================================================*/
 /*   Set up and perform the Close_Pipe call.                         */
 /*===================================================================*/
                                          /*==========================*/
    call_type=CLOSE_PIPE;                 /* Set up call type         */
    dfhxcis(&version,                     /* Version no. of EXCI API  */
            &call_retarea,                /* Return code area         */
            &user_token,                  /* Refer to the defined User*/
            &call_type,                   /* Call is a Close_Pipe     */
            &pipe_token);                 /* Refer to allocated Pipe  */
    printf("%80.80s\n", Msg29);           /* Msg='Close_Pipe complete'*/
                                          /*==========================*/
 /*===================================================================*/
 /*   Set up and perform the Deallocate_pipe call.                    */
 /*===================================================================*/
                                          /*==========================*/
    call_type=DEALLOCATE_PIPE;            /* Set up call type         */
    dfhxcis(&version,                     /* Version no. of EXCI API  */
            &call_retarea,                /* Return code area         */
            &user_token,                  /* Refer to the defined User*/
            &call_type,                   /* Call is a Deallocate     */
            &pipe_token);                 /* Refer to allocated Pipe  */
    printf("%80.80s\n", Msg30);           /* Msg='Deallocate complete'*/
                                          /*==========================*/
 }
/*:erefstep.dxcc_call_level *******************************************/
/*:refstep.dxcc_getfile ******************************** DFHDXCC 505 **/
/*                                                                    */
/* This routine will set up and perform multiple DPL requests into    */
/* the target CICS system, and invokes the server program with a      */
/* call_type of READ. Depending on the individual success or failure  */
/* of any call, either a record from the target file will be output   */
/* to the SYSPRINT log, or a message stating what has failed.  It     */
/* will also make a decision as to whether further calls are          */
/* required/necessary/possible.                                       */
/*                                                                    */
/**********************************************************************/
 void getfile(char file??(8??),
              char transid??(4??),
              char program??(8??),
              char userid??(8??),
              long int user_token,
              long int pipe_token)
 {
 /*===================================================================*/
 /*   Declare local variables.                                        */
 /*===================================================================*/
                                          /*==========================*/
 exci_return_code  call_retarea;          /* Return code area         */
 exci_dpl_retarea  dpl_retarea;           /* DPL response code area   */
 comms     commarea;                      /* Commarea for server parms*/
 char      *null_pointer;                 /* For omitting userid      */
 long int version;                        /* Version no. of EXCI API  */
 long  int comm_length;                   /* Length of return Commarea*/
 long  int data_length;                   /* Length of outbound Comm. */
 bit       dpl_type;                      /* DPL option bit           */
 short int abort_needed;                  /* Abort flag               */
 long  int call_type;                     /* EXCI call type fullword  */
 long  int server_retcode;                /* server response area     */
                                          /*==========================*/
 /*===================================================================*/
 /*   Initialise local variables.                                     */
 /*      These variables area mainly specific to the DPL_Request call.*/
 /*===================================================================*/
    version = VERSION_1;
    dpl_type.option_bit=SYNCONRETURN;
    null_pointer = 0;
 
 /*===================================================================*/
 /*   Set the Commarea Length and Data Length constants(in bytes).    */
 /*===================================================================*/
    comm_length = COMMAREA_LENGTH;
    data_length = DATA_LENGTH;
 
 /*===================================================================*/
 /*   Set up the Outbound Commarea for transmission.                  */
 /*===================================================================*/
                                          /*==========================*/
    strncpy(commarea.file_name,file,8);   /* Target file name         */
    strncpy(commarea.rid_field,"000001",6);/* Initial Rid Field       */
                                          /*==========================*/
 /*===================================================================*/
 /*   Initialise and start the conditional looping.                   */
 /*===================================================================*/
    abort_needed=no_abort;
    while(abort_needed==no_abort)
    {
 /*===================================================================*/
 /*                                                                   */
 /*   Set up and perform the DPL_Requset.                             */
 /*                                                                   */
 /*     No Userid will be passed, as an example of how to leave out   */
 /*     any parameter from the list.  A null pointer must replace the */
 /*     pointer in the list which would normally point to the         */
 /*     parameter missing.                                            */
 /*                                                                   */
 /*===================================================================*/
                                          /*==========================*/
    commarea.call_type=read_call;         /* Set up Server call type  */
    call_type=DPL_REQUEST;                /* Set up the EXCI call type*/
    dfhxcis(&version,                     /* Version no. of EXCI API  */
            &call_retarea,                /* Return code area         */
            &user_token,                  /* Refer to initialised User*/
            &call_type,                   /* Call is a DPL request    */
            &pipe_token,                  /* Refer to allocated Pipe  */
            program,                      /* The CICS-resident Server */
            &commarea,                    /* Contains Server parms    */
            &comm_length,                 /* Return Commarea length   */
            &data_length,                 /* Outbound Commarea length */
            transid,                      /* TransId -> DFHMIRS       */
            null_pointer,                 /* Uowid ommitted           */
            &userid,                      /* End user's UserId omitted*/
            &dpl_retarea,                 /* DPL return code area     */
            &dpl_type);                   /* DPL option bit           */
                                          /*==========================*/
 /*===================================================================*/
 /*                                                                   */
 /*   Check on how successful the link, and the DPL call were.        */
 /*                                                                   */
 /*===================================================================*/
                                          /*==========================*/
    if (call_retarea.exci_response==0)    /* Check if high level resp */
    {                                     /* is ok..                  */
       if (dpl_retarea.exci_dpl_resp!=0)  /* Check if low level (DPL) */
       {                                  /* response is ok...        */
          printf("%80.80s\n", Msg25);     /* Msg='DPL call failed.'   */
          printf("%80.80s\n", Msg27);     /* Msg='Return codes are:'  */
          printf("*      DPL Resp, Resp2, Abcode values of: %d %d %4.4s"
                 "                                 *\n",
                 dpl_retarea.exci_dpl_resp,dpl_retarea.exci_dpl_resp2,
                 dpl_retarea.exci_dpl_abcode);
          printf("%80.80s\n", Msg14);     /* Msg='Aborting... '       */
          batch_return_code = dpl_retarea.exci_dpl_resp;
          abort_needed=yes;               /*                          */
       }                                  /*                          */
       else                               /* Output the Record to the */
       {                                  /*  SYSPRINT Log.           */
          printf("%80.80s\n",commarea.record_area);
       }                                  /*                          */
    }                                     /*                          */
    else                                  /*                          */
       {                                  /*                          */
          printf("%80.80s\n", Msg11);     /* Msg='Link request failed'*/
          printf("%80.80s\n", Msg26);     /* Msg='Link return codes..'*/
          callrets(&call_retarea);        /* Output Response codes    */
          printf("%80.80s\n", Msg27);     /* Msg='Return codes..' */
          printf("*      DPL Resp, Resp2, Abcode values of: %d %d %4.4s"
                 "                                 *\n",
                 dpl_retarea.exci_dpl_resp,dpl_retarea.exci_dpl_resp2,
                 dpl_retarea.exci_dpl_abcode);
          printf("%80.80s\n", Msg14);     /* Msg='Aborting...'        */
          batch_return_code = call_retarea.exci_response;
          abort_needed=yes;               /*                          */
       }                                  /*                          */
    server_retcode=commarea.call_type;    /*Has End-File been reached?*/
    if (server_retcode==ret_eof)          /*                          */
    {                                     /*                          */
      abort_needed=yes;                   /*                          */
      printf("%80.80s\n", Msg22);         /* Msg='*====ENDFILE===*'   */
      printf("%80.80s\n", Msg02);         /* Msg='*              *'   */
    }                                     /*                          */
 }                                        /*==========================*/
 /*===================================================================*/
 /*                                                                   */
 /*   Perform Closing DPL_Request.                                    */
 /*      This final DPL call invokes the BATCHSER server program with */
 /*      a call-type of CLOS_CALL, in an attempt to close the target  */
 /*      file after processing is complete. No error checking is      */
 /*      required, as nothing can be done if the call fails.          */
 /*                                                                   */
 /*===================================================================*/
                                          /*==========================*/
    commarea.call_type=close_call;        /* Set up Server call type  */
    call_type=DPL_REQUEST;                /* Set up the EXCI call type*/
    dfhxcis(&version,                     /* Version no. of EXCI API  */
            &call_retarea,                /* Return code area         */
            &user_token,                  /* Refer to initialised User*/
            &call_type,                   /* Call is a DPL request    */
            &pipe_token,                  /* Refer to allocated Pipe  */
            program,                      /* The CICS-resident Server */
            &commarea,                    /* Contains Server parms    */
            &comm_length,                 /* Return Commarea length   */
            &data_length,                 /* Outbound Commarea length */
            transid,                      /* TransId -> DFHMIRS       */
            null_pointer,                 /* Uowid ommitted           */
            &userid,                      /* End user's UserId omitted*/
            &dpl_retarea,                 /* DPL return code area     */
            &dpl_type);                   /* DPL option bit           */
    printf("%80.80s\n", Msg28);           /* Msg='Closing DPL ...'    */
                                          /*==========================*/
 }
/*:erefstep.dxcc_getfile **********************************************/
/*:refstep.dxcc_linkrets ******************************* DFHDXCC 719 **/
/*                                                                    */
/* If the EXEC level EXCI link fails in any way, this routine will    */
/* display in decimal all high level return codes produced.  If a     */
/* message was received from the target CICS system, this will also   */
/* be displayed.                                                      */
/*                                                                    */
/**********************************************************************/
 void linkrets(exci_exec_return_code *pRet_Area)
 {
   char     *pMsg_String;
   short int Msg_Len;
 
   /* output response, reason, subreason */
   printf("*     Response = %d   Response2 = %d   Abend Code = %4.4s"
          "                      *\n",
              pRet_Area->exec_resp,
              pRet_Area->exec_resp2,
              pRet_Area->exec_abcode);
 
   /* output message if one exists */
   if (pRet_Area->exec_msg_ptr != NULL)
   {
     /* get message length       */
     Msg_Len = pRet_Area->exec_msg_len;
 
     /* allocate memory for message */
     if ((pMsg_String = (char *)malloc(Msg_Len+1)) != NULL)
     {
       /* malloc successful - copy contents of message into string */
 
       memcpy(pMsg_String, pRet_Area->exec_msg_ptr, Msg_Len);
 
       /* null terminate our message string */
       *(pMsg_String + Msg_Len) = '\0';
 
       /* output message */
       printf("%80.80s\n", Msg13);
       printf("%80.80s\n", Msg02);
       printf("%80.120s\n", pMsg_String);
       printf("%80.80s\n", Msg02);
 
     } else printf("malloc - failed No Memory For Message String\n");
 
   }
 
   return;
 }
/*:erefstep.dxcc_linkrets *********************************************/
/*:refstep.dxcc_callrets ******************************* DFHDXCC 670 **/
/*                                                                    */
/* If the call level EXCI link fails in any way, this routine will    */
/* display in decimal all high level return codes produced.  If a     */
/* message was received from the target CICS system, this will also   */
/* be displayed.                                                      */
/*                                                                    */
/**********************************************************************/
 void callrets(exci_return_code *pRet_Area)
 {
   char     *pMsg_String;
   short int Msg_Len;
 
   /* output response, reason, subreason */
   printf("*    Response = %d   Reason = %d   Subreason = %d"
          "                     *\n",
              pRet_Area->exci_response,
              pRet_Area->exci_reason,
              pRet_Area->exci_sub_reason1);
 
   /* output message if one exists */
   if (pRet_Area->exci_msg_ptr != NULL)
   {
     /* calculate message length */
     /* message length = value in first two bytes - 4 */
     Msg_Len = *(short int *)pRet_Area->exci_msg_ptr - 4;
 
     /* allocate memory for message */
     if ((pMsg_String = (char *)malloc(Msg_Len+1)) != NULL)
     {
       /* malloc successful - copy contents of message into string */
 
       memcpy(pMsg_String, pRet_Area->exci_msg_ptr+4, Msg_Len);
 
       /* null terminate our message string */
       *(pMsg_String + Msg_Len) = '\0';
 
       /* output message */
       printf("%80.80s\n", Msg13);
       printf("%80.80s\n", Msg02);
       printf("%80.120s\n", pMsg_String);
       printf("%80.80s\n", Msg02);
 
     } else printf("malloc - failed No Memory For Message String\n");
 
   }
 
   return;
 }
/*:erefstep.dxcc_callrets *********************************************/
 
/*:erefstep.dxcc_functions ********************************************/
 
/*:refstep.send_doc_function *************************** EXCICGI 168 **/
/*                                                                    */
/* sends the an html form based on the passed page number. If no page */
/* number is sent then the defaul first page will be delivered.       */
/*                                                                    */
/* page 2 does not require the page header since it is concatenated   */
/* with page 1 which contains the header information.                 */
/*                                                                    */
/**********************************************************************/
void send_doc(int page_number) {
 
    switch(page_number){
       case 0 :{
/*:refstep.page_header ********************************* EXCICGI 199 **/
/*                                                                    */
/* outputs the common HTML page header                                */
/*                                                                    */
/**********************************************************************/
printf("<TITLE>The MVS Internet Connection Server CICS ESA ");
printf("Interface </TITLE>%c", LF);
printf("<CENTER>%c",LF);
printf("<img SRC=\"/Admin/CICSESA.gif\"");
printf("alt=\"CICS/ESA 4.1 Application Server\">");
printf("<H1>The MVS Internet Connection Server CICS/ESA EXCI ");
printf("Interface</H1> </CENTER>%c",LF);
HTML_BREAK
printf("<HR>%c", LF);
HTML_BREAK
/*:erefstep.page_header ***********************************************/
/*:refstep.page_0 ************************************** EXCICGI 214 **/
/*                                                                    */
/* Output some form fields that allow the CICS applid to be input.    */
/*                                                                    */
/**********************************************************************/
  printf("<FORM METHOD=POST ");
  printf("ACTION=\"/cgi-bin/excicgi\">%c",LF);
 
  printf("<P>Below you may input the VTAM APPLID of the CICS ");
  printf("system that the EXCI program will establish connection ");
  printf("with. To do this overtype the default with your value. ");
  printf("You do not have to enter a value below, if you ");
  printf("choose not to the APPLID used will default to DBDCCICS.");
  printf("%c",LF);
  HTML_BREAK
  printf("<INPUT TYPE=\"text\" NAME=\"applid\" SIZE=8 ");
  printf("VALUE=\"DBDCCICS\" >%c",LF);
  printf("<INPUT TYPE=\"submit\" VALUE=\"Send Request\">");
  printf("<INPUT TYPE=\"reset\">%c",LF);
  HTML_BREAK
 
  printf("</FORM>%c", LF);
/*:erefstep.page_0 ****************************************************/
                break;
       }
       case 1 :{
/*:refstep.page_header ********************************* EXCICGI 199 **/
/*                                                                    */
/* outputs the common HTML page header                                */
/*                                                                    */
/**********************************************************************/
printf("<TITLE>The MVS Internet Connection Server CICS ESA ");
printf("Interface </TITLE>%c", LF);
printf("<CENTER>%c",LF);
printf("<img SRC=\"/Admin/CICSESA.gif\"");
printf("alt=\"CICS/ESA 4.1 Application Server\">");
printf("<H1>The MVS Internet Connection Server CICS/ESA EXCI ");
printf("Interface</H1> </CENTER>%c",LF);
HTML_BREAK
printf("<HR>%c", LF);
HTML_BREAK
/*:erefstep.page_header ***********************************************/
/*:refstep.page_1 ************************************** EXCICGI 236 **/
/*                                                                    */
/* produce the initial part of the exci results page.                 */
/*                                                                    */
/**********************************************************************/
  printf("<HTML>%c<BODY>%c", LF,LF);
  printf("<P>If the output returned below is not comparable to the ");
  printf("sample EXCI results described in Section 7.4.4 Figure 8 ");
  printf(" the CICS EXCI manual, then you may wish to test your ");
  printf("EXCI installation in isolation from this sample CGI ");
  printf("program.%c",LF);
 
  HTML_BREAK
  printf("<HR>%c", LF);
  HTML_BREAK
 
  printf("<CENTER>");
  printf("<P>The CICS Program output is shown below :%c",LF);
  printf("<PRE>");
/*:erefstep.page_1 ****************************************************/
                break;
       }
       case 2 :{
/*:refstep.page_2 ************************************** EXCICGI 255 **/
/*                                                                    */
/* produce the final part of the exci results page                    */
/*                                                                    */
/**********************************************************************/
  printf("</PRE>%c",LF);
  printf("</CENTER>%c",LF);
  printf("</BODY>%c</HTML>%c", LF,LF);
/*:erefstep.page_2 ****************************************************/
               break;
       }
       default:{
/*:refstep.page_header ********************************* EXCICGI 199 **/
/*                                                                    */
/* outputs the common HTML page header                                */
/*                                                                    */
/**********************************************************************/
printf("<TITLE>The MVS Internet Connection Server CICS ESA ");
printf("Interface </TITLE>%c", LF);
printf("<CENTER>%c",LF);
printf("<img SRC=\"/Admin/CICSESA.gif\"");
printf("alt=\"CICS/ESA 4.1 Application Server\">");
printf("<H1>The MVS Internet Connection Server CICS/ESA EXCI ");
printf("Interface</H1> </CENTER>%c",LF);
HTML_BREAK
printf("<HR>%c", LF);
HTML_BREAK
/*:erefstep.page_header ***********************************************/
/*:refstep.page_0 ************************************** EXCICGI 214 **/
/*                                                                    */
/* Output some form fields that allow the CICS applid to be input.    */
/*                                                                    */
/**********************************************************************/
  printf("<FORM METHOD=POST ");
  printf("ACTION=\"/cgi-bin/excicgi\">%c",LF);
 
  printf("<P>Below you may input the VTAM APPLID of the CICS ");
  printf("system that the EXCI program will establish connection ");
  printf("with. To do this overtype the default with your value. ");
  printf("You do not have to enter a value below, if you ");
  printf("choose not to the APPLID used will default to DBDCCICS.");
  printf("%c",LF);
  HTML_BREAK
  printf("<INPUT TYPE=\"text\" NAME=\"applid\" SIZE=8 ");
  printf("VALUE=\"DBDCCICS\" >%c",LF);
  printf("<INPUT TYPE=\"submit\" VALUE=\"Send Request\">");
  printf("<INPUT TYPE=\"reset\">%c",LF);
  HTML_BREAK
 
  printf("</FORM>%c", LF);
/*:erefstep.page_0 ****************************************************/
       }
    }
}
/*:erefstep.send_doc_function *****************************************/
 
/*:erefstep.Internal_functions ****************************************/
 
main(int argc, char *argv??(??)) {
/*:refstep.Global_variables **************************** EXCICGI 106 **/
/*                                                                    */
/* - cl - a pointer to a character string which represents the http   */
/*   content length.                                                  */
/*                                                                    */
/* - input_stream - a pointer to a character which represents the     */
/*   http stream. :.li.applid - the character string representing the */
/*   system VTAM applid.                                              */
/*                                                                    */
/* - i - a general integer counter.                                   */
/*                                                                    */
/* - input_length - the length of the http stream.                    */
/*                                                                    */
/**********************************************************************/
    char *cl;
    char *input_stream;
    char *applid;
    int i;
    int input_length;
/*:erefstep.Global_variables ******************************************/
 
 
    printf("Content-type: text/html%c%c",LF,LF);
     applid = NULL;
     cl = getenv("REQUEST_METHOD");
     if ( (cl == NULL) || (strcmp(cl,"POST")) )
       send_doc(0);
     else{
       cl = getenv("CONTENT_LENGTH");
       input_length = atoi(cl);
       if (!input_length) {
         send_doc(0);
         exit(1);
       }
       else {
         input_stream = malloc(input_length +1);
         if (input_stream == NULL ){
           printf("Sorry not enough storage left %c",LF);
           exit(0);
         }
         gets(input_stream);
         strtok(input_stream,"=");
         applid = strtok(NULL,"&");
         for(i=0;applid??(i??);i++)
         if (applid??(i??) == '+') applid??(i??) = ' ';
         send_doc(1);
         dxcc_sample(applid);
         send_doc(2);
       }
     }
}
/*:erefstep.excicgi ***************************************************/
