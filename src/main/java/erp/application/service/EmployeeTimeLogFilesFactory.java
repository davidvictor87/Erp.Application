package erp.application.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.slf4j.event.Level;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

@Service
public class EmployeeTimeLogFilesFactory {
	
	public void employeeTimeCounter(Authentication auth) {
		Logger logger = Logger.getLogger("Employee");
		FileHandler employeeWorkTimeLog = null;
		SimpleFormatter formatter = null;
		auth = SecurityContextHolder.getContext().getAuthentication();
		LOG.appLogger().debug("Start recording data");
		try {
			Object userPrincipal = auth.getPrincipal();
			String userName = "";
			if(userPrincipal instanceof DefaultOidcUser && auth != null) {
				userName = ((DefaultOidcUser) userPrincipal).getName();
			}else {
				throw new RuntimeException("USER IS NULL");
			}
			Instant startTimeCounter = Instant.now();
			Instant endTimeCounter = Instant.now();
			final long totalWorkTime = Duration.between(startTimeCounter, endTimeCounter).toMillis();
			employeeWorkTimeLog = new FileHandler(ApplicationStaticInfo.EMPLOYEE_LOG_DIRECTORY + userName + ApplicationStaticInfo.EMPLOYEE_LOG_FILE_EXTENSION);
			formatter = new SimpleFormatter();
			employeeWorkTimeLog.setFormatter(formatter);
			logger.info(Level.INFO.toString());
			logger.fine(userName);
			logger.fine(String.valueOf(totalWorkTime));
			logger.addHandler(employeeWorkTimeLog);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
			LOG.appLogger().error("Major Security Exception and/or IOException");
		}finally {
			employeeWorkTimeLog.flush();
			employeeWorkTimeLog.close();
		}
	}

}
