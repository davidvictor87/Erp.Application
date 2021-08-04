package erp.application.controller;

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
import erp.application.service.OrderUsers;

@Controller
@RequestMapping(value = "/users/management")
public class UserManagement {
	
	private OrderUsers users;
	
	@Autowired
	public UserManagement(OrderUsers users) {
		this.users = users;
	}

	@PreAuthorize(value = "hasAnyRole(T(erp.application.web.security.RolesAndRights).ADMIN.name(), T(erp.application.web.security.RolesAndRights).MANAGER.name(), T(erp.application.web.security.RolesAndRights).USER.name())")
	@PostFilter(value = "hasPermision(T(erp.application.web.security.RolesAndRights).READ.name())")
	@GetMapping("/Daily-Tasks")
	@ResponseBody
	public Queue<Users> infoUser(HttpServletResponse res, HttpServletRequest req) {
		try {
			final String getInput = req.getParameter("Daily-Tasks");
			if (getInput != null) {
				res.sendRedirect("/user/erp/daily/tasks");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users.orderUsers();
	}

}
