package com.shantanu.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;

public interface HomeService {
	ResponseEntity<ObjectNode> fetchCurrentWeather(String weatherStackApi, String city) throws JsonProcessingException;
}
