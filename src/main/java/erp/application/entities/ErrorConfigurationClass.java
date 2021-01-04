package erp.application.entities;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
@Profile(ApplicationStaticInfo.APPLICATION_ERROR_PROFILE)
public class ErrorConfigurationClass {
	
	public ErrorConfigurationClass() {
		super();
		LOG.appLogger().error("App Default Error Thrown", new RuntimeException());
	}
	
	@Bean(value = "simpleMappingException")
	public SimpleMappingExceptionResolver createSimpleMappingException() {
		LOG.appLogger().info("Throw simple mapping exception");
		SimpleMappingExceptionResolver errorResolver = new SimpleMappingExceptionResolver();
		Properties properrties = new Properties();
		properrties.setProperty("Application Default Error", "SimpleMappingException");
		errorResolver.setExceptionMappings(properrties);
		errorResolver.setExceptionAttribute("err");
		errorResolver.setWarnLogCategory("LOG");
		errorResolver.setDefaultErrorView("error");
		return errorResolver;
	}

}
