package erp.application.model.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Employee_Processed_Data")
public class EmployeeProcessedData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", nullable = false, unique = true)
	private int id;
	@Column(name="first_name")
	private String first_name;
	@Column(name="last_name")
	private String last_name;
	@Column(name="profession")
	private String profession;
	@Column(name="isExcepted")
	private boolean isExcepted;
	@Column(name="address")
	private String address;
	@Column(name="salary")
	private double salary;
	@Column(name="cnp")
	private String cnp;
	@Column(name = "gender")
	private String gender;
	@Column(name = "fulltime_employee")
	private boolean fulltime_employee;
	@Column(name = "aditionalInfo")
	private String aditionalInfo;
	
	public EmployeeProcessedData() {}

	public EmployeeProcessedData(String first_name, String last_name, String profession, boolean isExcepted,
			String address, double salary, String cnp, String gender, boolean fulltime_employee, String aditionalInfo) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.profession = profession;
		this.isExcepted = isExcepted;
		this.address = address;
		this.salary = salary;
		this.cnp = cnp;
		this.gender = gender;
		this.fulltime_employee = fulltime_employee;
		this.aditionalInfo = aditionalInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public boolean isExcepted() {
		return isExcepted;
	}

	public void setExcepted(boolean isExcepted) {
		this.isExcepted = isExcepted;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isFulltime_employee() {
		return fulltime_employee;
	}

	public void setFulltime_employee(boolean fulltime_employee) {
		this.fulltime_employee = fulltime_employee;
	}

	public String getAditionalInfo() {
		return aditionalInfo;
	}

	public void setAditionalInfo(String aditionalInfo) {
		this.aditionalInfo = aditionalInfo;
	}

	@Override
	public String toString() {
		return "EmployeeProcessedData [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", profession=" + profession + ", isExcepted=" + isExcepted + ", address=" + address + ", salary="
				+ salary + ", cnp=" + cnp + ", gender=" + gender + ", fulltime_employee=" + fulltime_employee
				+ ", aditionalInfo=" + aditionalInfo + "]";
	}
	
	

}
