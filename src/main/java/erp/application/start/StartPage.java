package erp.application.start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import erp.application.entities.LOG;
import erp.application.login.model.Users;
import reactor.core.publisher.Mono;

@RestController
@WebServlet
@ServletSecurity
public class StartPage {

	@PreAuthorize(value = "hasAnyRole(T(erp.application.web.security.RolesAndRights).ADMIN.name(), T(erp.application.web.security.RolesAndRights).MANAGER.name(), T(erp.application.web.security.RolesAndRights).USER.name())")
	@PostFilter(value = "hasPermision(T(erp.application.web.security.RolesAndRights).READ.name())")
	@GetMapping("/Daily-Tasks")
	public List<Users> infoUser(HttpServletResponse res, HttpServletRequest req) {
		final List<Users> allUsers = new ArrayList<Users>();
		try {
			final String getInput = req.getParameter("Daily-Tasks");
			if (getInput != null) {
				res.sendRedirect("/daily/tasks");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allUsers;
	}

	@PreAuthorize(value = "hasAnyRole(T(erp.application.web.security.RolesAndRights).ADMIN.name())")
	@PostFilter(value = "hasPermission(T(erp.application.web.security.RolesAndRights).WRITE.name())")
	@GetMapping(value = "/UserManager")
	public void RegisterUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			final String getInput = request.getParameter("Register");
			if (getInput != null) {
				response.sendRedirect("register.html");
			}
			LOG.appLogger().info("Redirect Succeded");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.appLogger().error("Redirect Process Failed");
		}
	}

	@PreAuthorize(value = "hasAnyRole(T(erp.application.web.security.RolesAndRights).ADMIN.name())")
	@PostFilter(value = "hasPermission(T(erp.application.web.security.RolesAndRights).DELETE.name())")
	@RequestMapping(value = "/DeleteUser", method = RequestMethod.GET)
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
		final String input = request.getParameter("Delete");
		try {
			if (input != null)
				response.sendRedirect("/PannelUser");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PreAuthorize(value = "hasAnyRole(T(erp.application.web.security.RolesAndRights).ADMIN.name(), T(erp.application.web.security.RolesAndRights).MANAGER.name(), T(erp.application.web.security.RolesAndRights).USER.name())")
	@PostFilter(value = "hasPermision(T(erp.application.web.security.RolesAndRights).READ.name())")
	@GetMapping("/logout")
	public Mono<String> logoutUser(ServerWebExchange exchange, Model model, HttpServletRequest req,
			HttpServletResponse resp, Authentication auth) {
		try {
			auth = SecurityContextHolder.getContext().getAuthentication();
			LOG.appLogger().info("Name: " + auth.getName());
			SecurityContextLogoutHandler sclh = new SecurityContextLogoutHandler();
			sclh.logout(req, resp, auth);
			LOG.appLogger().info("Logout Hit");
			
			Mono<CsrfToken> csrfToken = exchange.getAttributeOrDefault(CsrfToken.class.getName(), Mono.empty());
			return csrfToken.map(token -> {
				model.addAttribute("parameterName", SecurityContextHolder.getContext().getAuthentication().getName());
				model.addAttribute("_csrf", csrfToken);
				return "login";
			});
		} catch (Exception e) {
			LOG.appLogger().info("Error");
			e.printStackTrace();
		}
		return null;
	}

	@PreAuthorize(value = "hasAnyRole('ADMIN')")
	@GetMapping(value = "/text")
	@ResponseBody
	public String testSecurity() {
		return "test";
	}

}
