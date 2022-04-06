package com.mavericsystems.postservice.constant;

public final class PostConstant {

    private PostConstant() {
        // restrict instantiation
    }

    public static final String DELETEPOST = "Post deleted"  ;
    public static final String POSTNOTFOUND = "Post not found for : ";
    public static final String FEIGNEXCEPTON = "Feign call connection issue";
    public static final String POSTIDMISMATCH = "Id passes in url and request body does not match";
}
