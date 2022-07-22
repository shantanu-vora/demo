package com.shantanu.demo.service;

import com.shantanu.demo.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(int id);

    void saveProduct(Product product);

    void updateProduct(int id, Product product);

    void deleteProduct(int id);
}
