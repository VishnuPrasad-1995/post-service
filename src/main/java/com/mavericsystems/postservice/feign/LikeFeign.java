package com.mavericsystems.postservice.feign;

import com.mavericsystems.postservice.configuration.CustomRetryClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "like-service", configuration = CustomRetryClientConfig.class,fallbackFactory = HystrixFallBackLikeFactory.class)
public interface LikeFeign {
    @GetMapping("/postsOrComments/{postOrCommentId}/likes/count")
    Integer getLikesCount(@PathVariable("postOrCommentId") String postOrCommentId);
}
