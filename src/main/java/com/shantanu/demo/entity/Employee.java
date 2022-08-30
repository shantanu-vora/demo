package com.shantanu.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shantanu.demo.sequencegenerator.SequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
	@GenericGenerator(name = "employee_seq", strategy = "com.shantanu.demo.sequencegenerator.SequenceIdGenerator",
		parameters = {
			@Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = SequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "EMP_"),
			@Parameter(name = SequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
	private String id;
	private String name;
	private String email;
	private Double salary;

	@JsonIgnoreProperties(value = "employees")
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "employee_project", catalog = "demo",
		joinColumns = @JoinColumn(name = "employee_id"),
		inverseJoinColumns = @JoinColumn(name = "project_id"))
	private List<Project> projects = new ArrayList<>();

	public Employee(String id, String name, String email, Double salary) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.salary = salary;
	}

}
