package erp.application.entities.receipt;

import java.util.HashMap;
import java.util.Map;
import erp.application.products.Products;
import erp.application.service.CreateProductFiles;

public class AsyncReceiptPrint implements EventStarter{
	
	@Override
	public void eventStarter(int id, String product_category, String product_manufacturer, String product_name,
			int vat_level, int prodct_price, String product_code) {
		CreateProductFiles createProductFiles = new CreateProductFiles();
		String receipt_id = String.valueOf(id);
		Products products = new Products(id, product_category, product_manufacturer, product_name, vat_level, prodct_price, product_code);
		Map<String, Products> mapWithProducts= new HashMap<>();
		mapWithProducts.put(receipt_id, products);
		createProductFiles.writeProductFile(mapWithProducts);
	}
	
}
