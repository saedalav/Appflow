package appFlow.beans;

import java.io.FileInputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean (name="sampleProjectDownload")
@ViewScoped
public class SampleProjectDownloadBean {
	private final String PATH="C:/Users/IBM_ADMIN/Eclipse Projects/AppflowProject1/AppFlowWeb/WebContent/MavionizedProject/MavenizedSampleProject.zip";
	private StreamedContent mavenizedSampleProjectZipFile;
	public void onLoad() {
		FileInputStream sampleProjectInputStream;
		try {

			sampleProjectInputStream = new FileInputStream(this.getPath());
			mavenizedSampleProjectZipFile = new DefaultStreamedContent(sampleProjectInputStream, "zip","MavenizedSampleProject.zip");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public StreamedContent getMavenizedSampleProjectZipFile() {
		return mavenizedSampleProjectZipFile;
	}
	public void setMavenizedSampleProjectZipFile(
			StreamedContent mavenizedSampleProjectZipFile) {
		this.mavenizedSampleProjectZipFile = mavenizedSampleProjectZipFile;
	}

	public String getPath() {
		return PATH;
	}
}
