package erp.application.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import erp.application.employee.model.Employee;
import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;

@Service
@PropertySource("classpath:application.properties")
public class ConnectionService {

	@Value("${import.endpoint.user}")
	private String user;
	@Value("${import.endpoind.password}")
	private String password;

	private String returnModel() {
		try {
			ResponseEntity<String> responseObject = getRestTemplate().exchange(ApplicationStaticInfo.IMPORT_JSON_DATA + "/" + "user10" + "/" + "KYV54V", 
					HttpMethod.GET, getHttpEntity(), String.class);
			return responseObject.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.appLogger().error("Failure with root cause", e);
			return "ERROR";
		}
	}

	private HttpEntity<String> getHttpEntity() {
		try {
			HttpEntity<String> response = getRestTemplate().exchange(ApplicationStaticInfo.IMPORT_JSON_DATA + "/" + "user10" + "/" + "KYV54V", 
					HttpMethod.GET, null, String.class);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + response.getHeaders().getFirst("JWTToken"));
			return new HttpEntity<String>(headers);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.appLogger().error("Failure with root cause", e);
			return null;
		}
	}

	private final RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Secured(value="{ROLE_ADMIN, ROLE_MANAGER, ROLE_USER}")
	public List<Employee> getModel() {
		ObjectMapper mapper = new ObjectMapper();
		List<Employee> model = null;
		try {
			model = Arrays.asList(mapper.readValue(returnModel(), Employee[].class));
		} catch (IOException e) {
			LOG.appLogger().error("Failure with root cause", e);
			e.printStackTrace();
		}
		return model;
	}

}
