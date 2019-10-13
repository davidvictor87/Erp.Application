package erp.application.service;

import org.springframework.stereotype.Service;
import erp.application.employee.Employee;
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
public class CreateEmployeeFiles {

	private static Employee emp = new Employee();

	@SuppressWarnings("unused")
	public void writeEmployeeFile(Map<String, Employee> mapWithEmployees) {

		File eFile = new File("D:/SmartId/METI/import/empFile.txt");
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(eFile);
			fos.flush();
		} catch (FileNotFoundException e) {
			LOG.appLogger().error("File: " + eFile.getName() + " was not found");
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

		if (!eFile.exists()) {
			try {
				eFile.createNewFile();
			} catch (IOException e) {
				System.err.println(e.getMessage().toString());
			}
		}

		mapWithEmployees.entrySet().forEach(entryData -> {
			try {
				System.out.println("Recorded Data: " + mapWithEmployees.toString());
				bw.write("Employee ID: " + entryData.getValue().getId() + ", Employee CNP: "
						+ entryData.getValue().getCnp() + ", Employee work contract: "
						+ entryData.getValue().getWork_contract() + ", Employee Name: "
						+ entryData.getValue().getEmployee_name() + "Employee Salary: "
						+ entryData.getValue().getEmployee_salary() + ", Employee Bank Account: "
						+ entryData.getValue().getBank_account());
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
		File file = new File(eFile.getAbsolutePath().toString());

		try {
			sc = new Scanner(file, "UTF-8");
			String dataCaptor = null;
			String[] employees = new String[(int) file.length()];
			while (sc.hasNext()) {
				dataCaptor = sc.nextLine();
				employees = dataCaptor.split(" ");
				int index = -1;
				LOG.appLogger().info("List of Employees: " + Arrays.toString(employees));
				for (String empIter : employees) {
					index++;
					Employee employee = new Employee(Integer.parseInt(employees[index]), employees[index + 1],
							employees[index + 2], employees[index + 3], employees[index + 4], employees[index + 5]);
					emp = employee;
					System.out.println(employee);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOG.appLogger().error("File is not found, error: " + e.getMessage().toString());
		} finally {
			sc.close();
		}
	}

	public static Employee getEmployee() {
		if (emp == null) {
			System.err.println("Major Error, Employee not found");
			return null;
		} else
			return emp;
	}
}
