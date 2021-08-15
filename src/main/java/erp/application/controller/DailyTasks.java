package erp.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;
import erp.application.entities.tasks.FileStorage;
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
                      path -> MvcUriComponentsBuilder.fromMethodName(DailyTasks.class, "deliverFile",
                    		  path.getFileName().toString()).build().toUri().toString())
						      .collect(Collectors.toSet()));
				employeeLogFiles.employeeTimeCounter(employeeAuth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logged.html";
	}
	
	
	@GetMapping("/deliver/{file}")
	@ResponseBody
	public ResponseEntity<?> deliverFile(@PathVariable String file){
		LOG.appLogger().info(" === Deliver File ===");
		try {
			file = ApplicationStaticInfo.FOLDER__TO_ITERATE;
			Resource resource = storageService.loadResource(file);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, file).body(resource);
		}catch (Exception e) {
			LOG.appLogger().error("=== ERROR ===", e.fillInStackTrace());
			throw new RuntimeException();
		}
	}
}
