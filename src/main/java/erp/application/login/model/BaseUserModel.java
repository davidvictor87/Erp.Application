package erp.application.login.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseUserModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    @NotNull
	private Long id;
	
	@Version
	@Transient
	private Integer version;
	
	public BaseUserModel() {}
	
	public BaseUserModel(BaseUserModel base) {
		super();
		this.id = base.id;
		this.version = base.version;
	}

	public Long getId() {
		return id;
	}

	public BaseUserModel setId(Long id) {
		this.id = id;
		return this;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "BaseUserModel [id=" + id + ", version=" + version + "]";
	}
	
	
	

}
