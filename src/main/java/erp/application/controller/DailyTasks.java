package erp.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import erp.application.products.Products;
import erp.application.service.CreateProductFiles;
import erp.application.service.EmployeeTimeLogFilesFactory;
import erp.application.service.FileTransferService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;


@Controller
@SessionAttributes(value="products")
public class DailyTasks {
	
	private FileTransferService fileTransfer;
	private EmployeeTimeLogFilesFactory employeeLogFiles;
		
	@Autowired
	public DailyTasks(FileTransferService fts, EmployeeTimeLogFilesFactory elf) {
		this.fileTransfer = fts;
		this.employeeLogFiles = elf;
	}
	
	@ModelAttribute(value="products")
	public Products getProduct() {
		return new Products();
	}
	
	@GetMapping(value="/daily/tasks")
	public String showName(Model model, HttpServletRequest request, Authentication employeeAuth) {
		String n = "Welcome  " + request.getUserPrincipal().getName();
		employeeAuth = SecurityContextHolder.getContext().getAuthentication();
		employeeLogFiles.employeeTimeCounter(employeeAuth);
		model.addAttribute("name", n);
		try {
			fileTransfer.startWatch();
		} catch (IOException | ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
		return "logged.html";
	}
	
	@RequestMapping(value="/get/products", method = RequestMethod.POST)
	public ResponseEntity<Products> employeeJsonResponse(@ModelAttribute(value="products") Products product){
		product = CreateProductFiles.getProducts();
		try {
			return new ResponseEntity<Products>(product, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Products>(HttpStatus.NOT_FOUND);
	}

}
