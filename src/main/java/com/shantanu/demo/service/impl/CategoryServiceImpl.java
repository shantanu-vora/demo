package com.shantanu.demo.service.impl;

import com.shantanu.demo.entity.Category;
import com.shantanu.demo.exception.CustomException;
import com.shantanu.demo.repository.CategoryRepository;
import com.shantanu.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category getCategoryById(String categoryId) {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

		if(optionalCategory.isPresent()) {
			return optionalCategory.get();
		} else {
			throw new CustomException("There is no category with the given id: " + categoryId);
		}
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

//	@Override
//	public List<String> getAllProductsOfTheCategory(String categoryId) {
//		return null;
//	}
}
