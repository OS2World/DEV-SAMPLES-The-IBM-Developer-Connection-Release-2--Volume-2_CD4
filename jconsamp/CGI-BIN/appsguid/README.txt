To run:

	On Server:
	--------------
	- "net start db2"
	- "db2 connect to appsguid"
	- build and execute code in /dbcalls/appmgr_001 or appmgr_with_sp to load db with data
	- change to cgi-bin/appsguide and "nmake appsguid.mak /a"
	- "puthtml testAG"

	On Browser
	----------------
	http://dbserver/testag
