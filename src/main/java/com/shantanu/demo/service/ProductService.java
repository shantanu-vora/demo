package com.shantanu.demo.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    Product getProductById(String id);
    Product saveProduct(ObjectNode jsonObject);
//    Product updateProduct(String id, Product product);
    Product updateProduct(String id, ObjectNode jsonObject);
    void deleteProduct(String id);
}
