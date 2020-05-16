package erp.application.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import erp.application.products.Products;
import erp.application.products.ProductsRepository;
import erp.application.service.CreateProductFiles;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {
	
	private ProductsRepository productsRepository;
	
	@Autowired
	private CreateProductFiles createProductFiles;
	
	public ProductController(ProductsRepository prodRepo) {
		this.productsRepository = prodRepo;
	}
	
		
	@GetMapping(value="/addEmployee/{id}/{cnp}/{work_contract}/{employee_name}/{employee_salary}/{bank_account}")
	@ResponseBody
	public Products addEmployee(@PathVariable("id") final Integer id, @PathVariable("cnp")final String cnp, @PathVariable("work_contract")final String work_contract, @PathVariable("employee_name")final String employee_name, 
			@PathVariable("employee_salary")final int employee_salary, @PathVariable("bank_account")final String bank_account) {
		productsRepository.save(new Products(id, cnp, work_contract, employee_name, employee_salary, bank_account));
		System.out.println(allProducts());
		createProductFiles.writeEmployeeFile(allProducts());
		return productsRepository.findById(id);
	}
	
	@PutMapping(value="/updateProduct/{id}/{product_category}/{product_manufacturer}/{product_name}/{vat_level}/{product_code}")
	@ResponseBody
	public Products updateEmployee(@PathVariable("id") final Integer id, @PathVariable("product_category") final String product_category, 
			@PathVariable("product_manufacturer") final String product_manufacturer, @PathVariable("product_name") final String product_name, @PathVariable("vat_level") final int vat_level, @PathVariable("product_code") final String product_code) {
		productsRepository.update(new Products(id, product_category, product_manufacturer, product_name, vat_level, product_code));
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
		return allProducts();
	}
	
	@GetMapping(value="/allProducts")
	@ResponseBody
	public Map<String, Products> allProducts(){
		return productsRepository.findAll();
		
	}

}
