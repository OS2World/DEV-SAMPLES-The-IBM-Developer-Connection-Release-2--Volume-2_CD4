//-----------------------------------------------------------------------------
// properties.java
//
// Java class that displays the current Java properties and their values.
//
// Constructors:
//  none.
//
// Public Methods:
//  main()
//
// Private Methods:
//  none.
//
// Author : Kelvin R Lawrence.     6th-December-1996
//
// History:
//
//-----------------------------------------------------------------------------

import java.io.* ;
import java.awt.* ;
import java.util.* ;

public class properties
{

   //----------------------------------------------------------
   // main()
   //
   // Program entry point.
   //
   //----------------------------------------------------------
   static public void main( String[] args )
   {
    System.getProperties().list( System.out )  ;
   }


}
