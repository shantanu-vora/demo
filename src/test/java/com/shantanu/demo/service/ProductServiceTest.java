package com.shantanu.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shantanu.demo.entity.Product;
import com.shantanu.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

	@MockBean
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private ObjectMapper objectMapper;

	private Product product1;

	@BeforeEach
	public void setup() {
		product1 = new Product("PRO_00001", "Mac Pro", "APP103", 10000.0, 3);
	}

	@DisplayName("JUnit test for getAllProducts method")
	@Test
	public void givenProductsList_whenGetAllProducts_thenReturnProductsList() {
		Product product2 = new Product("PRO_00002", "Mac Studio", "APP789", 9000.0, 5);
		when(productRepository.findAll()).thenReturn(List.of(this.product1, product2));
		List<Product> productList = productService.getAllProducts();
		assertThat(productList).isNotNull();
		assertThat(productList.size()).isEqualTo(2);
	}

	@DisplayName("JUnit test for getAllProducts method (negative scenario)")
	@Test
	public void givenEmptyProductsList_whenGetAllProducts_thenReturnEmptyProductsList() {
		when(productRepository.findAll()).thenReturn(Collections.emptyList());
		List<Product> productList = productService.getAllProducts();
		assertThat(productList.isEmpty()).isTrue();
	}

	@DisplayName("JUnit test for getProductById method")
	@Test
	public void givenProductId_whenGetProductById_thenReturnProductObject() {
		Product product2 = new Product("PRO_00002", "Mac Studio", "APP789", 9000.0, 5);
		when(productRepository.findById("PRO_00002")).thenReturn(Optional.of(product2));
		Product savedProduct = productService.getProductById("PRO_00002");
		assertThat(savedProduct).isNotNull();
		assertEquals(product2, savedProduct);
	}

	@DisplayName("JUnit test for getProductById method (negative scenario)")
	@Test
	public void givenProductId_whenProductIdDoesNotExist_thenThrowRuntimeException() {
		Product product2 = new Product("PRO_00002", "Mac Studio", "APP789", 9000.0, 5);
		when(productRepository.findById(product2.getId())).thenReturn(Optional.of(product2));
		assertThrows(HttpClientErrorException.class,()-> productService.getProductById("PRO_00012"));
	}


//	@DisplayName("JUnit test for saveProduct method")
//	@Test
//	public void givenProductObject_whenSaveProduct_thenReturnProductObject() {
//		ObjectNode jsonObject = objectMapper.convertValue(product1, ObjectNode.class);
//		when(productRepository.save(product1)).thenReturn(product1);
//		when(productRepository.findProductByName((product1.getName()))).thenReturn(null);
//		Product savedProduct = productService.saveProduct(jsonObject);
//		assertThat(savedProduct).isNotNull();
//		assertEquals(product1, savedProduct);
//	}


//	@DisplayName("JUnit test for updateProduct method")
//	@Test
//	public void givenProductObject_whenUpdateProduct_thenReturnUpdatedProduct() {
//		ObjectNode jsonObject = objectMapper.convertValue(product1, ObjectNode.class);
//		when(productRepository.save(product1)).thenReturn(product1);
//		when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
//		product1.setName("Mac Book Pro");
//		product1.setBatchNo("APP123");
//		product1.setPrice(90000.0);
//		product1.setQuantity(2);
//		Product updatedProduct = productService.updateProduct(product1.getId(), jsonObject);
//		assertThat(updatedProduct.getName()).isEqualTo("Mac Book Pro");
//		assertThat(updatedProduct.getBatchNo()).isEqualTo("pranaya@gmail.com");
//		assertThat(updatedProduct.getPrice()).isEqualTo(90000.0);
//		assertThat(updatedProduct.getQuantity()).isEqualTo(2);
//		assertEquals(product1, updatedProduct);
//	}

	@DisplayName("JUnit test for deleteProduct method")
	@Test
	public void givenProductId_whenDeleteProduct_thenNothing() {
		String productId = "PRO_00001";
		willDoNothing().given(productRepository).delete(product1);
		when(productRepository.findById(productId)).thenReturn(Optional.of(product1));
		productService.deleteProduct(productId);
		verify(productRepository, times(1)).delete(product1);
	}
}
