package erp.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import erp.application.products.Products;
import erp.application.service.CreateProductFiles;
import erp.application.service.EmployeeTimeLogFilesFactory;
import erp.application.service.FileTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;


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
		employeeLogFiles.employeeTimeCounter(employeeAuth);
		model.addAttribute("name", n);
		try {
			fileTransfer.startWatch();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "logged.html";
	}
	
	@RequestMapping(value="/get/products", method = RequestMethod.GET)
	public ResponseEntity<Products> employeeJsonResponse(){
		Products product = CreateProductFiles.getProducts();
		try {
			return new ResponseEntity<Products>(product, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Products>(HttpStatus.NOT_FOUND);
	}

}
