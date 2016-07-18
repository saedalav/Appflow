package appFlow.fileGenerators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

import appFlow.beans.PageReqForm;
import appFlow.formGeneration.POJOs.FieldInfo;



public class PageGenerator implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8896533700641931539L;
	BufferedWriter writer;
	public static final String PATH = "C:/Users/IBM_ADMIN/Desktop/";
	private PageReqForm prf;
	
	public PageGenerator(PageReqForm prf){
		this.prf=prf;
	}

	public String generatePage() {

		try {
			//file name format
			new File(PATH+"/"+this.getPrf().getDashboard().getProcInst().getProcessInstanceId()).mkdir();
			File pageCode = new File(PATH +"/"+this.getPrf().getDashboard().getProcInst().getProcessInstanceId()+"/"+ this.getPrf().getPageTitle()+".xhtml");
			FileOutputStream generatedPage = new FileOutputStream(pageCode);
			System.out.println("initializing web page generation");

			writer = new BufferedWriter(new OutputStreamWriter(generatedPage,
					"utf-8"));

			this.ConstructPageHeader();
			writer.newLine();
			this.generatefields(this.getPrf().getFieldInfos());
			writer.newLine();
			this.closeWriter();
			System.out.println("writerClosed.");

		} catch (IOException e) {
			// report
			e.printStackTrace();

		}
		
		return this.getPrf().postFileGenerate();

	}

	public void closeWriter() {

		try {
			writer.newLine();
			writer.write("</h:from>");
			writer.newLine();
			writer.write("</h:body>");
			writer.newLine();
			writer.write("</html>");
			writer.flush();
			this.writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// generates the static .xhtml page declaration
	public void ConstructPageHeader() {
		try {
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.newLine();
			writer.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			writer.newLine();
			writer.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"");
			writer.newLine();
			writer.write("xmlns:h=\"http://java.sun.com/jsf/html\"");
			writer.newLine();
			writer.write("xmlns:f=\"http://java.sun.com/jsf/core\"");
			writer.newLine();
			writer.write("xmlns:p=\"http://primefaces.org/ui\">");
			writer.newLine();
			writer.write("<h:head><title>" + prf.getPageTitle()
					+ "</title></h:head>");
			writer.newLine();
			writer.write("<h:body>");
			writer.newLine();
			writer.write("<h:from>");
			writer.newLine();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// generate labels and their associated input fields
	public void generatefields(ArrayList<FieldInfo> fieldContent) {
		System.out.println("generating fields...");
		int fieldRandId = 0;
		for (FieldInfo fi : fieldContent) {

			if (fi.getType().equalsIgnoreCase("input field")) {
				// generate input field
				try {
					// Gneerate input field label with id= fieldNameRandId , for
					// input field with id= fieldNameRandId+1 with value =
					// fieldName
					writer.write("<p:outputLabel id=\"" + fi.getName() + "ID"
							+ fieldRandId + "\" for=\"" + fi.getName() + "ID"
							+ (fieldRandId + 1) + "\" value=\"" + fi.getName()
							+ ": \"> </p:outputLabel>");
					writer.newLine();
					writer.newLine();
					// generate input field with id= fieldNameRandId+1 which its
					// value is saved on variable fieldName in the backing bean.
					writer.write("<p:inputText id=\"" + fi.getName() + "ID"
							+ (fieldRandId + 1) + "\" value=\"#{"+this.getPrf().getPageTitle().substring(0, 1).toUpperCase()+this.getPrf().getPageTitle().substring(1)+"Bean."
							+ fi.getName() + "}\"></p:inputText><br/>");
					writer.newLine();
					writer.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// increment id Generator by 2
				fieldRandId += 2;

			} else if (fi.getType().equalsIgnoreCase("Check box")) {
				// Gneerate checkbox label with id= fieldNameRandId , for
				// checkbox with id= checkboxNameRandId+1 with value = checkboxName
				try {
					writer.write("<p:outputLabel id=\"" + fi.getName() + "ID"
							+ fieldRandId + "\" for=\"" + fi.getName() + "ID"
							+ +(fieldRandId + 1) + "\" value=\"" + fi.getName()
							+ ": \"> </p:outputLabel>");
					writer.newLine();
					writer.newLine();
					// generate input field with id= fieldNameRandId+1 which its
					// value is saved on variable fieldName in the backing bean.
					writer.write("<p:selectBooleanCheckbox id=\""
							+ fi.getName() + "ID" + (fieldRandId + 1)
							+ "\" value=\"#{"+this.getPrf().getPageTitle().substring(0, 1).toUpperCase()+this.getPrf().getPageTitle().substring(1)+"Bean." + fi.getName() + "}\"><br/>");
					writer.newLine();
					writer.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fieldRandId += 2;

			}else if (fi.getType().equalsIgnoreCase("Command Button")) {
				// Gneerate input field label with id= fieldNameRandId , for
				// input field with id= fieldNameRandId+1 with value = fieldName
				
				//<p:commandButton id="nameIdRandNum" type="submit" value="ButtonName" action="#{TitleNameBean.onButtonName}"></p:commandButton>
				try {
					writer.write("<p:commandButton id=\"" + fi.getName() + "ID"
							+ fieldRandId + "\"");
					writer.write(" type=\"submit\" value=\"" + fi.getName()+"\" " + "action=\"#{"+this.getPrf().getPageTitle().substring(0, 1).toUpperCase()+this.getPrf().getPageTitle().substring(1)+"Bean." +"on"+ fi.getName().substring(0, 1).toUpperCase()+fi.getName().substring(1)+"}\"></p:commandButton>");
					
					writer.newLine();
					writer.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fieldRandId ++;
			}
		}
	}
	

	public static void main(String[] args) {
		BufferedWriter writer;
		File pageCode = new File("./New folder/newfile.txt");
		System.out.println("initializing web page generation");
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(pageCode), "utf-8"));
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.newLine();
		} catch (IOException e) {
			// report
			e.printStackTrace();
		}

	}
	public void pageGenMsgs(){
		
		System.out.println("the .xhtml file has been generated.");
	}

	public PageReqForm getPrf() {
		return prf;
	}

	public void setPrf(PageReqForm prf) {
		this.prf = prf;
	}

	public BufferedWriter getWriter() {
		return writer;
	}

	public void setWriter(BufferedWriter writer) {
		this.writer = writer;
	}

	public String getPath() {
		return PATH;
	}

	
}
