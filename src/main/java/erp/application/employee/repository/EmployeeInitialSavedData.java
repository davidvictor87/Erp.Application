package erp.application.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeInitialSavedData extends JpaRepository<EmployeeInitialSavedData, Integer>{

}
