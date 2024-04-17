package com.laba.OrderService.feign;

import com.laba.OrderService.configuration.GeneralConfiguration;
import com.laba.OrderService.dto.UserInfoResponseDto;
import com.laba.OrderService.exception.UserException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userService")
public interface UserFeignClient {

    @GetMapping("/user/info")
    @CircuitBreaker(name = "getUserInfoCBreaker", fallbackMethod = "getUserInfoServiceFallback")
    UserInfoResponseDto getInfo(@RequestParam Long userId);

    default UserInfoResponseDto getUserInfoServiceFallback(Long userId, Exception e){
        //var olan bir default kullanıcı dönebilir
        // cache sistemi varsa eğer oradan datayı çekebilriz.
        throw new UserException("Giriş işleminde bir sorun oluştu. Anasayfaya yönlendiriliyorsunuz. " + userId);
    }

}
