package com.shantanu.demo.service;

import com.shantanu.demo.entity.Product;
import com.shantanu.demo.exception.CustomException;
import com.shantanu.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

	private Product product1;

	@BeforeEach
	public void setup() {
		product1 = Product.builder()
			.id(1)
			.name("Mac Pro")
			.batchNo("APP103")
			.price(10000)
			.quantity(3)
			.build();
	}

	@DisplayName("JUnit test for getAllProducts method")
	@Test
	public void givenProductsList_whenGetAllProducts_thenReturnProductsList() {
		Product product2 = Product.builder()
			.id(2)
			.name("Mac Studio")
			.batchNo("APP789")
			.price(9000)
			.quantity(5)
			.build();
//		given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));
		when(productRepository.findAll()).thenReturn(List.of(this.product1, product2));
		List<Product> productList = productService.getAllProducts();
		assertThat(productList).isNotNull();
		assertThat(productList.size()).isEqualTo(2);
	}

	@DisplayName("JUnit test for getAllProducts method (negative scenario)")
	@Test
	public void givenEmptyProductsList_whenGetAllProducts_thenReturnEmptyProductsList() {
//		given((employeeRepository.findAll())).willReturn(Collections.emptyList());
		when(productRepository.findAll()).thenReturn(Collections.emptyList());
		List<Product> productList = productService.getAllProducts();
		assertThat(productList.isEmpty()).isTrue();
	}

	@DisplayName("JUnit test for getProductById method")
	@Test
	public void givenProductId_whenGetProductById_thenReturnProductObject() {
		Product product2 = Product.builder()
			.id(2)
			.name("Mac Mini")
			.batchNo("APP102")
			.price(30000)
			.quantity(4)
			.build();
		when(productRepository.findById(2)).thenReturn(Optional.ofNullable(product2));
		Product savedProduct = productService.getProductById(2);
		assertThat(savedProduct).isNotNull();
		assertEquals(product2, savedProduct);
	}

	@DisplayName("JUnit test for getProductById method (negative scenario)")
	@Test
	public void givenProductIdThatDoesNotExist_whenGetProductById_thenThrowRuntimeException() {
		Product product2 = Product.builder()
			.id(2)
			.name("Mac Mini")
			.batchNo("APP102")
			.price(30000)
			.quantity(4)
			.build();
//		Employee employee = new Employee(86,"sharath",56666);
		when(productRepository.findById(2)).thenReturn(Optional.of(product2));
		assertThrows(CustomException.class,()->{
			productService.getProductById(9);
		});
	}


	@DisplayName("JUnit test for saveProduct method")
	@Test
	public void givenProductObject_whenSaveProduct_thenReturnProductObject() {
		when(productRepository.save(product1)).thenReturn(product1);
		Product savedProduct = productService.saveProduct(product1);
		assertThat(savedProduct).isNotNull();
		assertEquals(product1, savedProduct);
	}


	@DisplayName("JUnit test for updateProduct method")
	@Test
	public void givenProductObject_whenUpdateProduct_thenReturnUpdatedProduct() {
		when(productRepository.save(product1)).thenReturn(product1);
		when(productRepository.findById(product1.getId())).thenReturn(Optional.ofNullable(product1));
		product1.setName("Mac Book Pro");
		product1.setBatchNo("APP123");
		product1.setPrice(90000);
		product1.setQuantity(2);
		Product updatedProduct = productService.updateProduct(product1.getId(), product1);
		assertThat(updatedProduct.getName()).isEqualTo("Mac Book Pro");
		assertThat(updatedProduct.getBatchNo()).isEqualTo("APP123");
		assertThat(updatedProduct.getPrice()).isEqualTo(90000);
		assertThat(updatedProduct.getQuantity()).isEqualTo(2);
		assertEquals(product1, updatedProduct);
	}

	@DisplayName("JUnit test for deleteProduct method")
	@Test
	public void givenProductId_whenDeleteProduct_thenNothing() {
		int productId = 1;
		willDoNothing().given(productRepository).deleteById(productId);
		productService.deleteProduct(productId);
		verify(productRepository, times(1)).deleteById(productId);
	}
}
