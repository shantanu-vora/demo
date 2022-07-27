package com.shantanu.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public ResponseEntity<ObjectNode> fetchCurrentWeather(String weatherStackApi, String city) {
		RestTemplate restTemplate = new RestTemplate();
		//		ResponseEntity<ObjectNode> response = restTemplate.getForEntity(weatherStackApi + String.join("+", city.split(" ")), ObjectNode.class);
//		Map<String, Object> map = objectMapper.readValue(result, new TypeReference<>() {});
//		Map<String, Object> map = objectMapper.readValue(jsonObject, new TypeReference<>() {});
//		Map<String, Map<String, String>> map1 = map.get("location");
//		System.out.println(map.get("location"));
//		return map;
		city = String.join("+", city.split(" "));
		ResponseEntity<ObjectNode> response = restTemplate.getForEntity(weatherStackApi + city, ObjectNode.class);
		System.out.println(Objects.requireNonNull(response.getBody()).path("location").path("name"));
		return response;
	}
}
