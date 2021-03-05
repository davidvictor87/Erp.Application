package erp.application.entities.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import erp.application.entities.LOG;

@Aspect
@Component
public class AspectDTasks {
	
	//@Before(value="")
	public void beforeAdvice(JoinPoint joinPoit) {
		LOG.appLogger().info("LOG: " + joinPoit.getSignature());
	}
	
	//@After(value="")
	public void afterAdvice(JoinPoint joinPoint) {
		LOG.appLogger().info("LOG: " + joinPoint.getSignature());
	}

}
