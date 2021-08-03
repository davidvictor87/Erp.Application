package erp.application.web.security;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import erp.application.entities.LOG;
import erp.application.login.repository.UserRepository;
import erp.application.service.LoginService;
import org.springframework.context.annotation.Bean;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	/* This class contains commented code that will be used for further upgrades,
	 * temporarely SpringBoot default login page will be used
	 */
	
 @Autowired
 private LoginService loginService;
 @Autowired
 private SecretKey secretKey;
 @Autowired
 private JwtConfig jwtConfig;
  
 @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

      auth.userDetailsService(loginService)
      .passwordEncoder(getPasswordEncoder());
  }
	 
 @Override
 public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("**/login/**");
 }



 @Override
 @Order(SecurityProperties.BASIC_AUTH_ORDER)
 protected void configure(HttpSecurity httpSecurity) throws Exception {
	 httpSecurity.csrf().disable();
     httpSecurity.addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), 
    		 JwtUsernameAndPasswordAuthenticationFilter.class).authorizeRequests().antMatchers("/", "/index", "/css/*", "/js/*")
     .permitAll();
     httpSecurity.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/register").permitAll()
     .anyRequest().authenticated().and().formLogin().loginPage("/login.html").permitAll(true)
     .defaultSuccessUrl("/welcome.html", true).usernameParameter("username").passwordParameter("password").failureForwardUrl("/login.html").permitAll()
     .and().logout().logoutSuccessUrl("/login.html").permitAll(true).and().authorizeRequests()
     .antMatchers("/PannelUser").hasAnyRole(RolesAndRights.ADMIN.name()).anyRequest().authenticated().and().rememberMe().rememberMeParameter("remember-me")
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(210)).key(rememberKey()).and().logout()
				.logoutUrl("/login.html").logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
	 .clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JESSIONID","remember-me").logoutSuccessUrl("/login.html"); 
 }

  private PasswordEncoder getPasswordEncoder() {
      return new PasswordEncoder() {
          @Override
          public String encode(CharSequence charSequence) {
              return charSequence.toString();
          }

          @Override
          public boolean matches(CharSequence charSequence, String s) {
              return true;
          }
      };
  }
  
  @Bean
  public WebMvcConfigurer myWebMvcConfigurer() {
     return new WebMvcConfigurer() {

         @Override
         public void addViewControllers(ViewControllerRegistry registry) {
            ViewControllerRegistration r = registry.addViewController("/login");
            r.setViewName("login");
          }
       };
  }

  @Bean
  public ViewResolver viewResolver() {
  	InternalResourceViewResolver irvr = new InternalResourceViewResolver();
  	irvr.setPrefix("/static/");
  	irvr.setSuffix(".html");
  	return irvr;
  	
  }
  
  private String rememberKey() {
	    int leftLimit = 97; 
		int rightLimit = 122; 
		int targetStringLength = 50;
		Random random = new Random();
	    String generatedString = random.ints(leftLimit, rightLimit + 1)
		.limit(targetStringLength)
		.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		.toString();
	    LOG.appLogger().info("Remember key is: " + generatedString);
	    return generatedString;
  }

}

