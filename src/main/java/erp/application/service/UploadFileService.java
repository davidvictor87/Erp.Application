package erp.application.service;

import java.io.File;
import java.io.FileOutputStream;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class UploadFileService {

	public void uploadFile(String filePath){
		File receivedFile = null;
		FileOutputStream fout = null;
		final String uploadDirectory = "D:/SourceDirectory/";
		try {
			receivedFile = new File(uploadDirectory + "" + filePath);
			if(!receivedFile.exists()) {
				receivedFile.createNewFile();
			}
			fout = new FileOutputStream(receivedFile);
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fout.close();
				fout.flush();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
