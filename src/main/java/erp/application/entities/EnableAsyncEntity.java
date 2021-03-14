package erp.application.entities;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class EnableAsyncEntity {

	@Bean(name = "findProductThread")
	public Executor findProductTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.initialize();
		return executor;
	}

	@Bean(name = "saveProduct")
	public Executor saveProductExecutor() {
		return new ThreadPoolTaskExecutor();
	}

	@Bean(name = "updateProduct")
	public Executor updateProductExecutor() {
		return new ThreadPoolTaskExecutor();
	}

	@Bean(name = "returnAllProducts")
	public Executor returnAllProducts() {
		return new ThreadPoolTaskExecutor();
	}

	@Bean(name = "deleteProduct")
	public Executor deleteAllProducts() {
		return new ThreadPoolTaskExecutor();
	}

}
