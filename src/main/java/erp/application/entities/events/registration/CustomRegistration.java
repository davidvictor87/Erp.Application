package erp.application.entities.events.registration;

import org.springframework.context.ApplicationEvent;

public class CustomRegistration extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	private String filePath;
	private boolean isEdited;
	
	public CustomRegistration(String filePath, boolean isEdited) {
		super(new Object());
		this.filePath = filePath;
		this.isEdited = isEdited;
	}

	public String getFilePath() {
		return isEdited ? filePath : "FAILED TO FIND FILE PATH";
	}

}
