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

public class IsvappsForm extends DataAccessObjectForm
{

  private boolean iDataIdEditable = true;

  public IsvappsForm() {
    this( new Isvapps() );
  }
  public IsvappsForm(Isvapps target) {
    super( attributeNames
         , attributeLabels
         , attributeNullables
         , attributeClasses
         , attributeWidths 
         , attributeDataIds );
    setTarget(target);
  }


  public void setDataIdEditable( boolean enable ) {
    if ( enable == iDataIdEditable )
       return;
    iDataIdEditable = enable;
    setAttributeEditable("product_id",enable); 


  }

  public boolean isDataIdEditable() {
    return iDataIdEditable;
  }


  public Isvapps getTargetAsIsvapps() {
    return (Isvapps) getTarget();
  }

  public void setTargetAsIsvapps( Isvapps aTarget ) {
    super.setTarget( (DataAccessObject) aTarget );
  }
  protected DataAccessObject validateTarget(DataAccessObject aTarget)
  {
    return (Isvapps) aTarget;
  }

  public static Class[] attributeClasses = {
      String.class ,
                  String.class ,
                  String.class ,
                  String.class ,
                  String.class ,
                  String.class ,
                  String.class ,
                  String.class ,
                  byte[].class ,
                  Integer.class ,
                  String.class ,
                  java.sql.Date.class ,
                  String.class ,
                  String.class 
  };
  public static String[] attributeLabels = {
            "product_id",
            "product_name",
            "short_name",
            "description",
            "classification",
            "type",
            "platforms",
            "level",
            "file",
            "size",
            "supplier_id",
            "create_date",
            "url",
            "readme"  };
  public static String[] attributeNames = {
            "product_id",
            "product_name",
            "short_name",
            "description",
            "classification",
            "type",
            "platforms",
            "level",
            "file",
            "size",
            "supplier_id",
            "create_date",
            "url",
            "readme"  };
  public static boolean[] attributeNullables = {
            false,
            false,
            false,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            false,
            true,
            true,
            true  };
  public static int[] attributeWidths = {
            10,
            40,
            10,
            80,
            10,
            10,
            50,
            20,
            15728640,
            10,
            10,
            10,
            80,
            1000  };
  public static boolean[] attributeDataIds = {
            true,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false  }; 
  public void attributeFromEditor(String attributeName, PropertyEditor editor) throws DAException
  {
    if ( editor == null )
      return;
    else if ( attributeName.equals("product_id") )
         getTargetAsIsvapps().setProduct_id((String)editor.getValue());
     else if ( attributeName.equals("product_name") )
         getTargetAsIsvapps().setProduct_name((String)editor.getValue());
     else if ( attributeName.equals("short_name") )
         getTargetAsIsvapps().setShort_name((String)editor.getValue());
     else if ( attributeName.equals("description") )
         getTargetAsIsvapps().setDescription((String)editor.getValue());
     else if ( attributeName.equals("classification") )
         getTargetAsIsvapps().setClassification((String)editor.getValue());
     else if ( attributeName.equals("type") )
         getTargetAsIsvapps().setType((String)editor.getValue());
     else if ( attributeName.equals("platforms") )
         getTargetAsIsvapps().setPlatforms((String)editor.getValue());
     else if ( attributeName.equals("level") )
         getTargetAsIsvapps().setLevel((String)editor.getValue());
     else if ( attributeName.equals("file") )
         getTargetAsIsvapps().setFile((byte[])editor.getValue());
     else if ( attributeName.equals("size") )
         getTargetAsIsvapps().setSize((Integer)editor.getValue());
     else if ( attributeName.equals("supplier_id") )
         getTargetAsIsvapps().setSupplier_id((String)editor.getValue());
     else if ( attributeName.equals("create_date") )
         getTargetAsIsvapps().setCreate_date((java.sql.Date)editor.getValue());
     else if ( attributeName.equals("url") )
         getTargetAsIsvapps().setUrl((String)editor.getValue());
     else if ( attributeName.equals("readme") )
         getTargetAsIsvapps().setReadme((String)editor.getValue());
 
  }

  public void attributeIntoEditor(String attributeName, PropertyEditor editor)
  {
    if ( editor == null )
      return;
    else if ( attributeName.equals("product_id") )
      editor.setValue(getTargetAsIsvapps().getProduct_id());
     else if ( attributeName.equals("product_name") )
      editor.setValue(getTargetAsIsvapps().getProduct_name());
     else if ( attributeName.equals("short_name") )
      editor.setValue(getTargetAsIsvapps().getShort_name());
     else if ( attributeName.equals("description") )
      editor.setValue(getTargetAsIsvapps().getDescription());
     else if ( attributeName.equals("classification") )
      editor.setValue(getTargetAsIsvapps().getClassification());
     else if ( attributeName.equals("type") )
      editor.setValue(getTargetAsIsvapps().getType());
     else if ( attributeName.equals("platforms") )
      editor.setValue(getTargetAsIsvapps().getPlatforms());
     else if ( attributeName.equals("level") )
      editor.setValue(getTargetAsIsvapps().getLevel());
     else if ( attributeName.equals("file") )
      editor.setValue(getTargetAsIsvapps().getFile());
     else if ( attributeName.equals("size") )
      editor.setValue(getTargetAsIsvapps().getSize());
     else if ( attributeName.equals("supplier_id") )
      editor.setValue(getTargetAsIsvapps().getSupplier_id());
     else if ( attributeName.equals("create_date") )
      editor.setValue(getTargetAsIsvapps().getCreate_date());
     else if ( attributeName.equals("url") )
      editor.setValue(getTargetAsIsvapps().getUrl());
     else if ( attributeName.equals("readme") )
      editor.setValue(getTargetAsIsvapps().getReadme());
 
  }

}
