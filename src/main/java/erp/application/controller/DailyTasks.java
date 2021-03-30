package erp.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import erp.application.service.EmployeeTimeLogFilesFactory;
import erp.application.service.FileTransferService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DailyTasks {
	
	private FileTransferService fileTransfer;
	private EmployeeTimeLogFilesFactory employeeLogFiles;
		
	@Autowired
	public DailyTasks(FileTransferService fts, EmployeeTimeLogFilesFactory elf) {
		this.fileTransfer = fts;
		this.employeeLogFiles = elf;
	}
		
	@GetMapping(value="/daily/tasks")
	public String showName(Model model, HttpServletRequest request, Authentication employeeAuth) {
		String n = "Welcome  " + request.getUserPrincipal().getName();
		employeeAuth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("name", n);
		return "logged.html";
	}

}
