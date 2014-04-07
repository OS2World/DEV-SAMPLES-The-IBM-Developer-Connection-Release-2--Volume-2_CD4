all : AMFuncs.dll AppsGuid.exe 

AMFuncs.dll : AMFuncs.obj Util.obj AMFuncs.def
	link -debug:full -debugtype:cv -dll AMFuncs.obj Util.obj db2api.lib -out:AMFuncs.dll  -def:AMFuncs.def
	copy AMFuncs.dll d:\sqllib\function
		 
AMFuncs.obj : AMFuncs.sqc AppsGuid.h
	db2 prep AMFuncs.sqc package
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  AMFuncs.c

AppsGuid.exe : AppsGuid.obj DBCalls.obj Util.obj
	link -debug:full -debugtype:cv AppsGuid.obj DBCalls.obj Util.obj db2apie.lib db2api.lib
	copy AppsGuid.exe d:\www\cgi-bin
	
AppsGuid.obj : AppsGuid.c AppsGuid.h
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  AppsGuid.c

DBCalls.obj : DBCalls.sqc AppsGuid.h
	db2 prep dbcalls.sqc package
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  DBCalls.c

Util.obj : Util.c
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  Util.c