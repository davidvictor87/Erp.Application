package erp.application.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import erp.application.entities.LOG;
import erp.application.service.EmployeeTimeLogFilesFactory;

@Component
public class LoginListener implements ApplicationListener<ApplicationEvent> {

	@Autowired
	private EmployeeTimeLogFilesFactory logFactory;

	@Override
	public void onApplicationEvent(ApplicationEvent evt) {
		try {
			if (evt instanceof AuthenticationSuccessEvent) {
				LOG.appLogger().info("Login Event Recorded");
				logFactory.initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
