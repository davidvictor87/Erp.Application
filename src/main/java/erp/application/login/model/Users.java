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
import org.springframework.data.annotation.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(catalog = "login", name="user")
public class Users implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "user_id")
	    @NotNull
	    private Long id;
	    @Column(name = "email")
	    @NotNull
	    private String email;
	    @Transient
	    @Column(name = "password")
	    @NotNull
	    private String password;
	    @Column(name = "name")
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
	    }

	    public Users(Users users) {
	    	super();
	        this.active = users.getActive();
	        this.email = users.getEmail();
	        this.roles = users.getRoles();
	        this.name = users.getName();
	        this.lastName =users.getLastName();
	        this.id = users.getId();
	        this.password = users.getPassword();
	    }

	    public Long getId() {
	        return id;
	    }

	    public Users setId(Long id) {
	    	this.id = id;
	        return this;
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
			return "Users [id=" + id + ", email=" + email + ", password=" + password + ", name=" + name + ", lastName="
					+ lastName + ", active=" + active + ", roles=" + roles + "]";
		}   
	    
	    
}
