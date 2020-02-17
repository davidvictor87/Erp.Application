package erp.application;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.scheduling.annotation.EnableScheduling;

import erp.application.entities.LOG;

@SpringBootApplication(scanBasePackages = "erp.application")
@EnableScheduling
public class ErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpApplication.class, args);
		System.out.println(System.getProperty("java.runtime.version"));
		System.out.println("Spring Version: " + SpringVersion.getVersion());
		System.out.println("======= Application Started =============");
		startApp();
	}

	private static final void startApp() {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("D:/Workspace/generate.json.list/src/main/java/generate/json/list>javac Application.java");
			LOG.appLogger().info("Exit Value: " + process.exitValue());
		} catch (IOException e) {
			LOG.appLogger().error(e.getMessage());
		}
	}
}
