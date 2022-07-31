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
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/{id}")
	@RolesAllowed("admin")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") @Pattern(regexp = "^CAT_\\d{5}$") String categoryId) {
		Category category = categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok(category);
	}

	@GetMapping
	@RolesAllowed("admin")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}

//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public ResponseEntity<String> addCategory(@RequestBody ObjectNode jsonObject) {
//		System.out.println(jsonObject.get("products").get(0).get("name"));
////		Product product = productService.saveProduct(jsonObject);
//		return ResponseEntity.status(HttpStatus.CREATED).body("Product Details saved successfully with id: " + jsonObject.get("name").asText());
//	}

}
