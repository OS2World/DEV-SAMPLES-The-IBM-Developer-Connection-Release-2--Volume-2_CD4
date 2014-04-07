//-----------------------------------------------------------------------------
// UserLayout.java
//
// A simple example of how do build a custom layout manager by 
// implementing the layout manager interface.
// 
//
// This version requires Java JDK level 1.0.2 or higher.
//
// Demonstrates use of these interfaces:
//
//   LayoutManager
//
// Author : Kelvin R Lawrence.     12th-May-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.* ;

//---------------------------------------------------------------
// Class: UserLayout
//
// Simple class that experiments with custom layout managers.
//
//---------------------------------------------------------------
public class UserLayout implements LayoutManager
{
  private boolean debugMode = true ;

  //----------------------------------------------------------
  // Constructor
  //
  //----------------------------------------------------------
  public UserLayout()
  {
    debugOutput( "Enter: UserLayout constructor" ) ;
  }

  //--------------------------------------------------------------------
  // preferredLayoutSize()
  //
  // Called by the Container when its preferredSize method is called.
  //
  //--------------------------------------------------------------------
  public Dimension preferredLayoutSize( Container parent ) 
  {
    debugOutput( "Enter: preferredLayoutSize" ) ;
    Dimension d = new Dimension( 5,5 ) ;

    return d ;
  }

  //--------------------------------------------------------------------
  // minimumLayoutSize()
  //
  // Called by the Container when its minimumSize method is called.
  //
  //--------------------------------------------------------------------
  public Dimension minimumLayoutSize( Container parent )
  {
    debugOutput( "Enter: minimumLayoutSize" ) ;
    Dimension d = new Dimension( 5,5 ) ;

    return d ;
  }

  //--------------------------------------------------------------------
  // layoutContainer()
  //
  // Called by the Container when the components it contains need
  // to be laid out. This is the real worker method of a layout
  // manager.
  //
  //--------------------------------------------------------------------
  public void layoutContainer( Container parent )
  {
    int i,n,x,y,yDelta ;

    Dimension d ;
    Component[] comps ;

    debugOutput( "Enter: layoutContainer" ) ;

    n = parent.countComponents() ;

    debugOutput( "Number of components ==> " , n ) ;

    d = parent.size() ;

    comps = parent.getComponents() ;

    yDelta = d.height / n ;

    for ( i=0,x=0,y=0 ; i < n ; y += yDelta, i++ )
    {
      comps[i].reshape( x,y,d.width,yDelta ) ;
    }
  }

  //--------------------------------------------------------------------
  // removeLayoutComponent
  //
  // Called by the Container when one of its components is removed,
  // typically when its remove() method.
  //
  //--------------------------------------------------------------------
  public void removeLayoutComponent( Component comp ) 
  {
    debugOutput( "Enter: removeLayoutComponent" ) ;
  }

  //--------------------------------------------------------------------
  // addLayoutComponent
  //
  // Called by a Container when its add( String, Component ) method is
  // called. Not all layout managers use this method. Typically, the
  // BorderLayout uses this method but the FlowLayout and GridLayout
  // do not.
  //
  //--------------------------------------------------------------------
  public void addLayoutComponent( String name, Component comp )
  {
    debugOutput( "Enter: addLayoutComponent" ) ;
  }

  //--------------------------------------------------------------------
  // toString()
  //
  // Return a string representation of this class.
  //
  //--------------------------------------------------------------------
  public String toString()
  {
    return( "UserLayout layout manager" ) ;
  }

  //--------------------------------------------------------------------
  // debugOutput( String )
  //
  // Internal debugging method.
  //
  //--------------------------------------------------------------------
  void debugOutput( String s )
  {
    if ( debugMode )
    {
      System.out.println( "UserLayout : " + s ) ;
    }
  }

  //--------------------------------------------------------------------
  // debugOutput( String, int )
  //
  // Internal debugging method.
  //
  //--------------------------------------------------------------------
  void debugOutput( String s, int n )
  {
    if ( debugMode )
    {
      System.out.println( "UserLayout : " + s + n ) ;
    }
  }
}
