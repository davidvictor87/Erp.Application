package erp.application.entities;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class EnableAsyncEntity {
	
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		 executor.setCorePoolSize(2);
		 executor.setMaxPoolSize(2);
		 executor.setQueueCapacity(500);
		 executor.setThreadNamePrefix("Products-");
		 executor.initialize();
		return executor;
	}

}
