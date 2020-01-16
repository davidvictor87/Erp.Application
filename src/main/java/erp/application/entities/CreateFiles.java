package erp.application.entities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.security.SecureRandom;

import erp.application.entities.models.FileModel;

public class CreateFiles {
	
	public static void createFiles(int id, String name, String profession, String address, boolean isSmoker) {
		LOG.appLogger().info("Starting to write a file");
		FileModel fileModel = new FileModel(id, name, profession, address, isSmoker);
		SecureRandom secureRandom = new SecureRandom();
		File file = new File("D:/SecuredFile/UniqueFile" + secureRandom.nextInt() +".txt");
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(int i = 0; i < fileModel.toString().split(",").length; i++) {
				bw.write(fileModel.toString().split(",")[i]);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			LOG.appLogger().error("File not found FAIL", e);
		}catch (Exception e) {
			LOG.appLogger().error("CRITICAL SYSTEM FAILURE WITH ROOT CAUSE: ", e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

}
