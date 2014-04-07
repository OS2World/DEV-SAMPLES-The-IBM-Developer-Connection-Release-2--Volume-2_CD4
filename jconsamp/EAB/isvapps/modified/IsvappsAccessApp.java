//------------------------------------------------------------------------
// This code is generated by IBM VisualAge for Java Data Access Builder.
// WARNING:  User modifications will be lost on next code generation.
//------------------------------------------------------------------------

package Isvapps;

import java.lang.*;
import java.math.*;
import java.util.*;
import java.beans.*;
import java.sql.*;
import COM.ibm.ivj.eab.data.*;
import java.io.Serializable;

public class IsvappsAccessApp extends DataAccessApp
{
  private IsvappsForm aForm;
  public IsvappsAccessApp()
  {
    super( "Isvapps"
         , new DataAccessAction[]
           {
             new DataAccessAction( "update(dataId)",DAResource.getBeanText("UPDATEID_DESC"),null )
            , new DataAccessAction( "update()",DAResource.getBeanText("UPDATE_DESC"), null )
           }
         , new DataAccessAction[]
           {
             new DataAccessAction( "deleteAllProducts(Integer[])"
                                           , "This method is user defined."
                                           ,  new DataAccessParameter[]
                                              {
                                                 new DataAccessParameter( "numrows"
                                            , Integer.class
                                            , true                                            , 0 )
                                              } )
            , new DataAccessAction( "deleteProduct(String, Integer[])"
                                           , "This method is user defined."
                                           ,  new DataAccessParameter[]
                                              {
                                                 new DataAccessParameter( "product_id"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "numrows"
                                            , Integer.class
                                            , true                                            , 0 )
                                              } )
            , new DataAccessAction( "getProducts(String[])"
                                           , "This method is user defined."
                                           ,  new DataAccessParameter[]
                                              {
                                                 new DataAccessParameter( "productList"
                                            , String.class
                                            , true                                            , 0 )
                                              } )
            , new DataAccessAction( "getProduct(String, String[])"
                                           , "This method is user defined."
                                           ,  new DataAccessParameter[]
                                              {
                                                 new DataAccessParameter( "product_id"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "productInfo"
                                            , String.class
                                            , true                                            , 0 )
                                              } )
            , new DataAccessAction( "insertProduct(String, String, String, String, String, String, Integer[])"
                                           , "This method is user defined."
                                           ,  new DataAccessParameter[]
                                              {
                                                 new DataAccessParameter( "product_id"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "product_name"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "short_name"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "description"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "supplier_id"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "url"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "numrows"
                                            , Integer.class
                                            , true                                            , 0 )
                                              } )
            , new DataAccessAction( "updateProduct(String, String, String, String, String, String, Integer[])"
                                           , "This method is user defined."
                                           ,  new DataAccessParameter[]
                                              {
                                                 new DataAccessParameter( "product_id"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "product_name"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "short_name"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "description"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "supplier_id"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "url"
                                            , String.class
                                            , false                                            , 0 ),
                                       new DataAccessParameter( "numrows"
                                            , Integer.class
                                            , true                                            , 0 )
                                              } )
           }
         , new DataAccessAction[]
           {
             new DataAccessAction( "open(String)"
                                 , DAResource.getBeanText("OPEN_DESC")
                                 , new DataAccessParameter[]
                                   { new DataAccessParameter( "sqlSuffix"
                                                            , java.lang.String.class
                                                            , false ) } )

           }
         , new IsvappsManager(new IsvappsDatastore()) );
  }

  protected Object executeStaticAction( String _methodName, Object[] _params ) throws DAException
  {
    Object _result = null;
    if ( _methodName.equals("deleteAllProducts(Integer[])") ) {
      Isvapps.deleteAllProducts( (Integer[])_params[0] );
    } 
    else if ( _methodName.equals("deleteProduct(String, Integer[])") ) {
      Isvapps.deleteProduct( (String)_params[0], (Integer[])_params[1] );
    } 
    else if ( _methodName.equals("getProducts(String[])") ) {
      Isvapps.getProducts( (String[])_params[0] );
    } 
    else if ( _methodName.equals("getProduct(String, String[])") ) {
      Isvapps.getProduct( (String)_params[0], (String[])_params[1] );
    } 
    else if ( _methodName.equals("insertProduct(String, String, String, String, String, String, Integer[])") ) {
      Isvapps.insertProduct( (String)_params[0], (String)_params[1], (String)_params[2], (String)_params[3], (String)_params[4], (String)_params[5], (Integer[])_params[6] );
    } 
    else if ( _methodName.equals("updateProduct(String, String, String, String, String, String, Integer[])") ) {
      Isvapps.updateProduct( (String)_params[0], (String)_params[1], (String)_params[2], (String)_params[3], (String)_params[4], (String)_params[5], (Integer[])_params[6] );
    } 

    return _result;
  }

  public static void main(String args[]) {
    java.awt.Frame f = new java.awt.Frame("Isvapps");
    IsvappsAccessApp iApp = new IsvappsAccessApp();
    f.addWindowListener(_getMainWindowAdapter());
    f.add("Center", iApp);
    f.pack();
    iApp.init();
    f.setSize(f.getPreferredSize());
    f.show();
  }

  protected DataAccessResultForm getResultForm() { return new IsvappsResultForm(); }
  protected DataAccessObjectForm getObjectForm() { return new IsvappsForm(); }


}

