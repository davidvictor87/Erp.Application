package erp.application.entities;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import erp.application.entities.cacheable.CacheConfigurationProperties;
import erp.application.products.Products;

@Configuration
@EnableConfigurationProperties(value= CacheConfigurationProperties.class)
public class RedisConfigurationEntity {

	private static RedisCacheConfiguration createCacheConfiguration(long timeoutInDays) {
		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(timeoutInDays));
	}

	@Bean
	public LettuceConnectionFactory redisConnectionFactory(CacheConfigurationProperties properties) {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(properties.getRedisHost());
		redisStandaloneConfiguration.setPort(properties.getRedisPort());
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	public RedisTemplate<String, Products> redisTemplate() {
		RedisTemplate<String, Products> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	public RedisCacheConfiguration cacheConfiguration(CacheConfigurationProperties properties) {
		return createCacheConfiguration(properties.getTimeoutDays());
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,
			CacheConfigurationProperties properties) {
		Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
		for (Entry<String, Long> cacheNameAndTimeout : properties.getCacheExpirations().entrySet()) {
			cacheConfigurations.put(cacheNameAndTimeout.getKey(),
					createCacheConfiguration(cacheNameAndTimeout.getValue()));
		}
		return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfiguration(properties))
				.withInitialCacheConfigurations(cacheConfigurations).build();
	}

}
