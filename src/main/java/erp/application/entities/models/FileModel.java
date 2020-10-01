package erp.application.entities.models;

public class FileModel {
	
	private int id;
	private String name;
	private String profession;
	private String salary;
	private boolean isEnabled;
	
	public FileModel() {
		
	}

	public FileModel(int id, String name, String profession, String salary, boolean isEnabled) {
		super();
		this.id = id;
		this.name = name;
		this.profession = profession;
		this.salary = salary;
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

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	@Override
	public String toString() {
		return "id: " + id + ", name: " + name + ", profession: " + profession + ", salary: " + salary
				+ ", isSmoker: " + isEnabled + "";
	}

}
