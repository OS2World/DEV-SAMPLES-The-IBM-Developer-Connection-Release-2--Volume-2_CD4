/*
 * File: ./ISVAPPSCORBA/ISVAPPSMANAGER.JAVA
 * From: ISVAPPSMANAGER.IDL
 * Date: Mon Apr 06 12:01:20 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package IsvappsCORBA;
public interface IsvappsManager
    extends org.omg.CORBA.Object {
    String getProducts()
;
    String getProduct(String product_id)
;
}
