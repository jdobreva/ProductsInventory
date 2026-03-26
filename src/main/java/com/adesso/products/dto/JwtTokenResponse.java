package com.adesso.products.dto;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter(PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class JwtTokenResponse {
	
	private String token;
	private String refreshToken;
	private LocalDate time;
}
