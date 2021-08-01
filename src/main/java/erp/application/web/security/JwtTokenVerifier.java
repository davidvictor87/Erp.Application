package erp.application.web.security;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenVerifier extends OncePerRequestFilter{

	private SecretKey secretKey;
	private JwtConfig jwtConfig;

	public JwtTokenVerifier(SecretKey secretKey, JwtConfig jwtConfig) {
		super();
		this.secretKey = secretKey;
		this.jwtConfig = jwtConfig;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
		if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
		try {
			Jws<Claims> jws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			Claims body = jws.getBody();
			String username = body.getSubject();
			List<Map<String, String>> authorithies = (List<Map<String, String>>) body.get("authorithies");
			Set<SimpleGrantedAuthority> simpleGrantedAuthorithies = authorithies.stream()
					.map(m -> new SimpleGrantedAuthority(m.get("authority"))).collect(Collectors.toSet());
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
					simpleGrantedAuthorithies);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (JwtException e) {
			throw new IllegalStateException(e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

}
