package erp.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import erp.application.entities.LOG;
import erp.application.service.EmployeeTimeLogFilesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/user/erp")
@SessionAttributes(value="daily-Tasks")
public class DailyTasks {
	
	private EmployeeTimeLogFilesFactory employeeLogFiles;
	private Environment environment;
		
	@Autowired
	public DailyTasks(EmployeeTimeLogFilesFactory elf, Environment env) {
		super();
		this.employeeLogFiles = elf;
		this.environment = env;
	}

	@GetMapping(value="/daily/tasks")
	public String dailyTasks(Model model, HttpServletRequest request, Authentication employeeAuth) {
		LOG.appLogger().info(" === Name: " + employeeAuth.getName() + ", Values Received: " + request.toString());
		String n = "Welcome  " + request.getUserPrincipal().getName();
		employeeAuth = SecurityContextHolder.getContext().getAuthentication();
		String ok = request.getParameter("ok");
		try {
			if(ok != null) {
				model.addAttribute("name", n);
				model.addAttribute("appName", environment.getProperty("spring.application.name"));
				employeeLogFiles.employeeTimeCounter(employeeAuth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logged.html";
	}

}
