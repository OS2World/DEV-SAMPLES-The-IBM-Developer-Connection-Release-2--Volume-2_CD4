//-----------------------------------------------------------------------------
// Matrix.java
//
// A class that implements a three by three matrix and a set of helper
// methods that can be used by 2D graphics applications to apply
// various transformations to points before they are drawn. This class provides
// support for:
//
//   Scaling
//   Reflection (about X and Y axis)
//   Rotation
//   Translation
//   Shearing
//
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   Math
//
// Author : Kelvin R Lawrence.     29th-January-1998
//
// History:
//
//-----------------------------------------------------------------------------
public class Matrix
{
  public double m10 ;
  public double m11 ;
  public double m12 ;
  public double m20 ;
  public double m21 ;
  public double m22 ;
  public double m30 ;
  public double m31 ;
  public double m32 ;

  static final int RESETFIRST = 1 ;
  static final int COMBINE    = 2 ;

  //-------------------------------------------------------------------------
  // Constructor.
  //
  //-------------------------------------------------------------------------
  public Matrix()
  {
    makeIdentity( ) ;
  }

  //-------------------------------------------------------------------------
  // makeIdentity()
  //
  // Reset the matrix instance to be the identity matrix, such that any point
  // multiplied against this matrix will be unchanged.
  //
  // The identity matrix is defined as:
  //
  //           Ú       ¿
  //           ³ 1 0 0 ³
  //           ³ 0 1 0 ³
  //           ³ 0 0 1 ³
  //           À       Ù
  //-------------------------------------------------------------------------
  public void makeIdentity()
  {
    m10 = 1 ;    m11 = 0 ;   m12 = 0 ;
    m20 = 0 ;    m21 = 1 ;   m22 = 0 ;
    m30 = 0 ;    m31 = 0 ;   m32 = 1 ;
  }

  //-------------------------------------------------------------------------
  // updateMatrx()
  //
  // Update this matrix to have the same contents as the passed in matrix,
  //-------------------------------------------------------------------------
  public void updateMatrix( Matrix m )
  {
    this.m10 = m.m10 ;
    this.m11 = m.m11 ;
    this.m12 = m.m12 ;
    this.m20 = m.m20 ;
    this.m21 = m.m21 ;
    this.m22 = m.m22 ;
    this.m30 = m.m30 ;
    this.m31 = m.m31 ;
    this.m32 = m.m32 ;
  }


  //-------------------------------------------------------------------------
  // transformPoints()
  //
  // This version of the metod does not require that the number of points to
  // transform be specified but rather assumes that there are x.length points
  // to transform and attempts to transform all points. An exception will be
  // thrown if the y[] array has less points than the x[] array.
  //
  //-------------------------------------------------------------------------
  public void transformPoints( int[] x, int[] y )
  {
    transformPoints( x, y, x.length ) ;
  }

  //-------------------------------------------------------------------------
  // transformPoints()
  //
  // This version of the method will only transform the specified number
  // of points. This allows only a subset of an array of points to be
  // transformed. An exception will be thrown if there are not at least n
  // elements in both the x[] and y[] arrays.
  //
  //-------------------------------------------------------------------------
  public void transformPoints( int [] x, int [] y, int n )
  {
    double tx ;
    double ty ;

    for ( int i=0; i < x.length; i++ )
    {
      tx = (double)x[i] ;
      ty = (double)y[i] ;

      x[i] = (int) ( tx * m10 + ty * m20 + m30 ) ;
      y[i] = (int) ( tx * m11 + ty * m21 + m31 ) ;
    }
  }

  //-------------------------------------------------------------------------
  // setScaling()
  //
  // Helper method to setup a transformation to give the required scaling
  // about a given point. This is done by subtracting the given point,
  // doing the scaling and then re-adding the given point. This can be
  // expressed in matrix terms as:
  //
  //   Ú         ¿  Ú          ¿  Ú          ¿
  //   ³  1  0 0 ³  ³  sx 0  0 ³  ³ 1   0  0 ³
  //   ³  0  1 0 ³  ³  0  sy 0 ³  ³ 0   1  0 ³
  //   ³ -p -q 1 ³  ³  0  0  1 ³  ³ p   q  1 ³
  //   À         Ù  À          Ù  À          Ù
  //
  // Where (p,q) is the origin point of the scaling and sx and sy are the
  // respctive scaling factors for the x and y coordinates.
  //
  // By multiplying these matricies together we can derive a generic matrix
  // for scaling about a given point.
  //
  //                Ú                    ¿
  //                ³  sx       0      0 ³
  //                ³  0        sy     0 ³
  //                ³  -p*sx+p -q*sy+q 1 ³
  //                À                    Ù
  //
  // Which can be simplified to:
  //
  //                Ú                    ¿
  //                ³  sx       0      0 ³
  //                ³  0        sy     0 ³
  //                ³  p(1-sx) q(1-sy) 1 ³
  //                À                    Ù
  //
  //-------------------------------------------------------------------------
  public void setScaling(int p, int q, double sx, double sy)
  {
    this.m10 = sx ;
    this.m21 = sy ;
    this.m30 = (int)((double)p * (1-sx)) ;
    this.m31 = (int)((double)q * (1-sy)) ;
  }

  //-------------------------------------------------------------------------
  // setRotation()
  //
  // Helper method to setup a transformation to give the required rotation.
  //
  // A rotation of r degrees for a point (x,y) can be defined as:
  //
  //    x` = x * cos(r) - y * sin(r)
  //    y` = x * sin(r) + y * cos(r)
  //
  // A negative angle will cause a clockwise rotation:
  //
  //    x` =  x * cos(r) + y * sin(r)
  //    y` = -x * sin(r) + y * cos(r)
  //
  // A simple counterclockwise rotation of r degrees can be defined using a
  // matrix as follows:
  //
  //           Ú                   ¿
  //           ³  cos(r) sin(r)  0 ³
  //           ³ -sin(r) cos(r)  0 ³
  //           ³   0      0      1 ³
  //           À                   Ù
  //
  // A simple clockwise rotation of r degrees can be defined as:
  //
  //           Ú                   ¿
  //           ³  cos(r) -sin(r) 0 ³
  //           ³  sin(r)  cos(r) 0 ³
  //           ³   0      0      1 ³
  //           À                   Ù
  // Note that this assumes rotation about an origin of (0,0).
  //
  // To rotate a point about a given non zero point, you effectively have a
  // three step process.
  //
  //   1. Subtract the center point of the rotation from each point thus moving
  //      the origin of each point to be (0,0)
  //   2. Rotate the points.
  //   3. Add back in the center point.
  //
  // Each of these three steps could be expressed as a separate matrix where
  // (p,q) is the center point of the rotation and r is the angle of the
  // rotation. And indeed each step could be performed as a separate operation
  // using the three matrices below:
  //
  //   Ú         ¿  Ú                   ¿  Ú        ¿
  //   ³  1  0 0 ³  ³  cos(r)  sin(r) 0 ³  ³ 1  0 0 ³
  //   ³  0  1 0 ³  ³ -sin(r)  cos(r) 0 ³  ³ 0  1 0 ³
  //   ³ -p -q 1 ³  ³   0      0      1 ³  ³ p  q 1 ³
  //   À         Ù  À                   Ù  À        Ù
  //
  // However, a single matrix can be derived that represents this 3 stage operation
  // as a single step by multiplying each of the above matrices together. The
  // resultant matrix that defines a rotation of r degrees about a point (p,q) is
  // as follows:
  //
  //         Ú                                                     ¿
  //         ³  cos(r)                sin(r)                    0  ³
  //         ³ -sin(r)                cos(r)                    0  ³
  //         ³  p(1-cos(r))+q sin(r)  -p sin(r) + q(1-cos(r))   1  ³
  //         À                                                     Ù
  // The last row is a simplification of:
  //
  //           -p cos(r) + q sin(r) + p  and  -p sin(r) + q cos(r) + q
  //-------------------------------------------------------------------------
  public void setRotation( int x, int y, double degs )
  {
    //------------------------------------------------------
    // Convert degrees to radians for Java's trig functions.
    //------------------------------------------------------

    double rads = degs * Math.PI/180 ;

    //-------------------------------------------
    // Setup the correct values for the rotation.
    //-------------------------------------------

    m10 =  Math.cos( rads ) ;
    m11 =  Math.sin( rads ) ;
    m20 = -Math.sin( rads ) ;
    m21 =  Math.cos( rads ) ;

    m30 = -x * Math.cos(rads) + y * Math.sin(rads) + x ;
    m31 = -x * Math.sin(rads) - y * Math.cos(rads) + y;
  }

  //-------------------------------------------------------------------------
  // setTranslation()
  //
  // Helper method to setup a transformation to give the required translation.
  //
  //-------------------------------------------------------------------------
  public void setTranslation(int x, int y)
  {
    this.m30 = x ;
    this.m31 = y ;
  }

  //-------------------------------------------------------------------------
  // setXShear()
  //
  // Helper method to setup a transformation to give the required x and y
  // shearing.
  //
  //-------------------------------------------------------------------------
  public void setXShear()
  {
  }

  //-------------------------------------------------------------------------
  // setYShear()
  //
  // Helper method to setup a transformation to give the required x and y
  // shearing.
  //
  //-------------------------------------------------------------------------
  public void setYShear()
  {
  }

  //-------------------------------------------------------------------------
  // setXReflection()
  //
  // Setup a simple reflection about the X axis. The yMirror parameter
  // defines the location of an imaginary horizontal mirror line about
  // which the reflection will occur. Note that reflection is really just
  // a modified form of scaling with the y scale value being negative one.
  // This helper function means that the caller only has to decide upon
  // the location of the mirror line. The same effect could be achieved
  // by setting each of the matrix elements manually.
  //
  //-------------------------------------------------------------------------
  public void setXReflection( int yMirror )
  {
    setScaling( 1, yMirror, 1, -1 ) ;
  }

  //-------------------------------------------------------------------------
  // setYReflection()
  //
  // Setup a simple reflection about the Y axis. The xMirror parameter
  // defines the location of an imaginary vertical mirror line about
  // which the reflection will occur. Note that reflection is really just
  // a modified form of scaling with the x scale value being negative one.
  // This helper function means that the caller only has to decide upon
  // the location of the mirror line. The same effect could be achieved
  // by setting each of the matrix elements manually.
  //
  //-------------------------------------------------------------------------
  public void setYReflection( int xMirror )
  {
    setScaling( xMirror, 1, -1, 1 ) ;
  }

  //-------------------------------------------------------------------------
  // toString()
  //
  //-------------------------------------------------------------------------
  public String toString()
  {
    return "Simple 3 by 3 matrix" ;
  }

  //-------------------------------------------------------------------------
  // dumpToConsole()
  //
  // Dump out this matrix to the system console.
  //-------------------------------------------------------------------------
  public void dumpToConsole()
  {
    System.out.println( "Matrix contents" ) ;
    System.out.println( "===============" ) ;
    System.out.println( "m10 = " + m10 ) ;
    System.out.println( "m11 = " + m11 ) ;
    System.out.println( "m12 = " + m12 ) ;
    System.out.println( "m20 = " + m20 ) ;
    System.out.println( "m21 = " + m21 ) ;
    System.out.println( "m22 = " + m22 ) ;
    System.out.println( "m30 = " + m30 ) ;
    System.out.println( "m31 = " + m31 ) ;
    System.out.println( "m32 = " + m32 ) ;
  }
}
