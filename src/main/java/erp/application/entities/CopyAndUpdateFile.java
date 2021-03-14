package erp.application.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.io.RandomAccessFile;
import java.nio.file.Files;

public class CopyAndUpdateFile {
	
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
	private final Date date = new Date();
	private static final String PREFIX_STRING = "PROCESSED";
	
	public void copyDirectory(File fileSource) {
		
		LOG.appLogger().warn("- Copy file started -");
		
		final String fileDest = ApplicationStaticInfo.EMPLOYEE_PROCCESSED_DOCUMENTS + simpleDateFormat.format(date) + "txt";
		
		 try (Stream<String> lines = Files.lines(fileSource.toPath())) {
		        long numberOfLines = lines.count();
		        if(numberOfLines > 5L) {
		        	LOG.appLogger().error("Unconformal file format");
		        	return;
		       }
		       lines.close();
		    }catch (IOException e) {
		    	e.printStackTrace();
		    }

		File fileDestination = new File(fileDest);
		if(!fileDestination.exists()) {
			try {
				fileDestination.createNewFile();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.getMessage().toString();
			}
		}
		
		Date d = new Date(fileDestination.lastModified());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		sdf.format(d);
		
		if(!fileDestination.exists()) {
		   fileDestination.mkdirs();
		}
		
		System.out.println(fileSource.exists());
		
		try {
			if(fileSource.exists()) {
				System.out.println(fileSource.exists());
			    FileUtils.copyFile(fileSource, fileDestination);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(fileSource.exists());
		
		if(fileSource.exists()) {
			System.out.println(fileSource.exists());
		    formatingFile(fileSource, fileDestination);
		}
		
	}
	
	public void formatingFile(File f1, File f2) {
		
		LOG.appLogger().warn("- Formating files started -");
		
		try {
			RandomAccessFile raf1 = new RandomAccessFile(f1, "rw");
			RandomAccessFile raf2 = new RandomAccessFile(f2, "rw");
			String st = null;
			while((st = raf1.readLine()) != null) {
				System.out.println(PREFIX_STRING + st);
				raf2.writeBytes(System.getProperty("line.separator"));
				raf2.writeBytes(PREFIX_STRING + st);
			}
			raf1.close();
			raf2.close();
		}catch (IOException e) {
			e.printStackTrace();
			LOG.appLogger().error("Error message: " + e.getLocalizedMessage());
		}
		
		f1.deleteOnExit();
		
	}

}
