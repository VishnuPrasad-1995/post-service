package com.mavericsystems.postservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "comment-service")
public interface CommentFeign {
    @GetMapping("/comment/posts/{postId}/comments/count")
    Integer getCommentsCount(@PathVariable("postId") String postId);
}
