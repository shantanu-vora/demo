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
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
	@GenericGenerator(name = "category_seq", strategy = "com.shantanu.demo.sequencegenerator.SequenceIdGenerator",
					parameters = {
									@Parameter(name = SequenceIdGenerator.INCREMENT_PARAM, value = "1"),
									@Parameter(name = SequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "CAT_"),
									@Parameter(name = SequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
	private String id;
	private String name;
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
	@JoinTable(name = "category_product", catalog = "abc",
					joinColumns = @JoinColumn(name = "category_id"),
					inverseJoinColumns = @JoinColumn(name = "product_id"))
	private Set<Product> products = new HashSet<>(0);

	public Category(String name) {
		this.name = name;
	}

	public Category(String name, Set<Product> products) {
		this.name = name;
		this.products = products;
	}
}
