package erp.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import erp.application.entities.LOG;
import erp.application.service.UploadFileService;

@RestController
@RequestMapping(value="/add/working/data")
public class UploadWorkingFile {
	
	@Autowired
	private UploadFileService uploadService;
	
	@PostMapping(value="/upload/{file}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(MultipartFile workingFile) {
		try {
			LOG.appLogger().info("Received File: " + workingFile);
			uploadService.uploadFile(workingFile.getOriginalFilename());
			return new ResponseEntity<>("File Uploaded Successfully", HttpStatus.OK);
		}catch (Exception e) {
			LOG.appLogger().error(e.getMessage());
			return new ResponseEntity<>("Failed To Upload File", HttpStatus.NOT_FOUND);
		}
	}
}
