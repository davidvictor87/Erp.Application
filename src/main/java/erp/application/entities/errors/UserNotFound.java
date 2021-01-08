package erp.application.entities.errors;

public abstract class UserNotFound extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public UserNotFound() {
		super("User not found");
	}

}
