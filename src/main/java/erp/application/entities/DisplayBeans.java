package erp.application.entities;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisplayBeans {
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext appContext) {
		return args -> {
			LOG.appLogger().info(" === Display Loaded Beans ===");
			String [] beans = appContext.getBeanDefinitionNames();
			Arrays.sort(beans);
			for(String bName : beans) {
				LOG.appLogger().info("Loaded Beans: " + bName);
			}
		};
	}

}
