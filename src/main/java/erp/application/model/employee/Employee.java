package erp.application.model.employee;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value="Employee")
public class Employee implements Serializable{
	
	private static final long serialVersionUID = -8381282550023414155L;
	@JsonProperty(value="id")
	private int id;
	@JsonProperty(value="first_name")
	private String first_name;
	@JsonProperty(value="second_name")
	private String last_name;
	@JsonProperty(value="profession")
	private String profession;
	@JsonProperty(value="isExcepted")
	private boolean isExcepted;
	@JsonProperty(value="address")
	private String address;
	@JsonProperty(value="salary")
	private double salary;
	@JsonProperty(value="cnp")
	private String cnp;
	@JsonProperty(value="gender")
	private String gender;
	@JsonProperty(value="fulltime")
	private boolean fulltime_employee;
	@JsonProperty(value="aditionalInfo")
	private String aditionalInfo;
	
	public Employee() {}
	
	public Employee(int id, String first_name, String last_name, String profession, boolean isExcepted, String address,
			double salary, String cnp, String gender, boolean fulltime_employee, String aditionlInfo) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.profession = profession;
		this.isExcepted = isExcepted;
		this.address = address;
		this.salary = salary;
		this.cnp = cnp;
		this.gender = gender;
		this.fulltime_employee = fulltime_employee;
		this.aditionalInfo = aditionlInfo;
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
	
	

}
