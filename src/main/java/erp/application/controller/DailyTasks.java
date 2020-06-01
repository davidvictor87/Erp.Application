package erp.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;
import erp.application.products.Products;
import erp.application.service.CreateProductFiles;
import erp.application.service.FileTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;


@Controller
public class DailyTasks {
	
	private FileTransferService fileTransfer;
		
	@Autowired
	public DailyTasks(FileTransferService fts) {
		this.fileTransfer = fts;
	}
	
	@GetMapping(value="/d-Task")
	public String showName(Model model, HttpServletRequest request) {
		String n = "Welcome  " + request.getUserPrincipal().getName();
		model.addAttribute("name", n);
		try {
			fileTransfer.startWatch();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "logged.html";
	}
	
	@RequestMapping(value="/getProducts", method = RequestMethod.GET)
	public ResponseEntity<Products> employeeJsonResponse(){
		Products employee = CreateProductFiles.getProducts();
		try {
			return new ResponseEntity<Products>(employee, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Products>(HttpStatus.NOT_FOUND);
	}

}
