package erp.application.entities.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import erp.application.entities.LOG;

@Aspect
public class AspectDTasks {
	
	@Pointcut(value="@annotation(erp.application.entities.Marked) && execution(* erp.application.controller.ConnectionController.deleteAllRecords(..))")
	public void pointCut() {
		LOG.appLogger().info("PointCut called");
	}
	
	@Around(value="pointCut()")
	public void around(JoinPoint joinPoit) {
		Signature signature = joinPoit.getSignature();
		LOG.appLogger().info("LOG from Around: " + signature.getDeclaringTypeName());
	}
	
	@After(value="pointCut()")
	public void afterAdvice(JoinPoint joinPoint) {
		LOG.appLogger().info("LOG: " + joinPoint.getSignature());
	}
	
	@AfterThrowing(value="pointCut()")
	public void afterThowing() {
		LOG.appLogger().info("Error Encountered");
	}
	
	@AfterReturning("pointCut()")
	public void afterReturn() {
		LOG.appLogger().info("After Return");
	}
	
}
