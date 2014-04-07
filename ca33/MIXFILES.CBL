      $set callfh
       identification division.
       program-id. mixfiles.
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
      * This program accesses BOTH sequential AND BTrieve files
      *   - filetype indicates wich file handler is to be used
      *     for each individual file.
      *     - filetype "0" means "System specific default"
      *       (in this examples, a sequential file)
      *     - filetype "5" and "6"  means BTrieve 
      *     for more details about filetypes, please refer to 
      *     MicroFocus COBOL System Reference, File Handling    
      *     chapter, under FILETYPE Compiler Directive. 
      *
       environment division.
       input-output section.
       file-control.
      $set filetype"5"
           select techbase assign to 
               "E:\CICS200\USERBIN\DATA\TECHBASE.BTR"
               organization is indexed
               record key is fd-number
               access mode is dynamic
               file status is ws-file-status.
      $set filetype"0"
           select seqfile assign to 
               "E:\CICS200\USERBIN\DATA\SEQFILE.TXT"
           organization is line sequential.

       file section.
       fd techbase
          block contains 0 records.
       01 fd-techbase-record.
           05 fd-number        pic 9(5).
           05 fd-remainder     pic x(72).
       fd seqfile.
       01 fd-seqfile-record    pic x(77).

       working-storage section.
       77 ws-file-status       pic 9(002)  value 99.
       01 ws-rec-read          pic 9(003)  value 0.

       procedure division.
       main-line.
           display "ABOUT TO OPEN FILES".
           open input seqfile
                i-o   techbase.
           display "TECHBASE OPEN file status = ", ws-file-status. 
       loop-reading.
           read seqfile at end go to display-output.
           display "SEQFILE RECORD READ".
           display fd-seqfile-record.
           add 1 to ws-rec-read.
           write fd-techbase-record from fd-seqfile-record.
           display "TECHBASE WRITE file status = ", ws-file-status. 
           go to loop-reading.
       display-output.
           close seqfile techbase.
           display "TECHBASE CLOSE file status = ", ws-file-status. 
           display "records read = ", ws-rec-read.
           stop run.
       end program mixfiles.
