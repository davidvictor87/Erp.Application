package erp.application;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.scheduling.annotation.EnableScheduling;

import erp.application.entities.ApplicationStaticInfo;
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
			File scriptFile = new File(ApplicationStaticInfo.startScriptPath);
			if (!scriptFile.exists()) {
				scriptFile.createNewFile();
			}
			Scanner sc = new Scanner(scriptFile.getAbsoluteFile(), "utf-8");
			Runtime runtime = Runtime.getRuntime();
			StringBuilder sb = new StringBuilder();
			while (sc.hasNext()) {
				sb.append(sc.next());
			}
			System.out.println(sb.toString());
			Process process = runtime.exec(sb.toString());
			sc.close();
			LOG.appLogger().info("Exit Value: " + process.exitValue());
		} catch (IOException e) {
			LOG.appLogger().error(e.getMessage());
		}
	}
}
