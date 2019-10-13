package erp.application.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import erp.application.login.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Role, Long>{

	

}
