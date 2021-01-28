package erp.application.entities;

import org.apache.catalina.Context;
import org.apache.tomcat.websocket.server.WsSci;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GracefulShutdown {
	
	
	@Bean(name="getTomcatCustomizers")
	public TomcatContextCustomizer getCustomisers() {
		return new TomcatContextCustomizer() {
			
			@Override
			public void customize(Context context) {
				context.addServletContainerInitializer(new WsSci(), null);
			}
		};
	}
	
	@Bean(name="silentShutdown")
	public GracefulShutdown gracefulShutdown() {
		return new GracefulShutdown();
	}
	
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory(TomcatContextCustomizer customizers) {
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		serverFactory.addContextCustomizers(customizers);
		return serverFactory;
	}

}
