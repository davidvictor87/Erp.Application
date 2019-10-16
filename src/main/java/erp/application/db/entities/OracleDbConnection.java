package erp.application.db.entities;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	entityManagerFactoryRef = "oracleEntityManager",
	transactionManagerRef = "oracleTransactionManager",
	basePackages = "erp.application"
		)
@PropertySource({ "classpath:oracle-db.properties" })
public class OracleDbConnection {
	
	private Environment environment;
	
	@Autowired
	public OracleDbConnection(Environment env) {
		this.environment = env;
	}
	
	@Bean(name = "oracleEntityManager")
	public LocalContainerEntityManagerFactoryBean oracleEntityManager() {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(oracleDataSource());
		entityManager.setPackagesToScan(new String[] {"erp.application.employee.model", "erp.application.employee.model"});
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(vendorAdapter);
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.naming.physical-strategy", environment.getProperty("spring.oracle.jpa.hibernate.naming.physical-strategy"));
		properties.put("hibernate.dialect", environment.getProperty("spring.oracle.jpa.hibernate.dialect"));
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}
	
	@Bean
	public DataSource oracleDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.p6spy.engine.spy.P6SpyDriver");
		dataSource.setUrl("jdbc:p6spy:oracle:thin:@localhost:1521:orcl");
		dataSource.setUsername("SYSTEM");
		dataSource.setPassword("Secret.Password1987");
		return dataSource;
	}
	
	@Bean(name = "oracleTransactionManager")
	public PlatformTransactionManager oracleTransactionManager() {
		JpaTransactionManager jpaManager = new JpaTransactionManager();
		jpaManager.setEntityManagerFactory(oracleEntityManager().getObject());
		return jpaManager;

	}

}
