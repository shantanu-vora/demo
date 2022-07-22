package com.shantanu.demo.service;

import com.shantanu.demo.entity.Employee;
import com.shantanu.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	@PostConstruct
	public void initializeEmployeeTable() {
		employeeRepository.saveAll(
						Stream.of(
										new Employee("sarathi", 20000),
										new Employee("pranaya", 55000),
										new Employee("puneet", 120000),
										new Employee("ravi", 40000)
						).collect(Collectors.toList()));
	}

	public Employee getEmployee(int employeeId) {
		return employeeRepository.findById(employeeId)
						.orElse(null);
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
}
