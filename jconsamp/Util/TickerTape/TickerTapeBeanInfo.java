package sdm.util;
import java.beans.* ;
import java.awt.* ;

public class TickerTapeBeanInfo extends SimpleBeanInfo
{
	public Image getIcon(int type)
	{
		return loadImage("TickerTapeIcon.gif") ;
	}
}