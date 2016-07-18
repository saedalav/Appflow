package appFlow.beans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name="download")
@ViewScoped
public class GeneratedFilesDownloadBean {
	private StreamedContent xhtmlFile,BeanFile;
	
	@ManagedProperty(value = "#{pageReqForm}")
	PageReqForm prf;
	
	
	public void onLoad() {
		FileInputStream pageInputStream,BeanInputStream;
		try {

			pageInputStream = new FileInputStream(this.prf.getPageGenerator().getPath()+"/"+this.getPrf().getDashboard().getProcInst().getProcessInstanceId()+"/"+ this.getPrf().getPageTitle()+".xhtml");
			xhtmlFile = new DefaultStreamedContent(pageInputStream, "xhtml",
					this.getPrf().getPageTitle()+".xhtml");


			BeanInputStream = new FileInputStream(this.prf.getBeanGenerator().getPath()+"/"+this.getPrf().getDashboard().getProcInst().getProcessInstanceId()+"/"+ this.getPrf().getPageTitle()+"Bean.java");
			BeanFile = new DefaultStreamedContent(BeanInputStream, "java",
					this.getPrf().getPageTitle()+"Bean.java");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public StreamedContent getXhtmlFile() {
		return xhtmlFile;
	}

	public PageReqForm getPrf() {
		return prf;
	}

	public void setPrf(PageReqForm prf) {
		this.prf = prf;
	}

	public StreamedContent getBeanFile() {
		return BeanFile;
	}
}

	
