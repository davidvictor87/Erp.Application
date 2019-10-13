package erp.application.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import erp.application.employee.Employee;

@Configuration
public class RedisConfigurationEntity {
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}
	
	@Bean
	public RedisTemplate<String, Employee> redisTemplate(){
		RedisTemplate<String, Employee> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

}
