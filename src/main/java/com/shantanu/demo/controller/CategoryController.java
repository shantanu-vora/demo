package com.shantanu.demo.controller;

import com.shantanu.demo.entity.Category;
import com.shantanu.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {

	@Autowired
	private CategoryService categoryService;


//	@Pattern(regexp = "^CAT_\\d{5}$")
	@GetMapping("/{id}")
	@RolesAllowed({"admin", "user"})
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") String categoryId) {
		Category category = categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok(category);
	}

	@GetMapping
	@RolesAllowed({"admin", "user"})
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
}
