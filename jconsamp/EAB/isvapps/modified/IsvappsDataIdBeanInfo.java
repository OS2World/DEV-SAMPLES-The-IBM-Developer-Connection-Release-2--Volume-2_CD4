package Isvapps;

import java.beans.*;
import java.math.*;
import java.sql.*;
import COM.ibm.ivj.eab.data.*;

public class IsvappsDataIdBeanInfo extends SimpleBeanInfo
{
  public IsvappsDataIdBeanInfo() {
    if (!flag) {
      addProperties();
      addMethods();
      initBeanDescriptor();
      flag = true;
    }
  }

  private static void addProperties() {
    try {

      _pDesc[0] = new PropertyDescriptor("product_id", IsvappsDataId.class);
      _pDesc[0].setShortDescription("This attribute is mapped to PRODUCT_ID.");
      _pDesc[0].setDisplayName("product_id");
      _pDesc[0].setBound(true);
      _pDesc[1] = new PropertyDescriptor("product_idNullable", IsvappsDataId.class, "isProduct_idNullable", null ); 

    } catch(Exception e) {}
  }

  private static void addMethods() {
    ParameterDescriptor parms[] = null;
    try {

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aProduct_id");
      parms[0].setName("aProduct_id");

      _mDesc[0] = new MethodDescriptor(IsvappsDataId.class.getMethod("setProduct_id", new Class[] { String.class } ), parms);  
      _mDesc[1] = new MethodDescriptor(IsvappsDataId.class.getMethod("getProduct_id", null), null); 
      _mDesc[2] = new MethodDescriptor(Isvapps.class.getMethod("isProduct_idNullable", null), null); 

    } catch(Exception e) {}
  }


  public PropertyDescriptor[] getPropertyDescriptors() { return _pDesc; }

  public MethodDescriptor[] getMethodDescriptors() { return _mDesc; }

  public BeanInfo[] getAdditionalBeanInfo() {
    BeanInfo ret[] = new BeanInfo[1];
    ret[0] = new PODataIdBeanInfo();
    return ret;
  }

  public java.awt.Image getIcon(int iconKind) {
    if (iconKind == BeanInfo.ICON_COLOR_16x16) {
      java.awt.Image img = loadImage("PODataIdColor16.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_COLOR_32x32) {
      java.awt.Image img = loadImage("PODataIdColor32.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_MONO_16x16) {
      java.awt.Image img = loadImage("PODataIdMono16.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_MONO_32x32) {
      java.awt.Image img = loadImage("PODataIdMono32.gif");
      return img;
    }
    return null;
  }

  public static String getGifFile () {
    return "PODataId.gif";
  }

  public BeanDescriptor getBeanDescriptor() { return _descriptor; }

  private static void initBeanDescriptor() {
    _descriptor.setShortDescription("Data Identifier for the Isvapps class");
  }

  private static BeanDescriptor _descriptor = new BeanDescriptor(IsvappsDataId.class);
  private static PropertyDescriptor _pDesc[] = new PropertyDescriptor[2];
  private static MethodDescriptor _mDesc[] = new MethodDescriptor[3];
  private static boolean flag=false;

} 
