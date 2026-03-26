package com.adesso.products.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adesso.products.config.security.jwt.JwtAuthenticationToken;
import com.adesso.products.config.security.jwt.JwtUtils;
import com.adesso.products.dto.JwtTokenResponse;
import com.adesso.products.dto.LoginRequest;
import com.adesso.products.dto.UserDetailsRequest;
import com.adesso.products.exceptions.ProductsServiceException;
import com.adesso.products.service.UserAuthEntityService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BASIC_AUTH = "Basic ";
	private static final String BEARER_TOKEN = "Bearer ";
	private static final String REFRESH_TOKEN_PATH = "/refresh-token";
	private static final String REFRESH_TOKEN_COOKIE = "refreshToken";

    private final JwtUtils jwtService;
	private final UserAuthEntityService authEntityService;
	private final AuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;
	
	
	
	public UserAuthController(@Autowired JwtUtils jwtService, 
			@Autowired UserAuthEntityService authEntityService,
			@Autowired AuthenticationManager authenticationManager,
			@Autowired PasswordEncoder passwordEncoder) {
		this.jwtService = jwtService;
		this.authEntityService = authEntityService;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
	}


	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserDetailsRequest userDetails) {
		userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
		authEntityService.save(userDetails);
		return ResponseEntity.ok("The user is successfully registered.");
	}
	
	@PostMapping("/token")
    public JwtTokenResponse login(@RequestHeader("Authorization") String encCredentias, 
    		HttpServletResponse response) {
    	LoginRequest aRequest = decodeEncCredentials(encCredentias);
    
    	Authentication authResult = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        aRequest.getUsername(),
                        aRequest.getPassword()
                )
        );
    	if (authResult.isAuthenticated()) {
    		JwtTokenResponse jwtResponse = jwtService.generateTokenResponse(aRequest.getUsername());
    		setupResponseCookie(response, jwtResponse.getRefreshToken());
    		return jwtResponse;
    	}
    	
    	throw new ProductsServiceException("Bad credentials!");
    }
	
	@PostMapping(REFRESH_TOKEN_PATH)
    public JwtTokenResponse loginWithRefresh(HttpServletRequest request, 
    		HttpServletResponse response) {
		
    	String refreshToken = getRefreshToken(request);
    	
    	if (refreshToken == null) {
    	    throw new AuthorizationDeniedException("Bad refresh token!");
    	}
    	
    	JwtAuthenticationToken token = new JwtAuthenticationToken(refreshToken);
    	
		Authentication authResult = authenticationManager.authenticate(token);
		if (authResult.isAuthenticated()) {
    		
    		JwtTokenResponse tokenResponse = jwtService.recreateNewToken(refreshToken);
    		setupResponseCookie(response, tokenResponse.getRefreshToken());
    		return tokenResponse;
    	}
    	
    	throw new ProductsServiceException("Bad credentials!");
    }
	
	private String getRefreshToken(HttpServletRequest request) {
		
		final String cookie = extractJwtFromRequest(request);
		if ((cookie !=  null) && !cookie.isBlank()) {
			return cookie;
		}
		String authorization = request.getHeader(AUTHORIZATION_HEADER);
		if ((authorization == null) || 
				(authorization.trim().length() < BEARER_TOKEN.length()) || 
				(!authorization.startsWith(BEARER_TOKEN))) {
			return null;
		}
		return authorization.substring(BEARER_TOKEN.length());
	}
	
	private String extractJwtFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        String refreshToken = null;
        for (Cookie cookie : cookies) {
            if (REFRESH_TOKEN_COOKIE.equals(cookie.getName())) {
                refreshToken = cookie.getValue();
            }
        }
        return refreshToken;
    }
	
	
	private void setupResponseCookie(HttpServletResponse response, String refreshToken) {
		 Cookie refreshCookie = new Cookie(REFRESH_TOKEN_COOKIE, refreshToken);
         refreshCookie.setHttpOnly(true); 
         refreshCookie.setSecure(true); // sent only over HTTPS
         refreshCookie.setPath(REFRESH_TOKEN_PATH); // Cookie available only for refresh endpoint
         refreshCookie.setMaxAge(60 * 60); // 1 days expire
         response.addCookie(refreshCookie);
		
	}
    
    private LoginRequest decodeEncCredentials(final String encCredentials) {
    	
    	if (!encCredentials.startsWith(BASIC_AUTH)) {
    		throw new ProductsServiceException("Incorrect identification header");
    	}
    	
    	String decoded = new String(Base64.getDecoder().decode(encCredentials.substring(BASIC_AUTH.length())));
    	final int index = decoded.indexOf(":");
    	if (index == -1) {
    		return new LoginRequest(decoded, null);
    	}
    	return new LoginRequest(decoded.substring(0, index), decoded.substring(index+1));
    }
}
