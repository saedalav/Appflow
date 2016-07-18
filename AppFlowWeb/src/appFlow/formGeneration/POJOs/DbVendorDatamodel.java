package appFlow.formGeneration.POJOs;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class DbVendorDatamodel extends ListDataModel<DbVendor> implements SelectableDataModel<DbVendor> {  
	 
    public DbVendorDatamodel() {
    }
 
    public DbVendorDatamodel (List<DbVendor> data) {
        super(data);
    }
     
    @Override
    public DbVendor getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data
         
        @SuppressWarnings("unchecked")
		List<DbVendor> dbVendor = (List<DbVendor>) getWrappedData();
         
        for(DbVendor vendor : dbVendor) {
            if(vendor.name.equalsIgnoreCase(rowKey))
                return vendor;
        }
         
        return null;
    }
 
    @Override
    public Object getRowKey(DbVendor dbVendor) {
        return dbVendor.getName();
    }




}
