package com.shantanu.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class HomeServiceTest {

	@Autowired
	private HomeService homeService;

	@Value("${weatherStackApiUrl}")
	private String weatherStackApi;

	@Test
	void fetchCurrentWeather() throws Exception {
		assertNotNull(homeService.fetchCurrentWeather(weatherStackApi, "Akola"));
	}
}