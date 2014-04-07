grwwc002.exe: grwwc002.obj grwwc002.res grwwc002.def
   link /nod grwwc002,grwwc002,grwwc002,slibcew libw CCLWIN,grwwc002.def
   rcw grwwc002.res

grwwc002.res: grwwc002.rc grwwc002.h
   rcw -r grwwc002.rc

grwwc002.obj: grwwc002.c grwwc002.h
   cl /c grwwc002.c
