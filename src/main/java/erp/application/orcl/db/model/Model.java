package erp.application.orcl.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tabel")
public class Model {
	
	@Id
	@Column(name="tabelid")
	private int id;
	@Column(name="nume")
	private String name;
	
	public Model() {}
	
	public Model(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "Model [id=" + id + ", name=" + name + "]";
	}
}
