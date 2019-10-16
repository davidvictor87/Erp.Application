package erp.application.employee.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@JsonTypeName(value="Employee")
public class Employee implements Serializable{
	
	private static final long serialVersionUID = -8381282550023414155L;
	@SerializedName(value="id")
	@Expose
	private int id;
	@SerializedName(value="name")
	@Expose
	private String name;
	@SerializedName(value="second_name")
	@Expose
	private String second_name;
    @SerializedName(value="profession")
	@Expose
	private String profession;
	@SerializedName(value="isExcept")
	@Expose
	private boolean isExcept;
	@SerializedName(value="address")
	@Expose
	private String address;	
	@SerializedName(value="salary")
	@Expose
	private double salary;
	@SerializedName(value="cnp")
	@Expose
	private String cnp;
	@SerializedName(value="gender")
	@Expose
	private String gender;
	@SerializedName(value="fulltime")
	@Expose
	private boolean fulltime;
	@SerializedName(value="aditionInfo")
	@Expose
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
