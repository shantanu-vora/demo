package com.shantanu.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Product {

    private int id;
    private  String name;
    private String batchNo;
    private double price;
    private int quantity;

    public Product(int id, String name, String batchNo, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.batchNo = batchNo;
        this.price = price;
        this.quantity = quantity;
    }
}
