package appFlow.fileGenerators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import appFlow.beans.PageReqForm;
import appFlow.formGeneration.POJOs.FieldInfo;

@ManagedBean
@SessionScoped
public class BeanGenerator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7595108409842949083L;
	BufferedWriter writer;
	public static final String PATH = "C:/Users/IBM_ADMIN/Desktop/";
	private PageReqForm prf;
	
	
	public BeanGenerator(PageReqForm prf){
		this.prf=prf;
	}

	public void generateBean() {

		try {
			// file name format
			new File(PATH
					+ "/"
					+ this.getPrf().getDashboard().getProcInst()
							.getProcessInstanceId()).mkdir();
			File beanCode = new File(PATH
					+ "/"
					+ this.getPrf().getDashboard().getProcInst()
							.getProcessInstanceId() + "/"
					+ this.getPrf().getPageTitle()+"Bean.java");
			FileOutputStream generatedBean;
			generatedBean = new FileOutputStream(beanCode);
			System.out.println("initializing web page generation");

			writer = new BufferedWriter(new OutputStreamWriter(generatedBean,
					"utf-8"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		// generate bean:
		
		this.generateBeanHeader();
		this.generateBeanAttributes(this.getPrf().getFieldInfos());
		this.generateSettersAndGetters(this.getPrf().getFieldInfos());
		

	}

	public void generateBeanHeader() {

		try {
			writer.write("import javax.faces.bean.ManagedBean;");
			writer.newLine();
			writer.write("import javax.faces.bean.ViewScoped;");
			writer.newLine();
			writer.newLine();
			writer.write("@ManagedBean (name = \""+this.getPrf().getPageTitle().substring(0, 1).toUpperCase() + this.getPrf().getPageTitle().substring(1)+"\")");
			writer.newLine();
			writer.write("@ViewScoped");
			writer.newLine();
			writer.newLine();
			writer.write("public class " + this.getPrf().getPageTitle().substring(0, 1).toUpperCase() + this.getPrf().getPageTitle().substring(1)
					+ "Bean {");
			writer.newLine();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generateBeanAttributes(ArrayList<FieldInfo> fieldContent) {
		System.out.println("generating Attributes...");

		for (FieldInfo fi : fieldContent) {
			// generate String attributes for input fields
			if (fi.getType().equalsIgnoreCase("input Field")) {

				try {
					writer.newLine();
					writer.write(" String " + fi.getName() + ";");
					writer.newLine();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// generate boolean attributes for checkboxes
			} else if (fi.getType().equalsIgnoreCase("check box")) {
				try {
					writer.newLine();
					writer.write(" boolean " + fi.getName() + ";");
					writer.newLine();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
	
	public void generateSettersAndGetters(ArrayList<FieldInfo> fieldContent){
		System.out.println("generating Setters and Getters...");

		for (FieldInfo fi : fieldContent) {
			// generate String attributes for input fields
			if(fi.getType().equalsIgnoreCase("command Button")){
				try {
					writer.write(" public void on" + fi.getName().substring(0, 1).toUpperCase() + fi.getName().substring(1)+ " () {");
					writer.newLine();

					writer.write("   //Place the action code here..");
					writer.newLine();
					writer.write(" }");
					writer.newLine();
					writer.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			
			}else if (fi.getType().equalsIgnoreCase("input Field")) {

				try {
					writer.newLine();
					
					//Getter
					// capitalize the first character of attribute name
					writer.write(" public String get" + fi.getName().substring(0, 1).toUpperCase() + fi.getName().substring(1)+ " () {");
					writer.newLine();

					writer.write("   return this." + fi.getName() + ";");
					writer.newLine();
					writer.write(" }");
					writer.newLine();
					writer.newLine();
					//Setter
					// capitalize the first character of attribute name
					writer.write(" public void set" + fi.getName().substring(0, 1).toUpperCase() + fi.getName().substring(1)+ "(String value) {");
					writer.newLine();

					writer.write("   this." + fi.getName() + "= value;");
					writer.newLine();
					writer.write(" }");
					writer.newLine();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// generate boolean attributes for checkboxes
			} else if (fi.getType().equalsIgnoreCase("check box")) {
				try {
					writer.newLine();
					//Getter
					// capitalize the first character of attribute name
					writer.write(" public String is" + fi.getName().substring(0, 1).toUpperCase() + fi.getName().substring(1)+ "() {");
					writer.newLine();

					writer.write("   return this." + fi.getName() + ";");
					writer.newLine();
					writer.write(" }");
					writer.newLine();
					
					//Setter
					// capitalize the first character of attribute name
					writer.write(" public void set" + fi.getName().substring(0, 1).toUpperCase() + fi.getName().substring(1)+ "(boolean value) {");
					writer.newLine();

					writer.write("   this." + fi.getName() + "= value;");
					writer.newLine();
					writer.write(" }");
					writer.newLine();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
			
		}
		
		//close class bracket
		
		try {
			writer.newLine();
			writer.write("}");
			writer.newLine();
			writer.flush();
			this.writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void beanGenMsgs(){
		
		System.out.println("the Backing bean file has been generated.");
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
