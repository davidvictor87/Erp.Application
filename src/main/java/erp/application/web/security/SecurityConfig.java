package erp.application.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//import erp.application.login.model.CustomUsersDetails;
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
 protected void configure(HttpSecurity httpSecurity) throws Exception {
     httpSecurity.csrf().disable();
     httpSecurity.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/register").permitAll()
     .anyRequest().authenticated().and().formLogin().loginPage("/login.html")
     .defaultSuccessUrl("/welcome.html", true).failureForwardUrl("/login.html").permitAll()
     .and().logout().logoutSuccessUrl("/logout").permitAll(true).and().authorizeRequests()
     .antMatchers("/PannelUser").hasAnyRole("ADMIN").anyRequest().authenticated();
     /*httpSecurity.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/register").permitAll()
     .anyRequest().authenticated().and().formLogin().loginPage("/login.html")
     .defaultSuccessUrl("/welcome.html").failureForwardUrl("/login.html").and().authorizeRequests()
     .antMatchers("/PannelUser").hasAnyRole("ADMIN", "MANAGER", "USER");*/
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
  	irvr.setPrefix("/templates/");
  	irvr.setSuffix(".html");
  	return irvr;
  	
  }
  
  

	/*@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.inMemoryAuthentication().withUser("admin").password("admin").authorities("ROLE_USER").and()
				.withUser("javainuse").password("javainuse").authorities("ROLE_ADMIN");
	}*/



}

