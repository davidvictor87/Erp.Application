package erp.application.entities.tasks;

import java.nio.file.Path;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;

public interface FileStorage {
	
	public Stream<Path> listAll();
	
	public Resource loadResource(String file);
	
	public void deleteFilesFromWorkingDirectory();
	
	@Async
	public Future<String> editFile(String pathToFile, String editData);

}
