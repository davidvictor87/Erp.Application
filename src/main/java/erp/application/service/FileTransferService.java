package erp.application.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import erp.application.entities.LOG;
import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.CopyAndUpdateFile;

@Service
public class FileTransferService {
    
	private CopyAndUpdateFile cauf = new CopyAndUpdateFile();
	private final Object lock = new Object();
	private static final int NUMBERS_OF_SCHEDULED_THREADS = 1;

	@Scheduled(fixedRate = 1000L)
	@Secured(value="{ROLE_ADIN, ROLE_MANAGER, ROLE_USER}")
	public void startWatch() throws IOException, InterruptedException, ExecutionException {
		final String path = "D:/SourceDirectory/";
		String fileName = "";
		File file = new File(path);
		File[] listOfFiles = file.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile())
				fileName = listOfFiles[i].getName();
			System.out.println("File Name: " + listOfFiles[i].getName());
		}
		LOG.appLogger().info(
				"Import process started for file : " + new File("D:/SourceDirectory/" + fileName).getCanonicalPath());
		if (Files.exists(Paths.get(path))) {
			ScheduledExecutorService scheduledExecutorService = Executors
					.newScheduledThreadPool(NUMBERS_OF_SCHEDULED_THREADS);
			ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(new Callable<Object>() {
				public Object call() throws Exception {
					watchMethod();
					return "Called";
				}
			}, 1, TimeUnit.SECONDS);
			scheduledFuture.get();
			scheduledExecutorService.shutdown();
		}
	}

	private void watchMethod() {
		LOG.appLogger().warn("=== Watch Method Called ===");
		String value = "";
		final String watchedDirectory = ApplicationStaticInfo.SOURCE_DIRECTORY;
		try (WatchService service = FileSystems.getDefault().newWatchService()) {
			Map<WatchKey, Path> keyMap = new HashMap<>();
			Path path = Paths.get(watchedDirectory).toAbsolutePath();
			LOG.appLogger().debug("The path is: " + path.toString());
			keyMap.put(path.register(service, StandardWatchEventKinds.ENTRY_CREATE), path);
			WatchKey watchKey;
			do {
				watchKey = service.take();
				for (WatchEvent<?> event : watchKey.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();
					Path eventPath = (Path) event.context();
					if (kind == StandardWatchEventKinds.ENTRY_CREATE || isDirectoryEmpty(path)) {
						value = path.toAbsolutePath().toString() + "/" + eventPath.toString();
						System.out.println("Value: " + value);
						Thread.sleep(1000);
						writeFile(value);
					}
				}
			} while (watchKey.reset());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void writeFile(String value) {
		LOG.appLogger().info("=== Write File Started ===");
		if (!value.equals(null)) {
			synchronized (lock) {
				final String fileDestination = ApplicationStaticInfo.DESTINATION_DIRECTORY;
				File f = new File(value);
				FileOutputStream fos = null;
				try (FileInputStream fis = new FileInputStream(f)){
					fos = new FileOutputStream(fileDestination);
					int i = 0;
					while ((i = fis.read()) != -1) {
						String change = ""+(char)i;
						byte[] buffer = new byte[1024];
						i = fis.read(buffer, 0, buffer.length);
						String data = new String(buffer, StandardCharsets.UTF_8);
						String writeToFile = change + data;
						fos.write(writeToFile.getBytes());
						System.out.println("Values Readed: " + writeToFile);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fos.close();
						fos.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				cauf.copyDirectory(f);
				try {
					LOG.appLogger().warn("Deleting File Started");
					Files.deleteIfExists(Paths.get(f.getAbsolutePath()));
				} catch (NoSuchFileException e) {
					System.err.println(e.getMessage().toString());
				} catch (DirectoryNotEmptyException e) {
					System.err.println(e.getLocalizedMessage().toString());
				} catch (IOException e) {
					System.err.println(e.getCause().getMessage().toString());
				} catch (Exception e) {
					e.getCause();
				}
			}
		}
	}

	private final boolean isDirectoryEmpty(final Path path) throws IOException {
		if (Files.isDirectory(path)) {
			try (DirectoryStream<Path> directoryToCheck = Files.newDirectoryStream(path)) {
				return !directoryToCheck.iterator().hasNext();
			}
		}
		return false;
	}
}
