package appFlow.beans;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

import appFlow.fileGenerators.BeanGenerator;
import appFlow.fileGenerators.PageGenerator;

@ManagedBean(name = "dashboard")
@SessionScoped
public class DashboardBean {
	private ProcessEngine p = ProcessEngines.getProcessEngine("Main Engine");
	private RuntimeService runtimeService = p.getRuntimeService();
	private RepositoryService repoService = p.getRepositoryService();
	private ProcessInstance procInst;

	public void onLoad() {

	}
	
	//returns the taskID of the currently active task of a given process instance
	public String getCurrentTaskID(ProcessInstance procInstance){
		
		return p.getTaskService().createTaskQuery()
				.processInstanceId(procInstance.getProcessInstanceId()).active()
				.list().get(0).getId();
		
		
	}

	public String onPageGeneratorSelect() {

		
		//create deployment
		repoService
				.createDeployment()
				.addClasspathResource(
						"appFlow/processModel/PageGenerationProcess.bpmn20.xml")
				.deploy();
		PageGenerator pageGenerator= new PageGenerator(null);
		BeanGenerator beanGenerator= new BeanGenerator(null);
		
		Map<String, Object> procVars = new HashMap<String, Object>();

		procVars.put("pageGenerator",pageGenerator);
		procVars.put("beanGenerator",beanGenerator);
		procVars.put("generateBean", "true");
		
		//start process
		procInst = runtimeService
				.startProcessInstanceByKey("pageGenerationProcess",procVars);

		// get the id of the current active task
		String taskid = this.getCurrentTaskID(this.getProcInst());

		// navigate to next defined page
		return p.getFormService().getTaskFormData(taskid).getFormKey()
				+ "?faces-redirect=true";
	}

	public String onDownloadSampleProjectSelect() {

		return "downloadSampleProject?faces-redirect=true";
	}

	public String onDBScriptSelect() {

		return "dbScriptDownload?faces-redirect=true";
	}

	public String onTutorialSelect() {

		return "tutorial?faces-redirect=true";
	}

	public ProcessInstance getProcInst() {
		return procInst;
	}

	public void setProcInst(ProcessInstance procInst) {
		this.procInst = procInst;
	}

}
