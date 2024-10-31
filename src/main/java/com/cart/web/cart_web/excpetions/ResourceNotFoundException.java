package com.cart.web.cart_web.excpetions;

public class ResourceNotFoundException extends RuntimeException{
    public  ResourceNotFoundException(String message) {
        super(message);
    }
}
