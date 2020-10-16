package erp.application.controller;

import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

@Controller
@RequestMapping(value="/employee/access")
public class ConnectionController {

    private ConnectionService service;
	private EmployeeService employeeService;

	@Autowired
	public ConnectionController(ConnectionService serv, EmployeeService eService) {
		this.service = serv;
		this.employeeService = eService;
	}

	@GetMapping(value = "/get/{id}")
	@ResponseBody
	public String connectionMethod(@PathVariable(value = "id") final int idValue) {
		System.out.println("Service info: " + service.getModel() + " : " + idValue);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("return.html");
		ObjectMapper mpr = new ObjectMapper();
		try {
			String jsonInfo = mpr.writeValueAsString(
					service.getModel().parallelStream().filter(id -> id.getId() == idValue).findFirst().orElse(null));
			LOG.appLogger().info("JSON structure: " + jsonInfo);
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
			LOG.appLogger().warn("Saving data to database begun: ");
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
			return mpr.writeValueAsString(
					service.getModel().stream().parallel().filter(id -> id.getId() == idValue).findAny().orElse(null));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			LOG.appLogger().error("JSON PARSING ERROR WITH ROOT CAUSE: ", e.getCause().toString());
			return "FAIL";
		} catch (IOException e) {
			e.printStackTrace();
			LOG.appLogger().error("MAJOR SYSTEM FAILURE WITH ROOT CAUSE: ", e.getLocalizedMessage());
			return "FAIL";
		}
	}

	private double getFinalRevenue(int index) {
		return employeeService.findAll().get(index - 1).getSalary() - employeeService.calculateTaxes().get(index - 1);
	}

}
