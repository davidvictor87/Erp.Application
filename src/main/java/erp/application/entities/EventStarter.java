package erp.application.entities;

import erp.application.products.Products;
import java.util.Map;

public interface EventStarter {
	
	public void eventStarter(int id, String product_category, String product_manufacturer, String product_name, int vat_level, int prodct_price, String product_code);

}
