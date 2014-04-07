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
// package COM.ibm.sf.samples.pgmmodel.CheckBook;  //: moved to servlet

import  java.io.*;
import  java.util.*;
import  java.awt.*;
import  java.lang.*;
import  java.net.*;
//: import  COM.ibm.sf.gf.*;  //: moved to servlet

public class CheckBookApplet extends java.applet.Applet
{
// Frame used to display the error messages
	ErrorMsgFrame errorFrame;

	URL theEndURL;

	//: Person person;  //: moved to servlet
	//: CheckBook checkBook;  //: moved to servlet
	//: AccessMode accessMode;  //: moved to servlet

	Label balanceLabel;
	Label endLabel;

	TextField nameField;
	TextField checkField;
	TextField amountField;

	String userName;
	String iCheckBook;
	String lockType;
	String sessionnumber = "0" ;
	// String processType ;

	Button submit;
	Button exit;
	Button okay;
	Button apply;
	Button commit;
	Button rollback;

	CheckboxGroup WorkGroup;
	Checkbox local;
	Checkbox remote;
	String workType;

	CheckboxGroup ExistGroup;
	Checkbox yes;
	Checkbox no;
	int existance;
	boolean accountExists;
	String existType;

	CheckboxGroup processGroup;
	Checkbox deposit;
	Checkbox withdraw;
	String processType;

	Panel Cards;
	Panel p1,p11,p12,p13,p14,p15,p16;
	Panel p2,p21,p22,p23,p24,p25,p26,p27;
    Panel p3,p31,p32,p33,p34,p35;
    Panel p4,p41,p42,p43,p44,p45,p46;

    BorderLayout myLayout;
	CardLayout cl;

	public void init() {

 /* create panels with CardLayoutManager for the applet */
        createCards();
	}

	public void destroy() {
		try {
			// URL theUrl = new URL(url) ;
			URL url = new URL("http://ntsrv/servlet/SFCheckBookServlet?ACTION=rollback") ;
			System.out.println("destroy called") ;
			
			InputStream conn = null ;
			DataInputStream data = null ;
			String line ;
			StringBuffer buf = new StringBuffer() ;
		
			conn = url.openStream() ;
			conn.close() ;
		} catch(Exception exception) {
			exception.printStackTrace() ;
		}
	} //: end destroy


	public void stop() {
		try {
			// URL theUrl = new URL(url) ;
			URL url = new URL("http://ntsrv/servlet/SFCheckBookServlet?ACTION=rollback") ;
			System.out.println("destroy called") ;
			
			InputStream conn = null ;
			DataInputStream data = null ;
			String line ;
			StringBuffer buf = new StringBuffer() ;
		
			conn = url.openStream() ;
			conn.close() ;
		}
		catch(Exception exception) {
			exception.printStackTrace() ;
		}		
	} //: end stop



	public void createCards(){

        Cards = new Panel();
        cl = new CardLayout();
        Cards.setLayout(cl);

        p1 = new Panel();
        p2 = new Panel();
        p3 = new Panel();


/*  create the first panel  */
        createCard1();

/*  Create the second panel.   */
        createCard2();

/*  Create the third panel.   */
        createCard3();

/*  Create the fourth panel.   */
        createCard4();

/*  Add panel1, panel2 and panel3  */
        Cards.add(p1,"first");
        Cards.add(p2,"second");
        Cards.add(p3,"third");
        Cards.add(p4,"fourth");
        add(Cards);
    }

//____________________________________________________________________

    public void createCard1(){

        p1.setLayout(new GridLayout(6,1,10,10));
        p11 = new Panel();
        p12 = new Panel();
        p13 = new Panel();
        p14 = new Panel();
        p15 = new Panel();
        p16 = new Panel();

/*  Set up the name, the checkbook, and the Transaction      */
/*  number fields on the screen.  */

        p12.add(new Label("   Cust. Name: "));
		nameField = new TextField(30);
		p12.add(nameField);

        p13.add(new Label("Cust. Account: "));
		checkField = new TextField(30);
		p13.add(checkField);

/*  Add in the buttons for the yes/no options   */
        p14.add(new Label("Is This A New Account?"));
		ExistGroup = new CheckboxGroup();
		yes = new Checkbox("Yes", ExistGroup, false);
		no = new Checkbox("No", ExistGroup, false);
		p14.add(yes);
		p14.add(no);

/*  Add in the buttons for the Local/Remote options. */
		p15.add(new Label("Select Access Mode:"));
		WorkGroup = new CheckboxGroup();
		local = new Checkbox("Local", WorkGroup, false);
		remote = new Checkbox("Remote", WorkGroup, false);
		p15.add(local);
		p15.add(remote);

/*  Add in the two control buttons.                      */
/*  Submit, and Exit.                                  */
		submit = new Button("Submit");
		p16.add(submit);
		exit = new Button("Exit");
		p16.add(exit);

        p1.add(p11);
        p1.add(p12);
        p1.add(p13);
        p1.add(p14);
        p1.add(p15);
        p1.add(p16);
    }

//____________________________________________________________________

    public void createCard2(){

        p2.setLayout(new GridLayout(7,1,10,10));
        p21 = new Panel();
        p22 = new Panel();
        p23 = new Panel();
        p24 = new Panel();
        p25 = new Panel();
        p26 = new Panel();
        p27 = new Panel();

		p24.add(new Label("Creating or Retrieving Account..."));

        p2.add(p21);
        p2.add(p22);
        p2.add(p23);
        p2.add(p24);
        p2.add(p25);
        p2.add(p26);
        p2.add(p27);
    }

    //____________________________________________________________________

    public void createCard3(){


        p3.setLayout(new GridLayout(5,1,10,10));
        p31 = new Panel();
        p32 = new Panel();
        p33 = new Panel();
        p34 = new Panel();
        p35 = new Panel();

        balanceLabel = new Label("                                                                          ");
        p32.add(balanceLabel);
        p33.add(new Label("Enter Trans. Amount: "));
		amountField = new TextField(10);
		p33.add(amountField);


        p34.add(new Label("Select Deposit or Withdraw: "));
		processGroup = new CheckboxGroup();
		deposit = new Checkbox("Deposit", processGroup, false);
		withdraw = new Checkbox("Withdraw", processGroup, false);
		p34.add(deposit);
		p34.add(withdraw);

/*  Add in the two control buttons.                      */
/*  Apply, and OK.                                  */
		apply = new Button("Apply");
		p35.add(apply);
		okay = new Button("OK");
		p35.add(okay);

        p3.add(p31);
        p3.add(p32);
        p3.add(p33);
        p3.add(p34);
        p3.add(p35);

    }

    //____________________________________________________________________

    public void createCard4(){

        p4 = new Panel();
        p4.setLayout(new GridLayout(6,1,10,10));
        p41 = new Panel();
        p42 = new Panel();
        p43 = new Panel();
        p44 = new Panel();
        p45 = new Panel();
        p46 = new Panel();

        p43.add(new Label("    Do you want to commit or rollback? "));
        commit = new Button("Commit");
        p44.add(commit);
        rollback = new Button("Rollback");
        p44.add(rollback);

        p4.add(p41);
        p4.add(p42);
        p4.add(p43);
        p4.add(p44);
        p4.add(p45);
        p4.add(p46);

    }

//____________________________________________________________________

    public void reset(){

        nameField.setText("");
        checkField.setText("");
	    amountField.setText("");
	    balanceLabel.setText("                                            ");
	    ExistGroup.setSelectedCheckbox(null);
	    WorkGroup.setSelectedCheckbox(null);
	    processGroup.setSelectedCheckbox(null);
	}

//____________________________________________________________________

	public boolean action(Event evt, Object arg)
	{
	      if (evt.target instanceof Button) {
			String label = (String)arg;
			if (label.equals("Submit"))  {
			    try{
				     submitPressed();

			    } catch (Exception e){
			  }
			  return true;
		    } else if (label.equals("Exit")) {
		        this.setVisible(false);
  	            System.exit(0);
			} else if (label.equals("Apply")) {
				applyPressed();  
				return true;
			} else if (label.equals("OK")) {
			    cl.show(Cards,"fourth");
    			return true;
			} else if (label.equals("Commit")) {

				commitPressed();  
				reset();
				cl.show(Cards,"first");
    			return true;
			} else if (label.equals("Rollback")) {
				rollbackPressed();
				reset();
				cl.show(Cards,"first");
    			return true;
			}
		  }
		  return false;
	 }


//_____________________________________________________________________

    void submitPressed() {

	//:    Find out name and account number  
	userName = nameField.getText();
	if ( (userName == null) || (userName.length() == 0) ) {
        	cl.show(Cards,"first");
              	invalidName();
              	return;
        }
	else
	{
		userName = userName.replace(' ', '+') ;
	}

		iCheckBook = checkField.getText();

		boolean correctFormat = false;
        try{
            	Integer temp = new Integer(iCheckBook);
            	int intTemp = temp.intValue();
            	if (intTemp>=0)
              		correctFormat = true;
        } finally {
            	if (!correctFormat) {
              		cl.show(Cards,"first");
              		invalidAccountNumber();
              		return;
            }
        }

	workType = ((WorkGroup.getSelectedCheckbox()).getLabel());
	existType = ((ExistGroup.getSelectedCheckbox()).getLabel());

        System.out.println("submit pressed.");
	try {
		// URL theUrl = new URL(url) ;
		URL url = new URL("http://ntsrv/servlet/SFCheckBookServlet?ACTION=submit&USERNAME=" + userName + "&CHECKBOOK=" + iCheckBook + "&WORKTYPE=" + workType + "&EXISTTYPE=" + existType + "&SESSIONNUMBER=" + sessionnumber ) ;
		userName = userName.replace('+', ' ') ;
		System.out.println(url.toString()) ;
			
		InputStream conn = null ;
		DataInputStream data = null ;
		String line ;
		StringBuffer buf = new StringBuffer() ;
		
		conn = url.openStream() ;
		data = new DataInputStream(new BufferedInputStream(conn)) ;
		while(!(line = data.readLine()).equals("quit")) {

			System.out.println(line) ;
			int index = line.indexOf('&') ;
			String response = line.substring(0,index) ;
			System.out.println("Response is " + response) ;


			if(response.equals("sessionnumber"))
			{
				System.out.println("in sessionnumber...") ;
				sessionnumber = line.substring(index + 1) ;
				System.out.println(sessionnumber) ;			    		
			}


			if(response.equals("balance"))
			{
				System.out.println("in balance...") ;
				String balance = line.substring(index + 1) ;
				System.out.println(balance) ;
				balanceLabel.setText(balance) ;
				p3.setVisible(false);
    				p3.setVisible(true);
    				return;
			}

			else if(response.equals("first"))
			{
				String sub = line.substring(index + 1) ;
				System.out.println(sub) ;
				cl.show(Cards,"first");
				if(sub.equals("serverNotStarted"))
					serverNotStarted() ;
				if(sub.equals("invalidName"))
					invalidName() ;
				if(sub.equals("invalidAccountNumber"))
					invalidAccountNumber() ;
				if(sub.equals("personAlreadyExisted"))
					personAlreadyExisted() ;
				if(sub.equals("personNotFound"))
					personNotFound() ;
				return ;
			}

			else if(response.equals("second"))
			{
				System.out.println("in second...") ;
				cl.show(Cards, "second") ;
			}

			else if(response.equals("third"))
			{
				cl.show(Cards, "third") ;
			}

		    }
		} catch(Exception exception)
		{
			exception.printStackTrace() ;
		}
	System.out.println("leaving submit...") ;

    }


//________________________________________________________________________

//________________________________________________________________


       

	void applyPressed()  {
	    	float theFloat;
		boolean correctFormat = false;

        	try{
            		Float fAmount = new Float(amountField.getText());
            		theFloat = fAmount.floatValue();
            		correctFormat = true;
        	} finally {
            		if (!correctFormat) {
              			invalidAmount();
              			return;
            		}
        	} //: end try/finally

	    	processType = ((processGroup.getSelectedCheckbox()).getLabel());
	   
      
		System.out.println("apply pressed.");
		try {
			// URL theUrl = new URL(url) ;
			URL url = new URL("http://ntsrv/servlet/SFCheckBookServlet?ACTION=apply&PROCESSTYPE=" + processType + "&FLOAT=" + amountField.getText() + "&SESSIONNUMBER=" + sessionnumber ) ;
			System.out.println(url.toString()) ;

			InputStream conn = null ;
			DataInputStream data = null ;
			String line ;
			StringBuffer buf = new StringBuffer() ;
		
			conn = url.openStream() ;
			data = new DataInputStream(new BufferedInputStream(conn)) ;
	
			while(!(line = data.readLine()).equals("quit")) {
				System.out.println(line) ;
				int index = line.indexOf('&') ;
				String response = line.substring(0,index) ;
				System.out.println("Response is " + response) ;
				if(response.equals("OOPS1"))
				{
					String message = line.substring(index + 1) ;
					System.out.println(message) ;
					ErrorMsgFrame firstLimitFrame = new ErrorMsgFrame("OOOPS");
	        			firstLimitFrame.setSize(400,120);
        				firstLimitFrame.setTheMessage(message);
        				firstLimitFrame.addComponents();
       					firstLimitFrame.setVisible(true);
				}

				if(response.equals("OOPS2"))
				{
					String message = line.substring(index + 1) ;
					System.out.println(message) ;
					ErrorMsgFrame secondLimitFrame = new ErrorMsgFrame("OOOPS");
        				secondLimitFrame.setSize(550,120);
        				secondLimitFrame.setTheMessage(message);
        				secondLimitFrame.addComponents();
       					secondLimitFrame.setVisible(true);
				}

				if(response.equals("balance"))
				{
					String balance = line.substring(index + 1) ;
					System.out.println(balance) ;
					balanceLabel.setText(balance);
       					amountField.setText("");
       					processGroup.setSelectedCheckbox(null);
       					p3.setVisible(false);
       					p3.setVisible(true);
				}
				else
				{
					System.out.println("Unknown response at 515.") ;
				}
			}
	
        } catch (Exception ex) {};

}


//____________________________________________________________________

	void commitPressed() {

		System.out.println("commit pressed.");
		try {
			// URL theUrl = new URL(url) ;
			URL url = new URL("http://ntsrv/servlet/SFCheckBookServlet?ACTION=commit&SESSIONNUMBER=" + sessionnumber ) ;
			System.out.println(url.toString()) ;

			InputStream conn = null ;
			DataInputStream data = null ;
			String line ;
			StringBuffer buf = new StringBuffer() ;
		
			conn = url.openStream() ;
			data = new DataInputStream(new BufferedInputStream(conn)) ;
	
			while(!(line = data.readLine()).equals("quit")) {

				System.out.println(line) ;
			}
			
		} catch(Exception exception) {
			System.out.println(exception.toString()) ;
		}


	}

      

//____________________________________________________________________________

	void rollbackPressed() {

	System.out.println("rollback pressed.");
		try {
			// URL theUrl = new URL(url) ;
			URL url = new URL("http://ntsrv/servlet/SFCheckBookServlet?ACTION=rollback&SESSIONNUMBER=" + sessionnumber ) ;
			System.out.println(url.toString()) ;

			InputStream conn = null ;
			DataInputStream data = null ;
			String line ;
			StringBuffer buf = new StringBuffer() ;
		
			conn = url.openStream() ;
			data = new DataInputStream(new BufferedInputStream(conn)) ;
			while(!(line = data.readLine()).equals("quit")) {

				System.out.println(line) ;
			}
			
		} catch(Exception exception) {
			System.out.println(exception.toString()) ;
		}

    }


//__________________________________________________________________________

	void invalidName()
	{
		ErrorMsgFrame errorFrame = new ErrorMsgFrame("Error");
		errorFrame.addComponents();
		errorFrame.setSize(650,120);
    	String message = new String("You must enter a account owner name in the name field, the name field can not be empty.");
		errorFrame.setTheMessage(message);
		errorFrame.setVisible(true);
	}
//____________________________________________________________________________


	void invalidAccountNumber()
	{
		ErrorMsgFrame errorFrame = new ErrorMsgFrame("Error");
		errorFrame.addComponents();
		errorFrame.setSize(550,120);
    	String message = new String("You must enter a decimal number from 0 to " + Integer.MAX_VALUE + " as an account number."  );
		errorFrame.setTheMessage(message);
		errorFrame.setVisible(true);
	}

//____________________________________________________________________________


	void invalidAmount()
	{
		ErrorMsgFrame errorFrame = new ErrorMsgFrame("Error");
		errorFrame.addComponents();
		errorFrame.setSize(410,120);
    	String message = new String("You must enter a decimal number as a transaction amount.");
		errorFrame.setTheMessage(message);
		errorFrame.setVisible(true);
	}

//____________________________________________________________________________


	void serverNotStarted()
	{
		ErrorMsgFrame errorFrame = new ErrorMsgFrame("Error");
		errorFrame.addComponents();
		errorFrame.setSize(650,120);
    	String message = new String("Can not check transaction status or start transaction, please make sure servers are started.");
		errorFrame.setTheMessage(message);
		errorFrame.setVisible(true);
	}

//____________________________________________________________________________


	void personNotFound()
	{
		ErrorMsgFrame errorFrame = new ErrorMsgFrame("Error");
		errorFrame.addComponents();
		errorFrame.setSize(500,120);
    	String message = new String("The account for person " + userName + " and account number " + iCheckBook + " was not found. ");
		errorFrame.setTheMessage(message);
		errorFrame.setVisible(true);
	}
//______________________________________________________________

	void personAlreadyExisted()
	{
		ErrorMsgFrame errorFrame = new ErrorMsgFrame("Error");
		errorFrame.addComponents();
		errorFrame.setSize(550,120);
    	String message = new String("The account for person " + userName + " and/or account number " + iCheckBook + " already existed. ");
		errorFrame.setTheMessage(message);
		errorFrame.setVisible(true);
	}

}


/*****************************************************
******     The CheckBook Information Panel.     ******
*****************************************************/

  class ErrorMsgFrame extends Frame {

	String theString;
	Label messageLabel;

	Button okay;

	Font f = new Font("TimesRoman",Font.BOLD,15);


	public ErrorMsgFrame(String title)
	{
		super(title);
		addNotify();
		setLayout(new FlowLayout(FlowLayout.CENTER));
		messageLabel = new Label("This   is   the   text         .");
		setFont(f);

	}

    public boolean handleEvent(Event event) {

    	if (event.id == Event.WINDOW_DESTROY) {
    	    setVisible(false);
    	    dispose();
    	    return true;
    	}
        return super.handleEvent(event);
    }

//____________________________________________________________________

	public boolean action(Event evt, Object arg)
	{
	      if (evt.target instanceof Button) {
			String label = (String)arg;
			if (label.equals(" OK ")) {
			  this.setVisible(false);
			  dispose();
			  return true;
		    }
		  }
		  return false;
	 }

	public void addComponents()
	{
		add(messageLabel);
		okay = new Button(" OK ");
		add(okay);
	}

	public void setTheMessage(String message)
	{
		messageLabel.setText(message);

	}

	public void setTheTitle(String title)
	{
		setTitle(title);
	}

  }


