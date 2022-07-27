package com.shantanu.demo.service.impl;

import com.shantanu.demo.entity.Product;
import com.shantanu.demo.exception.CustomException;
import com.shantanu.demo.repository.ProductRepository;
import com.shantanu.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

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
	public Product getProductById(int id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if(optionalProduct.isPresent()) {
			return optionalProduct.get();
		} else {
			throw new CustomException("Product with product id " + id + " does not exist");
		}
	}

	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(int id, Product product) {
		Product savedProduct = getProductById(id);
			savedProduct.setName(product.getName());
			savedProduct.setBatchNo(product.getBatchNo());
			savedProduct.setPrice(product.getPrice());
			savedProduct.setQuantity(product.getQuantity());
			return productRepository.save(savedProduct);
	}

	@Override
	public void deleteProduct(int id) {
		productRepository.deleteById(id);
	}
}
