package com.adesso.products.service;

import java.util.Objects;

import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adesso.products.dto.UserDetailsRequest;
import com.adesso.products.entity.UserAuthEntity;
import com.adesso.products.exceptions.ProductsServiceException;
import com.adesso.products.repository.UserAuthEntityRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserAuthEntityService implements UserDetailsService {

	private UserAuthEntityRepository userAuthEntityRepository;
	private ConversionService conversionService;
	
	public UserDetails save(UserDetailsRequest userentity) {
		if (Objects.isNull(userentity)) {
			throw new ProductsServiceException("Incorrect user payload!");
		}
		return userAuthEntityRepository.save(conversionService.convert(userentity, UserAuthEntity.class));
		
	}
	
	@Override
	public UserAuthEntity loadUserByUsername(String username) {
		return userAuthEntityRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
	}
	

}
