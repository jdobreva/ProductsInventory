package com.adesso.products.config.security.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class JwtAuthenticationProvider implements AuthenticationProvider {
	
	private JwtUtils jwtUtil;
	private UserDetailsService userDetailsService;
	

	public JwtAuthenticationProvider(JwtUtils jwtUtil, UserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = ((JwtAuthenticationToken) authentication).getToken();
		if (jwtUtil.isValid(token)) {
			UserDetails ud = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
			return new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
		}
		throw new BadCredentialsException("Invalid JWT Token");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
