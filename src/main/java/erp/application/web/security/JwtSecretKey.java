package erp.application.web.security;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtSecretKey {
	
	@Autowired
	private JwtConfig jwtConfig;
		
	@Bean
	public SecretKey secretKey() {
		return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
	}

}
