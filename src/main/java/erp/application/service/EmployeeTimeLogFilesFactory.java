package erp.application.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

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
import erp.application.entities.StaticShutdownCallbackRegistry;

@Service
public class EmployeeTimeLogFilesFactory implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

	private volatile Connector connector;
	private static final int TIMEOUT = 30;
	private boolean isAuthenticated;
	private static final Object LOCK = new Object();

	static {
		System.setProperty("log4j.shutdownCallbackRegistry", "com.djdch.log4j.StaticShutdownCallbackRegistry");
	}

	public void shutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("Invoked");
				StaticShutdownCallbackRegistry.invoke();
			}
		}));
	}

	public void employeeTimeCounter(Authentication auth) {
		synchronized (LOCK) {
			auth = SecurityContextHolder.getContext().getAuthentication();
			LOG.appLogger().debug("Start recording data");
			Instant startTimeCounter = Instant.now();
			Instant endTimeCounter = Instant.now();
			final long totalWorkTime = Duration.between(startTimeCounter, endTimeCounter).toMillis();
			Logger logger = Logger.getLogger("AppLog");
			Appender appender = null;
			boolean b = true;
			isAuthenticated = b ? !(auth instanceof AnonymousAuthenticationToken)
					: (auth instanceof AnonymousAuthenticationToken);
			try {
				if (isAuthenticated) {
					final String userName = auth.getName();
					appender = new FileAppender(new SimpleLayout(), ApplicationStaticInfo.EMPLOYEE_LOG_DIRECTORY
							+ userName + ApplicationStaticInfo.EMPLOYEE_LOG_FILE_EXTENSION);
					logger.addAppender(appender);
					appender.setLayout(new SimpleLayout());
					logger.info("Time Recorded: " + totalWorkTime);
				} else {
					LOG.appLogger().error("FAILED TO RETRIEVE LOGGED USER");
				}
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
				LOG.appLogger().error("Major Security Exception and/or IOException");
			} finally {
				LOG.appLogger().info("Shut Down Hook Invoked");
				shutDownHook();
				appender.close();
			}
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
						if (threadPoolExecutor.shutdownNow() != null) {
							employeeTimeCounter(auth);
						}
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
