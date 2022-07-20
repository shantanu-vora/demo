package com.example.demo.model;

public class Product {

    private int id;
    private  String name;
    private String batchNo;
    private double price;
    private int quantity;

    public Product() {
    }

    public Product(int id, String name, String batchNo, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.batchNo = batchNo;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
