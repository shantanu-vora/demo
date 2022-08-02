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
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
	@GenericGenerator(name = "project_seq", strategy = "com.shantanu.demo.sequencegenerator.SequenceIdGenerator",
		parameters = {
			@Parameter(name = SequenceIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = SequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "PROJ_"),
			@Parameter(name = SequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
	private String id;

	@Column(unique = true)
	private String title;

	@JsonIgnoreProperties(value = "projects")
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
	@JoinTable(name = "employee_project", catalog = "demo",
		joinColumns = @JoinColumn(name = "project_id"),
		inverseJoinColumns = @JoinColumn(name = "employee_id"))
	private List<Employee> employees = new ArrayList<>();

	public Project(String title) {
		this.title = title;
	}

	public Project(String title, List<Employee> employees) {
		this.title = title;
		this.employees = employees;
	}

	public Project(String id, String title) {
		this.id = id;
		this.title = title;
	}
}
