
DISCLAIMER
----------

Nothing in this package should be take to imply any plans are in place
to satisfy any of the requirements listed in this document.  The
interfaces and functions supplied by this prototype will certainly
change before this type of support appears in the product, if it ever
appears in the product.

Support will be provided only as time allows and given our current
release schedule it looks like there will be no time for supporting
this package.

We are however interested in feedback and comments.  Inside IBM, please
use the VACPPVIS forum on IBMPC.  Outside IBM, please use Compuserve
(GO VACPP - section 3 "Visual Building") or via IBM TalkLink to
VACPP CFORUM on IBMPC.  Make the post to the attention of Steve Wasleski.


ABSTRACT
--------

The IBM VisualAge for C++ Visual Builder does not directly support
the creation of multithreaded applications.  Due to graphical user
interface (GUI) thread timing restrictions multiple threads are
usually needed in all but the most basic applications.

This package contains a prototype that enables multithreaded
programming with the VisualAge for C++ Visual Builder.


FILES
-----

Starting in the directory you download the ZIP file to:
  +
  | THREADS2.ZIP - Contains all these files except itself. Use
  |                PKUNZIP2 -d THREADS2.ZIP to unpack into current directory.
  | README.TXT   - This file.
  |
  +--INCLUDE
  |    ianotev.hpp  - IAsyncNotificationEvent
  |    inotdisp.hpp - INotificationDispatcher
  |    iobslis2.hpp - IObserverList (a new interface for an old class)
  |    iobsptr2.hpp - IObserverPtr (a new interface and implementation for
  |                   an old class)
  |    istdntf2.hpp - IStandardNotifier2 (a replacement for IStandardNotifier
  |                   that can coexist with IStandardNotifier)
  |    IVBDEFS.H    - The version shipped with VisualAge for C++ V3.5 on
  |                   Windows.  The package needs this versionn for both
  |                   Windows and OS/2, so it is included here.
  |
  +--SOURCE
  |    BUILD.CMD    - Rename the appropriate mak file to stdntf2.mak and run
  |                   this.  Rename this file to BUILD.BAT for Win95.
  |                   Specify /a parameter to force rebuild.
  |    ianotev.cpp  - Implementation for IAsyncNotificationEvent
  |    inotdisp.cpp - Implementation for INotificationDispatcher
  |    iobslis2.cpp - Implementation for IObserverList (new implementation)
  |    istdntf2.cpp - Implementation for IStandardNotifier2
  |    stdntf2.def  - Only used on OS/2
  |    stdntf2.os2  - OS/2 make file
  |    stdntf2.win  - Windows make file
  |
  +--OS2
  |  |
  |  +--BIN
  |  |    stdntf2.dll - The DLL
  |  |    stdntf2.LIB - The import library
  |  |    stdntf2.vbb - The VBB file
  |  |
  |  +--SAMPLES
  |     | BUILD.CMD - Builds all the samples.  Specify /a parameter to force
  |     |             rebuild.
  |     |
  |     +--COUNTER
  |     |    BUILD.CMD   - Builds this sample.  Specify /a parameter to
  |     |                  force rebuild.
  |     |    counter.cpv - This file is common between OS/2 and Windows, but
  |     |                  is replicated in both directory structures.
  |     |    counter.hpv - This file is common between OS/2 and Windows, but
  |     |                  is replicated in both directory structures.
  |     |    Counter.vbb - Generate part source CountMn, CountWnd and Counter,
  |     |                  and generate main for CountMn before building.
  |     |
  |     +--COUNTER2
  |     |    BUILD.CMD    - Builds this sample.  Specify /a parameter to
  |     |                   force rebuild.
  |     |    counter.cpv  - This file is common between OS/2 and Windows, but
  |     |                   is replicated in both directory structures.
  |     |    counter.hpv  - This file is common between OS/2 and Windows, but
  |     |                   is replicated in both directory structures.
  |     |    Counter.vbb  - Generate part source CountMn, CountWnd and Counter,
  |     |                   and generate main for CountMn before building.
  |     |    countmn.cpv  - This file is common between OS/2 and Windows, but
  |     |                   is replicated in both directory structures.
  |     |    countmn.hpv  - This file is common between OS/2 and Windows, but
  |     |                   is replicated in both directory structures.
  |     |    countwnd.cpv - This file is common between OS/2 and Windows, but
  |     |                   is replicated in both directory structures.
  |     |    countwnd.hpv - This file is common between OS/2 and Windows, but
  |     |                   is replicated in both directory structures.
  |     |
  |     +--TestArea
  |          GETCNT.CMD - Copies STDNTF2.DLL and the COUNTER sample to the
  |                       TestArea.  You can then run CountMe.exe from here
  |                       without any changes to your environment.  This
  |                       assumes you have a .; near the start of your LIBPATH.
  |
  +--WIN
     |
     +--BIN
     |    stdntf2.dll - The DLL
     |    stdntf2.LIB - The import library
     |    stdntf2.vbb - The VBB file
     |
     +--SAMPLES
        | BUILD.BAT - Builds all the samples.  Specify /a parameter to force
        |             rebuild.
        |
        +--COUNTER
        |    BUILD.BAT   - Builds this sample.  Specify /a parameter to
        |                  force rebuild.
        |    counter.cpv - This file is common between OS/2 and Windows, but
        |                  is replicated in both directory structures.
        |    counter.hpv - This file is common between OS/2 and Windows, but
        |                  is replicated in both directory structures.
        |    Counter.vbb - Generate part source CountMn, CountWnd and Counter,
        |                  and generate main for CountMn before building.
        |
        +--COUNTER2
        |    BUILD.BAT    - Builds this sample.  Specify /a parameter to
        |                   force rebuild.
        |    counter.cpv  - This file is common between OS/2 and Windows, but
        |                   is replicated in both directory structures.
        |    counter.hpv  - This file is common between OS/2 and Windows, but
        |                   is replicated in both directory structures.
        |    Counter.vbb  - Generate part source CountMn, CountWnd and Counter,
        |                   and generate main for CountMn before building.
        |    countmn.cpv  - This file is common between OS/2 and Windows, but
        |                   is replicated in both directory structures.
        |    countmn.hpv  - This file is common between OS/2 and Windows, but
        |                   is replicated in both directory structures.
        |    countwnd.cpv - This file is common between OS/2 and Windows, but
        |                   is replicated in both directory structures.
        |    countwnd.hpv - This file is common between OS/2 and Windows, but
        |                   is replicated in both directory structures.
        |
        +--TestArea
             GETCNT.BAT - Copies STDNTF2.DLL and the COUNTER sample to the
                          TestArea.  You can then run CountMe.exe from here
                          without any changes to your environment.


THE COUNTER SAMPLE
------------------

1) In the OS2 or WIN SAMPLES\COUNTER subdirectory, generate source from the
   VBB file.  See the notes in the file listing above.

2) In the OS2 or WIN SAMPLES\COUNTER subdirectory, run the BUILD command.
   Again, see the notes above.

3) In the OS2 or WIN SAMPLES subdirectoy, switch to the TestArea
   subdirectory and run the GETCNT command.  Again, see the notes above.

4) Run CountMn.exe.

5) When the window comes up, press the 'Create new count window' button.
   A connection is fired that brings up a CountWnd window.  The CountWnd
   part uses the Counter non-visual part that starts a thread that
   increments a counter every second.

   Notice that the counter advances, but does not hang the rest of the
   system since it is running on a separate thread.

6) You can create multiple instances of CountWnd by returning to the
   Counter Main Window and pressing the 'Create new count window' button
   again.

7) To exit the sample, close all the windows.  The main window can be closed
   before some or all the open count windows.


THE COUNTER2 SAMPLE
-------------------

Description:
  The COUNTER2 sample extends the COUNTER sample via three techniques that
  are often useful for applications that have multiple secondary windows
  and/or several background tasks running at once.  If you open the
  CountMn part (main window) you will see the three techniques.

  1) The "SystemMenu" part in the upper left corner and the connections
     to and from it prevent the main window from being closed while
     counter windows are open.
  2) The "SelectedCounter" part in the lower left corner shows how more
     detailed monitoring of an element selected in a container can be
     accomplished.
  3) The other variables to the right of the main window are used to
     monitor the currently (or most recently) active (had focus) counter
     window.

Running the sample:
  1) In the OS2 or WIN SAMPLES\COUNTER2 subdirectory, generate source from the
     VBB file.  See the notes in the file listing above.

  2) In the OS2 or WIN SAMPLES\COUNTER2 subdirectory, run the BUILD command.
     Again, see the notes above.

  3) In the OS2 or WIN SAMPLES subdirectoy, switch to the TestArea
     subdirectory and run the GETCNT2 command.  Again, see the notes above.

  4) Run CountMn.exe.

  5) When the window comes up, press the 'Create new count window' button.
     A connection is fired that brings up a CountWnd window.  The CountWnd
     part uses the Counter non-visual part that starts a thread that
     increments a counter every second.

     Notice that the counter advances, but does not hang the rest of the
     system since it is running on a separate thread.

  6) You can create multiple instances of CountWnd by returning to the
     Counter Main Window and pressing the 'Create new count window' button
     again.

     The values for all the count windows will display in the container
     in the main window.  The selected field near the bottom of the main
     window displays and can modify the count for the counter window selected
     in the container.  The active field near the bottom of the main window
     behaves similarly for the counter window that is, or last was, active
     (had focus).

     Notice that you can not close the main window while any counter windows
     are open.

  7) To exit the sample, close all the windows.  All the counter windows must
     be closed before the main window.


SOME DETAILS
------------

The following delimited sections are from the charts I use when making
presentations on this topic and package.  It is all I have time to provide
right now.  If after using the package, anyone has other information they
believe would be helpful to others trying to learn the package, please
send it to us.

*********************************************************************

VisualAge for C++ Visual Builder
--------------------------------

Multithreaded Programming
-------------------------

Steven F. Wasleski
VisualAge for C++ Visual Builder Development

IBM's technical and business plans are subject to change.  This
presentation is not intended to create any warranties.  IBM
warranties are contained in the appropriate IBM agreement(s).

*********************************************************************

Agenda
------

  - Motivations - the part user's view
  - Requirements - the part developer's view
  - How to create multithreaded non-visual parts
  - How to use multithreaded non-visual parts
  - Where to get the software
  - Other topics as time permits

*********************************************************************

Motivations - the part user's view
----------------------------------

  - Complex tasks and GUI responsiveness
  - New problems are not easily solved visually
    - Thread management
    - Synchronization
    - Resource allocation
    - Interthread communication
  - These problems have nothing to do with what the application
    developer really wants to do
  - New approach using encapsulation and notification

*********************************************************************

Motivations - the part user's view
----------------------------------

  - Encapsulation
  - A part can represent anything that is modeled as an object
  - Implementations can be very simple or arbitrarily complex
  - Threading can be encapsulated
    - Thread management is a hidden implementation detail
    - Users of the part should not care about threading but still
      receive the benefit of a responsive GUI
  - Notification
    - Must be the same for the part user as the single thread case
      - Notifications all dispatched on the thread on which the part
        is created (hangs in the GUI system are common today when a
        part notifies on multiple threads)
      - Event data supported across threads and on asynchronous
        notifications

*********************************************************************

Requirements - the part developer's view
----------------------------------------

  - Notifications must transparently cross threads
    - Cross thread mechanism must be efficient
      - Performance must not suffer when a thread switch is not
        needed
      - Minimal resource overhead
    - Ugly details of cross thread mechanism must be hidden
      - For example, deleting pending asynchronous notifications
        when notification is disabled or the part is deleted
  - Asynchronous notification must be added
    - An internal thread may need to remain responsive to an
      external source of events
    - Event data must be supported, including cleaning it up
  - All operations (notification, adding observers, removing
    observers, and clean up) must be thread safe

*********************************************************************

How to create multithreaded non-visual parts
--------------------------------------------

  - Create a non-visual part, but instead of inheriting from
    IStandardNotifier, inherit from IStandardNotifier2
  - Thread management includes
    - Creating and destroying thread objects as needed
    - Starting (and restarting) threads as needed
    - Writing threads to terminate cleanly (usually due to a
      condition being met or a task completing)
      - Often a thread can be left to terminate on its own
      - Sometimes you will need to wait for it to end (for example,
        in destructor)
  - Thread management takes place in
    - Constructors and destructor
    - Response to the change of an attribute
    - Response to an event from another object
    - Actions

*********************************************************************

How to create multithreaded non-visual parts
--------------------------------------------

  - Synchronous notification
    - Use notifyObservers ( const INotificationEvent& ) in exactly
      the same way you use it today
    - When called on the same thread on which the part was created,
      it does the same thing it does today
    - When called from another thread, the notification is
      synchronously dispatched on the same thread on which the part
      was created

*********************************************************************

How to create multithreaded non-visual parts
--------------------------------------------

  - Asynchronous notification
    - Use notifyObserversAsync ( IAsyncNotificationEvent* )
      - The event will be asynchronously dispatched and deleted
      - The IAsyncNotificationEvent object is adopted
      - Neither it nor its event data can be reliably referenced
        after the call returns
    - IAsyncNotificationEvent is a subclass of INotificationEvent
      - Defines a virtual destructor
      - If there is no event data or the event data is a four byte
        value, it can be used as is
      - If the event data is a pointer to another object, that object
        must be allocated on the heap and you must subclass
        IAsyncNotificationEvent
      - The destructor for your IAsyncNotificationEvent subclass
        must clean up the event data

*********************************************************************

How to create multithreaded non-visual parts
--------------------------------------------

  - Asynchronous notification with event data object example
    - If the event data is an IString like this

      IString*      eventData = 0;
      MyAsyncEvent* myEvent   = 0;
      try
      {
        eventData = new IString ( "My event data");
        myEvent = new MyAsyncEvent ( myId, *this, true, eventData );
        // This call throws an exception if the queue is full.
        notifyObserversAsync ( myEvent );
        // DO NOT REFERENCE OR DELETE eventData AND myEvent!!!
      }
      catch ( ... )
      {
        if ( myEvent != 0 )
          delete myEvent;    // This will clean up the event data too.
        else
          delete eventData;  // Delete on a 0 pointer is safe.
      }

*********************************************************************

How to create multithreaded non-visual parts
--------------------------------------------

  - Asynchronous notification with event data object example
    - Then the MyAsyncEvent class would look like this

      class MyAsyncEvent : public IAsyncNotificationEvent
      {
        MyAsyncEvent ( const INotificationId& identifier,
                       INotifier&             notifier,
                       Boolean                attrChanged=true,
                       const IEventData&      eventData=IEventData(),
                       const IEventData&      observerData=IEventData() )
          : IAsyncNotificationEvent ( identifier, notifier, attrChanged,
                                      eventData, observerData ) {}
        MyAsyncEvent ( const MyAsyncEvent& event )
          : IAsyncNotificationEvent ( event ) {}
        MyAsyncEvent& operator= ( const MyAsyncEvent& event )
        { IAsyncNotificationEvent::operator= ( event ); }
        virtual ~MyAsyncEvent ( )
        {
          IString* eventData = (IString*)(eventData().asUnsignedLong());
          delete eventData;
        }
      };

*********************************************************************

How to use multithreaded non-visual parts
-----------------------------------------

  - Use them the same way you use any other non-visual part
  - One thing that may seem a little different is that there
    may (or may not) be "start" and "stop" actions
  - Complex patterns of interconnections may be affected by
    asynchronous notifications

*********************************************************************

Where to get the software
-------------------------

  - Use anonymous ftp to service.boulder.ibm.com
  - If that does not work for you, try ftp.software.ibm.com
    - It is another name for the same ftp server
  - Change to directory ps/products/visualagecpp/tools/threads
  - Change to binary mode and download the file threads2.zip
    - You will also see the old threads.zip file
  - Unpack it with
    - On OS/2 -- PKUNZIP2 -d threads2.zip
    - On Windows -- PKUNZIP -d threads2.zip
    - This will unzip it into the current directory with several
      subdirectories
    - Please read the README.TXT file in the current directory

*********************************************************************

Other topics as time permits
----------------------------

  - Deletion of a non-visual part in response to one of its events.
    - See IStandardNotifier2::deleteAsync()

  - How is the cross thread notification implemented?
    - GUI message queue and one object window per notification
      dispatch thread (not per part and not one for every thread)
  - Then how on OS/2 can you synchronously send notifications from
    a non-PM thread?  A message queue is required to send a GUI
    message.
    - If the message queue does not exist when it is first needed,
      it is created.  It is safe to have a message queue on a
      background thread as long as nothing on that thread ever gets
      the input focus.

*********************************************************************

Other topics as time permits
----------------------------

  - How are the internal objects kept thread safe when observers
    can be added, observers can be removed, and notifiers can be
    deleted from other threads?
    - In short, hidden synchronous thread switches are used for
      these operations as needed.  As a result, the list of
      observers is touched only on the thread on which the part
      was created.  This results in performance gains.
  - How can all this result in better performance in the
    synchronous, no thread switch required, case?
    - The list of observers is only ever touched on a single thread,
      so a semaphore is not needed.
    - Single thread reentrancy is still a problem and is handled
      more efficiently.
  - How does this relate to future notification framework
    enhancements in the product?

*********************************************************************
