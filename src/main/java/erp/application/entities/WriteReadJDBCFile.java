package erp.application.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WriteReadJDBCFile {
	
	public static void writePrimaryKeys(String key1, String key2) {
		
		File file = new File("keys.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
		FileWriter fileWriter = null;
		
		try {
		    fileWriter = new FileWriter(file);
		    fileWriter.write(key1 + " " + key2);
		    fileWriter.flush();
		} catch (FileNotFoundException e) {
		    System.err.println(e.getMessage().toString());
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally {
			try {
				fileWriter.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static String[] readPrimaryKeys() {
		
		File file = new File("keys.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Scanner scan = null;
		String[] arr = null;
		
		try {
			scan = new Scanner(file);
			while(scan.hasNextLine()) {
				String s = scan.nextLine();
				arr = s.split(" ");			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			scan.close();
		}
		
		System.out.println(arr[0] + " " + arr[1]);
		
		return arr;
		
	}

}
