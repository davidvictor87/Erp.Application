package erp.application.entities.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class EnableAOP {
	
	@Bean
	public AspectDTasks configDTasks() {
		return new AspectDTasks();
	}

}
