package erp.application.entities;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class StartScripts implements Callable<Object> {

	private static final int NUMBER_OF_THREADS = 2;
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	@Override
	public Object call() {
		try {
			Thread.sleep(1000);
			startApp();
			startRedisServer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Thread.currentThread().getName();
	}

	@PostConstruct
	public void startMethod() throws InterruptedException, ExecutionException {
		ExecutorService exec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		Callable<Object> callable = new StartScripts();
		Future<Object> future = exec.submit(callable);
		future.get();
		exec.shutdown();
	}

	private void startApp() {
		lock1.lock();
		try {
			File scriptFile = new File(ApplicationStaticInfo.START_SCRIPT_PATH);
			if (!scriptFile.exists()) {
				scriptFile.createNewFile();
			}
			Scanner sc = new Scanner(scriptFile.getAbsoluteFile(), "utf-8");
			sc.useDelimiter("\\n");
			Runtime runtime = Runtime.getRuntime();
			StringBuilder sb = new StringBuilder();
			while (sc.hasNext()) {
				sb.append(sc.next());
			}
			System.out.println(sb.toString());
			Process process = runtime.exec(sb.toString());
			sc.close();
			LOG.appLogger().info("Exit Value: " + process.exitValue());
		} catch (IOException e) {
			LOG.appLogger().error(e.getMessage());
		} finally {
			lock1.unlock();
		}
	}

	private void startRedisServer() {
		lock2.lock();
		LOG.appLogger().info(" === Start Redis Server ===");
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(ApplicationStaticInfo.START_REDIS_SERVER_SCRIPT);
			if (!process.isAlive()) {
				LOG.appLogger().info("Process executed with status: " + process.exitValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			lock2.unlock();
		}
	}

}
