package com.acme.yupanaapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private SellerDetailsImpl sellerDetailsImpl;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(sellerDetailsImpl).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/login/**","/forgot_password/**","/reset_password_form/**","/assets/styles/**","/assets/script/**").permitAll().anyRequest().authenticated() // pueden ir todos los estaticos publicos donde no se requiera ningun acesso
		.and()
			.formLogin().loginPage("/login").defaultSuccessUrl("/mystore/clients",true).failureUrl("/login?error=true")
			.loginProcessingUrl("/login-post").permitAll()
		.and()
			.logout().logoutUrl("/logout").logoutSuccessUrl("/login"); 
	}
	
	
}
