ANCWC001.exe: ANCWC001.obj ANCWC001.res ANCWC001.def
   link /nod /co ANCWC001,ANCWC001,ANCWC001,slibcew libw cclwin,ANCWC001.def
   rcw ANCWC001.res

ANCWC001.res: ANCWC001.rc ANCWC001.h
   rcw -r ANCWC001.rc

ANCWC001.obj: ANCWC001.c ANCWC001.h
   cl /c /AS /Gsw /Zp /Zi /Od ANCWC001.c
