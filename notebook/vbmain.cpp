/*******************************************************************************
* FILE NAME: NbkTest.app                                                       *
*                                                                              *
* DESCRIPTION:                                                                 *
*   Main program stub for class:                                               *
*     NbkTest                                                                  *
* ---------------------------------------------------------------------------- *
* Warning: This file was generated by the VisualAge C++ Visual Builder.        *
* Modifications to this source file will be lost when the part is regenerated. *
*******************************************************************************/
#ifndef _IWINDOW_
#include <iwindow.hpp>
#endif

#ifndef _IAPP_
#include <iapp.hpp>
#endif

#ifndef _IMSGBOX_
#include <imsgbox.hpp>
#endif

#ifndef _NBKTEST_
#include "NbkTest.hpp"
#endif


//------------------------------------------------------------------------------
// main
//------------------------------------------------------------------------------
int main(int argc, char ** argv)
{
   IApplication::current().setArgs(argc, argv);

   NbkTest *iPart;
   IMessageBox::Response resp = IMessageBox::unknown;

   do {
      try {
         iPart = new NbkTest();
         iPart->initializePart();
         }
      catch (IException& exc) { 
         resp = IMessageBox(IWindow::desktopWindow()).show(exc);
         }
      }
   while (resp == IMessageBox::retry);

   if (resp == IMessageBox::cancel) IApplication::current().exit();

   iPart->setAutoDeleteObject();
   iPart->show();
   IApplication::current().run();
   return 0;
}   //end main

