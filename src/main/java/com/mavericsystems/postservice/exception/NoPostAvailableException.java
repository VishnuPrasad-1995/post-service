package com.mavericsystems.postservice.exception;

public class NoPostAvailableException extends RuntimeException{
    public NoPostAvailableException(String s){
        super(s);
    }
}
