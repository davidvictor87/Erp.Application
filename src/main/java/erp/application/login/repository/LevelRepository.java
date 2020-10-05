package erp.application.login.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import erp.application.login.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Role, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE role u SET u.role = :role WHERE u.role_id = :role_id", nativeQuery=true)
	public void updateUserWithId(@Param("role_id") Long roleId, @Param("role") String userRole);

	

}
