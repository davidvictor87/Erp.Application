package erp.application.entities.monitor.export;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFile.Type;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import erp.application.entities.LOG;

@Component
public class DirectoryListener implements FileChangeListener {
	
	public DirectoryListener() {
		super();
		System.out.println("Listener Called..");
	}

	@Override
	public void onChange(Set<ChangedFiles> changeSet) {
		for (ChangedFiles changedFiles : changeSet) {
			for (ChangedFile cFile : changedFiles.getFiles()) {
				if (cFile.getType().equals(Type.MODIFY) || cFile.getType().equals(Type.ADD)
						|| cFile.getType().equals(Type.DELETE) && !isLocked(cFile.getFile().toPath())) {
					LOG.appLogger().info("Op: " + cFile.getType() + ", On file: " + cFile.getFile().getName());
				}
			}
		}
	}

	private boolean isLocked(final Path path) {
		try (FileChannel fch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock fileLock = fch.tryLock()) {
			LOG.appLogger().info("File is locked");
			return fileLock == null;
		} catch (IOException ex) {
			ex.printStackTrace();
			LOG.appLogger().info("File is unlocked");
			return true;
		}
	}

}
