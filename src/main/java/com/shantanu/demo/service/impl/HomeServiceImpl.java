package com.shantanu.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public ResponseEntity<ObjectNode> fetchCurrentWeather(String weatherStackApi, String city) {
		RestTemplate restTemplate = new RestTemplate();
		city = String.join("+", city.split(" "));
		return restTemplate.getForEntity(weatherStackApi + city, ObjectNode.class);
	}
}
