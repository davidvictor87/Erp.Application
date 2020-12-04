package erp.application.service;

import org.springframework.stereotype.Service;

import erp.application.entities.LOG;
import erp.application.products.Products;
import erp.application.products.ProductsXmlFormat;

@Service
public class RedisToXmlConverter {

	private Products products = null;

	public final ProductsXmlFormat xmlConverter(final int id) {
		LOG.appLogger().debug("START TO CONVERT OBJECTS FORMAT");
		products = new Products();
		ProductsXmlFormat prodXmlFormat = null;
		try {
			LOG.appLogger().info("OBJECTS CONVERTED WITH SUCCESS");
			if (id == products.getId()) {
				prodXmlFormat = new ProductsXmlFormat(products.getId(), products.getProduct_category(),
						products.getProduct_manufacturer(), products.getProduct_name(), products.getVat_level(),
						products.getProduct_price(), products.getProduct_code());
				return prodXmlFormat;
			}else {
				LOG.appLogger().error("OBJECT DON'T EXISTS");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.appLogger().error("FAILED TO CONVERT OBJECTS");
			return null;
		}
	}

}
