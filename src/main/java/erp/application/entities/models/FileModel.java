package erp.application.entities.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document
public class FileModel {
	
	@Id
	private int id;
	private String name;
	private String profession;
	private String address;
	private boolean isEnabled;
	
	public FileModel() {
		
	}

	public FileModel(int id, String name, String profession, String address, boolean isEnabled) {
		super();
		this.id = id;
		this.name = name;
		this.profession = profession;
		this.address = address;
		this.isEnabled = isEnabled;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	@Override
	public String toString() {
		return "id: " + id + ", name: " + name + ", profession: " + profession + ", address: " + address
				+ ", isSmoker: " + isEnabled + "";
	}

}
