//-----------------------------------------------------------------------------
// TestFrame.java
//
// A simple test program to show how the adapter classes can be used to
// avoid having to implement all of the methods required by a given
// listener interface.
//
// BACKGROUND INFO:
//
// By using an adapter class, you can implemet just the specific methods of
// the interface that you are interested in. For example the WindowListener
// interface defines the following methods:
//
//     windowActivated()
//     windowClosed()
//     windowClosing()
//     windowDeactivated()
//     windowDeiconified()
//     windowIconified()
//     windowOpened()
//
// It is annoying to have to handle all of these methods when you implement the
// WindowListener interface if all you really wanted was to get notified of when
// the Frame window is being destroyed (i.e.) to get the windowClosing() method
// called. There are two ways to shortcut this process. One is to use EnableEvents
// to enable the WINDOW_EVENT_MASK and catch the WINDOW_CLOSING event in the 
// processEvent() method. The other is to use an adapter, in this case the
// WindowAdapter. The WindowAdapter is an abstract class that already has definitions
// of each of the methods in the ActionListener interface. All you have to do is
// either extend the WindowAdapter and provide the methods that you want to override
// or create a WindowListener instance in your program and set that as the 
// window listener for a given frame window.
//
// This example creates the simple WindowListener/Adapter in-line in the main()
// method.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   Frame
//
//
// Author : Kelvin R Lawrence.    13th-Nov-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;

public class TestFrame2
{
  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     WindowListener l = new WindowAdapter()
     {
       public void windowClosing( WindowEvent we)
       {
         System.out.println( "Window about to close" ) ;
         System.exit(0) ;
       }
     } ;
     
     Frame f = new Frame("Adapter Test") ;
     f.addWindowListener( l ) ;
     f.setSize( 300,300 ) ;
     f.show() ;
  }
}
