      $set callfh"xfh2btr"
       identification division.
       program-id. writfile.
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
      *   This program accesses ONLY BTrieve files
      *
       environment division.
       input-output section.
       file-control.
           select techbase assign to
               "E:\CICS200\USERBIN\DATA\TECHBASE.BTR"
               organization is indexed
               record key is fd-number
               access mode is dynamic
               file status is ws-file-status.

       file section.
       fd techbase
          block contains 0 records.
       01 fd-techbase-record.
           05 fd-number        pic 9(5).
           05 fd-remainder     pic x(72).

       working-storage section.
       77 ws-file-status       pic 9(2)    value 99.
       01 ws-end-of-file       pic 9       value 0.

       procedure division.
       main-line.
           display "About to Open the File".
           open i-o techbase.
           display "file status = ", ws-file-status.
           display "Record read -".
           read techbase next record at end move 1 to ws-end-of-file.
           display "read file status = ", ws-file-status.
           display "record read, eof = ", ws-end-of-file.
           move 5557  to fd-number.
           move all "9"  to fd-remainder.
           write fd-techbase-record.
           display "1st write file status = ", ws-file-status.
           write fd-techbase-record.
           display "2nd write file status = ", ws-file-status.
           close techbase.
           display "file status = ", ws-file-status.
           stop run.
       end program writfile.
