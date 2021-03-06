
 /*-------------------------------------------------------------------*/
 /*                                                                   */
 /*  Program Name  : SYNOC003.C                                       */
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
 /*  NMAKE SYNOC001.MAK                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 #define CICS_OS2
 #include <cics_eci.h>
                  
 #include <stdlib.h>
 #include <memory.h>
 #include <stdio.h>
 #include <string.h>
 
 #define COMMAREA_SIZE 10018
 
 void SayHelp( void );
 void ParseParms( int argc, char *argv[] );
 
 int   CallCount;
 int   Iterations;
 float Delay;
 ECI_PARMS EciBlock;
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
 printf("浜様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様�\n");
 printf("�                                                                           �\n");
 printf("� ECI SYNOC003 - A.Thomson Summer 94 (See CCECIDOC for Documentation)       �\n");
 printf("�                                                                           �\n");
 printf("� C program making call type ECI_SYNC (with large commarea)                 �\n");
 printf("�                                                                           �\n");
 printf("� Input Parameter Help;                                                     �\n");
 printf("�                                                                           �\n");
 printf("� /I:iterations   The number of times to run this test.                     �\n");
 printf("�                 0 or greater than 32000 results in infinite loop          �\n");
 printf("�                                                                           �\n");
 printf("� /D:Delay        Value specifying time to wait between call sets           �\n");
 printf("�                                                                           �\n");
 printf("� /S:Server       Name of CICS Server to Execute the ECI Calls              �\n");
 printf("�                                                                           �\n");
 printf("� /U:UserID       Userid to pass to CICS Server                             �\n");
 printf("�                                                                           �\n");
 printf("� /P:Password     Password to pass to CICS Server                           �\n");
 printf("�                                                                           �\n");
 printf("� /? or /Help     This most excellent help panel.                           �\n");
 printf("�                                                                           �\n");
 printf("藩様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様様�\n");
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
      printf(" \b");
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
 
    printf( "  Return Code : %s\n", ErrTxt );
    memcpy( DisplayField, EciBlock.eci_abend_code, 4 );
    printf( "  Abend Code  : '%s'\n", DisplayField);
    printf( "  SysCode     : %d\n", EciBlock.eci_sys_return_code);

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

     printf(" ------------------------------------------------------ \n"); 

     EciBlock.eci_call_type = ECI_SYNC;
     memcpy( EciBlock.eci_program_name, "FAADECIC", 8 );

     EciBlock.eci_commarea = CommArea;
     EciBlock.eci_commarea_length = COMMAREA_SIZE;

     EciBlock.eci_timeout = 0;

     EciBlock.eci_extend_mode = ECI_NO_EXTEND;
     EciBlock.eci_version = ECI_VERSION_1;

     printf( "  Calling     : CICS_ExternalCall\n" );
     printf( "  System      : %s\n", EciBlock.eci_system_name );

     /* Call CICS ECI with the parameter block filled out above. */
     ECIRetCode = CICS_ExternalCall( &EciBlock );

     Process_Return_Code(ECIRetCode);

     /* Display results. */
     if (ECIRetCode==ECI_NO_ERROR)
       {
         CommArea[10017] = '\0';
         printf( "  CommArea    : %s\n", CommArea );
       }

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

     memset( CommArea, 'x', COMMAREA_SIZE );
     memset( &EciBlock, 0, sizeof(ECI_PARMS) );

     ParseParms( argc, argv );

     /*-------------------------------------------------------------------*/
     /* Call CICS ECI with the parameter block as above.                  */
     /*-------------------------------------------------------------------*/

     printf("--------------------------------------------------------\n"); 
     printf(" ECI SYNOC001 - Original Coding by A.Thomson Summer 94 \n"); 
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
    printf(" ECI SYNOC001 - Application Termination\n"); 
    printf("--------------------------------------------------------\n"); 

    return(0);
  }

 /*-------------------------------------------------------------------*/
 /*                   END OF PROGRAM SYNOC001.C                       */
 /*-------------------------------------------------------------------*/
