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
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;
import erp.application.service.EmployeeTimeLogFilesFactory;
import erp.application.service.FileStorageImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	
	
	@GetMapping(value="/deliver/{file}")
	@ResponseBody
	public ResponseEntity<?> deliverFile(@PathVariable String file){
		LOG.appLogger().info(" === Deliver File: " + file);
		final String redirectURL = "http://localhost:8088/user/erp/view/" + file;
		try {
			file = ApplicationStaticInfo.FOLDER__TO_ITERATE;
			Resource resource = storageService.loadResource(file);
			return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, redirectURL).body(resource);
		}catch (Exception e) {
			LOG.appLogger().error("=== ERROR ===", e.fillInStackTrace());
			throw new RuntimeException();
		}
	}
	
	@GetMapping(value="/view/{file}")
	public ResponseEntity<InputStreamResource> viewFileData(@PathVariable("file") String fileName) throws FileNotFoundException{
		final String filePath = ApplicationStaticInfo.FOLDER__TO_ITERATE + fileName;
		File file = new File(filePath);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename="+filePath);
		InputStreamResource input = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("text/plain;charset=UTF-8")).body(input);
	}
}
