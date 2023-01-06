package trua_nay_an_gi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import trua_nay_an_gi.service.IAccountService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	 
	  @Autowired
	  PasswordEncoder passwordEncoder;
	  
	  @Autowired
	  private CustomSuccessHandler customSuccessHandler;
	  
	  @Autowired
	  private MyAuthenticationSuccessHandler authenticationSuccessHandler;
	  
	  @Autowired
	  private IAccountService accountService;
	  
	  @Autowired
	  private CustomIdentityAuthenticationProvider customIdentityAuthenticationProvider;
	  
	  
	  @Override
	  @Bean
	  public AuthenticationManager authenticationManagerBean() throws Exception{
	     return super.authenticationManagerBean();
	  }
	  
	  @Override
	  protected void configure(AuthenticationManagerBuilder auth) {
	      auth.authenticationProvider(customIdentityAuthenticationProvider);

	      //We can register as many providers as we may have
	      //auth.authenticationProvider(customProviderTwo);
	      //auth.authenticationProvider(customProviderThree);
	  }
	 
//	  @Override
//	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		  
//		  auth.userDetailsService((UserDetailsService) accountService).passwordEncoder(NoOpPasswordEncoder.getInstance());
//		  
////	    auth.inMemoryAuthentication()
////	    .passwordEncoder(passwordEncoder)
////	    .withUser("user").password(passwordEncoder.encode("123456")).roles("USER")
////	    .and()
////	    .withUser("admin").password(passwordEncoder.encode("123456")).roles("USER", "ADMIN");
//	  }
//	 
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
	 
	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
		  http.authorizeRequests()
		    .antMatchers("/login").permitAll()
		    .antMatchers("/admin/**").hasRole("ADMIN")
		    .antMatchers("/**").hasAnyRole("ADMIN", "USER")
		    .and().formLogin()
		    .loginPage("/login")
		    .usernameParameter("userName")
		    .successHandler(customSuccessHandler)
//		    .defaultSuccessUrl("/admin", true)
		    .and()
		    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		    .and().csrf().disable();

	  }
	  
	

}
