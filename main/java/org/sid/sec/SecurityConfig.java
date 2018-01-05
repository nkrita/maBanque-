package org.sid.sec;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

		  auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery(
				"select username,password,enabled from users where username=?")
			.authoritiesByUsernameQuery(
				"select username,role from users where username=?");
		}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// pour definir les strategies de securités,, les regles
		//on demande a Spring qu'on a besoin de passer par une authentification basée par un formulaire
		http.formLogin().loginPage("/login");
		//securiser les ressources de l'appli
		http.authorizeRequests().antMatchers("/operations","/consulterCompte").hasAnyRole("ADMIN","USER");
		http.authorizeRequests().antMatchers("/saveOperation").hasRole("ADMIN");
		
		http.exceptionHandling().accessDeniedPage("/403");
	}
	
}
 