package erp.application.entities;

import erp.application.login.model.Users;

public abstract class UsersAbstractEntity {
	
	public abstract void prioritizeTasks(Users user);
	
	public abstract void saveUser(Users user);
	
	public abstract void userRole(String user_id, String role_id);
	
	public abstract void updateUsers(String role_id, String level);

}
