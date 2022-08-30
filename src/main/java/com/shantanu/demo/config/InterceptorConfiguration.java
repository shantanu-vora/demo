//package com.shantanu.demo.config;
//
//import com.shantanu.demo.interceptor.CategoryInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class InterceptorConfiguration implements WebMvcConfigurer {
//
//	@Autowired
//	private CategoryInterceptor categoryInterceptor;
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(categoryInterceptor).addPathPatterns("/api/categories/*");
//	}
//}
