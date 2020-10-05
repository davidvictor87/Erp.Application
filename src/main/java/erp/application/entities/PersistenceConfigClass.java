package erp.application.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

import erp.application.db.entities.MySQLConnectionClass;

@Configuration
public class PersistenceConfigClass {
	
	
	public LocalEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
		factoryBean.setPersistenceUnitName("PERSISTENCE");
		return factoryBean;
	}

}
