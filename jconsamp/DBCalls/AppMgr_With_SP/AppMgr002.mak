all : AMFuncs.dll AppMgr002.exe 

AMFuncs.dll : AMFuncs.obj Util.obj AMFuncs.def
	link -debug:full -debugtype:cv -dll AMFuncs.obj Util.obj db2api.lib -out:AMFuncs.dll  -def:AMFuncs.def
	copy AMFuncs.dll d:\nix\dll
	del AMFuncs.dll
	 
AMFuncs.obj : AMFuncs.sqc AppsGuid.h
	db2 prep AMFuncs.sqc package
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  AMFuncs.c

AppMgr002.exe : AppMgr002.obj DBCalls.obj Util.obj
	link -debug:full -debugtype:cv AppMgr002.obj DBCalls.obj Util.obj db2apie.lib db2api.lib

AppMgr002.obj : AppMgr002.c AppsGuid.h
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  AppMgr002.c

DBCalls.obj : DBCalls.sqc AppsGuid.h
	db2 prep dbcalls.sqc package
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  DBCalls.c

Util.obj : Util.c
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  Util.c