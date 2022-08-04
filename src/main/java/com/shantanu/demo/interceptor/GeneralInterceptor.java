package com.shantanu.demo.interceptor;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GeneralInterceptor implements HandlerInterceptor {

	private final RestTemplate restTemplate = new RestTemplate();
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("Inside PreHandle");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String tokenEndpoint = "http://localhost:8080/realms/springboot/protocol/openid-connect/token";

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("username", "shantanu");
		map.add("password","shantanu");
		map.add("grant_type","password");
		map.add("client_id", "springboot-keycloak");
		map.add("client_secret", "JWMydqIqddlHe9oDpfT0KLco2p9DrUH5");
		map.add("scope", "openid");

		HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(map, headers);
		ResponseEntity<ObjectNode> result = restTemplate.postForEntity( tokenEndpoint, tokenRequest , ObjectNode.class );
		System.out.println(result.getBody());
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURL());
		System.out.println(request.getMethod());
		return true;
//		return HandlerInterceptor.super.preHandle(request, response, handler);

	}
}
