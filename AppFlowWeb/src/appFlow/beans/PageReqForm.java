package appFlow.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

import appFlow.fileGenerators.BeanGenerator;
import appFlow.fileGenerators.PageGenerator;
import appFlow.formGeneration.POJOs.FieldInfo;

@ManagedBean(name = "pageReqForm")
@SessionScoped

public class PageReqForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5590251498984973763L;
	
	private int fieldCount = 0;
	private int checkboxCount = 0;
	private int cmdButtonCount=0;
	private String pageTitle;
	
	private boolean generateBeanSelected= true;
	
	ArrayList<FieldInfo> fieldInfos = new ArrayList<FieldInfo>();

	private ProcessEngine p = ProcessEngines.getProcessEngine("Main Engine");

	@ManagedProperty(value = "#{dashboard}")
	DashboardBean dashboard;

	PageGenerator pageGenerator = new PageGenerator(this);
	BeanGenerator beanGenerator= new BeanGenerator(this);
	
	private String previousTaskId = "";
	
	public void onLoad(){
		this.pageTitle="";
		this.fieldCount=0;
		this.checkboxCount=0;
		this.cmdButtonCount=0;
		this.fieldInfos.clear();
		//this.actionAlreadPerformed=false;
	}
	
	public String onSubmit() {
		this.generateFieldObjects();

		// get the id of the current active task
		String taskid = this.getDashboard().getCurrentTaskID(
				this.getDashboard().getProcInst());
		
		//set generate bean variable
		p.getTaskService().setVariable(taskid,"generateBean",this.isGenerateBeanSelected());
		
		if(!this.previousTaskId.equals(taskid)){
			// complete the current task
			p.getTaskService().complete(taskid);
			this.previousTaskId=this.getDashboard().getCurrentTaskID(
					this.getDashboard().getProcInst());
			
		}
		// get the id of current task ( the new active task after the completion
		// of page requirement)
		taskid = this.getDashboard().getCurrentTaskID(
				this.getDashboard().getProcInst());
		// navigate to next defined page
		return p.getFormService().getTaskFormData(taskid).getFormKey()
				+ "?faces-redirect=true";
	}

	public String onGenerate(){
		
		if(this.generateBeanSelected)
			this.beanGenerator.generateBean();
		
		return this.pageGenerator.generatePage();
	}
	public String postFileGenerate() {
		// get the id of the current active task
		String taskid = this.getDashboard().getCurrentTaskID(
				this.getDashboard().getProcInst());

		// complete the current task
		p.getTaskService().complete(taskid);

		// get the id of current task ( the new active task after the completion
		// of page requirement)
		taskid = this.getDashboard().getCurrentTaskID(
				this.getDashboard().getProcInst());

		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// navigate to next defined page
		return p.getFormService().getTaskFormData(taskid).getFormKey()
				+ "?faces-redirect=true";
	}

	void generateFieldObjects() {
		int tempCount = 0;
		// generate input field objects with default name
		for (int i = 0; i < this.fieldCount; i++) {

			FieldInfo tempField = new FieldInfo();
			tempField.setName(("inputField" + tempCount));
			tempField.setType("input field");
			System.out.println("field" + tempField.getName() + " was entered");
			fieldInfos.add(tempField);
			tempCount++;
		}

		// generate check box field objects with default names.
		tempCount = 0;
		for (int i = 0; i < this.checkboxCount; i++) {

			FieldInfo tempField = new FieldInfo();
			tempField.setName("checkBox" + tempCount);
			tempField.setType("check box");
			fieldInfos.add(tempField);
			tempCount++;

		}
		
		tempCount = 0;
		for (int i = 0; i < this.cmdButtonCount; i++) {

			FieldInfo tempField = new FieldInfo();
			tempField.setName("commandButton" + tempCount);
			tempField.setType("command Button");
			fieldInfos.add(tempField);
			tempCount++;

		}


	}

	//Getters/Setters
	public int getFormCount() {
		return fieldCount;
	}

	public void setFormCount(int formCount) {
		this.fieldCount = formCount;
	}

	public int getCheckboxCount() {
		return checkboxCount;
	}

	public void setCheckboxCount(int checkboxCount) {
		this.checkboxCount = checkboxCount;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public ArrayList<FieldInfo> getFieldInfos() {
		return fieldInfos;
	}

	public void setFieldInfos(ArrayList<FieldInfo> fieldInfos) {
		this.fieldInfos = fieldInfos;
	}

	public int getFieldCount() {
		return fieldCount;
	}

	public void setFieldCount(int fieldCount) {
		this.fieldCount = fieldCount;
	}

	public void setDashboard(DashboardBean dashboard) {
		this.dashboard = dashboard;
	}

	public DashboardBean getDashboard() {
		return dashboard;
	}

	public boolean isGenerateBeanSelected() {
		return generateBeanSelected;
	}

	public void setGenerateBeanSelected(boolean generateBeanSelected) {
		this.generateBeanSelected = generateBeanSelected;
	}

	public PageGenerator getPageGenerator() {
		return pageGenerator;
	}

	public void setPageGenerator(PageGenerator pageGenerator) {
		this.pageGenerator = pageGenerator;
	}

	public BeanGenerator getBeanGenerator() {
		return beanGenerator;
	}

	public void setBeanGenerator(BeanGenerator beanGenerator) {
		this.beanGenerator = beanGenerator;
	}

	public int getCmdButtonCount() {
		return cmdButtonCount;
	}

	public void setCmdButtonCount(int cmdButtonCount) {
		this.cmdButtonCount = cmdButtonCount;
	}

}
