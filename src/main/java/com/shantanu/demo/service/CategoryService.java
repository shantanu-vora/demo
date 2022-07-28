package com.shantanu.demo.service;

import com.shantanu.demo.entity.Category;

import java.util.List;

public interface CategoryService {

	public Category getCategoryDetails(String categoryId);

	public List<String> getAllProductsOfTheCategory(String categoryId);
}
