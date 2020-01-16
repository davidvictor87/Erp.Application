package erp.application.entities.models;

public class FileModel {
	
	private int id;
	private String name;
	private String profession;
	private String address;
	private boolean isSmoker;
	
	public FileModel() {
		
	}

	public FileModel(int id, String name, String profession, String address, boolean isSmoker) {
		super();
		this.id = id;
		this.name = name;
		this.profession = profession;
		this.address = address;
		this.isSmoker = isSmoker;
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

	public boolean getIsSmoker() {
		return isSmoker;
	}

	public void setIsSmoker(boolean isSmoker) {
		this.isSmoker = isSmoker;
	}
	
	@Override
	public String toString() {
		return "id: " + id + ", name: " + name + ", profession: " + profession + ", address: " + address
				+ ", isSmoker: " + isSmoker + "";
	}

}
