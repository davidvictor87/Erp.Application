package erp.application.db.entities;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import erp.application.entities.ApplicationStaticInfo;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	entityManagerFactoryRef = "entityManagerFactory",
	transactionManagerRef = "transactionManager",
	basePackages = "erp.application"
		)
public class MySQLConnectionClass {
	
	@Autowired
	private Environment env;
	
	@Primary
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean mySqlEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] {"erp.application.login.model", "erp.application.login.repository"});
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(adapter);
		Map<String, Object> properties = new HashMap<>();
		properties.put("spring.jpa.hibernate.ddl-auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.put("spring.jpa.properties.hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		em.setJpaPropertyMap(properties);
		return em;
		
	}
	
	@Primary
	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(ApplicationStaticInfo.MY_SQL_DRIVER_CLASS);
		dataSource.setUrl(ApplicationStaticInfo.MY_SQL_URL);
		dataSource.setUsername("root");
		dataSource.setPassword("Password123");
		return dataSource;
	}
	
	@Primary
	@Bean(name= "transactionManager")
	public PlatformTransactionManager userTransactionManager() {
		JpaTransactionManager jpaManager = new JpaTransactionManager();
		jpaManager.setEntityManagerFactory(mySqlEntityManager().getObject());
		return jpaManager;
	}

}
