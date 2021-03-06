package erp.application.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LOG {
	
	private static final Logger APP_LOGGER = LoggerFactory.getLogger(LOG.class);
	
	public static final Logger appLogger() {
		return APP_LOGGER;
	}

}
