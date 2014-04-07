//-----------------------------------------------------------------------------
// Loader.java
//
// A simple example of using the reflection classes and the basic
// features of the Class class to load a class file see if it has a
// main method, and if it does, call it.
// 
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//  Class
//  Method
//  Object
//
// Author : Kelvin R Lawrence.     21st-November-1997
//
// History:
//
//-----------------------------------------------------------------------------
import java.lang.reflect.* ;

//---------------------------------------------------------------
// Class: Loader
//
// Simple class that loads a specified class and diplays 
// information about it.
//
//---------------------------------------------------------------

public class Loader
{
  //-------------------------------------------------------------------------
  // main()
  //
  // The argument args[0] should be the name of a Java class file 
  // but without the .class extension. The class name needs to be
  // fully specified unless it is in the current directory. Valid
  // examples are:
  //
  //  java Loader myClass
  //  java Loader java.awt.Graphics
  // 
  // 
  //-------------------------------------------------------------------------
  static public void main( String[] args )
  {
    Class c ;
    Method [] methods ;
    Method mainMethod ;

    if ( args.length == 0 )
    {
      System.out.println( "Usage: Loader <classname>" ) ;
      return ;
    }

    //--------------------------------------------------------------------
    // Get a reference to the class and get a list of the methods that the
    // class declares.
    //--------------------------------------------------------------------
    try
    {
      c = Class.forName( args[0] ) ;

      System.out.println( "Class loaded." ) ;
      System.out.println( c.getName() ) ;

      methods = c.getDeclaredMethods() ;

      //-------------------------------------------------------------
      // This is how you search the list of method names and fnd one
      // to execute. Note that this does not take into account the 
      // number or type of parameters as we have a certain specific
      // expectation of the main method as having one parameter.
      //-------------------------------------------------------------
      for ( int i=0; i<methods.length; i++ )
      {
        if (methods[i].getName().equals("main") )
        { 
          mainMethod = methods[i] ;
          System.out.println( "Class has a main() method." ) ;

          Object [] obj = new Object[1] ;
          obj[0] = args ;
          System.out.println( "Attempting to call main() - technique 1." ) ;
          mainMethod.invoke( c, obj ) ;
          break ;
        }
      }

      //-------------------------------------------------------------------
      // Another way that we can do the same thing is to get a reference
      // just to the main() method by calling getMethod() and specifiying
      // an array of Class objects that represents the parameters to the
      // method that we are interested in, in this case, main() takes just 
      // a String array as its parameter.
      //-------------------------------------------------------------------
      Class  [] mainArgs = new Class[1] ;
  
      String [] str = new String[1];
      mainArgs[0] = str.getClass() ;
      
      mainMethod = c.getMethod( "main" ,  mainArgs) ;

      Object [] obj = new Object[1] ;
      obj[0] = args ;
      System.out.println( "Attempting to call main() technique 2." ) ;
      mainMethod.invoke( c, obj ) ;
    }
    catch ( Exception e )
    {
      System.out.println( "Exception trying to process :" + args[0] + ".class" ) ;
      System.out.println( e.toString() ) ;
    }


  }
}

