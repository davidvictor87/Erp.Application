package erp.application.entities.tomcat.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import erp.application.entities.LOG;

@Configuration
@EnableAutoConfiguration
@ConditionalOnWebApplication(type = Type.SERVLET)
public class TomcatConfig {
	
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			public void postProcessContext(Context context) {
				SecurityConstraint constraint = new SecurityConstraint();
				constraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection securityCollection = new SecurityCollection();
				securityCollection.addPattern("/*");
				constraint.addCollection(securityCollection);
				context.addConstraint(constraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(new Connector[] {this.redirectConnector()});
		LOG.appLogger().debug(" === Custom Tomcat Config Class ===");
		return tomcat;
	}
	
	private Connector redirectConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8088);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}

}
