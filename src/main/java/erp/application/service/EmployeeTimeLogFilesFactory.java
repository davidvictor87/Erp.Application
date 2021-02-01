package erp.application.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.catalina.connector.Connector;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;

@Service
public class EmployeeTimeLogFilesFactory implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

	private volatile Connector connector;
	private static final int TIMEOUT = 30;

	public synchronized void employeeTimeCounter(Authentication auth) {
		Logger logger = Logger.getLogger("erp.application.service");
		FileHandler employeeWorkTimeLog = null;
		auth = SecurityContextHolder.getContext().getAuthentication();
		LOG.appLogger().debug("Start recording data");
		try {
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				String userName = auth.getName();
				Instant startTimeCounter = Instant.now();
				Instant endTimeCounter = Instant.now();
				final long totalWorkTime = Duration.between(startTimeCounter, endTimeCounter).toMillis();
				employeeWorkTimeLog = new FileHandler(ApplicationStaticInfo.EMPLOYEE_LOG_DIRECTORY + userName
						+ ApplicationStaticInfo.EMPLOYEE_LOG_FILE_EXTENSION, true);
				employeeWorkTimeLog.setFormatter(new SimpleFormatter());
				logger.info(Level.INFO.toString());
				logger.info("User Name " + userName);
				logger.fine(userName + "user details");
				logger.fine(String.valueOf(totalWorkTime));
				logger.addHandler(employeeWorkTimeLog);
			} else {
				LOG.appLogger().error("FAILED TO RETRIEVE LOGGED USER");
			}
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
			LOG.appLogger().error("Major Security Exception and/or IOException");
		} finally {
			employeeWorkTimeLog.flush();
			employeeWorkTimeLog.close();
		}
	}

	@Bean
	public Connector con() {
		return connector;
	}

	@Override
	public void customize(Connector connector) {
		this.connector = connector;
	}

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			this.connector.pause();
			final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Executor threadExecutor = this.connector.getProtocolHandler().getExecutor();
			if (threadExecutor instanceof ThreadPoolExecutor) {
				try {
					ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) threadExecutor;
					threadPoolExecutor.shutdown();
					if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS)) {
						LOG.appLogger().info("Proceeding with forceful shutdown");
						threadPoolExecutor.shutdownNow();
						employeeTimeCounter(auth);
						if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS)) {
							LOG.appLogger().error("FAILED TO SHUTDOWN");
						}
					}
				} catch (InterruptedException | NullPointerException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}
	}

}
