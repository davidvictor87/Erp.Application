package erp.application.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import erp.application.entities.AsyncReceiptPrint;
import erp.application.entities.AsynchrousHandler;
import erp.application.entities.EventStarter;
import erp.application.entities.LOG;
import erp.application.service.ProductManagementService;

@Controller
@RequestMapping(value = "/sell/category")
public class ProductSellController {
	
	private ProductManagementService productService;
	private AsynchrousHandler asyncHandler;
	private EventStarter eventStarter;
	
	@Autowired
	public ProductSellController(ProductManagementService pService, AsynchrousHandler aHandler) {
		super();
		this.productService = pService;
		this.asyncHandler = aHandler;
		this.eventStarter = new AsyncReceiptPrint();
	}
	
	@PostMapping(value="/product/{id}")
	@ResponseBody
	public void sellingManager(@PathVariable(value = "id") int id) {
		LOG.appLogger().info("SELECTING PRODUCT WITH ID: " + id);
		productService.deleteProduct(id);
		try {
			asyncHandler.registerEventHandler(eventStarter);
			asyncHandler.executeAsyncTask(productService.returnAllProducts().get(String.valueOf(id)).getId(), productService.returnAllProducts().get(String.valueOf(id)).getProduct_category(),
					productService.returnAllProducts().get(String.valueOf(id)).getProduct_manufacturer(), productService.returnAllProducts().get(String.valueOf(id)).getProduct_name(), 
					productService.returnAllProducts().get(String.valueOf(id)).getVat_level(), productService.returnAllProducts().get(String.valueOf(id)).getProduct_price(), 
					productService.returnAllProducts().get(String.valueOf(id)).getProduct_code());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			LOG.appLogger().error("ERROR FOR SELECTING PRODUCT WITH ID: " + id);
		}
		
	}

}
