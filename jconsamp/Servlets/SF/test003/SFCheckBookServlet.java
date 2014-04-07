//
// IBM grants you a nonexclusive license to use this as an example
// from which you can generate similar function tailored to
// your own specific needs. This sample is provided in the form of source
// material which you may change and use. If you change the source, it is
// recommended that you first copy the source to a user directory.
// This will ensure that your changes are preserved if the contents
// are changed by IBM in the future.
//
//
// This sample code is provided by IBM for illustrative purposes only.
// These examples have not been thoroughly tested under all conditions.
// IBM, therefore, cannot guarantee or imply reliability, serviceability,
// or function of these programs. All programs contained herein are
// provided to you "AS IS" without any warranties of any kind.
// THE IMPLIED WARRANTIES OF MERCHANTABILITY
// AND FITNESS FOR A PARTICULAR PURPOSE ARE EXPRESSLY DISCLAIMED.
//
//
// 5648-SF1
// (C) Copyright IBM Corp. 1996,1997
// All rights reserved.
// US Government Users Restricted Rights -
// Use, duplication, or disclosure restricted
// by GSA ADP Schedule Contract with IBM Corp.
//
// Licensed Materials - Property of IBM
//
// Modified by Mickey Nix (IBM--SDM, Austin), April 5, 1998
// Split code into Servlet/Applet
// 



import java.io.IOException ;
import java.io.PrintStream ;
import java.io.PrintWriter ;

import java.util.Enumeration ;
import java.util.Hashtable ;

import javax.servlet.ServletConfig ;
import javax.servlet.ServletContext ;
import javax.servlet.ServletRequest ;
import javax.servlet.ServletResponse ;
import javax.servlet.ServletInputStream ;
import javax.servlet.ServletOutputStream ;
import javax.servlet.ServletException ;

import javax.servlet.http.HttpServlet ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import javax.servlet.http.HttpUtils ;
import javax.servlet.http.HttpSession ;
import com.ibm.ServletExpress.personalization.sessiontracking.IBMSessionData ;

import COM.ibm.sf.samples.pgmmodel.CheckBook.* ;
import  COM.ibm.sf.gf.*;


public class SFCheckBookServlet extends javax.servlet.http.HttpServlet
{
	private ServletContext context ;
	private Person person = null ;
	private CheckBook checkBook = null ;
	private AccessMode accessMode = null ;
	private int existance;
	private boolean accountExists;
	private COM.ibm.sf.gf.DistributedProcess distributedProcess ;
	private COM.ibm.sf.gf.Control  control ;
	private Hashtable hashTable ;
	private Integer iSessionNumber ;
	


	public void init( ServletConfig config ) 
	{
		try {	
			super.init( config ) ; //:  Must call if overriding init( ServletConfig ) ;
			iSessionNumber = new Integer(0) ;
			hashTable = new Hashtable() ;
			context = getServletContext( ) ;
			//:  ServletContext::log writes to X:\ServletExpress\logs\ServletExpress\servletservice\event_log
			//: context.log( "SFCheckBookServlet's init( ServletConfig ) method invoked.\n" ) ;
			Global.initialize() ;
			try { 
				distributedProcess = Global.factory().getDP() ;
				control = Global.factory().suspend() ;
				//: context.log(" distributedProcess is " + distributedProcess.toString()) ;
			} catch(SmWorkAreaNotActiveException smException) {
				//: context.log("no dp associated...") ;
				try {
					Global.factory().createWorkArea(distributedProcess) ;
				}catch(Exception exception) {
					context.log("createWorkArea exception...") ;
					context.log(exception, exception.toString()) ;
				}
			
			}
			catch(Exception exception) {
				context.log("Unknown exception on getDP()") ;
				context.log(exception, exception.toString()) ;
			}
			
		} catch( Exception exception ) {
			context.log( exception, exception.toString( ) ) ;
		}
	}


	public void destroy()
    	{
	   try {
                //: context.log("SFCheckBookServlet's destroy() has been called.\n");
		Global.uninitialize() ;
            } catch(Exception exception) {
                context.log(exception, exception.toString()) ;
	    }
    	} // end of destroy()

	



	//:  Performs the HTTP GET operation; the default implementation 
	//:	reports an HTTP BAD_REQUEST error.
	protected void doGet(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType( "text/html" ) ;

		PrintWriter print = response.getWriter( ) ;
		
		String query = request.getQueryString() ;
		context.log(query) ;

		String[] action = request.getParameterValues("ACTION") ;
		String[] userName = request.getParameterValues("USERNAME") ;
		String[] iCheckBook = request.getParameterValues("CHECKBOOK") ;
		String[] workType = request.getParameterValues("WORKTYPE") ;
		String[] existType = request.getParameterValues("EXISTTYPE") ;
		//: context.log(action + ":" + userName + ":" + iCheckBook + ":" + workType + ":" + existType) ;
		

	  	//: *** can't use because Control is not serializable *** IBMSessionData session = (IBMSessionData) request.getSession(true);
		//: *** can't use because Control is not serializable *** control = (Control)session.getValue(new String("session")) ;
		//: *** BUMMER!
		String[] sessionNumber = request.getParameterValues("SESSIONNUMBER") ;
		//: context.log("SessionNumber = " + sessionNumber[0] ) ;
		control = (Control)hashTable.get(sessionNumber[0]) ;

		if(control == null)
		{
			try {	
				int num = iSessionNumber.intValue() ;
				if(num >= Integer.MAX_VALUE ) 
					num = 1 ;
				iSessionNumber = null ;
				iSessionNumber = new Integer(num + 1) ;
				sessionNumber[0] = iSessionNumber.toString() ;
				//: context.log("new session number is " + sessionNumber[0] ) ;
				print.println("sessionnumber&" + sessionNumber[0]) ;
				print.flush() ;
				
				//: context.log("control == null") ;
				Global.factory().createWorkArea(distributedProcess) ;
				//: context.log("created new workarea") ;
			} catch(Exception exception) {
				context.log(exception, "001") ;
			}
			

		} //: end if
		else
		{
			try {
				//: context.log("resuming control...") ;
				Global.factory().resume(control) ;
			} catch(Exception exception) {
				context.log(exception, "002") ;
			}
		} //: end else

	
		
		
	
	
		if(action[0].equals("submit"))
		{
			/*  Start a transaction     */

			
			
			

    			try{
				
      				if (Global.factory().getTransactionStatus() != Status.ACTIVE){
					try  {
		    				Global.factory().begin();
					}
					catch (Exception t1)  {
						try {Global.uninitialize() ;} catch(Exception e){}
		  				context.log("Can't begin the transaction.");
          					print.println("first&serverNotStarted") ;
						print.println("quit&") ;
						print.flush() ;
          					return;
					}
      				}
    			} catch (Exception t1) {
				context.log("Can't check state of the transaction.");
				try {Global.uninitialize() ;} catch(Exception e){ } ;
        			print.println("first&serverNotStarted") ;
				print.println("quit&") ;
				print.flush() ;
        			return;
    			}  



			/*   Find access mode  */
        		try{
            			accessMode = AccessMode.createNormal(); // default to createNormal
        		} catch (GFException ge) {
            			context.log("Can't create Normal accessMode.");
        		}

		
  			if (workType[0].compareTo("Local")==0){
  		  		try{
              				accessMode = AccessMode.create(LockMode.OPTIMISTIC,
                    		 	     				AccessLocation.LOCAL,
                                     					CompatibilityMode.CONTAINER,
                                     					LockMode.OPTIMISTIC,
                                     					AccessLocation.LOCAL,
                                     					CompatibilityMode.CONTAINER,
                                     					(long)0,
		                               				99,
                                     					Timeout.DEFAULT_WAIT);
          			} catch (GFException ge) {
              			context.log("Can't create local accessMode.");
	    			}
			} else {
		  			try{
            					accessMode = AccessMode.createCritical();
          				}catch (GFException ge) {
         					context.log("Can't create home accessMode.");
            					ge.printStackTrace();
          				}
        		}

			/*   Find out if new account  */
		
  			if (existType[0].compareTo("Yes")==0) {          //Then a new account
            			existance = 1;;
			} else {                                      //then an old account
            			existance = 0;
        		}

       			//: context.log("Get all the info!");
       			print.println("second&") ;
			print.flush() ;

			/****** Have all info, so now create!  *****/
			/*******************************************/


			// check if it's a previously existing account.....
    			try {
      				accountExists = false;
      				person = (Person)Global.factory().getEntity(userName[0], accessMode);
      				accountExists = true;
    			}
    			catch (COM.ibm.sf.gf.ConfigurationException ce) {
      				accountExists = false;
    			}
    			catch (Exception e1) {
      				context.log("Exception type : "+e1.getClass().getName()+", Message : "+e1.getMessage());
      				e1.printStackTrace();
				try {
					Global.factory().rollback() ;
				} catch(Exception exception) {
					context.log(exception, "006") ;
				}
				return ;
    			}

    			if ((accountExists) && (existance == 0)){            // This is an existing account

      				try {
        				checkBook = (CheckBook)person.getCheckBook();
      				}

      				catch (Exception e2) {
        				context.log("Exception type : "+e2.getClass().getName()+", Message : "+e2.getMessage());
        				e2.printStackTrace();
					try {
						Global.factory().rollback() ;
					} catch(Exception exception) {
						context.log(exception, "007") ;
					}
					return ;
      				}

				print.println("third&") ;
				print.flush() ;

    			}

    			else if ((!accountExists) && (existance == 1)){     // a new account

      			try {
        			//: context.log("Creating account: " + iCheckBook +
                           	//: 	" for User: " + userName + " on a " +
                           	//: 	workType + " Database. Please wait.");

        			person = PersonFactory.createPerson(accessMode,
                                  	Global.factory().getContainer("COM.ibm.sf.samples.pgmmodel.CheckBook.Person"),
                                  	userName[0]);

        			checkBook = CheckBookFactory.createCheckBook(
					person,
                              		accessMode,
                              		iCheckBook[0]);

        			print.println("third&") ;
        			//: context.log("\nAccount successful created.");
				print.flush() ;
      			}
      			catch (Exception e3) {
          			context.log("Exception type : "+e3.getClass().getName()+", Message : "+e3.getMessage());
          			e3.printStackTrace();
          			print.println("first&personAlreadyExisted") ;
				print.println("quit&") ;
				print.flush() ;
				try {
					Global.factory().rollback() ;
				} catch(Exception exception) {
					context.log(exception, "006") ;
				}
          			return;
      			}

    			}

    			else if ((!accountExists) && (existance == 0)){
        			print.println("first&personNotFound") ;
				print.println("quit&") ;
				print.flush() ;
				try {
					Global.factory().rollback() ;
				} catch(Exception exception) {
					context.log(exception, "006") ;
				}
        			return;
    			}

    			else {
				print.println("first&personAlreadyExisted") ;
				print.println("quit&") ;
				print.flush() ;
				try {
					Global.factory().rollback() ;
				} catch(Exception exception) {
					context.log(exception, "006") ;
				}
    			}

        		try {
				//: context.log( person.getName()+", your credit limit is: $"+person.getCreditLimit() );
				//: context.log( "Checkbook balance is: $"+checkBook.getBalance() );
	       			print.println("balance&Current Balance:  $ " + checkBook.getBalance() );
				print.println("quit&") ;
				print.flush() ;
	    		} catch(SFException sfe) {};
	    
		} //: end if "action"



		else if(action[0].equals("apply"))
		{
			try {
				String[] processType = request.getParameterValues("PROCESSTYPE") ;
				String[] amount = request.getParameterValues("FLOAT") ;
				Float fAmount = new Float(amount[0]) ;
				float theFloat = fAmount.floatValue() ;


				if ((processType[0].compareTo("Withdraw")==0) && (theFloat > person.getCreditLimit())) {
					print.println("OOPS1&You went over your limit, you have $ " + person.getCreditLimit() + " as your limit.") ;
					print.println("quit&") ;
					print.flush() ;
        			} else if ((processType[0].compareTo("Withdraw")==0) && (checkBook.getBalance() - theFloat <= 100 )) {
					print.println("OOPS2&You exceeded your minimum balance, you only have $ " + checkBook.getBalance() + " in your account.");
					print.println("quit&") ;
					print.flush() ;
	        		
        			} else {
					try  {
    		    				if (processType[0].compareTo("Deposit")==0)
    		      					checkBook.setBalance(checkBook.getBalance() + theFloat );
        					else
     		      					checkBook.setBalance(checkBook.getBalance() - theFloat );
						print.println("balance&Current Balance:  $ " + checkBook.getBalance() ) ;						
						print.println("quit&") ;
						print.flush() ;

					} catch(Exception exception) {
						print.println("Couldn't set the Balance&") ;
						print.println("quit&") ;
						print.flush() ;
						context.log(exception, exception.toString()) ;
					}
				}
			}catch(Exception exception) {
				context.log(exception, exception.toString()) ;
			}
			
		} //: end if "apply"

		else if(action[0].equals("commit") )
		{
			try{
            			Global.factory().commit();
				//: context.log("Commit was successful!") ;
				print.println("Commit was successful!" );
				print.println("quit&") ;
				print.flush() ;
      			} catch (Exception e) {
				context.log("Commit was unsuccessful.") ;
				context.log(e, "004") ;
            			print.println("Commit was unsuccessful." );
				print.println("quit&") ;
				print.flush() ;
      			}
		} //: end if "commit"

		else if(action[0].equals("rollback"))
		{	

			try{
            			Global.factory().rollback();
				//: context.log("rollback was successful!") ;
            			print.println("rollback was successful!" );
				print.println("quit&") ;
				print.flush() ;
      			} catch (Exception e) {
				context.log("rollback was unsuccessful.") ;
				context.log(e, "005") ;
            			print.println("rollback was unsuccessful." );
				print.println("quit&") ;
				print.flush() ;
      			}
		}

		try {
			//: context.log("leaving doGet(), suspending thread/work area...") ;
			control = Global.factory().suspend() ;
			hashTable.put(iSessionNumber.toString(), control) ;
			//: *** can't use because Control is not serializable *** session.putValue(new String("control"), control) ;
		} catch(Exception exception) {
			context.log(exception, "003") ;
		}

	}  //: end doGet()
	
	

} //: end class
	

  

