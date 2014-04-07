//******************************************************************************

//                                                                             *

//COPYRIGHT: (C) Copyright International Business Machines Corp., 1996.        *

//                                                                             *

//DISCLAIMER OF WARRANTIES:                                                    *

//  The following [enclosed] code is sample code created by IBM                *

//  Corporation.  This sample code is not part of any standard IBM product     *

//  and is provided to you solely for the purpose of assisting you in the      *

//  development of your applications.  The code is provided "AS IS",           *

//  without warranty of any kind.  IBM shall not be liable for any damages     *

//  arising out of your use of the sample code, even if they have been         *

//  advised of the possibility of such damages.                                *

//                                                                             *

//******************************************************************************





General Notes



   Open class libraries are not generally threadsafe.  It's not a good idea

   to destroy these sample on thread objects while they are busy, though the

   destructor does cancel the background thread first.  This may

   cause unpredictable results unless the entire application is

   terminating (but may potentially result in protection violations).



   The exact exception type occurring on the background thread cannot be

   preserved.  All exceptions are copied to IExceptions, the contents of

   which must be  examined to resolve the cause of the exception.

   This restriction is due to the fact that the exception must be copied

   from the background thread's stack, and the copy constructor to an

   IException loses any detailed inheritance information.



   This solution is not the same as the solution we will provide

   in a future release of DAX, though it will be compatible.

   You can revert your application back to the original

   implementation to take advantage of a future DAX release by dragging

   the action connections back to the original object, deleting the

   proxy objects, regenerating both DAX and the VB files, and recompiling

   and linking your application.



Prerequisites:



   You must have installed fixpak CTV304.  Any data access generated

   code you use with this package must be regenerated and rebuilt

   for this package (including the sample) to run correctly.



Installation:



   1) Unzip DAXTHRDS.ZIP into it's own directory.

   2) Copy DAXTHRDS.VBB to the drive that you installed VACPP, eg:

        copy DAXTHRDS.VBB d:\ibmcpp\dde4vb\DAXTHRDS.VBB

      You may wish to mark the file DAXTHRDS.VBB as read only when

      you do this to prevent it from being updated accidentally.  Use:

        attrib +R d:\ibmcpp\dde4vb\DAXTHRDS.VBB

   3) Copy the following files to the drive that you installed VACPP:

        copy IDATHRD.HPP d:\ibmcpp\include\idathrd.hpp

        copy cppods3i.lib d:\ibmcpp\lib\cppods3i.lib

        copy cppods3i.dll d:\ibmcpp\dll\cppods3i.dll

        copy daxscl.bnd d:\ibmcpp\bnd\daxscl.bnd

   4) Rebind the new version of the class library to any database that

      uses it, via: 

        sqlbind d:\ibmcpp\bnd\daxscl.bnd <yourDatabase>

   5) Relink any application using the DAB support and the DLL cppods3i.dll.


   The DAXTHRDS package will co-exist with the non-thread enabled version.


To build the sample:



  The sample program uses the DAXSAMP Car table.

    1) recreate the sample DAXSAMP database if you did not do so

       when you installed VACPP.  Issue:

          daxsamp

       from the directory that you unzipped DAXTHRDS.ZIP

    2) Use the Data Access Builder to generate and build the Car DLL.

    3) Start the Visual Builder, load VBDAX.VBB and DAXTHRDS.VBB.

       Import CAR.VBE.

    4) Generate the "Part source" and "main for part" for the sample file.

    5) Build and run sample.exe.  Make sure the CAR.DLL used in sample is

       indeed the one you built in step 2.



Use With Your Applications:



   Ensure that any data access code for your application has

   been generated and rebuilt using fixpak CTV304.

   You are then ready to use the background thread support.

   Start by loading VBDAX.VBB and DAXTHRDS.VBB into

   the visual builder to begin your application.

   DAXTHRDS.VBB contains a sample program that

   shows the use of the multithread proxies.  It also contains the

   part interfaces for the IVBDatastore and IVBDSConnectCanvas parts

   used for the multithreaded implementation.



IPersistentObjectOnThread



   IPersistentObjectOnThread is used as a proxy to allow the add, update

   add and delete methods to be performed in a background thread.

   It sends a notification when the operation is complete or if an exception

   occurs.  You can use this part with the threading overhead to allow you

   to determine when and whether the operation completed successfully or with

   a problem even if you do not require the background thread support.



   A proxy design pattern was used as it is difficult for you to change the

   inheritance of the generated code (you need to modify it).  An

   implementation requiring derivation would require you to derive

   classes for each of your generate part.  The proxy allows the support

   to be added with minimal changes to your existing applications and

   minimal work for each new class.  An integrated solution is being

   considered for a later release.



   When the object is modified during the database access (eg: retrieve),

   the row data is updated into the proxy object and copied into the

   target object when the operation completes.  Note that in the case

   of IStrings (typically any of the longer columns), the IString buffer

   is not copied, saving performance overhead.



   The objects are not protected by a semaphore while the datastore operations

   are in progress.  You need to ensure that the object is not modified

   during the operation.  If necessary, you should add semaphore protection or make

   a copy of the object before using the onThread operations.



   Use IPersistentObjectOnThread part by making the following connections between

   it and your generated IPersistentObject:

     1) identify the generated class type to be handled:

          IPersistentObjectOnThread.settings - set itemType to

          the generatedClassName (eg: Car).

     2) identify the object to be handled:

          IPersistentObject.this to IPersistentObjectOnThread.targetObject

        This is optional, you can identify (or override) the target object

        on the add, update, del or retrieve action.

     3) You do not need to modify your attribute to attribute connections

        of your target object.

     4) All database operation related actions need to be made (or moved) to the

        IPersistentObjectOnThread object, rather than the target object.

        Note that you can simply drag and drop the existing connections from

        the target part to the new IPersistentObjectOnThread object.

        If you did not identify the target via step 2, connect target.this

        to the target attribute of the action connection of the proxy.

     5) Use any new methods, attributes and events of IPersistentObjectThread

        as required in your application.  Browse the part features for

        details.



IPOManagerOnThread



   IPOManagerOnThread is used as a proxy to allow the select and refresh

   methods to be performed in a background thread.  It sends a notification

   when the operation is complete or if an exception occurs.  You can use

   this part with the threading overhead to allow you to determine when

   and whether the operation completed successfully or with a problem even

   if you do not require the background thread support.



   If you are using this part, do not call the refresh/select methods

   directly for the managed part, it will cause the items sequences to get

   out of sync and may result in protection violations (as elements may

   be deleted without being removed from the managed sequence).



   A proxy design pattern was used as it is difficult for you to change the

   inheritance of the generated code (you need to modify it).  An

   implementation requiring derivation would require you to derive

   classes for each of your generate part.  The proxy allows the support

   to be added with minimal changes to your existing applications and

   minimal work for each new class.  An integrated solution is being

   considered for a later release.



   Use IPOManagerOnThread part by making the following connections between

   it and your generated IPOManager:

     1) identify the generated class type to be managed:

          IPOManagerOnThread.settings - set itemType to

          the generatedClassName* (eg: Car*).

     2) identify the manager to be handled:

          IPOManager.this to IPOManagerOnThread.targetManager

     3) identify the sequence to be handled:

          IPOManager.items to IPOManagerOnThread.targetItems

     4) drag the IPOManager.items connection to IPOManagerOnThread.items.

        The target of this connection (ie: your container, listbox)

        will now be observing the IPOManagerOnThread items.

     5) Move the database actions from your existing manager part (select,

        refresh) to this IPOManagerOnThread object.  Note that you can

        simply drag and drop the existing connections from the target part

        to the new object.

     6) Use any new methods, attributes and events of IPOManagerOnThread

        as required in your application.  Browse the part features for

        details.





IVBDatastore



  The visual part description for the IVBDatastore class, which provides

  a background thread version of the IDatastore class.  Use in place

  of the IDatastore class, but note that the datastore related actions

  will snd IExceptionOccurred notifications rather than throw

  IException conditions (as the exception is thrown from the background).



IVBDSConnectCanvas



  A version of the IDSConnectCanvas that uses IVBDatastore rather than

  IDatastore.  This canvas class can replace IDSConnectCanvas directly,

  though this class inherits from IMulticellCanvas, while IDSConnectCanvas

  inherits from ICanvas.



Sample



  A sample showing how the various background thread enabled parts can be

  used in an application.  Generate the source and main code for this part,

  build and run.  The DAXSAMP database part, Car, is used for this sample

  (though must be rebuilt using fixpak CTV304).




