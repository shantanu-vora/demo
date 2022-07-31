package com.shantanu.demo.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.entity.Category;
import com.shantanu.demo.entity.Product;
import com.shantanu.demo.repository.CategoryRepository;
import com.shantanu.demo.repository.ProductRepository;
import com.shantanu.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

//	@PostConstruct
//	public void initializeEmployeeTable() {
//		productRepository.saveAll(
//			Stream.of(
//				new Product(1, "Mac Book Pro", "APP123", 9000.0, 2),
//				new Product(2, "Mac Book Air", "APP456", 60000.0, 1),
//				new Product(3, "Mac Studio", "APP789", 9000.0, 5),
//				new Product(4, "iMac 24\"", "APP101", 24000.0, 1),
//				new Product(5, "Mac Mini", "APP102", 30000.0, 4),
//				new Product(6, "Mac Pro", "APP103", 10000.0, 3)
//			).collect(Collectors.toList()));
//	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(String id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if(optionalProduct.isPresent()) {
			return optionalProduct.get();
		} else {
//			throw new CustomException("Product with product id " + id + " does not exist");
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Product saveProduct(ObjectNode jsonObject) {
		Product product = new Product();
		product.setName(jsonObject.get("name").asText());
		setProductFields(product, jsonObject);
		List<Category> categories = getCategoryList(jsonObject);
		product.setCategories(categories);
		Product existingProduct = productRepository.findProductByName(product.getName());

		if(existingProduct == null) {
			return productRepository.save(product);
		} else {
//			throw new CustomException("Product name already exists in the Database");
			throw new DataIntegrityViolationException("Product name already exists. Enter a unique product name");
		}
	}


	@Override
	public Product updateProduct(String id, ObjectNode jsonObject) {
		Product savedProduct = getProductById(id);
		setProductFields(savedProduct, jsonObject);
		List<Category> categories = getCategoryList(jsonObject);
		savedProduct.setCategories(categories);
//		Product existingProduct = productRepository.findProductByName(savedProduct.getName());

//		if(existingProduct == null) {
			return productRepository.save(savedProduct);
//		} else {
//			throw new CustomException("Product name already exists in the Database");
//			throw new DataIntegrityViolationException("Product name already exists. Enter a unique product name");
//		}

	}

	@Override
	public void deleteProduct(String id) {
		Product product = this.getProductById(id);
		productRepository.delete(product);
	}

	private void setProductFields(Product product, ObjectNode jsonObject) {
//		product.setName(jsonObject.get("name").asText());
		product.setBatchNo(jsonObject.get("batchNo").asText());
		product.setPrice(jsonObject.get("price").asDouble());
		product.setQuantity(jsonObject.get("quantity").asInt());
	}

	private List<Category> getCategoryList(ObjectNode jsonObject) {
		List<Category> categories = new ArrayList<>();
		jsonObject.get("categories").forEach(jsonNode -> {
			String categoryName = jsonNode.asText();
			if(!categoryName.equals("")) {
				Category existingCategory = categoryRepository.findCategoryByName(categoryName);
				if(existingCategory == null) {
					Category category = new Category();
					category.setName(categoryName);
					categories.add(category);
				} else {
					categories.add(existingCategory);
				}
			}
		});
		return categories;
	}
}





//		jsonObject.get("categories").forEach(jsonNode -> categories.add(jsonNode.asText()));

//		for(String categoryName: categories) {
//			if(!categoryName.equals("")) {
//				Category existingCategory = categoryRepository.findCategoryByName(categoryName);
//				if(existingCategory == null) {
//					Category category = new Category();
//					category.setName(categoryName);
//					product.getCategories().add(category);
//				} else {
//					product.getCategories().add(existingCategory);
//				}
//			}
//		}
