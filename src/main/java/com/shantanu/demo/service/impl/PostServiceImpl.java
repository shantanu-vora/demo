package com.shantanu.demo.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.File;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<ArrayNode> fetchAllPosts(String getAllPosts) {
		return restTemplate.getForEntity(getAllPosts, ArrayNode.class);
	}

	@Override
	public ResponseEntity<ObjectNode> fetchPostById(String getAllPosts, int id) {
		return restTemplate.getForEntity(getAllPosts + File.separator + id, ObjectNode.class);
	}
}
