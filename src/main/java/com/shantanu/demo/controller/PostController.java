package com.shantanu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.RolesAllowed;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static final String GET_ALL_POSTS_API = "https://blogzen.herokuapp.com/api/posts";

    @GetMapping
    @RolesAllowed("admin")
    public ResponseEntity<List<?>> getAllPosts() {
        RestTemplate restTemplate = new RestTemplate();
        List<?> result = restTemplate.getForObject(GET_ALL_POSTS_API, ArrayList.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<Map<String, Object>> getPostById(@PathVariable("id") int id) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(GET_ALL_POSTS_API + File.separator + id, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(result, new TypeReference<>() {
        });
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
