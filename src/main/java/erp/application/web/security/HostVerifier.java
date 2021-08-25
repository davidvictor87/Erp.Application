package erp.application.web.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import erp.application.entities.LOG;

public class HostVerifier implements HostnameVerifier {

	@Value("${ssl.password}")
	private String trustPassword;
	@Value("${keystore.location}")
	private String trustResource;
	@Value("${server.ssl.trust-store-type}")
	private String trustType;

	@Autowired
	public HostVerifier(String password, Resource resorce, String type) {
		super();
		this.trustPassword = password;
		this.trustResource = resorce.getFilename();
		this.trustType = type;
	}

	@Override
	public boolean verify(String hostname, SSLSession session) {
		try {
			X509Certificate[] certificate = (X509Certificate[]) session.getPeerCertificates();
			final int size = certificate.length;
			KeyStore trustStore = KeyStore.getInstance(trustType);
			ClassPathResource pathResource = new ClassPathResource(trustResource);
			InputStream input = pathResource.getInputStream();
			trustStore.load(input, trustPassword.toCharArray());
			for (X509Certificate x509Cert : certificate) {
				ArrayList<String> list = Collections.list(trustStore.aliases());
				for (String alias : list) {
					X509Certificate cert = (X509Certificate) trustStore.getCertificate(alias);
					final boolean selfSigned = cert.getIssuerDN().equals(cert.getSubjectDN());
					if (cert.equals(x509Cert) && (!selfSigned || size == 1)) {
						return true;
					}
				}
			}
		} catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			LOG.appLogger().error("=== FAILED TO VERIFY ===");
		}
		return false;
	}

}
