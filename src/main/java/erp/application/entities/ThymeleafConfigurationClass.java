package erp.application.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
public class ThymeleafConfigurationClass {
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setCacheable(false);
		templateResolver.setPrefix("classpath:/static/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(templateResolver());
		return templateEngine;
	}
	
	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver thymeleafResolver = new ThymeleafViewResolver();
		thymeleafResolver.setTemplateEngine(templateEngine());
		thymeleafResolver.setOrder(1);
		return thymeleafResolver;
	}

}
