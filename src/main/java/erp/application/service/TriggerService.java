package erp.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import erp.application.login.repository.LevelRepository;
import erp.application.login.repository.UserRepository;
import erp.application.login.repository.UserRoleRepository;

@Service
@Transactional
public class TriggerService {
	
	@Autowired
	private LevelRepository levelRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserRoleRepository urRepo;
	
	public void deleteUser(long id) {
		long l = 0 + id;
		userRepo.deleteById(id);
		levelRepo.deleteById(l);
		urRepo.deleteByUserId(String.valueOf(id));
	}


}
