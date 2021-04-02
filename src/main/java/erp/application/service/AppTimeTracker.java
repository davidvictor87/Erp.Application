package erp.application.service;

import java.io.File;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import erp.application.entities.LOG;

@Component
public class AppTimeTracker {
	
	private final File appLoggingTime = new File("appLog.txt");

	@PostConstruct
	public void startRecordingAppTime() {
		LOG.appLogger().info("App Started at: " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		try {
			if (!appLoggingTime.exists()) {
				appLoggingTime.createNewFile();
			}
			System.out.println("File length: " + appLoggingTime.length());
			Path filePath = Paths.get(appLoggingTime.getAbsolutePath());
			AsynchronousFileChannel writeAsyncData = AsynchronousFileChannel.open(filePath, StandardOpenOption.WRITE);
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			final String startTime = "Application started at " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			buffer.put(startTime.getBytes());
			buffer.flip();
			Future<FileLock> fileLock = writeAsyncData.lock();
			FileLock lock = fileLock.get();
			Future<Integer> asyncWriter = null;
			if (lock.isValid()) {
				if (appLoggingTime.length() == 0) {
					asyncWriter = writeAsyncData.write(buffer, 0);
				}
				asyncWriter = writeAsyncData.write(buffer, appLoggingTime.length() + 2);
				asyncWriter.get();
				writeAsyncData.close();
				buffer.clear();
			}
		} catch (IOException | ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void recordAppShutDownTime() {
		LOG.appLogger().info("App shutdown at: " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		try {
			Path filePath = Paths.get(appLoggingTime.getAbsolutePath());
			AsynchronousFileChannel writeAsyncData = AsynchronousFileChannel.open(filePath, StandardOpenOption.WRITE);
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			final String endTime = "Application shutdown at " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			buffer.put(endTime.getBytes());
			buffer.flip();
			Future<FileLock> fileLock = writeAsyncData.lock();
			FileLock lock = fileLock.get();
			Future<Integer> asyncWriter = null;
			if (lock.isValid()) {
				if (appLoggingTime.length() == 0) {
					asyncWriter = writeAsyncData.write(buffer, 0);
				}
				asyncWriter = writeAsyncData.write(buffer, appLoggingTime.length() + 2);
				asyncWriter.get();
				writeAsyncData.close();
				buffer.clear();
			}
		} catch (IOException | ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
