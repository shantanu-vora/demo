package com.shantanu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shantanu.demo.entity.Product;
import com.shantanu.demo.service.EmployeeService;
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
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@MockBean
	private EmployeeService employeeService;

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
		Product product1 = new Product(1, "Mac Mini", "APP102", 30000, 3);
		Product product2 = new Product(2, "Mac Pro", "APP103", 20000, 1);
		Product product3 = new Product(3, "Mac iMac24\"", "APP102", 30000, 3);
		List<Product> productList = new ArrayList<>(Arrays.asList(product1, product2, product3));
		when(productService.getAllProducts()).thenReturn(productList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(productList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@DisplayName("Junit test for getProductById")
	@Test
	@WithMockUser(roles = {"user", "admin"})
	public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception {
		int productId = 1;
		Product product = Product.builder()
			.id(1)
			.name("Mac Pro")
			.batchNo("APP103")
			.price(10000)
			.quantity(3).build();
		when(productService.getProductById(product.getId())).thenReturn(product);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/{id}", productId)
			.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(product);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@DisplayName("Junit test for addProduct method")
	@Test
	@WithMockUser(roles = "admin")
	public void givenProductObject_whenAddProduct_thenReturnSavedProduct() throws Exception {
		Product product = Product.builder()
			.id(1)
			.name("Mac Pro")
			.batchNo("APP103")
			.price(10000)
			.quantity(3).build();

		String inputInJson = this.mapToJson(product);
		when(productService.saveProduct(any(Product.class))).thenReturn(product);

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
		int productId = 1;
		Product savedProduct = Product.builder()
			.id(1)
			.name("Mac Pro")
			.batchNo("APP103")
			.price(10000)
			.quantity(3).build();

		Product updatedProduct = Product.builder()
			.id(1)
			.name("Mac Book Pro")
			.batchNo("APP100")
			.price(15000)
			.quantity(1).build();

		when(productService.getProductById(productId)).thenReturn(savedProduct);
		when(productService.updateProduct(1, updatedProduct)).thenReturn(updatedProduct);
		String inputInJson = this.mapToJson(updatedProduct);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/products/{id}", productId)
			.accept(MediaType.APPLICATION_JSON).content(inputInJson)
			.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
	}

	@DisplayName("JUnit test for deleteProduct method")
	@Test
	@WithMockUser(roles = "admin")
	public void givenProductId_whenDeleteProduct_thenReturn200() throws Exception {
		int productId = 1;
		willDoNothing().given(productService).deleteProduct(productId);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/products/{id}", productId)
				.contentType(MediaType.TEXT_PLAIN_VALUE);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo("Employee deleted successfully!");
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
