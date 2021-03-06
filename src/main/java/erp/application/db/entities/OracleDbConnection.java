package erp.application.db.entities;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import erp.application.entities.ApplicationStaticInfo;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	entityManagerFactoryRef = "oracleEntityManager",
	transactionManagerRef = "oracleTransactionManager",
	basePackages = "erp.application"
		)
@PropertySource({ "classpath:oracle-db.properties" })
@Order(2)
public class OracleDbConnection {
	
	private Environment environment;
	@Value("${oracle.db.username}")
	private String ORCL_USERNAME;
	@Value("${oracle.db.password}")
	private String ORCL_PASSWORD;
	
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
		properties.put("hibernate.hbm2ddl.auto", "update");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}
	
	@Bean
	public DataSource oracleDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(ApplicationStaticInfo.ORACLE_DRIVER_PSY_ENGINE);
		dataSource.setUrl(ApplicationStaticInfo.ORACLE_URL);
		dataSource.setUsername(ORCL_USERNAME);
		dataSource.setPassword(ORCL_PASSWORD);
		return dataSource;
	}
	
	@Bean(name = "oracleTransactionManager")
	public PlatformTransactionManager oracleTransactionManager() {
		JpaTransactionManager jpaManager = new JpaTransactionManager();
		jpaManager.setEntityManagerFactory(oracleEntityManager().getObject());
		return jpaManager;

	}

}
