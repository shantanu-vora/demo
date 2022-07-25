package com.shantanu.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
    @NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String batchNo;
    private double price;
    private int quantity;

    public Product(String name, String batchNo, double price, int quantity) {
        this.name = name;
        this.batchNo = batchNo;
        this.price = price;
        this.quantity = quantity;
    }
}
