package erp.application.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

//@ConfigurationProperties(prefix = "application.jwt")
@Configuration
public class JwtConfig {
	
	@Value("${application.jwt.secretKey}")
	private String secretKey;
	@Value("${application.jwt.tokenPrefix}")
	private String tokenPrefix;
	@Value("${application.jwt.tokenExpirationAfterDays}")
	private Integer tokenExpirationAfterDays;

	public JwtConfig() {}
	
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}

	public Integer getTokenExpirationAfterDays() {
		return tokenExpirationAfterDays;
	}

	public void setTokenExpirationAfterDays(Integer tokenExpirationAfterDays) {
		this.tokenExpirationAfterDays = tokenExpirationAfterDays;
	}
	
	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}



}
