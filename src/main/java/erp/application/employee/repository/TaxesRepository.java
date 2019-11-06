package erp.application.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import erp.application.employee.model.Taxes;

@Repository
public interface TaxesRepository extends JpaRepository<Taxes, Double>{

}
