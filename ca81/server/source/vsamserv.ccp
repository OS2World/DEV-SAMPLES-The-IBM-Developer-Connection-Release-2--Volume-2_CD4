      *****************************************************************
      *
      * AUTHOR\DATE\AREA JGSM, 11-04-1992, CICS OS/2 DEVELOPMENT.
      * REVISED          JGSM  29-04-1993, CICS OS/2 DEVELOPMENT.
      *
      * For CICS v3
      *
      * VSAM File Layout
      *
      * KSDS base file = TECHBASE  Key - 00 pos=0 len=5
      * Alt Index      = TECHALT   Key - 01 pos=5 len=15
      *
      * TECHBASE - record layout
      *
      * Field-name / Field-length
      *
      * Account-NO    5 - CHAR
      * Surname      15 - CHAR
      * First-Name   10 - CHAR
      * Address      45 - CHAR
      * Balance       4 - COMP
      *
      *
      *****************************************************************
       IDENTIFICATION DIVISION.
       PROGRAM-ID. VSAMSERV.
       ENVIRONMENT DIVISION.
       CONFIGURATION SECTION.
       SOURCE-COMPUTER. IBM-PC.
       OBJECT-COMPUTER. IBM-PC.
      *****************************************************************
       DATA DIVISION.

       WORKING-STORAGE SECTION.


       01 SERVER-IO-BUFFER.

        03 SERVER-STATE                PIC 9.

        03 RETURN-VALUE                PIC 9.

        03 SERVER-KEY-SIZE             PIC S9(4) COMP.


        03 SERVER-CUSTOMER-RECORD.
         05 ACCOUNTO                   PIC X(5).
         05 SURNAMEO                   PIC X(15).
         05 FIRST-NAMEO                PIC X(10).
         05 ADDRESSO                   PIC X(45).
         05 BALANCEO                   PIC S9(4) COMP.

        03 RESPONSES                   PIC X(50).


      *************************

       01 MISCEL-VARS.
        03 CUST-REC-LEN                PIC S9(4) VALUE 77.
        03 LENGTH-VAR                  PIC S9(4) VALUE 0.

        03 KEY-SIZE                    PIC S9(4) COMP.
        03 ACCOUNT-NUM                 PIC  9(5) VALUE 0.

        03 CHAR-BUFFER                 PIC X(15).
        03 SPLIT-BUFFER REDEFINES CHAR-BUFFER.
         05 BUFFER-ELEMENT OCCURS 15 TIMES  PIC X.
        03 ELEMENT-PTR                 PIC 99.
        03 BUFFER-LEN                  PIC 99.
        03 CHAR-FOUND                  PIC 9 VALUE 0.
      *

        03 BACKUP-CUSTOMER-RECORD.
         05 ACCOUNT-BAK                PIC X(5).
         05 SURNAME-BAK                PIC X(15).
         05 FIRST-NAME-BAK             PIC X(10).
         05 ADDRESS-BAK                PIC X(45).
         05 BALANCE-BAK                PIC S9(4) COMP.

        03 RESPONSES-BAK               PIC X(50).

        03 LEN-PARM                    PIC S9(4) COMP.

        03 VALID-STATES.

         05 SERVER-STATE-CREATE        PIC 9 VALUE 1.
         05 SERVER-STATE-READ          PIC 9 VALUE 2.
         05 SERVER-STATE-UPDATE        PIC 9 VALUE 3.
         05 SERVER-STATE-DELETE        PIC 9 VALUE 4.

       COPY DFHAID.

       LINKAGE SECTION.
       01 DFHCOMMAREA                  PIC X(131).
      *****************************************************************
       PROCEDURE DIVISION.

       MAINLINE SECTION.

            MOVE DFHCOMMAREA TO SERVER-IO-BUFFER

      * Convert trailing LOW-VALUES to SPACES in the customer record

           INSPECT FIRST-NAMEO REPLACING ALL LOW-VALUES BY SPACES
           INSPECT ADDRESSO REPLACING ALL LOW-VALUES BY SPACES
           INSPECT SURNAMEO REPLACING ALL LOW-VALUES BY SPACES

            MOVE SERVER-KEY-SIZE TO KEY-SIZE


           EVALUATE SERVER-STATE

             WHEN SERVER-STATE-CREATE

                  PERFORM CREATE-RECORD


             WHEN SERVER-STATE-READ

                  PERFORM READ-RECORD


             WHEN SERVER-STATE-UPDATE

                  PERFORM UPDATE-RECORD


             WHEN SERVER-STATE-DELETE

                  PERFORM DELETE-RECORD

           END-EVALUATE


            MOVE SERVER-IO-BUFFER TO DFHCOMMAREA

         EXEC CICS RETURN
                   END-EXEC

           .
       MAINLINE-EXIT.
           EXIT.
      *****************************************************************
       CREATE-RECORD SECTION.

      * check for duplicate record in file


      * calculate key size (temp support for detecting dup records

            MOVE LOW-VALUES TO CHAR-BUFFER
            MOVE SURNAMEO TO CHAR-BUFFER
            MOVE 15 TO ELEMENT-PTR
            PERFORM STRING-LENGTH
            MOVE BUFFER-LEN TO KEY-SIZE


            MOVE CUST-REC-LEN TO LENGTH-VAR
            MOVE LOW-VALUES TO BACKUP-CUSTOMER-RECORD


           EXEC CICS READ
                  FILE("TECHALT")
                  INTO(BACKUP-CUSTOMER-RECORD)
                  LENGTH(LENGTH-VAR)
                  RIDFLD(SURNAMEO)
                  KEYLENGTH(KEY-SIZE)
                  GENERIC
                  NOHANDLE
                  END-EXEC

           IF SURNAMEO = SURNAME-BAK

            MOVE 14 TO EIBRESP

            PERFORM EVALUATE-RESP

            GO TO CREATE-RECORD-EXIT

           END-IF


      * get max record number

           MOVE SERVER-CUSTOMER-RECORD TO BACKUP-CUSTOMER-RECORD

           MOVE HIGH-VALUES TO ACCOUNTO

           EXEC CICS STARTBR
                   FILE("TECHBASE")
                   RIDFLD(ACCOUNTO)
                   GTEQ
                   NOHANDLE
                   END-EXEC

           EXEC CICS READPREV
                   FILE("TECHBASE")
                   INTO(SERVER-CUSTOMER-RECORD)
                   LENGTH(LENGTH-VAR)
                   RIDFLD(ACCOUNTO)
                   NOHANDLE
                   END-EXEC

           EXEC CICS ENDBR
                   FILE("TECHBASE")
                   NOHANDLE
                   END-EXEC


      * add 1 and enter new record

           MOVE ACCOUNTO TO ACCOUNT-NUM

           ADD 1 TO ACCOUNT-NUM

           MOVE BACKUP-CUSTOMER-RECORD TO SERVER-CUSTOMER-RECORD

           MOVE ACCOUNT-NUM TO ACCOUNTO



           MOVE 5 TO KEY-SIZE

           MOVE CUST-REC-LEN TO LENGTH-VAR


           EXEC CICS WRITE
                   FILE("TECHBASE")
                   FROM(SERVER-CUSTOMER-RECORD)
                   LENGTH(LENGTH-VAR)
                   RIDFLD(ACCOUNTO)
                   KEYLENGTH(KEY-SIZE)
                   NOHANDLE
                   END-EXEC

           PERFORM EVALUATE-RESP


           .
         CREATE-RECORD-EXIT.
           EXIT.
      *****************************************************************
         READ-RECORD SECTION.

           MOVE CUST-REC-LEN TO LENGTH-VAR

           IF ACCOUNTO = LOW-VALUES

           EXEC CICS READ
                     FILE("TECHALT")
                     INTO(SERVER-CUSTOMER-RECORD)
                     LENGTH(LENGTH-VAR)
                     RIDFLD(SURNAMEO)
                     KEYLENGTH(KEY-SIZE)
                     GENERIC
                     NOHANDLE
                     END-EXEC

           ELSE

             EXEC CICS READ
                     FILE("TECHBASE")
                     INTO(SERVER-CUSTOMER-RECORD)
                     LENGTH(LENGTH-VAR)
                     RIDFLD(ACCOUNTO)
                     NOHANDLE
                     END-EXEC

           END-IF

           PERFORM EVALUATE-RESP

           .
         READ-RECORD-EXIT.
           EXIT.
      *****************************************************************
         UPDATE-RECORD SECTION.

           MOVE SERVER-CUSTOMER-RECORD TO BACKUP-CUSTOMER-RECORD

           MOVE CUST-REC-LEN TO LENGTH-VAR

           EXEC CICS READ
                   FILE("TECHBASE")
                   INTO(SERVER-CUSTOMER-RECORD)
                   LENGTH(LENGTH-VAR)
                   RIDFLD(ACCOUNTO)
                   UPDATE
                   NOHANDLE
                   END-EXEC

           IF EIBRESP = 0


            MOVE BACKUP-CUSTOMER-RECORD TO SERVER-CUSTOMER-RECORD

            MOVE CUST-REC-LEN TO LENGTH-VAR

            EXEC CICS REWRITE
                     FILE("TECHBASE")
                     FROM(SERVER-CUSTOMER-RECORD)
                     LENGTH(LENGTH-VAR)
                     NOHANDLE
                     END-EXEC
           ELSE

           PERFORM EVALUATE-RESP

           END-IF



           PERFORM EVALUATE-RESP


           .
         UPDATE-RECORD-EXIT.
           EXIT.
      *****************************************************************
         DELETE-RECORD SECTION.


            MOVE CUST-REC-LEN TO LENGTH-VAR

            EXEC CICS READ
                   FILE("TECHBASE")
                   INTO(SERVER-CUSTOMER-RECORD)
                   LENGTH(LENGTH-VAR)
                   RIDFLD(ACCOUNTO)
                   UPDATE
                   NOHANDLE
                   END-EXEC


           IF EIBRESP = 0

             EXEC CICS DELETE
                     FILE("TECHBASE")
                     NOHANDLE
                     END-EXEC
           ELSE

           PERFORM EVALUATE-RESP

           END-IF


           PERFORM EVALUATE-RESP


           .
          DELETE-RECORD-EXIT.
           EXIT.
      *****************************************************************
         EVALUATE-RESP SECTION.

           EVALUATE EIBRESP

               WHEN DFHRESP(NORMAL)

                  MOVE 0 TO RETURN-VALUE

               WHEN DFHRESP(DISABLED)

                  MOVE 'File is out of action'
                  TO RESPONSES


             WHEN 12

                  MOVE 'File cannot be found'
                  TO RESPONSES

             WHEN DFHRESP(DUPREC)

                  MOVE 'Duplicate found, record NOT created'
                  TO RESPONSES


             WHEN DFHRESP(NOTFND)

                  MOVE 'Record cannot be found'
                  TO RESPONSES


             WHEN DFHRESP(ILLOGIC)

                  MOVE 'Illogic operation Hmm!'
                  TO RESPONSES


             WHEN DFHRESP(INVREQ)

                  MOVE 'Invalid request'
                  TO RESPONSES

             WHEN DFHRESP(IOERR)

                  MOVE 'IO error disk full or setup wrong !'
                  TO RESPONSES

             WHEN DFHRESP(LENGERR)

                  MOVE 'Invalid length Hmm!'
                  TO RESPONSES

             WHEN DFHRESP(NOSPACE)

                  MOVE 'Not enough disk space ! Hmm!'
                  TO RESPONSES

           END-EVALUATE

           .
       EVALUATE-RESP-EXIT.
           EXIT.
      *****************************************************************
       STRING-LENGTH SECTION.

      * used to calculate keysize

           MOVE ELEMENT-PTR TO BUFFER-LEN

           PERFORM COUNT-LOW-VALS UNTIL ELEMENT-PTR = 1

           GO TO STRING-LENGTH-EXIT

           .
       COUNT-LOW-VALS.


           IF BUFFER-ELEMENT(ELEMENT-PTR) = LOW-VALUE OR SPACE

            SUBTRACT 1 FROM ELEMENT-PTR

           ELSE

            MOVE ELEMENT-PTR TO BUFFER-LEN
            MOVE 1 TO ELEMENT-PTR


           END-IF

           .
       STRING-LENGTH-EXIT.
           EXIT.
      *****************************************************************
