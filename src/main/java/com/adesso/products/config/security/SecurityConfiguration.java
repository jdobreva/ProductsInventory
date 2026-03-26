package com.adesso.products.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.adesso.products.config.security.jwt.JwtAuthenticationProvider;
import com.adesso.products.config.security.jwt.JwtUtils;
import com.adesso.products.config.security.jwt.JwtValidationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	private final JwtUtils jwtUtils;
	private final UserDetailsService userDetailsService;

	public SecurityConfiguration(@Autowired JwtUtils jwtUtils,
			UserDetailsService udservice) {
		this.jwtUtils = jwtUtils;
		this.userDetailsService = udservice;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		final JwtValidationFilter jwtValidationFilter = new JwtValidationFilter(jwtUtils, authenticationManager);
	        return http
	                .csrf(csrf -> csrf.disable())
	                .authorizeHttpRequests(auth -> auth
	                        .requestMatchers("/auth/**").permitAll()
	                        .anyRequest()
	                        .authenticated()
	                )
	                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
	                .build();
	 }

	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
	        return new ProviderManager(Arrays.asList(daoAuthenticationProvider(), jwtAuthenticationProvider()));
	 }
	 
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	 }
	 
	 
	 public DaoAuthenticationProvider daoAuthenticationProvider() {
		 DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider(userDetailsService);
		 daoProvider.setPasswordEncoder(passwordEncoder());
		 return daoProvider;
	 }
	 
	 public JwtAuthenticationProvider jwtAuthenticationProvider() {
	        return new JwtAuthenticationProvider(jwtUtils, userDetailsService);
     }

}
