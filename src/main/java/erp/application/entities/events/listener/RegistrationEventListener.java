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
		Path destDirectory = Paths.get("D:/Export/Files/");
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDirectory)){
			for(Path path:stream) {
				Path destinationResolve = destDirectory.resolve(path.getFileName());
				Files.move(path, destinationResolve, StandardCopyOption.ATOMIC_MOVE);
			}
		}catch (IOException e) {
			LOG.appLogger().error(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}
