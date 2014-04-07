AppMgr001.exe : AppMgr001.obj DBCalls.obj Util.obj
	cl AppMgr001.obj DBCalls.obj Util.obj db2api.lib 

AppMgr001.obj : AppMgr001.c AppsGuid.h
	cl -c AppMgr001.c

DBCalls.obj : DBCalls.sqc AppsGuid.h
	db2 prep dbcalls.sqc package
	cl -c DBCalls.c

Util.obj : Util.c
	cl -c Util.c