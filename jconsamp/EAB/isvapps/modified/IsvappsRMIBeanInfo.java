package IsvappsRMI;

import java.beans.*;
import java.math.*;
import java.sql.*;
import COM.ibm.ivj.eab.data.*;

public class IsvappsRMIBeanInfo extends SimpleBeanInfo
{
  public IsvappsRMIBeanInfo() {
    if (!flag) {
      addProperties();
      addMethods();
      initBeanDescriptor();
      flag = true;
    }
  }

  private static void addProperties() {
    try { 
      _pDesc[0] = new PropertyDescriptor("dataId", IsvappsRMI.class);
      _pDesc[0].setShortDescription(DAResource.getBeanText("DATAID_DESC"));
      _pDesc[0].setDisplayName(DAResource.getBeanText("DATAID_NAME"));
      _pDesc[0].setBound(true); 

      _pDesc[1] = new PropertyDescriptor("product_id", IsvappsRMI.class);
      _pDesc[1].setShortDescription("This attribute is mapped to PRODUCT_ID.");
      _pDesc[1].setDisplayName("product_id");
      _pDesc[1].setBound(true);
      _pDesc[2] = new PropertyDescriptor("product_idNullable", IsvappsRMI.class, "isProduct_idNullable", null ); 

      _pDesc[3] = new PropertyDescriptor("product_name", IsvappsRMI.class);
      _pDesc[3].setShortDescription("This attribute is mapped to PRODUCT_NAME");
      _pDesc[3].setDisplayName("product_name");
      _pDesc[3].setBound(true);
      _pDesc[4] = new PropertyDescriptor("product_nameNullable", IsvappsRMI.class, "isProduct_nameNullable", null ); 

      _pDesc[5] = new PropertyDescriptor("short_name", IsvappsRMI.class);
      _pDesc[5].setShortDescription("This attribute is mapped to SHORT_NAME.");
      _pDesc[5].setDisplayName("short_name");
      _pDesc[5].setBound(true);
      _pDesc[6] = new PropertyDescriptor("short_nameNullable", IsvappsRMI.class, "isShort_nameNullable", null ); 

      _pDesc[7] = new PropertyDescriptor("description", IsvappsRMI.class);
      _pDesc[7].setShortDescription("This attribute is mapped to DESCRIPTION.");
      _pDesc[7].setDisplayName("description");
      _pDesc[7].setBound(true);
      _pDesc[8] = new PropertyDescriptor("descriptionNullable", IsvappsRMI.class, "isDescriptionNullable", null ); 

      _pDesc[9] = new PropertyDescriptor("classification", IsvappsRMI.class);
      _pDesc[9].setShortDescription("This attribute is mapped to CLASSIFICATI");
      _pDesc[9].setDisplayName("classification");
      _pDesc[9].setBound(true);
      _pDesc[10] = new PropertyDescriptor("classificationNullable", IsvappsRMI.class, "isClassificationNullable", null ); 

      _pDesc[11] = new PropertyDescriptor("type", IsvappsRMI.class);
      _pDesc[11].setShortDescription("This attribute is mapped to TYPE.");
      _pDesc[11].setDisplayName("type");
      _pDesc[11].setBound(true);
      _pDesc[12] = new PropertyDescriptor("typeNullable", IsvappsRMI.class, "isTypeNullable", null ); 

      _pDesc[13] = new PropertyDescriptor("platforms", IsvappsRMI.class);
      _pDesc[13].setShortDescription("This attribute is mapped to PLATFORMS.");
      _pDesc[13].setDisplayName("platforms");
      _pDesc[13].setBound(true);
      _pDesc[14] = new PropertyDescriptor("platformsNullable", IsvappsRMI.class, "isPlatformsNullable", null ); 

      _pDesc[15] = new PropertyDescriptor("level", IsvappsRMI.class);
      _pDesc[15].setShortDescription("This attribute is mapped to LEVEL.");
      _pDesc[15].setDisplayName("level");
      _pDesc[15].setBound(true);
      _pDesc[16] = new PropertyDescriptor("levelNullable", IsvappsRMI.class, "isLevelNullable", null ); 

      _pDesc[17] = new PropertyDescriptor("file", IsvappsRMI.class);
      _pDesc[17].setShortDescription("This attribute is mapped to FILE.");
      _pDesc[17].setDisplayName("file");
      _pDesc[17].setBound(true);
      _pDesc[18] = new PropertyDescriptor("fileNullable", IsvappsRMI.class, "isFileNullable", null ); 

      _pDesc[19] = new PropertyDescriptor("size", IsvappsRMI.class);
      _pDesc[19].setShortDescription("This attribute is mapped to SIZE.");
      _pDesc[19].setDisplayName("size");
      _pDesc[19].setBound(true);
      _pDesc[20] = new PropertyDescriptor("sizeNullable", IsvappsRMI.class, "isSizeNullable", null ); 

      _pDesc[21] = new PropertyDescriptor("supplier_id", IsvappsRMI.class);
      _pDesc[21].setShortDescription("This attribute is mapped to SUPPLIER_ID.");
      _pDesc[21].setDisplayName("supplier_id");
      _pDesc[21].setBound(true);
      _pDesc[22] = new PropertyDescriptor("supplier_idNullable", IsvappsRMI.class, "isSupplier_idNullable", null ); 

      _pDesc[23] = new PropertyDescriptor("create_date", IsvappsRMI.class);
      _pDesc[23].setShortDescription("This attribute is mapped to CREATE_DATE.");
      _pDesc[23].setDisplayName("create_date");
      _pDesc[23].setBound(true);
      _pDesc[23].setPropertyEditorClass(DateEditor.class); 
      _pDesc[24] = new PropertyDescriptor("create_dateNullable", IsvappsRMI.class, "isCreate_dateNullable", null ); 

      _pDesc[25] = new PropertyDescriptor("url", IsvappsRMI.class);
      _pDesc[25].setShortDescription("This attribute is mapped to URL.");
      _pDesc[25].setDisplayName("url");
      _pDesc[25].setBound(true);
      _pDesc[26] = new PropertyDescriptor("urlNullable", IsvappsRMI.class, "isUrlNullable", null ); 

      _pDesc[27] = new PropertyDescriptor("readme", IsvappsRMI.class);
      _pDesc[27].setShortDescription("This attribute is mapped to README.");
      _pDesc[27].setDisplayName("readme");
      _pDesc[27].setBound(true);
      _pDesc[28] = new PropertyDescriptor("readmeNullable", IsvappsRMI.class, "isReadmeNullable", null ); 

    } catch (Exception e) {}
  }

  private static void addMethods() {
    ParameterDescriptor parms[] = null;
    try {
      if (_mDesc.length == 0) return; 

      // ***update(IsvappsDataId)***
      parms = new ParameterDescriptor[1];

      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aDataId");
      parms[0].setName("arg1");

      _mDesc[0] = new MethodDescriptor(IsvappsRMI.class.getMethod("update",
                                              new Class[] { IsvappsDataId.class } ), parms);
      _mDesc[0].setShortDescription(DAResource.getBeanText("UPDATEID_DESC"));
      _mDesc[0].setDisplayName(DAResource.getBeanText("UPDATEID_NAME")); 


      // ***update()***
      _mDesc[1] = new MethodDescriptor(IsvappsRMI.class.getMethod("update", null), null);
      _mDesc[1].setShortDescription(DAResource.getBeanText("UPDATE_DESC"));
      _mDesc[1].setDisplayName(DAResource.getBeanText("UPDATE_NAME")); 


     // ***deleteAllProducts*** 
      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor(); 
      parms[0].setDisplayName("numrows"); 
      parms[0].setName("arg1"); 
      _mDesc[2] = new MethodDescriptor(IsvappsRMI.class.getMethod("deleteAllProducts",
                                             new Class[] {Integer[].class} ), parms);  
      _mDesc[2].setShortDescription("This method is user defined.");
      _mDesc[2].setDisplayName("deleteAllProducts"); 

     // ***deleteProduct*** 
      parms = new ParameterDescriptor[2];
      parms[0] = new ParameterDescriptor(); 
      parms[0].setDisplayName("product_id"); 
      parms[0].setName("arg1"); 
      parms[1] = new ParameterDescriptor(); 
      parms[1].setDisplayName("numrows"); 
      parms[1].setName("arg2"); 
      _mDesc[3] = new MethodDescriptor(IsvappsRMI.class.getMethod("deleteProduct",
                                             new Class[] {String.class, Integer[].class} ), parms);  
      _mDesc[3].setShortDescription("This method is user defined.");
      _mDesc[3].setDisplayName("deleteProduct"); 

     // ***getProducts*** 
      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor(); 
      parms[0].setDisplayName("productList"); 
      parms[0].setName("arg1"); 
      _mDesc[4] = new MethodDescriptor(IsvappsRMI.class.getMethod("getProducts",
                                             new Class[] {String[].class} ), parms);  
      _mDesc[4].setShortDescription("This method is user defined.");
      _mDesc[4].setDisplayName("getProducts"); 

     // ***getProduct*** 
      parms = new ParameterDescriptor[2];
      parms[0] = new ParameterDescriptor(); 
      parms[0].setDisplayName("product_id"); 
      parms[0].setName("arg1"); 
      parms[1] = new ParameterDescriptor(); 
      parms[1].setDisplayName("productInfo"); 
      parms[1].setName("arg2"); 
      _mDesc[5] = new MethodDescriptor(IsvappsRMI.class.getMethod("getProduct",
                                             new Class[] {String.class, String[].class} ), parms);  
      _mDesc[5].setShortDescription("This method is user defined.");
      _mDesc[5].setDisplayName("getProduct"); 

     // ***insertProduct*** 
      parms = new ParameterDescriptor[7];
      parms[0] = new ParameterDescriptor(); 
      parms[0].setDisplayName("product_id"); 
      parms[0].setName("arg1"); 
      parms[1] = new ParameterDescriptor(); 
      parms[1].setDisplayName("product_name"); 
      parms[1].setName("arg2"); 
      parms[2] = new ParameterDescriptor(); 
      parms[2].setDisplayName("short_name"); 
      parms[2].setName("arg3"); 
      parms[3] = new ParameterDescriptor(); 
      parms[3].setDisplayName("description"); 
      parms[3].setName("arg4"); 
      parms[4] = new ParameterDescriptor(); 
      parms[4].setDisplayName("supplier_id"); 
      parms[4].setName("arg5"); 
      parms[5] = new ParameterDescriptor(); 
      parms[5].setDisplayName("url"); 
      parms[5].setName("arg6"); 
      parms[6] = new ParameterDescriptor(); 
      parms[6].setDisplayName("numrows"); 
      parms[6].setName("arg7"); 
      _mDesc[6] = new MethodDescriptor(IsvappsRMI.class.getMethod("insertProduct",
                                             new Class[] {String.class, String.class, String.class, String.class, String.class, String.class, Integer[].class} ), parms);  
      _mDesc[6].setShortDescription("This method is user defined.");
      _mDesc[6].setDisplayName("insertProduct"); 

     // ***updateProduct*** 
      parms = new ParameterDescriptor[7];
      parms[0] = new ParameterDescriptor(); 
      parms[0].setDisplayName("product_id"); 
      parms[0].setName("arg1"); 
      parms[1] = new ParameterDescriptor(); 
      parms[1].setDisplayName("product_name"); 
      parms[1].setName("arg2"); 
      parms[2] = new ParameterDescriptor(); 
      parms[2].setDisplayName("short_name"); 
      parms[2].setName("arg3"); 
      parms[3] = new ParameterDescriptor(); 
      parms[3].setDisplayName("description"); 
      parms[3].setName("arg4"); 
      parms[4] = new ParameterDescriptor(); 
      parms[4].setDisplayName("supplier_id"); 
      parms[4].setName("arg5"); 
      parms[5] = new ParameterDescriptor(); 
      parms[5].setDisplayName("url"); 
      parms[5].setName("arg6"); 
      parms[6] = new ParameterDescriptor(); 
      parms[6].setDisplayName("numrows"); 
      parms[6].setName("arg7"); 
      _mDesc[7] = new MethodDescriptor(IsvappsRMI.class.getMethod("updateProduct",
                                             new Class[] {String.class, String.class, String.class, String.class, String.class, String.class, Integer[].class} ), parms);  
      _mDesc[7].setShortDescription("This method is user defined.");
      _mDesc[7].setDisplayName("updateProduct"); 

    // add get/set methods 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aProduct_id");
      parms[0].setName("aProduct_id");

      _mDesc[8] = new MethodDescriptor(IsvappsRMI.class.getMethod("setProduct_id", new Class[] { String.class } ), parms);  
      _mDesc[9] = new MethodDescriptor(IsvappsRMI.class.getMethod("getProduct_id", null), null); 
      _mDesc[10] = new MethodDescriptor(IsvappsRMI.class.getMethod("isProduct_idNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aProduct_name");
      parms[0].setName("aProduct_name");

      _mDesc[11] = new MethodDescriptor(IsvappsRMI.class.getMethod("setProduct_name", new Class[] { String.class } ), parms);  
      _mDesc[12] = new MethodDescriptor(IsvappsRMI.class.getMethod("getProduct_name", null), null); 
      _mDesc[13] = new MethodDescriptor(IsvappsRMI.class.getMethod("isProduct_nameNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aShort_name");
      parms[0].setName("aShort_name");

      _mDesc[14] = new MethodDescriptor(IsvappsRMI.class.getMethod("setShort_name", new Class[] { String.class } ), parms);  
      _mDesc[15] = new MethodDescriptor(IsvappsRMI.class.getMethod("getShort_name", null), null); 
      _mDesc[16] = new MethodDescriptor(IsvappsRMI.class.getMethod("isShort_nameNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aDescription");
      parms[0].setName("aDescription");

      _mDesc[17] = new MethodDescriptor(IsvappsRMI.class.getMethod("setDescription", new Class[] { String.class } ), parms);  
      _mDesc[18] = new MethodDescriptor(IsvappsRMI.class.getMethod("getDescription", null), null); 
      _mDesc[19] = new MethodDescriptor(IsvappsRMI.class.getMethod("isDescriptionNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aClassification");
      parms[0].setName("aClassification");

      _mDesc[20] = new MethodDescriptor(IsvappsRMI.class.getMethod("setClassification", new Class[] { String.class } ), parms);  
      _mDesc[21] = new MethodDescriptor(IsvappsRMI.class.getMethod("getClassification", null), null); 
      _mDesc[22] = new MethodDescriptor(IsvappsRMI.class.getMethod("isClassificationNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aType");
      parms[0].setName("aType");

      _mDesc[23] = new MethodDescriptor(IsvappsRMI.class.getMethod("setType", new Class[] { String.class } ), parms);  
      _mDesc[24] = new MethodDescriptor(IsvappsRMI.class.getMethod("getType", null), null); 
      _mDesc[25] = new MethodDescriptor(IsvappsRMI.class.getMethod("isTypeNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aPlatforms");
      parms[0].setName("aPlatforms");

      _mDesc[26] = new MethodDescriptor(IsvappsRMI.class.getMethod("setPlatforms", new Class[] { String.class } ), parms);  
      _mDesc[27] = new MethodDescriptor(IsvappsRMI.class.getMethod("getPlatforms", null), null); 
      _mDesc[28] = new MethodDescriptor(IsvappsRMI.class.getMethod("isPlatformsNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aLevel");
      parms[0].setName("aLevel");

      _mDesc[29] = new MethodDescriptor(IsvappsRMI.class.getMethod("setLevel", new Class[] { String.class } ), parms);  
      _mDesc[30] = new MethodDescriptor(IsvappsRMI.class.getMethod("getLevel", null), null); 
      _mDesc[31] = new MethodDescriptor(IsvappsRMI.class.getMethod("isLevelNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aFile");
      parms[0].setName("aFile");

      _mDesc[32] = new MethodDescriptor(IsvappsRMI.class.getMethod("setFile", new Class[] { byte[].class } ), parms);  
      _mDesc[33] = new MethodDescriptor(IsvappsRMI.class.getMethod("getFile", null), null); 
      _mDesc[34] = new MethodDescriptor(IsvappsRMI.class.getMethod("isFileNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aSize");
      parms[0].setName("aSize");

      _mDesc[35] = new MethodDescriptor(IsvappsRMI.class.getMethod("setSize", new Class[] { Integer.class } ), parms);  
      _mDesc[36] = new MethodDescriptor(IsvappsRMI.class.getMethod("getSize", null), null); 
      _mDesc[37] = new MethodDescriptor(IsvappsRMI.class.getMethod("isSizeNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aSupplier_id");
      parms[0].setName("aSupplier_id");

      _mDesc[38] = new MethodDescriptor(IsvappsRMI.class.getMethod("setSupplier_id", new Class[] { String.class } ), parms);  
      _mDesc[39] = new MethodDescriptor(IsvappsRMI.class.getMethod("getSupplier_id", null), null); 
      _mDesc[40] = new MethodDescriptor(IsvappsRMI.class.getMethod("isSupplier_idNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aCreate_date");
      parms[0].setName("aCreate_date");

      _mDesc[41] = new MethodDescriptor(IsvappsRMI.class.getMethod("setCreate_date", new Class[] { java.sql.Date.class } ), parms);  
      _mDesc[42] = new MethodDescriptor(IsvappsRMI.class.getMethod("getCreate_date", null), null); 
      _mDesc[43] = new MethodDescriptor(IsvappsRMI.class.getMethod("isCreate_dateNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aUrl");
      parms[0].setName("aUrl");

      _mDesc[44] = new MethodDescriptor(IsvappsRMI.class.getMethod("setUrl", new Class[] { String.class } ), parms);  
      _mDesc[45] = new MethodDescriptor(IsvappsRMI.class.getMethod("getUrl", null), null); 
      _mDesc[46] = new MethodDescriptor(IsvappsRMI.class.getMethod("isUrlNullable", null), null); 

      parms = new ParameterDescriptor[1];
      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName("aReadme");
      parms[0].setName("aReadme");

      _mDesc[47] = new MethodDescriptor(IsvappsRMI.class.getMethod("setReadme", new Class[] { String.class } ), parms);  
      _mDesc[48] = new MethodDescriptor(IsvappsRMI.class.getMethod("getReadme", null), null); 
      _mDesc[49] = new MethodDescriptor(IsvappsRMI.class.getMethod("isReadmeNullable", null), null); 

      // qualifier
      parms = new ParameterDescriptor[1];

      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName(DAResource.getBeanText("QUAL_NAME"));
      parms[0].setName("qualifier");

      _mDesc[50] = new MethodDescriptor(IsvappsRMI.class.getMethod("setQualifier", new Class[] { String.class }), parms);
      _mDesc[50].setShortDescription(DAResource.getBeanText("SETQUAL_DESC"));
      _mDesc[50].setDisplayName(DAResource.getBeanText("SETQUAL_NAME")); 

      _mDesc[51] = new MethodDescriptor(IsvappsRMI.class.getMethod("getQualifier", null), null);
      _mDesc[51].setShortDescription(DAResource.getBeanText("GETQUAL_DESC"));
      _mDesc[51].setDisplayName(DAResource.getBeanText("GETQUAL_NAME"));

      // default datastore
      parms = new ParameterDescriptor[1];

      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName(DAResource.getBeanText("ADSM_NAME"));
      parms[0].setName("aDSM"); 

      _mDesc[52] = new MethodDescriptor(IsvappsRMI.class.getMethod("setDefaultDatastore", new Class[] { DatastoreJDBC.class }), parms);
      _mDesc[52].setShortDescription(DAResource.getBeanText("SETDEFDSM_DESC"));
      _mDesc[52].setDisplayName(DAResource.getBeanText("SETDEFDSM_NAME")); 

      _mDesc[53] = new MethodDescriptor(IsvappsRMI.class.getMethod("getDefaultDatastore", null), null);
      _mDesc[53].setShortDescription(DAResource.getBeanText("GETDEFDSM_DESC"));
      _mDesc[53].setDisplayName(DAResource.getBeanText("GETDEFDSM_NAME")); 

      // dataId
      parms = new ParameterDescriptor[1];

      parms[0] = new ParameterDescriptor();
      parms[0].setDisplayName(DAResource.getBeanText("ADATAID_NAME"));
      parms[0].setName("aDataId"); 

      _mDesc[54] = new MethodDescriptor(IsvappsRMI.class.getMethod("setDataId", new Class[] { IsvappsDataId.class }), parms);
      _mDesc[54].setShortDescription(DAResource.getBeanText("SETDATAID_DESC"));
      _mDesc[54].setDisplayName(DAResource.getBeanText("SETDATAID_NAME")); 

      _mDesc[55] = new MethodDescriptor(IsvappsRMI.class.getMethod("getDataId", null), null);
      _mDesc[55].setShortDescription(DAResource.getBeanText("GETDATAID_DESC"));
      _mDesc[55].setDisplayName(DAResource.getBeanText("GETDATAID_NAME")); 

    } catch(Exception e) {}
  }

  private static void initBeanDescriptor() {
    _descriptor.setShortDescription("Copyright information goes here.... Auth");
  }

  public PropertyDescriptor[] getPropertyDescriptors() { return _pDesc; }
  public MethodDescriptor[] getMethodDescriptors() { return _mDesc; }

  public BeanInfo[] getAdditionalBeanInfo() {
    BeanInfo ret[] = new BeanInfo[1];
    ret[0] = new PersistentObjectBeanInfo();
    return ret;
  }

  public java.awt.Image getIcon(int iconKind) {
    if (iconKind == BeanInfo.ICON_COLOR_16x16) {
      java.awt.Image img = loadImage("PersistentObjectColor16.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_COLOR_32x32) {
      java.awt.Image img = loadImage("PersistentObjectColor32.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_MONO_16x16) {
      java.awt.Image img = loadImage("PersistentObjectMono16.gif");
      return img;
    }
    if (iconKind == BeanInfo.ICON_MONO_32x32) {
      java.awt.Image img = loadImage("PersistentObjectMono32.gif");
      return img;
    }
    return null;
  }

  public static String getGifFile () {
    return "PersistentObject.gif";
  }

  public BeanDescriptor getBeanDescriptor() { return _descriptor; }

  private static PropertyDescriptor _pDesc[] = new PropertyDescriptor[29];
  private static MethodDescriptor _mDesc[] = new MethodDescriptor[56];
  private static BeanDescriptor _descriptor = new BeanDescriptor(IsvappsRMI.class);
  private static boolean flag = false;
} 
