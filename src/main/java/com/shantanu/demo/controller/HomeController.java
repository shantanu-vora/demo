package com.shantanu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Min;

@RestController
@Validated
public class HomeController {

	@Autowired
	private HomeService homeService;

	@Value("${weatherStackApiUrl}")
	private String weatherStackApi;

	@PostMapping("/api/test")
	public ResponseEntity<String> validateRequestHeader(@RequestHeader("Content-Length") @Min(25) int value) {
		return new ResponseEntity<>("Headers are read successfully", HttpStatus.OK);
	}

	@GetMapping("/api/weather")
	@RolesAllowed({"admin", "user"})
	public ResponseEntity<ObjectNode> getWeather(@RequestParam(value = "city", required = true) String city) throws JsonProcessingException {
		ResponseEntity<ObjectNode> response = homeService.fetchCurrentWeather(weatherStackApi, city);
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}
}
