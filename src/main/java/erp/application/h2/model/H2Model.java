package erp.application.h2.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(catalog="testdb", name="test")
public class H2Model implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	private String name;
	private String profession;
	private String salary;
	private boolean isEnabled;
	
	public H2Model() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
