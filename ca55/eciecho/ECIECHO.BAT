@REM *******************************************************************
@REM *                                                                 *
@REM * MODULE NAME        ECIECHO.BAT                                  *
@REM *                                                                 *
@REM * DESCRIPTIVE NAME   CICS External Call Interface                 *
@REM *                    WINDOWS Sample - Build file                  *
@REM *                    CA55 SupportPac                              *
@REM *                                                                 *
@REM * Statement:         (C) Copyright IBM Corporation 1995           *
@REM *                                                                 *
@REM * Status:            Version 1 Release 0                          *
@REM *                                                                 *
@REM * Components:        ECIECHO.CBL                                  *
@REM *                    ECIECHO.BAT                                  *
@REM *                    CCLCOBOL.DEF                                 *
@REM *                                                                 *
@REM * NOTES :-                                                        *
@REM *                                                                 *
@REM * ECIECHO is an example application which demonstrates how to     *
@REM * use the External Call Interface (ECI).                          *
@REM *                                                                 *
@REM * It does not demonstrate all the techniques required for a       *
@REM * large application.  This sample is an example of the form of    *
@REM * an ECI application; it is not a template and should not be used *
@REM * as the foundation for your next mission critical application.   *
@REM *                                                                 *
@REM * This sample requires the transaction ECHO to be installed on    *
@REM * your server. The source ECHO.CCP can be found in the            *
@REM * subdirectory ..\SERVER                                          *
@REM *                                                                 *
@REM * This CMD file is designed for use with Micro Focus COBOL        *
@REM * Compiler Version 3.0.54                                         *
@REM *                                                                 *
@REM *******************************************************************

cobol eciecho,,,,
link  eciecho+adis+adiskey+adisinit+cblwina,,,lcoboldw+lcobol+cobw+cclwin,cclcobol.def/noe/nod;
