package erp.application.web.security;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import erp.application.entities.LOG;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestRejectedExceptionFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			RequestRejectedException requestRejectedException = (RequestRejectedException) request
					.getAttribute("isNormalized");
			if (Objects.nonNull(requestRejectedException)) {
				throw requestRejectedException;
			} else {
				chain.doFilter(request, response);
			}
		} catch (RequestRejectedException requestRejectedException) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			LOG.appLogger().error("request_rejected: remote={}, user_agent={}, request_url={}",
					httpServletRequest.getRemoteHost(), httpServletRequest.getHeader(HttpHeaders.USER_AGENT),
					httpServletRequest.getRequestURL(), requestRejectedException);

			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
