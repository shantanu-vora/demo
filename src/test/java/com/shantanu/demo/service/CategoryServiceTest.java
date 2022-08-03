package com.shantanu.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shantanu.demo.entity.Category;
import com.shantanu.demo.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceTest {

	@MockBean
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ObjectMapper objectMapper;

	private Category category1;

	@BeforeEach
	public void setup() {
		category1 = new Category("CAT_00001", "Category1");
	}

	@DisplayName("JUnit test for getCategoryById method")
	@Test
	public void givenCategoryId_whenGetCategoryById_thenReturnCategoryObject() {
		Category category2 = new Category("CAT_00002", "Category2");
		when(categoryRepository.findById("CAT_00002")).thenReturn(Optional.of(category2));
		Category savedCategory = categoryService.getCategoryById("CAT_00002");
		assertThat(savedCategory).isNotNull();
		assertEquals(category2, savedCategory);
	}

	@DisplayName("JUnit test for getAllCategories method")
	@Test
	public void givenCategoriesList_whenGetAllCategories_thenReturnCategoryList() {
		Category category2 = new Category("CAT_00002", "Category2");
		when(categoryRepository.findAll()).thenReturn(List.of(this.category1, category2));
		List<Category> categoryList = categoryService.getAllCategories();
		assertThat(categoryList).isNotNull();
		assertThat(categoryList.size()).isEqualTo(2);
	}
}