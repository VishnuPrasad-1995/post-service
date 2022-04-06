package com.mavericsystems.postservice.constant;

public final class PostConstant {

    private PostConstant() {
        // restrict instantiation
    }

    public static final String DELETEPOST = "Post deleted"  ;
    public static final String POSTNOTFOUND = "Post not found for : ";
    public static final String FEIGNEXCEPTON = "One of the service among user, comment, like is unavailable";
    public static final String POSTIDMISMATCH = "Id passes in url and request body does not match";
    public static final String NOPOSTFOUND = "No post available";
}
