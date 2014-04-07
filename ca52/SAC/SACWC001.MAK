SACWC001.exe: SACWC001.obj SACWC001.res SACWC001.def
   link /nod /co SACWC001,SACWC001,SACWC001,slibcew libw cclwin,SACWC001.def
   rcw SACWC001.res

SACWC001.res: SACWC001.rc SACWC001.h
   rcw -r SACWC001.rc

SACWC001.obj: SACWC001.c SACWC001.h
   cl /c /AS /Gsw /Zp /Zi /Od SACWC001.c
