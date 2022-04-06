package com.mavericsystems.postservice.feign;

import com.mavericsystems.postservice.configuration.CustomRetryClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "comment-service", configuration = CustomRetryClientConfig.class,fallbackFactory = HystrixFallBackCommentFactory.class)
public interface CommentFeign {
    @GetMapping("/posts/{postId}/comments/count")
    Integer getCommentsCount(@PathVariable("postId") String postId);
}
