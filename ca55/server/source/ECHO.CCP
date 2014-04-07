      * JGSM 1991
       IDENTIFICATION DIVISION.
       PROGRAM-ID. ECHO.
       ENVIRONMENT DIVISION.
       CONFIGURATION SECTION.
       SOURCE-COMPUTER. IBM-PC.
       OBJECT-COMPUTER. IBM-PC.
      *
       DATA DIVISION.
       WORKING-STORAGE SECTION.

       77 CA-LEN                               PIC S9(4) COMP.
       77 CA-VARIABLE-LEN                      PIC S9(4) COMP.

       01 DATA-ITEMS.
           03 OUTPUT-DATA-ITEM                 PIC X(20).
           03 OUTPUT-COUNT                     PIC X(8).
           03 FILLER                           PIC XX.
           03 INPUT-DATA-ITEM                  PIC X(10).
           03 FILLER                           PIC X(10).

       LINKAGE SECTION.
       01 DFHCOMMAREA.
          03  CA-OUT.
              05  CA-INPUT                     PIC X(10).
              05  CA-RESPONSE                  PIC X(40).
          03  CA-SPACE-FILL.
              05  FILLER                       PIC X
                          OCCURS 0 TO 32717 TIMES
                          DEPENDING ON CA-VARIABLE-LEN.
      *
       PROCEDURE DIVISION.

       MAINLINE SECTION.

           MOVE SPACES TO DATA-ITEMS.
           COMPUTE CA-LEN = EIBCALEN - 50.
           IF CA-LEN > 0
               MOVE CA-LEN TO CA-VARIABLE-LEN
           ELSE
               MOVE ZERO TO CA-VARIABLE-LEN.
           IF EIBCALEN > 10,
               MOVE CA-INPUT TO INPUT-DATA-ITEM.
           MOVE 'Transaction Count:' TO OUTPUT-DATA-ITEM
           MOVE EIBTASKN TO OUTPUT-COUNT
           IF EIBCALEN >= 50,
               MOVE  DATA-ITEMS TO CA-OUT.
           MOVE SPACES TO CA-SPACE-FILL.

           EXEC CICS RETURN
                        END-EXEC.
       MAINLINE-EXIT.
           EXIT.
