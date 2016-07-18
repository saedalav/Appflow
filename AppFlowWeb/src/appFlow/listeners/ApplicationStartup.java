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
				.setJdbcUrl("jdbc:mysql://localhost:3306/appflowdb?autoReconnect=true")
				.setJdbcDriver("com.mysql.jdbc.Driver").setJdbcUsername("root")
				.setJdbcPassword("asdfas32").setJobExecutorActivate(true).setDatabaseSchemaUpdate("true")
				.setProcessEngineName("Main Engine").buildProcessEngine();
		ProcessEngines.registerProcessEngine(processEngine);
		if (processEngine == null)
			System.out.println("Activiti Engine Failed to Start");

		ServletContext sc = arg0.getServletContext();
		sc.setAttribute("processEngine", processEngine);

		System.out.println("Activiti Process Engine Started Successfully");

	}

}
