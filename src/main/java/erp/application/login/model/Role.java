package erp.application.login.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Immutable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Immutable
@Table(catalog = "login", name = "role")
public class Role implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private long roleId;

    @Column(name = "role", updatable = true)
    private String role;

    public Role() {
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", role=" + role + "]";
	}
	
	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(role);
		return hcb.toHashCode();
	}
    
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Role)) {
			return false;
		}
		Role thatRole = (Role) obj;
		EqualsBuilder eqBuilder = new EqualsBuilder();
		eqBuilder.append(this.role, thatRole.role);
		return eqBuilder.isEquals();
	}

}
