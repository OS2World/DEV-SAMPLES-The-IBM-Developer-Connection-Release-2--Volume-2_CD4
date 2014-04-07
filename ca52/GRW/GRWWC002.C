
 /*-------------------------------------------------------------------*/
 /*                                                                   */
 /*  Program Name  : GRWWC002.C                                       */
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
 /*  Code Origin : Based upon ECI Sample code by D.Smith              */
 /*                                                                   */
 /*  Compile Options Used by Developers :                             */
 /*                                                                   */
 /*  NMAKE GRWWC002.MAK                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 #define CICS_WIN

 #define COMMAREA_SIZE 18
 #define NUMRECS       6

 /* C include files: */
 #include <stdio.h>
 #include <stdlib.h>
 #include <stdarg.h>
 #include <string.h>
 /* Windows include file: */
 #include <windows.h>
 /* ECI include file: */
 #include "cics_eci.h"
 /* Program include file: */
 #include "grwwc002.h"

 /* Function declarations: */
 int PASCAL WinMain ( HANDLE hInst,
                     HANDLE hPrevInst,
                     LPSTR lpCmd,
                     int nCmdShow ) ;
 long FAR PASCAL MainWindowProc ( HWND hwnd,
                                 unsigned msg,
                                 WORD wParam,
                                 LONG lParam ) ;
 void Info ( char *Format, ... ) ;
 void Response ( char *Call, cics_sshort_t ECIRetCode ) ;

 unsigned short far pascal DosSleep( unsigned long );

 /* Global variables */
 HANDLE      hInstance            ;
 HWND        hwndFrame            ;
 HWND        hwndList             ;
 char        szBuffer     [ 512 ] ;
 ECI_PARMS   EciBlock             ;
 char        szCommArea   [COMMAREA_SIZE] ;

 int       Num_LUWS;
 int       CallCount;
 int       Iterations;
 float     Delay;
 int       Unattended;

 extern int    __argc;           /*  Access the command */
 extern char **__argv;           /*   line parameters   */

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
 Info("                                                                            ");
 Info("  ECI GRWWC002 - A.Thomson Summer 94 (See CCECIDOC for Documentation)       ");
 Info("                                                                            ");
 Info("  C program making EXTENDED call type GET_REPLY_WAIT                        ");
 Info("                                                                            ");
 Info("  Input Parameter Help;                                                     ");
 Info("                                                                            ");
 Info("  /I:iterations   The number of times to run this test.                     ");
 Info("                  0 or greater than 32000 results in infinite loop          ");
 Info("                                                                            ");
 Info("  /L:luws         Number of concurrent Logical Units of Work                ");
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
    Num_LUWS    = 1;

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
                sprintf(TempText,"Iterations out of range 0 used instead of %s",
                                                                     &argv[j][3]);
                Info(TempText);                                                         
                Iterations = 0;
             } else {
                sprintf(TempText,"Iterations changed to %s",&argv[j][3]);
                Info(TempText);                                                         
             } /* endif */
             Unattended = 1;
             break;
          case 'L' :                         /* /D:Delay              */
             Num_LUWS = atoi ( &argv[j][3] );
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
             sprintf(TempText,"Ignoring Parameter %s",&argv[j][0]);
             Info(TempText);                                                         
             break;
          } /* endswitch */
       }
     else
       {
         sprintf(TempText,"Parameters must begin with a /=");
         Info(TempText);                                                         
       }
     j++;
  } /* endwhile */
 
 }

 /*-------------------------------------------------------------------*/
 /* Procedure    : Info                                               */
 /*                                                                   */
 /* Reference    : Code and Design by A.Thomson 1994                  */
 /*                                                                   */
 /* Function     : To output a single line of text to the application */
 /*                main window. Also prunes at 500 lines and          */
 /*                maintains view point at latest entries.            */
 /*                                                                   */
 /* Input        : Text String                                        */
 /*                                                                   */
 /* Assumptions  : None                                               */
 /*                                                                   */
 /* Required sub : None                                               */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : Void                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 void Info( char *Format, ... )
 {
   va_list ArgPtr;
   WORD Index;

   va_start ( ArgPtr, Format ) ;
   vsprintf ( szBuffer, Format, ArgPtr ) ;
   va_end ( ArgPtr ) ;
              
   Index = SendMessage ( hwndList, LB_INSERTSTRING, -1, ( LONG ) szBuffer ) ;
   if (Index>500)
     {
       SendMessage ( hwndList, WM_SETREDRAW, 0, 0L ) ;
       SendMessage ( hwndList, LB_DELETESTRING, 0, 0L ) ;
       SendMessage ( hwndList, WM_SETREDRAW, 1, 0L ) ;
       Index--; 
     }
   SendMessage (hwndList, LB_SETTOPINDEX, Index, 0L);

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
       }
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
 
    info("      Return Code : %s", ErrTxt );
    memcpy( DisplayField, EciBlock.eci_abend_code, 4 );
    info( "      Abend Code  : '%s'", DisplayField);
    info( "      SysCode     : %d", EciBlock.eci_sys_return_code);

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

     char *ErrTxt;
     int Record, LUW; /* loop counters */   
     long Tokens[100]; /* Array for LUW Tokens. 100 should be enougth! */
     cics_sshort_t ECIRetCode; /* Return code from the ECI call. */ 
     char CommArea[COMMAREA_SIZE]; /* comm area to be passed  */ 

     Info(" -------------------------------------------------------------------------------- "); 
     strcpy ( szCommArea, "Original Data" ) ;

     EciBlock.eci_version = ECI_VERSION_1;
     EciBlock.eci_call_type                         = ECI_ASYNC ;
     memcpy ( &EciBlock.eci_program_name, "FAADECIC", 8 ) ;
     EciBlock.eci_commarea                          = szCommArea ;
     EciBlock.eci_commarea_length                   = COMMAREA_SIZE ;
     EciBlock.eci_extend_mode                       = ECI_NO_EXTEND ;
     EciBlock.eci_async_notify.win_fields.hwnd      = hwndFrame ;
     EciBlock.eci_async_notify.win_fields.hinstance = hInstance ;
     EciBlock.eci_message_id                        = ECI_MSG ;
     EciBlock.eci_luw_token                         = ECI_LUW_NEW ;

     for (LUW=1; LUW<=Num_LUWS; LUW++) Tokens[LUW]=0;   

     /* Add requested number of records for each requested LUW */
                  
     for (Record=1; Record<=NUMRECS; Record++) /* For Each Record */
       {

         switch(Record)
         {
         case 3       :
           EciBlock.eci_extend_mode=ECI_BACKOUT;
           info("  Set of Calls %d (Extend_Mode = ECI_BACKOUT)", Record);
           break; 
         case NUMRECS :
           EciBlock.eci_extend_mode=ECI_COMMIT;
           info("  Set of Calls %d (Extend_Mode = ECI_COMMIT)", Record);
           break; 
         default      :
           EciBlock.eci_extend_mode=ECI_EXTENDED;
           info("  Set of Calls %d (Extend_Mode = ECI_EXTENDED)", Record);
           break; 
         }

         for (LUW=1; LUW<=Num_LUWS; LUW++) /* For each LUW */
           {            
  
             EciBlock.eci_luw_token=Tokens[LUW];
              
             ECIRetCode = CICS_ExternalCall( &EciBlock ); 

             if ( ECIRetCode == ECI_NO_ERROR )
               { 
                 EciBlock.eci_call_type = ECI_GET_REPLY_WAIT;
                 ECIRetCode = CICS_ExternalCall ( &EciBlock );
               }
  
             if ( ECIRetCode == ECI_NO_ERROR )
               {
                 CommArea[17] = '\0';
                 info( "       LUW=%d : Extend_Mode=%d : RC=%d : Token=%d",
                       LUW,
                       EciBlock.eci_extend_mode,
                       ECIRetCode,
                       Tokens[LUW] );
                 info("        Commarea = %s", EciBlock.eci_commarea );
               }
             else
               {
                 info( "    LUW %d",LUW );
                 info( "      Extend_Mode : %d",EciBlock.eci_extend_mode );
                 Process_Return_Code(ECIRetCode);
               }
             
             Tokens[LUW]=EciBlock.eci_luw_token;  
  
             Perform_Delay(Delay); 

           }
       }

     Info(" -------------------------------------------------------------------------------- "); 
     Perform_Delay(Delay); 
  }

 /*-------------------------------------------------------------------*/
 /* Procedure    : WinMain                                            */
 /*                                                                   */
 /* Reference    : Stolen from somebody                               */
 /*                                                                   */
 /* Function     : Initialize, create & handle window messages        */
 /*                                                                   */
 /* Input        : lots                                               */
 /*                                                                   */
 /* Assumptions  : Many                                               */
 /*                                                                   */
 /* Required sub : None                                               */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : int                                                */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 int PASCAL WinMain ( HANDLE hInst,
                      HANDLE hPrevInst,
                      LPSTR lpCmd,
                      int nCmdShow )
 {
   MSG          msg  ;
   WNDCLASS     wc   ;
   RECT         rc   ;
 
   /* Save the instance handle in a global */
   hInstance = hInst;
   
   /* Application specific initialisation */
   if ( hPrevInst == NULL ) {
      /* Create the window class */
      wc.style         = CS_HREDRAW | CS_VREDRAW;
      wc.lpfnWndProc   = MainWindowProc;
      wc.cbClsExtra    = 0;
      wc.cbWndExtra    = 0;
      wc.hInstance     = hInstance;
      wc.hIcon         = LoadIcon(NULL, IDI_APPLICATION);
      wc.hCursor       = LoadCursor(NULL, IDC_ARROW);
      wc.hbrBackground = COLOR_WINDOW+1;
      wc.lpszMenuName  = ECI_MENU;
      wc.lpszClassName = ECI_CLASS;
      if ( !RegisterClass ( &wc ) ) {
         return 8 ;
      } /* endif */
    } /* endif */

    /* Create the window */
    hwndFrame = CreateWindow ( ECI_CLASS,
                               "CICS Client Test - GRWWC002 (A.Thomson 94)",
                               WS_OVERLAPPEDWINDOW,
                               CW_USEDEFAULT, CW_USEDEFAULT,
                               450, 600,
                               NULL,
                               NULL,
                               hInstance,
                               NULL ) ;
    /* Check the result: */
    if ( hwndFrame == NULL ) {
       return 8 ;
    } /* endif */

    /* Create a listbox as the client area */
    GetClientRect ( hwndFrame, &rc ) ;
    hwndList = CreateWindow ( "Listbox",
                             NULL,
                              WS_CHILD | WS_VISIBLE | WS_VSCROLL |
                              LBS_NOTIFY | LBS_NOINTEGRALHEIGHT,
                              rc.left, rc.top,
                              rc.right - rc.left, rc.bottom - rc.top,
                              hwndFrame,
                              NULL,
                              hInstance,
                              NULL ) ;
    /* Check the result: */
    if ( hwndList == NULL ) {
       return 8 ;
    } /* endif */

    /* Show the window: */
    ShowWindow ( hwndFrame, nCmdShow ) ;
    UpdateWindow ( hwndFrame ) ;

    Info(" -------------------------------------------------------------------------------- "); 
    Info(" ECI GRWWC002 - A.Thomson Summer 94 "); 
    Info("                Last Compiled "__DATE__" at "__TIME__""); 
    Info(" -------------------------------------------------------------------------------- "); 

    /* Do the standard message loop */
    while ( GetMessage ( &msg, NULL, 0, 0 ) ) {
       TranslateMessage ( &msg ) ;
       DispatchMessage ( &msg ) ;
    } /* endwhile */

    /* End the program: */
    return 0 ;
 }

 /*-------------------------------------------------------------------*/
 /* Procedure    : MainWindowProc                                     */
 /*                                                                   */
 /* Reference    : Stolen from somebody                               */
 /*                                                                   */
 /* Function     : Handle Standard window stuff                       */
 /*                                                                   */
 /* Input        : lots                                               */
 /*                                                                   */
 /* Assumptions  : Many                                               */
 /*                                                                   */
 /* Required sub : None                                               */
 /* procedures                                                        */
 /*                                                                   */
 /* Returns      : long                                               */
 /*                                                                   */
 /*-------------------------------------------------------------------*/

 long FAR PASCAL MainWindowProc ( HWND     hwnd,
                                  unsigned msg,
                                  WORD     wParam,
                                  LONG     lParam )
 {
   char            Name [ 80 ]       ;
   int             Size              ;
   unsigned short  Index             ;
   short           ECIRetCode           ;
   char            szCommarea [ 80 ] ;
   int             Not_Finished      ;

   switch ( msg ) {
      case WM_CREATE:
         /* When the window is created post ourselves a PARSE_PARMS */
         /* so we can get at the command line parameters            */
         PostMessage(hwnd, PARSE_PARMS, 0, 0L);
         break;
      case PARSE_PARMS:
         memset(&EciBlock,0,sizeof(EciBlock));
         ParseParms(__argc, __argv);
         if (Unattended==1) SendMessage(hwnd, WM_COMMAND, CALL_ECI, 0L);
         break; 
      case WM_SIZE    :
         MoveWindow ( hwndList,
                      0, 0,
                      LOWORD ( lParam ),
                      HIWORD ( lParam ),
                      TRUE ) ;
         break ;
      case WM_COMMAND :
         switch ( wParam ) {
            case ECI_ITEM_CLEAR :
               SendMessage ( hwndList, LB_RESETCONTENT, 0, 0L ) ;
               break;
            case CALL_ECI       :
               switch(Iterations)
                 {
                 case 0: /* If Zero Iterations specified then loop forever */ 
                   Make_One_Call_Set();
                   sprintf(szBuffer,"  Iteration %d of Infinite Loop Complete",CallCount);
                   Info(szBuffer);
                   CallCount++;       
                   SendMessage(hwnd, WM_COMMAND, CALL_ECI, 0L);
                  break; 
                 case 1: /* If 1 Iteration specified then just make one call */ 
                  Make_One_Call_Set();
                  Info(" ");
                  break; 
                 default: /* Otherwise loop to make the requested number of calls */ 
                  Make_One_Call_Set();
                  sprintf(szBuffer,"  Iteration %d of %d Complete",CallCount,Iterations);
                  Info(szBuffer);
                  CallCount++; 
                  if (CallCount<=Iterations)
                    SendMessage(hwnd, WM_COMMAND, CALL_ECI, 0L);
                  else
                    CallCount=1; /* Reset ready to go again */ 
                 }
               break ;
            default :
               break ;
         } /* endswitch */
         break;
      case WM_DESTROY :
         PostQuitMessage ( 0 ) ;
         break;
      default:
         return DefWindowProc ( hwnd, msg, wParam, lParam ) ;
   } /* endswitch */

   return ( LONG ) 0 ;
 }


 /*-------------------------------------------------------------------*/
 /*                   END OF PROGRAM GRWWC002.C                       */
 /*-------------------------------------------------------------------*/          

