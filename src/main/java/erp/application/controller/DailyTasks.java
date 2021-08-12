package erp.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import erp.application.entities.LOG;
import erp.application.service.EmployeeTimeLogFilesFactory;
import erp.application.service.FileStorageImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/user/erp")
@SessionAttributes(value="daily-Tasks")
public class DailyTasks {
	
	private EmployeeTimeLogFilesFactory employeeLogFiles;
	private Environment environment;
	private FileStorageImpl storageService;
		
	@Autowired
	public DailyTasks(EmployeeTimeLogFilesFactory elf, Environment env, FileStorageImpl ss) {
		super();
		this.employeeLogFiles = elf;
		this.environment = env;
		this.storageService = ss;
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
				model.addAttribute("files", storageService.listAll().map(
                      path -> MvcUriComponentsBuilder.fromMethodName(DailyTasks.class, "serveFile",
                    		  path.getFileName().toString()).build().toUri().toString())
						      .collect(Collectors.toSet()));
				employeeLogFiles.employeeTimeCounter(employeeAuth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logged.html";
	}

}
