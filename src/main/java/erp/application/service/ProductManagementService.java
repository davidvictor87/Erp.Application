package erp.application.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import erp.application.entities.LOG;
import erp.application.products.Products;
import erp.application.products.ProductsRepositoryImpl;

@Service
public class ProductManagementService {

	private ProductsRepositoryImpl productRepositoryImp;

	@Autowired
	public ProductManagementService(ProductsRepositoryImpl pRepositoryI) {
		this.productRepositoryImp = pRepositoryI;
	}

	@Async
	public CompletableFuture<Products> findUser(int id) {
		Products product = null;
		LOG.appLogger().info("FINDING USER WITH ID: " + id);
		try {
			product = productRepositoryImp.findById(id);
			return CompletableFuture.completedFuture(product);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
