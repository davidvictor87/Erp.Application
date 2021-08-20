package erp.application.controller;

import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import erp.application.entities.LOG;
import erp.application.entities.JDBCUpdate;
import erp.application.login.model.Users;
import erp.application.login.repository.UserRepository;
import erp.application.service.UsersService;
import erp.application.service.WriteToFileBlockedUsernames;
import erp.application.web.security.PreventAddingUndesiredValues;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;

@Controller
public class UsersManagerController {

	private UserRepository uRepository;
	private UsersService userService;
	private PreventAddingUndesiredValues undesired;
	private WriteToFileBlockedUsernames unwantedUsernames;

	@Autowired
	public UsersManagerController(@Qualifier(value = "UserRepository") UserRepository userRepository,
			@Qualifier(value = "usersService") UsersService uService,
			@Qualifier(value = "undisered") PreventAddingUndesiredValues reject,
			@Qualifier("blockedusernames") WriteToFileBlockedUsernames blocked) {
		this.uRepository = userRepository;
		this.userService = uService;
		this.undesired = reject;
		this.unwantedUsernames = blocked;
	}

	@PostMapping(value = "/add/user")
	@ResponseBody
	public ModelAndView userFactory(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password, @RequestParam(value = "fName") String fName,
			@RequestParam(value = "sName") String sName, @RequestParam(value = "status") String status)
			throws IOException {
		final String nameLength = "^[a-zA-Z]{6,12}$";
		Pattern pattern = Pattern.compile(nameLength);
		Matcher matcher = pattern.matcher(sName);
		LOG.appLogger().info("Hit the endpoint.." + sName.length() + " " + password.length() + " " + isValid(password));
		if (matcher.matches() && isValid(password) && (password.length() >= 8) && undesired.preventAdd(fName)) {
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

	@GetMapping(value = "/PannelUser")
	public String showUsers(Model model, @RequestParam(defaultValue = "0") int startPage) {
		final int pageSize = 20;
		model.addAttribute("data", uRepository.findAll(PageRequest.of(startPage, pageSize)));
		return "delete.html";
	}

	@PutMapping(value = "/update/user{id}")
	public ResponseEntity<Users> updateUser(@RequestParam(value = "id") Long id,
			@RequestHeader(name = "update-user") HttpHeaders headers) {
		LOG.appLogger().info("Input info: " + id);
		System.out.println("Id is " + id);
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

	@PostMapping("/unwanted")
	@ResponseBody
	public ModelAndView addUnwantedUsers(@NotBlank @Valid @RequestParam(value = "username") final String username) {
		LOG.appLogger().warn("User with name: " + username + " will not be able to be added");
		ModelAndView view = new ModelAndView("unwanted.html");
		try {
			unwantedUsernames.writeUnwantedUsers(username);
			view.setStatus(HttpStatus.OK);
			view.setViewName("unwanted.html");
			return view;
		} catch (Exception e) {
			e.printStackTrace();
			view.setStatus(HttpStatus.BAD_GATEWAY);
			view.setViewName("error.html");
		}
		return view;
	}

	@PostMapping(value = "/save/user")
	public String saveUser(@Valid @RequestBody Users user) {
		LOG.appLogger().info("Received data: " + user);
		try {
			LOG.appLogger().warn("Processed data: " + user);
			System.out.println("Active: " + user.getActive());
			userService.saveUser(user);
		} catch (Exception e) {
			LOG.appLogger().error("Catched error: " + e.getMessage());
		}
		return "redirect:/PannelUser";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/delete/user{id}")
	public String deleteUser(@RequestParam(value = "id") Long id, HttpServletRequest req) {
		try {
			System.out.println("Method access: " + id);
			uRepository.deleteById(id);
			HttpSession session = req.getSession();
			LOG.appLogger().info("Creation Time: " + session.getCreationTime());
		} catch (Exception e) {
			throw new RuntimeException("Cannot delete user with id: " + id + ", Err: " + e.getMessage());
		}
		return "redirect:/PannelUser";
	}

	private boolean isValid(String input) {
		final String charachter = ".*[a-zA-Z].*";
		final String numeric = ".*[0-9].*";
		return input.matches(charachter) && input.matches(numeric);
	}

}
