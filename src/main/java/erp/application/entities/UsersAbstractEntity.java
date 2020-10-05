package erp.application.entities;

public abstract class UsersAbstractEntity {
	
	public abstract void userRole(String user_id, String role_id);
	
	public abstract void updateUsers(String role_id, String level);

}
