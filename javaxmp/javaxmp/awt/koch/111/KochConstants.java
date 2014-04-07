//-----------------------------------------------------------------------------
// KochConstants.java
//
// Constants used by the Koch.java application. These constants can
// be used by other Java applications by simply implementing the 
// KochConstants interface. Even though there are no methods in this
// interface the constant values are still available to those who implement
// the interface.
//
// Author : Kelvin R Lawrence.     19th-Nov-1997
//
// History:
//
//-----------------------------------------------------------------------------
public interface KochConstants
{
  static final double SIN_60 = 0.87 ;
  static final double COS_60 = 0.5  ;

  static final int MODE_LINES = 0 ;
  static final int MODE_POLYS = 1 ;
  static final int MODE_FILLS = 2 ;
  
  static final int DEPTH_BASE  = 100 ;
  
  static final int MID_DEPTH_0 = DEPTH_BASE ;
  static final int MID_DEPTH_1 = DEPTH_BASE + 1 ;
  static final int MID_DEPTH_2 = DEPTH_BASE + 2 ;
  static final int MID_DEPTH_3 = DEPTH_BASE + 3 ;
  static final int MID_DEPTH_4 = DEPTH_BASE + 4 ;
  static final int MID_DEPTH_5 = DEPTH_BASE + 5 ;
  static final int MID_DEPTH_6 = DEPTH_BASE + 6 ;
  static final int MID_DEPTH_7 = DEPTH_BASE + 7 ;
  static final int MID_DEPTH_8 = DEPTH_BASE + 8 ;
  
  static final int DRAW_BASE      = 200 ;

  static final int MID_DRAW_LINES = DRAW_BASE ;    
  static final int MID_DRAW_POLYS = DRAW_BASE + 1 ;
  static final int MID_DRAW_FILLS = DRAW_BASE + 2 ;
}
