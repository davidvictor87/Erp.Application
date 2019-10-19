package erp.application.products;

import java.util.Map;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;

@SuppressWarnings({"unchecked","rawtypes"})
@Repository
public class ProductsRepositoryImpl implements ProductsRepository{
	
	private RedisTemplate<String, Products> redisTemplate;
	
	private HashOperations hashOperations;
	
	@Autowired
	public ProductsRepositoryImpl(RedisTemplate<String, Products> rTemp) {
		this.redisTemplate = rTemp;
		this.hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void save(Products employee) {
		hashOperations.put("PRODUCTS", employee.getId(), employee);
	}

	@Override
	public Map<String, Products> findAll() {
		return hashOperations.entries("PRODUCTS");
	}

	@Override
	public Products findById(Integer id) {
		return (Products)hashOperations.get("PRODUCTS", id);
	}

	@Override
	public void update(Products employee) {
		save(employee);		
	}

	@Override
	public void delete(Integer id) {
		hashOperations.delete("PRODUCTS", id);
	}

}
