//------------------------------------------------------------------------
// MyThread
//
// Simple example of creating and executing multiple threads of execution.
//
//------------------------------------------------------------------------
class MyThread extends Thread
{
  String txt ;
  int    delay ;

  MyThread( String txt_out, int delay_time )
  {
    txt   = txt_out ;
    delay = delay_time ;
  }

  //---------------------------------------------------------------
  // When the start() method is called this method is automatically
  // run. When this method returns, the thread will end. The Java
  // compiler requires that we handle the InterruptedException
  // exception. The code will not compile otherwise.
  //---------------------------------------------------------------
  public void run()
  {
    try
    {
      for ( int i=0; i<100; i++ )
      {
        System.out.println( txt ) ;
        sleep( delay ) ;
      }
    }
    catch( InterruptedException e )
    {
      return ;
    }
  }

  public static void main ( String[] args )
  {
    new MyThread( "Thread-1", 50).start() ;
    new MyThread( "Thread-2", 100).start() ;
  }
}
