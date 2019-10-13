package erp.application.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import erp.application.employee.Employee;
import erp.application.employee.EmployeeRepository;
import erp.application.service.CreateEmployeeFiles;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmployeeController {
	
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private CreateEmployeeFiles createEmployeeFiles;
	
	public EmployeeController(EmployeeRepository empRepo) {
		this.employeeRepository = empRepo;
	}
	
		
	@GetMapping(value="/addEmployee/{id}/{cnp}/{work_contract}/{employee_name}/{employee_salary}/{bank_account}")
	@ResponseBody
	public Employee addEmployee(@PathVariable("id") final Integer id, @PathVariable("cnp")final String cnp, @PathVariable("work_contract")final String work_contract, @PathVariable("employee_name")final String employee_name, 
			@PathVariable("employee_salary")final String employee_salary, @PathVariable("bank_account")final String bank_account) {
		employeeRepository.save(new Employee(id, cnp, work_contract, employee_name, employee_salary, bank_account));
		System.out.println(allEmployees());
		createEmployeeFiles.writeEmployeeFile(allEmployees());
		return employeeRepository.findById(id);
	}
	
	@PutMapping(value="/updateEmployee/{id}/{cnp}/{work_contract}/{employee_name}/{employee_salary}/{bank_account}")
	@ResponseBody
	public Employee updateEmployee(@PathVariable("id") final Integer id, @PathVariable("cnp") final String cnp, @PathVariable("work_contract") final String work_contract, 
			@PathVariable("employee_name") final String employee_name, @PathVariable("employee_salary") final String employee_salary, @PathVariable("bank_account") final String bank_account) {
		employeeRepository.update(new Employee(id, cnp, work_contract, employee_name, employee_salary, bank_account));
		return employeeRepository.findById(id);
	}
	
	@DeleteMapping(value="/deleteEmployee/{id}")
	@ResponseBody
	public Map<String, Employee> deleteEmployee(@PathVariable(value="id") final String i){
		int id = 0;
		try {
			id = Integer.parseInt(i);
		}catch (NumberFormatException e) {
			System.err.println(e.getCause().toString());
		}
		employeeRepository.delete(id);
		return allEmployees();
	}
	
	@GetMapping(value="/allEmployees")
	@ResponseBody
	public Map<String, Employee> allEmployees(){
		return employeeRepository.findAll();
		
	}

}
