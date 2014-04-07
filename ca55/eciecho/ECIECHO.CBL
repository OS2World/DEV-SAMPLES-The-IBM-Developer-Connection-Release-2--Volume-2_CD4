      *********************************************************************
      *                                                                   *
      * MODULE NAME        ECIECHO.CBL                                    *
      *                                                                   *
      * DESCRIPTIVE NAME   CICS External Call Interface                   *
      *                    OS/2 Sample - COBOL Source                     *
      *                    CA55 SupportPac                                *
      *                                                                   *
      * Statement:         (C) Copyright IBM Corporation 1995,            *
      *                                                                   *
      * Status:            Version 1 Release 0                            *
      *                                                                   *
      * Components:        ECIECHO.CBL                                    *
      *                    ECIECHO.BAT                                    *
      *                    CCLCOBOL.DEF                                   *
      *                                                                   *
      * NOTES :-                                                          *
      *                                                                   *
      * ECIECHO is an example application which demonstrates how to       *
      * use the External Call Interface (ECI).                            *
      *                                                                   *
      * It does not demonstrate all the techniques required for a         *
      * large application.  This sample is an example of the form of      *
      * an ECI application; it is not a template and should not be used   *
      * as the foundation for your next mission critical application.     *
      *                                                                   *
      * This sample requires the transaction ECHO to be installed on      *
      * your server. The source ECHO.CCP can be found in the subdirectory *
      * ..\SERVER                                                         *
      *                                                                   *
      * You must fill in the name of your SERVER, USERID and PASSWORD     *
      * in the GLOBALS section below                                      *
      *********************************************************************

       IDENTIFICATION DIVISION.
       PROGRAM-ID.    ECIECHO.

       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.

      ***************************************************************
      * Variable definitions                                        *
      ***************************************************************
       01 WS-AREA.
          02 COMMAREA        VALUE LOW-VALUES   PIC X(100).

      * FILL IN YOUR SERVER HERE
          02 SERVER                     PIC X(8) VALUE LOW-VALUES.

      * FILL IN YOUR USER ID HERE
          02 USERID                     PIC X(8) VALUE 'SYSAD   '.

      * FILL IN YOUR PASSWORD HERE
          02 PASSWD                     PIC X(8) VALUE 'SYSAD   '.

          02  WS-CONNECTION-TEXT.
              05  FILLER                PIC X(21)
              VALUE "Connection Type : ".
              05  FILLER                PIC X(24).
                  88 WS-ECI-CONNECTED-NOWHERE
                  VALUE "ECI_CONNECTED_NOWHERE".
                  88 WS-ECI-CONNECTED-TO-SERVER
                  VALUE "ECI_CONNECTED_TO_SERVER".
                  88 WS-ECI-CONNECTED-TO-CLIENT
                  VALUE "ECI_CONNECTED_TO_CLIENT".
                  88 WS-ECI-CONNECTED-UNKNOWN
                  VALUE "Unknown".
          02  WS-SERVERSTATUS-TEXT.
              05  FILLER                PIC X(21)
              VALUE "Server status   : ".
              05  FILLER                PIC X(21).
                  88 WS-ECI-SERVERSTATE-UP
                  VALUE "ECI_SERVERSTATE_UP".
                  88 WS-ECI-SERVERSTATE-DOWN
                  VALUE "ECI_SERVERSTATE_DOWN".
                  88 WS-ECI-SERVERSTATE-UNKNOWN
                  VALUE "Unknown".
          02  WS-CLIENTSTATUS-TEXT.
              05  FILLER                PIC X(21)
              VALUE "Client status   : ".
              05  FILLER                PIC X(29).
                  88 WS-ECI-CLIENTSTATE-UP
                  VALUE "ECI_CLIENTSTATE_UP".
                  88 WS-ECI-CLIENTSTATE-INAPPL
                  VALUE "ECI_CLIENTSTATE_INAPPLICABLE".
                  88 WS-ECI-CLIENTSTATE-UNKNOWN
                  VALUE "Unknown".
          02 STARTPOINT      USAGE IS PROCEDURE-POINTER.
      * Used by Response
          02 RESPONSE-TEXT.
             05  NAME-TEXT             PIC X(25).
             05  FILLER                PIC X(10)
             VALUE "returned: ".
             05  FILLER                PIC X(30).
                 88 ECI-NO-ERROR-T
                 VALUE "ECI_NO_ERROR".
                 88 ECI-ERR-INVALID-DATA-LENGTH-T
                 VALUE "ECI_ERR_INVALID_DATA_LENGTH".
                 88 ECI-ERR-INVALID-EXTEND-MODE-T
                 VALUE "ECI_ERR_INVALID_EXTEND_MODE".
                 88 ECI-ERR-NO-CICS-T
                 VALUE "ECI_ERR_NO_CICS".
                 88 ECI-ERR-CICS-DIED-T
                 VALUE "ECI_ERR_CICS_DIED".
                 88 ECI-ERR-REQUEST-TIMEOUT-T
                 VALUE "ECI_ERR_REQUEST_TIMEOUT".
                 88 ECI-ERR-RESPONSE-TIMEOUT-T
                 VALUE "ECI_ERR_RESPONSE_TIMEOUT".
                 88 ECI-ERR-TRANSACTION-ABEND-T
                 VALUE "ECI_ERR_TRANSACTION_ABEND".
                 88 ECI-ERR-EXEC-NOT-RESIDENT-T
                 VALUE "ECI_ERR_EXEC_NOT_RESIDENT".
                 88 ECI-ERR-SYSTEM-ERROR-T
                 VALUE "ECI_ERR_SYSTEM_ERROR".
                 88 ECI-ERR-NULL-WIN-HANDLE-T
                 VALUE "ECI_ERR_NULL_WIN_HANDLE".
                 88 ECI-ERR-NULL-MESSAGE-ID-T
                 VALUE "ECI_ERR_NULL_MESSAGE_ID".
                 88 ECI-ERR-THREAD-CREATE-ERROR-T
                 VALUE "ECI_ERR_THREAD_CREATE_ERROR".
                 88 ECI-ERR-INVALID-CALL-TYPE-T
                 VALUE "ECI_ERR_INVALID_CALL_TYPE".
                 88 ECI-ERR-ALREADY-ACTIVE-T
                 VALUE "ECI_ERR_ALREADY_ACTIVE".
                 88 ECI-ERR-RESOURCE-SHORTAGE-T
                 VALUE "ECI_ERR_RESOURCE_SHORTAGE".
                 88 ECI-ERR-NO-SESSIONS-T
                 VALUE "ECI_ERR_NO_SESSIONS".
                 88 ECI-ERR-NULL-SEM-HANDLE-T
                 VALUE "ECI_ERR_NULL_SEM_HANDLE".
                 88 ECI-ERR-INVALID-DATA-AREA-T
                 VALUE "ECI_ERR_INVALID_DATA_AREA".
                 88 ECI-ERR-INVALID-VERSION-T
                 VALUE "ECI_ERR_INVALID_VERSION".
                 88 ECI-ERR-UNKNOWN-SERVER-T
                 VALUE "ECI_ERR_UNKNOWN_SERVER".
                 88 ECI-ERR-CALL-FROM-CALLBACK-T
                 VALUE "ECI_ERR_CALL_FROM_CALLBACK".
                 88 ECI-ERR-INVALID-TRANSID-T
                 VALUE "ECI_ERR_INVALID_TRANSID".
                 88 ECI-ERR-MORE-SYSTEMS-T
                 VALUE "ECI_ERR_MORE_SYSTEMS".
                 88 ECI-ERR-NO-SYSTEMS-T
                 VALUE "ECI_ERR_NO_SYSTEMS".
                 88 ECI-ERR-SECURITY-ERROR-T
                 VALUE "ECI_ERR_SECURITY_ERROR".
                 88 ECI-ERR-MAX-SYSTEMS-T
                 VALUE "ECI_ERR_MAX_SYSTEMS".
                 88 ECI-ERR-MAX-SESSIONS-T
                 VALUE "ECI_ERR_MAX_SESSIONS".
                 88 ECI-ERR-ROLLEDBACK-T
                 VALUE "ECI_ERR_ROLLEDBACK".
                 88 ECI-ERR-OTHER-T
                 VALUE "!!!Unknown!!!".

      ***************************************************************
      * Copybooks                                                   *
      ***************************************************************
       COPY CICS_ECI.

      ***************************************************************
      * Procedure Division                                          *
      ***************************************************************
       PROCEDURE DIVISION.

      ***************************************************************
      * MAIN - Control section                                      *
      ***************************************************************
       MAIN SECTION.

          DISPLAY 'ECI Sample Program'
          DISPLAY '------------------'

          PERFORM DO-STATE-SYNC.
          PERFORM DO-SYNC.
          PERFORM DO-ASYNC.
          PERFORM DO-GET.

          DISPLAY '----------------------------'
          DISPLAY 'ECI Sample Program Completed'

          PERFORM DO-EXIT.
          EXIT.

      ***************************************************************
      * DO-STATE-SYNC                                               *
      ***************************************************************
      * Issue a CICS_Externalcall for an ECI_STATE_SYNC             *
      ***************************************************************
       DO-STATE-SYNC SECTION.

          DISPLAY 'ECI_STATE_SYNC test'
          MOVE LOW-VALUES           TO ECI-PARMS
                                       ECI-STATUS
          SET ECI-STATE-SYNC        TO TRUE
          MOVE USERID               TO ECI-USERID
          MOVE PASSWD               TO ECI-PASSWORD
          MOVE SERVER               TO ECI-SYSTEM-NAME
          SET ECI-COMMAREA          TO ADDRESS OF ECI-STATUS
          MOVE LENGTH OF ECI-STATUS TO ECI-COMMAREA-LENGTH
          MOVE 0                    TO ECI-TIMEOUT
          SET ECI-STATE-IMMEDIATE   TO TRUE
          SET ECI-NO-ERROR          TO TRUE
          SET ECI-VERSION-1         TO TRUE

      * Make the ECI call.
          CALL '_CICS_ExternalCall'
                USING BY REFERENCE ECI-PARMS
                RETURNING ECI-ERROR-ID.

          IF ECI-NO-ERROR

      * Test the ECI connection type.
             EVALUATE TRUE
                 WHEN ECI-CONNECTED-NOWHERE
                     SET WS-ECI-CONNECTED-NOWHERE   TO TRUE
                 WHEN ECI-CONNECTED-TO-SERVER
                     SET WS-ECI-CONNECTED-TO-SERVER TO TRUE
                 WHEN ECI-CONNECTED-TO-CLIENT
                     SET WS-ECI-CONNECTED-TO-CLIENT TO TRUE
                 WHEN OTHER
                     SET WS-ECI-CONNECTED-UNKNOWN   TO TRUE
             END-EVALUATE
             DISPLAY WS-CONNECTION-TEXT

      * Test the ECI server status.
             EVALUATE TRUE
                 WHEN ECI-SERVERSTATE-UP
                      SET WS-ECI-SERVERSTATE-UP      TO TRUE
                 WHEN ECI-SERVERSTATE-DOWN
                      SET WS-ECI-SERVERSTATE-DOWN    TO TRUE
                 WHEN OTHER
                      SET WS-ECI-SERVERSTATE-UNKNOWN TO TRUE
             END-EVALUATE
             DISPLAY WS-SERVERSTATUS-TEXT

      * Test the ECI requester status.
             EVALUATE TRUE
                 WHEN ECI-CLIENTSTATE-UNKNOWN
                      SET WS-ECI-CLIENTSTATE-UNKNOWN TO TRUE
                 WHEN ECI-CLIENTSTATE-UP
                      SET WS-ECI-CLIENTSTATE-UP      TO TRUE
                 WHEN ECI-CLIENTSTATE-INAPPLICABLE
                      SET WS-ECI-CLIENTSTATE-INAPPL  TO TRUE
                 WHEN OTHER
                      SET WS-ECI-CLIENTSTATE-UNKNOWN TO TRUE
             END-EVALUATE
             DISPLAY WS-CLIENTSTATUS-TEXT
          END-IF

          MOVE 'ECI_STATE_SYNC:' TO NAME-TEXT
          PERFORM RESPONSE

          EXIT.

      ***************************************************************
      * DO-SYNC                                                     *
      ***************************************************************
      * Issue a CICS_Externalcall for an ECI_SYNC                   *
      ***************************************************************
       DO-SYNC SECTION.

          MOVE LOW-VALUES TO ECI-PARMS

          SET  ECI-SYNC            TO TRUE
          MOVE 'ECHO'              TO ECI-PROGRAM-NAME
          MOVE USERID              TO ECI-USERID
          MOVE PASSWD              TO ECI-PASSWORD
          MOVE SERVER              TO ECI-SYSTEM-NAME
          SET  ECI-COMMAREA        TO ADDRESS OF COMMAREA
          MOVE LENGTH OF COMMAREA  TO ECI-COMMAREA-LENGTH
          MOVE 0                   TO ECI-TIMEOUT
          SET  ECI-NO-EXTEND       TO TRUE
          SET  ECI-VERSION-1       TO TRUE


          DISPLAY 'ECI_SYNC test for' server
          CALL '_CICS_ExternalCall'
                USING BY REFERENCE ECI-PARMS
                RETURNING ECI-ERROR-ID.

          IF ECI-NO-ERROR
              DISPLAY 'CommArea returned:' COMMAREA
          END-IF

          MOVE 'ECI_SYNC:' TO NAME-TEXT
          PERFORM RESPONSE

          EXIT.

      ***************************************************************
      * DO-ASYNC                                                    *
      ***************************************************************
      * Issue a CICS_Externalcall for an ECI_ASYNC                  *
      ***************************************************************
       DO-ASYNC SECTION.

          MOVE LOW-VALUES TO ECI-PARMS

          SET  ECI-ASYNC           TO TRUE
          MOVE 'ECHO'              TO ECI-PROGRAM-NAME
          MOVE USERID              TO ECI-USERID
          MOVE PASSWD              TO ECI-PASSWORD
          MOVE SERVER              TO ECI-SYSTEM-NAME
          SET  ECI-COMMAREA        TO ADDRESS OF COMMAREA
          MOVE LENGTH OF COMMAREA  TO ECI-COMMAREA-LENGTH
          MOVE 0                   TO ECI-TIMEOUT
          SET  ECI-NO-EXTEND       TO TRUE
          SET  ECI-VERSION-1       TO TRUE


          DISPLAY 'ECI_ASYNC test'
          CALL '_CICS_ExternalCall'
                USING BY REFERENCE ECI-PARMS
                RETURNING ECI-ERROR-ID.

          MOVE 'ECI_ASYNC:' TO NAME-TEXT
          PERFORM RESPONSE

          EXIT.

      ***************************************************************
      * DO-GET                                                      *
      ***************************************************************
      * Issue a CICS_Externalcall for an ECI_GET_REPLY_WAIT         *
      ***************************************************************
       DO-GET SECTION.

          MOVE LOW-VALUES TO ECI-PARMS

          SET  ECI-GET-REPLY-WAIT  TO TRUE
          MOVE USERID              TO ECI-USERID
          MOVE PASSWD              TO ECI-PASSWORD
          MOVE SERVER              TO ECI-SYSTEM-NAME
          SET  ECI-COMMAREA        TO ADDRESS OF COMMAREA
          MOVE LENGTH OF COMMAREA  TO ECI-COMMAREA-LENGTH
          MOVE 0                   TO ECI-TIMEOUT
          SET  ECI-NO-EXTEND       TO TRUE
          SET  ECI-VERSION-1       TO TRUE


          DISPLAY 'ECI_GET_REPLY_WAIT called'
          CALL '_CICS_ExternalCall'
                USING BY REFERENCE ECI-PARMS
                RETURNING ECI-ERROR-ID.

          IF ECI-NO-ERROR
              DISPLAY 'CommArea returned:' COMMAREA
          END-IF

          MOVE 'ECI_GET_REPLY_WAIT:' TO NAME-TEXT
          PERFORM RESPONSE

          EXIT.

      ***************************************************************
      * RESPONSE                                                    *
      ***************************************************************
      * Display the immediate response code from an ECI call        *
      ***************************************************************
       RESPONSE SECTION.
          EVALUATE TRUE
              WHEN ECI-NO-ERROR
                  SET ECI-NO-ERROR-T                 TO TRUE
              WHEN ECI-ERR-INVALID-DATA-LENGTH
                  SET ECI-ERR-INVALID-DATA-LENGTH-T  TO TRUE
              WHEN ECI-ERR-INVALID-EXTEND-MODE
                  SET ECI-ERR-INVALID-EXTEND-MODE-T  TO TRUE
              WHEN ECI-ERR-NO-CICS
                  SET ECI-ERR-NO-CICS-T              TO TRUE
              WHEN ECI-ERR-CICS-DIED
                  SET ECI-ERR-CICS-DIED-T            TO TRUE
              WHEN ECI-ERR-REQUEST-TIMEOUT
                  SET ECI-ERR-REQUEST-TIMEOUT-T      TO TRUE
              WHEN ECI-ERR-RESPONSE-TIMEOUT
                  SET ECI-ERR-RESPONSE-TIMEOUT-T     TO TRUE
              WHEN ECI-ERR-TRANSACTION-ABEND
                  SET ECI-ERR-TRANSACTION-ABEND-T    TO TRUE
              WHEN ECI-ERR-EXEC-NOT-RESIDENT
                  SET ECI-ERR-EXEC-NOT-RESIDENT-T    TO TRUE
              WHEN ECI-ERR-SYSTEM-ERROR
                  SET ECI-ERR-SYSTEM-ERROR-T         TO TRUE
              WHEN ECI-ERR-NULL-WIN-HANDLE
                  SET ECI-ERR-NULL-WIN-HANDLE-T      TO TRUE
              WHEN ECI-ERR-NULL-MESSAGE-ID
                  SET ECI-ERR-NULL-MESSAGE-ID-T      TO TRUE
              WHEN ECI-ERR-THREAD-CREATE-ERROR
                  SET ECI-ERR-THREAD-CREATE-ERROR-T  TO TRUE
              WHEN ECI-ERR-INVALID-CALL-TYPE
                  SET ECI-ERR-INVALID-CALL-TYPE-T    TO TRUE
              WHEN ECI-ERR-ALREADY-ACTIVE
                  SET ECI-ERR-ALREADY-ACTIVE-T       TO TRUE
              WHEN ECI-ERR-RESOURCE-SHORTAGE
                  SET ECI-ERR-RESOURCE-SHORTAGE-T    TO TRUE
              WHEN ECI-ERR-NO-SESSIONS
                  SET ECI-ERR-NO-SESSIONS-T          TO TRUE
              WHEN ECI-ERR-NULL-SEM-HANDLE
                  SET ECI-ERR-NULL-SEM-HANDLE-T      TO TRUE
              WHEN ECI-ERR-INVALID-DATA-AREA
                  SET ECI-ERR-INVALID-DATA-AREA-T    TO TRUE
              WHEN ECI-ERR-INVALID-VERSION
                  SET ECI-ERR-INVALID-VERSION-T      TO TRUE
              WHEN ECI-ERR-UNKNOWN-SERVER
                  SET ECI-ERR-UNKNOWN-SERVER-T       TO TRUE
              WHEN ECI-ERR-CALL-FROM-CALLBACK
                  SET ECI-ERR-CALL-FROM-CALLBACK-T   TO TRUE
              WHEN ECI-ERR-INVALID-TRANSID
                  SET ECI-ERR-INVALID-TRANSID-T      TO TRUE
              WHEN ECI-ERR-MORE-SYSTEMS
                  SET ECI-ERR-MORE-SYSTEMS-T         TO TRUE
              WHEN ECI-ERR-NO-SYSTEMS
                  SET ECI-ERR-NO-SYSTEMS-T           TO TRUE
              WHEN ECI-ERR-SECURITY-ERROR
                  SET ECI-ERR-SECURITY-ERROR-T       TO TRUE
              WHEN ECI-ERR-MAX-SYSTEMS
                  SET ECI-ERR-MAX-SYSTEMS-T          TO TRUE
              WHEN ECI-ERR-MAX-SESSIONS
                  SET ECI-ERR-MAX-SESSIONS-T         TO TRUE
              WHEN ECI-ERR-ROLLEDBACK
                  SET ECI-ERR-ROLLEDBACK-T           TO TRUE
              WHEN OTHER
                  SET ECI-ERR-OTHER-T                TO TRUE
          END-EVALUATE
          DISPLAY RESPONSE-TEXT

          IF NOT ECI-NO-ERROR
             DISPLAY 'Abend code was:' ECI-ABEND-CODE
          END-IF

          IF ECI-ERR-SYSTEM-ERROR
             PERFORM DO-ERROR
          END-IF

          EXIT.

      ***************************************************************
      * Display the System Error response from an ECI Call          *
      ***************************************************************
       DO-ERROR SECTION.

          DISPLAY  'System Error:'
          DISPLAY  'Return Code:' ECI-SYS-RETURN-CODE

          PERFORM DO-EXIT

          EXIT.

      ***************************************************************
      * DO-EXIT                                                     *
      ***************************************************************
      * End program                                                 *
      ***************************************************************
       DO-EXIT SECTION.

          STOP RUN.

          EXIT.
