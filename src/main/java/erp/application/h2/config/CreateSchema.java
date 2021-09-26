package erp.application.h2.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateSchema {
	
	@Bean
	public void createH2DB() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
			Statement statement = conn.createStatement();
			statement.execute("CREATE TABLE employees (id integer, name varchar(100), profession varchar(100), salary varchar(100), isEnabled varchar(10))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
