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
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
	@GenericGenerator(name = "category_seq", strategy = "com.shantanu.demo.sequencegenerator.SequenceIdGenerator",
		parameters = {
						@Parameter(name = SequenceIdGenerator.INCREMENT_PARAM, value = "1"),
						@Parameter(name = SequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "CAT_"),
						@Parameter(name = SequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
	private String id;

	@Column(unique = true)
	private String name;

	@JsonIgnoreProperties(value = "categories")
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
	@JoinTable(name = "category_product", catalog = "demo",
					joinColumns = @JoinColumn(name = "category_id"),
					inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products = new ArrayList<>();

	public Category(String id, String name) {
		this.id = id;
		this.name = name;
	}
}



