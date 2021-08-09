package erp.application.service;

import java.util.PriorityQueue;
import java.util.Queue;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import erp.application.entities.LOG;
import erp.application.login.model.Users;
import erp.application.login.repository.UserRepository;

@Service
public class OrderUsers{

	private UserRepository userRepository;

	@Autowired
	public OrderUsers(UserRepository uRepo) {
		super();
		this.userRepository = uRepo;
	}
	
	@Secured(value="ROLE_ADMIN, ROLE_MANAGER, ROLE_USER")
	public Queue<Users> orderUsers(){
		Queue<Users> usersQueue = new PriorityQueue<Users>();
		try {
			LOG.appLogger().info("Start ordering users for processing");
			usersQueue.add(userRepository.findAll().iterator().next());
			while(!usersQueue.isEmpty()) {
				usersQueue.remove();
			}
		}catch (Error ex) {
			throw new RuntimeErrorException(ex, "== Fail to order users ==");
		}
		return usersQueue;
	}

}
