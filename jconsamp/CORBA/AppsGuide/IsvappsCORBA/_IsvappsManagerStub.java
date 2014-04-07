/*
 * File: ./ISVAPPSCORBA/_ISVAPPSMANAGERSTUB.JAVA
 * From: ISVAPPSMANAGER.IDL
 * Date: Mon Apr 06 12:01:20 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package IsvappsCORBA;
public class _IsvappsManagerStub
	extends org.omg.CORBA.portable.ObjectImpl
    	implements IsvappsCORBA.IsvappsManager {

    public _IsvappsManagerStub(org.omg.CORBA.portable.Delegate d) {
          super();
          _set_delegate(d);
    }

    private static final String _type_ids[] = {
        "IDL:IsvappsCORBA/IsvappsManager:1.0"
    };

    public String[] _ids() { return (String[]) _type_ids.clone(); }

    //	IDL operations
    //	    Implementation of ::IsvappsCORBA::IsvappsManager::getProducts
    public String getProducts()
 {
           org.omg.CORBA.Request r = _request("getProducts");
           r.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
           r.invoke();
           String __result;
           __result = r.return_value().extract_string();
           return __result;
   }
    //	    Implementation of ::IsvappsCORBA::IsvappsManager::getProduct
    public String getProduct(String product_id)
 {
           org.omg.CORBA.Request r = _request("getProduct");
           r.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
           org.omg.CORBA.Any _product_id = r.add_in_arg();
           _product_id.insert_string(product_id);
           r.invoke();
           String __result;
           __result = r.return_value().extract_string();
           return __result;
   }

};
