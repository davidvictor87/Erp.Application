package erp.application.entities.aop;

import java.io.File;

import org.aspectj.apache.bcel.classfile.Field;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;

import erp.application.entities.LOG;
import io.jsonwebtoken.io.IOException;

@Aspect
public class AspectEmployeeFiles {

	private int i = 0;

	@Around(value = "execution(* erp.application.web.security.LoginListener.onApplicationEvent(..))")
	public void loginEventListener() throws IOException{
		i++;
		LOG.appLogger().info("Event Recorded: " + i);
		try {
			if (i == 17) {
				File logFile = new File(SecurityContextHolder.getContext().getAuthentication().getName() + ".log");
				if (logFile.exists()) {
					logFile.createNewFile();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Before(value = "execution(* erp.application.start.StartPage.logoutUser(..))")
	public void logoutEvent() {
		System.out.println("Logout");
	}

}
