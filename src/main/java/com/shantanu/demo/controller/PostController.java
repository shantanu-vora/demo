package com.shantanu.demo.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/posts")
@Validated
public class PostController {

//    private static final String GET_ALL_POSTS_API = "https://blogzen.herokuapp.com/api/posts";
    @Value("${getAllPostsApiUrl}")
    private String getAllPosts;

    @Autowired
    private PostService postService;

    @GetMapping
    @RolesAllowed("admin")
    public ResponseEntity<ArrayNode> getAllPosts() {
//        RestTemplate restTemplate = new RestTemplate();
//        List<?> result = restTemplate.getForObject(GET_ALL_POSTS_API, ArrayList.class);
//        return ResponseEntity.status(HttpStatus.OK).body(result);
        ResponseEntity<ArrayNode> response = postService.fetchAllPosts(getAllPosts);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

    }

    @GetMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<ObjectNode> getPostById(@PathVariable("id") @Pattern(regexp = "^\\d+$") String id) {
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(getAllPosts + File.separator + id, String.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> map = objectMapper.readValue(result, new TypeReference<>() {
//        });
//        return ResponseEntity.status(HttpStatus.OK).body(map);
        ResponseEntity<ObjectNode> response = postService.fetchPostById(getAllPosts, Integer.parseInt(id));
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
//        return null;
    }
}
