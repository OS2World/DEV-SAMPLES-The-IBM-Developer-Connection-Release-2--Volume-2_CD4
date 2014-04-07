//-----------------------------------------------------------------------------
// MyLook.java
//
// A simple example of ...
//
// This version requires Java JDK level 1.1.2 or higher and the Swing Set
// classes (typically called swing.jar) to be in the Classpath.
//
// Requires Swing ver. 1.0 (JFC 1.1)
//
// Demonstrates use of these classes/interfaces:
//
//
// SPECIAL NOTE: The JFC definition is still not set in concrete. This
//               sample program may need to be modified to run on later
//               versions of the JFC. The look and feel area has so far
//               changed in every drop up to and including JFC 1.1. This
//               version has been updated to run on Swing 1.0 (JFC 1.1) in
//               fact having been updated it now requires it to run.
//
//
// Author : Kelvin R Lawrence.     5th-March-1998
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import com.sun.java.swing.plaf.* ;
import com.sun.java.swing.plaf.metal.* ;
import com.sun.java.swing.plaf.basic.*;

public class MyLook extends BasicLookAndFeel
{
  public String getName()
  {
    return( "MyLook" ) ;
  }

  public String getID()
  {
    return( "1" ) ;
  }

  public String getDescription()
  {
    return( "My test look and feel" ) ;
  }

  public boolean isNativeLookAndFeel()
  {
    return false ;
  }

  public boolean isSupportedLookAndFeel()
  {
    return true ;
  }
}

