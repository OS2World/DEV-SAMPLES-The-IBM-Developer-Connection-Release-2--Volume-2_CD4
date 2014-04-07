
/*

(c) Copyright IBM Corp. 1997  All rights reserved.

This sample program is owned by International Business Machines
Corporation or one of its subsidiaries ("IBM") and is copyrighted
and licensed, not sold.

You may copy, modify, and distribute this sample program in any
form without payment to IBM,  for any purpose including developing,
using, marketing or distributing programs that include or are
derivative works of the sample program.

The sample program is provided to you on an "AS IS" basis, without
warranty of any kind.  IBM HEREBY  EXPRESSLY DISCLAIMS ALL WARRANTIES,
EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
Some jurisdictions do not allow for the exclusion or limitation of
implied warranties, so the above limitations or exclusions may not
apply to you.  IBM shall not be liable for any damages you suffer as
a result of using, modifying or distributing the sample program or
its derivatives.

Each copy of any portion of this sample program or any derivative
work,  must include a the above copyright notice and disclaimer of
warranty.

*/



import sdm.util.* ;
import java.awt.* ;
import java.applet.* ;

public class MyApplet extends java.applet.Applet
{
	public void init()
	{
		String str = getParameter("TEXT") ;
		if(str == null) str = new String(" ") ;
		fTickerTape = new sdm.util.TickerTape(str) ;
		fTickerTape.setBounds(0,0,200,50) ;
	      fTickerTape.setForeground(Color.red) ;
		fTickerTape.setBackground(Color.black) ;
		Font font = new Font("Courier", Font.BOLD, 48) ;
		fTickerTape.setFont(font) ;
		fTickerTape.setTickerText("Eat at Joes.") ;
		fTickerTape.setFlyOverText("Fly over text") ;
		fTickerTape.setDelayRate(100) ;
		fTickerTape.setIncrement(5) ;
		add(fTickerTape) ;

		t2 = new sdm.util.TickerTape(str, new Font("Helv", Font.ITALIC, 18), 200) ;
		FontMetrics fm = getFontMetrics(t2.getFont()) ;
		t2.setBounds(0,0,fm.stringWidth(str), fm.getHeight() * 3) ;
		resize(fm.stringWidth(str) + 5, 300) ;
		t2.setDelayRate(800) ;
		t2.setIncrement(80) ;
		add(t2) ;

		t3 = new sdm.util.TickerTape() ;
		t3.setForeground(Color.black) ;
		t3.setBackground(Color.lightGray) ;
		t3.setFont(font) ;
		t3.setTickerText("Yet another ticker") ;
		t3.setBounds(0,0,300,50) ;
		t3.setDelayRate(50) ;
		add(t3) ;
		
	}

	public void start()
	{
		fTickerTape.start() ;
		t2.start() ;
		t3.start() ;
	}

	public void stop()
	{
		fTickerTape.stop() ;
		t2.stop() ;
		t3.stop() ;
	}

	
  private sdm.util.TickerTape  fTickerTape, t2,t3 ;
}
