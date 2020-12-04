package erp.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import erp.application.entities.LOG;
import erp.application.products.ProductsXmlFormat;
import erp.application.service.RedisToXmlConverter;

@Controller
@RequestMapping(value="/export/product/")
public class ExportXmlFile {
	
	private final RedisToXmlConverter redisToXmlConverter;
	
	@Autowired
	public ExportXmlFile(@Qualifier final RedisToXmlConverter redisToXml) {
		super();
		this.redisToXmlConverter = redisToXml;
	}
	
	@GetMapping(value = "/xml/format", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public ProductsXmlFormat exportXmlFile() {
		LOG.appLogger().info("START EXPORTING XML FORMAT");
		try{
			return redisToXmlConverter.xmlConverter();
		}catch (Exception e) {
			e.printStackTrace();
			LOG.appLogger().error("FAILED TO EXPORT XML FORMAT");
			return null;
		}
	}

}
