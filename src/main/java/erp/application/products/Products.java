package erp.application.products;

import java.io.Serializable;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "PRODUCTS")
public class Products implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String cnp;
	private String work_contract;
	private String employee_name;
	private String employee_salary;
	private String bank_account;
	
	public Products() {
		
	}

	public Products(int id, String cnp, String work_contract, String employee_name, String employee_salary,
			String bank_account) {
		super();
		this.id = id;
		this.cnp = cnp;
		this.work_contract = work_contract;
		this.employee_name = employee_name;
		this.employee_salary = employee_salary;
		this.bank_account = bank_account;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public String getWork_contract() {
		return work_contract;
	}

	public void setWork_contract(String work_contract) {
		this.work_contract = work_contract;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEmployee_salary() {
		return employee_salary;
	}

	public void setEmployee_salary(String employee_salary) {
		this.employee_salary = employee_salary;
	}

	public String getBank_account() {
		return bank_account;
	}

	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", cnp=" + cnp + ", work_contract=" + work_contract + ", employee_name="
				+ employee_name + ", employee_salary=" + employee_salary + ", bank_account=" + bank_account + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bank_account == null) ? 0 : bank_account.hashCode());
		result = prime * result + ((cnp == null) ? 0 : cnp.hashCode());
		result = prime * result + ((employee_name == null) ? 0 : employee_name.hashCode());
		result = prime * result + ((employee_salary == null) ? 0 : employee_salary.hashCode());
		result = prime * result + id;
		result = prime * result + ((work_contract == null) ? 0 : work_contract.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Products other = (Products) obj;
		if (bank_account == null) {
			if (other.bank_account != null)
				return false;
		} else if (!bank_account.equals(other.bank_account))
			return false;
		if (cnp == null) {
			if (other.cnp != null)
				return false;
		} else if (!cnp.equals(other.cnp))
			return false;
		if (employee_name == null) {
			if (other.employee_name != null)
				return false;
		} else if (!employee_name.equals(other.employee_name))
			return false;
		if (employee_salary == null) {
			if (other.employee_salary != null)
				return false;
		} else if (!employee_salary.equals(other.employee_salary))
			return false;
		if (id != other.id)
			return false;
		if (work_contract == null) {
			if (other.work_contract != null)
				return false;
		} else if (!work_contract.equals(other.work_contract))
			return false;
		return true;
	}
	
}
