
 /*-------------------------------------------------------------------*/
 /*                                                                   */
 /*  Program Name  : ANEOC003.C                                       */
 /*                                                                   */
 /*  Purpose : This program is one of a suite of programs designed    */
 /*            to test the CICS Client ECI Function.                  */
 /*                                                                   */
 /*  Associated Applications :                                        */
 /*                                                                   */
 /*  Date    : 20-08-94                                               */
 /*                                                                   */
 /*  Language : C                                                     */
 /*                                                                   */
 /*  Tested Environments : CICS CLIENT                                */
 /*                        (Microsoft C6)                             */
 /*                                                                   */
 /*  Associated Programmers : A.P.Thomson                             */
 /*                                                                   */
 /*  Compile Options Used by Developers :                             */
 /*                                                                   */
 /*  NMAKE ANEOC003.MAK                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 #define  CICS_OS2
 #define  COMMAREA_SIZE 18
 #define  INCL_DOS

 #include <cics_eci.h>
 #include <stdlib.h>
 #include <memory.h>
 #include <stdio.h>  
 #include <string.h>
 #include <os2.h>
 
 void SayHelp( void );
 void ParseParms( int argc, char *argv[] );
 
 char      DisplayField[]= "0000\0" ;
 int       CallCount;
 int       Iterations;
 float     Delay;
 ECI_PARMS EciBlock;
 HEV       hevSemEci   ;   /* Semaphore to wait on                */
 ULONG     ulPostCount ;   /* Count of DosPostEventSemCalls       */

 char CommArea[COMMAREA_SIZE];
 
 /*-------------------------------------------------------------------*/
 /* Procedure    : Parse_Parms                                        */
 /*                                                                   */
 /* Reference    : Code and Design by Paul Carey 1993                 */
 /*                                                                   */
 /* Function     : Parse command line input parameters                */
 /*                                                                   */
 /* Input        : Parameters from Command Line                       */
 /*                                                                   */
 /* Assumptions  : None                                               */
 /*                                                                   */
 /* Required sub : None                                               */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : Void                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 void ParseParms( int argc, char *argv[] )
  {
    int     j = 1 ;
    CallCount   = 1;
    Iterations  = 1;
    Delay       = 0;
    while ( j < argc )  {
       if ( strchr ( "/-(", argv[j][0] ) ) { /* looks like an option  */
          argv[j] = strupr ( argv[j] );
          switch ( argv[j][1] )  {
          case '?' :                         /* /?:HELP               */
          case 'H' :                         /* /H:                   */
             SayHelp();
             break;
          case 'I' :                         /* /I:iterations         */
             Iterations = atoi ( &argv[j][3] );
             if( ( Iterations < 0 ) || ( Iterations  > 32000 ) ) {
                printf( "Iterations out of range 0 used instead of %s\n",
                                                              &argv[j][3]);
                Iterations = 0;
             } else {
                printf("Iterations changed to %s\n",&argv[j][3]);
             } /* endif */
             break;
          case 'D' :                         /* /D:Delay              */
             Delay = atof ( &argv[j][3] );
             Delay = Delay * 400; /* Make it a reasonable value */
             break;
          case 'S' :                         /* /S:Server             */
             strcpy( EciBlock.eci_system_name, &argv[j][3]);
             printf("Server Selected = %s\n",&argv[j][3]);
             break;
          case 'U' :                         /* /U:UserID             */
             strcpy( EciBlock.eci_userid, &argv[j][3]);
             printf("User ID Selected = %s\n",&argv[j][3]);
             break;
          case 'P' :                         /* /P:Password           */
             strcpy( EciBlock.eci_password, &argv[j][3]);
             printf("Password Supplied = %s\n",&argv[j][3]);
             break;
          default :
                printf("Ignoring Parameter %s",&argv[j][0]);
             break;
          } /* endswitch */
       } else { printf("Parameters must begin with a /-("); } /* endif */
       j++;
    } /* endwhile */
 
 }

 /*-------------------------------------------------------------------*/
 /* Procedure    : SayHelp                                            */
 /*                                                                   */
 /* Reference    : A.Thomson rehash of Code by Paul Carey 1993        */
 /*                                                                   */
 /* Function     : To Display Parameter driven options for this       */
 /*                Test application                                   */
 /*                                                                   */
 /* Input        : None                                               */
 /*                                                                   */
 /* Assumptions  : None                                               */
 /*                                                                   */
 /* Required sub : None                                               */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : Void                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 void SayHelp( void )
 {
 printf("ษอออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออป\n");
 printf("บ                                                                           บ\n");
 printf("บ ECI ANEOC003 - A.Thomson Summer 94 (See CCECIDOC for Documentation)       บ\n");
 printf("บ                                                                           บ\n");
 printf("บ C program making call type ECI_ASYNC_NOTIFY_SEM (with Event semaphore)    บ\n");
 printf("บ                                                                           บ\n");
 printf("บ Input Parameter Help;                                                     บ\n");
 printf("บ                                                                           บ\n");
 printf("บ /I:iterations   The number of times to run this test.                     บ\n");
 printf("บ                 0 or greater than 32000 results in infinite loop          บ\n");
 printf("บ                                                                           บ\n");
 printf("บ /D:Delay        Value specifying time to wait between call sets           บ\n");
 printf("บ                                                                           บ\n");
 printf("บ /S:Server       Name of CICS Server to Execute the ECI Calls              บ\n");
 printf("บ                                                                           บ\n");
 printf("บ /U:UserID       Userid to pass to CICS Server                             บ\n");
 printf("บ                                                                           บ\n");
 printf("บ /P:Password     Password to pass to CICS Server                           บ\n");
 printf("บ                                                                           บ\n");
 printf("บ /? or /Help     This most excellent help panel.                           บ\n");
 printf("บ                                                                           บ\n");
 printf("ศอออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออผ\n");
 exit(0);
 }

 /*-------------------------------------------------------------------*/
 /* Procedure    : Perform_Delay                                      */
 /*                                                                   */
 /* Reference    : Create a delay                                     */
 /*                                                                   */
 /* Function     : To delay the application between calls             */
 /*                                                                   */
 /* Input        : Integer                                            */
 /*                                                                   */
 /* Assumptions  : None                                               */
 /*                                                                   */
 /* Required sub : None                                               */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : Void                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 void Perform_Delay( float Duration )
   {
     if(Duration==0) return;
     for(Duration;Duration;Duration--) 
       {
         printf("-");  printf("\b");
         printf("\\"); printf("\b");
         printf("|");  printf("\b");
         printf("/");  printf("\b");
         printf("-");  printf("\b");
       }
      printf(" ");
    }

 /*-------------------------------------------------------------------*/
 /* Procedure    : Process_Return_Code                                */
 /*                                                                   */
 /* Reference    : Code and Design by A.Thomson Summer 1994           */
 /*                                                                   */
 /* Function     : Process ECI Return Code                            */
 /*                                                                   */
 /* Input        : None                                               */
 /*                                                                   */
 /* Assumptions  : None                                               */
 /*                                                                   */
 /* Required sub : None                                               */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : Void                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 void Process_Return_Code(ECIRetCode)
   cics_sshort_t ECIRetCode;
   {
    char *ErrTxt;
    char DisplayField[]= "0000\0" ;
    
    switch ( ECIRetCode )
    {
    case ECI_NO_ERROR                :ErrTxt="ECI_NO_ERROR               ";break;
    case ECI_ERR_INVALID_DATA_LENGTH :ErrTxt="ECI_ERR_INVALID_DATA_LENGTH";break;
    case ECI_ERR_INVALID_EXTEND_MODE :ErrTxt="ECI_ERR_INVALID_EXTEND_MODE";break;
    case ECI_ERR_NO_CICS             :ErrTxt="ECI_ERR_NO_CICS            ";break;
    case ECI_ERR_CICS_DIED           :ErrTxt="ECI_ERR_CICS_DIED          ";break;
    case ECI_ERR_REQUEST_TIMEOUT     :ErrTxt="ECI_ERR_REQUEST_TIMEOUT    ";break;
    case ECI_ERR_RESPONSE_TIMEOUT    :ErrTxt="ECI_ERR_RESPONSE_TIMEOUT   ";break;
    case ECI_ERR_TRANSACTION_ABEND   :ErrTxt="ECI_ERR_TRANSACTION_ABEND  ";break;
    case ECI_ERR_EXEC_NOT_RESIDENT   :ErrTxt="ECI_ERR_EXEC_NOT_RESIDENT  ";break;
    case ECI_ERR_SYSTEM_ERROR        :ErrTxt="ECI_ERR_SYSTEM_ERROR       ";break;
    case ECI_ERR_NULL_WIN_HANDLE     :ErrTxt="ECI_ERR_NULL_WIN_HANDLE    ";break;
    case ECI_ERR_NULL_MESSAGE_ID     :ErrTxt="ECI_ERR_NULL_MESSAGE_ID    ";break;
    case ECI_ERR_THREAD_CREATE_ERROR :ErrTxt="ECI_ERR_THREAD_CREATE_ERROR";break;
    case ECI_ERR_INVALID_CALL_TYPE   :ErrTxt="ECI_ERR_INVALID_CALL_TYPE  ";break;
    case ECI_ERR_ALREADY_ACTIVE      :ErrTxt="ECI_ERR_ALREADY_ACTIVE     ";break;
    case ECI_ERR_RESOURCE_SHORTAGE   :ErrTxt="ECI_ERR_RESOURCE_SHORTAGE  ";break;
    case ECI_ERR_NO_SESSIONS         :ErrTxt="ECI_ERR_NO_SESSIONS        ";break;
    case ECI_ERR_NULL_SEM_HANDLE     :ErrTxt="ECI_ERR_NULL_SEM_HANDLE    ";break;
    case ECI_ERR_INVALID_DATA_AREA   :ErrTxt="ECI_ERR_INVALID_DATA_AREA  ";break;
    case ECI_ERR_INVALID_VERSION     :ErrTxt="ECI_ERR_INVALID_VERSION    ";break;
    case ECI_ERR_UNKNOWN_SERVER      :ErrTxt="ECI_ERR_UNKNOWN_SERVER     ";break;
    case ECI_ERR_CALL_FROM_CALLBACK  :ErrTxt="ECI_ERR_CALL_FROM_CALLBACK ";break;
    case ECI_ERR_INVALID_TRANSID     :ErrTxt="ECI_ERR_INVALID_TRANSID    ";break;
    case ECI_ERR_MORE_SYSTEMS        :ErrTxt="ECI_ERR_MORE_SYSTEMS       ";break;
    case ECI_ERR_NO_SYSTEMS          :ErrTxt="ECI_ERR_NO_SYSTEMS         ";break;
    case ECI_ERR_SECURITY_ERROR      :ErrTxt="ECI_ERR_SECURITY_ERROR     ";break;
    case ECI_ERR_MAX_SYSTEMS         :ErrTxt="ECI_ERR_MAX_SYSTEMS        ";break;
    case ECI_ERR_MAX_SESSIONS        :ErrTxt="ECI_ERR_MAX_SESSIONS       ";break;
    case ECI_ERR_ROLLEDBACK          :ErrTxt="ECI_ERR_ROLLEDBACK         ";break;
    default: sprintf (ErrTxt, " %d (Unknown!)",ECIRetCode);
    } /* endswitch */
 
    printf( "  Return Code    : %s\n", ErrTxt );
    memcpy( DisplayField, EciBlock.eci_abend_code, 4 );
    printf( "  Abend Code     : '%s'\n", DisplayField);
    printf( "  SysCode        : %d\n", EciBlock.eci_sys_return_code);

   }

 /*-------------------------------------------------------------------*/
 /* Procedure    : Make_One_Call_Set                                  */
 /*                                                                   */
 /* Reference    : Code and Design by A.Thomson Summer 1994           */
 /*                                                                   */
 /* Function     : To Make one set of ECI Calls                       */
 /*                                                                   */
 /* Input        : None                                               */
 /*                                                                   */
 /* Assumptions  : None                                               */
 /*                                                                   */
 /* Required sub : None                                               */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : Void                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 void Make_One_Call_Set()
   {
     cics_sshort_t ECIRetCode;
     int SEMRetCode;

     printf(" ------------------------------------------------------ \n"); 

     /***************************************************************************/
     /* Reset the semaphore:                                                    */
     /***************************************************************************/
     SEMRetCode = DosResetEventSem ( hevSemEci, &ulPostCount ) ;
     if ( SEMRetCode != 0 ) {
        printf ( "Could not reset semaphore. DosResetEventSem returned %d.\n",
                 SEMRetCode ) ;
     } /* endif */

     EciBlock.eci_version = ECI_VERSION_1;
     EciBlock.eci_call_type = ECI_ASYNC_NOTIFY_SEM ;/* Asynchronous call   */
     memcpy ( &EciBlock.eci_program_name,         /* Back-end program name */
              "FAADECIC",                         /* Note space filling.   */
              sizeof ( EciBlock.eci_program_name ) ) ;
     EciBlock.eci_commarea = &CommArea ;
     EciBlock.eci_commarea_length = sizeof ( CommArea ) ;
     EciBlock.eci_timeout = 20 ;                  /* 20-second timeout.    */
     EciBlock.eci_extend_mode = ECI_NO_EXTEND ;   /* Single call.          */
     EciBlock.eci_async_notify.sem_handle = hevSemEci ; /* Semaphore  */

     /*****************************************************************/
     /* Call the ECI. This call will return immediately:              */
     /*****************************************************************/

     printf("\n"); 
     printf("  Initial call   : ECI_ASYNC_NOTIFY_SEM\n"); 

     ECIRetCode = CICS_ExternalCall ( &EciBlock ) ;

     Process_Return_Code(ECIRetCode);

     /***************************************************************************/
     /* Check to see if the ECI accepted the call:                              */
     /***************************************************************************/
     if ( ECIRetCode != ECI_NO_ERROR )
       {
        printf("\n  First Call Failed so don't bother with ECI_GET_REPLY\n"); 
       }
     else
       {
        /************************************************************************/
        /* The ECI call succeeded. Carry on with some other processing while    */
        /* the target transaction is running....                                */
        /************************************************************************/
        printf("\n"); 
        printf ( "  Wait on Event Semaphore before calling for reply\n" ) ;
        /************************************************************************/
        /* The local processing has finished. Wait for the semaphore to be      */
        /* cleared to show that CICS has finish running the target program:     */
        /************************************************************************/
        DosWaitEventSem ( hevSemEci, SEM_INDEFINITE_WAIT  );/* Wait indefinitely*/
  
        /************************************************************************/
        /* The semaphore cleared. Set up the ECI parameter block to solicit a   */
        /* reply from CICS for OS/2:                                            */
        /************************************************************************/
        EciBlock.eci_call_type = ECI_GET_REPLY ;

        /************************************************************************/
        /* Call the ECI to determine CICS for OS/2's response:                  */
        /************************************************************************/

        printf("\n"); 
        printf("  Follow on call : ECI_GET_REPLY\n"); 
        ECIRetCode = CICS_ExternalCall ( &EciBlock ) ;

        /************************************************************************/
        /* Interpret the result:                                                */
        /************************************************************************/
        Process_Return_Code(ECIRetCode);

        /* Display results. */
        if (ECIRetCode==ECI_NO_ERROR)
          {
            CommArea[17] = '\0';
            printf( "  CommArea       : %s\n", CommArea );
          }

       }

     printf("\n"); 
     printf(" ------------------------------------------------------ \n"); 

     Perform_Delay(Delay); 

   }

 /*-------------------------------------------------------------------*/
 /* Procedure    : Main                                               */
 /*                                                                   */
 /* Reference    : Code and Design by A.Thomson Summer 1994           */
 /*                                                                   */
 /* Function     : To Drive all Sub Procedures of this Application    */
 /*                                                                   */
 /* Input        : Command Line Parameters (See Help)                 */
 /*                                                                   */
 /* Assumptions  : None                                               */
 /*                                                                   */
 /* Required sub : All                                                */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : Void                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 int main(argc, argv, envp)
   int argc;
   char *argv[];
   char *envp[];
   {
     int pos=1;
     int SEMRetCode;

     memset ( &EciBlock,0,sizeof ( EciBlock ) );

     ParseParms( argc, argv );

     /***************************************************************************/
     /* Create the semaphore:                                                   */
     /***************************************************************************/
     SEMRetCode = DosCreateEventSem ( "\\SEM32\\ECIEVENT", /* Semaphore name    */
                                      &hevSemEci,          /* Semaphore handle  */
                                      0,                   /* Unused            */
                                      TRUE ) ;             /* Posted            */

     printf("--------------------------------------------------------\n"); 
     printf(" ECI ANEOC003 - Original Coding by A.Thomson Summer 94 \n"); 
     printf("                Last Compiled "__DATE__" at "__TIME__"\n"); 
     printf("--------------------------------------------------------\n\n"); 
 
     switch(Iterations)
       {
 
         case 0: /* If Zero Iterations specified then loop forever */ 
           while(1)
             {
               Make_One_Call_Set();
               printf("\n  Iteration %d of Infinite Loop Complete\n\n",pos);
               pos++; 
             } break; 
 
         case 1: /* If 1 Iteration specified then just make one call */ 
            {
              Make_One_Call_Set();
              printf("\n");
            } break; 

         default: /* Otherwise loop to make the requested number of calls */ 
           while(pos<=Iterations)
             {
               Make_One_Call_Set();
               printf("\n  Iteration %d of %d Complete\n\n",pos,Iterations);
               pos++; 
             } break; 
       }
    
    printf("--------------------------------------------------------\n"); 
    printf(" ECI ANEOC003 - Application Termination\n"); 
    printf("--------------------------------------------------------\n"); 

    return(0);
  }

 /*-------------------------------------------------------------------*/
 /*                   END OF PROGRAM ANEOC003.C                       */
 /*-------------------------------------------------------------------*/              

