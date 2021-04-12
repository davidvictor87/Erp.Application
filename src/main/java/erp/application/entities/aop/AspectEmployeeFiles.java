package erp.application.entities.aop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;

import erp.application.entities.LOG;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;

@Aspect
public class AspectEmployeeFiles {

	private int preventMultipleAccessing = 0;
	private File logFile = null;
	private Instant recordLoggedInTime = null;
	private Instant recordLogoutTime = null;

	@Around(value = "execution(* erp.application.web.security.LoginListener.onApplicationEvent(..))")
	public void loginEventListener() throws IOException {
		ExecutorService exec = Executors.newSingleThreadExecutor();
		try {
			boolean enable = SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !String
					.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()).equals("anonymousUser");
			String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
			if (enable) {
				recordLoggedInTime = Instant.now();
				exec.execute(() -> {
					logFile = new File(loggedUser + ".log");
					try {
						if (!logFile.exists()) {
							logFile.createNewFile();
						}
						preventMultipleAccessing++;
						try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
							if (preventMultipleAccessing == 1) {
								LOG.appLogger().info("Logged User: " + loggedUser);
								bw.write("User logged in at: "
										+ new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + ", for: "
										+ recordLoggedInTime.toEpochMilli());
								bw.newLine();
								bw.flush();
								bw.close();
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		} catch (Exception e) {
		} finally {
			exec.shutdown();
		}
	}

	@Around(value = "execution(* erp.application.start.StartPage.signOut(..))")
	public void logoutEvent() {
		LOG.appLogger().info(" === LOGOUT EVENT INTERCEPTED ===");
		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		recordLogoutTime = Instant.now();
		ExecutorService exec = Executors.newSingleThreadExecutor();
		try {
			exec.execute(() -> {
				long timeWorked = Duration.between(recordLoggedInTime, recordLogoutTime).getSeconds();
				try {
					final String writeMessage = "User logout at: "
							+ new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + ", for: "
							+ recordLogoutTime.toEpochMilli() + ", total time worked: " + timeWorked
							+ System.getProperty("line.separator");
					Path path = Paths.get(new File(loggedUser + ".log").getAbsolutePath());
					Files.write(path, writeMessage.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
					preventMultipleAccessing = 0;
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			exec.shutdown();
		}
	}

}
