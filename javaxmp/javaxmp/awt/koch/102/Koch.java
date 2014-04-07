//---------------------------------------------------------------------------
//
//  Name     : koch.java
//
//  Purpose  : Simple program that demonstrates a way to construct (to
//             various depths) the Koch snowflake curve fractal.
//
//             Note that for accuracy all calculations are done
//             using floating point values. 
//
//             See the function comment header blocks for exact details
//             of the algorithm used.
//
//  For any given depth, the number of lines to be drawn will
//  be as follows:
//
//           Depth  Number of lines
//
//             0            3
//             1           12
//             2           48
//             3          192
//             4          768
//             5         3072
//             6        12288
//             7        49152
//             8       196608
//
//  Or, alternatively, the number of lines at any depth can be
//  calculated using the formula:
//
//                   depth
//              3 * 4
//
// At greater depths, the amount of actual memory required to
// hold all of the line definitions will grow considerably.
// Each line will require four "double" data types to hold it.
// Additionally, before the lines are rendered, they are converted
// to integers and stored as two POINTLs (four LONGs) per line.
//
// This program is a port of my original koch.c, developed in 1996.
//
//             Copyright (C) Kelvin R. Lawrence
//             Java Version: 28th-March-1997 
//             C    Version:  9th-July-1996
//
// This version requires Java JDK level 1.0.2 or higher.
// I have tested it on Java JDK levels 1.0.2 and 1.1 only.
//
// History:
//   Mar-28-1997 -KRL- Initial port to Java from OS/2 PM C code. (J-0.a)
//
// Still to do:
//   Sierpinski triangle.
//   Multi threaded.
//   Status "processing" window.
//---------------------------------------------------------------------------

import java.awt.* ;
import java.applet.* ;
import java.util.*;

// Double precision POINTL
class DPOINTL
{
  public double x ;
  public double y ;
} ;


// Double precision line definition

class LINE_AB
{
  public DPOINTL a ;
  public DPOINTL b ;

  public LINE_AB()
  {
    //System.out.println("LINE_AB Constructor") ;
    a = new DPOINTL() ;
    b = new DPOINTL() ;
  }

} ;

//---------------------------------------------------------------
// Class: Koch
//
// Class that knows how to draw and render Koch Fractal Snowflake
// curves to specified depths.
//
//---------------------------------------------------------------
public class Koch extends Frame 
{
  final double SIN_60 = 0.87 ;
  final double COS_60 = 0.5  ;

  int     Depth     = 1 ;                   // Iteration depth

  double dist ;

  boolean debugMode = false ;

  CheckboxMenuItem d0 ;
  CheckboxMenuItem d1 ;
  CheckboxMenuItem d2 ;
  CheckboxMenuItem d3 ;
  CheckboxMenuItem d4 ;
  CheckboxMenuItem d5 ;
  CheckboxMenuItem d6 ;
  CheckboxMenuItem d7 ;
  CheckboxMenuItem d8 ;

  Menu depthMenu ;

  MenuBar menuBar ;


  //-------------------------------------------------------------
  // Constructor: 
  //
  //-------------------------------------------------------------
  public Koch( String s )
  {
    super( s ) ;

    menuBar = new MenuBar();

    depthMenu = new Menu( "Depth" ) ;

    d0 = new CheckboxMenuItem( "Depth 0" ) ;
    d1 = new CheckboxMenuItem( "Depth 1" ) ;
    d2 = new CheckboxMenuItem( "Depth 2" ) ;
    d3 = new CheckboxMenuItem( "Depth 3" ) ;
    d4 = new CheckboxMenuItem( "Depth 4" ) ;
    d5 = new CheckboxMenuItem( "Depth 5" ) ;
    d6 = new CheckboxMenuItem( "Depth 6" ) ;
    d7 = new CheckboxMenuItem( "Depth 7" ) ;
    d8 = new CheckboxMenuItem( "Depth 8" ) ;

    depthMenu.add( d0 ) ;
    depthMenu.add( d1 ) ;
    depthMenu.add( d2 ) ;
    depthMenu.add( d3 ) ;
    depthMenu.add( d4 ) ;
    depthMenu.add( d5 ) ;
    depthMenu.add( d6 ) ;
    depthMenu.add( d7 ) ;
    depthMenu.add( d8 ) ;

    menuBar.add( depthMenu ) ;

    this.setMenuBar( menuBar ) ;
  }

  //-------------------------------------------------------------------------
  // handleEvent()
  //
  // Handle events. We need this so that the close in the title bar will
  // actually close the application etc.
  //-------------------------------------------------------------------------
  public boolean handleEvent( Event evt )
  {
    if ( evt.id == Event.WINDOW_DESTROY)
    {
      System.exit(0) ;
      return true ;
    }
    else
    {
      return( super.handleEvent( evt ) );
    }
  }

  //----------------------------------------------------------
  // action()
  //
  // Handle user actions.
  //
  // 
  //----------------------------------------------------------
  public boolean action( Event evt, Object what )
  {
    if ( debugMode )
    {
      System.out.println("Start of action() method");
    }

    //--------------------
    // Handle menu events.
    //--------------------
    if ( evt.id == Event.ACTION_EVENT )
    {
        if ( evt.target == d0 )
        {
          this.Depth = 0;
          this.repaint() ;
        }
        else if ( evt.target == d1 )
        {
          this.Depth = 1;
          this.repaint() ;
        }
        else if ( evt.target == d2 )
        {
          this.Depth = 2;
          this.repaint() ;
        }
        else if ( evt.target == d3 )
        {
          this.Depth = 3;
          this.repaint() ;
        }
        else if ( evt.target == d4 )
        {
          this.Depth = 4;
          this.repaint() ;
        }
        else if ( evt.target == d5 )
        {
          this.Depth = 5;
          this.repaint() ;
        }
        else if ( evt.target == d6 )
        {
          this.Depth = 6;
          this.repaint() ;
        }
        else if ( evt.target == d7 )
        {
          this.Depth = 7;
          this.repaint() ;
        }
        else if ( evt.target == d8 )
        {
          this.Depth = 8;
          this.repaint() ;
        }
    }
    else
    {
      return false ;
    }

    return true ;
  }

  //--------------------------------------------------------------------------
  //
  // DoKoch()
  //
  // Perform the iterations necessary to generate the fractal to the
  // required depth. 
  //
  // --------------------------------------------------------------------------
  public void doKoch( int depth ) 
  {
    int cLines = 3 ;

    LINE_AB [] lines, newLines ;

    this.Depth = depth ;

    Dimension d = this.size()  ;
    Insets ins  = this.insets() ;

    if ( debugMode )
    {
      System.out.println( "Width of frame  = " + d.width ) ;
      System.out.println( "Height of frame = " + d.height ) ;
      System.out.println( "About to allocate memory" ) ;
    }

    lines = new LINE_AB[3] ;

    for ( int i = 0; i<3; i++ )
    {
      lines[i] = new LINE_AB() ;  
    }

    //--------------------------------------------------------------------
    // Set up three lines to define the initial equilateral triangle.
    // The calculations below attempt to generate the largest possble
    // equilateral triangle that will fit in the current client window
    // and still leave enough room for expansion as the fractal evolves.
    // The triangle will also be centred in the window.
    //--------------------------------------------------------------------

    d.height = d.height -  (ins.top + ins.bottom ) ;
    d.width  = d.width  -  (ins.left + ins.right ) ;

    dist = Math.min( d.width, d.height)/5*4 ;

    if ( debugMode )
    {
      System.out.println( "Adjusted height ==>" + d.height ) ;                                       
      System.out.println( "Adjusted width  ==> " + d.width ) ;
      System.out.println( "Dist            ==> " + dist ) ;
    }
    
    lines[0].a.x = (double) ins.left + ((d.width-dist)/2) ;
    lines[0].a.y = (double) (ins.top + (dist/3 )) ;
    lines[0].b.x = lines[0].a.x + dist * COS_60 ;
    lines[0].b.y = lines[0].a.y + dist * SIN_60 ;
    lines[1].a.x = lines[0].b.x ;
    lines[1].a.y = lines[0].b.y ;
    lines[1].b.x = lines[0].a.x + dist ;
    lines[1].b.y = lines[0].a.y ;
    lines[2].a.x = lines[1].b.x ;
    lines[2].a.y = lines[1].b.y ;
    lines[2].b.x = lines[0].a.x ;
    lines[2].b.y = lines[0].a.y ;

    if ( debugMode )
    {
      System.out.println( "*** Starting triangle follows:" ) ;
      for ( int i=0; i<3; i++ )
      {
        System.out.println( lines[i].a.x ) ;
        System.out.println( lines[i].a.y ) ;
        System.out.println( lines[i].b.x ) ;
        System.out.println( lines[i].b.y ) ;
      }
    }

    //----------------------------------------------------
    // Iterate and generate the new lines. If the depth is
    // zero we just draw the starting triangle.
    //----------------------------------------------------
  
    if ( debugMode )
    {
      System.out.println( "Current depth is :: " + Depth ) ;
    }

    for ( int i=1; i <= Depth; i++ )
    {
      //----------------------------------------------------
      // Allocate a buffer big enough to hold the new points
      // we are about to generate. Each new set of lines
      // will have four times as many lines as the previous
      // set.
      //----------------------------------------------------

      newLines = new LINE_AB[ cLines * 4 ] ;
  
      for ( int j = 0; j < cLines * 4; j++ )
      {
        newLines[j] = new LINE_AB() ;  
      }

      if ( debugMode )
      {
        System.out.println( "Current number of lines is "+  cLines ) ;
      }

      //------------------------------------------------------------------
      // Now break each line in the current line buffer into smaller lines
      //------------------------------------------------------------------

      for ( int j=0; j < cLines; j++ )
      {
        replaceLine( lines[j], newLines, j*4 ) ; 
      }
  
      lines = newLines ;

      cLines = cLines * 4 ;
    }

    //------------------------------------------------------
    // Before we can render the lines we have to convert
    // the results of our computations from floating point
    // "double" values back to integers so that the 
    // AWT drawLine() function can draw the lines.
    //------------------------------------------------------
  
    Graphics g = this.getGraphics();
    g.setColor( Color.cyan ) ;

    for ( int i=0; i < cLines; i++ )
    {
      g.drawLine( (int)lines[i].a.x
                , (int)lines[i].a.y
                , (int)lines[i].b.x
                , (int)lines[i].b.y
                );
    }
  }

  //--------------------------------------------------------------------------
  //
  // ReplaceLine()
  //
  // Takes a line and applies the Koch substitution to the passed in line and
  // generates the points that define four new lines.
  //
  //
  // The line:                       would become:
  //
  //                                             E
  //                                             +
  //                                        ce  / \  de
  //                                           /   \
  //                                     ac   /     \   db
  //   ---------------------          -------+       +-------
  //   A         ab        B          A      C   F   D      B
  //
  // Where A,B,C,D,E are the points of the new lines, F is the midpoint of the line
  // AB and ac, ce, de and da are the lengths of those respective lines.
  // Note that the triangle CED will be an equilateral triangle with all
  // sides equal in length to the lines AC and DB.
  //
  // This function assumes that the source line pointer ptlLine always points
  // to three  DLINE_AB structures that define a straight line and also assumes
  // the new lines pointer ptlNew points to a buffer big enough to hold four
  // DLINE_AB structures (i.e. enough points to define four connected lines).
  //
  // Algorithm summary. The new points are generated as follows:
  //
  //   Point A = original point A
  //   Point B = original point B
  //   Point C = Point A + one third x and one third y
  //   Point D = Point D - one third x and one third y
  //   Point E = Use sine/cosine formulae to calculate
  //
  // We also have to take into account the slope of the line when generating
  // point E. The slope of the line combined with the direction of the
  // line will tell us on which side of the line AB the new point E resides.
  //
  // TRIGONOMETRY BACKGROUND RECAP:
  //
  // For those interested, the following describes the trigonometrical formulae
  // used to help generate the new points.
  //
  // Most of the points in the new lines are trivial to generate and can be generated
  // without using any trig functions or any kind of complex mathematics.
  // The points A and B are unchanged in the new lines, the points C and D are
  // derived simply by adding or subtracting one third the length of the line ab
  // from A and B respectively, very straightforward. This is not quite true for
  // the point E. This point has to be derived using simple trig formulae.
  //
  // Note that The length of each new line is one third the length of the old lines.
  // In other words, the lines ac, ce, ed and db are all equal in length. Note also
  // that the triangle CDE will always be an equilateral triangle and that the triangle
  // formed by the points CFE will always have a right angle at the point F where the
  // line FE (not shown above) meets the line AB.
  //
  // Given that we know the lengths of the lines CF and CE we know (pythagaros) that
  // the length of the line FE can be found using the formula:
  //
  //                                    2     2
  //                       FE = sqrt( CE  - CF  )
  //
  // However, we don't need to use line FE to find point E.
  // We know that if we know the length of a line and its start point and the
  // angle that line makes with another line, we can find the end point of the line
  // using the formula:
  //
  //                       x2 = x1 + length * cosine(angle)
  //                       y2 = y1 + length * sine(angle)
  //
  // We also know that for a right angled triangle, we don't need to know the angle
  // if we know the length of the sides, to calculate the sine and cosine values. We
  // can use the formulae (where C is the angle of the line ce)
  //
  //                       cosine(C) = cf/ce   (adjacent/hypoteneuse)
  //                       sine(C)   = ef/ce   (opposite/hypoteneuse)
  //
  //
  // As you will see in the code below, because we are dealing with equilateral
  // triangles, the mathematics gets simplified quite a bit.
  //
  // We also look at the gradient (slope) of the line. The formula for a straight line
  // is defined as y = mx + c where m is the gradient and c is the point where the line
  // crosses the y axis. If we wanted to know the gradient of the line CE (for example)
  // we would simply take the tangent of the angle. Tangent is defined as
  // oppsoite/adjacent or in this case de/cd.  Another way to think of this is
  // y difference/x difference.
  //
  // Put all of this together and you have an easy way to calculate the point E of the
  // new line set.
  //
  // NOTE: I feel that this algorithm can be refined further given a little
  // more time. I put this together to demo the basic concepts rather than
  // the perfect way to draw Von Koch snowflakes.
  // -----------------------------------------------------------------------------------
  void replaceLine( LINE_AB  line, LINE_AB [] newLines, int idx )
  {
    double  x1,x2,y1,y2 ;
    double  xDelta, yDelta ;
    double  xSign, ySign, xStart, yStop ;

    DPOINTL midpoint = new DPOINTL() ;
 
    double gradient, ac, af, cf, fe, s1, s2 ;
 
    xSign = ySign = 1 ;

    if ( debugMode )
    {
      System.out.println( "*** replaceLine entered, line follows:" ) ;
      System.out.println( line.a.x ) ;
      System.out.println( line.a.y ) ;
      System.out.println( line.b.x ) ;
      System.out.println( line.b.y ) ;
    }
 
    //------------------------------------------------------------
    // The first point of our new set of lines and the last point
    // are easy to derive. They will be the same as for the passed
    // in line that we are replacing.
    //------------------------------------------------------------
 
    newLines[idx+0].a.x = line.a.x ;    // Point A
    newLines[idx+0].a.y = line.a.y ;
 
    newLines[idx+3].b.x = line.b.x ;    // Point B
    newLines[idx+3].b.y = line.b.y ;
 
    //-----------------------------------------------------------
    // Calculate one third of the x and y distance along the line
    //-----------------------------------------------------------
 
    xDelta = (double)((line.b.x - line.a.x)  / 3 )  ;
    yDelta = (double)((line.b.y - line.a.y)  / 3 )  ;
 
    //-----------------------------------------------------
    // Now calculate the points for the ending of the first
    // new line, the starting point of the second line and
    // the starting point of the last line.
    //-----------------------------------------------------
 
    newLines[idx+0].b.x =  newLines[idx+1].a.x = line.a.x + xDelta ;  // Point C
    newLines[idx+0].b.y =  newLines[idx+1].a.y = line.a.y + yDelta ;
 
    newLines[idx+3].a.x =  newLines[idx+2].b.x = line.b.x - xDelta ;  // Point D
    newLines[idx+3].a.y =  newLines[idx+2].b.y = line.b.y - yDelta ;
 
    //-------------------------------------------------------------------
    // Finally prepare to calculate point E, the peak of the new triangle.
    // We first calculate the midpoint of the line AB. The temp values
    // x1,x2, y1,y2 are used for code readability to represent the
    // coordinates of the original line.
    //--------------------------------------------------------------------
 
    x1 = line.a.x ; y1 = line.a.y ;
    x2 = line.b.x ; y2 = line.b.y ;
 
    midpoint.x =   Math.min(x2,x1) + Math.abs((( line.b.x - line.a.x ) /2 )) ;
    midpoint.y =   Math.min(y2,y1) + Math.abs((( line.b.y - line.a.y ) /2 )) ;
 
    //-----------------------------------------------------------------------
    // Calculate the length of the line ac which is the same as the length of
    // the line ce (see function comment block ).
    //-----------------------------------------------------------------------
 
    s1 = (double) Math.abs( newLines[idx+1].a.x - line.a.x ) ;
    s2 = (double) Math.abs( newLines[idx+1].a.y - line.a.y ) ;
 
    ac = Math.sqrt( s1 * s1 + s2 * s2 ) ;
 
    //-------------------------------------------------------------------------
    // Calculate the length of the line cf by calculating the length of the line
    // af and subtracting the length of the line ac.
    //--------------------------------------------------------------------------
 
    s1 = (double) Math.abs( midpoint.x - line.a.x ) ;
    s2 = (double) Math.abs( midpoint.y - line.a.y ) ;
 
    af = Math.sqrt( s1 * s1 + s2 * s2 ) ;
    cf = af - ac ;
 
    //-------------------------------------
    // Calculate the length of the line fe.
    //-------------------------------------
 
    fe = Math.sqrt( ac * ac - cf * cf ) ;
 
    //-----------------------------------------------------------------
    // We need to take into account the gradient (slope) of the line AB.
    // This is so that we can decide which side of the line the point E
    // lies. The gradient (same as TAN angle A) is derived by dividing
    // the y difference by the x difference between the points A and B.
    // We also have to allow for the direction of the line. We adjust
    // the starting point of our point Ecalculation also based on the
    // gradient. We special case the situation where the line AB is
    // horizontal, i.e. gradient=0.
    //
    // WARNING: The if tests that follow appear as if they can be
    // compressed. Do not do this! They must be in the order they are
    // in for the xSign calculations to be valid.
    //-----------------------------------------------------------------
 
    gradient = (double) (( y2-y1 ) / ( x2-x1 )) ;
 
    if ( gradient !=0 )
    {
      if ( gradient > 1 || gradient > -1 )
      {
        xSign = -1 ;
      }
 
      if ( x2 < x1 && gradient != 0 )
      {
        xSign  = -xSign ;
      }
 
      if ( gradient < 0 )
      {
        xStart = newLines[idx+3].a.x ;
        yStop  = newLines[idx+1].a.y ;
      }
      else
      {
        xStart = newLines[idx+1].a.x ;
        yStop  = newLines[idx+3].a.y ;
      }
 
      //----------------------------------------------------------
      // Now calculate the point E using the sine/cosine formulae.
      // See function comment block for more details.
      //----------------------------------------------------------
 
      newLines[idx+1].b.x = xStart + (( (cf/ac) * ac) * xSign  )   ;
      newLines[idx+1].b.y = yStop ;
    }
    else  // Gradient is zero
    {
      if ( x2 < x1 )
      {
        ySign  = -1 ;
      }
      //----------------------------------------------
      // Calculate point E for the zero gradient case.
      //----------------------------------------------
 
      newLines[idx+1].b.x = midpoint.x  ;
      newLines[idx+1].b.y = midpoint.y + fe * ySign ;
    }
 
    newLines[idx+2].a.x = newLines[idx+1].b.x ;
    newLines[idx+2].a.y = newLines[idx+1].b.y ;

    if ( debugMode )
    {
      System.out.println( "*** replaceLine about to exit,new points follow:" ) ;
      System.out.println(newLines[idx+0].a.x + " " + newLines[idx+0].a.y ) ;
      System.out.println(newLines[idx+0].b.x + " " + newLines[idx+0].b.y ) ;
      System.out.println(newLines[idx+1].a.x + " " + newLines[idx+1].a.y ) ;
      System.out.println(newLines[idx+1].b.x + " " + newLines[idx+1].b.y ) ;
      System.out.println(newLines[idx+2].a.x + " " + newLines[idx+2].a.y ) ;
      System.out.println(newLines[idx+2].b.x + " " + newLines[idx+2].b.y ) ;
      System.out.println(newLines[idx+3].a.x + " " + newLines[idx+3].a.y ) ;
      System.out.println(newLines[idx+3].b.x + " " + newLines[idx+3].b.y ) ;
    }
  }


  //----------------------------------------------------------------
  // paint()
  //
  // Paint method, called whenever the application client area needs
  // repainting.
  //----------------------------------------------------------------
  public void paint( Graphics g )
  {
    System.out.println("Paint generated");
    doKoch( this.Depth ) ;
  }


  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     Koch KochFrame ;

     KochFrame = new Koch( "Koch" ) ;

     KochFrame.resize( 300,300 ) ;
     KochFrame.show() ;
     KochFrame.setBackground( Color.black ) ;
     KochFrame.doKoch( 3 ) ;
  }
}


