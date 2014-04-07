//-----------------------------------------------------------------------------
// Spiro2.java
//
// A simple example of using our Matrix class to draw transformed shapes.
//
// This version requires Java JDK level 1.1.0 or higher.
//
// Demonstrates use of these classes/interfaces:
//
//   Graphics
//
// Author : Kelvin R Lawrence.     27th-January-1998
//
// History:
//
//-----------------------------------------------------------------------------
import java.awt.* ;
import java.awt.event.* ;
import java.util.* ;

//---------------------------------------------------------------
// Class: Spiro2
//
// Simple class that experiments with
//
//---------------------------------------------------------------
public class Spiro2 extends Frame implements ItemListener
{
  private MenuBar          menuBar     ;
  private Menu             mnuOptions  ;
  private CheckboxMenuItem mitRotate   ;
  private CheckboxMenuItem mitScale    ;
  private CheckboxMenuItem mitXlate    ;
  private CheckboxMenuItem mitReflectX ;
  private CheckboxMenuItem mitReflectY ;

  private CheckboxMenuItem mitCurrent ;

  private int mode ;

  private static final int MODE_ROTATE   = 1 ;
  private static final int MODE_SCALE    = 2 ;
  private static final int MODE_XLATE    = 3 ;
  private static final int MODE_REFLECTX = 4 ;
  private static final int MODE_REFLECTY = 5 ;

  //----------------------------------------------------------
  // Constructor
  //
  // Initialise key objects and create our menu bar.
  //
  //----------------------------------------------------------
  public Spiro2( String s )
  {
    super(s);

    enableEvents( AWTEvent.WINDOW_EVENT_MASK ) ;

    mnuOptions = new Menu( "Options" ) ;

    mitRotate   =  new CheckboxMenuItem( "Rotation"      ) ;
    mitScale    =  new CheckboxMenuItem( "Scaling"       ) ;
    mitXlate    =  new CheckboxMenuItem( "Translation"   ) ;
    mitReflectX =  new CheckboxMenuItem( "Reflection X"  ) ;
    mitReflectY =  new CheckboxMenuItem( "Reflection Y"  ) ;

    mnuOptions.add( mitRotate   ) ;
    mnuOptions.add( mitScale    ) ;
    mnuOptions.add( mitXlate    ) ;
    mnuOptions.add( mitReflectX ) ;
    mnuOptions.add( mitReflectY ) ;

    mitRotate.addItemListener( this ) ;
    mitScale.addItemListener( this ) ;
    mitXlate.addItemListener( this ) ;
    mitReflectX.addItemListener( this ) ;
    mitReflectY.addItemListener( this ) ;

    menuBar = new MenuBar() ;
    menuBar.add( mnuOptions ) ;

    mode = MODE_ROTATE ;
    mitCurrent = mitRotate ;
    mitCurrent.setState( true ) ;

    this.setMenuBar( menuBar ) ;
  }

  //-------------------------------------------------------------------------
  // paint()
  //
  //-------------------------------------------------------------------------
  public void paint( Graphics g )
  {
    Dimension d = getSize() ;

    g.setColor( Color.black ) ;

    g.fillRect( 0, 0, d.width, d.height ) ;

    switch ( mode )
    {
      case MODE_ROTATE:
      {
        doRotationTests( g ) ;
      }
      break ;

      case MODE_SCALE:
      {
        doScalingTests( g ) ;
      }
      break ;

      case MODE_XLATE:
      {
        //doTranslationTests( g ) ;
      }
      break ;

      case MODE_REFLECTX:
      case MODE_REFLECTY:
      {
        doReflectionTests( g, mode ) ;
      }
      break ;
    }
  }

  //-------------------------------------------------------------------------
  // doRotationtests()
  //
  // Test the rotation functions provided by the Matrix class.
  //
  //-------------------------------------------------------------------------
  private void doRotationTests( Graphics g )
  {
    Matrix m = new Matrix() ;

    Rectangle rectl = new Rectangle() ;
    calculateClientRect( rectl ) ;

    int [] x = new int[5] ;
    int [] y = new int[5] ;

    int xdelta = ( rectl.width  / 3 ) - 10 ;
    int ydelta = ( rectl.height / 3 ) - 10 ;
    int xstart = rectl.x + rectl.width  /2 ;
    int ystart = rectl.y + rectl.height /2 ;

    x[0] = xstart        ; y[0] = ystart ;
    x[1] = x[0] + xdelta ; y[1] = y[0] ;
    x[2] = x[1]          ; y[2] = y[1] + ydelta ;
    x[3] = x[0]          ; y[3] = y[2] ;
    x[4] = x[0]          ; y[4] = y[0] ;

    //--------------------------------------
    // Uncomment the next two lines to
    // display the initial unrotated polygon
    //--------------------------------------
    //g.setColor( Color.green ) ;
    //g.drawPolygon( x, y, 5 ) ;

    m.setRotation( x[0], y[0], 5 ) ;

    //m.dumpToConsole();

    g.setColor( Color.cyan) ;

    for ( int a=0; a<360; a+=5 )
    {
      m.transformPoints( x,y ) ;

      //----------------------------------------------------
      //Dump out contents of the array after transformation.
      //----------------------------------------------------
      //
      //dumpArrays( x,y ) ;

      g.drawPolygon( x, y, 5 ) ;
    }
  }
  //-------------------------------------------------------------------------
  // doScalingTests()
  //
  // Test the scaling functions provided by the Matrix class.
  //
  //-------------------------------------------------------------------------
  private void doScalingTests( Graphics g )
  {
    Matrix m = new Matrix() ;

    int [] x = new int[5] ;
    int [] y = new int[5] ;

    Rectangle rectl = new Rectangle() ;
    calculateClientRect( rectl ) ;

    System.out.println( rectl.toString() ) ;

    x[0] = rectl.x + 10 ;  y[0] = rectl.y + 10 ;
    x[1] = x[0] + 20    ;  y[1] = y[0]         ;
    x[2] = x[1]         ;  y[2] = y[1] + 10    ;
    x[3] = x[0]         ;  y[3] = y[2]         ;
    x[4] = x[0]         ;  y[4] = y[0]         ;

    g.setColor( Color.blue ) ;

    m.setScaling( x[0],y[0],1.5,1.5 ) ;

    for ( int i = 1; i < 10; i++ )
    {
      dumpArrays( x,y ) ;

      m.transformPoints( x, y ) ;
      m.dumpToConsole() ;
      g.drawPolygon( x, y, 5 ) ;

      dumpArrays( x,y ) ;
    }
  }

  //-------------------------------------------------------------------------
  // doReflectionTests()
  //
  //-------------------------------------------------------------------------
  private void doReflectionTests( Graphics g, int mode )
  {
    Matrix m = new Matrix() ;

    int [] x = new int[5] ;
    int [] y = new int[5] ;

    Rectangle rectl = new Rectangle() ;
    calculateClientRect( rectl ) ;

    System.out.println( rectl.toString() ) ;


    x[0] = rectl.x + 10 ;  y[0] = rectl.y + 150 ;
    x[1] = x[0] + 100   ;  y[1] = y[0]          ;
    x[2] = x[1]         ;  y[2] = y[1] + 50     ;
    x[3] = x[0]         ;  y[3] = y[2]          ;
    x[4] = x[0]         ;  y[4] = y[0]          ;

    g.setColor( Color.blue ) ;

    g.drawPolygon( x, y, 5 ) ;

    if ( mode == MODE_REFLECTX )
    {
      m.setXReflection( y[0] );
    }
    else
    {
      m.setYReflection( x[1] ) ;
    }

    dumpArrays( x,y ) ;

    m.transformPoints( x,y ) ;

    dumpArrays( x,y ) ;

    g.setColor( Color.cyan ) ;

    g.drawPolygon( x, y, 5 ) ;
  }


  //-------------------------------------------------------------------------
  // calculateClientRect()
  //
  //-------------------------------------------------------------------------
  private void calculateClientRect( Rectangle rectl )
  {
    Insets ins = this.getInsets() ;

    Dimension dim = this.getSize() ;

    rectl.x      = ins.left ;
    rectl.y      = ins.top  ;
    rectl.width  = dim.width - ins.left  - ins.right ;
    rectl.height = dim.height - ins.top - ins.bottom ;

    System.out.println( "Rectangle calculated" ) ;
    System.out.println( rectl.x + " " + rectl.y + " " + rectl.width + " " + rectl.height ) ;

  }

  //-------------------------------------------------------------------------
  // processEvent()
  //
  // Handle high level events. We need this so that the close in the title bar
  // will actually close the application etc.
  //-------------------------------------------------------------------------
  public void processEvent( AWTEvent evt )
  {
    if ( evt.getID() == WindowEvent.WINDOW_CLOSING )
    {
      System.out.println( "Window about to close..." ) ;
      System.exit(0) ;
    }
    else
    {
      super.processEvent( evt ) ;
    }
  }

  //-------------------------------------------------------------------------
  // actionPerformed()
  //
  // Handle an action for which we have registered interest. In this case
  // process user selections from the menu bar.
  //
  //-------------------------------------------------------------------------
  public void itemStateChanged( ItemEvent ievt )
  {
    System.out.println( "actionPerformed() got called for a menu item." ) ;

    if ( ievt.getSource() instanceof CheckboxMenuItem )
    {
      CheckboxMenuItem mit = (CheckboxMenuItem)ievt.getSource() ;

      mitCurrent.setState( false ) ;
      mitCurrent = mit ;
      mitCurrent.setState( true ) ;

      if ( mit.equals( mitRotate ))
      {
        mode = MODE_ROTATE ;
      }
      else if ( mit.equals( mitScale ))
      {
        mode = MODE_SCALE ;
      }
      else if ( mit.equals( mitReflectX ))
      {
        mode = MODE_REFLECTX ;
      }
      else if ( mit.equals( mitReflectY ))
      {
        mode = MODE_REFLECTY ;
      }

      repaint() ;
    }
  }

  //-------------------------------------------------------------------------
  // dumpArrays()
  //
  // Debug routine to be used to dump integer array contents.
  //-------------------------------------------------------------------------
  static private void dumpArrays( int [] x, int [] y )
  {
    for (int j =0; j< x.length; j++ )
    {
      System.out.println( "x= " + x[j] + " y= " + y[j] ) ;
    }
  }

  //--------------------------------------------------------------------
  // main()
  //
  //--------------------------------------------------------------------
  static public void main( String[] args )
  {
     Spiro2 Spiro2Frame ;

     Spiro2Frame = new Spiro2( "Testcase : Spiro2" ) ;

     Spiro2Frame.setSize( 300,300 ) ;
     Spiro2Frame.show() ;
  }
}
