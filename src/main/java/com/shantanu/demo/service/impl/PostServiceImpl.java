package com.shantanu.demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Objects;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	ObjectMapper objectMapper;
	@Override
	public ResponseEntity<ArrayNode> fetchAllPosts(String getAllPosts) {
		RestTemplate restTemplate = new RestTemplate();
//		return restTemplate.getForObject(getAllPosts, ArrayList.class);
		ResponseEntity<ArrayNode> response = restTemplate.getForEntity(getAllPosts, ArrayNode.class);
		System.out.println(response.getBody());
		return response;
	}

	@Override
	public ResponseEntity<ObjectNode> fetchPostById(String getAllPosts, int id) throws JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
//		String result = restTemplate.getForObject(getAllPosts + File.separator + id, String.class);
//		return objectMapper.readValue(result, new TypeReference<>() {});
		ResponseEntity<ObjectNode> response = restTemplate.getForEntity(getAllPosts + File.separator + id, ObjectNode.class);
		System.out.println(Objects.requireNonNull(response.getBody()).path("tags"));
		return response;

	}
}