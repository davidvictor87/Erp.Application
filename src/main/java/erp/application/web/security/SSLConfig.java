package erp.application.web.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStore.LoadStoreParameter;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

import erp.application.entities.LOG;

@Configuration
public class SSLConfig {
	
	@Value("${keystore.location}")
	private String keyStoreFile;
	@Value("${ssl.password}")
	private String keyPassword;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public RestTemplate sslTemplate() throws CertificateException, FileNotFoundException, IOException {
		TrustStrategy trustStrategy = null;
		KeyStore key = null;
		SSLContext context = null;
		SSLConnectionSocketFactory socketFactory = null;
		CloseableHttpClient httpClient = null;
		HttpComponentsClientHttpRequestFactory requestFactory = null;
		RestTemplate template = null;
		try {
			trustStrategy = new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			};
			key = KeyStore.getInstance(KeyStore.getDefaultType());
			key.load((InputStream) new FileInputStream(new File(keyStoreFile)), keyPassword.toCharArray());
		    context = SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build();
		    socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
		    httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
		    requestFactory = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    template = new RestTemplate();
		    return template;
		}catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			LOG.appLogger().error(" ==== SSLTemplate ERROR ====");
			e.printStackTrace();
			return null;
		}
	}

}
