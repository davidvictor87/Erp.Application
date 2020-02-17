package erp.application.web.security;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import erp.application.entities.LOG;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authManager;
	private JwtConfig jwtConfig;
	private SecretKey secretKey;

	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtConfig jwtConfig,
			SecretKey secretKey) {
		super();
		this.authManager = authManager;
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
					.readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
			Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
					authenticationRequest.getPassword());
			Authentication authenticate = authManager.authenticate(authentication);
			return authenticate;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.attemptAuthentication(request, response);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException{
		try {
			String token = Jwts.builder().setSubject(authResult.getName())
					.claim("authorithies", authResult.getAuthorities()).setIssuedAt(new Date())
					.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2))).signWith(secretKey).compact();
			response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
		} catch (Exception e) {
			LOG.appLogger().error(e.getMessage());
		}
	}

}
