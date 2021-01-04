package erp.application.entities;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ResponseDataControllerAdvice {
	
	@Autowired(required=false)
	private ExampleSimpleMappingExceptionResolver defaultError;
	
	@ModelAttribute("timestamp")
	public String getTimestamp() {
		return new Date().toString();
	}
	
	@ModelAttribute("switchState")
	public String getSwitchState() {
		if (defaultError == null)
			return "off";
		else {
			return "on";
		} 
	}
}
