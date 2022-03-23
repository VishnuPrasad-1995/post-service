package com.mavericsystems.postservice.feign;

import com.mavericsystems.postservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeign {
    @GetMapping("/users/{userId}")
    UserDto getUserById(@PathVariable("userId") String userId);
}
