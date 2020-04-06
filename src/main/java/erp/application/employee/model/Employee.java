package erp.application.employee.model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "Employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = -8381282550023414155L;
	@JsonProperty(value = "id")
	private int id;
	@JsonProperty(value = "name")
	private String name;
	@JsonProperty(value = "second_name")
	private String second_name;
	@JsonProperty(value = "profession")
	private String profession;
	@JsonProperty(value = "isExcept")
	private boolean isExcept;
	@JsonProperty(value = "salary")
	private double salary;
	@JsonProperty(value = "cnp")
	private String cnp;
	@JsonProperty(value = "gender")
	private String gender;
	@JsonProperty(value = "fulltime")
	private boolean fulltime;
	@JsonProperty(value = "aditionInfo")
	private String aditionInfo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date currentDate;
	@JsonProperty(value = "addressCountry")
	private String addressCountry;
	@JsonProperty(value = "addressCounty")
	private String addressCounty;
	@JsonProperty(value = "addressCity")
	private String addressCity;
	@JsonProperty(value = "addressStreet")
	private String addressStreet;
	@JsonProperty(value = "addressNumber")
	private String addressNumber;

	public Employee() {
	}

	public Employee(int id, String name, String second_name, String profession, boolean isExcept, double salary,
			String cnp, String gender, boolean fulltime, String aditionInfo, Date currentDate, String addressCountry,
			String addressCounty, String addressCity, String addressStreet, String addressNumber) {
		super();
		this.id = id;
		this.name = name;
		this.second_name = second_name;
		this.profession = profession;
		this.isExcept = isExcept;
		this.salary = salary;
		this.cnp = cnp;
		this.gender = gender;
		this.fulltime = fulltime;
		this.aditionInfo = aditionInfo;
		this.currentDate = currentDate;
		this.addressCountry = addressCountry;
		this.addressCounty = addressCounty;
		this.addressCity = addressCity;
		this.addressStreet = addressStreet;
		this.addressNumber = addressNumber;
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

	public boolean isExcept() {
		return isExcept;
	}

	public void setExcept(boolean isExcept) {
		this.isExcept = isExcept;
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

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public String getAddressCountry() {
		return addressCountry;
	}

	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}

	public String getAddressCounty() {
		return addressCounty;
	}

	public void setAddressCounty(String addressCounty) {
		this.addressCounty = addressCounty;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", second_name=" + second_name + ", profession=" + profession
				+ ", isExcept=" + isExcept + ", salary=" + salary + ", cnp=" + cnp + ", gender=" + gender
				+ ", fulltime=" + fulltime + ", aditionInfo=" + aditionInfo + ", currentDate=" + currentDate
				+ ", addressCountry=" + addressCountry + ", addressCounty=" + addressCounty + ", addressCity="
				+ addressCity + ", addressStreet=" + addressStreet + ", addressNumber=" + addressNumber + "]";
	}

}
