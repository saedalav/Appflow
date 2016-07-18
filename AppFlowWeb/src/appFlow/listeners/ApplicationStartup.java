package appFlow.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;

@WebListener
public class ApplicationStartup implements ServletContextListener {

	//Make sure JDBC Driver for your Database is on the path
	//Set the following Variables
	final private String jdbcUrl = "jdbc:mysql://localhost:3306/appflowdb?autoReconnect=true";
	final private String jdbcDriver = "com.mysql.jdbc.Driver";
	final private String jdbcUser = "root";
	final private String jdbcPassword = "asdfas32"; 
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ProcessEngines.destroy();

	}

	// runs on application start up. process engine configurations are defined here.
	// database name must be entered as part of jdbcURL
 
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ProcessEngines.init();
		
		// db user & paswrd, jdbc driver and engine name are set 
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration()
				.setJdbcUrl(jdbcUrl)
				.setJdbcDriver(jdbcDriver).setJdbcUsername(jdbcUser)
				.setJdbcPassword(jdbcPassword).setJobExecutorActivate(true).setDatabaseSchemaUpdate("true")
				.setProcessEngineName("Main Engine").buildProcessEngine();
		ProcessEngines.registerProcessEngine(processEngine);
		if (processEngine == null)
			System.out.println("Activiti Engine Failed to Start");

		ServletContext sc = arg0.getServletContext();
		sc.setAttribute("processEngine", processEngine);

		System.out.println("Activiti Process Engine Started Successfully");

	}

}
