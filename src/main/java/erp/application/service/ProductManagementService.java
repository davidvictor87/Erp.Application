package erp.application.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
	public CompletableFuture<Products> findProduct(int id) {
		Products product = null;
		LOG.appLogger().info("FINDING USER WITH ID: " + id);
		try {
			product = productRepositoryImp.findById(id);
			return CompletableFuture.completedFuture(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Async
	public void saveProduct(Products product) {
		LOG.appLogger().info("SAVE PRODUCT");
		try {
			CompletableFuture<Void> saveProduct = CompletableFuture.runAsync(() -> productRepositoryImp.save(product));
			saveProduct.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async
	public void updateProduct(Products product) {
		LOG.appLogger().info("UPDATE PRODUCT");
		try {
			CompletableFuture.runAsync(() -> productRepositoryImp.update(product)).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Async
	public Map<String, Products> returnAllProducts() throws InterruptedException, ExecutionException{
		LOG.appLogger().info("RETURN ALL PRODUCTS");
		CompletableFuture<Map<String, Products>> allProducts = CompletableFuture.supplyAsync(() -> {
			return productRepositoryImp.findAll();
		});
		return allProducts.get();
	}
	
	@Async
	public void deleteProduct(int id) {
		LOG.appLogger().info("DELETE PRODUCT");
		try {
			CompletableFuture.runAsync(() -> productRepositoryImp.delete(id)).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
