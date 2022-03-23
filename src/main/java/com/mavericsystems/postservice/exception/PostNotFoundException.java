package com.mavericsystems.postservice.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String s) {
        super(s);
    }
}
