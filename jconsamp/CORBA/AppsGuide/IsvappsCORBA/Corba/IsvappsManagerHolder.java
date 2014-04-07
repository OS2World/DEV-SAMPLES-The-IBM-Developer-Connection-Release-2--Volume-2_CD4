/*
 * File: ./ISVAPPSCORBA/ISVAPPSMANAGERHOLDER.JAVA
 * From: ISVAPPSMANAGER.IDL
 * Date: Mon Apr 06 12:01:20 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package IsvappsCORBA;
public final class IsvappsManagerHolder
     implements org.omg.CORBA.portable.Streamable{
    //	instance variable 
    public IsvappsCORBA.IsvappsManager value;
    //	constructors 
    public IsvappsManagerHolder() {
	this(null);
    }
    public IsvappsManagerHolder(IsvappsCORBA.IsvappsManager __arg) {
	value = __arg;
    }

    public void _write(org.omg.CORBA.portable.OutputStream out) {
        IsvappsCORBA.IsvappsManagerHelper.write(out, value);
    }

    public void _read(org.omg.CORBA.portable.InputStream in) {
        value = IsvappsCORBA.IsvappsManagerHelper.read(in);
    }

    public org.omg.CORBA.TypeCode _type() {
        return IsvappsCORBA.IsvappsManagerHelper.type();
    }
}
