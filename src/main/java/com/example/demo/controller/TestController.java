package com.example.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class TestController {
    private static final String GET_ALL_POSTS_API = "https://blogzen.herokuapp.com/api/posts";

    @PostMapping("/test")
    public ResponseEntity<String> validateRequestHeader(@RequestHeader("Content-Length") @Min(25) int value) {
        System.out.println("Header content-length = " + value);
        return ResponseEntity.ok("Headers are read successfully ");
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable("id") int id) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(GET_ALL_POSTS_API + File.separator + id, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(result, new TypeReference<>() {
        });
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        List<?> result = restTemplate.getForObject(GET_ALL_POSTS_API, ArrayList.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
