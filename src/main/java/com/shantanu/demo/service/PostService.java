package com.shantanu.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;

public interface PostService {
	ResponseEntity<ArrayNode> fetchAllPosts(String get_all_posts_api);

	ResponseEntity<ObjectNode> fetchPostById(String getAllPosts, int id) throws JsonProcessingException;
}
