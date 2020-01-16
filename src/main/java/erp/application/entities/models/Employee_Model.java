package erp.application.entities.models;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value="Employee_Model")
public class Employee_Model implements Serializable{

	private static final long serialVersionUID = 661604219216153531L;
	
	@JsonProperty(value="id")
	private int id;
	@JsonProperty(value="name")
	private String name;
	@JsonProperty(value="profession")
	private String profession;
	@JsonProperty(value="isEnabled")
	private boolean isEnabled;
	@JsonProperty(value="address")
	private String address;	
	
	public Employee_Model() {
		
	}

	public Employee_Model(int id, String name, String profession, boolean isEnabled, String address) {
		super();
		this.id = id;
		this.name = name;
		this.profession = profession;
		this.isEnabled = isEnabled;
		this.address = address;
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

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + ", name=" + name + ", profession=" + profession + ", isEnabled=" + isEnabled
				+ ", address=" + address + "]";
	}
	
	public boolean equals(Object object) {
		boolean returnValue = false;
		if(object instanceof Employee_Model) {
			Employee_Model model = (Employee_Model)object;
			returnValue = model.getId() == getId();
		}
		return returnValue;
	}

}
