package erp.application.entities.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import erp.application.entities.LOG;

@Aspect
@Component
public class AspectDTasks {
	
	@Before(value="execution(* erp.application.service.EmployeeService.*(..)) and args(id))")
	public void beforeAdvice(JoinPoint joinPoit, String id) {
		Signature signature = joinPoit.getSignature();
		LOG.appLogger().info("LOG from Aspect: " + signature.getDeclaringTypeName() + ", ID: " + id);
	}
	
	@After(value="execution(* erp.application.service.EmployeeService.*(..)) and args(id))")
	public void afterAdvice(JoinPoint joinPoint, String id) {
		LOG.appLogger().info("LOG: " + joinPoint.getSignature() + ", ID: " + id);
	}

}
