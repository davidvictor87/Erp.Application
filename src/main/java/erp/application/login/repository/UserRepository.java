package erp.application.login.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import erp.application.login.model.Users;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

@Repository(value="UserRepository")
public interface UserRepository extends JpaRepository<Users, Integer>{
	
	public Optional<Users> findByName(String name);
	
	@Transactional
	@Modifying
	@Query(value="update user e SET e.email=:email, e.active=:active, e.name=:name, e.last_name=:last_name,"
			+ " e.password=:password WHERE e.user_id=:user_id", nativeQuery=true)
	public void updateUsersWithId(@Param("user_id")Long id, @Param("email") String email, @Param("active") String status, 
			@Param("name") String name, @Param("last_name") String username, @Param("password") String password);
	

}
