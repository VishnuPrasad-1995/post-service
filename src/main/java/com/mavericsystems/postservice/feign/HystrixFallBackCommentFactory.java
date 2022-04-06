package com.mavericsystems.postservice.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class HystrixFallBackCommentFactory implements FallbackFactory<CommentFeign> {
    private static Logger logger = LoggerFactory.getLogger(HystrixFallBackCommentFactory.class);
    @Override
    public CommentFeign create(Throwable cause) {
            logger.info("fallback reason was: {}" + cause.getMessage());
            return null;
    }


}
