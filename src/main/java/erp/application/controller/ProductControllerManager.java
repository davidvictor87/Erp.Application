package erp.application.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import erp.application.entities.LOG;
import erp.application.products.Products;
import erp.application.products.repository.ProductsRepository;
import erp.application.service.CreateProductFiles;
import erp.application.service.ProductManagementService;
import erp.application.service.SortProducts;

import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ProductControllerManager {

	private ProductsRepository productsRepository;
	private CreateProductFiles createProductFiles;
	private ProductManagementService productManagementService;
	private SortProducts sortProducts;

	@Autowired
	public ProductControllerManager(ProductsRepository prodRepo, CreateProductFiles productFiles, ProductManagementService productManagemet, SortProducts so) {
		super();
		this.productsRepository = prodRepo;
		this.createProductFiles = productFiles;
		this.productManagementService = productManagemet;
		this.sortProducts = so;
	}

	@GetMapping(value = "/addProduct/{id}/{product_category}/{product_manufacturer}/{product_name}/{vat_level}/{prodct_price}/{product_code}")
	@ResponseBody
	public Products addEmployee(@PathVariable("id") final Integer id, @PathVariable("product_category") final String product_category, @PathVariable("product_manufacturer") final String product_manufacturer,
			@PathVariable("product_name") final String product_name, @PathVariable("vat_level") final int vat_level, @PathVariable("product_price") final int product_price,
			@PathVariable("product_code") final String product_code) {
		productManagementService.saveProduct(new Products(id, product_category, product_manufacturer, product_name, vat_level, product_price, product_code));
		System.out.println(allProducts());
		System.out.println("This is the map " + productsRepository.findAll());
		createProductFiles.writeProductFile(productsRepository.findAll());
	    LOG.appLogger().info("PRODUCT ADDED");
		return productsRepository.findById(id);
	}

	@PutMapping(value = "/updateProduct/{id}/{product_category}/{product_manufacturer}/{product_name}/{vat_level}/{product_code}")
	@ResponseBody
	public Products updateEmployee(@PathVariable("id") final Integer id, @PathVariable("product_category") final String product_category,
	    @PathVariable("product_manufacturer") final String product_manufacturer, @PathVariable("product_name") final String product_name, @PathVariable("vat_level") final int vat_level,
		@PathVariable("product_price") final int product_price, @PathVariable("product_code") final String product_code) {
		productManagementService.updateProduct(new Products(id, product_category, product_manufacturer, product_name, vat_level, product_price, product_code));
		return productsRepository.findById(id);
	}

	
	  @DeleteMapping(value="/deleteProduct/{id}")
	  @ResponseBody 
	  public Map<String, Products> deleteProduct(@PathVariable(value="id") final String i){ 
		  int id = 0; 
		  try { 
			  id = Integer.parseInt(i); 
		  }catch (NumberFormatException e) {
	          System.err.println(e.getCause().toString()); 
	      } 
		  productManagementService.deleteProduct(id);
	      return productsRepository.findAll(); 
	  }
	 

	@GetMapping(value = "/allProducts")
	@ResponseBody
	public String allProducts() {
		ObjectMapper mapper = new ObjectMapper();
		LOG.appLogger().info("Data Map info: " + productsRepository.findAll());
		String jsonCollection = null;
		try {
			jsonCollection = mapper.writeValueAsString(productManagementService.returnAllProducts());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonCollection;
     }
	
	@GetMapping(value = "/ordered/list")
	@ResponseBody
	public List<Products> retrieveOrderedProductList(){
		LOG.appLogger().info("== Start returning ordered list of products ==");
		List<Products> listOfProducts = null;
		try {
			listOfProducts = sortProducts.sortedProducts();
			LOG.appLogger().info("List of products: " + listOfProducts);
		}catch (Exception e) {
			System.err.println("== Failed to retrieve ordered list of products ==");
			e.printStackTrace();
		}
		return listOfProducts;
	}

}
