package erp.application.service;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import erp.application.entities.LOG;

@Service(value="blockedusernames")
public class WriteToFileBlockedUsernames{

	public void writeUnwantedUsers(String username) {
		LOG.appLogger().info("Start writing on blockedUsernames file");
		try {
			File file = new File("blockedUsernames.txt");
			Path path = Paths.get(file.getAbsolutePath());
			AsynchronousFileChannel asyncFileWriter = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			buffer.put(username.getBytes());
			Future<Integer> future = asyncFileWriter.write(buffer, (file.length() + 1));
			future.get();
			asyncFileWriter.close();
			buffer.clear();
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

}
