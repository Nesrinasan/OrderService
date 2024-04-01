package com.laba.OrderService.feign;

import com.laba.OrderService.configuration.GeneralConfiguration;
import com.laba.OrderService.dto.UserInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userService", url = "http://127.0.0.1:8087")
public interface UserFeignClient {

    @GetMapping("/user/info")
    UserInfoResponseDto getInfo(@RequestParam Long userId);

}
