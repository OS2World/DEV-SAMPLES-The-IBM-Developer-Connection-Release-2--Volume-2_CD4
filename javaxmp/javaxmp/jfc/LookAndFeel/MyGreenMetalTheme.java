//-----------------------------------------------------------------------------
// JFCLook.java
//
// A simple example of extending the DefaultMetalTheme to produce a custom
// theme for use with the Metal look and feel.
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

public class MyRedMetalTheme extends DefaultMetalTheme
{

  private final ColorUIResource primary1 = new ColorUIResource(51, 102, 51);
  private final ColorUIResource primary2 = new ColorUIResource(102, 153, 102);
  private final ColorUIResource primary3 = new ColorUIResource(153, 204, 153);

  //-------------------------------------------------------------------------
  // getPrimary1()
  //
  //-------------------------------------------------------------------------
  protected ColorUIResource getPrimary1()
  {
    return primary1 ;
  }

  //-------------------------------------------------------------------------
  // getPrimary2()
  //
  //-------------------------------------------------------------------------
  protected ColorUIResource getPrimary2()
  {
    return primary2 ;
  }

  //-------------------------------------------------------------------------
  // getPrimary3()
  //
  //-------------------------------------------------------------------------
  protected ColorUIResource getPrimary3()
  {
    return primary3 ;
  }

  //-------------------------------------------------------------------------
  // getName()
  //
  //-------------------------------------------------------------------------
  public String getName()
  {
    return "Simple red theme";
  }
}

