/*----------------------------------------*/
/* Build a new program skeleton directory */
/*                                        */
/* Copyright (C) Kelvin R. Lawrence       */
/*   17th-June-1995                       */
/*                                        */
/* Java version:                          */
/*   14th-May-1997                        */
/*----------------------------------------*/

parse arg nm rel

if nm = '' then nm = 'newapp'
if rel = '' then rel = '111'

/* 'd:' */

'@cd \krl\java'

dat= space(date(),1,'-')

say 'Making new directory : ' nm
'@md' nm

say 'Copying skeleton files'
'@cd' nm

if rel = '111' then
 do
  '@copy ..\skel\skel111.java skel.java'
 end
else
 do
  '@copy ..\skel\skel102.java skel.java'
 end

say 'Editing files via SED'

up = translate(nm)

'@sed -e s/xxxxxx/'nm'/g skel.java    > skel.temp'
'@sed -e s/dddddd/'dat'/g skel.temp   > 'nm'.java'

say 'Deleting files'

'@del skel.java'
'@del skel.temp'



/*
say 'Renaming files'

'@rename skel.*' nm'.*'
*/

say 'Done'


