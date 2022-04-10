package com.mavericsystems.postservice.constant;

public final class PostConstant {

    private PostConstant() {
        // restrict instantiation
    }

    public static final String DELETE_POST = "Post deleted"  ;
    public static final String POST_NOT_FOUND = "Post not found for : ";
    public static final String FEIGN_EXCEPTION = "One of the service among user, comment, like is unavailable";
    public static final String POST_ID_MISMATCH = "Id passed in url and request body does not match";
    public static final String NO_POST_FOUND = "No post available";
}
