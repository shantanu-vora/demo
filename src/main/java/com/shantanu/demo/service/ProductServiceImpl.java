package com.shantanu.demo.service;

import com.shantanu.demo.entity.Product;
import com.shantanu.demo.exception.CustomException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final List<Product> productList = new ArrayList<>();

    @Override
    public List<Product> findAll() {
        productList.clear();
        generateProductList();
        return productList;
    }

    @Override
    public Product findById(int id) {
        Product product = null;
        for(Product prod : productList) {
            if (prod.getId() == id) {
                product = prod;
            }
        }

        if(product == null) {
            throw new CustomException("Did not find product with id " + id);
        } else {
            return product;
        }
    }

    @Override
    public void saveProduct(Product product) {
        productList.add(product);
        System.out.println(productList);
    }

    @Override
    public void updateProduct(int id, Product product) {
        Product oldProduct = findById(id);
        System.out.println(oldProduct);
        if(oldProduct == null) {
            throw new CustomException("Did not find product with id " + id);
        } else {
            oldProduct.setName(product.getName());
            oldProduct.setBatchNo(product.getBatchNo());
            oldProduct.setPrice(product.getPrice());
            oldProduct.setQuantity(product.getQuantity());
        }
    }

    @Override
    public void deleteProduct(int id) {
        Product oldProduct = findById(id);
        System.out.println(oldProduct);
        if(oldProduct == null) {
            throw new CustomException("Did not find product with id " + id);
        } else {
            productList.remove(oldProduct);
        }
    }

    private void generateProductList() {
        productList.add(new Product(1, "Mac Book Pro", "APP123", 9000.0, 2));
        productList.add(new Product(2, "Mac Book Air", "APP456", 60000.0, 1));
        productList.add(new Product(3, "Mac Studio", "APP789", 9000.0, 5));
        productList.add(new Product(4, "iMac 24\"", "APP101", 24000.0, 1));
        productList.add(new Product(5, "Mac Mini", "APP102", 30000.0, 4));
        productList.add(new Product(6, "Mac Pro", "APP103", 10000.0, 3));
    }
}
