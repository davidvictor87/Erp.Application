package erp.application.login.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Immutable;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Immutable
@Table(catalog = "login", name="user")
public class Users extends BaseUserModel implements Serializable{
	
	    private static final long serialVersionUID = 1L;
	
	    @Column(name = "email")
	    @NotNull
	    private String email;
	    @Transient
	    @Column(name = "password")
	    @NotNull
	    @Min(value=8, message="Password must have at least 8 chacters")
	    @Max(value=20, message="Password must not have more than 20 charcters")
	    private String password;
	    @Column(name = "name", unique = true)
	    @NotNull
	    private String name;
	    @Column(name = "last_name")
	    @NotNull
	    private String lastName;
	    @Column(name = "active")
	    private int active;
	    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id") ,inverseJoinColumns = @JoinColumn(name = "role_id"))
	    private Set<Role> roles;

	    public Users() {
	    	super();
	    }

	    public Users(Users users) {
	    	super();
	        this.active = users.getActive();
	        this.email = users.getEmail();
	        this.roles = users.getRoles();
	        this.name = users.getName();
	        this.lastName =users.getLastName();
	        this.password = users.getPassword();
	    }

	    public String getEmail() {
	        return email;
	    }

	    public Users setEmail(String email) {
	    	this.email = email;
	        return this;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public Users setPassword(String password) {
	    	this.password = password;
	        return this;
	    }

	    public String getName() {
	        return name;
	    }

	    public Users setName(String name) {
	    	this.name = name;
	        return this;
	    }

	    public String getLastName() {
	        return lastName;
	    }

	    public Users setLastName(String lastName) {
	    	this.lastName = lastName;
	        return this;
	    }

	    public int getActive() {
	        return active;
	    }

	    public Users setActive(int active) {
	    	this.active = active;
	        return this;
	    }

	    public Set<Role> getRoles() {
	        return roles;
	    }

	    public Users setRoles(Set<Role> roles) {
	    	this.roles = roles;
	        return this;
	    }

		@Override
		public String toString() {
			return "Users [email=" + email + ", password=" + password + ", name=" + name + ", lastName=" + lastName
					+ ", active=" + active + ", roles=" + roles + "]";
		}

		@Override
        public int hashCode() {
        	return new HashCodeBuilder().append(email).append(password).append(name).append(lastName).append(active).append(roles)
        			.hashCode();
        }
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Users)) {
				 return false;
			}
			Users thatUser = (Users) obj;
			return new EqualsBuilder().append(this.email, thatUser.email).append(this.password, thatUser.password)
					.append(this.name, thatUser.name).append(this.lastName, thatUser.lastName).append(this.active, thatUser.active).append(this.roles, thatUser.roles)
					.isEquals();
		}
	    
}
