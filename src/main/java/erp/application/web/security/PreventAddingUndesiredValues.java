package erp.application.web.security;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import erp.application.entities.LOG;

@Component(value = "undisered")
public class PreventAddingUndesiredValues {

	private boolean isOk = true;

	public boolean preventAdd(final String undiseredValues) throws IOException {
		LOG.appLogger().warn("Received username: " + undiseredValues);
		Path path = Paths.get(new File("blockedUsernames.txt").getAbsolutePath());
		List<String> unwantedUsernames = Files.readAllLines(path);
		String[] b = unwantedUsernames.get(0).split(" ");
		unwantedUsernames.clear();
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
