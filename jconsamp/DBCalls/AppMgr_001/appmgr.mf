AppMgr.exe : AppMgr.obj DBCalls.obj Util.obj
	cl AppMgr.obj DBCalls.obj Util.obj db2api.lib

AppMgr.obj : AppMgr.c AppsGuid.h
	cl -c AppMgr.c

DBCalls.obj : DBCalls.sqc AppsGuid.h
	db2 prep dbcalls.sqc package
	cl -c DBCalls.c

Util.obj : Util.c
	cl -c Util.c