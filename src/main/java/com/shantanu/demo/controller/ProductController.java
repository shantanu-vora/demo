package com.shantanu.demo.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.entity.Product;
import com.shantanu.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController() {
    }

    @GetMapping
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<Product> getProductById(@PathVariable("id") @Pattern(regexp = "^PRO_\\d{5}$") String id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping
    @RolesAllowed("admin")
    public ResponseEntity<Product> addProduct(@RequestBody ObjectNode jsonObject) {
        System.out.println(jsonObject);
        Product product = productService.saveProduct(jsonObject);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    @RolesAllowed("admin")
//    public ResponseEntity<?> addProduct(@RequestBody Product product) {
//        System.out.println(product);
////        Product product = productService.saveProduct(jsonObject);
//        return ResponseEntity.status(HttpStatus.CREATED).body("Product Details saved successfully with id: " + product.getId());
//    }


//    @PutMapping("/{id}")
//    @RolesAllowed("admin")
//    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
//        return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
//    }

    @PutMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") @Pattern(regexp = "^PRO_\\d{5}$") String id,
                                                 @RequestBody ObjectNode jsonObject) {
        Product product = productService.updateProduct(id, jsonObject);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") @Pattern(regexp = "^PRO_\\d{5}$") String id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully!");
    }
}
