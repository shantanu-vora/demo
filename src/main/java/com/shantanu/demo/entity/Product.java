package com.shantanu.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shantanu.demo.sequencegenerator.SequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Column(unique = true, name = "name")
    private String name;
    private String batchNo;
    private Double price;
    private Integer quantity;
    @JsonIgnoreProperties(value = "products")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "category_product", catalog = "demo",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    public Product(String id, String name, String batchNo, Double price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.batchNo = batchNo;
        this.price = price;
        this.quantity = quantity;
    }
}
