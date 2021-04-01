package erp.application.web.security;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import erp.application.entities.LOG;

@Component
public class LoginListener implements ApplicationListener<ApplicationEvent> {

	//
	@Override
	public void onApplicationEvent(ApplicationEvent evt) {
		try {
			if (evt instanceof AuthenticationSuccessEvent) {
				LOG.appLogger().info("Login Event Recorded");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
