package shopbaeFood.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomLoginSuccessHandler customLoginSuccessHandler;

	@Autowired
	private CustomIdentityAuthenticationProvider customIdentityAuthenticationProvider;
	
	@Autowired
	private CustomLoginFailureHandler customLoginFailureHandler;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.authenticationProvider(customIdentityAuthenticationProvider);

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/login", "/register/**", "/product", "/user/**", "/home/**", "/static/**",
						"/image/**","/chat/**")
				.permitAll().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/merchant/**").hasRole("MERCHANT")
				.antMatchers("/**").hasAnyRole("ADMIN").and().formLogin().loginPage("/login")
				.usernameParameter("userName")
				.successHandler(customLoginSuccessHandler)
				.failureHandler(customLoginFailureHandler)
				.and().logout()
				.logoutSuccessUrl("/home")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).clearAuthentication(true)
				.deleteCookies("JSESSIONID").invalidateHttpSession(true).and().csrf().disable();

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
				.invalidSessionUrl("/login?mess=timeout"));

	}
	
	

}
