package erp.application.entities.cacheable;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cache")
public class CacheConfigurationProperties {
	
	private long timeoutDays = 60;
    private int redisPort = 6379;
    private String redisHost = "localhost";
    private Map<String, Long> cacheExpirations = new HashMap<>();
	
    public CacheConfigurationProperties() {};
    
    public long getTimeoutDays() {
		return timeoutDays;
	}
	public void setTimeoutDays(long timeoutDays) {
		this.timeoutDays = timeoutDays;
	}
	public int getRedisPort() {
		return redisPort;
	}
	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}
	public String getRedisHost() {
		return redisHost;
	}
	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}
	public Map<String, Long> getCacheExpirations() {
		return cacheExpirations;
	}
	public void setCacheExpirations(Map<String, Long> cacheExpirations) {
		this.cacheExpirations = cacheExpirations;
	}
	
	@Override
	public String toString() {
		return "CacheConfigurationProperties [timeoutDays=" + timeoutDays + ", redisPort=" + redisPort + ", redisHost="
				+ redisHost + ", cacheExpirations=" + cacheExpirations + "]";
	}
    
    

}
