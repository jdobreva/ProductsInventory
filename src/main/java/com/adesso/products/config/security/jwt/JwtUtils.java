package com.adesso.products.config.security.jwt;

import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.adesso.products.dto.JwtTokenResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtils {
	private static final long TOKEN_DURABILITY = 1000 * 60 * 5l; // 5 MIN
	private static final long REFRESH_DURABILITY = 1000 * 60 * 60l; // 30 MIN

	// openssl rand -base64 32
	@Value("${products.hmacShaKey}")
	private String hmacShaKey;
	private Key key;

	@PostConstruct
	public void initKey() {
		key = Keys.hmacShaKeyFor(hmacShaKey.getBytes());
	}

	public JwtTokenResponse generateTokenResponse(String username) {
		Date date = new Date();
		return JwtTokenResponse.builder().token(createToken(username, date, date.getTime() + TOKEN_DURABILITY))
				.refreshToken(createToken(username, date, date.getTime() + REFRESH_DURABILITY))
				.time(LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault())).build();
	}

	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

	/**
	 * Returns true if there is a subject and the token is valid
	 * 
	 * @param token
	 * @return
	 */
	public boolean isValid(String token) {
		try {
			Claims claims = getClaims(token);
			String subject = claims.getSubject();
			return ((subject != null) && !subject.isBlank() && (claims.getExpiration().compareTo(new Date()) > 0));
		} catch (JwtException e) {
			return false;
		}
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public String validateAndExtractUsername(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
		} catch (JwtException e) {
			return null; // Invalid or expired JWT
		}
	}

	public JwtTokenResponse recreateNewToken(String refreshToken) {
		try {
			String username = extractUsername(refreshToken);
			return generateTokenResponse(username);
		} catch (JwtException e) {
			return null; // Invalid or expired JWT
		}
	}

	private String createToken(final String username, final Date date, long timestamp) {
		return Jwts.builder().setSubject(username).setIssuedAt(date).setExpiration(new Date(timestamp)) // 5 min
				.signWith(key).compact();
	}

}
