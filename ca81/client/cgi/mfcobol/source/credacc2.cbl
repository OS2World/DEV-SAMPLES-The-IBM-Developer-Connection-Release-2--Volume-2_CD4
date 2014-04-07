       identification division.
       program-id. credacc2.
       environment division.
       data division.
       working-storage section.
      ************************************************************
      *   Work areas
      ************************************************************
       77 ws-crlf              pic x(002) value x'0D0A'.
       77 ws-cgi-input         pic x(300) value spaces.
       77 ws-ind               pic 9(004) comp value 0.
       77 ws-ccnum-in          pic x(005) justified right.
       77 ws-rua               pic x(015) value spaces.
       77 ws-cs                pic x(015) value spaces.
       77 ws-cep               pic x(015) value spaces.
       01 ws-cgi-input-len     pic x(002).
       01 ws-cgi-tab.
          03 ws-cgi-item       occurs 8 times
                               pic x(040).
      ************************************************************
      *   Interface areas to CICS system and applications
      ************************************************************
       COPY F:\COS2USER\CICSECI.
       01 WS-COMMAREA.                                   
          03 CA-REQ                    PIC  X(001).      
          03 CA-RC                     PIC  X(001).      
          03 CA-KEYSIZE                PIC S9(004) COMP. 
          03 CA-VSAMRECORD.                              
             05 CA-CONTA               PIC  9(005).      
             05 CA-ULTNOME             PIC  X(015).      
             05 CA-NOME                PIC  X(010).      
             05 CA-RUA                 PIC  X(015).      
             05 CA-CS                  PIC  X(015).      
             05 CA-CEP                 PIC  X(015).      
             05 CA-SALDO               PIC S9(004). 
          03 CA-RESP                   PIC  X(050).      
      ************************************************************
      *   HTML document skeleton to be returned to the Web Server
      ************************************************************
       01 ws-html-out.
          03 ht1               pic x(30)  value
             'Content-type: text/html '.
          03 ht2               pic x(10)  value
             '<html>'.
          03 ht3               pic x(55)  value
             '<title>Credit Account Information thru Internet</title>'.
          03 ht4               pic x(6)  value
             '<body>'.
          03 ht999             pic x(20)  value
             '</body></html>'.
      *****************************************************************
      *   PROCEDURE DIVISION
      *************************************************************
       PROCEDURE DIVISION.
           display ht1 ws-crlf
           display ht2 ws-crlf
           display ht3 ws-crlf
           display ht4 ws-crlf
      *
           accept  ws-cgi-input from SYSIN.
           move spaces     to ws-cgi-tab.
           unstring ws-cgi-input 
                    delimited by '&' or '  '
                    into ws-cgi-item (1) ws-cgi-item(2)
                         ws-cgi-item (3) ws-cgi-item(4).
           inspect ws-cgi-tab replacing all '+' by ' '.
           perform varying ws-ind from 1 by 1 until ws-ind > 4
              if ws-cgi-item(ws-ind) (1:5) = 'CCNUM'
                 move ws-cgi-item(ws-ind)(7:5)  to ws-ccnum-in end-if
              if ws-cgi-item(ws-ind) (1:3) = 'RUA'
                 move ws-cgi-item(ws-ind)(5:15) to ws-rua end-if
              if ws-cgi-item(ws-ind) (1:2) = 'CS'
                 move ws-cgi-item(ws-ind)(4:15) to ws-cs end-if
              if ws-cgi-item(ws-ind) (1:3) = 'CEP'
                 move ws-cgi-item(ws-ind)(5:15) to ws-cep end-if
           end-perform.
           move low-values      to ws-commarea.
           move '2'             to ca-req.
           move '1'             to ca-rc.
           move ws-ccnum-in     to ca-conta.
      *
           MOVE  LOW-VALUES     TO  ECI-PARMS.
           SET   ECI-SYNC       TO  TRUE.
      *    MOVE  0              TO  ECI-TIMEOUT.
           MOVE  'VSAMSERV'     TO  ECI-PROGRAM-NAME.
           MOVE  'SYSAD'        TO  ECI-USERID.
           MOVE  'SYSAD'        TO  ECI-PASSWORD.
           SET ECI-COMMAREA TO ADDRESS OF WS-COMMAREA.
           MOVE LENGTH OF WS-COMMAREA TO ECI-COMMAREA-LENGTH.
           SET   ECI-NO-EXTEND  TO  TRUE
           SET   ECI-VERSION-1  TO  TRUE
      *
           CALL  '_CICS_EXTERNALCALL' USING ECI-PARMS 
                                            ECI-ERROR-ID.
      *
           IF  NOT ECI-NO-ERROR
               display '<h1>Error. Couldn''t contact CICS!' ws-crlf
               display '</h1>' ws-crlf
               display 'Return-code: ' RETURN-CODE '<br>' ws-crlf
               display 'Abend-code : ' ECI-ABEND-CODE '<p>' ws-crlf
               display '<A href="/credacct.html">' ws-crlf
               display 'Click here</a> ' ws-crlf
               display 'to return to the previous page.' ws-crlf
               go  to  fim-prog.
      *
            IF  CA-RC  NOT = '0'
               display '<h1>Error. Account Number not found!' ws-crlf
               display '</h1>' ws-crlf
               display 'Account number: ' ca-conta '<p>' ws-crlf
               display '<A href="/credacct.html">' ws-crlf
               display 'Click here</a> ' ws-crlf
               display 'to return to the previous page.' ws-crlf
               go  to  fim-prog.
      *
           move '3'        to ca-req.
           move '1'        to ca-rc.
           move ws-rua        to ca-rua.
           move ws-cs         to ca-cs.
           move ws-cep        to ca-cep.
      *
           SET   ECI-SYNC       TO  TRUE.
           MOVE  0              TO  ECI-TIMEOUT.
           MOVE  'VSAMSERV'     TO  ECI-PROGRAM-NAME.
           MOVE  'SYSAD'        TO  ECI-USERID.
           MOVE  'SYSAD'        TO  ECI-PASSWORD.
           SET ECI-COMMAREA TO ADDRESS OF WS-COMMAREA.
           MOVE LENGTH OF WS-COMMAREA TO ECI-COMMAREA-LENGTH.
           SET   ECI-NO-EXTEND  TO  TRUE.
           SET   ECI-VERSION-1  TO  TRUE.
      *
           CALL  '_CICS_EXTERNALCALL'  USING ECI-PARMS 
                                       ECI-ERROR-ID.
      *
           IF  NOT ECI-NO-ERROR
               display '<h1>Error. Couldn''t contact CICS!' ws-crlf
               display '</h1>' ws-crlf
               display 'Return-code: ' RETURN-CODE '<br>' ws-crlf
               display 'Abend-code : ' ECI-ABEND-CODE '<p>' ws-crlf
               display '<A href="/credacct.html">' ws-crlf
               display 'Click here</a> ' ws-crlf
               display 'to return to the previous page.' ws-crlf
               go  to  fim-prog.
      *
            IF  CA-RC  NOT = '0'
               display '<h1>Error. Account not updated!</h1>' ws-crlf
               display 'Account number: ' ca-conta '<p>' ws-crlf
               display 'Application RC: ' ca-rc '<p>' ws-crlf
               display '<A href="/credacct.html">' ws-crlf
               display 'Click here</a> ' ws-crlf
               display 'to return to the previous page.' ws-crlf
               go  to  fim-prog.
      *
           display '<h1>Address Update</h1>' ws-crlf
           display 'Ok. Your address was succesfully ' ws-crlf
           display 'updated.<p>' ws-crlf
           display '<a href="/credacct.html">' ws-crlf
           display 'Click here</a> to return.<p>' ws-crlf.
       fim-prog.
           display ht999 ws-crlf.
           stop run.

       end program credacc2.
