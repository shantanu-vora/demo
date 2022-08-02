package com.shantanu.demo.controller;

import com.shantanu.demo.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@WebMvcTest(PostController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private PostService postService;

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@DisplayName("JUnit test for getAllPosts method")
	@Test
	@WithMockUser(roles = {"user", "admin"})
	public void testGetAllPosts() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/posts");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@DisplayName("JUnit test for getPostById method")
	@Test
	@WithMockUser(roles = "admin")
	public void testGetPostById() throws Exception {
		int postId = 26;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/posts/{id}", postId);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
}
