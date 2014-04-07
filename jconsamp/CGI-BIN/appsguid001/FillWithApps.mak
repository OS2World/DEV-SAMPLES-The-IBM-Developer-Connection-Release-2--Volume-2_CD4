all : AMFuncs.dll FillWithApps.exe 

AMFuncs.dll : AMFuncs.obj Util.obj AMFuncs.def
	link -debug:full -debugtype:cv -dll AMFuncs.obj Util.obj db2api.lib -out:AMFuncs.dll  -def:AMFuncs.def
	 
AMFuncs.obj : AMFuncs.sqc AppsGuid.h
	db2 prep AMFuncs.sqc package
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  AMFuncs.c

FillWithApps.exe : FillWithApps.obj DBCalls.obj Util.obj
	link -debug:full -debugtype:cv FillWithApps.obj DBCalls.obj Util.obj db2apie.lib db2api.lib
	copy Fill*.exe d:\www\cgi-bin
	




FillWithApps.obj : FillWithApps.c AppsGuid.h
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  FillWithApps.c

DBCalls.obj : DBCalls.sqc AppsGuid.h
	db2 prep dbcalls.sqc package
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  DBCalls.c

Util.obj : Util.c
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  Util.c