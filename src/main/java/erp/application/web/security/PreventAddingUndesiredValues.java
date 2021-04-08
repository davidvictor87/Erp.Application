package erp.application.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import erp.application.entities.LOG;

@Component(value = "undisered")
public class PreventAddingUndesiredValues {
	
	private boolean isOk = true;
	
	public boolean preventAdd(final String undiseredValues) {
		LOG.appLogger().warn("Received username: " + undiseredValues);
		List<String> unwantedUsernames = new ArrayList<>();
		unwantedUsernames.add("Testache");
		unwantedUsernames.add("Bubulache");
		unwantedUsernames.stream().forEach(name -> {
			if(name.equalsIgnoreCase(undiseredValues)) {
				LOG.appLogger().info(" == Unacceptable Username ==");
				isOk = false;
				return;
			}
		});
		LOG.appLogger().warn("Return Value, isOk: " + isOk);
		return isOk;
	}

}
