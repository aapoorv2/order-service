package com.swiggy.order.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String s) {
        super(s);
    }
}
