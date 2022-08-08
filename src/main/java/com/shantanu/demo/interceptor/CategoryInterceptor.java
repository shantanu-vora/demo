package com.shantanu.demo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class CategoryInterceptor implements HandlerInterceptor {
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		boolean isValidPathVariable = Pattern.matches("^CAT_\\d{5}$", pathVariables.get("id"));

		if(isValidPathVariable) {
			return true;
		} else {
			response.sendError(400, "Invalid Path Variable");
			return false;
		}
	}
}
