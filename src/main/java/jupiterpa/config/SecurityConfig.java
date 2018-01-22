package jupiterpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import jupiterpa.controller.Controller;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	ApplicationConfig config;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	throws Exception {
		auth
			.inMemoryAuthentication()
			.withUser("user").password(config.getUserPassword()).roles("USER").and()
			.withUser("admin").password(config.getAdminPassword()).roles("USER", "ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()//.loginPage("/login")
			.and()
			.logout()//.logoutSuccessUrl("/")
			.and()
//			.rememberMe().tokenValiditySeconds(2419200).key("Tagesplaner").and()
			.httpBasic().realmName(config.getName()).and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, Controller.PATH+"/**").hasRole("USER")
			.antMatchers(HttpMethod.PUT, Controller.PATH+"/update").hasRole("ADMIN")
			.anyRequest().permitAll();
	}
}