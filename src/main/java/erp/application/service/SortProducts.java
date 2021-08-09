package erp.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import erp.application.products.Products;
import erp.application.products.repository.ProductsRepositoryImpl;

@Service
public class SortProducts {

	private ProductsRepositoryImpl productsRepository;

	@Autowired
	private SortProducts(ProductsRepositoryImpl pRepo) {
		super();
		this.productsRepository = pRepo;
	}

	@Secured(value="ROLE_ADMIN, ROLE_MANAGER, ROLE_USER")
	public List<Products> sortedProducts() {
		List<Products> sortedList = productsRepository.allValues();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sortedList;
	}
	
	
}
