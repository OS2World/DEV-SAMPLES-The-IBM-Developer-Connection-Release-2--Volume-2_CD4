      $set callfh"xfh2btr"
       identification division.
       program-id. readfile.
      *
      * Statement        Licensed Materials - Property of IBM 
      *                                                       
      *                  CA33 SupportPac                      
      *                  (c) Copyright IBM Corp. 1994.  
      *                                                       
      *                  See Copyright Instructions.          
      *                                                       
      *                  All rights reserved.                 
      *                                                       
      *                  U.S. Government Users Restricted Rights - use,
      *                  duplication or disclosure restricted by GSA   
      *                  ADP Schedule Contract with IBM Corp.          
      *                                                                
      * Status           Version 1 Release 0                           
      *
      * Description
      *
      *  This program accesses ONLY BTrieve files
      *
       environment division.
       input-output section.
       file-control.
           select techbase assign to
               "E:\CICS200\USERBIN\DATA\TECHBASE.BTR"
               organization is indexed
               record key is fd-number
               access mode is dynamic.

       file section.
       fd techbase.
       01 fd-techbase-record.
           05 fd-number        pic 9(5).
           05 fd-remainder     pic x(72).

       working-storage section.
       01 ws-end-of-file       pic 9       value 0.
       01 ws-rec-read          pic 9(003)  value 0.

       procedure division.
       main-line.
           display "About to Open the File".
           open input techbase.
           perform read-the-file.
           perform until ws-end-of-file = 1
               add   +1     to  ws-rec-read
               perform read-the-file
           end-perform.
           perform display-output.
           close techbase.
           stop run.

       read-the-file.
           display "Record read - ".
           move low-values to fd-techbase-record.
           read techbase next record at end move 1 to ws-end-of-file.
               display fd-techbase-record upon console
           display "Record found, eof = ", ws-end-of-file.

       display-output.
           display "records read = ", ws-rec-read.

       end program readfile.
