package com.mavericsystems.postservice.exception;

public class PostIdMismatchException extends RuntimeException{
    public PostIdMismatchException(String s){
        super(s);
    }
}
