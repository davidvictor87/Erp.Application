package erp.application.employee;

import java.util.Map;

public interface EmployeeRepository {
	
	public void save(Employee employee);
	public Map<String, Employee> findAll();
	public Employee findById(Integer id);
	public void update(Employee employee);
	public void delete(Integer id);

}
