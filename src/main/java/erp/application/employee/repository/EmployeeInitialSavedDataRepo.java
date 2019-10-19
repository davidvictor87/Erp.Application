package erp.application.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import erp.application.employee.model.EmployeeInitialSavedData;;

@Repository
public interface EmployeeInitialSavedDataRepo extends JpaRepository<EmployeeInitialSavedData, Integer>{

	public void save(EmployeeInitialSavedDataRepo employee);


}
