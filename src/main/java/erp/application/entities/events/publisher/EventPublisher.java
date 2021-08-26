package erp.application.entities.events.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import erp.application.entities.events.registration.CustomRegistration;

@Component
public class EventPublisher {
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	public void publishEvent(String name, boolean isEdited) {
		System.out.println("Name event: " + name);
		eventPublisher.publishEvent(new CustomRegistration(name, isEdited));
	}

}
