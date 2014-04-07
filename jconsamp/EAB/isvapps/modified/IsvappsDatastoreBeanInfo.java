package Isvapps;

import java.beans.*;
import COM.ibm.ivj.eab.data.*;

public class IsvappsDatastoreBeanInfo extends SimpleBeanInfo
{
  public IsvappsDatastoreBeanInfo() {
    if (!flag) {
      addMethods();
      initBeanDescriptor();
      flag = true;
    }
  }

  public BeanInfo[] getAdditionalBeanInfo() {
    BeanInfo ret[] = new BeanInfo[1];
    ret[0] = new DatastoreJDBCBeanInfo();
    return ret;
  }

  private static void addMethods() {
    try {
      _mDesc[0] = new MethodDescriptor(IsvappsDatastore.class.getMethod("setAsDefaultDatastore", null), null);
      _mDesc[0].setShortDescription(DAResource.getBeanText("SETASDEFDSM_DESC"));
      _mDesc[0].setDisplayName(DAResource.getBeanText("SETASDEFDSM_NAME"));
    } catch(Exception e) {}
  }

  public java.awt.Image getIcon(int iconKind) {
    if (iconKind == BeanInfo.ICON_COLOR_16x16) {
      java.awt.Image img = loadImage("DatastoreJDBCColor16.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_COLOR_32x32) {
      java.awt.Image img = loadImage("DatastoreJDBCColor32.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_MONO_16x16) {
      java.awt.Image img = loadImage("DatastoreJDBCMono16.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_MONO_32x32) {
      java.awt.Image img = loadImage("DatastoreJDBCMono32.gif");
      return img;
    }
    return null;
  }

  public static String getGifFile () {
    return "DatastoreJDBC.gif";
  }

  public MethodDescriptor[] getMethodDescriptors() { return _mDesc; }
  public BeanDescriptor getBeanDescriptor() { return _descriptor; }

  private static void initBeanDescriptor() {
    _descriptor.setShortDescription("Connects by default to datastore: 'jdbc:db2:APPSGUID'");
  }

  private static BeanDescriptor _descriptor = new BeanDescriptor(IsvappsDatastore.class);
  private static MethodDescriptor _mDesc[] = new MethodDescriptor[1];
  private static boolean flag = false;
} 
