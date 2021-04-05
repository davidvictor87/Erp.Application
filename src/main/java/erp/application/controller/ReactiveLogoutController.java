package erp.application.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.server.ServerWebExchange;

import erp.application.entities.LOG;
import reactor.core.publisher.Mono;

@Component
public class ReactiveLogoutController {
	

    public Mono<String> logoutUser(ServerWebExchange exchange, Model model, HttpServletRequest req,
			HttpServletResponse resp, Authentication auth) {
		LOG.appLogger().info("Logout Hit");
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

}

