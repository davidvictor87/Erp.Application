package erp.application.employee.model;

import java.io.Serializable;
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
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name= "employee_processed_data")
public class EmployeeProcessedData implements Serializable{
	
	private static final long serialVersionUID = 4026795358374408208L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="employee_processed_id")
	@NotNull
	private int employee_processed_id;
	@Column(name="employee_id", nullable = false, unique = true)
	private int id;
	@Column(name="first_name")
	private String first_name;
	@Column(name="last_name")
	private String last_name;
	@Column(name="profession")
	private String profession;
	@Column(name="isExcepted")
	String isExcepted;
	@Column(name="salary")
	private double salary;
	@Column(name="cnp")
	private String cnp;
	@Column(name = "gender")
	private String gender;
	@Column(name = "fulltime_employee")
	String fulltime_employee;
	@Column(name = "aditionalInfo")
	private String aditionalInfo;
	@Temporal(TemporalType.DATE)
	@Column(name = "registration_date")
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
	
	public EmployeeProcessedData() {};
	

	public EmployeeProcessedData(int id, String first_name, String last_name, String profession, String isExcepted,
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
		this.date = date;
		this.address = address;
	}
	
	

	public int getEmployee_processed_id() {
		return employee_processed_id;
	}


	public void setEmployee_processed_id(int employee_processed_id) {
		this.employee_processed_id = employee_processed_id;
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
	
	

	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "EmployeeProcessedData [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", profession=" + profession + ", isExcepted=" + isExcepted + ", address=" + address + ", salary="
				+ salary + ", cnp=" + cnp + ", gender=" + gender + ", fulltime_employee=" + fulltime_employee
				+ ", aditionalInfo=" + aditionalInfo + "]";
	}
	
	
	
	

}
