package erp.application.entities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppInfoData implements EnvironmentAware{
	
	private Environment environment;
	
	@Autowired
	@Override
	public void setEnvironment(Environment env) {
		this.environment = env;		
	}
	
	private Environment getEnvironment() {
		if(environment == null) {
			throw new RuntimeException(" === Environment is not set ===");
		}else
			return environment;
	}
	
	@Bean( name = "InfoData")
	public void startInfoData() throws IOException {
		System.out.println(" ====== Info Data ======");
		SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		final String startAppData = getEnvironment().getProperty("spring.application.name") + ":" + date.format(timeStamp);
		File file = new File("startAppData.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		Files.write(file.toPath(), startAppData.getBytes(), StandardOpenOption.WRITE);
		LOG.appLogger().debug(" === Recording Start Log ====");
	}

}
