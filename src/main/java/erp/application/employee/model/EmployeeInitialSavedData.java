package erp.application.employee.model;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "employee_initial_saved_data")
public class EmployeeInitialSavedData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name="employee_init_id")
	private int employee_init_id;
	@Column(name="employee_id", nullable = false)
	private int id;
	@Column(name="first_name")
	private String first_name;
	@Column(name="last_name")
	private String last_name;
	@Column(name="profession")
	private String profession;
	@Column(name="isExcepted")
	private String isExcepted;
	@Column(name="salary")
	private double salary;
	@Column(name="cnp")
	private String cnp;
	@Column(name = "gender")
	private String gender;
	@Column(name = "fulltime_employee")
	private String fulltime_employee;
	@Column(name = "aditionalinfo")
	private String aditionalInfo;
	@Temporal(TemporalType.DATE)
	@Column(name="registration_date")
	private Date date;
	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name="country", column=@Column(name="employee_country")),
			@AttributeOverride(name="county", column=@Column(name="employee_county")),
			@AttributeOverride(name="city", column=@Column(name="employee_city")),
			@AttributeOverride(name="street", column=@Column(name="employee_street")),
			@AttributeOverride(name="number", column=@Column(name="employee_number"))
	})
	private Address address;
	
	public EmployeeInitialSavedData() {
	}

	public EmployeeInitialSavedData(int id, String first_name, String last_name, String profession, String isExcepted,
			double salary, String cnp, String gender, String fulltime_employee, String aditionalInfo, Date date, Address address) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.profession = profession;
		this.isExcepted = isExcepted;
		this.salary = salary;
		this.cnp = cnp;
		this.gender = gender;
		this.fulltime_employee = fulltime_employee;
		this.aditionalInfo = aditionalInfo;
		this.address = address;
	}
	
	public int getEmployee_init_id() {
		return employee_init_id;
	}

	public void setEmployee_init_id(int employee_init_id) {
		this.employee_init_id = employee_init_id;
	}

	public String getIsExcepted() {
		return isExcepted;
	}

	public void setIsExcepted(String isExcepted) {
		this.isExcepted = isExcepted;
	}

	public String getFulltime_employee() {
		return fulltime_employee;
	}

	public void setSalary(double salary) {
		this.salary = salary;
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

	public String isExcepted() {
		return isExcepted;
	}

	public void setExcepted(String isExcepted) {
		this.isExcepted = isExcepted;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
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

	public String isFulltime_employee() {
		return fulltime_employee;
	}

	public void setFulltime_employee(String fulltime_employee) {
		this.fulltime_employee = fulltime_employee;
	}

	public String getAditionalInfo() {
		return aditionalInfo;
	}

	public void setAditionalInfo(String aditionalInfo) {
		this.aditionalInfo = aditionalInfo;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "EmployeeInitialSavedData [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", profession=" + profession + ", isExcepted=" + isExcepted + ", address=" + address + ", salary="
				+ salary + ", cnp=" + cnp + ", gender=" + gender + ", fulltime_employee=" + fulltime_employee
				+ ", aditionalInfo=" + aditionalInfo + "]";
	}
	
	

}
