package erp.application.entities.errors;

public abstract class AppFailedToStart extends RuntimeException{
	
	private static final long serialVersionUID = -1793539375153790208L;

	public AppFailedToStart() {
		super("Application Failed To Start");
	}

}
