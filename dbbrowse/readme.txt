DBBbrowse - Database Browse utility.

Description:

  This utility uses some classes under development in the Data Access
  Builder team to build applications within the Visual Builder for
  dynamic access to data sources.  It can be used to browse the available
  datasources, as well as connect to, then browse the contents of the
  datasources.  This utility can be used to help resolve connection
  problems when using the DAB and generated code - errors should be
  reported as error messages in DBBrowse, which can help with
  problem determination.

Prerequisites:

  Win95, Win/NT - VACPP 3.5 GA.
  OS/2 - VACPP 3.4, CTV304

Files:
  Win95, Win/NT - dbbrsWIN.zip
  OS/2          - dbbrsOS2.zip

Use:

  Start via:
    dbbrowse
  There are no startup parameters.

  Select the datasources you wish to browse via double clicking.
  Connect to selected datasources, press "Get Tables" once connect
  completes.  Double-click on the tables to see the datatypes of
  the columns within the tables.  Record any errors encountered,
  especially any SQLSTATE/SQLCODE information.

  Providing a filename on the main panel and pressing the file button
  will start a savefile (as text).  This will enable the file button
  for the other panels, which can then be saved in any order desired.
  Re-pressing the file button on the main file will replace any current
  file of that name; doing this without changing the filename re-initializes
  the save filed contents (you need to re-save any of the other windows
  as well). Send the resulting text file to your service rep, who can analyse
  the contents for compability with the DAB tool.

Future Enhancements:

  Stand alone version (will be larger) that will not require VACPP
  to be installed.

  The ability to browse and edit the contents of the databases.


