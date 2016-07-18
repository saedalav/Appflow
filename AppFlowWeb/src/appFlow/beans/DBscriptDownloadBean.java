package appFlow.beans;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import appFlow.formGeneration.POJOs.DbVendor;
import appFlow.formGeneration.POJOs.DbVendorDatamodel;

@ManagedBean(name="dbDownload")
@SessionScoped
public class DBscriptDownloadBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -416742586285106705L;
	ArrayList<DbVendor> dbVendors;
	DbVendor selectedVendor=null;
	private final String PATH = "C:/Users/Saeed/Desktop/ScriptZips";
	private StreamedContent dbScript;
	private DbVendorDatamodel vendorDataModel;
	
	
	public void onLoad(){
		dbVendors = new ArrayList<DbVendor>();
		
		DbVendor h2 = new DbVendor(1,"H2","H2");
		dbVendors.add(h2);
		DbVendor db2 = new DbVendor(2,"IBM DB2","DB2");
		dbVendors.add(db2);
		DbVendor mySQL = new DbVendor(3,"MySQL","MySQL");
		dbVendors.add(mySQL);
		DbVendor msSQL = new DbVendor(4,"Microsoft SQL server","MSSQL");
		dbVendors.add(msSQL);
		DbVendor oracle = new DbVendor(5,"Oracle","Oracle");
		dbVendors.add(oracle);
		DbVendor postgres = new DbVendor(6,"Postgres","Postgres");
		dbVendors.add(postgres);
		vendorDataModel= new DbVendorDatamodel(dbVendors);
		
		
		
		
	}
	
	public void onDownloadScripts () {
		//Place the action code here..
		}

	public List<DbVendor> getDbVendors() {
		return dbVendors;
	}




	public StreamedContent getDbScript() {
		FileInputStream dbScriptInputStream;
		try {

			dbScriptInputStream = new FileInputStream(this.getPATH()+"/"+this.selectedVendor.getPhoto()+".zip");
			dbScript = new DefaultStreamedContent(dbScriptInputStream, "zip",this.selectedVendor.getName()+".zip");
		}catch(Exception e){
			e.printStackTrace();
		}
		return dbScript;
	}

	public void setDbScript(StreamedContent dbScript) {
		this.dbScript = dbScript;
	}

	public String getPATH() {
		return PATH;
	}

	public DbVendor getSelectedVendor() {
		return selectedVendor;
	}

	public void setSelectedVendor(DbVendor selectedVendor) {
		this.selectedVendor = selectedVendor;
	}

	public void setDbVendors(ArrayList<DbVendor> dbVendors) {
		this.dbVendors = dbVendors;
	}

	public DbVendorDatamodel getVendorDataModel() {
		return vendorDataModel;
	}

	public void setVendorDataModel(DbVendorDatamodel vendorDataModel) {
		this.vendorDataModel = vendorDataModel;
	}

}
