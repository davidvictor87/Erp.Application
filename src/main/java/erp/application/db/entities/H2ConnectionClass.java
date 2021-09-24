package erp.application.db.entities;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(
		transactionManagerRef = "h2EntityManagerFactory",
		basePackages = "erp.application"
			)
@Order(3)
public class H2ConnectionClass {
	
    @Autowired
    private Environment environment;
	
    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(environment.getRequiredProperty("spring.datasource.driverClassName"));
        dataSource.setJdbcUrl(environment.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
        return dataSource;
    }
	
    @Bean(name = "h2EntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
	    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
	    emf.setDataSource(dataSource());
	    HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
	    emf.setJpaVendorAdapter(jpaAdapter);
	    emf.setPackagesToScan(new String[]{"erp.application.h2.model", "erp.application.h2.repository"});
	    emf.afterPropertiesSet();
	    return emf.getObject();
    }

}
