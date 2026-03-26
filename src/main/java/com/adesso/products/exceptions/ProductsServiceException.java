package com.adesso.products.exceptions;

public class ProductsServiceException extends RuntimeException {

  
    public ProductsServiceException(final String message){
        super(message);
    }
}
