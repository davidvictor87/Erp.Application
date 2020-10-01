package erp.application.service;

import org.springframework.stereotype.Service;
import erp.application.products.Products;
import erp.application.entities.ApplicationStaticInfo;
import erp.application.entities.LOG;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

@Service
public class CreateProductFiles {

	private static Products products = new Products();

	@SuppressWarnings("unused")
	public void writeProductFile(Map<String, Products> mapWithProducts) {

		File productFile = new File(ApplicationStaticInfo.PRODUCTS_DOCUMENTS_DIRECTORY);
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(productFile);
			fos.flush();
		} catch (FileNotFoundException e) {
			LOG.appLogger().error("File: " + productFile.getName() + " was not found");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.appLogger().error(e.getMessage());
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		if (!productFile.exists()) {
			try {
				productFile.createNewFile();
			} catch (IOException e) {
				System.err.println(e.getMessage().toString());
			}
		}

		mapWithProducts.entrySet().forEach(entryData -> {
			try {
				System.out.println("Recorded Data: " + mapWithProducts.toString());
				bw.write("Product ID: " + entryData.getValue().getId() + ", Product Category: "
						+ entryData.getValue().getProduct_category() + ", Product manufacturer: "
						+ entryData.getValue().getProduct_manufacturer() + ", Product Name: "
						+ entryData.getValue().getProduct_name() + "Product VAT Level: "
						+ entryData.getValue().getVat_level() + ", Product Code: "
						+ entryData.getValue().getProduct_code());
				bw.newLine();
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scanner sc = null;
		File file = new File(productFile.getAbsolutePath().toString());

		try {
			sc = new Scanner(file, "UTF-8");
			String dataCaptor = null;
			String[] productsList = new String[(int) file.length()];
			while (sc.hasNext()) {
				dataCaptor = sc.nextLine();
				productsList = dataCaptor.split(" ");
				int index = -1;
				LOG.appLogger().info("List of Employees: " + Arrays.toString(productsList));
				for (String prodIter : productsList) {
					index++;
					Products prod = new Products(Integer.parseInt(productsList[index]), productsList[index + 1],
							productsList[index + 2], productsList[index + 3], Integer.parseInt(productsList[index + 4]), productsList[index + 5]);
					products = prod;
					System.out.println(prod);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOG.appLogger().error("File is not found, error: " + e.getMessage().toString());
		} finally {
			sc.close();
		}
	}

	public static Products getProducts() {
		if (products == null) {
			System.err.println("EMPLOYEE NOT FOUND");
			return null;
		} else
			return products;
	}
}
