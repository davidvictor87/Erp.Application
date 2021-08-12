package erp.application.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;
import erp.application.entities.errors.StorageException;
import erp.application.entities.tasks.FileStorage;
import java.io.IOException;

@Service
public class FileStorageImpl implements FileStorage{
	
	private Path filePath;
	
	@Autowired
	public FileStorageImpl() {
		super();
		this.filePath = Paths.get(ApplicationStaticInfo.DESTINATION_DIRECTORY);
	}

	@Override
	@Secured(value="{ROLE_ADMIN, ROLE_MANAGER, ROLE_USER")
	public Stream<Path> listAll() {
		LOG.appLogger().info("=== List All Files ===");
		try {
			return Files.walk(this.filePath, 1).filter(path -> !path.equals(this.filePath))
					.map(path -> this.filePath.relativize(path));
		}catch (IOException ex) {
			throw new StorageException("Files Storage Exception", ex);
		}
	}
	
	

}
