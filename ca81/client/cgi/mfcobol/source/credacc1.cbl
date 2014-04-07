       identification division.
       program-id. credacc1.
       environment division.
       data division.
       working-storage section.
      ************************************************************
      *   Work Areas
      ************************************************************
       77 ws-crlf              pic x(002) value x'0D0A'.
       77 ws-cgi-input         pic x(300) value spaces.
       77 ws-ccnum-in          pic x(005) justified right.
       01 ws-cgi-input-len     pic x(002).
      ************************************************************
      *   Interface areas with CICS system and applications
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
             '<title>Credit Account information thru Internet</title>'.
          03 ht4               pic x(6)  value
             '<body>'.
          03 ht999             pic x(20)  value
             '</body></html>'.
      *****************************************************************
      *   PROCEDURE DIVISION
      *************************************************************
       PROCEDURE DIVISION.
           accept  ws-cgi-input from SYSIN.
           display ht1 ws-crlf
           display ht2 ws-crlf
           display ht3 ws-crlf
           display ht4 ws-crlf
           display 'CONTENT_LENGTH' upon ENVIRONMENT-NAME.
           accept  ws-cgi-input-len from ENVIRONMENT-VALUE.
           if  ws-cgi-input-len = "6"
               display '<h1>Error. Credit account Number not informed.'
               display '</h1>' ws-crlf
               display '<A href="/credacct.html">' ws-crlf
               display 'Click here</a> ' ws-crlf
               display 'to return to the previous page.' ws-crlf
               go to   fim-prog.
      *
           move low-values to ws-commarea.
           move '2'        to ca-req.
           move '1'        to ca-rc.
           move ws-cgi-input(7:5) to ca-conta.
      *
           MOVE  LOW-VALUES     TO  ECI-PARMS.
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
           CALL  '_CICS_EXTERNALCALL' USING ECI-PARMS
                                          ECI-ERROR-ID.
      *
           IF  NOT ECI-NO-ERROR
               display '<h1>Error. Couldn''t contact CICS!</h1>' ws-crlf
               display 'Return-code: ' RETURN-CODE '<br>' ws-crlf
               display 'Abend-code : ' ECI-ABEND-CODE '<p>' ws-crlf
               display '<A href="/credacct.html">' ws-crlf
               display 'Click here</a> ' ws-crlf
               display 'to return to the previous page.' ws-crlf
               go  to  fim-prog.
      *
            IF  CA-RC  NOT = '0'
               display '<h1>Error. Account number not found!'
               display '</h1>' ws-crlf
               display 'Account number: ' ca-conta '<p>' ws-crlf
               display '<A href="/credacct.html">' ws-crlf
               display 'Click here</a> ' ws-crlf
               display 'to return to the previous page' ws-crlf
               go  to  fim-prog.
      *
           display '<h1>Details for account number ' ws-crlf
           display ca-conta '</h1>' ws-crlf.
           display '<form action=' ws-crlf
           display '"/cgi-bin/credacc2.exe" ' ws-crlf
           display 'method="POST">' ws-crlf
           display '<input type="hidden" name="CCNUM" ' ws-crlf
           display 'value="' ca-conta '"><p>' ws-crlf
           display 'Name: <b>' ca-nome ' ' ca-ultnome '</b><p>' ws-crlf
           display 'Balance: <b>' ca-saldo  '</b><p><p>' ws-crlf
           display 'Address: <p>'
           display '<input name="RUA" value="' ws-crlf
           display ca-rua '" size=15 maxlength=15> <p>' ws-crlf
           display '<input name="CS" value="' ws-crlf
           display ca-cs '" size=15 maxlength=15> <p>' ws-crlf
           display '<input name="CEP" value="' ws-crlf
           display ca-cep '" size=15 maxlength=15> <p>' ws-crlf
           display 'To change your address, type it over ' ws-crlf
           display 'the data shown, and hit ' ws-crlf
           display '[Update Address].<p>' ws-crlf
           display 'If you don''t want to change it, ' ws-crlf
           display 'just return to the ' ws-crlf
           display '<a href="/credacct.html">' ws-crlf
           display 'previous page</a><p>' ws-crlf
           display '<input type="submit" ' ws-crlf
           display 'value="Update Address">' ws-crlf
           display '<input type="reset"  value="Clear Entry">' ws-crlf.
           display '</form>' ws-crlf.
       fim-prog.
           display ht999 ws-crlf.
           stop run.

       end program credacc1.
