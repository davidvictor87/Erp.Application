package erp.application.employee.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value="Employee")
public class Employee implements Serializable{
	
	private static final long serialVersionUID = -8381282550023414155L;
	@JsonProperty(value="id")
	private int id;
	@JsonProperty(value="name")
	private String name;
	@JsonProperty(value="second_name")
	private String second_name;
    @JsonProperty(value="profession")
	private String profession;
    @JsonProperty(value="isExcept")
	private boolean isExcept;
    @JsonProperty(value="address")
	private String address;	
    @JsonProperty(value="salary")
	private double salary;
    @JsonProperty(value="cnp")
	private String cnp;
    @JsonProperty(value="gender")
	private String gender;
    @JsonProperty(value="fulltime")
	private boolean fulltime;
    @JsonProperty(value="aditionInfo")
	private String aditionInfo;
	
	public Employee() {}

	public Employee(int id, String name, String second_name, String profession, boolean isExcept, String address,
			double salary, String cnp, String gender, boolean fulltime, String aditionInfo) {
		super();
		this.id = id;
		this.name = name;
		this.second_name = second_name;
		this.profession = profession;
		this.isExcept = isExcept;
		this.address = address;
		this.salary = salary;
		this.cnp = cnp;
		this.gender = gender;
		this.fulltime = fulltime;
		this.aditionInfo = aditionInfo;
	}

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

	public String getSecond_name() {
		return second_name;
	}

	public void setSecond_name(String second_name) {
		this.second_name = second_name;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public boolean getisExcept() {
		return isExcept;
	}

	public void setExcept(boolean isExcept) {
		this.isExcept = isExcept;
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

	public boolean isFulltime() {
		return fulltime;
	}

	public void setFulltime(boolean fulltime) {
		this.fulltime = fulltime;
	}

	public String getAditionInfo() {
		return aditionInfo;
	}

	public void setAditionInfo(String aditionInfo) {
		this.aditionInfo = aditionInfo;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", second_name=" + second_name + ", profession=" + profession
				+ ", isExcept=" + isExcept + ", address=" + address + ", salary=" + salary + ", cnp=" + cnp
				+ ", gender=" + gender + ", fulltime=" + fulltime + ", aditionInfo=" + aditionInfo + "]";
	}
	
	
	
	
	

}
