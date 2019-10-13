package erp.application.employee;

import java.util.Map;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;

@SuppressWarnings({"unchecked","rawtypes"})
@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{
	
	private RedisTemplate<String, Employee> redisTemplate;
	
	private HashOperations hashOperations;
	
	@Autowired
	public EmployeeRepositoryImpl(RedisTemplate<String, Employee> rTemp) {
		this.redisTemplate = rTemp;
		this.hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void save(Employee employee) {
		hashOperations.put("EMPLOYEE", employee.getId(), employee);
	}

	@Override
	public Map<String, Employee> findAll() {
		return hashOperations.entries("EMPLOYEE");
	}

	@Override
	public Employee findById(Integer id) {
		return (Employee)hashOperations.get("EMPLOYEE", id);
	}

	@Override
	public void update(Employee employee) {
		save(employee);		
	}

	@Override
	public void delete(Integer id) {
		hashOperations.delete("EMPLOYEE", id);
	}

}
