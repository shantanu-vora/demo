package com.shantanu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shantanu.demo.entity.Category;
import com.shantanu.demo.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@DisplayName("Junit test for getAllCategories method")
	@Test
	@WithMockUser(roles = {"user","admin"})
	public void givenListOfCategories_whenGetAllCategories_thenReturnCategoryList() throws Exception {
		Category category1 = new Category("CAT_00001", "Category1");
		Category category2 = new Category("CAT_00002", "Category2");
		List<Category> categoryList = new ArrayList<>(List.of(category1, category2));
		when(categoryService.getAllCategories()).thenReturn(categoryList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/categories");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expectedJson = this.mapToJson(categoryList);
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}


	@DisplayName("Junit test for getCategoryById method")
	@Test
	@WithMockUser(roles = {"user", "admin"})
	void givenCategoryId_whenGetCategoryById_thenReturnCategoryObject() throws Exception {
		String categoryId = "CAT_00001";
		Category category = new Category("CAT_00001", "Category1");

		when(categoryService.getCategoryById(category.getId())).thenReturn(category);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/categories/{id}", categoryId);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expectedJson = this.mapToJson(category);
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

}