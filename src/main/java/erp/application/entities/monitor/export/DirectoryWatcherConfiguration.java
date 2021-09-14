package erp.application.entities.monitor.export;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.boot.devtools.filewatch.FileSystemWatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;

@Configuration
public class DirectoryWatcherConfiguration {
	
	private FileSystemWatcherFactory watcherFactory = null;
	
	public DirectoryWatcherConfiguration(FileSystemWatcherFactory factory) {
		this.watcherFactory = factory;
	}

	@Bean
	public FileSystemWatcher directoryWatcher() {
		FileSystemWatcher directoryWatcher = null;
		try {
			directoryWatcher = this.watcherFactory.getFileSystemWatcher();
			directoryWatcher.addSourceFolder(new File(ApplicationStaticInfo.EXPORT_DIRECTORY));
			directoryWatcher.addListener(new DirectoryListener());
			directoryWatcher.start();
			LOG.appLogger().info("Directory Watcher is On");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return directoryWatcher;

	}

	@PostConstruct
	public void stopWatcher() {
		LOG.appLogger().info("Stop Watcher Called");
		try {
			directoryWatcher().stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
