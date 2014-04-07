package Isvapps;

import java.beans.*;
import java.math.*;
import java.sql.*;
import COM.ibm.ivj.eab.data.*;

public class IsvappsManagerBeanInfo extends SimpleBeanInfo
{
  public IsvappsManagerBeanInfo() {
    if (!flag) {
      addProperties();
      addMethods();
      initBeanDescriptor();
      flag = true;
    }
  }

  private static void addProperties() {
    try {
      _pDesc[0] = new PropertyDescriptor("element", IsvappsManager.class, "element", null );
      _pDesc[0].setShortDescription(DAResource.getBeanText("ELEMENT_DESC"));
      _pDesc[0].setDisplayName(DAResource.getBeanText("ELEMENT_NAME")); 
    } catch(Exception e) {}
  }

  private static void addMethods() {
    ParameterDescriptor parms[] = null;
    try {
      if (_mDesc.length == 0) return; 


      _mDesc[0] = new MethodDescriptor(IsvappsManager.class.getMethod("element", null), null);
      _mDesc[0].setShortDescription(DAResource.getBeanText("ELEMENT_DESC"));
      _mDesc[0].setDisplayName(DAResource.getBeanText("ELEMENT_NAME")); 

    } catch(Exception e) {}
  }

  public PropertyDescriptor[] getPropertyDescriptors() { return _pDesc; }
  public MethodDescriptor[] getMethodDescriptors() { return _mDesc; }

  public BeanInfo[] getAdditionalBeanInfo() {
    BeanInfo ret[] = new BeanInfo[1];
    ret[0] = new DAManagerBeanInfo();
    return ret;
  }

  public java.awt.Image getIcon(int iconKind) {
    if (iconKind == BeanInfo.ICON_COLOR_16x16) {
      java.awt.Image img = loadImage("DAManagerColor16.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_COLOR_32x32) {
      java.awt.Image img = loadImage("DAManagerColor32.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_MONO_16x16) {
      java.awt.Image img = loadImage("DAManagerMono16.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_MONO_32x32) {
      java.awt.Image img = loadImage("DAManagerMono32.gif");
      return img;
    }
    return null;
  }

  public static String getGifFile () {
    return "DAManager.gif";
  }

  public BeanDescriptor getBeanDescriptor() { return _descriptor; }

  private static void initBeanDescriptor() {
    _descriptor.setShortDescription("Manager class for Isvapps objects.");
  }

  private static BeanDescriptor _descriptor = new BeanDescriptor(IsvappsManager.class);
  private static PropertyDescriptor _pDesc[] = new PropertyDescriptor[1];
  private static MethodDescriptor _mDesc[] = new MethodDescriptor[1];
  private static boolean flag=false;

} 
