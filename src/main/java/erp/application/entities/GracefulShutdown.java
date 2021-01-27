package erp.application.entities;

import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GracefulShutdown {
	
	@Bean
	public GracefulShutdown gracefulShutdown() {
		return new GracefulShutdown();
	}
	
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory(final TomcatContextCustomizer tomcatContextCustomizers) {
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		serverFactory.addContextCustomizers(tomcatContextCustomizers);
		return serverFactory;
	}

}
