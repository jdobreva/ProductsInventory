package com.adesso.products.config.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {

	private JwtUtils jwtUtils;
	private AuthenticationManager userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		final String strToken = authHeader.substring(7).trim();

		if (!strToken.isBlank() && jwtUtils.isValid(strToken)) {

			JwtAuthenticationToken token = new JwtAuthenticationToken(strToken);
			Authentication authResult = userDetailsService.authenticate(token);
			if (authResult.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authResult);
			}
		}

		filterChain.doFilter(request, response);

	}

}
