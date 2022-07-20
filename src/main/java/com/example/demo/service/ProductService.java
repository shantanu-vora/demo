package com.example.demo.service;

import com.example.demo.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(int id);

    void saveProduct(Product product);

    void updateProduct(int id, Product product);

    void deleteProduct(int id);
}
