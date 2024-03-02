package com.swiggy.order.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String s) {
        super(s);
    }
}
