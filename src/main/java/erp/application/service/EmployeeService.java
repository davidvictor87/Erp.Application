package erp.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.application.employee.model.EmployeeInitialSavedData;
import erp.application.employee.repository.EmployeeInitialSavedDataRepo;
import erp.application.employee.repository.EmployeeProcessedDataRepo;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeInitialSavedDataRepo initRepo;
	@Autowired
	private EmployeeProcessedDataRepo processedRepo;
	
	public void saveInitiaInfos(EmployeeInitialSavedData employee) {
		initRepo.saveAndFlush(employee);
	}
	
	public void find() {
		System.out.println(initRepo.findAll());
	}

}
