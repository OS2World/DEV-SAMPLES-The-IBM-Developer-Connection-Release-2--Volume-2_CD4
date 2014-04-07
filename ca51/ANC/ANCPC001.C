
 /*-------------------------------------------------------------------*/
 /*                                                                   */
 /*  Program Name  : ANCPC001.C                                       */
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
 /*  Code Origin : Based upon ECI Sample code by P.Warren             */
 /*                                                                   */
 /*  Compile Options Used by Developers :                             */
 /*                                                                   */
 /*  NMAKE ancpc001.MAK                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 #define CICS_OS2

 #define INCL_WIN
 #define INCL_GPI
 #define INCL_DOS

 #include <os2.h>
 #include <stdlib.h>
 #include <stdio.h>
 #include <string.h>
 #include <cics_eci.h>
 #include "ancpc001.h"

 MRESULT EXPENTRY  MainWindowProc ( HWND, USHORT, MPARAM, MPARAM ) ;
 void Process_Return_Code(int, HWND);
 
 PFNWP SystemListboxWindowProc ;
 
 int         CallCount;
 int         Iterations;
 float       Delay;
 int         Unattended;
 ECI_PARMS   EciBlock;
 CHAR        CommArea [18] ;
 int         pos=1; 
 HWND        hwndFrame;
 HWND        MainHwnd;

 /*-------------------------------------------------------------------*/
 /* Procedure    : Info                                               */
 /*                                                                   */
 /* Reference    : Code and Design by A.Thomson Summer 1994           */
 /*                                                                   */
 /* Function     : Output line of text to the Window                  */
 /*                                                                   */
 /* Input        : Test String                                        */
 /*                                                                   */
 /* Assumptions  : None                                               */
 /*                                                                   */
 /* Required sub : None                                               */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : Void                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 void Info(char *Text)
   {
     MRESULT Index;

     Index = WinSendMsg(MainHwnd,
                        LM_INSERTITEM,
                        MPFROMSHORT(LIT_END),  
                        MPFROMP(Text));

     if (Index>MPFROMSHORT(500))
       Index = WinSendMsg(MainHwnd, LM_DELETEITEM, 0, 0L) ;
 
     WinSendMsg (MainHwnd, LM_SETTOPINDEX, Index, NULL);

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
 Info("ษอออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออป");
 Info("                                                                            ");
 Info("  ECI ANCPC001 - A.Thomson Summer 94 (See CCECIDOC for Documentation)       ");
 Info("                                                                            ");
 Info("  C program making call type ECI_ASYNC with callback notification           ");
 Info("                                                                            ");
 Info("  Input Parameter Help;                                                     ");
 Info("                                                                            ");
 Info("  /I:iterations   The number of times to run this test.                     ");
 Info("                  0 or greater than 32000 results in infinite loop          ");
 Info("                                                                            ");
 Info("  /D:Delay        Value specifying time to wait between call sets           ");
 Info("                                                                            ");
 Info("  /S:Server       Name of CICS Server to Execute the ECI Calls              ");
 Info("                                                                            ");
 Info("  /U:UserID       Userid to pass to CICS Server                             ");
 Info("                                                                            ");
 Info("  /P:Password     Password to pass to CICS Server                           ");
 Info("                                                                            ");
 Info("  /? or /Help     This most excellent help panel.                           ");
 Info("                                                                            ");
 Info("ศอออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออผ");
 }

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
    char TempText[80];

    CallCount   = 1;
    Iterations  = 1;
    Delay       = 0;
    while ( j < argc )  {
       if ( strchr ( "/-(", argv[j][0] ) ) { /* looks like an option  */
          argv[j] = strupr ( argv[j] );             
          switch ( argv[j][1] )  {
            case '?' :                          /* /?:HELP               */
            case 'H' :                          /* /H:                   */
              SayHelp();          
              break;            
          case 'I' :                         /* /I:iterations         */
             Iterations = atoi ( &argv[j][3] );
             if( ( Iterations < 0 ) || ( Iterations  > 32000 ) ) {
                sprintf("Iterations out of range 0 used instead of %s\n",
                                                              &argv[j][3]);
                Iterations = 0;
             } else {
                printf("Iterations changed to %s\n",&argv[j][3]);
             } /* endif */
             Unattended = 1;
             break;
          case 'D' :                         /* /D:Delay              */
             Delay = atof ( &argv[j][3] );
             Delay = Delay * 400; /* Make it a reasonable value */
             break;
          case 'S' :                         /* /S:Server             */
             strcpy( EciBlock.eci_system_name, &argv[j][3]);
             sprintf(TempText,"Server Selected = %s",EciBlock.eci_system_name);
             Info(TempText);                                                         
             break;
          case 'U' :                         /* /U:UserID             */
             strcpy( EciBlock.eci_userid, &argv[j][3]);
             sprintf(TempText,"User ID Selected = %s",EciBlock.eci_userid);
             Info(TempText);                                                         
             break;
          case 'P' :                         /* /P:Password           */
             strcpy( EciBlock.eci_password, &argv[j][3]);
             sprintf(TempText,"Password Supplied = %s",EciBlock.eci_password);
             Info(TempText);                                                         
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
     DosSleep(Duration*2); 
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

 void Process_Return_Code(ECIRetCode, hwnd)
   cics_sshort_t ECIRetCode; 
   HWND hwnd;
   {
    char *ErrTxt;
    char DisplayField[]= "0000\0" ;
    char szBuffer [ 80 ] ;
    
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
 
    sprintf(szBuffer,"Return Code : %s",ErrTxt);
    Info(szBuffer);
    memcpy( DisplayField, EciBlock.eci_abend_code, 4 );
    sprintf(szBuffer,"Abend Code  : '%s'", DisplayField);
    Info(szBuffer);
    sprintf(szBuffer,"SysCode     : %d", EciBlock.eci_sys_return_code);
    Info(szBuffer);

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
    HAB         hab;
    HMQ         hmq;
    QMSG        qmsg;
    HWND        hwndClient;
    ULONG       flCreate;
    PID         pid;
    TID         tid;
    HSWITCH     hswitch;
    SWCNTRL     swctl ;
 
    /***************************************************************************/
    /* Initialize PM and create a window with a listbox as a subclassed client */
    /* area:                                                                   */
    /***************************************************************************/
    hab = WinInitialize ( 0 ) ;
    hmq = WinCreateMsgQueue ( hab, 0 ) ;
    flCreate = FCF_TITLEBAR | FCF_SYSMENU | FCF_MINMAX | FCF_SIZEBORDER |
               FCF_ICON | FCF_ACCELTABLE | FCF_MENU;
    hwndFrame = WinCreateStdWindow ( HWND_DESKTOP,
                                     FS_SCREENALIGN,
                                     &flCreate,
                                     WC_LISTBOX,
                                     "CICS Client Test - ancpc001 (A.Thomson 94)",
                                     LS_HORZSCROLL,
                                     0,
                                     ID_MAINWND,
                                     ( PHWND ) &hwndClient ) ;
    WinSetWindowPos ( hwndFrame, HWND_TOP,
                      10, 10, 855, 475,
                      SWP_SIZE | SWP_MOVE | SWP_ACTIVATE | SWP_SHOW ) ;
    SystemListboxWindowProc = WinSubclassWindow ( hwndClient,
                                                  MainWindowProc ) ;
 
    /***************************************************************************/
    /* Add the main window to the switch list:                                 */
    /***************************************************************************/
    WinQueryWindowProcess ( hwndFrame, &pid, &tid ) ;
    swctl.hwnd = hwndFrame ;
    swctl.hwndIcon = NULL ;
    swctl.hprog = NULL ;
    swctl.idProcess = pid ;
    swctl.idSession = NULL ;
    swctl.uchVisibility = SWL_VISIBLE ;
    swctl.fbJump = SWL_JUMPABLE ;
    sprintf ( swctl.szSwtitle, "CICS Client Test - ANCPC001" ) ;
    hswitch = WinAddSwitchEntry ( &swctl ) ;
 
    /*********************************************************************/
    /* Initialise the ECI parameter block:                              */
    /*********************************************************************/

    memset ( &EciBlock, 0, sizeof ( EciBlock ) ) ;
    EciBlock.eci_version = ECI_VERSION_1;

    /***************************************************************************/
    /* check for unattended operation mode                                     */
    /***************************************************************************/

    WinSetFocus( HWND_DESKTOP, hwndFrame );
    ParseParms( argc, argv );

    if (Unattended)
      {
        WinPostMsg( hwndFrame, WM_COMMAND, (MPARAM)ID_UNATTENDED, 0L );
       } /* endif */

    /***************************************************************************/
    /* Message loop:                                                           */
    /***************************************************************************/
    while ( WinGetMsg ( hab, ( PQMSG ) &qmsg, (HWND)NULL, 0, 0 ) )
      WinDispatchMsg ( hab, ( PQMSG ) &qmsg );
 
    /***************************************************************************/
    /* Exit:                                                                   */
    /***************************************************************************/
    WinRemoveSwitchEntry ( hswitch ) ;
    WinDestroyWindow ( hwndFrame ) ;
    WinDestroyMsgQueue ( hmq ) ;
    WinTerminate ( hab ) ;
 
    return 0 ;
 }
 
 /*-------------------------------------------------------------------*/
 /* Procedure    : Call_Back_Proc                                     */
 /*                                                                   */
 /* Reference    : Code and Design by A.Thomson Summer 1994           */
 /*                                                                   */
 /* Function     : Procedure for CICS to call when it is ready for    */
 /*                to get the response from our application           */
 /*                                                                   */
 /* Input        : cics_ushort_t                                      */
 /*                                                                   */
 /* Assumptions  : None                                               */
 /*                                                                   */
 /* Required sub : None                                               */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : Void                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 void CICSEXIT CallBackProc (cics_ushort_t idx)
  {

    WinPostMsg( hwndFrame, WM_COMMAND, (MPARAM)ID_GET_REPLY, 0L );

  } 

 /*-------------------------------------------------------------------*/
 /* Procedure    : Make_One_Call_Set                                  */
 /*                                                                   */
 /* Reference    : Code and Design by A.Thomson Summer 1994           */
 /*                                                                   */
 /* Function     : To Make one set of ECI Calls                       */
 /*                                                                   */
 /* Input        : hwnd                                               */
 /*                                                                   */
 /* Assumptions  : None                                               */
 /*                                                                   */
 /* Required sub : None                                               */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : Void                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 void Make_One_Call_Set(HWND hwnd)
   {
     cics_sshort_t ECIRetCode;

     /*********************************************************************/
     /* Set up the commarea:                                              */
     /*********************************************************************/
     strcpy ( CommArea,
              "ECI async data" ) ;
     /*********************************************************************/
     /* Set the values:                                                   */
     /*********************************************************************/
     EciBlock.eci_version = ECI_VERSION_1;
     EciBlock.eci_call_type = ECI_ASYNC;
     memcpy ( &EciBlock.eci_program_name,
              "FAADECIC",
              sizeof ( EciBlock.eci_program_name ) ) ;
     EciBlock.eci_commarea = &CommArea ;
     EciBlock.eci_commarea_length = sizeof ( CommArea ) ;
     EciBlock.eci_timeout = 0 ;
     EciBlock.eci_extend_mode = ECI_NO_EXTEND ;
     EciBlock.eci_luw_token = 0L; 
     EciBlock.eci_callback = (CICS_EciNotify_t)&CallBackProc;

     /*********************************************************************/
     /* Call the ECI:                                                    */
     /*********************************************************************/
     Info("Calling      : ECI_ASYNC with callback notification");
     ECIRetCode = CICS_ExternalCall ( &EciBlock ) ;
     /*********************************************************************/
     /* Report on response:                                               */
     /*********************************************************************/
     Process_Return_Code(ECIRetCode, hwnd);

   }

 /*-------------------------------------------------------------------*/
 /* Procedure    : MainWindowProc                                     */
 /*                                                                   */
 /* Reference    : Stolen Code ...                                    */
 /*                                                                   */
 /* Function     : ...                                                */
 /*                                                                   */
 /* Input        : ...                                                */
 /*                                                                   */
 /* Assumptions  : ...                                                */
 /*                                                                   */
 /* Required sub : ...                                                */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : ...                                                */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 MRESULT EXPENTRY MainWindowProc ( HWND   hwnd,
                                   USHORT msg,
                                   MPARAM mp1,
                                   MPARAM mp2 )
 {
    USHORT command;
    CHAR   szBuffer[80];
    int   ECIRetCode;

    MainHwnd=hwnd;
    switch ( msg ) {
    case WM_COMMAND:
       command = SHORT1FROMMP ( mp1 ) ;
       switch ( command ) {
       case ID_CALL_ECI :
          Info("ออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออ");
          Make_One_Call_Set(hwnd);
          Info("ออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออ");
          Perform_Delay(Delay); 
          break;
       case ID_GET_REPLY :
          Info("ออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออ");
          /************************************************************************/
          /* The ECI is responding to the application. Report the fact:          */
          /************************************************************************/
          Info("Calling      : ECI_GET_REPLY");
          /************************************************************************/
          /* Set up the ECI parameter block to solicit the status:               */
          /************************************************************************/
          EciBlock.eci_call_type = ECI_GET_REPLY ;
          EciBlock.eci_commarea = &CommArea ;
          EciBlock.eci_commarea_length = sizeof ( CommArea ) ;
          /************************************************************************/
          /* Call the ECI:                                                       */
          /************************************************************************/
          ECIRetCode = CICS_ExternalCall ( &EciBlock ) ;
          /************************************************************************/
          /* Show the information returned:                                       */
          /************************************************************************/
          Process_Return_Code(ECIRetCode, hwnd);
          if (ECIRetCode==ECI_NO_ERROR)
            {
              sprintf(szBuffer,"Commarea     : %s", &CommArea ) ;
              Info(szBuffer);
            }
          Info("ออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออออ");
          break;
       case ID_UNATTENDED :      
         switch(Iterations)
           {
 
             case 0: /* If Zero Iterations specified then loop forever */ 
               WinPostMsg( hwndFrame, WM_COMMAND, (MPARAM)ID_CALL_ECI, 0L );
               sprintf ( szBuffer, "Iteration %d of Infinite Loop", pos);
               Info(szBuffer);
               DosSleep(1000);
               pos++; 
               WinPostMsg( hwndFrame, WM_COMMAND, (MPARAM)ID_UNATTENDED, 0L );
               break; 
     
             case 1: /* If 1 Iteration specified then just make one call */ 
               WinPostMsg( hwndFrame, WM_COMMAND, (MPARAM)ID_CALL_ECI, 0L );
               break; 
   
             default: /* Otherwise loop to make the requested number of calls */ 
               if (pos<=Iterations)
                 {
                   WinPostMsg( hwndFrame, WM_COMMAND, (MPARAM)ID_CALL_ECI, 0L );
                   sprintf ( szBuffer, "Iteration %d of %d",pos,Iterations);
                   Info(szBuffer);
                   DosSleep(1000);
                   pos++; 
                   WinPostMsg( hwndFrame, WM_COMMAND, (MPARAM)ID_UNATTENDED, 0L );
                 } break; 
           }
          break;
       case ID_EXITPROGRAM :
          WinPostMsg( hwnd, WM_CLOSE, 0L, 0L );
          break;
       }
       break;
    case WM_CLOSE:
       WinPostMsg ( hwnd, WM_QUIT, 0L, 0L ) ;
       break;
    default:
       return ( SystemListboxWindowProc ( hwnd, msg, mp1, mp2 ) ) ;
    }
    return ( MRESULT ) FALSE ;
 }

 /*-------------------------------------------------------------------*/
 /*                   END OF PROGRAM ANCPC001.C                       */
 /*-------------------------------------------------------------------*/
