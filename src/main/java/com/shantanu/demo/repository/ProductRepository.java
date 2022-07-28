package com.shantanu.demo.repository;

import com.shantanu.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	Product findProductByName(String name);
}
