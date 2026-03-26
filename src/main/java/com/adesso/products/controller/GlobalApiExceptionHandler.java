package com.adesso.products.controller;

import java.util.Date;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.adesso.products.dto.ErrorResponseDto;
import com.adesso.products.exceptions.ProductsServiceException;

@RestControllerAdvice
public class GlobalApiExceptionHandler {
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(PSQLException.class)
	protected @ResponseBody ErrorResponseDto handlePSQLException(
			PSQLException ex, WebRequest request) {
	 	return ErrorResponseDto.builder()
        		.error(ex.getMessage())
        		.status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        		.timestamp(new Date())
        		.build();
	}	

	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ProductsServiceException.class)
	public @ResponseBody ErrorResponseDto handleServiceException(
			ProductsServiceException ex, WebRequest request) {
	 	return ErrorResponseDto.builder()
        		.error(ex.getMessage())
        		.status(HttpStatus.BAD_REQUEST.getReasonPhrase())
        		.timestamp(new Date())
        		.build();
		}
	
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AuthorizationDeniedException.class)
	public @ResponseBody ErrorResponseDto handleAuthorizationException(
			AuthorizationDeniedException ex, WebRequest request) {
	 	return ErrorResponseDto.builder()
        		.error(ex.getMessage())
        		.status(HttpStatus.FORBIDDEN.getReasonPhrase())
        		.timestamp(new Date())
        		.build();
		}

}
