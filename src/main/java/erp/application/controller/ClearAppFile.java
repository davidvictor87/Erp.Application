package erp.application.controller;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/clear/file")
public class ClearAppFile {
	
	@GetMapping(value="/app/log")
	public ResponseEntity<?> clearAppLogFile(){
		try {
			FileChannel.open(Paths.get(new File("appLog.txt").getPath()), StandardOpenOption.WRITE).truncate(0).close();
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("App File Cleared", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>("App File Cleared", HttpStatus.ACCEPTED);
	}
	
	

}
