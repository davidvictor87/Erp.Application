package erp.application.entities.errors;

import erp.application.entities.LOG;

public class StorageException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
		LOG.appLogger().error("=== CUSTOM EXCEPTION FOR FILE STORAGE ERROR ===");
	}

}
