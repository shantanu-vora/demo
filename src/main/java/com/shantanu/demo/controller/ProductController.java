package com.shantanu.demo.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.entity.Product;
import com.shantanu.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController() {
    }

    @GetMapping
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
        Product product;
        product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed("admin")
    public ResponseEntity<?> addProduct(@RequestBody ObjectNode jsonObject) {
        System.out.println(jsonObject);
        Product product = productService.saveProduct(jsonObject);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product Details saved successfully with id: " + product.getId());
    }

    @PutMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
    }
}
