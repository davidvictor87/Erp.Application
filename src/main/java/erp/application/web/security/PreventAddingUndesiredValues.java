package erp.application.web.security;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import erp.application.entities.LOG;

@Component(value = "undisered")
public class PreventAddingUndesiredValues {

	private boolean isOk = true;

	@Secured(value = "{ROLE_ADMIN}")
	public boolean preventAdd(final String undiseredValues) throws IOException {
		LOG.appLogger().warn("Received username: " + undiseredValues);
		Path path = Paths.get(new File("blockedUsernames.txt").getAbsolutePath());
		String[] b = Files.readAllLines(path).get(0).split(" ");
		ArrayBlockingQueue<String> unwantedUsernames = new ArrayBlockingQueue<String>(b.length);
		Arrays.asList(b).stream().collect(Collectors.toCollection(() -> unwantedUsernames));
		unwantedUsernames.stream().forEach(name -> {
			if (name.equalsIgnoreCase(undiseredValues)) {
				LOG.appLogger().info(" == Unacceptable Username ==");
				isOk = false;
				return;
			}
		});
		LOG.appLogger().warn("Return Value, isOk: " + isOk);
		return isOk;
	}

}
