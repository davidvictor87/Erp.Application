package erp.application.login.repository;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import erp.application.login.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String>{
	
	public void deleteByUserId(String id);
	
	public CopyOnWriteArrayList<UserRole> findByUserId(String role_ID);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE role r SET r.role_id= :role_id WHERE r.user_id= :user_id", nativeQuery= true)
	public void updateUserWithId(@Param(value="user_id") String userId, @Param(value="role_id")String userRole);

}
