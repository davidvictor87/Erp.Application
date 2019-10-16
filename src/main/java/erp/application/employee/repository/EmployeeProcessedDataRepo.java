package erp.application.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import erp.application.employee.model.EmployeeProcessedData;

@Repository
public interface EmployeeProcessedDataRepo extends JpaRepository<EmployeeProcessedData, Integer>{

}
