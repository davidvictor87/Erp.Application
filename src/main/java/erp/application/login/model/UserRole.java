package erp.application.login.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(catalog = "login", name = "user_role")
public class UserRole {
	
	@Id
	@Column(name="user_id")
	private String userId;
	@Column(name="role_id")
	private String roleId;
	
	public UserRole() {
		
	}
	
	public UserRole(String uId, String rId) {
		userId = uId;
		roleId = rId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String uId) {
		userId = uId;
	}
	
	public String getRoleId() {
		return roleId;
	}
	
	public void setRoleId(String rId) {
		roleId = rId;
	}

	@Override
	public String toString() {
		return "UserRole [userId=" + userId + ", roleId=" + roleId + ", getUserId()=" + getUserId() + ", getRoleId()="
				+ getRoleId() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
