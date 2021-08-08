package erp.application.products;

import java.util.ArrayList;
import java.util.List;
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
	public void save(Products product) {
		hashOperations.put("PRODUCTS", product.getId(), product);
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
	public void update(Products product) {
		save(product);		
	}

	@Override
	public void delete(Integer id) {
		hashOperations.delete("PRODUCTS", id);
	}

	@Override
	public List<Products> allValues() {
		List<Products> allProducts = new ArrayList<>();
		allProducts.addAll(hashOperations.entries("PRODUCTS").values());
		return allProducts;
	}
	
	

}
