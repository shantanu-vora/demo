package com.shantanu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Min;
import java.util.Map;

@RestController
@Validated
public class TestController {

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/api/test")
    public ResponseEntity<String> validateRequestHeader(@RequestHeader("Content-Length") @Min(25) int value) {
        return ResponseEntity.ok("Headers are read successfully ");
    }

    @GetMapping("/api/weather")
    @RolesAllowed("admin")
    public ResponseEntity<Map<String, Object>> getWeather(@RequestParam("city") String city) throws JsonProcessingException {
        city = String.join("+", city.split(" "));
        String url = "http://api.weatherstack.com/current?access_key=f216262609602500c30959e57ef04c81&units=s&query="+city;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(result, new TypeReference<>() {});

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
