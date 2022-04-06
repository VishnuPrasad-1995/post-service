package com.mavericsystems.postservice.exception;


public class CustomFeignException extends RuntimeException{
    public CustomFeignException(String s) {
        super(s);
    }
}
