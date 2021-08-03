package erp.application.controller;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import erp.application.login.model.Users;
import erp.application.login.repository.UserRepository;

@Controller
@RequestMapping(value = "/users/management")
public class UserManagement {

	@Autowired
	UserRepository u;

	@PreAuthorize(value = "hasAnyRole(T(erp.application.web.security.RolesAndRights).ADMIN.name(), T(erp.application.web.security.RolesAndRights).MANAGER.name(), T(erp.application.web.security.RolesAndRights).USER.name())")
	@PostFilter(value = "hasPermision(T(erp.application.web.security.RolesAndRights).READ.name())")
	@GetMapping("/Daily-Tasks")
	@ResponseBody
	public Queue<Users> infoUser(HttpServletResponse res, HttpServletRequest req) {
		Queue<Users> allUsers = new PriorityQueue<Users>();
		Users user = null;
		// System.out.println(u.findAll().stream().findFirst().get().getRoles().stream().findFirst().ifPresent(allUsers.add(user)));
		// System.out.println(user.getRoles());
		try {
			final String getInput = req.getParameter("Daily-Tasks");
			if (getInput != null) {
				res.sendRedirect("/user/erp/daily/tasks");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allUsers;
	}

}

class Comp implements Comparable<Users> {

	@Autowired
	UserRepository u;

	@Override
	public int compareTo(Users o) {
		if (o == null) {
			return 0;
		}
		if(o.getRoles().iterator().next().getRole().equals("ADMIN")) {
			return -1;
		}else if(o.getRoles().iterator().next().getRole().equals("MANAGER")) {
			return 1;
		}else if(o.getRoles().iterator().next().getRole().equals("USER")) {
			return 2;
		}else
			return 0;
	}

}
