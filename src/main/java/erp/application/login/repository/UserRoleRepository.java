package erp.application.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import erp.application.login.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String>{
	
	public void deleteByUserId(String id);

}
