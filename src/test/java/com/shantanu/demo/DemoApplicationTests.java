package com.shantanu.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

	private final Calculator calculator = new Calculator();

	@Test
	void contextLoads() {
	}

	@Test
	void testSum() {
		int expectedResult = 17;
		int actualResult = calculator.doSum(12, 3, 2);
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test
	void testProduct() {
		int expectedResult = 6;
		int actualResult = calculator.doProduct(3, 2);
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test
	void testCompareNums() {
		Boolean actualResult = calculator.compareTwoNums(3, 3);
		assertThat(actualResult).isTrue();
	}

}
