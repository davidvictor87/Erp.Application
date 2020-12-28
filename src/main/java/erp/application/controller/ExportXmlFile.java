package erp.application.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import erp.application.entities.LOG;
import erp.application.products.ProductsXmlFormat;
import erp.application.service.FtpSender;
import erp.application.service.RedisToXmlConverter;

@Controller
@RequestMapping(value="/export/product/")
public class ExportXmlFile {
	
	private final RedisToXmlConverter redisToXmlConverter;
	private final FtpSender ftpSender;
	
	@Autowired
	public ExportXmlFile(@Qualifier(value="exportToXML") final RedisToXmlConverter redisToXml, @Qualifier(value="ftpSender") FtpSender sender) {
		super();
		this.redisToXmlConverter = redisToXml;
		this.ftpSender = sender;
	}
	
	@GetMapping(value = "/xml/format", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	public ProductsXmlFormat exportXmlFile(final int id) {
		LOG.appLogger().info("START EXPORTING XML FORMAT");
		try{
			return redisToXmlConverter.xmlConverter(id);
		}catch (Exception e) {
			e.printStackTrace();
			LOG.appLogger().error("FAILED TO EXPORT XML FORMAT");
			return null;
		}
	}
	
	@PostMapping(value= "/ftp/export")
	public ResponseEntity<?> ftpFileSender(){
		try {
			ftpSender.establishFtpConnection();
			return ResponseEntity.ok().build();
		}catch (Exception e){
			
		}
		   return ResponseEntity.badRequest().build();
	}

}
