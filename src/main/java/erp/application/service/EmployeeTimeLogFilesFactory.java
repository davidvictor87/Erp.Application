package erp.application.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
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
	private boolean isAuthenticated;
	private static final Object LOCK = new Object();

	public void employeeTimeCounter(Authentication auth) {
		synchronized (LOCK) {
			LogManager logManager = LogManager.getLogManager();
			final String logName = Logger.GLOBAL_LOGGER_NAME;
			Logger logger = logManager.getLogger(logName);
			logger.setLevel(java.util.logging.Level.ALL);
			FileHandler employeeWorkTimeLog = null;
			auth = SecurityContextHolder.getContext().getAuthentication();
			LOG.appLogger().debug("Start recording data");
			Instant startTimeCounter = Instant.now();
			Instant endTimeCounter = Instant.now();
			final long totalWorkTime = Duration.between(startTimeCounter, endTimeCounter).toMillis();
			boolean b = true;
			isAuthenticated = b ? !(auth instanceof AnonymousAuthenticationToken)
					: (auth instanceof AnonymousAuthenticationToken);
			try {
				if (isAuthenticated) {
					String userName = auth.getName();
					employeeWorkTimeLog = new FileHandler(ApplicationStaticInfo.EMPLOYEE_LOG_DIRECTORY + userName
							+ ApplicationStaticInfo.EMPLOYEE_LOG_FILE_EXTENSION, true);
					SimpleFormatter formatter = new SimpleFormatter();
					employeeWorkTimeLog.setFormatter(formatter);
					employeeWorkTimeLog.setLevel(java.util.logging.Level.FINE);
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
			logger.log(java.util.logging.Level.INFO, "Working Time: " + totalWorkTime);
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
		Connector connection = null;
		if (event.getApplicationContext().getParent() == null) {
			connection = new Connector();
			try {
				connection = new Connector();
				connection.init();
				connection.pause();
			} catch (LifecycleException ex) {
				ex.printStackTrace();
			}
			connection.pause();
			final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Executor threadExecutor = connection.getProtocolHandler().getExecutor();
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
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}
	}

}
