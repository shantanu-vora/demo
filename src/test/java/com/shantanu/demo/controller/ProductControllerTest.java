package com.shantanu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.entity.Product;
import com.shantanu.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

//	@MockBean
//	private EmployeeService employeeService;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@DisplayName("Junit test for getAllProducts method")
	@Test
	@WithMockUser(roles = {"user","admin"})
	public void givenListOfProducts_whenGetAllProducts_thenReturnProductList() throws Exception {
		Product product1 = new Product("PRO_00001", "Mac Mini", "APP102", 30000.0, 3);
		Product product2 = new Product("PRO_00002", "Mac Pro", "APP103", 20000.0, 1);
		List<Product> productList = new ArrayList<>(List.of(product1, product2));
		when(productService.getAllProducts()).thenReturn(productList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(productList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@DisplayName("Junit test for getProductById")
	@Test
	@WithMockUser(roles = {"user", "admin"})
	public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception {
		String productId = "PRO_00001";
		Product product = new Product("PRO_00001", "Mac Pro", "APP103", 10000.0, 3);

		when(productService.getProductById(product.getId())).thenReturn(product);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/{id}", productId);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(product);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@DisplayName("Junit test for addProduct method")
	@Test
	@WithMockUser(roles = "admin")
	public void givenProductObject_whenAddProduct_thenReturnSavedProduct() throws Exception {
		Product product = new Product("PRO_00001", "Mac Pro", "APP103", 10000.0, 3);
		String inputInJson = this.mapToJson(product);
		ObjectNode jsonObject = objectMapper.convertValue(product, ObjectNode.class);
		when(productService.saveProduct(jsonObject)).thenReturn(product);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/products")
			.accept(MediaType.APPLICATION_JSON).content(inputInJson)
			.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@DisplayName("Junit test for updateProduct method")
	@Test
	@WithMockUser(roles = "admin")
	public void givenUpdatedProduct_whenUpdateProduct_thenReturnUpdatedProductObject() throws Exception {
		String productId = "PRO_00001";
		Product savedProduct = new Product("PRO_00001", "Mac Pro", "APP103", 10000.0, 3);
		Product updatedProduct = new Product("PRO_00001", "Mac Book Pro", "APP100", 15000.0, 1);
		String inputInJson = this.mapToJson(updatedProduct);
		ObjectNode jsonObject = objectMapper.convertValue(updatedProduct, ObjectNode.class);

		when(productService.getProductById(productId)).thenReturn(savedProduct);
		when(productService.updateProduct(productId, jsonObject)).thenReturn(updatedProduct);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/products/{id}", productId)
			.accept(MediaType.APPLICATION_JSON).content(inputInJson)
			.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@DisplayName("JUnit test for deleteProduct method")
	@Test
	@WithMockUser(roles = "admin")
	public void givenProductId_whenDeleteProduct_thenReturn200() throws Exception {
		String productId = "PRO_00001";
		willDoNothing().given(productService).deleteProduct(productId);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/products/{id}", productId);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo("Product deleted successfully!");
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}
