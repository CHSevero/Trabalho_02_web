package com.ufc.br.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ufc.br.security.UserDetailsServiceImplementacao;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserDetailsServiceImplementacao userDetaisServiceImple;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetaisServiceImple).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().antMatchers("/css/**","/js/**", "img/**","/images/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable().authorizeRequests()
		
		
		.antMatchers("/home").permitAll()
		.antMatchers("/prato/listar").permitAll()
		.antMatchers("/prato/salvar").hasRole("GERENTE")
		.antMatchers("/cliente/cadastrar").permitAll()
		.antMatchers("/cliente/salvar").permitAll()
		
		
		
		.antMatchers("/prato/cadastrar").hasRole("GERENTE")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/logar").defaultSuccessUrl("/home").permitAll()
		
		.and()
		.logout()
		.logoutSuccessUrl("/home?logout")
		.permitAll();
		
	}
	
}
