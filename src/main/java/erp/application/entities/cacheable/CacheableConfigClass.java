package erp.application.entities.cacheable;

import java.util.Arrays;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@ComponentScan(value="erp.application.service")
public class CacheableConfigClass {
	
	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager1 = new SimpleCacheManager();
		Cache employeeCache = new ConcurrentMapCache("employeeService", false);
		Cache calculateTaxes = new ConcurrentMapCache("calculateTaxes", false);
		Cache calculateCasTax = new ConcurrentMapCache("calculateCasTax", false);
		Cache incomeTax = new ConcurrentMapCache("incomeTax", false);
		Cache findAll = new ConcurrentMapCache("findAll", false);
		cacheManager1.setCaches(Arrays.asList(employeeCache, calculateTaxes, calculateCasTax, incomeTax, incomeTax, findAll));
		return cacheManager1;
		
	}

}
