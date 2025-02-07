package com.laba.OrderService.resttemplate;

import com.laba.OrderService.dto.UserInfoResponseDto;
import com.laba.OrderService.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    public UserInfoResponseDto getInfo(Long userId){
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<UserInfoResponseDto> httpEntity = new HttpEntity(httpHeaders);

        String url = "http://127.0.0.1:8087/user/info?userId="+userId;
        try{
            ResponseEntity<UserInfoResponseDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserInfoResponseDto.class);
            return responseEntity.getBody();
        }catch (Exception e){
            throw new UserException("Kullanıcnın verisini çekerken bir hata ile karşılaşıldı.");
        }

    }


}
