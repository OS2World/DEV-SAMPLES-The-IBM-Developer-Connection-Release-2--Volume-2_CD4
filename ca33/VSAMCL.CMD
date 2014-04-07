@echo off
@echo -----------------------------------------------------------------------
@echo    NON-CICS -  Compile and Link of %1.cbl
@echo  This command file is set up for MicroFocus COBOL
@echo -----------------------------------------------------------------------
if %1.==. goto msg
if not exist %1.cbl goto msg1
COBOL %1,%1,%1;
if errorlevel 1 goto acerr
goto alink
:acerr
@echo .
@echo    Compiler errors discovered
@echo .
goto aend
:alink
link  /NOP /MAP /PM:VIO %1+XFH2BTR+EXTFH+OBTRV,%1.EXE,%1.MAP,LCOBOL+DOSCALLS,%1.DEF;
if errorlevel 1 goto alerr
goto aend
:alerr
@echo .
@echo    Link errors discovered
@echo .
goto aend
:msg1
@echo .
@echo    %1.CBL does not exist
:msg
@echo .
@echo    This command will compile and link a NON-CICS program accessing
@echo    VSAM-like datasets (BTrieve).
@echo    The source module is expected to have an extension of CBL.
@echo .
@echo    Using this command the compiler directives should either be put
@echo    in the source file or in the file COBOL.DIR
@echo .
@echo    Create a pgmname.DEF file containing:
@echo     STACKSIZE 4096
@echo .
@echo    At execution time BTRIEVE must have already been started or
@echo     "SET BTRINTF=/H:x:\CICS200\BIN" command must be executed before
@echo     user program invokation.
@echo .

:aend

