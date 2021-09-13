package erp.application.entities.events.listener;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;
import erp.application.entities.events.registration.CustomRegistration;

@Component
public class RegistrationEventListener {

	@EventListener
	public void handleRegistration(CustomRegistration impl) {
		System.out.println("File Path: " + impl.getFilePath());
		Path sourceDirectory = Paths.get(ApplicationStaticInfo.FOLDER__TO_ITERATE);
		Path destDirectory = Paths.get(ApplicationStaticInfo.EXPORT_DIRECTORY);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDirectory)) {
			for (Path path : stream) {
				System.out.println(path.getFileName() + " : " + impl.getFilePath().substring(21));
				if(path.getFileName().toString().equals(impl.getFilePath().substring(21))) {
					Path fileToTransfer = Paths.get(impl.getFilePath());
					Path destinationResolve = destDirectory.resolve(fileToTransfer.getFileName());
					Files.move(fileToTransfer, destinationResolve, StandardCopyOption.REPLACE_EXISTING,
							StandardCopyOption.ATOMIC_MOVE);
				}
			}
		} catch (IOException e) {
			LOG.appLogger().error(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}
