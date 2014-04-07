package sdm.util ;

/*

(c) Copyright IBM Corp. 1997  All rights reserved.

This sample program is owned by International Business Machines
Corporation or one of its subsidiaries ("IBM") and is copyrighted
and licensed, not sold.

You may copy, modiy, and distribute this sample program in any
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
a result of using, modiying or distributing the sample program or
its derivatives.

Each copy of any portion of this sample program or any derivative
work,  must include a the above copyright notice and disclaimer of
warranty.

*/


import java.awt.* ;
import java.applet.* ;
import java.awt.event.* ;
import java.net.* ;

public class TickerTape extends java.awt.Canvas
			implements Runnable, MouseListener
{
	public TickerTape()
	{
		canvasRect = new Rectangle(0,0,250,50) ;
		stringRect = new Rectangle(0,0,0,0) ;
		clipRect = new Rectangle(0,0,0,0) ;
		background = new Color(180,180,180) ; // light gray (0,0,255) ; //: Blue
		foreground = new Color(0,0,0) ; // black (255,255,0) ;  //: Yellow
		setBackground(background) ;
		setForeground(foreground) ;
		tickerText = " " ;
		tickerFont = new Font("Helv", Font.PLAIN, 12) ;
		setFont(tickerFont) ;
	      fontMetrics = getFontMetrics(tickerFont) ;
		delayRate = 300 ;
		addMouseListener(this) ;
		increment = 1 ;
	}


	public TickerTape(String aString)
	{
		canvasRect = new Rectangle(0,0,200,50) ;
		stringRect = new Rectangle(0,0,0,0) ;
		clipRect = new Rectangle(0,0,0,0) ;
		background = new Color(180,180,180) ; // light gray(0,0,255) ; //: Blue
		foreground = new Color(0,0,0) ; // black (255,255,0) ;  //: Yellow
		setBackground(background) ;
		setForeground(foreground) ;
		tickerText = aString ;
		tickerFont = new Font("Helv", Font.BOLD, 12) ;
		setFont(tickerFont) ;
	      fontMetrics = getFontMetrics(tickerFont) ;
		delayRate = 300 ;
		addMouseListener(this) ;
		increment = 1 ;
	}

	public TickerTape(String aString, Font aFont, int aRate)
	{
		canvasRect = new Rectangle(0,0,0,0) ;
		stringRect = new Rectangle(0,0,0,0) ;
		clipRect = new Rectangle(0,0,0,0) ;
		background = new Color(180,180,180) ; //: light gray (0,0,255) ; //: Blue
		foreground = new Color(0,0,0) ; //: black (255,255,0) ;  //: Yellow
		setBackground(background) ;
		setForeground(foreground) ;
		tickerText = aString ;
		tickerFont = aFont ;
		setFont(aFont) ;
	      fontMetrics = getFontMetrics(aFont) ;
		delayRate = aRate ;
		addMouseListener(this) ;
		increment = 1 ;
	}

	public void start()
	{
		if(runner == null)
		{
			runner = new Thread(this) ;
			runner.start() ;
		}
	}

	public void stop()
	{
		if(runner != null)
		{
			runner.stop() ;
			runner = null ;
		}

	}

	
	public void mouseEntered(MouseEvent anEvent)
	{
		Container parent = (Container)getParent() ;
		if(parent instanceof Applet)
		{
		   Applet applet = (Applet)parent ; 
		   String str = applet.getParameter("HTTP") ;
		   if(flyOverText != null)
		   {
			applet.getAppletContext().showStatus(flyOverText) ;
		   }
		   else if(str != null)
		   {
		      applet.getAppletContext().showStatus(applet.getParameter("HTTP")) ;
		   }
		}
	}

	public void mouseExited(MouseEvent anEvent)
	{
		Container parent = (Container)getParent() ;
		if(parent instanceof Applet)
		{
		   Applet applet = (Applet)parent ; 
		   applet.getAppletContext().showStatus(" ") ;
		}

	}

	public void mouseClicked(MouseEvent anEvent) 
	{
	}

	public void mousePressed(MouseEvent anEvent)
	{
		Container parent = (Container)getParent() ;
		if(parent instanceof Applet)
		{
		   Applet applet = (Applet)parent ; 
		   String str = applet.getParameter("HTTP") ;
		   if(flyOverText != null)
		   {
			try
			{
			   URL url = new URL(flyOverText) ;
			   applet.getAppletContext().showDocument(url) ;

			}
			catch(MalformedURLException exception)
			{
			   applet.getAppletContext().showStatus("Malformed URL Exception thrown...") ;
			}
		   }
		   else if(str != null)
		   {
				try
			{
			   URL url = new URL(str) ;
			   applet.getAppletContext().showDocument(url) ;
			}
			catch(MalformedURLException exception)
			{
			   applet.getAppletContext().showStatus("Malformed URL Exception thrown...") ;
			}

		     
		   }
		}

	}

	public void mouseReleased(MouseEvent anEvent)
	{
	}	





	public void run( )
	{
		while(true)
		{
			try
			{
		  		prevX = nextX ;
		  		if(nextX + stringRect.width <= 0)
	  	  		{
	   		  		nextX = canvasRect.width - increment ;
					clipRect.x = 0 ;
					clipRect.width = increment ; // canvasRect.width ;
		  		}
		  		else
		  		{
			  		nextX -= increment ;
					if(nextX > 0)
					      clipRect.x = nextX ;
					else
						clipRect.x = 0 ;

					if(canvasRect.width - nextX >= stringRect.width)
						clipRect.width = stringRect.width + increment + 2 /* for Italic-Bold */ ;
					else 
						clipRect.width = canvasRect.width - nextX + increment + 2 /* for Italic-Bold */ ;
		  		}
		  		repaint()  ;
				Thread.sleep(delayRate) ;
			}
			catch(InterruptedException excpt) {}
		}
		
	}

	public void update(Graphics g)
	{
		if(clearFieldFirst == true)
		{
			clearFieldFirst = false ;
			g.setColor(background) ;
			g.clearRect(0,0, canvasRect.width, canvasRect.height) ;
		}
		g.clipRect(clipRect.x,clipRect.y, clipRect.width, clipRect.height) ;
		paint(g) ;
	}  

	public void paint(Graphics g)
	{
		g.setColor(background) ;
		g.drawString(tickerText, prevX, baseline) ;
		g.setColor(foreground) ;
		g.drawString(tickerText, nextX, baseline) ;
	}

	public void setBackground(Color color)
	{
		super.setBackground(color) ;
		background = color ;
	}

	public Color getBackground()
	{
		return background ;
	}

	public void setForeground(Color color)
	{
		super.setForeground(color) ;
		foreground = color ;
	}

	public Color getForeground()
	{
		return foreground ;
	}

	public void setFont(Font font) 
	{
		super.setFont(font) ;
		tickerFont = font ;
		fontMetrics = getFontMetrics(tickerFont) ;
		calculateRectangles() ;
	}

	public Font getFont()
	{
		return(super.getFont()) ;
	}

	public void setTickerText(String string)
	{
		clearFieldFirst = true ;
		tickerText = string ;
		calculateRectangles() ;
	}

	public String getTickerText()
	{
		return tickerText ;
	}


	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x,y,width,height) ;
		canvasRect.x = x ;
		canvasRect.y = y ;
		canvasRect.width = width ;
		canvasRect.height = height ; 
		calculateRectangles() ;  
	}

	public void setBounds(Rectangle rect)
	{
		super.setBounds(rect) ;
		canvasRect.x = rect.x ;
		canvasRect.y = rect.y ;
		canvasRect.width = rect.width ;
		canvasRect.height = rect.height ; 
		calculateRectangles() ;  
	}


	public Rectangle getBounds()
	{
		return(super.getBounds()) ;
	}

	public void setDelayRate(int rate)
	{
		delayRate = rate ;
	}

	public int getDelayRate()
	{
		return delayRate ;
	}

	public void setFlyOverText(String string)
	{
		flyOverText = string ;
	}

	public String getFlyOverText()
	{
		return flyOverText ;
	}

	public void setIncrement(int inc)
	{
		increment = inc ;
	}

	public int getIncrement()
	{
		return increment ;
	}

	public java.awt.Dimension getPreferredSize()
	{
		return(new java.awt.Dimension(canvasRect.width,canvasRect.height)) ;
	}

	public java.awt.Dimension getMinimumSize()
	{
		return(getPreferredSize()) ;
	}



	private void calculateRectangles()
	{
		charHeight = fontMetrics.getHeight() ;
		int maxAscent = fontMetrics.getMaxAscent() ;
		nextX = prevX = canvasRect.width ; 
		baseline = (canvasRect.height / 2) + (charHeight / 4) ; 
		
		stringRect.width = fontMetrics.stringWidth(tickerText) ;
		stringRect.height = charHeight ; 
     
		clipRect.x = nextX ;
		clipRect.y = baseline - fontMetrics.getMaxAscent() ; 
		clipRect.width = increment ;
		clipRect.height = charHeight  ;
	}

	
	private String tickerText, flyOverText ;
	private boolean clearFieldFirst = false ;
	private int charHeight  ;
	private int prevX, nextX, baseline ;
	private Rectangle canvasRect, stringRect, clipRect ;
	private int delayRate ;
	private Color background, foreground ;
	private Font tickerFont ;
	private FontMetrics fontMetrics ;
	private Thread runner ;
	private int increment ;
}
