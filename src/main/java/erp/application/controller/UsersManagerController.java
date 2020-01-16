package erp.application.controller;

import org.springframework.web.servlet.ModelAndView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import erp.application.entities.LOG;
import erp.application.entities.JDBCUpdate;
import erp.application.login.model.Users;
import erp.application.login.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;

@Controller
public class UsersManagerController {

	UserRepository uRepository;

	@Autowired
	public UsersManagerController(@Qualifier(value = "UserRepository") UserRepository u) {
		uRepository = u;
	}

	@PostMapping(value = "/addUser")
	@ResponseBody
	public ModelAndView userFactory(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password, @RequestParam(value = "fName") String fName,
			@RequestParam(value = "sName") String sName, @RequestParam(value = "status") String status) {
		final String nameLength = "^[a-zA-Z]{6,12}$";
		Pattern pattern = Pattern.compile(nameLength);
		Matcher matcher = pattern.matcher(sName);
		LOG.appLogger().info("Hit the endpoint.." + sName.length() + " " + password.length() + " " + isValid(password));
		if (matcher.matches() && isValid(password) && (password.length() >= 8)) {
			Users user = new Users();
			user.setEmail(email).setPassword(password).setName(fName).setLastName(sName)
					.setActive(Integer.parseInt(status));
			LOG.appLogger().info("Added data: " + user.getEmail() + ", " + user.getPassword() + ", " + user.getName()
					+ ", " + user.getLastName() + ", " + user.getActive());
			uRepository.save(user);
			JDBCUpdate.updateRole(Integer.parseInt(status));
		}
		return new ModelAndView("register.html");
	}

	@GetMapping("/PannelUser")
	public String showUsers(Model model) {
		model.addAttribute("data", uRepository.findAll());
		return "delete.html";
	}

	@GetMapping(value = "/update-user{id}")
	public ResponseEntity<Users> updateUser(@RequestParam(value = "id") Long id) {
		LOG.appLogger().info("Input info: " + id);
		;
		HttpHeaders headers = null;
		try {
			System.out.println("User: " + uRepository.findById(id).get());
			headers = new HttpHeaders();
			return ResponseEntity.ok().headers(headers).body(uRepository.findById(id).get());
		} catch (Exception e) {
			LOG.appLogger().error(" ======== Update Process Failed with root cause ======== " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.badRequest().body(uRepository.findById(id).get());
		}

	}

	@PostMapping(value = "/save-user")
	public String saveUser(@Valid Users user) {
		LOG.appLogger().info("Received data: " + user);
		try {
			LOG.appLogger().warn("Processed data: " + user);
			uRepository.save(user);
		} catch (Exception e) {
			LOG.appLogger().error("Catched error: " + e.getMessage());
		}
		return "redirect:/PannelUser";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete-user{id}")
	public String deleteUser(@RequestParam(value = "id") Long id) {
		System.out.println("Method acceses " + id);
		uRepository.deleteById(id);
		return "redirect:/PannelUser";
	}

	private boolean isValid(String input) {
		final String charachter = ".*[a-zA-Z].*";
		final String numeric = ".*[0-9].*";
		return input.matches(charachter) && input.matches(numeric);
	}

}
