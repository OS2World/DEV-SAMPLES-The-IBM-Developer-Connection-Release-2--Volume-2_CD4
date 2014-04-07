/*
 * File: ./ISVAPPSCORBA/ISVAPPSMANAGERHELPER.JAVA
 * From: ISVAPPSMANAGER.IDL
 * Date: Mon Apr 06 12:01:20 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package IsvappsCORBA;
public class IsvappsManagerHelper {
     // It is useless to have instances of this class
     private IsvappsManagerHelper() { }

    public static void write(org.omg.CORBA.portable.OutputStream out, IsvappsCORBA.IsvappsManager that) {
        out.write_Object(that);
    }
    public static IsvappsCORBA.IsvappsManager read(org.omg.CORBA.portable.InputStream in) {
        return IsvappsCORBA.IsvappsManagerHelper.narrow(in.read_Object());
    }
   public static IsvappsCORBA.IsvappsManager extract(org.omg.CORBA.Any a) {
     org.omg.CORBA.portable.InputStream in = a.create_input_stream();
     return read(in);
   }
   public static void insert(org.omg.CORBA.Any a, IsvappsCORBA.IsvappsManager that) {
     org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
     write(out, that);
     a.read_value(out.create_input_stream(), type());
   }
   private static org.omg.CORBA.TypeCode _tc;
   synchronized public static org.omg.CORBA.TypeCode type() {
          if (_tc == null)
             _tc = org.omg.CORBA.ORB.init().create_interface_tc(id(), "IsvappsManager");
      return _tc;
   }
   public static String id() {
       return "IDL:IsvappsCORBA/IsvappsManager:1.0";
   }
   public static IsvappsCORBA.IsvappsManager narrow(org.omg.CORBA.Object that)
	    throws org.omg.CORBA.BAD_PARAM {
        if (that == null)
            return null;
        if (that instanceof IsvappsCORBA.IsvappsManager)
            return (IsvappsCORBA.IsvappsManager) that;
	if (!that._is_a(id())) {
	    throw new org.omg.CORBA.BAD_PARAM();
	}
        org.omg.CORBA.portable.Delegate dup = ((org.omg.CORBA.portable.ObjectImpl)that)._get_delegate();
        IsvappsCORBA.IsvappsManager result = new IsvappsCORBA._IsvappsManagerStub(dup);
        return result;
   }
}
