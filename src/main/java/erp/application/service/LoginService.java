package erp.application.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import erp.application.login.model.Users;
import erp.application.login.repository.UserRepository;
import erp.application.web.security.CustomUsersDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class LoginService implements UserDetailsService{
    
	private UserRepository userRepo;
	
	@Autowired
	public LoginService(UserRepository userRepo) {
		super();
	    this.userRepo = userRepo;
	}
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> users = userRepo.findByName(username);
		users.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		return users.map(CustomUsersDetails::new).get();
	
	}

}
