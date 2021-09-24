package erp.application.h2.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(catalog="test", name="test_users")
public class H2Model implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	private Integer id;
	@Column
	private String name;
	@Column
	private String profession;
	@Column
	private String salary;
	@Column
	private boolean isEnabled;
	
	public H2Model() {}
	
	public H2Model(Integer id, String name, String profession, String salary, boolean isEnabled) {
		super();
		this.id = id;
		this.name = name;
		this.profession = profession;
		this.salary = salary;
		this.isEnabled = isEnabled;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}	

	@Override
	public String toString() {
		return "H2Model [id=" + id + ", name=" + name + ", profession=" + profession + ", salary=" + salary
				+ ", isEnabled=" + isEnabled + "]";
	}
	
}
