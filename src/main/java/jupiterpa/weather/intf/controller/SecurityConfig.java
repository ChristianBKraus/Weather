package jupiterpa.weather.intf.controller;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import jupiterpa.weather.infrastructure.config.BaseSecurityConfig;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends BaseSecurityConfig {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, Controller.PATH+"/**").hasRole("USER")
			.antMatchers(HttpMethod.PUT, Controller.PATH+"/update").hasRole("ADMIN")
			.anyRequest().permitAll();
	}
}