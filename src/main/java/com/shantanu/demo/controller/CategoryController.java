package com.shantanu.demo.controller;

import com.shantanu.demo.entity.Category;
import com.shantanu.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/{id}")
	public Category getCategoryById(@PathVariable("id") String categoryId) {
		return categoryService.getCategoryDetails(categoryId);
	}
}
