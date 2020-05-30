package erp.application.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import erp.application.entities.LOG;
import erp.application.products.Products;
import erp.application.products.ProductsRepository;
import erp.application.service.CreateProductFiles;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ProductController {

	private ProductsRepository productsRepository;

	@Autowired
	private CreateProductFiles createProductFiles;

	public ProductController(ProductsRepository prodRepo) {
		this.productsRepository = prodRepo;
	}

	@GetMapping(value = "/addProduct/{id}/{product_category}/{product_manufacturer}/{product_name}/{vat_level}/{product_code}")
	@ResponseBody
	public Products addEmployee(@PathVariable("id") final Integer id, @PathVariable("product_category") final String product_category, @PathVariable("product_manufacturer") final String product_manufacturer,
			@PathVariable("product_name") final String product_name, @PathVariable("vat_level") final int vat_level,
			@PathVariable("product_code") final String product_code) {
		productsRepository.save(new Products(id, product_category, product_manufacturer, product_name, vat_level, product_code));
		System.out.println(allProducts());
		createProductFiles.writeProductFile(productsRepository.findAll());
		return productsRepository.findById(id);
	}

	@PutMapping(value = "/updateProduct/{id}/{product_category}/{product_manufacturer}/{product_name}/{vat_level}/{product_code}")
	@ResponseBody
	public Products updateEmployee(@PathVariable("id") final Integer id, @PathVariable("product_category") final String product_category,
	    @PathVariable("product_manufacturer") final String product_manufacturer, @PathVariable("product_name") final String product_name, @PathVariable("vat_level") final int vat_level,
		@PathVariable("product_code") final String product_code) {
		productsRepository.update(
				new Products(id, product_category, product_manufacturer, product_name, vat_level, product_code));
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
		  productsRepository.delete(id);
	      return productsRepository.findAll(); 
	  }
	 

	@GetMapping(value = "/allProducts")
	@ResponseBody
	public String allProducts() {
		ObjectMapper mapper = new ObjectMapper();
		LOG.appLogger().info("Data Map info: " + productsRepository.findAll());
		String jsonCollection = null;
		try {
			jsonCollection = mapper.writeValueAsString(productsRepository.findAll());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonCollection;

	}

}
