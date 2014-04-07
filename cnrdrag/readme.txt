CNRDRAG.ZIP

COPYRIGHT:
----------
Copyright (C) International Business Machines Corp., 1991,1995.

DISCLAIMER OF WARRANTIES:
-------------------------
The following [enclosed] code is sample code created by IBM
Corporation.  This sample code is not part of any standard IBM product
and is provided to you solely for the purpose of assisting you in the
development of your applications.  The code is provided "AS IS",
without warranty of any kind.  IBM shall not be liable for any damages
arising out of your use of the sample code, even if they have been
advised of the possibility of such damages.

Purpose:

This sample was designed to illustrate how the direct manipulation
classed provide by the Open Class Library can be integrated with
container controls in the Visual Builder.


Description:

This sample will display two primary frame windows. The first window
will contain a container of camera objects.  Cameras can be added to
the container by entering a location, room number and description
then pressing the "Add" button.  The second window will contain a
container of display objects.  Each display object can have a camera
object associated with it.  New displays can be added to the
container by entering a location and description then pressing the
"Add" button.  Cameras from the first window can be dragged onto
displays in the second window to change the camera associated with
the display.  When the drop occurs, a message box is displayed to
indicate the change.  Only cameras with the same location as the
display can be associated together.  When the user double-clicks on
a display, the current camera information is displayed in a message
box.


Sample:

1. Create non-visual parts for Camera and Display objects and generate
   part and feature source for each.  Be sure to include an attribute
   in the Display object that is a Camera*.

2. Create a visual part for CameraWindow and DisplayWindow.  Each
   window will have a container control and entry fields for creating
   new objects that are added to the container.

3. Create a variable pointer and a message box for the DisplayWindow.
   Connect the enterEvent from the container control to the variable
   this attribute and connect the selectedElement from the container
   to the this parameter of the previous connection.  Connect the
   this event of the variable to the message box with custom logic
   to show the camera used by the display.

   Example:

      Display* display = source->targetPtr();
      Camera*  camera  = display->activeCamera();

      target->setTitle(display->Location());

      if ( camera )
      {
        target->show(IString(" Active camera : ")+
                     camera->Name(),
                     IMessageBox::information);
      }
      else
      {
        target->show(IString("No active camera"),
                     IMessageBox::information);

      }

4. Enable the basic direct manipulation (drag/drop) support provided
   by the class library for dragging from the CameraWindow and
   dropping on the DisplayWindow.

   This is accomplished by creating a custom logic connection from the
   ready event to the container control and calling the static methods
   provided by the IDMHandler class.

   Examples:

       IDMHandler::enableDragFrom(target);

       IDMHandler::enableDropOn(target);

   Be sure to add <idmhndlr.hpp> to the required header file list for
   the window for the IDMHandler class.

5. Using the Open Class Library, write the code for a new CameraItem
   and CameraItemProvider.  This code contains the logic for drag
   of Camera objects (instead of the default IContainerObject
   support) and for selectively controlling the drop.

   The files DMProv.hpp and DMProv.cpp contain sample code for the
   CameraItem and CameraItemprovider.  Please refer the the comments
   in these files for more information about how to write your own
   custom item and item provider classes.

6. Add the CameraItemProvider to the visual builder as a class
   interface part.  We can tell the visual builder to inherit from
   IVBase since we are not interested in the interface to the
   class (we only need to instanciate the class and set it as the
   item provider for each of our containers).  Once we have the
   part in the builder, we can drop an instance of the construction
   window and make a custom logic connection from the ready event to
   the container to set the item provider.

   Example:

    target->setItemProvider((IDMItemProvider*)
                            (source->icameraItemProvider));

7. Create a dummy DMProv.rci so that the builder will not fail when
   trying to build the custom class library code.

8. Create an icons.rcv file for the icons used in our containers and
   create the icons.  Add the file to the CameraWindow so that it
   will be bound to the executable and modify the containers to load
   the icons (instead of using the default icon).

9. Build the application (being sure to generate the part source for
   the windows that have been modified).

10. Run the application (VBMain.exe).


