package erp.application.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import erp.application.products.ProductsRepository;
import erp.application.entities.LOG;
import erp.application.employee.model.Employee;
import erp.application.employee.model.EmployeeInitialSavedData;
import erp.application.employee.repository.EmployeeInitialSavedDataRepo;
import erp.application.entities.CreateFiles;
import erp.application.service.ConnectionService;
import erp.application.service.EmployeeService;

@Controller
public class ConnectionController {
	
	private ConnectionService service;
	
	private EmployeeService employeeService;
	
	@Autowired
	public ConnectionController(ConnectionService serv, EmployeeService eService) {
		this.service = serv;
		this.employeeService = eService;
	}
	
	@GetMapping(value = "/return/{id}")
	@ResponseBody
	public String connectionMethod(@PathVariable(value="id") final int idValue) {
		System.out.println("Service info: " + service.getModel() + " : " + idValue);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("return.html");
		ObjectMapper mpr = new ObjectMapper();
		try {
			String jsonInfo = mpr.writeValueAsString(service.getModel().stream().filter(id -> id.getId() == idValue)
					.findAny().orElse(null));
			LOG.appLogger().info("JSON structure: " + jsonInfo);
			JsonNode idReceiver = mpr.readTree(jsonInfo);
			JsonNode firstNameReceiver = mpr.readTree(jsonInfo);
			JsonNode lastNameReceiver = mpr.readTree(jsonInfo);
			JsonNode professionReceiver = mpr.readTree(jsonInfo);
			JsonNode isExceptedReceiver = mpr.readTree(jsonInfo);
			JsonNode addressReceiver = mpr.readTree(jsonInfo);
			JsonNode salaryReceiver = mpr.readTree(jsonInfo);
			JsonNode cnpReceiver = mpr.readTree(jsonInfo);
			JsonNode genderReceiver = mpr.readTree(jsonInfo);
			JsonNode fulltTimeReceiver = mpr.readTree(jsonInfo);
			JsonNode aditionalInfoReceiver = mpr.readTree(jsonInfo);
			LOG.appLogger().warn("Writing data to file begun: ");
			employeeService.saveInitiaInfos(new EmployeeInitialSavedData(idReceiver.get("id").asInt(), firstNameReceiver
					.get("name").asText(), lastNameReceiver.get("second_name").asText(), professionReceiver.get("profession").asText(),
					isExceptedReceiver.get("isExcept").asText(), addressReceiver.get("address").asText(), salaryReceiver.get("salary").asDouble(),
					cnpReceiver.get("cnp").asText(), genderReceiver.get("gender").asText(), fulltTimeReceiver.get("fulltime").asText(), 
					aditionalInfoReceiver.get("aditionInfo").asText()));
				System.out.println("Calculate taxes: " + employeeService.calculateTaxes());
			return mpr.writeValueAsString(service.getModel().stream().filter(id -> id.getId() == idValue).findAny().orElse(null));
		}catch (JsonProcessingException e) {
			e.printStackTrace();
			LOG.appLogger().error("JSON PARSING ERROR WITH ROOT CAUSE: ", e.getCause().toString());
			return "FAIL";
		}catch (IOException e) {
			e.printStackTrace();
			LOG.appLogger().error("MAJOR SYSTEM FAILURE WITH ROOT CAUSE: ", e.getLocalizedMessage());
			return "FAIL";
		}
	}
	
	@GetMapping("/save")
	@ResponseBody
	public void save() {
		employeeService.saveInitiaInfos(new EmployeeInitialSavedData(19101,"name", "second_name", 
				"profession","false", "address", 100.21,"1781222460322", "gender", "fulltime", 
				"aditionInfo"));
	}
	
	@GetMapping("/findall")
	@ResponseBody
	public void findAll() {
		employeeService.find();
	}

}
