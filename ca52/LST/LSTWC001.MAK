LSTWC001.exe: LSTWC001.obj LSTWC001.res LSTWC001.def
   link /nod LSTWC001,LSTWC001,LSTWC001,slibcew libw cclwin,LSTWC001.def
   rcw LSTWC001.res

LSTWC001.res: LSTWC001.rc LSTWC001.h
   rcw -r LSTWC001.rc

LSTWC001.obj: LSTWC001.c LSTWC001.h
   cl /c LSTWC001.c
