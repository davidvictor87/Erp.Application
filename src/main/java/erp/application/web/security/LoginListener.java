package erp.application.web.security;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import erp.application.entities.LOG;
import erp.application.entities.Marked;

@Component
public class LoginListener implements ApplicationListener<ApplicationEvent> {

	@Override
	public void onApplicationEvent(ApplicationEvent evt) {
		try {
			if (evt instanceof AuthenticationSuccessEvent) {
				LOG.appLogger().info("Login Event Recorded");
				//checkIfAppIsRunning();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*@Marked
	public void checkIfAppIsRunning() {
		try {
			LOG.appLogger().info(" == is running ==");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
