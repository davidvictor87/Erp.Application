package erp.application.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import erp.application.entities.LOG;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class UploadFileService {

	@Secured(value="ROLE_ADMIN, ROLE_MANAGER, ROLE_USER")
	public void uploadFile(String filePath) throws IOException {
        if (!StringUtils.isEmpty(filePath)) {
			StringBuffer fileContentBuilder = new StringBuffer();
			filePath = "D:/NIO1/" + filePath;
			AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get(filePath),
					StandardOpenOption.READ);
			CompletionHandler<Integer, ByteBuffer> handler = new CompletionHandler<Integer, ByteBuffer>() {
				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					for (int i = 0; i < attachment.limit(); i++) {
						System.out.println(result + " : " + attachment);
					}
				}
                @Override
				public void failed(Throwable e, ByteBuffer attachment) {
					throw new IllegalStateException(e.getMessage());
				}
			};
			final int bufferCount = 5;
			ByteBuffer buffers[] = new ByteBuffer[bufferCount];
			for (int i = 0; i < bufferCount; i++) {
				buffers[i] = ByteBuffer.allocate(10);
				fileChannel.read(buffers[i], i * 10, buffers[i], handler);
			}
			for (ByteBuffer byteBuffer : buffers) {
				for (int i = 0; i < byteBuffer.limit(); i++) {
					fileContentBuilder.append((char) +byteBuffer.get(i));
				}
			}
			writeAsyncFile(fileContentBuilder.toString());
		}
	}

	@Secured(value="ROLE_ADMIN, ROLE_MANAGER, ROLE_USER")
	@SuppressWarnings("rawtypes")
	public static void writeAsyncFile(String input) throws IOException {
		byte[] byteArray = input.getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(byteArray);
		Path path = Paths.get("D:/NIO2/");
		if (path.toFile().exists()) {
			path = Paths.get("D:/NIO2/" + (path.toFile().list().length + 1) + ".txt");
			path.toFile().createNewFile();
		}
		AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
		@SuppressWarnings("unchecked")
		CompletionHandler<Integer, String> handler = new CompletionHandler() {
			@Override
			public void completed(Object result, Object attachment) {
				LOG.appLogger().warn(attachment + " completed and " + result + " bytes are written.");
				LOG.appLogger().warn("Execution Time : " + System.currentTimeMillis());
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				LOG.appLogger().error(attachment + " failed with exception:");
				exc.printStackTrace();
			}
		};
		channel.write(buffer, 0, "Async Task", handler);
		channel.close();

	}

}
