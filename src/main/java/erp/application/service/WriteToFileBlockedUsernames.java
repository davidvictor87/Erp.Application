package erp.application.service;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import erp.application.entities.LOG;

@Service(value = "blockedusernames")
public class WriteToFileBlockedUsernames {

	public void writeUnwantedUsers(String username) {
		LOG.appLogger().info("Start writing on blockedUsernames file: " + username);
		try {
			File file = new File("blockedUsernames.txt");
			LOG.appLogger().info("File Length: " + file.length());
			Path path = Paths.get(file.getAbsolutePath());
			AsynchronousFileChannel asyncFileWriter = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			buffer.put(username.getBytes());
			buffer.flip();
			Future<FileLock> fileLock = asyncFileWriter.lock();
			FileLock lock = fileLock.get();
			Future<Integer> future = null;
			if (lock.isValid()) {
				if (file.length() == 0) {
					future = asyncFileWriter.write(buffer, 0);
					future.get();
					asyncFileWriter.close();
					buffer.clear();
				}
				future = asyncFileWriter.write(buffer, (file.length() + 1));
				future.get();
				asyncFileWriter.close();
				buffer.clear();
			}
		} catch (IOException | ExecutionException | InterruptedException e) {
		
		}
	}

}
