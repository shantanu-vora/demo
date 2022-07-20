package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") int id) {
        Product product = null;
        try {
            product = productService.findById(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return product;
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @PutMapping("/products/{id}")
    public void updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        try {
            productService.updateProduct(id, product);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("products/{id}")
    public void deleteProduct(@PathVariable("id") int id) {
        try {
                productService.deleteProduct(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
