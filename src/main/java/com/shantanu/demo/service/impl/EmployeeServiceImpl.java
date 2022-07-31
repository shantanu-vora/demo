package com.shantanu.demo.service.impl;

import com.shantanu.demo.entity.Employee;
import com.shantanu.demo.repository.EmployeeRepository;
import com.shantanu.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

//	@PostConstruct
//	public void initializeEmployeeTable() {
//		employeeRepository.saveAll(
//			Stream.of(
//				new Employee("sarathi", 20000.0),
//				new Employee("pranaya", 55000.0),
//				new Employee("puneet", 120000.0),
//				new Employee("ravi", 40000.0)
//			).collect(Collectors.toList()));
//	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(String employeeId) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

		if(optionalEmployee.isPresent()) {
			return optionalEmployee.get();
		} else {
//			throw new CustomException("Employee with employee id " + employeeId + " does not exist");
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

}
