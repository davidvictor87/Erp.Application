package erp.application.entities;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.stereotype.Component;

@Component
public class FileWatcher {
	
	@SuppressWarnings("unchecked")
	public boolean watchFileTask(String filePath) {
		try {
			WatchService watchFile = FileSystems.getDefault().newWatchService();
			Path path = Paths.get(filePath);
			path.register(watchFile, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.OVERFLOW);
			while(true) {
				WatchKey key = watchFile.take();
				for(WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();
					if(kind == StandardWatchEventKinds.ENTRY_MODIFY && fileName.toString().equals(filePath)) {
						System.out.println("Inside if..");
						return true;
					}
				}
				boolean valid = key.reset();
				if(!valid) {
					break;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
