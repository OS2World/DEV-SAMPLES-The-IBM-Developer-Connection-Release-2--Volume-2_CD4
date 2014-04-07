SampleCGI.exe : SampleCGI.obj 
	link -debug:full -debugtype:cv SampleCGI.obj
	copy *.exe d:\www\cgi-bin
	

SampleCGI.obj : SampleCGI.c
	cl -Z7 -Od -c -W2 -D_X86_=1 -DWIN32  SampleCGI.c
