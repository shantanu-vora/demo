package com.shantanu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Min;
import java.util.Map;

@RestController
@Validated
public class TestController {
    @PostMapping("/test")
    public ResponseEntity<String> validateRequestHeader(@RequestHeader("Content-Length") @Min(25) int value) {
        System.out.println("Header content-length = " + value);
        return ResponseEntity.ok("Headers are read successfully ");
    }
    @GetMapping("/weather")
    @RolesAllowed("admin")
    public ResponseEntity<Map<String, Object>> getWeatherForecast() throws JsonProcessingException {
        String url = "http://api.weatherstack.com/current?access_key=f216262609602500c30959e57ef04c81&query=Bangalore&units=s";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(result, new TypeReference<>() {});
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
