//------------------------------------------------------------------------
// This code is generated by IBM VisualAge for Java Data Access Builder.
// WARNING: User modifications will be lost on next code generation.
//------------------------------------------------------------------------

package Isvapps;

import java.lang.*;
import COM.ibm.ivj.eab.data.*;
import java.beans.*;

public class IsvappsDataIdResultForm extends DataAccessResultForm
{
   public IsvappsDataIdResultForm()
   {
      this( new IsvappsDataIdManager() );
   }
   public IsvappsDataIdResultForm(IsvappsDataIdManager aManager)
   {
      super(attributeLabels);
      setManager(aManager);
   }
   private static String[] attributeLabels = {
            "product_id"  };
   // only allow labels to be changed, not the number of columns
   public void setColumns( String[] labels )
   {
      if ( labels.length != attributeLabels.length )
         throw new IllegalArgumentException();
      else
         super.setColumns(labels);
   }

}
