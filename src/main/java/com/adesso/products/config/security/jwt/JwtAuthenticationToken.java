package com.adesso.products.config.security.jwt;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -6332844996017712551L;
	private String token;
	
	public JwtAuthenticationToken(final String token) {
		super(List.of());
		this.token = token;
	}
	
	@Override
	public Object getCredentials() {
		return getToken();
	}

	@Override
	public Object getPrincipal() {
		return getToken();
	}

	public String getToken() {
		return token;
	}

}
