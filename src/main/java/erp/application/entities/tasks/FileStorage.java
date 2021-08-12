package erp.application.entities.tasks;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorage {
	
	public Stream<Path> listAll();

}
