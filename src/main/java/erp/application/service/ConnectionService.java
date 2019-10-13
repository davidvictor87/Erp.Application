package erp.application.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import erp.application.enteties.models.Employee_Model;
import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;

@Service
public class ConnectionService {

	@Value("{import.endpoint.user}")
	private String user;
	@Value("{import.endpoind.password}")
	private String password;

	private String returnModel() {
		try {
			ResponseEntity<String> responseObject = getRestTemplate().exchange(ApplicationStaticInfo.IMPORT_JSON_DATA + "/" + user + "/" + password, 
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
			HttpEntity<String> response = getRestTemplate().exchange(ApplicationStaticInfo.IMPORT_JSON_DATA,
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

	private RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public List<Employee_Model> getModel() {
		ObjectMapper mapper = new ObjectMapper();
		List<Employee_Model> model = null;
		try {
			model = Arrays.asList(mapper.readValue(returnModel(), Employee_Model[].class));
		} catch (IOException e) {
			LOG.appLogger().error("Failure with root cause", e);
			e.printStackTrace();
		}
		return model;
	}

}
