package erp.application.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import erp.application.entities.LOG;
import erp.application.employee.model.Address;
import erp.application.employee.model.EmployeeInitialSavedData;
import erp.application.employee.model.EmployeeProcessedData;
import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.CreateFiles;
import erp.application.service.ConnectionService;

import erp.application.service.EmployeeService;

@RestController
@RequestMapping(value="/employee/access")
public class ConnectionController {

    private ConnectionService service;
	private EmployeeService employeeService;

	@Autowired
	public ConnectionController(ConnectionService serv, EmployeeService eService) {
		this.service = serv;
		this.employeeService = eService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBider) {
		final boolean customValidator = true;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, customValidator);
		dataBider.registerCustomEditor(Date.class, editor);
	}

	@GetMapping(value = "/get/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "http://localhost:3000/employee/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ResponseBody
	public ModelAndView connectionMethod(@PathVariable(value = "id") final int idValue) {
		
		System.out.println("Service info: " + service.getModel() + " : " + idValue);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("return.html");
		ObjectMapper mpr = new ObjectMapper();
		try {
			String jsonInfo = mpr.writeValueAsString(
					service.getModel().parallelStream().filter(id -> id.getId() == idValue).findFirst().orElse(null));
			LOG.appLogger().info("JSON structure: " + jsonInfo);
			JsonNode currentDate = mpr.readTree(jsonInfo);
			JsonNode idReceiver = mpr.readTree(jsonInfo);
			JsonNode firstNameReceiver = mpr.readTree(jsonInfo);
			JsonNode lastNameReceiver = mpr.readTree(jsonInfo);
			JsonNode professionReceiver = mpr.readTree(jsonInfo);
			JsonNode isExceptedReceiver = mpr.readTree(jsonInfo);
			JsonNode salaryReceiver = mpr.readTree(jsonInfo);
			JsonNode cnpReceiver = mpr.readTree(jsonInfo);
			JsonNode genderReceiver = mpr.readTree(jsonInfo);
			JsonNode fullTimeReceiver = mpr.readTree(jsonInfo);
			JsonNode aditionalInfoReceiver = mpr.readTree(jsonInfo);
			JsonNode addressReceiverCountry = mpr.readTree(jsonInfo);
			JsonNode addressReceiverCounty = mpr.readTree(jsonInfo);
			JsonNode addressReceiverCity = mpr.readTree(jsonInfo);
			JsonNode addressReceiverStreet = mpr.readTree(jsonInfo);
			JsonNode addressReceiverNumber = mpr.readTree(jsonInfo);
			LOG.appLogger().warn("Saving data to database begun: " + currentDate.get("currentDate").asText());
			employeeService.saveInitiaInfos(new EmployeeInitialSavedData(idReceiver.get("id").asInt(),
					firstNameReceiver.get("name").asText(), lastNameReceiver.get("second_name").asText(),
					professionReceiver.get("profession").asText(),
					ApplicationStaticInfo.setExceptValue(isExceptedReceiver.asBoolean()),
					salaryReceiver.get("salary").asDouble(), cnpReceiver.get("cnp").asText(),
					genderReceiver.get("gender").asText(),
					ApplicationStaticInfo.setFullTimeValue(fullTimeReceiver.asBoolean()),
					aditionalInfoReceiver.get("aditionInfo").asText(), new Date(),
					new Address(addressReceiverCountry.get("addressCountry").asText(),
							addressReceiverCounty.get("addressCounty").asText(), addressReceiverCity.get("addressCity").asText(),
							addressReceiverStreet.get("addressStreet").asText(),
							addressReceiverNumber.get("addressNumber").asText())));
			System.out.println("Calculate taxes: " + employeeService.calculateTaxes());
			employeeService.saveProcessedInfos(new EmployeeProcessedData(idReceiver.get("id").asInt(),
					firstNameReceiver.get("name").asText(), lastNameReceiver.get("second_name").asText(),
					professionReceiver.get("profession").asText(),
					ApplicationStaticInfo.setFullTimeValue(isExceptedReceiver.asBoolean()),
					getFinalRevenue(employeeService.findAll().size()), cnpReceiver.get("cnp").asText(),
					genderReceiver.get("gender").asText(),
					ApplicationStaticInfo.setFullTimeValue(fullTimeReceiver.asBoolean()),
					aditionalInfoReceiver.get("aditionInfo").asText(), new Date(),
					new Address(addressReceiverCountry.get("addressCountry").asText(),
							addressReceiverCounty.get("addressCounty").asText(), addressReceiverCity.get("addressCity").asText(),
							addressReceiverStreet.get("addressStreet").asText(),
							addressReceiverNumber.get("addressNumber").asText())));
			System.out.println("Saving data for ticket salaryem files");
			CreateFiles.createFiles(idReceiver.get("id").asInt(), firstNameReceiver.get("name").asText(), professionReceiver.get("profession").asText(), 
					salaryReceiver.get("salary").asText(), isExceptedReceiver.asBoolean());
			employeeService.printTaxes();
			mpr.writeValueAsString(
					service.getModel().stream().parallel().filter(id -> id.getId() == idValue).findAny().orElse(null));
			modelAndView.addObject("id", idReceiver.get("id").asText());
			modelAndView.addObject("currentDate", currentDate.get("currentDate").asText());
			modelAndView.addObject("name", firstNameReceiver.get("name").asText());
			modelAndView.addObject("second_name", lastNameReceiver.get("second_name").asText());
			modelAndView.addObject("profession", professionReceiver.get("profession").asText());
			modelAndView.addObject("isExcept", isExceptedReceiver.get("except").asText());
			modelAndView.addObject("salary", salaryReceiver.get("salary").asText());
			modelAndView.addObject("cnp", cnpReceiver.get("cnp").asText());
			modelAndView.addObject("gender", genderReceiver.get("gender").asText());
			modelAndView.addObject("fullTime", fullTimeReceiver.get("fulltime").asText());
			modelAndView.addObject("adition_info", aditionalInfoReceiver.get("aditionInfo").asText());
			return modelAndView;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			LOG.appLogger().error("JSON PARSING ERROR WITH ROOT CAUSE: ", e.getCause().toString());
			return modelAndView.addObject("Err", "FAIL");
		} catch (IOException e) {
			e.printStackTrace();
			LOG.appLogger().error("MAJOR SYSTEM FAILURE WITH ROOT CAUSE: ", e.getLocalizedMessage());
			return modelAndView.addObject("Err", "FAIL");
		}
	}

	private double getFinalRevenue(int index) {
		return employeeService.findAll().get(index - 1).getSalary() - employeeService.calculateTaxes().get(index - 1);
	}

}
