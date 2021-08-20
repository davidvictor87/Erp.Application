package erp.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.SpringVersion;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "erp.application")
@EnableScheduling
@EnableAsync(proxyTargetClass=true)
public class ErpApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ErpApplication.class);
		application.setWebApplicationType(WebApplicationType.REACTIVE);
		SpringApplication.run(ErpApplication.class, args);
		System.out.println(System.getProperty("java.runtime.version"));
		System.out.println("Spring Version: " + SpringVersion.getVersion());
		System.out.println("======= Application Started =============");
	}

}
