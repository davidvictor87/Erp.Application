package erp.application.h2.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigH2Web {
	
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public ServletRegistrationBean servletRegistration(){
		ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console-h2/*");
        return registrationBean;
    }

}
