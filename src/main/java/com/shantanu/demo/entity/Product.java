package com.shantanu.demo.entity;

import com.shantanu.demo.sequencegenerator.SequenceIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @GenericGenerator(name = "product_seq", strategy = "com.shantanu.demo.sequencegenerator.SequenceIdGenerator",
    parameters = {
            @Parameter(name = SequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = SequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "PRO_"),
            @Parameter(name = SequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
    private String id;

    private String name;
    private String batchNo;
    private Double price;
    private Integer quantity;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "category_product", catalog = "abc",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>(0);

    public Product(String name, String batchNo, Double price, Integer quantity, Set<Category> categories) {
        this.name = name;
        this.batchNo = batchNo;
        this.price = price;
        this.quantity = quantity;
        this.categories = categories;
    }

    public Product(String id, String name, String batchNo, Double price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.batchNo = batchNo;
        this.price = price;
        this.quantity = quantity;
    }
}
