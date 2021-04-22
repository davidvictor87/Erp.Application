package erp.application.web.security;

import javax.net.ssl.SSLContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

@Configuration
public class SSLContextConfig {
	
	@Value("${keystore.location}")
	private String keyStoreFile;
	@Value("${ssl.password}")
	private String keyPassword;
	@Value("${trustore.location}")
	private String trustStore;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder templateBuilder) throws Exception{
		new SSLContextBuilder();
		SSLContext sslContext = SSLContextBuilder.create().loadKeyMaterial(ResourceUtils.getFile(keyStoreFile), 
				keyPassword.toCharArray(), keyPassword.toCharArray())
				.loadTrustMaterial(ResourceUtils.getFile(trustStore), keyPassword.toCharArray()).build();
		HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();
		return templateBuilder.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
	}

}
