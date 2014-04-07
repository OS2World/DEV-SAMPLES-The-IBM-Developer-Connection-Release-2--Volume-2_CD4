/***********************************************************************/
/*                                                                     */
/* MODULE NAME        CREDACC1.CMD                                     */
/*                                                                     */
/* DESCRIPTIVE NAME   Sample command file to build CREDACC1.CBL        */
/*                                                                     */
/* Statement:         Licensed Materials - Property of IBM             */
/*                                                                     */
/*                    CA81 SupportPac                                  */
/*                    (c) Copyright IBM Corp. 1996.                    */
/*                                                                     */
/*                    All rights reserved.                             */
/*                                                                     */
/*                    U.S. Government Users Restricted Rights - use,   */
/*                    duplication or disclosure restricted by GSA      */
/*                    ADP Schedule Contract with IBM Corp.             */
/*                                                                     */
/*  Status:           Version 1 Release 1                              */
/*                                                                     */
/*  NOTES :-                                                           */
/*    DEPENDENCIES = None                                              */
/*                   None                                              */
/*    RESTRICTIONS = None                                              */
/*    MODULE TYPE  = Program                                           */
/*    PROCESSOR    = PS/2 and PC                                       */
/*                                                                     */
/***********************************************************************/
/*                                                                     */
/*  FUNCTION                                                           */
/*       Sample command file to build sample CREDACC1.CBL              */
/*                                                                     */
/***********************************************************************/

/* compile source */
if value('CICSCOBOL',,'OS2ENVIRONMENT') = 'IBM' then
  'COB2 -c -qbinary(s370) -qapost -qnosequence -qlib -comprc_ok=4 CREDACC1.CBL'
else
  'cobol CREDACC1.CBL /LITLINK;'
if rc <> 0 then
    do
    say "Compile error rc = "rc
    return rc
    end

/* link with appropriate libraries */
if value('CICSCOBOL',,'OS2ENVIRONMENT') = 'IBM' then
  'ilink @CREDACC1.L2'
else do
  'cblnames -mCREDACC1 -oCREDACC1.CBJ CREDACC1'
  if rc <> 0 then
    do
    say "Cblnames error rc = "rc
    return rc
    end
  'link386 @CREDACC1.L'
end
if rc <> 0 then
    do
    say "Link error rc = "rc
    return rc
    end

say "CREDACC1 built successfully"

return
