//-----------------------------------------------------------------------------
// MyAdapter.java
//
// A class that extends WindowAdapter.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   WindowAdapter
//
//
// Author : Kelvin R Lawrence.    13th-Nov-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.*;
import java.awt.event.*;

public class MyAdapter extends WindowAdapter
{
  public MyAdapter()
  {
    System.out.println( "MyAdapter() constructor" ) ;
  }
  
               
  //--------------------------------------------------------------------
  // windowClosing()
  //
  // Called when a Frame window has is about to close.
  //--------------------------------------------------------------------
  public void windowClosing( WindowEvent we ) 
  {
    System.out.println( "Frame about to close" ) ;
    
    System.exit(0) ;
  }
}
