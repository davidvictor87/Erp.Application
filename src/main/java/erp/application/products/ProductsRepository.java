package erp.application.products;

import java.util.Map;

public interface ProductsRepository {
	
	public void save(Products products);
	public Map<String, Products> findAll();
	public Products findById(Integer id);
	public void update(Products products);
	public void delete(Integer id);

}
