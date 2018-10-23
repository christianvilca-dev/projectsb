package com.christian.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // Habilita la seguridad web
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userService")
	private UserDetailsService userService;
	
	// Añade el UserDetailsService
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// Cifra el password en la bd
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			// Autorizadas sin login
			.antMatchers("/css/*", "/imgs/*").permitAll()
			// Autorizadas con login
			.anyRequest().authenticated()
			.and()
				// Pagina del login y la URL que lo procesa
				.formLogin().loginPage("/login").loginProcessingUrl("/logincheck")
				.usernameParameter("username").passwordParameter("password")
				// URL despues de ser logeado 
				.defaultSuccessUrl("/loginsuccess").permitAll()
				.and()
				// Pagina del logout y la URL que lo procesa
				.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout")
				.permitAll();
	}
}
