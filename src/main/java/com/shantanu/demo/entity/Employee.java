package com.shantanu.demo.entity;

import com.shantanu.demo.sequencegenerator.SequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @GenericGenerator(name = "product_seq", strategy = "com.shantanu.demo.sequencegenerator.SequenceIdGenerator",
            parameters = {
                    @Parameter(name = SequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = SequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "EMP_"),
                    @Parameter(name = SequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")})
    private String id;
    private String name;
    private Double salary;
    public Employee(String name, Double salary) {
        this.name = name;
        this.salary = salary;
    }

}
