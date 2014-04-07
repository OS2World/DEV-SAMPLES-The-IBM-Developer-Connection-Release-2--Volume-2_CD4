/*
 * File: ./ISVAPPSCORBA/_ISVAPPSMANAGERIMPLBASE.JAVA
 * From: ISVAPPSMANAGER.IDL
 * Date: Mon Apr 06 12:01:20 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package IsvappsCORBA;
public abstract class _IsvappsManagerImplBase extends org.omg.CORBA.DynamicImplementation implements IsvappsCORBA.IsvappsManager {
    // Constructor
    public _IsvappsManagerImplBase() {
         super();
    }
    // Type strings for this class and its superclases
    private static final String _type_ids[] = {
        "IDL:IsvappsCORBA/IsvappsManager:1.0"
    };

    public String[] _ids() { return (String[]) _type_ids.clone(); }

    private static java.util.Dictionary _methods = new java.util.Hashtable();
    static {
      _methods.put("getProducts", new java.lang.Integer(0));
      _methods.put("getProduct", new java.lang.Integer(1));
     }
    // DSI Dispatch call
    public void invoke(org.omg.CORBA.ServerRequest r) {
       switch (((java.lang.Integer) _methods.get(r.op_name())).intValue()) {
           case 0: // IsvappsCORBA.IsvappsManager.getProducts
              {
              org.omg.CORBA.NVList _list = _orb().create_list(0);
              r.params(_list);
              String ___result;
                            ___result = this.getProducts();
              org.omg.CORBA.Any __result = _orb().create_any();
              __result.insert_string(___result);
              r.result(__result);
              }
              break;
           case 1: // IsvappsCORBA.IsvappsManager.getProduct
              {
              org.omg.CORBA.NVList _list = _orb().create_list(0);
              org.omg.CORBA.Any _product_id = _orb().create_any();
              _product_id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
              _list.add_value("product_id", _product_id, org.omg.CORBA.ARG_IN.value);
              r.params(_list);
              String product_id;
              product_id = _product_id.extract_string();
              String ___result;
                            ___result = this.getProduct(product_id);
              org.omg.CORBA.Any __result = _orb().create_any();
              __result.insert_string(___result);
              r.result(__result);
              }
              break;
            default:
              throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
       }
 }
}
