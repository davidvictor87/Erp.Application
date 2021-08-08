package erp.application.products;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface ProductsRepository {
	
	public void save(Products products);
	public Map<String, Products> findAll();
	public Products findById(Integer id);
	public void update(Products products);
	public void delete(Integer id);
	public List<Products> allValues();

}
