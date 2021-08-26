package erp.application.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;
import erp.application.entities.errors.StorageException;
import erp.application.entities.events.publisher.EventPublisher;
import erp.application.entities.tasks.FileStorage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class FileStorageImpl implements FileStorage{
	
	private Path filePath;
	private String editedFilePath;
	private boolean isEdited;
	@Autowired
	private EventPublisher publisher;
	
	@Autowired
	public FileStorageImpl() {
		super();
		this.filePath = Paths.get(ApplicationStaticInfo.FOLDER__TO_ITERATE);
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
	
	@Override
	@Secured(value="{ROLE_ADMIN, ROLE_MANAGER, ROLE_USER")
	public Resource loadResource(String file) {
		try {
			filePath.resolve(file);
			Resource resource = new UrlResource(filePath.toUri());
			return (resource.exists() || resource.isReadable()) ? resource : (Resource) new RuntimeException();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	@Override
	@Secured(value="{ROLE_ADMIN}")
	public void deleteFilesFromWorkingDirectory() {
		LOG.appLogger().warn("=== START DELETE ALL FILES INSIDE WORKING DIR ===");
		try {
			FileSystemUtils.deleteRecursively(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Async
	@Secured(value = "{ROLE_ADMIN, ROLE_MANAGER, ROLE_USER")
	public Future<String> editFile(String pathToFile, String editData) {
		LOG.appLogger().info("== Start editing file data ===");
		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;
		try {
			File fileToUpdate = new File(pathToFile);
			String initialData = "";
			bufferedReader = new BufferedReader(new FileReader(fileToUpdate));
			String data = bufferedReader.readLine();
			while (data != null) {
				initialData = initialData + data + System.lineSeparator();
				data = bufferedReader.readLine();
			}
			String updatedData = initialData.replaceAll(initialData, editData);
			fileWriter = new FileWriter(fileToUpdate);
			fileWriter.write(updatedData);
			Thread.sleep(1);
			editedFilePath = pathToFile;
			isEdited = true;
			publisher.publishEvent(editedFilePath, isEdited);
			return new AsyncResult<String>(editData);
		} catch (InterruptedException | IOException e) {
			isEdited = false;
			e.printStackTrace();
			throw new RuntimeException("Failed to edit file");
		} finally {
			try {
				bufferedReader.close();
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
