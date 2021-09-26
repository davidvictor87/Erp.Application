import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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

@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(
		entityManagerFactoryRef = "h2ManagerFactory",
		transactionManagerRef = "h2TransactionManager",
		basePackages = "erp.application"
			)
@ComponentScan("erp.application.h2.model")
@Order(3)
public class H2ConnectionClass {
	
	@Autowired
	private Environment environment;
	
    @Bean(name="h2ManagerFactory")
	public LocalContainerEntityManagerFactoryBean h2EntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] {"erp.application.h2.model", "erp.application.h2.repository"});
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(adapter);
		Map<String, Object> properties = new HashMap<>();
		properties.put("spring.jpa.hibernate.ddl-auto", environment.getProperty("spring.jpa.hibernate.ddl-auto=create-drop"));
		properties.put("spring.jpa.hibernate.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		properties.put("hibernate.dialect", environment.getProperty("org.hibernate.dialect.H2Dialect"));
		em.setJpaPropertyMap(properties);
		return em;
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:testdb");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
	
	@Bean(name= "h2TransactionManager")
	public PlatformTransactionManager h2TransactionManager() {
		JpaTransactionManager jpaManager = new JpaTransactionManager();
		jpaManager.setEntityManagerFactory(h2EntityManager().getObject());
		return jpaManager;
	}

}
