package erp.application.login.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;

import javax.persistence.Column;

@Entity
@Immutable
@Table(catalog = "login", name = "user_role")
public class UserRole implements Serializable{
	
	private static final long serialVersionUID = -6013024283046924747L;
	
	@Id
	@Column(name="user_id", unique = true, updatable = false)
	private String userId;
	@Column(name="role_id", unique = true, updatable = false)
	private String roleId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", insertable=false, updatable=false)
	private Users users;
	
	public UserRole() {
		
	}
	
	public UserRole(String uId, String rId, Users user) {
		userId = uId;
		roleId = rId;
		users = user;
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
	
	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "UserRole [userId=" + userId + ", roleId=" + roleId + ", users=" + users + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(users).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof UserRole)) {
			return false;
		}
		UserRole uRole = (UserRole)obj;
		return new EqualsBuilder().append(this.users, uRole.users).isEquals();
	}

}
