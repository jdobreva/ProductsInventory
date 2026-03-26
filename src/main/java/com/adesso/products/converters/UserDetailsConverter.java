package com.adesso.products.converters;

import org.springframework.core.convert.converter.Converter;

import com.adesso.products.dto.UserDetailsRequest;
import com.adesso.products.entity.UserAuthEntity;
import com.adesso.products.exceptions.ProductsServiceException;

public class UserDetailsConverter implements Converter<UserDetailsRequest, UserAuthEntity> {
	 
		
	public UserAuthEntity convert(UserDetailsRequest request) {
		UserAuthEntity userEntity = new UserAuthEntity();
		userEntity.setUsername(sanitizeStringValue(request.getUsername()));
		userEntity.setPassword(request.getPassword());
		userEntity.setRole(sanitizeStringValue(request.getRole()));
		return userEntity;
	}
	
	private String sanitizeStringValue(final String username) {
		if ((username == null) || username.isBlank()) {
			throw new ProductsServiceException("Incorrect data!");
		}
		String sanitized = username.replaceAll("[\s()=\"'%]{1,5}", "");
		if ((sanitized == null) || sanitized.isBlank()) {
			throw new ProductsServiceException("Incorrect data!");
		}
		return sanitized;
	}

}
