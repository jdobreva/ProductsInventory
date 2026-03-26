package com.adesso.products.config;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.adesso.products.dto.ErrorResponseDto;

import jakarta.annotation.Nonnull;


@ControllerAdvice
public class ProductsApiExceptionHandler extends ResponseEntityExceptionHandler {

	
	 @Override
	
	protected @Nonnull ResponseEntity<Object> handleNoResourceFoundException(
				NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		 	ErrorResponseDto bodyOfResponse = ErrorResponseDto.builder()
	        		.error(ex.getMessage())
	        		.status(HttpStatus.NOT_FOUND.getReasonPhrase())
	        		.timestamp(new Date())
	        		.build();
		 	ResponseEntity<Object> response = handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
		 	if (response == null) {
		 		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		 	}
			return response;
	}
	 
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorResponseDto> handleAuthorizationException(
			AuthorizationDeniedException ex) {
	 	return ResponseEntity.status(HttpStatus.FORBIDDEN)
	 			.body(ErrorResponseDto.builder()
        		.error(ex.getMessage())
        		.status(HttpStatus.FORBIDDEN.getReasonPhrase())
        		.timestamp(new Date())
        		.build());
	 			
		}
	 
}

