package erp.application.service;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.application.controller.UsersManagerController;
import erp.application.entities.LOG;
import erp.application.entities.ParametersInterface;
import erp.application.entities.UsersAbstractEntity;
import erp.application.login.model.LevelType;
import erp.application.login.repository.LevelRepository;
import erp.application.login.repository.UserRoleRepository;

@Service
public class UsersService extends UsersAbstractEntity{

	private UserRoleRepository userRoleRepository;
	private LevelRepository levelRepository;

	@Autowired
	public UsersService(UserRoleRepository uRoleRepositor, LevelRepository lRepository) {
		super();
		this.userRoleRepository = uRoleRepositor;
		this.levelRepository = lRepository;
	}

	@Override
	@Transactional
	public void userRole(String user_id, String role_id) {
		try {
			LOG.appLogger().warn("Update USER_ROLE table begun, parameters received: ", user_id, role_id);
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSISTENCE");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Query updateQuery = em
					.createQuery("UPDATE user_role us SET us.roleId = :role_id WHERE us.userId = " + user_id);
			updateQuery.setParameter("role_id", role_id);
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			LOG.appLogger().error("Error with cause: ", e.getLocalizedMessage());
		}
	}

	@Override
	@Transactional
	public void updateUsers(String role_ID, String level) {
		try {
			LOG.appLogger().info("Role id value is: " + role_ID);
			String role_id = userRoleRepository.findByUserId(role_ID).parallelStream()
					.filter(findRoleByUserId -> findRoleByUserId.getUserId().equals(role_ID)).findFirst().get().getRoleId();
			levelRepository.updateUserWithId(Long.parseLong(role_id), roleChanger(level));
		} catch (Exception e) {
			LOG.appLogger().error("ERROR: " + e.getMessage());
		}
	}

	private final String roleChanger(String id) {
		String USER_LEVEL = null;
		switch (id) {
		case "1":
			USER_LEVEL = LevelType.ADMIN.toString();
			break;
		case "2":
			USER_LEVEL = LevelType.MANAGER.toString();
			break;
		case "3":
			USER_LEVEL = LevelType.USER.toString();
			break;
		default:
			LOG.appLogger().error("ERROR: Switch in default case");
			break;
		}
		return USER_LEVEL;

	}

}
