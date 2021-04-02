package erp.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "erp.application")
@EnableScheduling
public class ErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpApplication.class, args);
		System.out.println(System.getProperty("java.runtime.version"));
		System.out.println("Spring Version: " + SpringVersion.getVersion());
		System.out.println("======= Application Started =============");
	}

}
