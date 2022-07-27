package com.shantanu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Min;

@RestController
@Validated
public class HomeController {

//    @Autowired
//    ObjectMapper objectMapper;

    @Autowired
    private HomeService homeService;

    @Value("${weatherStackApiUrl}")
    private String weatherStackApi;


    @PostMapping("/api/test")
    public ResponseEntity<String> validateRequestHeader(@RequestHeader("Content-Length") @Min(25) int value) {
        return ResponseEntity.ok("Headers are read successfully ");
    }

    @GetMapping("/api/weather")
    @RolesAllowed("admin")
    public ResponseEntity<ObjectNode> getWeather(@RequestParam("city") String city) throws JsonProcessingException {
//        city = String.join("+", city.split(" "));
//        String url = "http://api.weatherstack.com/current?access_key=f216262609602500c30959e57ef04c81&units=s&query="+city;
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(url, String.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> map = objectMapper.readValue(result, new TypeReference<>() {});

//        return ResponseEntity.status(HttpStatus.OK).body(map);
        ResponseEntity<ObjectNode> response = homeService.fetchCurrentWeather(weatherStackApi, city);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
