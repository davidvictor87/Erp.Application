package erp.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import erp.application.products.Products;
import erp.application.service.CreateProductFiles;

@Controller
@SessionAttributes(value = "attributes")
public class ProductsController {
	
	@ModelAttribute(value="products")
	public Products getProduct() {
		return new Products();
	}
	
	@RequestMapping(value="/get/products", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Products> employeeJsonResponse(@ModelAttribute(value="products") Products product){
		product = CreateProductFiles.getProducts();
		try {
			return new ResponseEntity<Products>(product, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Products>(HttpStatus.NOT_FOUND);
	}

}
